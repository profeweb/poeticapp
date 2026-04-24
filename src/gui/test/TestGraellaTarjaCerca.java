package gui.test;

import gui.Colors;
import gui.Fonts;
import gui.GraellaTarjaCerca;
import gui.TarjaCerca;
import processing.core.PApplet;

public class TestGraellaTarjaCerca extends PApplet {

    GraellaTarjaCerca gtc;
    TarjaCerca tc;
    Colors colors;
    Fonts fonts;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestGraellaTarjaCerca");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        String[][] textos = {
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},

        };

        gtc = new GraellaTarjaCerca(10, 1, 50, 100, 700, 600);
        gtc.setColorsFonts(colors, fonts);
        gtc.setData(textos);
        gtc.setTitol("Resultats cerca");
        gtc.setTarges(this);
        gtc.setBotons(this);

    }

    public void draw(){
        background(255, 100, 100);
        gtc.display(this);
    }

    public void mousePressed(){

    }
}
