package parser;

public class Token {

    // Tipus de tokens possibles
    public enum Tipus {PARAULA, SEPARADOR};

    Tipus tipus;    // Tipus de token
    String valor;   // Text del token

    // Constructor
    public Token(Tipus tipus, String valor){
        this.tipus = tipus;
        this.valor = valor;
    }

    // Getters

    public String getValor(){ return this.valor; }

    public Tipus getTipus(){ return this.tipus; }

    // Imprimeix el token
    public void printToken(){
        System.out.print("Token: " + valor + "(" + tipus + "), \t");
    }
}
