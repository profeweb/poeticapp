package gui.test;

import gui.BotonsEstrofa;
import gui.BotonsPoema;
import gui.Colors;
import gui.Fonts;
import parser.Estrofa;
import parser.Poema;
import parser.Poemari;
import processing.core.PApplet;

public class TestBotonsPoema extends PApplet {

    BotonsPoema b1;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotonsPoema");
    }

    public void settings(){ size(1200, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/mariera/poemes a nai/");

        Poema poema = poemari.getPoemaAt(12);

        b1 = new BotonsPoema( 100, 100, 800, 50);
        b1.setColorsFonts(colors, fonts);
        b1.setBotonsPoema(poema);
    }

    public void draw(){
        background(255, 100, 100);
        b1.display(this);
    }

    public void mousePressed(){
        b1.updateClick(this);
    }
}
