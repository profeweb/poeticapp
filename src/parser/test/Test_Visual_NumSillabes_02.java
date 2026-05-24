package parser.test;

import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumSillabes_02 extends PApplet {

    Poemari poemari;
    int[] colors;
    int numPoema =  0;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumSillabes_02");
    }

    public void settings(){ size(1920, 1080, P3D); }

    public void setup(){

        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");

        colors = new int[22];
        for(int i=0; i<colors.length; i++){
            colors[i] = lerpColor(color(200), color(100), ((float)i)/colors.length);
        }

        textMode(SHAPE);
    }

    public void draw(){
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - P" + (numPoema+1) + " - num sil.labes (radial).pdf";
            beginRecord(PDF, nomPDF);
        }

        int maxSillabesVers = poemari.getMaxSillabesVersosPoemari();
        float mitjanaSillabesVers = poemari.getMitjanaSillabesVersosPoemari();

        textAlign(LEFT); fill(255, 0, 0); textSize(14);
        text(nf(mitjanaSillabesVers, 0, 2) + " síl.labes/vers (Poemari)", 100, height-150);

        float angInic   = numPoema%2 == 0 ? PI : PI;
        float angFi     = numPoema%2 ==0  ? 0 : TWO_PI;

        Visualitzacions.dibuixaEstrofesArc(this, poemari.getPoemaAt(numPoema), Visualitzacions.QUANTITAT.SILABES,  width/2, height/2, 120, 200, angInic, angFi, mitjanaSillabesVers, maxSillabesVers, colors, color(200, 100, 100));

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }

    }

    public void keyPressed(){
        if(keyCode==UP){
            if(numPoema<poemari.getNumPoemes()-1){
                numPoema++;
            }
        }

        else if(keyCode==DOWN){
            if(numPoema>0){
                numPoema--;
            }
        }
        else if(key=='s' || key=='S'){
            exportaPDF = true;
        }

    }
}
