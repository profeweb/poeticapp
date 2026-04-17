package gui;

import processing.core.PApplet;

import static gui.Mides.GRUIX_BOTO;
import static gui.Mides.TEXT_BOTO;

public class Boto extends GuiElement {

    // Text del botó
    String textBoto;

    // Estat del botó (actiu / inactiu).
    boolean activat;

    // Constructor
    public Boto(String text, float x, float y, float w, float h){
        super(x, y, w, h);
        this.textBoto = text;
        this.activat = true;
    }

    // Setters

    public void setActivat(boolean b){
        this.activat = b;
    }

    public void setTextBoto(String t){ this.textBoto = t; }

    // Getters

    public boolean isActivat(){
        return  this.activat;
    }

    // Color del boto
    public int getColorActual(PApplet p5){
        if(!activat){
            return colors.getColorBotoFarcimentDesactivat();  // Color desabilitat
        }
        else if(mouseDins(p5)){
            return colors.getColorBotoFarcimentDins();      // Color quan ratolí a sobre
        }
        else{
            return colors.getColorBotoFarcimentFora();    // Color actiu però ratolí fora
        }
    }

    //
    public void toggleActivat(){ this.activat = !this.activat; }

    // Dibuixa el botó
    public void display(PApplet p5){
        p5.pushStyle();

        // Rectangle del botó (color, gruixa del contorn)
        p5.fill(getColorActual(p5));
        p5.stroke(colors.getColorBotoContorn());
        p5.strokeWeight(GRUIX_BOTO);
        p5.rect(this.x, this.y, this.w, this.h, 10);

        // Text (color, alineació i mida)
        if(textBoto!=null) {
            p5.fill(colors.getColorBotoText());
            p5.textAlign(p5.CENTER);
            p5.textFont(fonts.getFontSecundaria());
            p5.textSize(TEXT_BOTO);
            p5.text(textBoto, this.x + this.w / 2, this.y + this.h / 2 + 10);
        }

        p5.popStyle();
    }

    // Indica si cal posar el cursor a HAND
    public boolean updateCursor(PApplet p5){
        return mouseDins(p5) && isActivat();
    }

}
