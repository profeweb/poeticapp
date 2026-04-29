package gui.test;

import gui.Boto;
import gui.BotonsVers;
import gui.Colors;
import gui.Fonts;
import parser.ParserPoema;
import parser.Vers;
import processing.core.PApplet;

public class TestBotomsVers extends PApplet {

    Vers vers;
    BotonsVers b1;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotomsVers");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        vers = ParserPoema.parseVers("Tateix Nai, restes com una son desperta.", 1);
        b1 = new BotonsVers( 100, 200, 50);
        b1.setColorsFonts(colors, fonts);
        b1.setBotons(vers);
    }

    public void draw(){
        background(255, 100, 100);
        b1.display(this);
    }

    public void mousePressed(){
        b1.updateClick(this);
    }
}
