package gui;

import processing.core.PApplet;

public class PuntDades extends ElementDades {

    public PuntDades(float x, float y, float d) {
        super(x, y, d, d);
    }

    public void display(PApplet p5){

        p5.pushStyle();

        p5.fill(color); p5.stroke(0); p5.strokeWeight(5);
        p5.circle(this.x, this.y, this.w);

        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
        p5.text(this.categoria, this.x, this.y - 50);

        String percentage = p5.nf(this.percentatge, 2, 2);
        p5.fill(255); p5.textAlign(p5.CENTER); p5.textSize(18);
        p5.text(p5.nf(this.valor, 2, 0), this.x, this.y - 30);
        //p5.text(percentage+"%", this.x, this.y - 10);
        p5.popStyle();
    }


}
