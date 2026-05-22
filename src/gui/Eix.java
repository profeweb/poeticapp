package gui;

import processing.core.PApplet;

public class Eix extends GuiElement {

    int numMarques;
    String llegenda;
    String[] categories;
    float espaiX;

    public Eix(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setLlegenda(String llegenda){ this.llegenda = llegenda; }

    public void setNumMarques(int n){ this.numMarques = n; }

    public void setCategories(String[] categories){ this.categories = categories; }

    public void setEspaiX(float espaiX){ this.espaiX = espaiX; }

    public void display(PApplet p5){
        p5.pushStyle();

        // Linia de l'Eix
        p5.stroke(0);
        p5.strokeWeight(1f);
        p5.line(this.x, this.y, this.x + this.w, this.y);

        // Ticks
        for(int i=0; i< categories.length; i++){
            float xTick = this.x + espaiX*i + espaiX/2;
            p5.line(xTick, y - 5, xTick, y + 5);
            p5.textAlign(p5.CENTER, p5.CENTER);
            p5.text(categories[i], xTick, y + 15);
        }


        // Llegenda
        p5.fill(0);
        p5.textFont(fonts.getFontTerciaria());
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(this.llegenda, this.x + this.w/2, this.y + 30);
        p5.popStyle();
    }
}
