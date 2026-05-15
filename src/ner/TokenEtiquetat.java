package ner;

public class TokenEtiquetat {

    private final String original;  // forma original al text (amb puntuació)
    private final String forma;     // forma normalitzada (minúscules, sense punct.)
    private final String etiqueta;  // "O" | "B-PER" | "I-LOC" | ...

    public TokenEtiquetat(String original, String forma, String etiqueta) {
        this.original = original;
        this.forma    = forma;
        this.etiqueta = etiqueta;
    }

    public String getOriginal(){
        return original;
    }

    public String getForma(){
        return forma;
    }

    public String getEtiqueta(){
        return etiqueta;
    }

    /** Retorna cert si el token forma part d'una entitat (B o I). */
    public boolean esEntitat(){
        return !etiqueta.equals("O");
    }

    /** Retorna cert si és el token inicial d'una entitat (B). */
    public boolean esInici(){
        return etiqueta.startsWith("B-");
    }

    /** Retorna el tipus d'entitat o null si és O. */
    public String getTipus(){
        return esEntitat() ? etiqueta.substring(2) : null;
    }

    public String toString() {
        return String.format("%-24s %s", original, etiqueta);
    }
}
