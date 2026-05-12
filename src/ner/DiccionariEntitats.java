package ner;


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

    public List<EntradaDiccionari> getEntrades(){ return  this.entrades; }

    //  CONSTRUCCIÓ DEL DICCIONARI

    /** Afegeix (o sobreescriu) una expressió al diccionari. */
    private void afegeix(String expressio, EntitatNomenada.TipusEntitat tipus) {
        diccionari.put(expressio, tipus);   // clau = expressió original (respecta la capitalització)
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
        afegeix("Miquel Àngel Riera",        EntitatNomenada.TipusEntitat.PER);
        afegeix("Joan Miró",        EntitatNomenada.TipusEntitat.PER);
        afegeix("Rafael Alberti",   EntitatNomenada.TipusEntitat.PER);
        afegeix("Maria Teresa",     EntitatNomenada.TipusEntitat.PER);
        afegeix("Maria del Mar",     EntitatNomenada.TipusEntitat.PER);
        afegeix("Pau Casals",     EntitatNomenada.TipusEntitat.PER);

        // Noms de persona simples (un sol token; menys prioritat que els composts)
        afegeix("Nai",                 EntitatNomenada.TipusEntitat.PER);
        afegeix("Roser",                 EntitatNomenada.TipusEntitat.PER);
        afegeix("Miquel",                 EntitatNomenada.TipusEntitat.PER);
        afegeix("Apol·lònia",                 EntitatNomenada.TipusEntitat.PER);
        afegeix("Rafael",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Gianna",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Panda",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Tomassi",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Quatrucci",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Maïa",              EntitatNomenada.TipusEntitat.PER);
        afegeix("Duca",              EntitatNomenada.TipusEntitat.PER);


        // LOC: Llocs geogràfics ─────────────────────────────────────────

        // Expressions compostes (han d'anar al mapa abans que les simples)
        afegeix("Jardí Botànic",         EntitatNomenada.TipusEntitat.LOC);
        afegeix("Sant Feliu de Rocabruna",         EntitatNomenada.TipusEntitat.LOC);

        // Noms simples
        afegeix("Miamar",             EntitatNomenada.TipusEntitat.LOC);
        afegeix("Tortova",             EntitatNomenada.TipusEntitat.LOC);
        afegeix("Roma",             EntitatNomenada.TipusEntitat.LOC);
        afegeix("Molló",             EntitatNomenada.TipusEntitat.LOC);
        afegeix("Beget",             EntitatNomenada.TipusEntitat.LOC);


        // ORG: Organitzacions ───────────────────────────────────────────

        // Expressions compostes
        afegeix("Palau de la Música",    EntitatNomenada.TipusEntitat.ORG);

        // Noms simples
        afegeix("Generalitat",           EntitatNomenada.TipusEntitat.ORG);

        // MISC: Miscel·lani ─────────────────────────────────────────────

        // Obres literàries destacades
        afegeix("Cants Espirituals",     EntitatNomenada.TipusEntitat.MISC);

        // Festivitats i commemoracions
        afegeix("Diada Nacional",        EntitatNomenada.TipusEntitat.MISC);

        // Éssers llegendaris i religiosos
        afegeix("Drac",                  EntitatNomenada.TipusEntitat.MISC);
        afegeix("Déu",                   EntitatNomenada.TipusEntitat.MISC);
        afegeix("Crist",                 EntitatNomenada.TipusEntitat.MISC);
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
