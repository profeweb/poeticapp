package parser;

public class Paraula extends Token{

    int numSilabes;     // Número de sil·labes de la paraula
    boolean majúscula;  // En majúscules

    // Constructor
    public Paraula(String valor) {
        super(Tipus.PARAULA, valor);
        this.majúscula = valor.equals(valor.toUpperCase());
    }

    // Getters
    public int getNumLletres(){ return this.valor.length(); }

    public int getNumSilabes(){ return this.numSilabes; }

    // Setters
    public void setNumSilabes(int numSilabes){ this.numSilabes = numSilabes; }

    public void printToken(){
        System.out.print("Paraula: " + valor + " \t");
    }
}
