package parser.test;

import parser.Poemari;
import processing.core.PApplet;

public class Test_Visual02 extends PApplet {

    Poemari poemari;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual02");
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

        poemari.getPoemaAt(0).dibuixaEstrofesArc(this, width/2, height/2, 100, 300);

    }
}
