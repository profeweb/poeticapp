package gui.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.DiagramaBarresApilat;
import gui.Fonts;
import processing.core.PApplet;

public class TestDiagramaBarresApilat extends PApplet {

    float[][] valors = { {100, 50, 200, 60}, {50, 150, 80, 20}, {50, 100, 20, 80}};
    String[] piles = {"2010", "2015", "2020"};  // terme
    String[] titols = {"A", "B", "C", "D"};  // poemaris
    int[] colorBarres;
    DiagramaBarresApilat db;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestDiagramaBarresApilat");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        db = new DiagramaBarresApilat(50, 50, width-100, height-100);
        db.setColorsFonts(colors, fonts);

        colorBarres = new int[4];
        for(int i=0; i<colorBarres.length; i++){
            colorBarres[i] = colors.getColorAt(i+1);
        }

        db.setCategories(titols);
        db.setPiles(piles);
        db.setValors(valors);
        db.setColorsCategories(colorBarres);
        db.setBarres();
    }

    public void draw(){
        background(255, 100, 100);
        db.display(this);
    }

    public void mousePressed(){
    }
}
