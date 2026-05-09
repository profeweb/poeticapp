package gui.test;

import gui.Colors;
import gui.DiagramaLiniesApilat;
import gui.Fonts;
import processing.core.PApplet;

public class TestDiagramaLiniesApilat extends PApplet {

    float[][] valors = { {100, 50, 200, 60},
                          {50, 150, 80, 20},
                          {150, 100, 20, 80}};
    String[] piles      = {"2010", "2015", "2020", "2025"};
    String[] categories = {"A", "B", "C"};
    int[] colorBarres;
    DiagramaLiniesApilat db;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestDiagramaLiniesApilat");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        db = new DiagramaLiniesApilat(100, 100, width-200, height-200);
        db.setColorsFonts(colors, fonts);

        colorBarres = new int[categories.length];
        for(int i=0; i<colorBarres.length; i++){
            colorBarres[i] = colors.getColorAt(i+1);
        }

        db.setCategories(categories);
        db.setPiles(piles);
        db.setValors(valors);
        db.setColorsCategories(colorBarres);
        db.setPunts();
    }

    public void draw(){
        background(255, 100, 100);
        db.display(this);
    }

    public void mousePressed(){
    }
}
