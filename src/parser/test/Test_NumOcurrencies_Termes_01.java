package parser.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.DiagramaLiniesApilat;
import gui.Fonts;
import parser.Autor;
import parser.DadesPoemaris;
import parser.Poemari;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Test_NumOcurrencies_Termes_01 extends PApplet {

    DiagramaLiniesApilat dl;
    int[] colorBarres;
    Colors colors;
    Fonts fonts;

    String[] termes = {"amor", "cos", "home", "vida" };
    String[] categories, piles;
    String totsTermes;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_NumOcurrencies_Termes_01");
    }

    public void settings(){ size(1920, 1080);}

    public void setup(){

        // Carrega els poemaris de tots els autors (aplicant segmentació i tokenització)
        ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
        ArrayList<Poemari> poemaris = autors.get(0).getPoemaris();

        // Ordena els poemaris per data ascendent
        Collections.sort(poemaris, new Comparator<Poemari>() {
            @Override
            public int compare(Poemari o1, Poemari o2) {
                return o1.getAny() - o2.getAny();
            }
        });

        totsTermes = "";
        int numTerme = 0;
        float[][] valors = new float[termes.length][poemaris.size()];
        for(String terme : termes){
            totsTermes += terme + ((numTerme==termes.length-1) ? "." : ", ");
            valors[numTerme] = DadesPoemaris.getNumOcurrenciesTerme(terme, poemaris);
            numTerme++;
        }

        piles = DadesPoemaris.getTitolsSencersPoemaris(autors.get(0));
        categories = termes;  // paraules

        // Defineix la paleta de colors per als 8 poemaris
        colorBarres = new int[8];
        colorBarres[0] = color(0xFF6f1926);
        colorBarres[1] = color(0xFFde324c);
        colorBarres[2] = color(0xFFf4895f);
        colorBarres[3] = color(0xFFf8e16f);
        colorBarres[4] = color(0xFF95cf92);
        colorBarres[5] = color(0xFF369acc);
        colorBarres[6] = color(0xFF9656a2);
        colorBarres[7] = color(0xFFcbabd1);

        // Estableix les propietats i valors del diagrama
        colors = new Colors(this);
        fonts = new Fonts(this);
        dl = new DiagramaLiniesApilat(100, 100, width-200, 600);
        dl.setColorsFonts(colors, fonts);
        dl.setCategories(categories);
        dl.setPiles(piles);
        dl.setValors(valors);
        dl.setColorsCategories(colorBarres);
        dl.setPunts();
        dl.setEixHoritzontal("Poemaris");

    }

    public void draw(){

        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/ termes "+totsTermes+" - num Ocurrencies (corpus).pdf";
            beginRecord(PDF, nomPDF);
        }

        // Informació del terme i número d'ocurrències
        textAlign(LEFT); textSize(18); fill(0);
        text("Termes: " + totsTermes, 100, 100);

        // Dibuixa la llegenda de categories i colors
        dibuixaLlegendaCategoriesColors(120, 200);

        // Dibuixa el diagrama de barres
        dl.display(this);

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void dibuixaLlegendaCategoriesColors(float x, float y){
        int numTerme = 0;
        for(String terme : termes){
            fill( colorBarres[numTerme] );
            noStroke();
            circle(x, y + numTerme*25, 20);
            fill(0); textAlign(LEFT, CENTER);
            text(categories[numTerme], x + 25, y + numTerme*25);
            numTerme++;
        }
    }

    public void keyPressed(){
        if(key=='s' || key=='S'){
            exportaPDF = true;
        }
    }


}
