package gui.test;

import gui.*;
import processing.core.PApplet;

public class TestCasellaSeleccio extends PApplet {

    Boto b1;
    Colors colors;
    Fonts fonts;
    CasellaSeleccioGrup csg;
    CasellaSeleccio c1, c2;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestCasellaSeleccio");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        c1 = new CasellaSeleccio("Opcio A", width/2, 200, 50, 50);
        c1.setColors(colors);
        c1.setFonts(fonts);

        c2 = new CasellaSeleccio("Opcio B", width/2, 400, 50, 50);
        c2.setColors(colors);
        c2.setFonts(fonts);

        csg = new CasellaSeleccioGrup(2);
        csg.setSeleccionables(c1, c2);
    }

    public void draw(){
        background(255, 100, 100);
        csg.display(this);
    }

    public void mousePressed(){
        csg.updateAmbClic(this);
    }
}
