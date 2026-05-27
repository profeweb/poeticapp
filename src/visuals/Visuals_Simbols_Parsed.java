package visuals;

import parser.*;
import processing.core.PApplet;

public class Visuals_Simbols_Parsed extends PApplet {

    Poemari poemari;
    Poema poema;
    int numPoema = 0;

    boolean exportaPDF = false;
    String[] separadors = {"punt", "punt i coma", "coma", "altres"};
    String[] simbols = {".", ";", ","};
    int[] colors;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Simbols_Parsed");
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {

        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1960);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
        poema = poemari.getPoemaAt(numPoema);

        colors = new int[4];
        colors[0] = color(255, 0, 0);
        colors[1] = color(0, 255, 0);
        colors[2] = color(0, 0, 255);
        colors[3] = color(100);

    }

    public void draw() {

        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - P" + (numPoema+1) +" - símbols.pdf";
            beginRecord(PDF, nomPDF);
        }

        textSize(24); textAlign(LEFT); fill(0);
        text(poemari.getTitol() + " (" + poemari.getAny() + ")", 50, 50);
        textSize(18);
        text("Poema " + (numPoema+1) +": " + poema.getTitol() + "...", 50, 80);
        dibuixaPoema(this, poema, 50, 100, 20, 7.5f);

        dibuixaLlegendaColors(this, separadors, simbols, colors, 600, 50);

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void dibuixaLlegendaColors(PApplet p5, String[] separadors, String[] simbols, int[] colors, float x, float y){

        p5.fill(0);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.textSize(16);
        p5.text("Símbols", x, y -10);
        p5.stroke(0); p5.strokeWeight(1f);
        p5.line(x, y-10, x + 80, y-10);
        for(int i=0; i<separadors.length; i++){
            p5.fill(0);
            p5.textAlign(p5.LEFT, p5.CENTER);
            p5.textSize(14);
            p5.text(separadors[i], x, y + i*20);
            p5.noStroke();
            p5.fill(colors[i]);
            p5.circle(x - 25, y + i*20, 15);
        }
    }

    public int getColorSimbol(PApplet p5, Simbol simbol, String[] simbols, int[] colors){
        for(int i=0; i<simbols.length; i++){
            if(simbols[i].equals(simbol.getValor())){
                return colors[i];
            }
        }
        return colors[colors.length-1];
    }

    public void dibuixaVers(PApplet p5, Vers vers, float x, float y, float factor) {

        String textVers = vers.getText();
        int llarg = textVers.length();

        float xToken = x;
        for (Token token : vers.getTokens()) {

            int llargToken = token.getValor().length();

            if(token.getTipus() == Token.Tipus.SEPARADOR){
                p5.fill(getColorSimbol(p5, (Simbol)token, simbols, colors));
                p5.noStroke();
                p5.circle(xToken +(llargToken * factor)/2f, y, llargToken * factor);
            }
            else {
                p5.stroke(0);
                p5.strokeWeight(5);
                p5.line(xToken, y, xToken + llargToken * factor, y);
            }

            xToken += llargToken * factor + 10;
        }

        if(vers.getNumVers()%5==0 || vers.getNumVers()==1) {
            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(vers.getNumVers(), x - 10, y);
        }

        p5.fill(0);
        p5.textSize(12);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(llarg, xToken + 10, y);
    }

    public void dibuixaPoema(PApplet p5, Poema p, float x, float y, float yStep, float factor){
        for(Estrofa estrofa : p.getEstrofes()) {
            for(Vers vers : estrofa.getVersos()) {
                    dibuixaVers(p5, vers, x, y + yStep * vers.getNumVers() + yStep*(estrofa.getNumero()-1), factor);
            }

        }
    }


    public void keyPressed(){

        if(key=='s' || key=='S'){
            exportaPDF = true;
        }

        if(keyCode==UP){
            if(numPoema < poemari.getNumPoemes()-1) {
                numPoema++;
            }
        }
        else if(keyCode==DOWN){
            if(numPoema>0){
                numPoema--;
            }
        }
        poema = poemari.getPoemaAt(numPoema);
    }


}
