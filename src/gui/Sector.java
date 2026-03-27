package gui;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Sector extends ElementDades {

    float minAngle, maxAngle, migAngle, distAngle;

    public Sector(float x, float y, float w, float minAngle, float maxAngle) {
        super(x, y, w, w);
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.distAngle = maxAngle - minAngle;
        this.migAngle = (this.maxAngle + this.minAngle) /2f;
    }

    public void display(PApplet p5){

        p5.pushStyle();

            p5.fill(color); p5.stroke(0); p5.strokeWeight(5);
            p5.arc(this.x, this.y, this.w, this.w, this.minAngle, this.maxAngle);

            float textX = this.x + (this.w/2f + 50)*cos(migAngle);
            float textY = this.y + (this.w/2f + 50)*sin(migAngle);
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
            p5.text(this.categoria, textX, textY);

            float percX = this.x + (this.w/4)*cos(migAngle);
            float percY = this.y + (this.w/4)*sin(migAngle);
            String percentage = p5.nf(this.percentatge, 2, 2);
            p5.fill(255); p5.textAlign(p5.CENTER); p5.textSize(18);
            p5.text(percentage+"%", percX, percY);

        p5.popStyle();
    }

}
