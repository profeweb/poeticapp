package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class BotoIcona extends Boto {

    int codiIcona;

    public BotoIcona(PApplet p5, String text, int codiIcona, float x, float y, float w, float h){
        super(p5, text, x, y, w, h);
        this.codiIcona = codiIcona;
    }

    // Dibuixa el botó
    public void display(PApplet p5){
        p5.pushStyle();
        if(!activat){
            p5.fill(colorFarcimentDesactivat);  // Color desabilitat
        }
        else if(mouseDins(p5)){
            p5.fill(colorFarcimentActiu);      // Color quan ratolí a sobre
        }
        else{
            p5.fill(colorFarciment);          // Color actiu però ratolí fora
        }
        p5.stroke(colorContorn);
        p5.strokeWeight(GRUIX_BOTO);        //Color i gruixa del contorn
        p5.rect(this.x, this.y, this.w, this.h, 10);    // Rectangle del botó

        // Text (color, alineació i mida)
        p5.fill(0); p5.textAlign(p5.CENTER, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_BOTO);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2 - 8);

        p5.fill(255, 100, 100);
        p5.textFont(fonts.getFontEmoji());
        p5.textSize(TEXT_ICONA_BOTO); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(new String(Character.toChars(codiIcona)), this.x + 10, this.y + this.h/2 - 2);
        p5.popStyle();
    }
}
