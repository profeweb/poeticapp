package gui;

import processing.core.PApplet;

public class BotoToken extends Boto{

    boolean selected;

    float roundness = 5;
    float sizeTextBoto = 16;

    public BotoToken(String text, float x, float y, float w, float h) {
        super(text, x, y, w, h);
        this.selected= false;
    }

    public void toggleSelection(){ this.selected = !this.selected; }

    public void display(PApplet p5){
        p5.pushStyle();
        if(!this.activat){
            p5.fill(colors.getColorPrimari());  // Color desabilitat
        }
        else if(selected){
            p5.fill(colors.getColorTerciari());      // Color quan ratolí a sobre
        }
        else if(mouseDins(p5)){
            p5.fill(colors.getColorBotoFarcimentDins());      // Color quan ratolí a sobre
        }
        else{
            p5.fill(colors.getColorBotoFarcimentFora());          // Color actiu però ratolí fora
        }
        p5.stroke(colors.getColorBotoContorn()); p5.strokeWeight(2);        //Color i gruixa del contorn
        p5.rectMode(p5.CORNER);
        p5.rect(this.x, this.y, this.w, this.h, roundness);    // Rectangle del botó

        // Text (color, alineació i mida)
        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(sizeTextBoto);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2f + sizeTextBoto/2f);
        p5.popStyle();
    }


}
