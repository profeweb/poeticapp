package parser.test;

import parser.Poema;
import parser.Poemari;
import processing.core.PApplet;

public class Test_Visual03 extends PApplet {

    Poemari poemari;
    int[] colors;
    int numPoema = 12;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual03");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/mariera/poemes a nai/");
        poemari.getPoemaAt(numPoema).printInfo();

        System.out.println("MITJANA PARAULES: " + poemari.getMitjanaParaulesVersosPoemari());

        colors = new int[10];
        for(int i=0; i<colors.length; i++){
            colors[i] = lerpColor(color(200, 100, 100), color(100, 200, 50), ((float)i)/colors.length);
        }
    }

    public void draw(){

        background(255);

        int maxParaulesVers = poemari.getMaxParaulesVersosPoemari();
        float mitjanaParaulesVers = poemari.getMitjanaParaulesVersosPoemari();

        textAlign(LEFT); fill(0); textSize(14);
        text(mitjanaParaulesVers + " paraules / vers (poemari)", 100, height-100);

        float angleVers = TWO_PI / poemari.getNumVersos();
        float angInici = 0;
        for(int np=0; np<poemari.getNumPoemes(); np++) {
            int numVersosPoema = poemari.getPoemaAt(np).getNumVersos();
            float angFi = angInici + angleVers * numVersosPoema;
            poemari.getPoemaAt(np).dibuixaEstrofesArc(this, width / 2, height / 2, 120, 300, angInici, angFi, mitjanaParaulesVers, maxParaulesVers, colors, color(200, 100, 100));
            angInici += angleVers * numVersosPoema;
        }
    }

    public void keyPressed(){
        numPoema = (int)random(0, poemari.getNumPoemes());
    }
}
