package visuals;

import gui.Colors;
import gui.DiagramaLinies;
import gui.Fonts;
import processing.core.PApplet;

public class DiagramaLinies_Anys extends PApplet {

    String[] categories = {"2010", "2015", "2020", "2025"};
    float[] valors = {1500, 3200, 2400, 1885};
    int[] colors = {color(255, 0, 0), color(0, 255, 0), color(0,0, 255), color(255, 255, 0)};

    DiagramaLinies dl;
    Fonts fonts;
    Colors colorsApp;

    public static void main(String[] args) {
        PApplet.main("visuals.DiagramaLinies_Anys");
    }

    public void settings(){ size(800, 800); }

    public void setup(){

        fonts = new Fonts(this);
        colorsApp = new Colors(this);

        dl = new DiagramaLinies(50, 100, width-100, height -200);
        dl.setCategories(categories);
        dl.setValors(valors);
        dl.setColorsCategories(colors);
        dl.setFonts(fonts);
        dl.setColors(colorsApp);
        dl.setPunts();

        dl.setEixHoritzontal("Eix Horitzontal");
        dl.setEixVertical("Eix Vertical", 10);
    }

    public void draw(){
        background(200, 100, 100);
        dl.display(this);
    }
}
