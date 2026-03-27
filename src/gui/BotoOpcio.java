package gui;

import processing.core.PApplet;

public class BotoOpcio extends Seleccionable {

    public BotoOpcio(float x, float y, float d) {
        super(x, y, d, d);
    }

    public void display(PApplet p5){

        p5.pushStyle();

        // Dibuixa el cercle
        p5.stroke(colors.getColorBotoContorn());
        p5.strokeWeight(2);

        if(this.seleccionat){
            p5.fill(colors.getColorBotoFarcimentDins());
        }
        else{
            p5.fill(colors.getColorBotoFarcimentFora());
        }
        p5.circle(x, y, w);

        if(this.seleccionat){
            p5.fill(colors.getColorBotoContorn());
            p5.noStroke();
            p5.circle(x, y, 1.5f*w);
        }

        // Dibuixa el text
        p5.textFont(fonts.getFontTerciaria());
        p5.fill(colors.getColorBotoContorn());
        p5.textSize(Mides.TEXT_BOTO);
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.text(textOpcio, this.x - 10, this.y + this.h/2);

        p5.popStyle();
    }

    public boolean mouseDins(PApplet p5){
        return p5.dist(p5.mouseX, p5.mouseY, this.x, this.y) < this.w/2f;
    }
}
