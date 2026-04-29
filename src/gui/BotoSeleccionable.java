package gui;

import processing.core.PApplet;

public class BotoSeleccionable extends Boto {

    boolean selected;

    public BotoSeleccionable(String text, float x, float y, float w, float h) {
        super(text, x, y, w, h);
        this.selected = false;
    }

    public void setSelected(boolean b){ this.selected = b; }

    public void toggleSelection(){ this.selected = !this.selected; }

    public void display(PApplet p5){
        p5.pushStyle();
        if(!this.activat){
            p5.fill(colors.getColorSecundari());  // Color desabilitat
        }
        else if(selected){
            p5.fill(colors.getColorPrimari());      // Color quan ratolí a sobre
        }
        else if(mouseDins(p5)){
            p5.fill(colors.getColorBotoFarcimentDins());      // Color quan ratolí a sobre
        }
        else{
            p5.fill(colors.getColorBotoFarcimentFora());          // Color actiu però ratolí fora
        }
        p5.stroke(colors.getColorBotoContorn()); p5.strokeWeight(2);        //Color i gruixa del contorn
        p5.rectMode(p5.CORNER);
        p5.rect(this.x, this.y, this.w, this.h, arrodoniment);    // Rectangle del botó

        // Text (color, alineació i mida)
        p5.fill(0); p5.textAlign(p5.CENTER);
        p5.textFont(fonts.getFontPrimaria());
        p5.textSize(Mides.midaParagraf);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2f + Mides.midaParagraf/2f);
        p5.popStyle();
    }

    public void updateClick(PApplet p5){
        if(mouseDins(p5)){
            toggleSelection();
        }
    }
}
