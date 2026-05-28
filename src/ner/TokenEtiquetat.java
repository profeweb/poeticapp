package ner;

public class TokenEtiquetat {

    private final String original;  // forma original al text (amb puntuació)
    private final String forma;     // forma normalitzada (minúscules)
    private final String etiqueta;  // "O" , "B-PER" , "I-LOC" , ...

    // Constructor
    public TokenEtiquetat(String original, String forma, String etiqueta) {
        this.original = original;
        this.forma    = forma;
        this.etiqueta = etiqueta;
    }

    // Retorna forma original del text
    public String getOriginal(){
        return original;
    }

    // Retorna forma normalitzada del text
    public String getForma(){
        return forma;
    }

    // Retorna l'etiqueta BIO
    public String getEtiqueta(){
        return etiqueta;
    }

    // Retorna cert si el token forma part d'una entitat (B o I)
    public boolean esEntitat(){
        return !etiqueta.equals("O");
    }

    // Retorna cert si és el token inicial d'una entitat (B)
    public boolean esInici(){
        return etiqueta.startsWith("B-");
    }

    // Retorna el tipus d'entitat o null si és O.
    public String getTipus(){
        return esEntitat() ? etiqueta.substring(2) : null;
    }

    // Retorna un text descriptiu del token etiquetat
    public String toString() {
        return String.format("%-24s %s", original, etiqueta);
    }
}
