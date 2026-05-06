package stats;

public class TermeFreq {

    String terme;
    float frequencia;

    public TermeFreq(String terme, float frequencia) {
        this.terme = terme;
        this.frequencia = frequencia;
    }

    public String getTerme() {
        return terme;
    }

    public float getFrequencia() {
        return frequencia;
    }

    @Override
    public String toString() {
        return terme + '(' + frequencia + ')';
    }
}
