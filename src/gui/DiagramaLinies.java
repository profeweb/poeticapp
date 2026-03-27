package gui;

import processing.core.PApplet;

public class DiagramaLinies extends Diagrama {

    public DiagramaLinies(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    // Dibuixa el Diagrama de Línies

    public void display(PApplet p5){

        p5.pushStyle();

        p5.stroke(0); p5.strokeWeight(2);
        p5.line(this.x, this.y, this.x, this.y + this.h);
        p5.line(this.x, this.y + this.h, this.x +  this.w, this.y + this.h);

        float widthBar = w / (float) this.values.length;

        for(int i=0; i<this.values.length-1; i++){

            // Posició Mes i
            float barValue1 = this.y + this.h - p5.map(this.values[i], 0, maxValue, 0, h-50);
            float xBar1 = this.x + widthBar*i + widthBar/2;

            // Posició Mes i+1
            float barValue2 = this.y + this.h - p5.map(this.values[i+1], 0, maxValue, 0, h-50);
            float xBar2 = this.x + widthBar*(i+1) + widthBar/2;

            // Linia de mesos i a i+1
            p5.stroke(colors[0]); p5.strokeWeight(2);
            p5.line(xBar1, barValue1, xBar2, barValue2);

            // Quadradet del mes i
            p5.noStroke();
            p5.fill(colors[1]); p5.rectMode(p5.CENTER);
            p5.rect(xBar1, barValue1, 10, 10);

            //
            float textY = this.y + this.h + 50;
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
            p5.text(this.texts[i], xBar1, textY);

            p5.textSize(24);
            p5.text((int)this.values[i], xBar1, barValue1 - 20);

            if(i+1==this.values.length-1){
                p5.text(this.texts[i+1], xBar2, textY);
                p5.text((int)this.values[i+1], xBar2, barValue2 - 20);

                p5.noStroke();
                p5.fill(colors[1]); p5.rectMode(p5.CENTER);
                p5.rect(xBar2, barValue2, 10, 10);
            }

        }
        p5.popStyle();
    }

}
