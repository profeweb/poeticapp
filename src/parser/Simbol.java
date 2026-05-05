package parser;

public class Simbol extends Token {

    public Simbol(String valor) {
        super(Tipus.SEPARADOR, valor);
    }

    public void printToken(){
        System.out.print("Separador: " + valor + "\t");
    }
}
