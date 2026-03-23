package gui.test;

import gui.Boto;
import gui.Colors;
import gui.Fonts;
import processing.core.PApplet;

public class TestTresBotons extends PApplet {

    Boto bRed, bGreen, bBlue;
    Colors colors;
    Fonts fonts;
    int colorFons;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestTresBotons");
    }

    public void settings(){ size(800, 800); }

    public void setup(){

        colors = new Colors(this);
        fonts = new Fonts(this);

        bRed = new Boto("RED", width/4 - 75, height/2 - 50, 150, 100);
        bRed.setColors(colors);
        bRed.setFonts(fonts);

        bGreen = new Boto("GREEN", width/2 - 75, height/2 - 50, 150, 100);
        bGreen.setColors(colors);
        bGreen.setFonts(fonts);

        bBlue = new Boto("BLUE", 3*width/4 -75, height/2 - 50, 150, 100);
        bBlue.setColors(colors);
        bBlue.setFonts(fonts);

        colorFons = color(255);
    }

    public void draw(){
        background(colorFons);
        bRed.display(this);
        bGreen.display(this);
        bBlue.display(this);

        if(bRed.updateCursor(this) || bGreen.updateCursor(this)|| bBlue.updateCursor(this)){
            cursor(HAND);
        }
        else {
            cursor(ARROW);
        }
    }

    public void mousePressed(){
        if(bRed.mouseDins(this)){
            println("BOTO RED CLICKAT!");
            colorFons = color(255, 0, 0);
        }
        else if(bGreen.mouseDins(this)){
            println("BOTO GREEN CLICKAT!");
            colorFons = color(0, 255, 0);
        }
        else if(bBlue.mouseDins(this)){
            println("BOTO BLUE CLICKAT!");
            colorFons = color(0, 0, 255);
        }
    }
}
