package gui;

import processing.core.PApplet;

public class BotoToken extends BotoSeleccionable{

    public BotoToken(String text, float x, float y, float w, float h) {
        super(text, x, y, w, h);
    }

    public void updateClick(PApplet p5){
        if(mouseDins(p5)){
            toggleSelection();
            p5.println("TOKEN: "+ this.textBoto);
        }
    }


}
