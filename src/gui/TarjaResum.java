package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class TarjaResum extends GuiElement {
    
    String[] textos;
    float margeEsq = 25;
    boolean textLlarg = false;

    public TarjaResum(float x, float y, float w, float h){
        super(x, y, w, h);
        this.textos = new String[3];
    }
    
    public TarjaResum(String[] textos, float x, float y, float w, float h){
        super(x, y, w, h);
        this.textos = textos;
    }
    
    public void setTextos(String t1, String t2, String t3){
        this.textos[0] = t1;
        this.textos[1] = t2;
        this.textos[2] = t3;
    }

    public void setTextLlarg(boolean b){ this.textLlarg = b; }
    
    public void display(PApplet p5){
        p5.pushStyle();
        
        p5.fill(255);
        p5.rect(this.x, this.y, this.w, this.h, 5);
        
        // Text nivell 2
        p5.fill(0);
        p5.textFont(fonts.getFontResumNivell2());
        p5.textSize(TEXT_NIVELL2);
        p5.textAlign(p5.LEFT, p5.TOP);
        p5.text(this.textos[0], this.x + this.margeEsq, this.y + 5);

        // Text nivell 1
        p5.fill(0);
        p5.textFont(fonts.getFontResumNivell1());
        p5.textSize(textLlarg ? TEXT_NIVELL1_LLARG: TEXT_NIVELL1);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(this.textos[1], this.x + this.margeEsq, this.y + this.h/2);

        // Text nivell 3
        p5.fill(50);
        p5.textFont(fonts.getFontResumNivell3());
        p5.textSize(TEXT_NIVELL3);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text(this.textos[2], this.x + this.margeEsq, this.y + this.h - 5);
        
        p5.popStyle();
    }
    
}
