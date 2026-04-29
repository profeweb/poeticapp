package parser;

public class Token {

    public enum Tipus {PARAULA, SEPARADOR};
    Tipus tipus;
    String valor;

    public Token(Tipus tipus, String valor){
        this.tipus = tipus;
        this.valor = valor;
    }

    public String getValor(){ return this.valor; }

    public Tipus getTipus(){ return this.tipus; }

    public void printToken(){
        System.out.print("Token: " + valor + "(" + tipus + "), \t");
    }
}
