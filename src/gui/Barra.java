package gui;

import processing.core.PApplet;

public class Barra extends GuiElement{

    String text;
    float valor, percentatge;

    public Barra(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setPercentatge(float percentatge) {
        this.percentatge = percentatge;
    }

    public void display(PApplet p5){
        p5.pushStyle();

        p5.pop();
    }
}
