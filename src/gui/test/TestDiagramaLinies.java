package gui.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.Fonts;
import processing.core.PApplet;

public class TestDiagramaLinies extends PApplet {

    float[] valors = {100, 50, 200, 60};
    String[] titols = {"2010", "2011", "2012", "2013"};
    int[] colorBarres;
    DiagramaBarres db;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestDiagramaLinies");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        db = new DiagramaBarres(100, 100, width-200, height-200);
        db.setColors(colors);
        db.setFonts(fonts);

        colorBarres = new int[4];
        for(int i=0; i<colorBarres.length; i++){
            colorBarres[i] = colors.getColorAt(i);
        }

        db.setValors(valors);
        db.setCategories(titols);
        db.setColors(colorBarres);
        db.setBarres();
    }

    public void draw(){
        background(255, 100, 100);
        db.display(this);
    }

    public void mousePressed(){
    }
}
