package parser;

import java.util.ArrayList;

public class Vers {

    int numVers;
    String text;
    ArrayList<Token> tokens;

    public Vers(int nv, String text){
        this.numVers = nv;
        this.text = text;
        this.tokens = new ArrayList<>();
    }

    public int getNumTokensTipus(Token.Tipus tipus){
        int num = 0;
        for(Token token: tokens){
            if(token.tipus == tipus){
                num++;
            }
        }
        return num;
    }

    public int getNumParaules(){ return getNumTokensTipus(Token.Tipus.PARAULA); }

    public int getNumLletres(){
        int num = 0;
        for(Token paraula : getParaules()){
            num += ((Paraula)paraula).getNumLletres();
        }
        return num;
    }

    public int getNumCaracters(){
        int num = 0;
        for(Token token: tokens){
            if(token instanceof Paraula){
                num += ((Paraula)token).getNumLletres();
            }
            else {
                num++;
            }
        }
        return num;
    }

    public int getNumSimbols(){ return getNumTokensTipus(Token.Tipus.SEPARADOR); }

    public ArrayList<Token> getTokensTipus(Token.Tipus tipus){
        ArrayList<Token> tipusTokens = new ArrayList<>();
        for(Token token : tokens){
            if(token.tipus == tipus){
                tipusTokens.add(token);
            }
        }
        return tipusTokens;
    }

    public ArrayList<Token> getParaules(){
        return getTokensTipus(Token.Tipus.PARAULA);
    }

    public ArrayList<Token> getSimbols() {
        return getTokensTipus(Token.Tipus.SEPARADOR);
    }

    public void printVers(){
        System.out.println("\nVers #"+ numVers + " ("+tokens.size()+ " tokens): "+ text);
        for(Token token : tokens){
            token.printToken();
        }
        System.out.println();
    }

}
