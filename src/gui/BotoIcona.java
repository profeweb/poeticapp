package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class BotoIcona extends Boto {

    int codiIcona;

    public BotoIcona(PApplet p5, String text, int codiIcona, float x, float y, float w, float h){
        super(text, x, y, w, h);
        this.codiIcona = codiIcona;
    }

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
        p5.pushStyle();
        p5.fill(getColorActual(p5));
        p5.stroke(colors.getColorBotoContorn());
        p5.strokeWeight(GRUIX_BOTO);        //Color i gruixa del contorn
        p5.rect(this.x, this.y, this.w, this.h, 10);    // Rectangle del botó

        // Text (color, alineació i mida)
        if(this.textBoto!=null) {
            p5.fill(0);
            p5.textAlign(p5.CENTER, p5.CENTER);
            p5.textFont(this.fonts.getFontSecundaria());
            p5.textSize(TEXT_BOTO);
            p5.text(this.textBoto, this.x + this.w / 2, this.y + this.h / 2 - 8);
        }

        p5.fill(255, 100, 100);
        p5.textFont(this.fonts.getFontEmoji());
        p5.textSize(TEXT_ICONA_BOTO); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(new String(Character.toChars(codiIcona)), this.x + 10, this.y + this.h/2 - 2);
        p5.popStyle();
    }
}
