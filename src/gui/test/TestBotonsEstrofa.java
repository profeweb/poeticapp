package gui.test;

import gui.BotonsEstrofa;
import gui.BotonsVers;
import gui.Colors;
import gui.Fonts;
import parser.Estrofa;
import parser.ParserPoema;
import parser.Poemari;
import parser.Vers;
import processing.core.PApplet;

public class TestBotonsEstrofa extends PApplet {

    BotonsEstrofa b1;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotonsEstrofa");
    }

    public void settings(){ size(1200, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(3, "data/poems/mariera/poemes a nai/");

        Estrofa estrofa = poemari.getPoemaAt(1).getEstrofaAt(0);
        estrofa.printEstrofa();

        b1 = new BotonsEstrofa( 100, 50, 800, 50);
        b1.setColorsFonts(colors, fonts);
        b1.setBotonsEstrofa(estrofa, 12);
        b1.setBotonsPaginacio();
    }

    public void draw(){
        background(255, 100, 100);
        b1.display(this);
    }

    public void mousePressed(){
        b1.updateClick(this);
    }
}
