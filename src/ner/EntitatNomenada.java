package ner;

public class EntitatNomenada {


    public enum TipusEntitat {
        PER  ("Persona"),
        LOC  ("Lloc geogràfic"),
        ORG  ("Organització"),
        MISC ("Miscel·lani");

        private final String descripcio;
        TipusEntitat(String d) { this.descripcio = d; }
        public String getDescripcio() { return descripcio; }
    }
}
