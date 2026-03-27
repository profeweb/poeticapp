package gui;

import processing.core.PApplet;

public class CasellaSeleccio extends Seleccionable {


    public CasellaSeleccio(float x, float y, float w, float h) {
        super("", x, y, w, h);
    }

    public CasellaSeleccio(String text, float x, float y, float w, float h) {
        super(text, x, y, w, h);
    }

    public void display(PApplet p5){

        p5.pushStyle();

        // Dibuixa el rectangle
        p5.stroke(colors.getColorBotoContorn());
        p5.strokeWeight(2);

        if(this.seleccionat){
            p5.fill(colors.getColorBotoFarcimentDins());
        }
        else{
            p5.fill(colors.getColorBotoFarcimentFora());
        }
        p5.rect(x, y, w, w, 5);

        // Dibuixa la creu
        if(this.seleccionat){
            p5.line(x, y, x + w, y + w);
            p5.line(x, y+w, x + w, y);
        }

        // Dibuixa el text
        p5.textFont(fonts.getFontTerciaria());
        p5.fill(colors.getColorBotoContorn());
        p5.textSize(Mides.TEXT_BOTO);
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.text(textOpcio, this.x - 10, this.y + this.h/2);

        p5.popStyle();
    }

}
