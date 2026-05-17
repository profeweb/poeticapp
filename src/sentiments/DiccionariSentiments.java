package sentiments;

import ner.DiccionariEntitats;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.*;

public class DiccionariSentiments {

    /** Lexicó: forma normalitzada → puntuació de polaritat. */
    public Map<String, Double> lexic;

    /** Negadors: paraules que inverteixen la polaritat dels tokens propers. */
    public Set<String> negadors;

    /**
     * Modificadors: paraula → factor multiplicador.
     * > 1.0 = intensificador  |  < 1.0 = diminuïdor
     */
    public Map<String, Double>  modificadors;

    public DiccionariSentiments(){
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

        lexic = new LinkedHashMap<>();

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
        negadors     = new LinkedHashSet<>();
        // Paraules que inverteixen la polaritat del token lexical proper
        negadors.addAll(Arrays.asList(
                "no", "mai", "tampoc", "ni", "sense",
                "cap", "ningú", "res", "gens", "jamais"
        ));
    }

    private void construeixModificadors() {
        modificadors = new LinkedHashMap<>();
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

    /*
    private static void processaJSON(DiccionariEntitats diccionari,
                                     String contingut)
            throws JSONException {

        // 1. Construeix l'objecte arrel
        JSONObject arrel = new JSONObject();

        // 2. Obté l'array d'entitats
        if (!arrel.has(CAMP_ARREL)) {
            throw new JSONException(
                "El JSON no conté la clau arrel esperada: \"" + CAMP_ARREL + "\"");
        }
        JSONArray arrayEntitats = arrel.getJSONArray(CAMP_ARREL);

        int comptador = 0;

        // 3. Itera cada entrada de l'array
        for (int i = 0; i < arrayEntitats.length(); i++) {

            JSONObject entrada = arrayEntitats.getJSONObject(i);

            // 4a. Llegeix el camp "text"
            if (!entrada.has(CAMP_TEXT) || entrada.getString(CAMP_TEXT).isBlank()) {
                System.err.printf("Avís [entrada %d]: camp \"%s\" absent o buit, s'ignora.%n",
                        i, CAMP_TEXT);
                continue;
            }
            String text = entrada.getString(CAMP_TEXT).trim();

            // 4b. Llegeix el camp "tipus"
            if (!entrada.has(CAMP_TIPUS) || entrada.getString(CAMP_TIPUS).isBlank()) {
                System.err.printf("Avís [entrada %d, text=\"%s\"]: camp \"%s\" absent o buit," +
                        " s'ignora.%n", i, text, CAMP_TIPUS);
                continue;
            }
            String tipusStr = entrada.getString(CAMP_TIPUS).trim().toUpperCase();

            // 5. Afegeix al diccionari segons el tipus
            switch (tipusStr) {
                case "PER"  -> diccionari.afegeixEntitatPER(text);
                case "LOC"  -> diccionari.afegeixEntitatLOC(text);
                case "ORG"  -> diccionari.afegeixEntitatORG(text);
                case "MISC" -> diccionari.afegeixEntitatMISC(text);
                default -> System.err.printf(
                        "Avís [entrada %d, text=\"%s\"]: tipus desconegut \"%s\", s'ignora.%n",
                        i, text, tipusStr);
            }
            comptador++;
        }

        // 6. Reconstrueix la llista ordenada per longest-match-first
        diccionari.construeixEntrades();

        System.out.printf("Diccionari actualitzat: %d entitat(s) carregada(s) des del JSON.%n",
                comptador);
    }

     */

    public void imprimeixLexic(float minFactor, float maxFactor, String text, int numLexics){
        System.out.print(text +" ["+minFactor+", "+maxFactor+"]: ");
        lexic.entrySet().stream()
                .filter(e -> e.getValue() >= minFactor && e.getValue() <= maxFactor)
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(numLexics)
                .forEach(e -> System.out.printf("%s(%.1f) ", e.getKey(), e.getValue()));
        System.out.println();
    }

    public void imprimeixMoltPositius(int num){
        imprimeixLexic(1.5f, 5f, "Molt positiu", num);
    }

    public void imprimeixPositius(int num){
        imprimeixLexic(0.5f, 1.4f,"Positiu", num);
    }

    public void imprimeixNegatius(int num){
        imprimeixLexic(-0.5f, -1.5f,"Negatiu", num);
    }

    public void imprimeixMoltNegatius(int num){
        imprimeixLexic(-5f, -1.5f,"Molt negatiu", num);
    }


    public void imprimeix(){

         System.out.println("Diccionari de Sentiments");
        System.out.printf("  Lexic: %d paraules  |  Negadors: %d  |  Modificadors: %d%n", lexic.size(), negadors.size(), modificadors.size());

        System.out.println("[ Lèxic de Polaritats (mostra) ]");
        imprimeixMoltPositius(8);
        imprimeixPositius(8);
        imprimeixNegatius(8);
        imprimeixMoltNegatius(8);

        System.out.print("  Negadors: ");
        negadors.forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("  Modificadors: ");
        modificadors.forEach((k, v) -> System.out.printf("%s(x%.1f) ", k, v));
        System.out.println("\n");
    }

}
