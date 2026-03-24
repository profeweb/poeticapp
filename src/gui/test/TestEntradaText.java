package gui.test;

import gui.Colors;
import gui.EntradaText;
import gui.Fonts;
import processing.core.PApplet;

public class TestEntradaText extends PApplet {

    EntradaText entrada;
    Colors colors;
    Fonts fonts;
    String textEscrit;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestEntradaText");
    }

    public void settings(){
        size(800, 800);
    }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);
        entrada = new EntradaText("USER:", 200, 100, 300, 50);
        entrada.setColors(colors);
        entrada.setFonts(fonts);
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
        entrada.updateKeyPressed(keyCode);
    }

    public void keyTyped(){
        entrada.updateKeyTyped(key);
    }


}
