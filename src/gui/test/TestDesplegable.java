package gui.test;

import gui.Boto;
import gui.Colors;
import gui.Desplegable;
import gui.Fonts;
import processing.core.PApplet;

public class TestDesplegable extends PApplet {

    Desplegable desplegable;
    Colors colors;
    Fonts fonts;
    int colorFons;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestDesplegable");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        String[] opcions = {"RED", "GREEN", "BLUE"};
        desplegable = new Desplegable(opcions, 200, 200, 200, 30);
        desplegable.setColors(colors);
        desplegable.setFonts(fonts);

        colorFons = color(255);
    }

    public void draw(){
        background(colorFons);
        desplegable.display(this);
    }

    public void mousePressed(){
        desplegable.update(this);
        String opcioSeleccionada = desplegable.getOpcioSeleccionada();
        if(opcioSeleccionada.equals("RED")){
            colorFons = color(255, 0, 0);
        }
        else if(opcioSeleccionada.equals("GREEN")){
            colorFons = color(0, 255, 0);
        }
        else if(opcioSeleccionada.equals("BLUE")){
            colorFons = color(0, 0, 255);
        }

    }
}
