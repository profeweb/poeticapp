package gui.test;

import gui.Boto;
import gui.Colors;
import gui.Fonts;
import processing.core.PApplet;

public class TestBoto extends PApplet {

    Boto b1;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBoto");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        b1 = new Boto("BOTO", 100, 200, 300, 100);
        b1.setColors(colors);
        b1.setFonts(fonts);
    }

    public void draw(){
        background(255, 100, 100);
        b1.display(this);

        if(b1.updateCursor(this)){
            cursor(HAND);
        }
        else {
            cursor(ARROW);
        }
    }

    public void mousePressed(){
        if(b1.mouseDins(this)){
            println("BOTO CLICKAT!");
        }
    }
}
