package gui;

import processing.core.PApplet;

public class DiagramaLiniesApilat extends DiagramaApilat{

    PuntDades[][] punts;

    public DiagramaLiniesApilat(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setPunts(){

        punts = new PuntDades[this.categories.length][this.piles.length];

        float espaiX = w / (float) this.piles.length;

        for(int categoria=0; categoria<categories.length; categoria++) {

            for(int pila=0; pila<piles.length; pila++){

                float yPunt = this.y + this.h - (this.valors[categoria][pila] / maxValor) * h;
                float xPunt = this.x + espaiX * pila + espaiX / 2;

                punts[categoria][pila] = new PuntDades(xPunt, yPunt, 10);
                punts[categoria][pila].setValor(this.valors[categoria][pila]);
                punts[categoria][pila].setPercentatge(this.percentages[categoria][pila]);
                punts[categoria][pila].setCategoria(this.categories[categoria]);
                punts[categoria][pila].setColor(colorsCategories[categoria]);
            }

        }
    }

    // Dibuixa el Diagrama de Línies

    public void display(PApplet p5){

        displayLinies(p5, colorsCategories, 1.5f);

        for(int categoria=0; categoria<categories.length; categoria++) {
            for(int pila=0; pila<piles.length; pila++){
                punts[categoria][pila].display(p5, pila>0);
            }
        }

        // Eix qualitatiu

        // Eix quantitatiu

    }

    public void displayLinies(PApplet p5, int[] colorLinia, float gruixaLinia){

        p5.pushStyle();

        p5.strokeWeight(gruixaLinia);
        for(int categoria=0; categoria<categories.length; categoria++) {
            p5.stroke(colorLinia[categoria]);
            for (int pila = 0; pila < piles.length - 1; pila++) {
                p5.line(punts[categoria][pila].x, punts[categoria][pila].y, punts[categoria][pila+1].x, punts[categoria][pila+1].y);
            }
        }

        p5.popStyle();
    }
}
