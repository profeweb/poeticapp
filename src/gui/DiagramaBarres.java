package gui;

import processing.core.PApplet;

public class DiagramaBarres extends Diagrama {

    Barra[] barres;
    float wBar;

    public DiagramaBarres(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBarres(){

        barres = new Barra[this.valors.length];

        wBar = w / (float) this.valors.length;

        for(int i=0; i<barres.length; i++){

            float hBar = (this.valors[i] / this.maxValor) * h;
            float xBar = this.x + wBar *i;
            float yBar = this.y + this.h - hBar;

            barres[i] = new Barra(xBar, yBar, wBar, hBar);
            barres[i].setValor(this.valors[i]);
            barres[i].setPercentatge(this.percentages[i]);
            barres[i].setCategoria(this.categories[i]);
            barres[i].setColor(colors[i]);

        }
    }

    // Dibuixa el Diagrama de Barres

    public void display(PApplet p5){
        p5.pushStyle();

        for(int i=0; i<barres.length; i++){
            barres[i].display(p5);
        }
        p5.popStyle();
    }


}
