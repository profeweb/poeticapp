package sentiments;

import java.util.*;

public class AnalisiSentiments {

    public enum Sentiment {
        MOLT_POSITIU ("Molt positiu",          1.2,  Double.MAX_VALUE),
        POSITIU      ("Positiu",               0.4,  1.2),
        LLEU_POSITIU ("Lleugerament positiu",  0.1,  0.4),
        NEUTRE       ("Neutre",               -0.1,  0.1),
        LLEU_NEGATIU ("Lleugerament negatiu", -0.4, -0.1),
        NEGATIU      ("Negatiu",             -1.2, -0.4),
        MOLT_NEGATIU ("Molt negatiu",  Double.NEGATIVE_INFINITY, -1.2);

        final String etiqueta;
        final double llindarInf, llindarSup;

        Sentiment(String etiqueta, double inf, double sup) {
            this.etiqueta   = etiqueta;
            this.llindarInf = inf;
            this.llindarSup = sup;
        }

        public String getEtiqueta(){ return  this.etiqueta; }

        /** Retorna el Sentiment corresponent a la puntuació donada. */
        public static Sentiment de(double p) {
            for (Sentiment s : values())
                if (p >= s.llindarInf && p < s.llindarSup) return s;
            return MOLT_NEGATIU;
        }

        /** Símbol ASCII de 3 caràcters per a la sortida compacta. */
        public String simbol() {
            return switch (this) {
                case MOLT_POSITIU -> "[++]";
                case POSITIU      -> "[ +]";
                case LLEU_POSITIU -> "[ ~]";
                case NEUTRE       -> "[ 0]";
                case LLEU_NEGATIU -> "[ ~]";
                case NEGATIU      -> "[ -]";
                case MOLT_NEGATIU -> "[--]";
            };
        }
    }

    public enum Rol {
        LEXIC          ("LEX", "Paraula al lexicó amb puntuació"),
        NEGADOR        ("NEG", "Negador — inverteix la polaritat (x-1)"),
        INTENSIFICADOR ("INT", "Intensificador — amplifica la polaritat (x1.5)"),
        DIMINUIDOR     ("DIM", "Diminuïdor — redueix la polaritat (x0.5)"),
        NEUTRE         (" — ", "Sense càrrega afectiva (no al lexicó)");

        final String codi;
        final String descripcio;
        Rol(String codi, String descripcio) { this.codi = codi; this.descripcio = descripcio; }
        public String getCodi() { return codi; }
    }


    /** Lexicó: forma normalitzada → puntuació de polaritat. */
    public final Map<String, Double> lexic;

    /** Negadors: paraules que inverteixen la polaritat dels tokens propers. */
    public final Set<String> negadors;

    /**
     * Modificadors: paraula → factor multiplicador.
     * > 1.0 = intensificador  |  < 1.0 = diminuïdor
     */
    public final Map<String, Double>  modificadors;

    /**
     * Finestra de negació: nombre màxim de tokens endavant afectats
     * per un negador. La finestra es decrementa amb cada token no-negador.
     */
    private static final int FINESTRA_NEGACIO = 3;

    public AnalisiSentiments() {
        lexic        = new LinkedHashMap<>();
        negadors     = new LinkedHashSet<>();
        modificadors = new LinkedHashMap<>();
        construeixLexic();
        construeixNegadors();
        construeixModificadors();
    }

    /** Normalitza: minúscules + suprimeix puntuació als extrems. */
    public static String normalitza(String s) {
        return s.toLowerCase()
                .replaceAll("^[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+", "")
                .replaceAll("[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+$",  "")
                .trim();
    }

    /** Afegeix una paraula i la seva puntuació de polaritat al lexicó. */
    private void af(String paraula, double puntuacio) {
        lexic.put(normalitza(paraula), puntuacio);
    }

    private void construeixLexic() {

        // ── Molt positiu (+2.0 a +1.5) ───────────────────────────────────
        af("amor",           +2.0);  af("amor",          +2.0);
        af("joia",           +2.0);  af("llibertat",     +2.0);
        af("pau",            +2.0);  af("glòria",        +1.8);
        af("alegria",        +1.8);  af("esperança",     +1.8);
        af("feliç",          +1.8);  af("felicitat",     +1.8);
        af("meravellós",     +1.8);  af("meravellosa",   +1.8);
        af("magnífic",       +1.8);  af("magnífica",     +1.8);
        af("bell",           +1.5);  af("bella",         +1.5);
        af("bells",          +1.5);  af("belles",        +1.5);
        af("llum",           +1.5);  af("tendresa",      +1.5);
        af("esplèndid",      +1.5);  af("esplèndida",    +1.5);
        af("harmonia",       +1.5);  af("eternitat",     +1.5);

        // ── Positiu (+1.2 a +0.5) ─────────────────────────────────────────
        af("vida",           +1.2);  af("sol",           +1.2);
        af("primavera",      +1.2);  af("aurora",        +1.2);
        af("alba",           +1.2);  af("cançó",         +1.2);
        af("flor",           +1.2);  af("flors",         +1.2);
        af("victòria",       +1.2);  af("triomf",        +1.2);
        af("abraça",         +1.2);  af("estima",        +1.2);
        af("somni",          +1.0);  af("dolç",          +1.0);
        af("dolça",          +1.0);  af("suau",          +1.0);
        af("rialles",        +1.0);  af("riure",         +1.0);
        af("music",          +1.0);  af("música",        +1.0);
        af("canta",          +0.8);  af("canten",        +0.8);
        af("cor",            +0.8);  af("mare",          +0.8);
        af("amic",           +0.8);  af("amiga",         +0.8);
        af("calma",          +0.8);  af("serè",          +0.8);
        af("serena",         +0.8);  af("forta",         +0.8);
        af("fort",           +0.8);  af("noble",         +0.8);
        af("just",           +0.8);  af("justa",         +0.8);
        af("bo",             +0.8);  af("bona",          +0.8);
        af("tranquil",       +0.8);  af("estimat",       +0.8);
        af("estimada",       +0.8);  af("gentil",        +0.8);
        af("color",          +0.6);  af("clar",          +0.6);
        af("clara",          +0.6);  af("infant",        +0.8);
        af("torna",          +0.5);  af("retorna",       +0.5);
        af("floreix",        +0.8);

        // ── Lleugerament positiu (+0.4 a +0.1) ───────────────────────────
        af("terra",          +0.3);  af("silenci",       +0.3);
        af("arbre",          +0.3);  af("camí",          +0.3);
        af("mar",            +0.3);  af("cel",           +0.4);
        af("muntanya",       +0.3);  af("verd",          +0.4);

        // ── Lleugerament negatiu (−0.1 a −0.5) ───────────────────────────
        af("fred",           -0.4);  af("freda",         -0.4);
        af("gris",           -0.4);  af("buit",          -0.5);
        af("buida",          -0.5);  af("tardor",        -0.3);
        af("hivern",         -0.5);  af("lluny",         -0.4);
        af("vell",           -0.3);  af("vella",         -0.3);
        af("dur",            -0.4);  af("dura",          -0.4);
        af("estrany",        -0.3);  af("estranya",      -0.3);

        // ── Negatiu (−0.6 a −1.2) ────────────────────────────────────────
        af("tristesa",       -1.0);  af("tristor",       -1.0);
        af("trist",          -1.0);  af("trista",        -1.0);
        af("plor",           -0.8);  af("plora",         -0.8);
        af("llàgrima",       -0.8);  af("llàgrimes",     -0.8);
        af("soledat",        -1.0);  af("solitud",       -1.0);
        af("dolor",          -1.2);  af("foscor",        -0.8);
        af("fosca",          -0.8);  af("fosc",          -0.8);
        af("ombra",          -0.6);  af("ombres",        -0.6);
        af("oblit",          -0.8);  af("oblida",        -0.8);
        af("pena",           -1.0);  af("penes",         -1.0);
        af("patiment",       -1.2);  af("malaltia",      -1.0);
        af("cansament",      -0.6);  af("cansat",        -0.6);
        af("perdut",         -0.8);  af("perduda",       -0.8);
        af("por",            -1.0);  af("sang",          -0.8);
        af("espina",         -0.6);  af("espines",       -0.8);
        af("ferida",         -0.8);  af("ferit",         -0.8);
        af("fam",            -0.8);  af("presó",         -1.0);
        af("cadenes",        -0.8);  af("malson",        -1.0);
        af("cremen",         -0.8);  af("assolen",       -1.0);
        af("condemnat",      -1.0);  af("llàstima",      -0.8);
        af("trencada",       -0.8);  af("trencat",       -0.8);

        // ── Molt negatiu (−1.3 a −2.0) ───────────────────────────────────
        af("mort",           -2.0);  af("guerra",        -2.0);
        af("odi",            -2.0);  af("angoixa",       -1.8);
        af("desesperació",   -1.8);  af("desolació",     -1.8);
        af("agonia",         -1.5);  af("turment",       -1.5);
        af("horror",         -1.8);  af("terror",        -1.8);
        af("cruel",          -1.5);  af("crueltat",      -1.8);
        af("destrucció",     -1.8);  af("ruïna",         -1.5);
        af("tragèdia",       -1.5);  af("infern",        -1.5);
        af("maleït",         -1.5);  af("maleïda",       -1.5);
        af("traïció",        -1.5);  af("damnació",      -1.5);
        af("verí",           -1.2);
    }

    private void construeixNegadors() {
        // Paraules que inverteixen la polaritat del token lexical proper
        negadors.addAll(Arrays.asList(
            "no", "mai", "tampoc", "ni", "sense",
            "cap", "ningú", "res", "gens", "jamais"
        ));
    }

    private void construeixModificadors() {
        // Intensificadors (factor > 1.0)
        modificadors.put("molt",           1.5);
        modificadors.put("molta",          1.5);
        modificadors.put("molts",          1.5);
        modificadors.put("moltes",         1.5);
        modificadors.put("força",          1.4);
        modificadors.put("bastant",        1.3);
        modificadors.put("prou",           1.2);
        modificadors.put("tan",            1.3);
        modificadors.put("tant",           1.3);
        modificadors.put("massa",          1.4);
        modificadors.put("extremadament",  1.8);
        modificadors.put("absolutament",   1.8);
        modificadors.put("totalment",      1.5);
        modificadors.put("profundament",   1.5);

        // Diminuïdors (factor < 1.0)
        modificadors.put("poc",            0.5);
        modificadors.put("poca",           0.5);
        modificadors.put("quasi",          0.6);
        modificadors.put("gairebé",        0.6);
        modificadors.put("lleugerament",   0.4);
    }

    public static String[] tokenitza(String linia) {
        List<String> tokens = new ArrayList<>();
        for (String part : linia.trim().split("\\s+")) {
            if (part.isEmpty()) continue;
            int ap = -1;
            for (int k = 1; k < part.length() - 1; k++) {
                char c = part.charAt(k);
                if (c == '\'' || c == '\u2019') { ap = k; break; }
            }
            if (ap > 0) {
                tokens.add(part.substring(0, ap + 1)); // "d'"
                tokens.add(part.substring(ap + 1));    // "amor"
            } else {
                tokens.add(part);
            }
        }
        return tokens.toArray(new String[0]);
    }

    public VersAnalitzat analitzaVers(String text, int numero) {
        String[] parts = tokenitza(text);
        List<TokenAnalitzat> resultat = new ArrayList<>();

        int    finestraNegacio = 0;   // tokens que resten de negació activa
        double factorMod       = 1.0; // factor pendent (intensificador/diminuïdor)

        for (String part : parts) {
            if (part.isEmpty()) continue;
            String forma = normalitza(part);
            if (forma.isEmpty()) continue;

            boolean esNegador      = negadors.contains(forma);
            boolean esModificador  = modificadors.containsKey(forma);
            boolean alLexic        = lexic.containsKey(forma);

            if (esNegador) {
                // ── Negador ─────────────────────────────────────────────
                finestraNegacio = FINESTRA_NEGACIO;
                resultat.add(new TokenAnalitzat(part, forma, 0.0, 1.0, Rol.NEGADOR));

            } else if (esModificador) {
                // ── Intensificador o Diminuïdor ─────────────────────────
                factorMod = modificadors.get(forma);
                Rol rol   = (factorMod > 1.0) ? Rol.INTENSIFICADOR : Rol.DIMINUIDOR;
                resultat.add(new TokenAnalitzat(part, forma, 0.0, factorMod, rol));

            } else if (alLexic) {
                // ── Token lexical amb puntuació ──────────────────────────
                double base      = lexic.get(forma);
                double signe     = (finestraNegacio > 0) ? -1.0 : 1.0;
                double factorFin = signe * factorMod;

                resultat.add(new TokenAnalitzat(part, forma, base, factorFin, Rol.LEXIC));

                factorMod = 1.0;                         // consumeix el modificador pendent
                if (finestraNegacio > 0) finestraNegacio--;

            } else {
                // ── Token neutre (no al lexicó) ──────────────────────────
                if (finestraNegacio > 0) finestraNegacio--;
                // El factorMod NO es reset: persisteix fins al proper token lexical
                resultat.add(new TokenAnalitzat(part, forma, 0.0, 1.0, Rol.NEUTRE));
            }
        }

        return new VersAnalitzat(numero, text, resultat);
    }

     public List<List<VersAnalitzat>> analitzaPoema(String text) {
        List<List<VersAnalitzat>> estrofes    = new ArrayList<>();
        List<VersAnalitzat>       estrofaActual = new ArrayList<>();
        int numVers = 0;

        for (String linia : text.split("\n")) {
            if (linia.isBlank()) {
                if (!estrofaActual.isEmpty()) {
                    estrofes.add(new ArrayList<>(estrofaActual));
                    estrofaActual.clear();
                }
            } else {
                estrofaActual.add(analitzaVers(linia, ++numVers));
            }
        }
        if (!estrofaActual.isEmpty()) estrofes.add(estrofaActual);
        return estrofes;
    }

}
