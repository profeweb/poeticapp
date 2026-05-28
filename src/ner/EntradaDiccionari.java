package ner;

import parser.Token;

public class EntradaDiccionari {

    // Tokens normalitzats de l'entrada
    final String[] tokens;

    // Tipus de l'entrada
    final EntitatNomenada.TipusEntitat tipus;

    // Majúscula (Noms propis)
    final boolean requereixMajuscula;

    // Constructor
    EntradaDiccionari(String expressioOriginal, EntitatNomenada.TipusEntitat tipus) {
            this.tokens = normalitza(expressioOriginal).split("\\s+");
            this.tipus  = tipus;
            this.requereixMajuscula = !expressioOriginal.isEmpty() && Character.isUpperCase(expressioOriginal.charAt(0));
    }

    // Retorna la llargada de l'entrada
    int longitud() { return tokens.length; }

    // Retorna el tipus de l'entitat
    public EntitatNomenada.TipusEntitat getTipus(){ return  this.tipus; }


    // Normalitza el text de l'entrada
    public static String normalitza(String s) {
        return s.toLowerCase()
                .replaceAll("^[.,;:!?¡¿\"«»()\\[\\]{}\\-–—/'·]+", "")
                .replaceAll("[.,;:!?¡¿\"«»()\\[\\]{}\\-–—/'·]+$",  "")
                .trim();
    }
}
