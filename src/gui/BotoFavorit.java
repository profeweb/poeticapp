package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class BotoFavorit extends GuiElement {

    boolean favorit;

    public BotoFavorit(float x, float y, float w){
        super(x, y, w, w);
        this.favorit = false;
    }

    // Setters

    public void setFavorit(boolean b) {
        this.favorit = b;
    }

    public void toggleFavorit() {
        this.favorit = !this.favorit;
    }

    // Dibuixa el botó
    public void display(PApplet p5) {

        p5.pushStyle();
        p5.noStroke();
        p5.fill(colors.getColorPrimari());
        p5.circle(this.x, this.y, this.w);
        p5.textFont(this.fonts.getFontEmoji());
        p5.textSize(this.w); p5.textAlign(p5.CENTER, p5.CENTER);
        p5.fill(255);
        if (this.favorit) {
            p5.text(new String(Character.toChars(CODI_FAVORIT)), this.x , this.y-6);
        } else {
            p5.text(new String(Character.toChars(CODI_NO_FAVORIT)), this.x, this.y-6);
        }
        p5.popStyle();
    }

    // Indica si el cursor està sobre el botó
    public boolean mouseDins(PApplet p5) {
        return p5.dist(p5.mouseX, p5.mouseY, this.x, this.y)<= this.w/2;
    }

    // Canvia l'estat de favorit en fer click a sobre
    public void clickFavorit(PApplet p5){
        if(mouseDins(p5)){
            toggleFavorit();
        }
    }

}
