package gui;

import processing.core.PApplet;

public class DiagramaLinies extends Diagrama {

    PuntDades[] punts;
    Eix eixHoritzontal;
    float espaiX;

    public DiagramaLinies(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setPunts(){

        punts = new PuntDades[this.valors.length];

        espaiX = w / (float) this.valors.length;

        for(int i=0; i<punts.length; i++){

            float yPunt = this.y + this.h - (this.valors[i] / maxValor) * h;;
            float xPunt = this.x + espaiX*i + espaiX/2;

            punts[i] = new PuntDades(xPunt, yPunt, 10);
            punts[i].setValor(this.valors[i]);
            punts[i].setPercentatge(this.percentages[i]);
            punts[i].setCategoria(this.categories[i]);
            punts[i].setColor(colors[0]);

        }
    }

    public void setEixHoritzontal(String titolEix){
        eixHoritzontal = new Eix(x, y + h, w, h);
        eixHoritzontal.setLlegenda(titolEix);
        eixHoritzontal.setFonts(fonts);
        eixHoritzontal.categories = this.categories;
        eixHoritzontal.espaiX = this.espaiX;
    }

    // Dibuixa el Diagrama de Línies

    public void display(PApplet p5){

        displayLinies(p5, colors[0], 1.5f);

        for(PuntDades pd: punts){
            pd.display(p5);
        }

        // Eix qualitatiu
        eixHoritzontal.display(p5);

        // Eix quantitatiu

    }

    public void displayLinies(PApplet p5, int colorLinia, float gruixaLinia){

        p5.pushStyle();
        p5.stroke(colorLinia);
        p5.strokeWeight(gruixaLinia);
        for(int i=0; i<punts.length -1; i++){
            p5.line(punts[i].x, punts[i].y, punts[i+1].x, punts[i+1].y);
        }

        p5.popStyle();
    }

}
