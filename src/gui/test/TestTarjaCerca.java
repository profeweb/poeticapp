package gui.test;

import gui.Boto;
import gui.Colors;
import gui.Fonts;
import gui.TarjaCerca;
import processing.core.PApplet;

public class TestTarjaCerca extends PApplet {

    TarjaCerca tc;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestTarjaCerca");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        String[] textos = {"Poema", "Llibre", "Autor", "12", "56"};
        // TarjaCerca(int n, String[] textos, String vers, float x, float y, float w, float h){
        tc = new TarjaCerca(1, textos, "vers", 50, 200, 700, 50);
        tc.setColorsFonts(colors, fonts);
        tc.setBotoDetall();
    }

    public void draw(){
        background(255, 100, 100);
        tc.display(this);
    }

    public void mousePressed(){

    }
}
