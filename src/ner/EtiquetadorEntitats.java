package ner;

import parser.ParserPoema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ner.EntradaDiccionari.normalitza;

public class EtiquetadorEntitats {

    DiccionariEntitats diccionariEntitats;
    List<TokenEtiquetat> totalsTokens;
    List<String[]> entitats;
    int numVersos = 0;

    public EtiquetadorEntitats(DiccionariEntitats diccionariEntitats){
        this.diccionariEntitats = diccionariEntitats;
        this.totalsTokens = new ArrayList<>();
        this.entitats = new ArrayList<>();
    }

    public void tokenitzaPoema(String rutaFitxerPoema){

        ArrayList<String> linies = ParserPoema.getLinies(rutaFitxerPoema);

        System.out.println("[ ETIQUETATGE BIO — VERS PER VERS ]");
        System.out.printf("  %-28s %-12s%n", "TOKEN", "ETIQUETA BIO");
        for (String linia : linies) {
            if (linia.isBlank()) { System.out.println(); continue; }

            numVersos++;

            List<TokenEtiquetat> tl = etiqueta(linia, diccionariEntitats);
            totalsTokens.addAll(tl);

            System.out.println("TOKENS:" + tl.size());

            System.out.printf("  ── Vers %-2d: %s%n", numVersos, linia);
            for (TokenEtiquetat t : tl) {
                if (t.esEntitat()) {
                    // Mostra els tokens d'entitat amb un prefix visual
                    String prefix = t.esInici() ? "  ▶ " : "  │ ";
                    System.out.printf("%s%-28s %s%n", prefix, t.getOriginal(), t.getEtiqueta());
                } else {
                    System.out.printf("    %-28s %s%n", t.getOriginal(), t.getEtiqueta());
                }
            }
            System.out.println();
        }
    }

    /**
     * Tokenitza una línia de text separant per espais i tractant les
     * contraccions amb apòstrof pròpies del català.
     *
     * Exemples:
     *   "l'Empordà"   →  ["l'",  "Empordà"]
     *   "d'Aragó"     →  ["d'",  "Aragó"]
     *   "s'estima"    →  ["s'",  "estima"]
     *   "Catalunya,"  →  ["Catalunya,"]        (sense apòstrof intern)
     */
    public static String[] tokenitza(String linia) {
        List<String> tokens = new ArrayList<>();
        for (String part : linia.trim().split("\\s+")) {
            if (part.isEmpty()) continue;
            // Cerca apòstrof intern (no al principi ni al final)
            int ap = -1;
            for (int k = 1; k < part.length() - 1; k++) {
                char c = part.charAt(k);
                if (c == '\'' || c == '\u2019') { ap = k; break; }
            }
            if (ap > 0) {
                tokens.add(part.substring(0, ap + 1)); // "l'"
                tokens.add(part.substring(ap + 1));    // "Empordà"
            } else {
                tokens.add(part);
            }
        }
        return tokens.toArray(new String[0]);
    }

    public List<TokenEtiquetat> etiqueta(String linia, DiccionariEntitats diccionariEntitats) {
        String[] paraules = tokenitza(linia);
        List<TokenEtiquetat> resultat = new ArrayList<>();
        int i = 0;

        while (i < paraules.length) {
            String paraula = paraules[i];
            if (paraula.isEmpty()) { i++; continue; }

            boolean trobat = false;

            for (EntradaDiccionari entrada : diccionariEntitats.getEntrades()) {
                int n = entrada.longitud();
                if (i + n > paraules.length) continue;

                // ── Filtre de majúscula ──────────────────────────────────
                // Les entitats de noms propis (Rosa, Joan, Catalunya…) requereixen
                // que el primer token del text comenci amb majúscula.
                // Això evita marcar "rosa" (la flor) com a B-PER.
                if (entrada.requereixMajuscula
                        && !Character.isUpperCase(paraula.charAt(0))) {
                    continue;
                }

                // ── Comprovació de coincidència ──────────────────────────
                // Tots els tokens de l'expressió (normalitzats) han de coincidir
                // amb els tokens del text a partir de la posició i.
                boolean coincideix = true;
                for (int j = 0; j < n; j++) {
                    if (!normalitza(paraules[i + j]).equals(entrada.tokens[j])) {
                        coincideix = false;
                        break;
                    }
                }

                if (coincideix) {
                    // ── Assignació d'etiquetes BIO ───────────────────────
                    String tipusNom = entrada.tipus.name();

                    // B- per al primer token de l'entitat
                    resultat.add(new TokenEtiquetat(
                            paraules[i],
                            normalitza(paraules[i]),
                            "B-" + tipusNom));

                    // I- per als tokens interiors de l'entitat
                    for (int j = 1; j < n; j++) {
                        resultat.add(new TokenEtiquetat(
                                paraules[i + j],
                                normalitza(paraules[i + j]),
                                "I-" + tipusNom));
                    }

                    i += n;     // salta els tokens ja processats
                    trobat = true;
                    break;      // passa a la següent posició
                }
            }

            // ── Token sense coincidència ─────────────────────────────────
            if (!trobat) {
                resultat.add(new TokenEtiquetat(
                        paraula, normalitza(paraula), "O"));
                i++;
            }
        }

        return resultat;
    }


    /**
     * Etiqueta un text multi-línia (p. ex. un poema sencer).
     *
     * @param text text complet, pot contenir salts de línia
     * @return llista de tots els tokens etiquetats
     */
    public List<TokenEtiquetat> etiquetaText(String text, DiccionariEntitats diccionariEntitats) {
        List<TokenEtiquetat> tots = new ArrayList<>();
        for (String linia : text.split("\n")) {
            if (!linia.isBlank()) {
                tots.addAll(etiqueta(linia, diccionariEntitats));
            }
        }
        return tots;
    }


    public void extrauEntitats(){
        this.entitats = extrauEntitats(totalsTokens);
    }

    /**
     * Extreu les entitats reconegudes agrupant els tokens B + I consecutius
     * del mateix tipus en una sola expressió.
     *
     * Exemple:
     *   [B-PER "joan", I-PER "maragall", O "canta"]
     *   →  entitat: forma="joan maragall", tipus="PER"
     *
     * @param tokens llista de tokens etiquetats
     * @return llista de parells  { forma_normalitzada, tipus }
     */
    public List<String[]> extrauEntitats(List<TokenEtiquetat> tokens) {
        List<String[]> entitats = new ArrayList<>();
        int i = 0;
        while (i < tokens.size()) {
            TokenEtiquetat t = tokens.get(i);
            if (t.esInici()) {
                String tipus = t.getTipus();
                StringBuilder expr = new StringBuilder(t.getForma());
                int j = i + 1;
                // Recull els tokens I- consecutius del mateix tipus
                while (j < tokens.size()
                        && tokens.get(j).getEtiqueta().startsWith("I-")) {
                    expr.append(" ").append(tokens.get(j).getForma());
                    j++;
                }
                entitats.add(new String[]{ expr.toString(), tipus });
                i = j;
            } else {
                i++;
            }
        }
        return entitats;
    }

    public void mostraTokens(){
        System.out.println("[ SEQÜÈNCIA BIO COMPLETA — FORMAT CoNLL ]");
        System.out.printf("  %-6s %-28s %-12s%n", "Pos.", "TOKEN", "BIO");
        for (int idx = 0; idx < totalsTokens.size(); idx++) {
            TokenEtiquetat t = totalsTokens.get(idx);
            System.out.printf("  %-6d %-28s %s%n", idx + 1, t.getOriginal(), t.getEtiqueta());
        }
        System.out.println();
    }

    public void mostraEntitats(){

        System.out.println("[ ENTITATS RECONEGUDES ]");

        List<String[]> entitats = extrauEntitats(totalsTokens);

        Map<String, List<String>> perTipus = new LinkedHashMap<>();
        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) perTipus.put(te.name(), new ArrayList<>());
        for (String[] e : entitats) perTipus.get(e[1]).add(e[0]);

        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) {
            List<String> llista = perTipus.get(te.name());
            if (!llista.isEmpty()) {
                System.out.printf("  %s  %-20s→  %s%n", te.name(), "(" + te.getDescripcio() + ")", String.join(", ", llista));
            }
        }
    }

    public void estadistiquesEtiquetador(){

        System.out.println("\n[ ESTADÍSTIQUES DEL POEMA ]");

        long nTotal     = totalsTokens.size();
        long nB         = totalsTokens.stream().filter(TokenEtiquetat::esInici).count();
        long nO         = totalsTokens.stream().filter(t -> !t.esEntitat()).count();
        long nBIO       = nTotal - nO;

        // Recompte per tipus d'entitat
        Map<String, Long> comptesPerTipus = totalsTokens.stream()
            .filter(TokenEtiquetat::esInici)
            .collect(Collectors.groupingBy(
                t -> t.getTipus(), Collectors.counting()));

        System.out.printf("  Entrades al diccionari          : %3d%n", diccionariEntitats.getEntrades().size());
        System.out.printf("  Versos processats               : %3d%n", numVersos);
        System.out.printf("  Tokens totals                   : %3d%n", nTotal);
        System.out.printf("  Entitats reconegudes            : %3d%n", nB);
        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) {
            long c = comptesPerTipus.getOrDefault(te.name(), 0L);
            if (c > 0) System.out.printf("    %-5s                         : %3d%n", te.name(), c);
        }
        System.out.printf("  Tokens etiquetats B+I           : %3d%n", nBIO);
        System.out.printf("  Tokens fora d'entitat (O)       : %3d%n", nO);
        System.out.printf("  Cobertura NER                   : %5.1f%%%n", 100.0 * nBIO / nTotal);
    }
}
