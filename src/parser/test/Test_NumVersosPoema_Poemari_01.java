package parser.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.Fonts;
import parser.Autor;
import parser.DadesPoemaris;
import parser.Poemari;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Test_NumVersosPoema_Poemari_01 extends PApplet {

    DiagramaBarres db;
    Colors colors;
    Fonts fonts;

    int numPoemari =  0;
    Poemari poemari;
    ArrayList<Poemari> poemaris;
    boolean exportaPDF = false;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_NumVersosPoema_Poemari_01");
    }

    public void settings(){ size(1920, 1080);}

    public void setup(){

        // Carrega els poemaris de tots els autors (aplicant segmentació i tokenització)
        poemaris = DadesPoemaris.carregaPoemarisAutors().get(0).getPoemaris();

       setDadesDiagrama(poemaris);
    }

    public void setDadesDiagrama(ArrayList<Poemari> poemaris ){

        // Selecciona el poemari
        poemari = poemaris.get(numPoemari);

        // Calcula el número d'ocurrències del terme en cadascun dels poemaris
        float[] valors = DadesPoemaris.getNumVersosPoemes(poemari);

        // Consulta els títols dels poemes
        String[] titols = DadesPoemaris.getNumerosPoemes(poemari);

        colors = new Colors(this);
        fonts = new Fonts(this);

        // Crea el diagrama de barres per a la visualització
        db = new DiagramaBarres(100, 100, valors.length * 80, 400);
        db.setColors(colors);
        db.setFonts(fonts);

        // Defineix la paleta de colors per als 8 poemaris
        int[] colorBarres = new int[titols.length];
        for(int i=0; i<colorBarres.length; i++) {
            colorBarres[i] = color(0xFFde324c);
        }

        // Estableix les propietats i valors del diagrama
        db.setValors(valors);
        db.setCategories(titols);
        db.setColorsCategories(colorBarres);
        db.setOrientacio(DiagramaBarres.ORIENTACIO.VERTICAL);
        db.setBarres();
    }

    public void draw(){

        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() + " - num versos (poemes).pdf";
            beginRecord(PDF, nomPDF);
        }

        // Informació del terme i número d'ocurrències
        textAlign(LEFT); textSize(18); fill(0);
        text("Num. Versos Poemari: " + poemari.getTitol() + "(" + poemari.getAny() +")", 100, 100);

        // Dibuixa el diagrama de barres
        db.display(this);

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void keyPressed(){
        if(key=='s' || key=='S'){
            exportaPDF = true;
        }
        else if(keyCode==UP && numPoemari < poemari.getNumPoemes()-1){
            numPoemari++;
            setDadesDiagrama(poemaris);
        }
        else if(keyCode==DOWN && numPoemari>0){
            numPoemari--;
            setDadesDiagrama(poemaris);
        }
    }


}
