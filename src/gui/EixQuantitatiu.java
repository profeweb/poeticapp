package gui;

import processing.core.PApplet;

public class EixQuantitatiu extends GuiElement {

    int numMarques;
    String llegenda;
    float maxValor;
    float espaiY;

    public EixQuantitatiu(float x, float y, float h) {
        super(x, y, 0, h);
    }

    public void setLlegenda(String llegenda){ this.llegenda = llegenda; }

    public void setNumMarques(int n){
        this.numMarques = n;
        this.espaiY = this.h / numMarques;
    }

    public void setMaxValor(float maxValor) {
        this.maxValor = maxValor;
    }


    public void display(PApplet p5){
        p5.pushStyle();

        // Linia de l'Eix
        p5.stroke(0);
        p5.strokeWeight(1f);
        p5.line(this.x, this.y, this.x, this.y - this.h);

        // Ticks
        for(int i=0; i< numMarques; i++){
            float yTick = this.y - espaiY*i - espaiY/2;
            p5.line(x-5, yTick, x+5, yTick);
            p5.textAlign(p5.CENTER, p5.CENTER);
            //p5.text(categories[i], xTick, y + 15);
        }


        // Llegenda
        p5.fill(0);
        p5.textFont(fonts.getFontTerciaria());
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(this.llegenda, this.x , this.y - this.h  - 50);
        p5.popStyle();
    }
}
