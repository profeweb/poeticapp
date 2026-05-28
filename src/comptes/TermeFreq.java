package comptes;

public class TermeFreq implements Comparable {

    String terme;       // Text del terme
    float frequencia;   // Núm. ocurrències

    // Constructor
    public TermeFreq(String terme, float frequencia) {
        this.terme = terme;
        this.frequencia = frequencia;
    }

    // Retorna el text del terme
    public String getTerme() {
        return terme;
    }

    // Retorna el número d'ocurrències del terme
    public float getFrequencia() {
        return frequencia;
    }

    @Override
    public String toString() {
        return terme + '(' + frequencia + ')';
    }

    @Override
    public int compareTo(Object altre) {
        float freqAltre = ((TermeFreq)altre).getFrequencia();
        return (int) (this.getFrequencia() - freqAltre);
    }
}
