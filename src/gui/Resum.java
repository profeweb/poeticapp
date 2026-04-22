package gui;

import processing.core.PApplet;

public class Resum extends GuiElement{


    TarjaResum[] resums;
    int numTarges;
    float wResum;
    float margeHoritzontal = 50;

    public Resum(int numTarges, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.numTarges = numTarges;
        this.wResum = (w - (numTarges-1)*margeHoritzontal) / numTarges;
        this.resums = new TarjaResum[numTarges];
    }

    public void setResum(int numResum, String titolResum, String valorResum, String detallResum){
        resums[numResum] = new TarjaResum(x + numResum*(wResum + margeHoritzontal), y, wResum, h);
        resums[numResum].setFonts(fonts);
        resums[numResum].setColors(colors);
        resums[numResum].setTextos(titolResum, valorResum, detallResum);
    }

    public void display(PApplet p5){
        for(TarjaResum r: resums){
            r.display(p5);
        }
    }
}
