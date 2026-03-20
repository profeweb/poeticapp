package gui;

public abstract class GuiElement {

    float x, y, w, h;
    Colors colors;
    Fonts fonts;

    public GuiElement(float x, float y, float w, float h){
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void setColors(Colors c){ this.colors = c; }

    public void setFonts(Fonts f){ this.fonts = f; }

    public void setX(float x){ this.x = x;}
    public void setY(float y){ this.y = y;}

    public void setW(float w){ this.w = w;}
    public void setH(float h){ this.h = h;}

}
