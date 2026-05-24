package visuals;

import gui.DiagramaSectors;
import parser.*;
import processing.core.PApplet;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static parser.DadesPoemaris.getNumVersosPoemaris;
import static parser.DadesPoemaris.getTitolAnysPoemaris;

public class Visuals_VersosPoemaris extends PApplet {

    int[] colors;
    float[] dades;
    String[] etiquetes;
    DiagramaSectors sectors;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_VersosPoemaris");
    }

    public void settings() {
        size(1920, 1080);
    }

    public void setup() {

        ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
        Autor autor = autors.get(0);

        etiquetes = getTitolAnysPoemaris(autor);
        dades = getNumVersosPoemaris(autor);

        colors = new int[8];
        colors[0] = color(0xFF6f1926);
        colors[1] = color(0xFFde324c);
        colors[2] = color(0xFFf4895f);
        colors[3] = color(0xFFf8e16f);
        colors[4] = color(0xFF95cf92);
        colors[5] = color(0xFF369acc);
        colors[6] = color(0xFF9656a2);
        colors[7] = color(0xFFcbabd1);

        sectors = new DiagramaSectors(width/2, height/2, 800);
        sectors.setCategories(etiquetes);
        sectors.setValors(dades);
        sectors.setColorsCategories(colors);
        sectors.setSectors();

    }

    public void draw() {
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/ Coprus MA Riera - Num Versos x Poemari - sectors .pdf";
            beginRecord(PDF, nomPDF);
        }

        sectors.display(this);

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
