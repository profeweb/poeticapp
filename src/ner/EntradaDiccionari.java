package ner;

import parser.Token;

public class EntradaDiccionari {

    /** Tokens normalitzats de l'expressió (p. ex. ["joan", "maragall"]). */
    final String[]     tokens;
    final EntitatNomenada.TipusEntitat tipus;

    /**
         * Si és cert, el primer token al text ha de començar amb majúscula
         * per tal que l'entrada es consideri una coincidència vàlida.
         * Això evita falsos positius com etiquetar "rosa" (flor) com B-PER.
     */
    final boolean requereixMajuscula;

    EntradaDiccionari(String expressioOriginal, EntitatNomenada.TipusEntitat tipus) {
            this.tokens              = normalitza(expressioOriginal).split("\\s+");
            this.tipus               = tipus;
            this.requereixMajuscula = !expressioOriginal.isEmpty()
                                       && Character.isUpperCase(expressioOriginal.charAt(0));
    }

    int longitud() { return tokens.length; }

    public EntitatNomenada.TipusEntitat getTipus(){ return  this.tipus; }


    public static String normalitza(String s) {
        return s.toLowerCase()
                .replaceAll("^[.,;:!?¡¿\"«»()\\[\\]{}\\-–—/'·]+", "")
                .replaceAll("[.,;:!?¡¿\"«»()\\[\\]{}\\-–—/'·]+$",  "")
                .trim();
    }
}
