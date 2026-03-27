package gui;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TWO_PI;

public class DiagramaSectors extends Diagrama {


    public DiagramaSectors(float x, float y, float w) {
        super(x, y, w, w);
    }

    // Dibuixa el Diagrama de Sectors

    public void display(PApplet p5){

        p5.pushStyle();

        float angStart = 0;
        for(int i=0; i<this.values.length; i++){

            float sectorValue = (this.values[i] / this.total)*TWO_PI;
            float angEnd = angStart + sectorValue;

            p5.fill(colors[i]); p5.stroke(0); p5.strokeWeight(5);
            p5.arc(this.x, this.y, this.w, this.w, angStart, angEnd);

            float textX = this.x + (this.w/2f + 50)*cos((angStart+angEnd)/2f);
            float textY = this.y + (this.w/2f + 50)*sin((angStart+angEnd)/2f);
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
            p5.text(this.texts[i], textX, textY);

            float percX = this.x + (this.w/4)*cos((angStart+angEnd)/2f);
            float percY = this.y + (this.w/4)*sin((angStart+angEnd)/2f);
            String percentage = p5.nf(this.percentages[i], 2, 2);
            p5.fill(255); p5.textAlign(p5.CENTER); p5.textSize(18);
            p5.text(percentage+"%", percX, percY);

            angStart = angEnd;
        }
        p5.popStyle();
    }
}
