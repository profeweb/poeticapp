package parser;

import processing.core.PApplet;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Vers {

    int numVers;
    String text;
    ArrayList<Token> tokens;

    public Vers(int nv, String text){
        this.numVers = nv;
        this.text = text;
        this.tokens = new ArrayList<>();
    }

    public int getNumVers(){ return this.numVers; }

    public String getText(){ return this.text; }

    public ArrayList<Token> getTokens(){ return this.tokens; }

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

    public void dibuixaVersLinea(PApplet p5, float x, float y, float gruixa, float wLinia, float w, int color, int quantitat){
        p5.pushStyle();

            // Línia
            p5.stroke(color);
            p5.strokeWeight(gruixa);
            p5.strokeCap(p5.ROUND);
            p5.line(x, y, x + wLinia, y);

            // Núm. vers
            if(numVers%5==0) {
                p5.fill(0);
                p5.textSize(12);
                p5.textAlign(p5.RIGHT, p5.CENTER);
                p5.text(numVers, x - 5, y);
            }

            // Quantitat
            p5.fill(color);
            p5.textSize(12); p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(quantitat, x + w - 30, y);
        p5.popStyle();
    }

    public void dibuixaVersRadial(PApplet p5, float x, float  y, float radiShift, float angle, float radiQuant, float gruixa, int color, int quantitat){
        p5.pushStyle();

            // Línia
            p5.stroke(color);
            p5.strokeWeight(gruixa);
            p5.strokeCap(p5.ROUND);
            p5.line(x +  radiShift*cos(angle), y +  radiShift*sin(angle), x +  (radiShift + radiQuant)*cos(angle), y +  (radiShift + radiQuant)*sin(angle));

            // Núm. vers
            if(numVers%5==0) {
                p5.fill(0);
                p5.textSize(12);
                p5.textAlign(p5.CENTER, p5.CENTER);
                p5.text(numVers, x +  (radiShift - 15)*cos(angle), y +  (radiShift -15)*sin(angle));
            }

            // Quantitat
            p5.fill(color);
            p5.textSize(12); p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(quantitat, x +  (radiShift + radiQuant + 15)*cos(angle), y +  (radiShift + radiQuant + 15)*sin(angle));
        p5.popStyle();
    }

}
