package gui;

import processing.core.PApplet;

public class Barra extends ElementDades{

    public Barra(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void display(PApplet p5){

        p5.pushStyle();

        p5.fill(color);
        p5.stroke(0);
        p5.strokeWeight(5);
        p5.rect(this.x, this.y, this.w, this.h);

        float textX = this.x + this.w/2;
        float textY = this.y + this.h + 30;
        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
        p5.text(this.categoria, textX, textY);

        float percX = this.x + this.w/2;
        float percY = this.y - 20;
        String percentage = p5.nf(this.percentatge, 2, 2);
        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(18);
        p5.text(percentage+"%", percX, percY);

        p5.textSize(24);
        p5.text((int)this.valor, percX, percY - 20);

        p5.popStyle();
    }
}
