package gui;

import processing.core.PApplet;

public class Titulars extends GuiElement {

    String [] titulars;
    float stepY = 25;

    public Titulars(float x, float y){
        super(x, y, 0, 0);
    }

    public Titulars(float x, float y, float w, float h, String ... titulars){
        super(x, y, w, h);
        this.titulars = new String[titulars.length];
        for(int i=0; i<titulars.length; i++){
            this.titulars[i] = titulars[i];
        }
    }

    public void setTitulars(String ... titulars){
        this.titulars = new String[titulars.length];
        for(int i=0; i<titulars.length; i++){
            this.titulars[i] = titulars[i];
        }
    }

    public void setTitularAt(int i, String titular){ this.titulars[i] = titular; }

    public String getTitularAt(int i){ return this.titulars[i]; }

    public void display(PApplet p5){

        p5.pushStyle();

        for(int i=0; i<titulars.length; i++){
            p5.fill(0);
            p5.textAlign(p5.LEFT);
            p5.textFont(fonts.getFontAt(i));
            p5.textSize(Mides.midaSubtitol);
            p5.text(titulars[i], x, y + i*stepY);
        }

        p5.popStyle();
    }

}
