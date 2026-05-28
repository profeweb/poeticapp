package ner;

public class EntitatNomenada {


    // Tipus d'entitats anomenades que inclou el diccionari
    public enum TipusEntitat {
        PER  ("Persona"),
        LOC  ("Lloc geogràfic"),
        ORG  ("Organització"),
        MISC ("Miscel·lani");

        // Descripció textual del tipus d'entitat
        private final String descripcio;

        // Constructor
        TipusEntitat(String d) {
            this.descripcio = d;
        }

        // Retorna descripció textual del tipus d'entitat
        public String getDescripcio() {
            return descripcio;
        }
    }
}
