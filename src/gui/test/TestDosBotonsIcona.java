package gui.test;

import gui.BotoIcona;
import gui.Colors;
import gui.Fonts;
import gui.Mides;
import processing.core.PApplet;

public class TestDosBotonsIcona extends PApplet{

    BotoIcona bOn, bOff;
    Colors colors;
    Fonts fonts;

    int colorFons;
    boolean sound = false;
    final int CODI_ON = 0x1F600 - 236;
    final int CODI_OFF = 0x1F600 - 235;


    public static void main(String[] args) {
        PApplet.main("gui.test.TestDosBotonsIcona");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        bOn = new BotoIcona("ON", CODI_ON, 50, height/2 - 50, 300, 100);
        bOn.setColors(colors);
        bOn.setFonts(fonts);

        bOff = new BotoIcona("OFF", CODI_OFF, 450, height/2 - 50, 300, 100);
        bOff.setColors(colors);
        bOff.setFonts(fonts);

        colorFons = color(155);
    }

    public void draw(){

        background(colorFons);

        bOn.display(this);
        bOff.display(this);

        if(bOn.updateCursor(this) || bOff.mouseDins(this)){
            cursor(HAND);
        }
        else {
            cursor(ARROW);
        }

        fill(255, 0, 0); textSize(48); textAlign(CENTER);
        text(sound ? "SOUND ON" : "SOUND OFF", width/2, height/4);
    }

    public void mousePressed(){
        if(bOn.mouseDins(this)){
            println("BOTO ON!");
            sound = true;
            colorFons = color(255);
        }
        else if(bOff.mouseDins(this)){
            println("BOTO OFF!");
            sound = false;
            colorFons = color(0);
        }
    }

}
