package parser;

public class Token {

    public enum Tipus {PARAULA, SEPARADOR};
    Tipus tipus;
    String valor;


    public Token(Tipus tipus, String valor){
        this.tipus = tipus;
        this.valor = valor;
    }
}
