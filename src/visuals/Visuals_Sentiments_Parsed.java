package visuals;

import parser.*;
import processing.core.PApplet;
import sentiments.AnalisiSentiments;
import sentiments.DiccionariSentiments;
import sentiments.TokenAnalitzat;
import sentiments.VersAnalitzat;

import java.util.ArrayList;
import java.util.List;

public class Visuals_Sentiments_Parsed extends PApplet {

    ArrayList<Poemari> poemaris;
    Poemari poemari;
    Poema poema;
    int numPoemari = 0;
    int numPoema = 2;

    public static String rutaJSON = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\diccionaris\\diccionari_sentiments_ca.json";
    DiccionariSentiments diccionariSentiments;
    AnalisiSentiments analisiSentiments;
    boolean exportaPDF = false;
    int[] colors;
    String[] categories = {"LEXIC", "NEGADOR", "INTENSIFICADOR", "DIMINUIDOR", "NEUTRE"};

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Sentiments_Parsed");
    }

    public void settings() {
        size(1200, 800);
    }

    public void setup() {

        ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
        poemaris = autors.get(0).getPoemaris();
        poemari = poemaris.get(numPoemari);
        poema = poemari.getPoemaAt(numPoema);

        diccionariSentiments = new DiccionariSentiments(rutaJSON);

        analisiSentiments = new AnalisiSentiments(diccionariSentiments);
        analisiSentiments.analitzaPoema(poema);

        colors = new int[5];
        colors[0] = color(100);
        colors[1] = color(255, 0, 0);
        colors[2] = color(0, 255, 0);
        colors[3] = color(0, 0, 255);
        colors[4] = color(0);

    }

    public void draw() {

        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - P" + (numPoema+1) +" - sentiments.pdf";
            beginRecord(PDF, nomPDF);
        }

        textSize(24); textAlign(LEFT); fill(0);
        text(poemari.getTitol() + " (" + poemari.getAny() + ")", 50, 50);
        textSize(18);
        text("Poema " + (numPoema+1) +": " + poema.getTitol() + "...", 50, 80);


        float puntuacio = (float) analisiSentiments.getPuntuacioPoema();
        String sentiment = analisiSentiments.getSentimentPoema().getEtiqueta();

        text("Puntuació: " + nf(puntuacio, 0, 2), 50, 110);
        text("Sentiment: " + sentiment, 250, 110);


        dibuixaSentimentsPoema(this, analisiSentiments, 50, 100, 50, 10.5f);

        dibuixaLlegendaColors(this, categories, colors, 1000, 50);

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void dibuixaLlegendaColors(PApplet p5, String[] ners, int[] colors, float x, float y){

        p5.fill(0);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.textSize(16);
        p5.text("Categories", x, y -10);
        p5.stroke(0); p5.strokeWeight(1f);
        p5.line(x, y-10, x + 80, y-10);
        for(int i=0; i<ners.length; i++){
            p5.fill(0);
            p5.textAlign(p5.LEFT, p5.CENTER);
            p5.textSize(14);
            p5.text(ners[i], x, y + i*20);
            p5.noStroke();
            p5.fill(colors[i]);
            p5.circle(x - 25, y + i*20, 15);
        }
    }

    public int getColorSentiment(String tipusSentiment, String[] sentiments, int[] colors){
        for(int i=0; i<sentiments.length; i++){
            if(sentiments[i].equals(tipusSentiment)){
                return colors[i];
            }
        }
        return colors[colors.length-1];
    }

    public void dibuixaVers(PApplet p5, VersAnalitzat vers, float x, float y, float factor) {

        float xToken = x;

        for (TokenAnalitzat token : vers.getTokens()) {

            int llargToken = token.getOriginal().length();

            if(token.getRol() == AnalisiSentiments.Rol.LEXIC){
                p5.stroke(colors[0]);
                p5.strokeWeight(25);
                p5.line(xToken, y, xToken + llargToken * factor, y);
                p5.fill(0); p5.textSize(14);
                p5.text(nf((float)token.getPuntuacioBase(), 0, 2), xToken, y -25);

            }
            else if(token.getRol() == AnalisiSentiments.Rol.NEGADOR){
                p5.stroke(colors[1]);
                p5.strokeWeight(25);
                p5.line(xToken, y, xToken + llargToken * factor, y);
            }
            else if(token.getRol() == AnalisiSentiments.Rol.INTENSIFICADOR){
                p5.stroke(colors[2]);
                p5.strokeWeight(25);
                p5.line(xToken, y, xToken + llargToken * factor, y);
                p5.fill(0); p5.textSize(14);
                p5.text(nf((float)token.getFactor(), 0, 2), xToken, y -25);

            }
            else if(token.getRol() == AnalisiSentiments.Rol.DIMINUIDOR){
                p5.stroke(colors[3]);
                p5.strokeWeight(25);
                p5.line(xToken, y, xToken + llargToken * factor, y);
                p5.fill(0); p5.textSize(14);
                p5.text(nf((float)token.getFactor(), 0, 2), xToken, y -25);

            }
            else if(token.getRol() == AnalisiSentiments.Rol.NEUTRE){
                p5.stroke(colors[4]);
                p5.strokeWeight(25);
                p5.line(xToken, y, xToken + llargToken * factor, y);

            }

            p5.fill(255);
            p5.textSize(14);
            p5.textAlign(p5.LEFT, p5.CENTER);
            p5.text(token.getForma(), xToken, y);

            xToken += llargToken * factor + 30;
        }

        if(vers.getNumero() %5 == 0 || vers.getNumero() == 1) {
            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(vers.getNumero(), x - 20, y);
        }

        p5.fill(0);
        p5.textSize(18);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text((float)vers.getPuntuacio() + " " +  vers.getSentiment(), xToken + 10, y);
    }

    public void dibuixaSentimentsPoema(PApplet p5, AnalisiSentiments analisiSentiments, float x, float y, float yStep, float factor){
        int numEstrofa = 0;
        for(List<VersAnalitzat> versos: analisiSentiments.estrofes) {
            for(VersAnalitzat vers : versos) {
                    dibuixaVers(p5, vers, x, y + yStep * vers.getNumero() + yStep*numEstrofa, factor);
            }
            numEstrofa++;
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
        else if(keyCode==RIGHT){
            if(numPoemari < poemaris.size()-1) {
                numPoemari++;
                numPoema = 0;
            }
        }
        else if(keyCode==LEFT){
            if(numPoemari>0){
                numPoemari--;
                numPoema = 0;
            }
        }

        poemari = poemaris.get(numPoemari);
        poema = poemari.getPoemaAt(numPoema);

        analisiSentiments = new AnalisiSentiments(diccionariSentiments);
        analisiSentiments.analitzaPoema(poema);
    }


}
