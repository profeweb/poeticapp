package gui;

import processing.core.PApplet;

public class BotonsGrup extends GuiElement {

    Boto[] botons;

    int botoSeleccionat = 0;

    public BotonsGrup(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBotons(PApplet p5, String[] titols){
        botons = new Boto[titols.length];
        for (int i = 0; i< botons.length; i++) {
            botons[i] = new Boto(titols[i], x + (w+10)*i, y, w, h);
            botons[i].setActivat(true);
            botons[i].setColors(colors);
            botons[i].setFonts(fonts);
        }
        botons[botoSeleccionat].setActivat(false);
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        for (int i = 0; i< botons.length; i++) {
            botons[i].display(p5);
        }
        p5.popStyle();
    }

    public int getBotoActivat() {
        return (this.botoSeleccionat + 1);
    }

    public void updateBotons() {
        for (int i = 0; i< botons.length; i++) {
            botons[i].activat = (botoSeleccionat != i);
        }
    }

    public void clickBotons(PApplet p5) {
        for (int i = 0; i< botons.length; i++) {
            if (botons[i].mouseDins(p5) && botons[i].activat) {
                botoSeleccionat = i;
                updateBotons();
                break;
            }
        }
    }

    public boolean mouseDins(PApplet p5) {
        for (int i = 0; i< botons.length; i++) {
            if (botons[i].mouseDins(p5) && botons[i].activat) {
                return true;
            }
        }
        return false;
    }
}
