package parser.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.Fonts;
import parser.Autor;
import parser.DadesPoemaris;
import parser.Poemari;
import processing.core.PApplet;

import java.util.ArrayList;

public class Test_NumOcurrencies_Terme_01 extends PApplet {

    float[] valors;
    String[] titols;
    int[] colorBarres;
    DiagramaBarres db;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {

    PApplet.main("parser.test.Test_NumOcurrencies_Terme_01");
    }

    public void settings(){ size(1920, 1080);}

    public void setup(){

        ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
        ArrayList<Poemari> poemaris = autors.get(0).getPoemaris();
        valors = DadesPoemaris.getNumOcurrenciesTerme("vida", poemaris);
        titols = DadesPoemaris.getTitolsSencersPoemaris(autors.get(0));

        colors = new Colors(this);
        fonts = new Fonts(this);
        db = new DiagramaBarres(500, 100, 1000, 400);
        db.setColors(colors);
        db.setFonts(fonts);

        colorBarres = new int[8];
        colorBarres[0] = color(0xFF6f1926);
        colorBarres[1] = color(0xFFde324c);
        colorBarres[2] = color(0xFFf4895f);
        colorBarres[3] = color(0xFFf8e16f);
        colorBarres[4] = color(0xFF95cf92);
        colorBarres[5] = color(0xFF369acc);
        colorBarres[6] = color(0xFF9656a2);
        colorBarres[7] = color(0xFFcbabd1);

        db.setValors(valors);
        db.setCategories(titols);
        db.setColorsCategories(colorBarres);
        db.setOrientacio(DiagramaBarres.ORIENTACIO.HORITZONTAL);
        db.setBarres();
    }

    public void draw(){
        background(255);
        db.display(this);
    }


}
