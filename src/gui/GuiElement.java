package gui;

import processing.core.PApplet;

public abstract class GuiElement {

    float x, y, w, h;
    Colors colors;
    Fonts fonts;

    public GuiElement(float x, float y, float w, float h){
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void setColorsFonts(Colors c, Fonts f){
        this.colors = c;
        this.fonts = f;
    }

    public void setColors(Colors c){ this.colors = c; }

    public void setFonts(Fonts f){ this.fonts = f; }

    public void setX(float x){ this.x = x;}

    public void setY(float y){ this.y = y;}

    public void setW(float w){ this.w = w;}

    public void setH(float h){ this.h = h;}

    // Indica si el cursor està sobre el botó
    public boolean mouseDins(PApplet p5){
        return (p5.mouseX >= this.x) && (p5.mouseX <= this.x + this.w) &&
                (p5.mouseY >= this.y) && (p5.mouseY <= this.y + this.h);
    }

}
