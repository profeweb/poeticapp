package parser.test;

import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumLletres_01 extends PApplet {

    Poemari poemari;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumLletres_01");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(5, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
    }

    public void draw(){
        background(255);
        //poemari.dibuixaNumLletresPoemesBlocs(this, 75, 75, 300, 15, color(200), color(200, 100, 100));
        Visualitzacions.dibuixaNumLletresPoemariBlocs(this, poemari,75, 75, 300, 15, color(200), color(200, 100, 100));

    }
}
