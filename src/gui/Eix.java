package gui;

import processing.core.PApplet;

public class Eix extends GuiElement {

    int numMarques;
    String llegenda;

    public Eix(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setNumMarques(int n){ this.numMarques = n; }

    public void display(PApplet p5){
        p5.pushStyle();

        // Linia de l'Eix
        p5.stroke(0);
        p5.strokeWeight(1f);
        p5.line(this.x, this.y, this.x + this.w, this.y + this.h);



        // Llegenda
        p5.fill(0);
        p5.textFont(fonts.getFontTerciaria());
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text(this.llegenda, this.x + this.w + 10, this.y + this.h + 10);
        p5.popStyle();
    }
}
