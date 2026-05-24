package parser.test;

import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumSillabes_01 extends PApplet {

    Poemari poemari;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumSillabes_01");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(5, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
    }

    public void draw(){
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - num síl.labes (blocs).pdf";
            beginRecord(PDF, nomPDF);
        }

        Visualitzacions.dibuixaNumSillabesPoemariBlocs(this, poemari,75, 75, 300, 15, color(200), color(200, 100, 100));

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
