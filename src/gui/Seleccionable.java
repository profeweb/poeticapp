package gui;

import processing.core.PApplet;

public class Seleccionable extends GuiElement {

    String textOpcio;
    boolean seleccionat;

    public Seleccionable(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.seleccionat = false;
        this.textOpcio = "";
    }

    public Seleccionable(String text, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.seleccionat = false;
        this.textOpcio = text;
    }

    public void setSeleccionat(boolean b){ this.seleccionat = b; }

    public void toggleSeleccionat(){ this.seleccionat = !this.seleccionat; }

    public boolean isSeleccionat(){ return  this.seleccionat; }

    public void display(PApplet p5){

        p5.pushStyle();

        // Dibuixa el rectangle
        p5.stroke(colors.getColorBotoContorn());
        p5.strokeWeight(2);

        if(this.seleccionat){
            p5.fill(colors.getColorBotoFarcimentDins());
        }
        else{
            p5.fill(colors.getColorBotoFarcimentFora());
        }
        p5.rect(x, y, w, w, 5);

        // Dibuixa la creu
        if(this.seleccionat){
            p5.noStroke();
            p5.fill(colors.getColorBotoContorn());
            p5.rect(x + 5, y + 5, w-10, w-10, 5);
        }

        // Dibuixa el text
        p5.textFont(fonts.getFontTerciaria());
        p5.fill(colors.getColorBotoContorn());
        p5.textSize(Mides.TEXT_BOTO);
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.text(textOpcio, this.x - 10, this.y + this.h/2);

        p5.popStyle();
    }
}
