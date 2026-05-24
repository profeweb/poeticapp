package parser.test;

import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumLletres_01 extends PApplet {

    Poemari poemari;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumLletres_01");
    }

    public void settings(){ size(1920, 1080, P2D); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1960);
        poemari.parsePoemes(12, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");

        System.out.println("MITJANA PARAULES: " + poemari.getMitjanaParaulesVersosPoemari());

        textMode(SHAPE);
    }

    public void draw(){
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - num lletres (blocs).pdf";
            beginRecord(PDF, nomPDF);
        }

        Visualitzacions.dibuixaNumLletresPoemariBlocs(this, poemari,75, 75, 300, 15, color(200), color(200, 100, 100));

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void keyPressed(){
        if(key=='s' || key=='S'){
            exportaPDF = true;
        }
    }
}
