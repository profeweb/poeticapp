package parser;

public class Simbol extends Token {

    // Constructor
    public Simbol(String valor) {
        super(Tipus.SEPARADOR, valor);
    }

    // Imprimeix el símbol
    public void printToken(){
        System.out.print("Separador: " + valor + "\t");
    }
}
