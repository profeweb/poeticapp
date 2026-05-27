package gui;

import processing.core.PApplet;

import static processing.core.PApplet.lerpColor;

public class DiagramaBarres extends Diagrama {

    Barra[] barres;

    public enum ORIENTACIO { HORITZONTAL, VERTICAL};
    public ORIENTACIO orientacio;

    public DiagramaBarres(float x, float y, float w, float h) {
        super(x, y, w, h);
        orientacio = ORIENTACIO.VERTICAL;
    }

    public void setOrientacio(ORIENTACIO orientacio){ this.orientacio = orientacio; }

    public void setBarres(){

        barres = new Barra[this.valors.length];

        if(orientacio == ORIENTACIO.VERTICAL) {

            float wBar = w / (float) this.valors.length;

            for (int i = 0; i < barres.length; i++) {

                float hBar = (this.valors[i] / this.maxValor) * h;
                float xBar = this.x + wBar * i;
                float yBar = this.y + this.h - hBar;

                barres[i] = new Barra(xBar, yBar, wBar, hBar);
                barres[i].setValor(this.valors[i]);
                barres[i].setPercentatge(this.percentages[i]);
                barres[i].setCategoria(this.categories[i]);
                barres[i].setColor(colors[i]);
                barres[i].setOrientacio(orientacio);

            }
        }
        else {
            float hBar = h / (float) this.valors.length;

            for (int i = 0; i < barres.length; i++) {

                float wBar = (this.valors[i] / this.maxValor) * w;
                float xBar = this.x;
                float yBar = this.y + hBar * i;

                barres[i] = new Barra(xBar, yBar, wBar, hBar);
                barres[i].setValor(this.valors[i]);
                barres[i].setPercentatge(this.percentages[i]);
                barres[i].setCategoria(this.categories[i]);
                barres[i].setColor(colors[i]);
                barres[i].setOrientacio(orientacio);

            }
        }
    }

    // Dibuixa el Diagrama de Barres

    public void display(PApplet p5){
        for(Barra b : barres){
            b.display(p5);
        }
    }


}
