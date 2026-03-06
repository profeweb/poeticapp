package gui;

import processing.core.PApplet;

import static gui.Mides.GRUIX_BOTO;
import static gui.Mides.TEXT_BOTO;

public class Boto extends GuiElement {

    // Colors del botó
    int colorContorn, colorFarciment, colorFarcimentActiu, colorFarcimentDesactivat;

    // Text del botó
    String textBoto;  // Text

    // Estat del botó (actiu / inactiu).
    boolean activat;

    // Constructor
    public Boto(PApplet p5, String text, float x, float y, float w, float h){
        super(x, y, w, h);
        this.textBoto = text;
        this.activat = true;
        this.colorFarciment = p5.color(155, 55, 155);
        this.colorFarcimentActiu = p5.color(255, 55, 155);
        this.colorFarcimentDesactivat = p5.color(150);
        this.colorContorn = p5.color(0);
    }

    // Setters

    public void setActivat(boolean b){
        this.activat = b;
    }

    public void setTextBoto(String t){ this.textBoto = t; }

    public void setColors(int cContorn, int cFarciment, int cActiu, int cDesactivat){
        this.colorFarciment = cFarciment;
        this.colorContorn = cContorn;
        this.colorFarcimentActiu = cActiu;
        this.colorFarcimentDesactivat = cDesactivat;
    }

    public void setColors(Colors c){
        this.colorFarciment = c.getColorAt(0);
        this.colorContorn = c.getColorAt(1);
        this.colorFarcimentActiu = c.getColorAt(2);
        this.colorFarcimentDesactivat = c.getColorAt(3);
    }

    // Getters

    public boolean isActivat(){
        return  this.activat;
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
        p5.fill(0); p5.textAlign(p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_BOTO);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2 + 10);
        p5.popStyle();
    }

    // Indica si el cursor està sobre el botó
    public boolean mouseDins(PApplet p5){
        return (p5.mouseX >= this.x) && (p5.mouseX <= this.x + this.w) &&
                (p5.mouseY >= this.y) && (p5.mouseY <= this.y + this.h);
    }

    // Indica si cal posar el cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseDins(p5) && activat;
    }

}
