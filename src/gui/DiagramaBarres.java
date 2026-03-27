package gui;

import processing.core.PApplet;

public class DiagramaBarres extends Diagrama {

    Barra[] barres;
    float wBar;

    public DiagramaBarres(float x, float y, float w, float h) {
        super(x, y, w, h);
    }


    public void setBarres(){

        barres = new Barra[this.values.length];

        wBar = w / (float) this.values.length;

        for(int i=0; i<barres.length; i++){

            float hBar = (this.values[i] / this.total)*h;
            float xBar = this.x + wBar *i;
            float yBar = this.y + this.h - hBar;

            barres[i] = new Barra(xBar, yBar, wBar, hBar);
            barres[i].setValor(this.values[i]);
            barres[i].setPercentatge(this.percentages[i]);
            barres[i].setText(this.texts[i]);

        }
    }

    // Dibuixa el Diagrama de Barres

    public void display(PApplet p5){
        p5.pushStyle();

        for(int i=0; i<barres.length; i++){

        }

        for(int i=0; i<this.values.length; i++){

            float hBar = (this.values[i] / this.total)*h;
            float xBar = this.x + wBar *i;
            float yBar = this.y + this.h - hBar;

            p5.fill(colors[i]); p5.stroke(0); p5.strokeWeight(5);
            p5.rect(xBar, yBar, wBar, hBar);

            float textX = xBar + wBar /2;
            float textY = this.y + this.h + 50;
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
            p5.text(this.texts[i], textX, textY);

            float percX = xBar + wBar /2;
            float percY = this.y + this.h - hBar - 50;
            String percentage = p5.nf(this.percentages[i], 2, 2);
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(18);
            p5.text(percentage+"%", percX, percY);

            p5.textSize(24);
            p5.text((int)this.values[i], percX, percY - 30);

        }
        p5.popStyle();
    }


}
