package sentiments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DiccionariSentiments {

    // Noms de claus del JSON del diccionari de sentiments
    private static final String CLAU_LEXIC        = "lexic";
    private static final String CLAU_NEGADORS     = "negadors";
    private static final String CLAU_MODIFICADORS = "modificadors";
    private static final String CLAU_PARAULA       = "paraula";
    private static final String CLAU_PUNTUACIO     = "puntuacio";
    private static final String CLAU_FACTOR        = "factor";
    private static final String CLAU_ROL           = "rol";

    // Conjunt de termes del diccionari: forma normalitzada i puntuació de polaritat
    public Map<String, Double> lexic;

    // Negadors: paraules que inverteixen la polaritat dels tokens propers
    public Set<String> negadors;

    // Modificadors (intensificadors i diminuïdors)
    public Map<String, Double>  modificadors;

    // Constructor
    public DiccionariSentiments(){
        construeixLexic();
        construeixNegadors();
        construeixModificadors();
    }

    // Constructor del diccionari a partir del JSON
    public DiccionariSentiments(String rutaJSON){

        Path path = Paths.get(rutaJSON);

        try {
            // Llegim tot el fitxer com a String UTF-8
            String contingutJSON = Files.readString(path, StandardCharsets.UTF_8);

            // Processa el fitxer JSON
            processa(contingutJSON);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Normalitza: minúscules i suprimeix puntuació als extrems
    public static String normalitza(String s) {
        return s.toLowerCase()
                .replaceAll("^[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+", "")
                .replaceAll("[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+$",  "")
                .trim();
    }

    // Afegeix una paraula i la seva puntuació de polaritat al diccionari
    private void afegeixTerme(String paraula, double puntuacio) {
        lexic.put(normalitza(paraula), puntuacio);
    }

    private void construeixLexic() {

        lexic = new LinkedHashMap<>();

        // ── Molt positiu (+2.0 a +1.5) ───────────────────────────────────
        afegeixTerme("amor",           +2.0);  afegeixTerme("estima",        +2.0);
        afegeixTerme("joia",           +2.0);  afegeixTerme("llibertat",     +2.0);
        afegeixTerme("pau",            +2.0);  afegeixTerme("glòria",        +1.8);
        afegeixTerme("alegria",        +1.8);  afegeixTerme("esperança",     +1.8);
        afegeixTerme("feliç",          +1.8);  afegeixTerme("felicitat",     +1.8);
        afegeixTerme("meravellós",     +1.8);  afegeixTerme("meravellosa",   +1.8);
        afegeixTerme("magnífic",       +1.8);  afegeixTerme("magnífica",     +1.8);
        afegeixTerme("bell",           +1.5);  afegeixTerme("bella",         +1.5);
        afegeixTerme("bells",          +1.5);  afegeixTerme("belles",        +1.5);
        afegeixTerme("llum",           +1.5);  afegeixTerme("tendresa",      +1.5);
        afegeixTerme("esplèndid",      +1.5);  afegeixTerme("esplèndida",    +1.5);
        afegeixTerme("harmonia",       +1.5);  afegeixTerme("eternitat",     +1.5);

        // ── Positiu (+1.2 a +0.5) ─────────────────────────────────────────
        afegeixTerme("vida",           +1.2);  afegeixTerme("sol",           +1.2);
        afegeixTerme("primavera",      +1.2);  afegeixTerme("aurora",        +1.2);
        afegeixTerme("alba",           +1.2);  afegeixTerme("cançó",         +1.2);
        afegeixTerme("flor",           +1.2);  afegeixTerme("flors",         +1.2);
        afegeixTerme("victòria",       +1.2);  afegeixTerme("triomf",        +1.2);
        afegeixTerme("abraça",         +1.2);  afegeixTerme("estima",        +1.2);
        afegeixTerme("somni",          +1.0);  afegeixTerme("dolç",          +1.0);
        afegeixTerme("dolça",          +1.0);  afegeixTerme("suau",          +1.0);
        afegeixTerme("rialles",        +1.0);  afegeixTerme("riure",         +1.0);
        afegeixTerme("music",          +1.0);  afegeixTerme("música",        +1.0);
        afegeixTerme("canta",          +0.8);  afegeixTerme("canten",        +0.8);
        afegeixTerme("cor",            +0.8);  afegeixTerme("mare",          +0.8);
        afegeixTerme("amic",           +0.8);  afegeixTerme("amiga",         +0.8);
        afegeixTerme("calma",          +0.8);  afegeixTerme("serè",          +0.8);
        afegeixTerme("serena",         +0.8);  afegeixTerme("forta",         +0.8);
        afegeixTerme("fort",           +0.8);  afegeixTerme("noble",         +0.8);
        afegeixTerme("just",           +0.8);  afegeixTerme("justa",         +0.8);
        afegeixTerme("bo",             +0.8);  afegeixTerme("bona",          +0.8);
        afegeixTerme("tranquil",       +0.8);  afegeixTerme("estimat",       +0.8);
        afegeixTerme("estimada",       +0.8);  afegeixTerme("gentil",        +0.8);
        afegeixTerme("color",          +0.6);  afegeixTerme("clar",          +0.6);
        afegeixTerme("clara",          +0.6);  afegeixTerme("infant",        +0.8);
        afegeixTerme("torna",          +0.5);  afegeixTerme("retorna",       +0.5);
        afegeixTerme("floreix",        +0.8);

        // ── Lleugerament positiu (+0.4 a +0.1) ───────────────────────────
        afegeixTerme("terra",          +0.3);  afegeixTerme("silenci",       +0.3);
        afegeixTerme("arbre",          +0.3);  afegeixTerme("camí",          +0.3);
        afegeixTerme("mar",            +0.3);  afegeixTerme("cel",           +0.4);
        afegeixTerme("muntanya",       +0.3);  afegeixTerme("verd",          +0.4);

        // ── Lleugerament negatiu (−0.1 a −0.5) ───────────────────────────
        afegeixTerme("fred",           -0.4);  afegeixTerme("freda",         -0.4);
        afegeixTerme("gris",           -0.4);  afegeixTerme("buit",          -0.5);
        afegeixTerme("buida",          -0.5);  afegeixTerme("tardor",        -0.3);
        afegeixTerme("hivern",         -0.5);  afegeixTerme("lluny",         -0.4);
        afegeixTerme("vell",           -0.3);  afegeixTerme("vella",         -0.3);
        afegeixTerme("dur",            -0.4);  afegeixTerme("dura",          -0.4);
        afegeixTerme("estrany",        -0.3);  afegeixTerme("estranya",      -0.3);

        // ── Negatiu (−0.6 a −1.2) ────────────────────────────────────────
        afegeixTerme("tristesa",       -1.0);  afegeixTerme("tristor",       -1.0);
        afegeixTerme("trist",          -1.0);  afegeixTerme("trista",        -1.0);
        afegeixTerme("plor",           -0.8);  afegeixTerme("plora",         -0.8);
        afegeixTerme("llàgrima",       -0.8);  afegeixTerme("llàgrimes",     -0.8);
        afegeixTerme("soledat",        -1.0);  afegeixTerme("solitud",       -1.0);
        afegeixTerme("dolor",          -1.2);  afegeixTerme("foscor",        -0.8);
        afegeixTerme("fosca",          -0.8);  afegeixTerme("fosc",          -0.8);
        afegeixTerme("ombra",          -0.6);  afegeixTerme("ombres",        -0.6);
        afegeixTerme("oblit",          -0.8);  afegeixTerme("oblida",        -0.8);
        afegeixTerme("pena",           -1.0);  afegeixTerme("penes",         -1.0);
        afegeixTerme("patiment",       -1.2);  afegeixTerme("malaltia",      -1.0);
        afegeixTerme("cansament",      -0.6);  afegeixTerme("cansat",        -0.6);
        afegeixTerme("perdut",         -0.8);  afegeixTerme("perduda",       -0.8);
        afegeixTerme("por",            -1.0);  afegeixTerme("sang",          -0.8);
        afegeixTerme("espina",         -0.6);  afegeixTerme("espines",       -0.8);
        afegeixTerme("ferida",         -0.8);  afegeixTerme("ferit",         -0.8);
        afegeixTerme("fam",            -0.8);  afegeixTerme("presó",         -1.0);
        afegeixTerme("cadenes",        -0.8);  afegeixTerme("malson",        -1.0);
        afegeixTerme("cremen",         -0.8);  afegeixTerme("assolen",       -1.0);
        afegeixTerme("condemnat",      -1.0);  afegeixTerme("llàstima",      -0.8);
        afegeixTerme("trencada",       -0.8);  afegeixTerme("trencat",       -0.8);

        // ── Molt negatiu (−1.3 a −2.0) ───────────────────────────────────
        afegeixTerme("mort",           -2.0);  afegeixTerme("guerra",        -2.0);
        afegeixTerme("odi",            -2.0);  afegeixTerme("angoixa",       -1.8);
        afegeixTerme("desesperació",   -1.8);  afegeixTerme("desolació",     -1.8);
        afegeixTerme("agonia",         -1.5);  afegeixTerme("turment",       -1.5);
        afegeixTerme("horror",         -1.8);  afegeixTerme("terror",        -1.8);
        afegeixTerme("cruel",          -1.5);  afegeixTerme("crueltat",      -1.8);
        afegeixTerme("destrucció",     -1.8);  afegeixTerme("ruïna",         -1.5);
        afegeixTerme("tragèdia",       -1.5);  afegeixTerme("infern",        -1.5);
        afegeixTerme("maleït",         -1.5);  afegeixTerme("maleïda",       -1.5);
        afegeixTerme("traïció",        -1.5);  afegeixTerme("damnació",      -1.5);
        afegeixTerme("verí",           -1.2);
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


    // Construeix el diccionari a partir del JSON
    private void processa(String contingut) throws JSONException {

        JSONObject arrel = new JSONObject(contingut);

        int totalLexic      = carregaLexic(arrel);
        int totalNegadors   = carregaNegadors(arrel);
        int totalMods       = carregaModificadors(arrel);

        System.out.printf("Diccionari de sentiments carregat:%n" +
            "  Lexic:        %4d entrades%n" +
            "  Negadors:     %4d paraules%n" +
            "  Modificadors: %4d entrades%n",
            totalLexic, totalNegadors, totalMods);
    }

    private int carregaLexic(JSONObject arrel) throws JSONException {

        lexic = new LinkedHashMap<>();

        JSONArray array = arrel.getJSONArray(CLAU_LEXIC);
        int comptador = 0;

        for (int i = 0; i < array.length(); i++) {
            JSONObject entrada = array.getJSONObject(i);

            String paraula   = entrada.getString(CLAU_PARAULA).trim();
            double puntuacio = entrada.getDouble(CLAU_PUNTUACIO);

            // Normalitza la paraula abans d'inserir-la (igual que DiccionariSentiments.af())
            String clau = DiccionariSentiments.normalitza(paraula);
            lexic.put(clau, puntuacio);
            comptador++;
        }
        return comptador;
    }

    private int carregaNegadors(JSONObject arrel) throws JSONException {

        negadors = new LinkedHashSet<>();

        JSONArray array = arrel.getJSONArray(CLAU_NEGADORS);
        int comptador = 0;

        for (int i = 0; i < array.length(); i++) {
            String negador = array.getString(i).trim();
            if (!negador.isBlank()) {
                negadors.add(DiccionariSentiments.normalitza(negador));
                comptador++;
            }
        }
        return comptador;
    }

    private int carregaModificadors(JSONObject arrel) throws JSONException {

        modificadors = new LinkedHashMap<>();

        JSONArray array = arrel.getJSONArray(CLAU_MODIFICADORS);
        int comptador = 0;

        for (int i = 0; i < array.length(); i++) {
            JSONObject entrada = array.getJSONObject(i);

            String paraula = entrada.getString(CLAU_PARAULA).trim();
            double factor  = entrada.getDouble(CLAU_FACTOR);
            String rol = entrada.has(CLAU_ROL) ? entrada.getString(CLAU_ROL) : "DESCONEGUT";

            String clau = DiccionariSentiments.normalitza(paraula);
            modificadors.put(clau, factor);
            comptador++;
        }
        return comptador;
    }


    // Imprimeix el lèxic del diccionari de sentiments
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


    // Imprimeix el diccionari de sentiments
    public void imprimeix(){

        System.out.println("Diccionari de Sentiments: ");
        System.out.printf("Lèxic: %d paraules  |  Negadors: %d  |  Modificadors: %d%n", lexic.size(), negadors.size(), modificadors.size());

        System.out.println("Lèxic de Polaritats (subconjunt)");
        imprimeixMoltPositius(8);
        imprimeixPositius(8);
        imprimeixNegatius(8);
        imprimeixMoltNegatius(8);

        System.out.print("Negadors: ");
        negadors.forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("Modificadors: ");
        modificadors.forEach((k, v) -> System.out.printf("%s(x%.1f) ", k, v));
        System.out.println("\n");
    }

}
