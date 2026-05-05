package parser.test;

import parser.Poemari;
import processing.core.PApplet;

public class Test_Visual02 extends PApplet {

    Poemari poemari;
    int[] colors;
    int numPoema = 12;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual02");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        //poemari.parsePoemes(13, "data/poems/mariera/poemes a nai/");
        //poemari.parsePoemes(13, "data/poems/mariera/biografia/");
        poemari.parsePoemes(30, "data/poems/mariera/el pis de la badia/");
        poemari.getPoemaAt(numPoema).printInfo();

        System.out.println("MITJANA PARAULES: " + poemari.getMitjanaParaulesVersosPoemari());

        colors = new int[22];
        for(int i=0; i<colors.length; i++){
            colors[i] = lerpColor(color(200), color(100), ((float)i)/colors.length);
        }
    }

    public void draw(){

        background(255);

        int maxParaulesVers = poemari.getMaxParaulesVersosPoemari();
        float mitjanaParaulesVers = poemari.getMitjanaParaulesVersosPoemari();

        textAlign(LEFT); fill(255, 0, 0); textSize(14);
        text(mitjanaParaulesVers + " paraules / vers (llibre)", 100, height-150);

        float angInic   = numPoema%2 == 0 ? PI : PI;
        float angFi     = numPoema%2 ==0  ? 0 : TWO_PI;

        poemari.getPoemaAt(numPoema).dibuixaEstrofesArc(this, width/2, height/2, 120, 200, angInic, angFi, mitjanaParaulesVers, maxParaulesVers, colors, color(200, 100, 100));

    }

    public void keyPressed(){
        numPoema = (int)random(0, poemari.getNumPoemes());
    }
}
