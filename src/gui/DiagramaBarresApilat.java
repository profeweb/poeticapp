package gui;

import processing.core.PApplet;

public class DiagramaBarresApilat extends DiagramaApilat{

    BarraApilada[][] barres;
    float wBar;

    public DiagramaBarresApilat(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBarres(){

        barres = new BarraApilada[this.valors.length][this.valors[0].length];

        wBar = w / (float) this.valors.length;

        for(int pila=0; pila<piles.length; pila++){
            float yAcumuladaPila = 0;
            for(int categoria=0; categoria<categories.length; categoria++) {

                float hBar = (this.valors[pila][categoria] / this.maxValor) * h;
                float xBar = this.x + wBar * pila;
                float yBar = this.y + this.h - yAcumuladaPila - hBar;

                barres[pila][categoria] = new BarraApilada(xBar, yBar, wBar, hBar);
                barres[pila][categoria].setValor(this.valors[pila][categoria]);
                barres[pila][categoria].setPercentatge(this.percentages[pila][categoria]);
                barres[pila][categoria].setCategoria(this.categories[categoria]);
                barres[pila][categoria].setColor(colorsCategories[categoria]);

                yAcumuladaPila += hBar;
            }

        }
    }

    // Dibuixa el Diagrama de Barres

    public void display(PApplet p5){
        for(int pila = 0; pila<piles.length; pila++){
            for(int categoria=0; categoria<categories.length; categoria++) {
                barres[pila][categoria].display(p5);
            }

            p5.pushStyle();
            p5.textSize(24); p5.fill(0); p5.textAlign(p5.CENTER);
            p5.text(piles[pila], this.x + wBar/2f + wBar*pila, this.y + this.h + 25);
            p5.popStyle();
        }


    }
}
