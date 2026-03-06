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

}
