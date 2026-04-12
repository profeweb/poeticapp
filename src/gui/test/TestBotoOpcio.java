package gui.test;

import gui.*;
import processing.core.PApplet;

public class TestBotoOpcio extends PApplet {

    Colors colors;
    Fonts fonts;
    BotoOpcioGrup csg;
    BotoOpcio c1, c2, c3;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotoOpcio");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        c1 = new BotoOpcio("Opcio A", width/2, 150, 50, 50);
        c1.setColors(colors);
        c1.setFonts(fonts);

        c2 = new BotoOpcio("Opcio B", width/2, 300, 50, 50);
        c2.setColors(colors);
        c2.setFonts(fonts);

        c3 = new BotoOpcio("Opcio C", width/2, 450, 50, 50);
        c3.setColors(colors);
        c3.setFonts(fonts);

        csg = new BotoOpcioGrup(3);
        csg.setSeleccionables(c1, c2, c3);
    }

    public void draw(){
        background(255, 100, 100);
        csg.display(this);
    }

    public void mousePressed(){
        csg.updateAmbClic(this);
    }
}
