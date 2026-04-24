package gui.test;

import gui.Colors;
import gui.EntradaTextLlista;
import gui.Fonts;
import processing.core.PApplet;

public class TestEntradaTextLlista extends PApplet {

    EntradaTextLlista entrada;
    Colors colors;
    Fonts fonts;
    String textEscrit;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestEntradaTextLlista");
    }

    public void settings(){
        size(800, 800);
    }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        String[] opcions = {"Alemania", "Angola", "Belgica", "Dinamarca"};
        entrada = new EntradaTextLlista(opcions, 200, 100, 300, 50);
        entrada.setColorsFonts(colors, fonts);
        textEscrit = "";
    }

    public void draw(){
        background(255);
        entrada.display(this);

        fill(0);
        textSize(24); textAlign(CENTER);
        text(textEscrit,width/2, height/2);
    }

    public void mousePressed(){
        entrada.updateClick(this);
    }

    public void keyPressed(){
        entrada.updateKeyPressed(this);
    }

    public void keyTyped(){

        entrada.updateKeyTyped(this);
    }


}
