package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class Titol extends GuiElement{

    String titol;

    public Titol(float x, float y, float w, float h, String titol) {
        super(x, y, w, h);
        this.titol = titol;
    }

    public Titol(float x, float y, Colors colors, Fonts fonts, String  titol){
        super(x, y, 0, 0);
        setColorsFonts(colors, fonts);
        this.titol = titol;
    }

    public void setTitol(String titol){ this.titol = titol; }

    public void display(PApplet p5){

        p5.pushStyle();

            p5.fill(0);
            p5.textAlign(p5.LEFT, p5.BOTTOM);
            p5.textFont(fonts.getFontTitular());
            p5.textSize(midaTextTitol);
            p5.text(titol, x, y);

        p5.popStyle();
    }

}
