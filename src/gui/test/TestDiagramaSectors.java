package gui.test;

import gui.Colors;
import gui.DiagramaBarres;
import gui.DiagramaSectors;
import gui.Fonts;
import processing.core.PApplet;

public class TestDiagramaSectors extends PApplet {

    float[] valors = {100, 50, 200, 60};
    String[] titols = {"A", "B", "C", "D"};
    int[] colorBarres;
    DiagramaSectors diagramaSectors;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestDiagramaSectors");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        diagramaSectors = new DiagramaSectors(width/2, height/2, width-100);
        diagramaSectors.setColors(colors);
        diagramaSectors.setFonts(fonts);

        colorBarres = new int[4];
        for(int i=0; i<colorBarres.length; i++){
            colorBarres[i] = colors.getColorAt(i);
        }

        diagramaSectors.setValors(valors);
        diagramaSectors.setCategories(titols);
        diagramaSectors.setColors(colorBarres);
        diagramaSectors.setSectors();
    }

    public void draw(){
        background(255, 100, 100);
        diagramaSectors.display(this);
    }

    public void mousePressed(){
    }
}
