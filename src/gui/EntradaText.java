package gui;

import processing.core.PApplet;

import static processing.core.PConstants.BACKSPACE;

public class EntradaText {

    // Propietats del camp d'entrada de text
    int x, y, h, w;

    // Colors
    int colorFarciment, colorText, colorFarcimentActiu, colorContorn;
    int gruixaContorn;

    // Text del camp
    String text = "";
    int midaText;

    // Estat del camp de text
    boolean actiu = false;

    // Constructor
    public EntradaText(PApplet p5, int x, int y, int w, int h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.colorFarciment = p5.color(140, 140, 140);
        this.colorText = p5.color(0, 0, 0);
        this.colorFarcimentActiu = p5.color(190, 190, 60);
        this.colorContorn = p5.color(30, 30, 30);
        this.gruixaContorn = 1;
        this.midaText = 24;
    }

    // Setters

    public void setColors(int cContorn, int cFarciment, int cActiu, int cText){
        this.colorFarciment = cFarciment;
        this.colorContorn = cContorn;
        this.colorFarcimentActiu = cActiu;
        this.colorText = cText;
    }

    // Setter del text
    public void setText(String t){
        this.text= t;
    }

    // Dibuixa el Camp de Text
    public void display(PApplet p5) {
        p5.pushStyle();
        if (actiu) {
            p5.fill(colorFarcimentActiu);
        } else {
            p5.fill(colorFarciment);
        }

        p5.strokeWeight(gruixaContorn);
        p5.stroke(colorContorn);
        p5.rect(x, y, w, h, 5);

        p5.fill(colorText);
        p5.textSize(midaText); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(text, x + 5, y + h - midaText);
        p5.popStyle();
    }

    // Afegeix la lletra c al final del text
    public void afegirText(char c) {
        this.text += c;
    }

    // Lleva la darrera lletra del text
    public void esborrarText() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
    }

    // Lleva tot el text
    public void esborrraTotText(){
        this.text = "";
    }

    // Retorna el text
    public String getText(){
        return this.text;
    }


    // Indica si el ratolí està sobre el camp de text
    public boolean mouseDins(PApplet p5) {
        return (p5.mouseX >= this.x && p5.mouseX <= this.x + this.w &&
                p5.mouseY >= this.y && p5.mouseY <= this.y + this.h);
    }

    // Selecciona el camp de text si pitjam a sobre
    // Deselecciona el camp de text si pitjam a fora
    public void updateClick(PApplet p5) {
        if (mouseDins(p5)) {
            actiu = true;
        } else {
            actiu = false;
        }
    }

    // Afegeix i/o lleva el text que es tecleja
    public void updateKey(PApplet p5){
        if (actiu) {
            if (p5.keyCode == (int)BACKSPACE) {
                esborrarText();
            } else if (p5.keyCode == 32) {
                afegirText(' '); // SPACE
            } else {
                afegirText(p5.key);
            }
        }
    }
}
