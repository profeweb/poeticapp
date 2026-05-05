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
        poemari.parsePoemes(5, "data/poems/mariera/poemes a nai/");
    }

    public void draw(){
        background(255);
        poemari.dibuixaPoemesBlocs(this, 75, 75, 300, 15, color(200), color(200, 100, 100));

    }
}
