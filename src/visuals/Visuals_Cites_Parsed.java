package visuals;

import parser.*;
import processing.core.PApplet;

public class Visuals_Cites_Parsed extends PApplet {

    Poemari poemari;
    Poema poema;
    int numPoema = 0;

    String[] separadors = {"cita", "altres"};
    String[] simbols = {"\""};
    int[] colors;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Cites_Parsed");
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {

        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
        poema = poemari.getPoemaAt(numPoema);

        colors = new int[4];
        colors[0] = color(255, 255, 0);
        colors[3] = color(100);

    }

    public void draw() {
        background(255);

        textSize(24); textAlign(LEFT); fill(0);
        text(poemari.getTitol(), 50, 50);
        textSize(18);
        text("Poema " + (numPoema+1) +": " + poema.getTitol(), 50, 80);
        dibuixaCitesPoema(this, poema, 50, 100, 20, 7.5f);

        dibuixaLlegendaColors(this, separadors, simbols, colors, 600, 50);
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

    public void dibuixaCitesVers(PApplet p5, Vers vers, float x, float y, float factor) {

        String textVers = vers.getText();
        int llarg = textVers.length();

        boolean citaOberta = false;
        float xToken = x;

        for (Token token : vers.getTokens()) {

            int llargToken = token.getValor().length();

            if(token.getTipus() == Token.Tipus.SEPARADOR){
                p5.fill(getColorSimbol(p5, (Simbol)token, simbols, colors));
                p5.noStroke();
                p5.circle(xToken +(llargToken * factor)/2f, y, llargToken * factor);
                if(token.getValor().equals("\"")) {
                    citaOberta = !citaOberta;
                }
            }
            else {
                p5.stroke(0);
                if(citaOberta){
                    p5.stroke(255, 255, 0, 50);
                }
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


        /*
        p5.fill(0);
        p5.textSize(12);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(llarg, xToken + 10, y);
         */
    }

    public void dibuixaCitesPoema(PApplet p5, Poema p, float x, float y, float yStep, float factor){
        for(Estrofa estrofa : p.getEstrofes()) {
            for(Vers vers : estrofa.getVersos()) {
                    dibuixaCitesVers(p5, vers, x, y + yStep * vers.getNumVers() + yStep*(estrofa.getNumero()-1), factor);
            }

        }
    }


    public void keyPressed(){

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
