package visuals;

import parser.*;
import processing.core.PApplet;

public class Visuals_Enunciacions_Parsed extends PApplet {

    Poemari poemari;
    Poema poema;
    int numPoema = 0;

    String[] separadors = {"dos punts", "guió", "altres"};
    String[] simbols = {":", "-"};
    int[] colors;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Enunciacions_Parsed");
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {

        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
        poema = poemari.getPoemaAt(numPoema);

        colors = new int[3];
        colors[0] = color(255, 255, 0);
        colors[1] = color(0, 255, 255);
        colors[2] = color(100);

    }

    public void draw() {
        background(255);

        textSize(24); textAlign(LEFT); fill(0);
        text(poemari.getTitol(), 50, 50);
        textSize(18);
        text("Poema " + (numPoema+1) +": " + poema.getTitol(), 50, 80);

        dibuixaEnunciatsPoema(this, poema, 50, 100, 20, 7.5f);

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

    public boolean dibuixaEnunciatsVers(PApplet p5, Vers versActual, Vers versSeguent, float x, float y, float factor, boolean enunciatObert) {

        String textVers = versActual.getText();
        int llarg = textVers.length();

        float xToken = x;

        int numTokenVers =  0;
        for (Token token : versActual.getTokens()) {

            int llargToken = token.getValor().length();

            boolean darrerTokenVers = numTokenVers == versActual.getTokens().size()-1;
            Token segToken = darrerTokenVers ? null : versActual.getTokens().get(numTokenVers+1);

            if(token.getTipus() == Token.Tipus.SEPARADOR){
                p5.fill(getColorSimbol(p5, (Simbol)token, simbols, colors));
                p5.noStroke();
                p5.circle(xToken +(llargToken * factor)/2f, y, llargToken * factor);

                if(token.getValor().equals(":") &&
                        ((darrerTokenVers &&  versSeguent.getTokens().get(0).getValor().equals("-")) || (!darrerTokenVers && segToken!=null && segToken.getValor().equals("-")))
                ) {
                    enunciatObert = true;
                }

                if(enunciatObert && token.getValor().equals(".") && darrerTokenVers){
                    enunciatObert = false;
                }

            }
            else {
                p5.stroke(0);
                if(enunciatObert){
                    p5.stroke(0, 255, 255, 50);
                }
                p5.strokeWeight(5);
                p5.line(xToken, y, xToken + llargToken * factor, y);
            }

            xToken += llargToken * factor + 10;
            numTokenVers++;
        }

        if(versActual.getNumVers()%5==0 || versActual.getNumVers()==1) {
            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(versActual.getNumVers(), x - 10, y);
        }

        return enunciatObert;
        /*
        p5.fill(0);
        p5.textSize(12);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(llarg, xToken + 10, y);
         */
    }

    public void dibuixaEnunciatsPoema(PApplet p5, Poema p, float x, float y, float yStep, float factor){
        for(Estrofa estrofa : p.getEstrofes()) {
            boolean enunciatObert = false;
            for(int i=0; i<estrofa.getNumVersos(); i++) {
                Vers versActual = estrofa.getVersAt(i);
                Vers versSeguent = (i<estrofa.getNumVersos()-1) ? estrofa.getVersAt(i+1) : null;
                enunciatObert = dibuixaEnunciatsVers(p5, versActual, versSeguent, x, y + yStep * versActual.getNumVers() + yStep*(estrofa.getNumero()-1), factor, enunciatObert);
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
