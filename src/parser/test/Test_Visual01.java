package parser.test;

import parser.Poemari;
import processing.core.PApplet;

public class Test_Visual01 extends PApplet {

    Poemari poemari;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual01");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(6, "data/poems/mariera/poemes a nai/");
        poemari.getPoemaAt(1).printInfo();

        System.out.println("MITJANA PARAULES: " + poemari.getMitjanaParaulesVersosPoemari());
    }

    public void draw(){

        background(255);
        //int maxVersos = poemari.getPoemaAt(0).getMaxVersosEstrofes();
        //poemari.getPoemaAt(0).getEstrofaAt(0).dibuixaVersosEstrofa(this, 100, 100, 300, 300, color(100, 50), color(200, 0, 0, 250), maxVersos);

        poemari.dibuixaPoemesBlocs(this, 50, 50, 200, 15, color(200), color(200, 100, 100));

    }
}
