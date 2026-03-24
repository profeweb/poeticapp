package gui;

import processing.core.PApplet;

import static gui.Mides.TEXT_ENTRADA;
import static processing.core.PConstants.BACKSPACE;

public class EntradaText extends GuiElement {

    // Text del camp
    String textEtiqueta = "";
    String text = "";

    // Estat del camp de text
    boolean actiu = false;

    // Constructor

    public EntradaText(String textEtiqueta, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.textEtiqueta = textEtiqueta;
    }

    public EntradaText(String textEtiqueta, float x, float y, float w, float h, Colors colors, Fonts fonts) {
        super(x, y, w, h);
        this.textEtiqueta = textEtiqueta;
        setFonts(fonts);
        setColors(colors);
    }

    // Setters

    // Setter del text
    public void setTextEtiqueta(String t){
        this.textEtiqueta= t;
    }

    public void setText(String t){
        this.text= t;
    }

    // Dibuixa el Camp de Text
    public void display(PApplet p5) {
        p5.pushStyle();

        // Rectangle
        p5.stroke(colors.getColorEntradaTextContorn());
        p5.strokeWeight(Mides.GRUIXA_ENTRADA_TEXT);
        if(!actiu){
            p5.fill(colors.getColorEntradaTextDesactivat());
        }
        else {
            p5.fill(colors.getColorEntradaTextActivat());
        }
        p5.rect(x, y, w, h, 5);

        // Text
        p5.fill(colors.getColorEntradaTextText());
        p5.textSize(TEXT_ENTRADA);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.text(text, x + 5, y + h - TEXT_ENTRADA);

        // Etiqueta
        p5.fill(colors.getColorEntradaTextText());
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.text(textEtiqueta, x - 15, y + h - TEXT_ENTRADA);
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

    // Gestiona tecles especials
    public void updateKeyPressed(int keyCode) {
        if (!actiu) return;

        if (keyCode == BACKSPACE) {
            esborrarText();
        }
    }

    // Gestiona entrada de text real (inclou accents)
    public void updateKeyTyped(char key) {
        if (!actiu) return;

        // Evita caracteres de control
        if (key == '\n' || key == '\r' || key == '\b') return;

        afegirText(key);
    }
}
