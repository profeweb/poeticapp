package gui.test;

import gui.Boto;
import gui.BotonsPaginacio;
import gui.Colors;
import gui.Fonts;
import processing.core.PApplet;

public class TestBotonsPaginacio extends PApplet {

    BotonsPaginacio b1;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotonsPaginacio");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        b1 = new BotonsPaginacio(100, 200, 50);
        b1.setColorsFonts(colors, fonts);
        b1.setBotonsPagines(5);
        b1.setBotonsSegAnt();
    }

    public void draw(){
        background(255, 100, 100);
        b1.display(this);
    }

    public void mousePressed(){
        b1.updateClick(this);
    }
}
