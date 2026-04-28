package parser;

public class Paraula extends Token{

    int numSilabes;
    boolean majúscula;

    public Paraula(String valor) {
        super(Tipus.PARAULA, valor);
        this.majúscula = valor.equals(valor.toUpperCase());
    }

    public int getNumLletres(){ return this.valor.length(); }

    public int getNumSilabes(){ return this.numSilabes; }
}
