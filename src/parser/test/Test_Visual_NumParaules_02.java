package parser.test;

import gui.Fonts;
import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumParaules_02 extends PApplet {

    Poemari poemari;
    int[] colors;
    int numPoema = 0;
    boolean exportaPDF = false;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumParaules_02");
    }

    public void settings(){ size(1920, 1080, P2D); }

    public void setup(){
        poemari = new Poemari("El pis de la badia", "MA Rieria", 1993);
        poemari.parsePoemes(30, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");

        System.out.println("MITJANA PARAULES: " + poemari.getMitjanaParaulesVersosPoemari());

        colors = new int[22];
        for(int i=0; i<colors.length; i++){
            colors[i] = lerpColor(color(200), color(100), ((float)i)/colors.length);
        }

        fonts = new Fonts(this);

        textMode(SHAPE);
    }

    public void draw(){

        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - P" + (numPoema+1) + " - num paraules (radial).pdf";
            beginRecord(PDF, nomPDF);
        }

        int maxParaulesVers = poemari.getMaxParaulesVersosPoemari();
        float mitjanaParaulesVers = poemari.getMitjanaParaulesVersosPoemari();

        textAlign(LEFT); fill(255, 0, 0); textSize(14);
        text(nf(mitjanaParaulesVers,0, 2) + " paraules/vers (Poemari)", 100, height-150);

        //float angInic   = 0; //numPoema%2 == 0 ? PI : PI;
        float angInic   = numPoema%2 == 0 ? PI : PI;
        //float angFi     = TWO_PI; //numPoema%2 ==0  ? 0 : TWO_PI;
        float angFi     = numPoema%2 ==0  ? 0 : TWO_PI;

        Visualitzacions.dibuixaEstrofesArc(this, poemari.getPoemaAt(numPoema), Visualitzacions.QUANTITAT.PARAULES, width/2, height/2, 120, 200, angInic, angFi, mitjanaParaulesVers, maxParaulesVers, colors, color(200, 100, 100));

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void keyPressed(){
        if(keyCode == UP){
            if(numPoema < poemari.getNumPoemes()-1) {
                numPoema++;
            }
        }
        else if(keyCode == DOWN){
            if(numPoema >0) {
                numPoema--;
            }
        }
        else if(key=='s' || key=='S'){
            exportaPDF = true;
        }
    }
}
