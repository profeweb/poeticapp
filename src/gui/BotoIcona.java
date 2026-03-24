package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class BotoIcona extends Boto {

    int codiIcona;

    public BotoIcona(String text, int codiIcona, float x, float y, float w, float h){
        super(text, x, y, w, h);
        this.codiIcona = codiIcona;
    }

    public BotoIcona(PApplet p5, int codiIcona, float x, float y, float w, float h){
        super( null, x, y, w, h);
        this.codiIcona = codiIcona;
    }

    // Dibuixa el botó
    public void display(PApplet p5){

        super.display(p5);

        // Icona
        p5.pushStyle();
        p5.fill(255, 100, 100);
        p5.textFont(this.fonts.getFontEmoji());
        p5.textSize(TEXT_ICONA_BOTO); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(new String(Character.toChars(codiIcona)), this.x + 10, this.y + this.h/2 - 2);
        p5.popStyle();
    }
}
