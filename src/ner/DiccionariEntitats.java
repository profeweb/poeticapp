package ner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DiccionariEntitats {

    /**
     * Diccionari principal.
     * Clau:  expressió original (amb la capitalització correcta), p. ex. "Joan Maragall"
     * Valor: tipus d'entitat
     */

    private static final String CAMP_ARREL = "entitats";
    private static final String CAMP_TEXT  = "text";
    private static final String CAMP_TIPUS = "tipus";

    private final Map<String, EntitatNomenada.TipusEntitat> diccionari;

    /**
     * Llista d'entrades ordenada per longitud descendent per a la cerca greedy.
     * Les expressions més llargues s'intenten primer (longest-match-first).
     */
    private List<EntradaDiccionari> entrades;

    public DiccionariEntitats() {
        diccionari = new LinkedHashMap<>();
        construeixDiccionari();
        construeixEntrades();
    }

    public DiccionariEntitats(String rutaJSON) {
        diccionari = new LinkedHashMap<>();
        carregaDesDeJSON(this, rutaJSON);
        construeixEntrades();
    }

    public List<EntradaDiccionari> getEntrades(){ return  this.entrades; }

    //  CONSTRUCCIÓ DEL DICCIONARI

    /** Afegeix (o sobreescriu) una expressió al diccionari. */
    private void afegeixEntitat(String expressio, EntitatNomenada.TipusEntitat tipus) {
        diccionari.put(expressio, tipus);   // clau = expressió original (respecta la capitalització)
    }

    private void afegeixEntitatPER(String expressio) {
        afegeixEntitat(expressio, EntitatNomenada.TipusEntitat.PER);
    }

    private void afegeixEntitatLOC(String expressio) {
        afegeixEntitat(expressio, EntitatNomenada.TipusEntitat.LOC);
    }

    private void afegeixEntitatORG(String expressio) {
        afegeixEntitat(expressio, EntitatNomenada.TipusEntitat.ORG);
    }

    private void afegeixEntitatMISC(String expressio) {
        afegeixEntitat(expressio, EntitatNomenada.TipusEntitat.MISC);
    }

    /** Construeix la llista d'entrades ordenada per a la cerca greedy. */
    private void construeixEntrades() {

        entrades = new ArrayList<>();

        for (Map.Entry<String, EntitatNomenada.TipusEntitat> e : diccionari.entrySet()) {
            entrades.add(new EntradaDiccionari(e.getKey(), e.getValue()));
        }

        // Ordena per longitud descendent → longest-match-first
        entrades.sort((a, b) -> Integer.compare(b.longitud(), a.longitud()));
    }


    /** Construeix el diccionari complet d'entitats predefinides. */
    private void construeixDiccionari() {

        // PER: Persones ─────────────────────────────────────────────────

        // Noms de persona (expressions compostes primer)
        afegeixEntitatPER("Miquel Àngel Riera");
        afegeixEntitatPER("Miquel Àngel");
        afegeixEntitatPER("Joan Miró");
        afegeixEntitatPER("Joan Pau Picasso XIII");
        afegeixEntitatPER("Rafael Alberti");
        afegeixEntitatPER("Pablo Neruda");
        afegeixEntitatPER("Maria Teresa");
        afegeixEntitatPER("Maria del Mar");
        afegeixEntitatPER("Pau Casals");
        afegeixEntitatPER("Wolfgang Amadeus");
        afegeixEntitatPER("Antoni Vivaldi");
        afegeixEntitatPER("Anna Magdalena Bach");
        afegeixEntitatPER("Juan Ruiz");
        afegeixEntitatPER("Jan Steen");
        afegeixEntitatPER("Ramon Casas");
        afegeixEntitatPER("Albert Schweitzer");
        afegeixEntitatPER("George Lance");
        afegeixEntitatPER("la tia Clara");
        afegeixEntitatPER("Comte Olinos");

        // Noms de persona simples (un sol token; menys prioritat que els composts)
        afegeixEntitatPER("Nai");
        afegeixEntitatPER("Roser");
        afegeixEntitatPER("Miquel");
        afegeixEntitatPER("miquelangelejar");
        afegeixEntitatPER("emmiquelada");
        afegeixEntitatPER("Apol·lònia");
        afegeixEntitatPER("Rafael");
        afegeixEntitatPER("Gianna");
        afegeixEntitatPER("Panda");
        afegeixEntitatPER("Tomassi");
        afegeixEntitatPER("Quatrucci");
        afegeixEntitatPER("Maïa");
        afegeixEntitatPER("Duca");
        afegeixEntitatPER("Tàpies");
        afegeixEntitatPER("Miró");
        afegeixEntitatPER("Modigliani");
        afegeixEntitatPER("Botticelli");
        afegeixEntitatPER("Buonarroti");
        afegeixEntitatPER("Mengs");
        afegeixEntitatPER("Meyfren");
        afegeixEntitatPER("Clavé");
        afegeixEntitatPER("Vayreda");


        // LOC: Llocs geogràfics ─────────────────────────────────────────

        // Expressions compostes (han d'anar al mapa abans que les simples)
        afegeixEntitatLOC("Jardí Botànic");
        afegeixEntitatLOC("Sant Feliu de Rocabruna");
        afegeixEntitatLOC("Sant Joan de les Abadesses");
        afegeixEntitatLOC("Sa Coma");
        afegeixEntitatLOC("Cala Virgili");
        afegeixEntitatLOC("Tancat de la Torre");
        afegeixEntitatLOC("Mar de Solís");
        afegeixEntitatLOC("Río de la Plata");
        afegeixEntitatLOC("Carrer d'Artà");
        afegeixEntitatLOC("Pis de la badia");

        // Noms simples
        afegeixEntitatLOC("Miamar");
        afegeixEntitatLOC("Tortova");
        afegeixEntitatLOC("Justaní");
        afegeixEntitatLOC("Roma");
        afegeixEntitatLOC("Urbino");
        afegeixEntitatLOC("Molló");
        afegeixEntitatLOC("Beget");
        afegeixEntitatLOC("Porqueres");
        afegeixEntitatLOC("Besalú");
        afegeixEntitatLOC("Cadis");
        afegeixEntitatLOC("Atlàntic");
        afegeixEntitatLOC("Paranà");
        afegeixEntitatLOC("Espanya");


        // ORG: Organitzacions ───────────────────────────────────────────

        // Noms simples
        afegeixEntitatORG("Veritat");
        afegeixEntitatORG("Dolor");
        afegeixEntitatORG("Equilibri");
        afegeixEntitatORG("Persona-Trista");

        // MISC: Miscel·lani ─────────────────────────────────────────────
        afegeixEntitatMISC("Sant Joan Sebastià");
        afegeixEntitatMISC("Pentecostès");
        afegeixEntitatMISC("Gènesi");
        afegeixEntitatMISC("Fariseus");
        afegeixEntitatMISC("Florantins");
        afegeixEntitatMISC("Déu");
        afegeixEntitatMISC("Déus");
        afegeixEntitatMISC("Crist");

    }



    public static void carregaDesDeJSON(DiccionariEntitats diccionari,String rutaFitxer) {

        Path path = Paths.get(rutaFitxer);

        try {
            // Llegim tot el fitxer com a String UTF-8
            String contingutJSON = Files.readString(path, StandardCharsets.UTF_8);

            // Processa el fitxer JSON i afegeix
            processaJSON(diccionari, contingutJSON);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void processaJSON(DiccionariEntitats diccionari,String contingut) {

        // 1. Construeix l'objecte arrel
        JSONObject arrel = new JSONObject(contingut);

        // 2. Obté l'array d'entitats
        JSONArray arrayEntitats = arrel.getJSONArray(CAMP_ARREL);

        int comptador = 0;

        // 3. Itera cada entrada de l'array
        for (int i = 0; i < arrayEntitats.length(); i++) {

            JSONObject entrada = arrayEntitats.getJSONObject(i);

            // 4. Llegeix els camps "text" i "tipus"
            String text = entrada.getString(CAMP_TEXT).trim();
            String tipusStr = entrada.getString(CAMP_TIPUS).trim().toUpperCase();

            // 5. Afegeix al diccionari segons el tipus
            switch (tipusStr) {
                case "PER": diccionari.afegeixEntitatPER(text); break;
                case "LOC": diccionari.afegeixEntitatLOC(text); break;
                case "ORG": diccionari.afegeixEntitatORG(text); break;
                case "MISC": diccionari.afegeixEntitatMISC(text); break;
            }
            comptador++;
        }

        // 6. Reconstrueix la llista ordenada per longest-match-first
        diccionari.construeixEntrades();

        System.out.printf("Diccionari actualitzat: %d entitat(s) carregada(s) des del JSON.%n", comptador);
    }


    public void imprimeixEntradesDiccionari(){
        System.out.println("[ DICCIONARI D'ENTITATS ]");

        Map<String, List<String>> entitatsPerTipus = new LinkedHashMap<>();
        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) entitatsPerTipus.put(te.name(), new ArrayList<>());
        for (Map.Entry<String, EntitatNomenada.TipusEntitat> e : diccionari.entrySet())
            entitatsPerTipus.get(e.getValue().name()).add(e.getKey());

        for (EntitatNomenada.TipusEntitat te : EntitatNomenada.TipusEntitat.values()) {
            List<String> llista = entitatsPerTipus.get(te.name());
            System.out.printf("  %-5s %-18s (%d entrades):%n",
                te.name(), "(" + te.getDescripcio() + ")", llista.size());
            // Mostra fins a 6 exemples per línia
            for (int k = 0; k < llista.size(); k += 6) {
                List<String> chunk = llista.subList(k, Math.min(k + 6, llista.size()));
                System.out.println("    " + String.join(" · ", chunk));
            }
        }
        System.out.println();
    }

}
