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

    // Constructor
    public EtiquetadorEntitats(DiccionariEntitats diccionariEntitats){
        this.diccionariEntitats = diccionariEntitats;
        this.totalsTokens = new ArrayList<>();
        this.entitats = new ArrayList<>();
    }

    // Etiqueta els tokens d'un poema com entitats
    public void etiquetaPoema(String rutaFitxerPoema){

        ArrayList<String> linies = ParserPoema.getLinies(rutaFitxerPoema);

        System.out.println("Etiquetatge BIO — Vers per vers");
        System.out.printf("  %-28s %-12s%n", "Token", "Etiqueta BIO");
        for (String linia : linies) {

            if (linia.isBlank()) { System.out.println(); continue; }

            numVersos++;

            List<TokenEtiquetat> tl = etiqueta(linia, diccionariEntitats);
            totalsTokens.addAll(tl);

            System.out.println("Tokens:" + tl.size());

            System.out.printf("  --- Vers %-2d: %s%n", numVersos, linia);
            for (TokenEtiquetat t : tl) {
                if (t.esEntitat()) {
                    // Mostra els tokens d'entitat amb un prefix visual
                    String prefix = t.esInici() ? "  -> " : "  │ ";
                    System.out.printf("%s%-28s %s%n", prefix, t.getOriginal(), t.getEtiqueta());
                } else {
                    System.out.printf("    %-28s %s%n", t.getOriginal(), t.getEtiqueta());
                }
            }
            System.out.println();
        }
    }

    // Tokenitza una línia de text (vers)
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

    // Retorna la llista de tokens etiquetats d'una línia emprant el diccionari
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

                // Filtre de majúscula
                if (entrada.requereixMajuscula && !Character.isUpperCase(paraula.charAt(0))) {
                    continue;
                }

                // Comprovació de coincidència
                boolean coincideix = true;
                for (int j = 0; j < n; j++) {
                    if (!normalitza(paraules[i + j]).equals(entrada.tokens[j])) {
                        coincideix = false;
                        break;
                    }
                }

                if (coincideix) {
                    // Assignació d'etiquetes BIO
                    String tipusNom = entrada.tipus.name();

                    // B- per al primer token de l'entitat
                    resultat.add(new TokenEtiquetat( paraules[i], normalitza(paraules[i]), "B-" + tipusNom));

                    // I- per als tokens interiors de l'entitat
                    for (int j = 1; j < n; j++) {
                        resultat.add(new TokenEtiquetat(paraules[i + j], normalitza(paraules[i + j]), "I-" + tipusNom));
                    }

                    i += n; // descarta els tokens ja processats
                    trobat = true;
                    break; // passa a la següent posició
                }
            }

            // Token sense coincidència: O
            if (!trobat) {
                resultat.add(new TokenEtiquetat( paraula, normalitza(paraula), "O"));
                i++;
            }
        }

        return resultat;
    }


    // Etiqueta els tokens d'un text de múltiples línies
    public List<TokenEtiquetat> etiquetaText(String text, DiccionariEntitats diccionariEntitats) {
        List<TokenEtiquetat> tots = new ArrayList<>();
        for (String linia : text.split("\n")) {
            if (!linia.isBlank()) {
                tots.addAll(etiqueta(linia, diccionariEntitats));
            }
        }
        return tots;
    }


    // Estableix les entitats a partir dels tokens etiquetats
    public void extrauEntitats(){
        this.entitats = extrauEntitats(totalsTokens);
    }


    // Retorna una llista de les entitats anomenades a partir dels tokens
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
                while (j < tokens.size()  && tokens.get(j).getEtiqueta().startsWith("I-")) {
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

    // Imprimeix els tokens analitzats
    public void mostraTokens(){
        System.out.println("Seqüència BIO completa en Format CoNLL:");
        System.out.printf("  %-6s %-28s %-12s%n", "Pos.", "Token", "BIO");
        for (int i = 0; i < totalsTokens.size(); i++) {
            TokenEtiquetat t = totalsTokens.get(i);
            System.out.printf("  %-6d %-28s %s%n", i + 1, t.getOriginal(), t.getEtiqueta());
        }
        System.out.println();
    }

    // Imprimeix les entitats anomenades reconegudes
    public void mostraEntitats(){

        System.out.println("Entitats anomenades reconegudes:");

        List<String[]> entitats = extrauEntitats(totalsTokens);

        Map<String, List<String>> perTipus = new LinkedHashMap<>();
        for (EntitatNomenada.TipusEntitat tipusEntitat : EntitatNomenada.TipusEntitat.values()) perTipus.put(tipusEntitat.name(), new ArrayList<>());
        for (String[] e : entitats) perTipus.get(e[1]).add(e[0]);

        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) {
            List<String> llista = perTipus.get(te.name());
            if (!llista.isEmpty()) {
                System.out.printf("  %s  %-20s->  %s%n", te.name(), "(" + te.getDescripcio() + ")", String.join(", ", llista));
            }
        }
    }

    // Imprimeix les estadistiques de l'etiquetador
    public void estadistiquesEtiquetador(){

        System.out.println("\nEstadístiques del Poema");

        long numTotal  = totalsTokens.size();
        long numBs  = totalsTokens.stream().filter(TokenEtiquetat::esInici).count();
        long numOs = totalsTokens.stream().filter(t -> !t.esEntitat()).count();
        long nBIO = numTotal - numOs;

        // Recompte per tipus d'entitat
        Map<String, Long> comptesPerTipus = totalsTokens.stream()
            .filter(TokenEtiquetat::esInici)
            .collect(Collectors.groupingBy(
                t -> t.getTipus(), Collectors.counting()));

        System.out.printf("Entrades al diccionari: %3d%n", diccionariEntitats.getEntrades().size());
        System.out.printf("Versos processats: %3d%n", numVersos);
        System.out.printf("Tokens totals: %3d%n", numTotal);
        System.out.printf("Entitats reconegudes: %3d%n", numBs);

        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) {
            long c = comptesPerTipus.getOrDefault(te.name(), 0L);
            if (c > 0) System.out.printf("    %-5s                         : %3d%n", te.name(), c);
        }

        System.out.printf("Tokens etiquetats B+I: %3d%n", nBIO);
        System.out.printf("Tokens fora d'entitat (O) : %3d%n", numOs);
        System.out.printf("Cobertura NER: %5.1f%%%n", 100.0f * nBIO / numTotal);
    }


    // Retorna vertader si la paraula és una entitat del tipus
    public boolean esEntitat(String paraula, String tipus){
        for(TokenEtiquetat tokenEtiquetat : totalsTokens) {
                if (tokenEtiquetat.getForma().equals(paraula.toLowerCase()) && tokenEtiquetat.esEntitat() && tokenEtiquetat.getTipus().equals(tipus)) {
                    return true;
            }
        }
        return false;
    }

    // Retorna vertader si la paraula és una entitat del tipus I-
    public boolean esIniciEntitat(String paraula){
        for(TokenEtiquetat tokenEtiquetat : totalsTokens) {
            if (tokenEtiquetat.getForma().equals(paraula.toLowerCase()) && tokenEtiquetat.esEntitat() && tokenEtiquetat.esInici()) {
                return true;
            }
        }
        return false;
    }

    // Retorna vertader si la paraula és una entitat de qualsevol tipus
    public boolean esEntitat(String paraula){
        for(TokenEtiquetat tokenEtiquetat : totalsTokens) {
            if (tokenEtiquetat.getForma().equals(paraula.toLowerCase()) && tokenEtiquetat.esEntitat()) {
                return true;
            }
        }
        return false;
    }

    // Retorna el tipus d'entitat de la paraula
    public String tipusEntitat(String paraula){
        for(TokenEtiquetat tokenEtiquetat : totalsTokens) {
            if (tokenEtiquetat.getForma().equals(paraula.toLowerCase()) && tokenEtiquetat.esEntitat()) {
                return tokenEtiquetat.getTipus();
            }
        }
        return null;
    }
}
