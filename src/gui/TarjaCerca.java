package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class TarjaCerca extends GuiElement {

    int num;
    String[] textos;
    String vers;
    float margeEsq = 25;

    BotoIcona botoDetall;

    public TarjaCerca(float x, float y, float w, float h){
        super(x, y, w, h);
    }

    public TarjaCerca(int n, String[] textos, String vers, float x, float y, float w, float h){
        super(x, y, w, h);
        this.num = n;
        this.textos = textos;
        this.vers = vers;
    }

    public void setNum(int n){ this.num = n; }

    public void setVers(String v){ this.vers = v; }
    
    public void setTextos(String ... textos){
        this.textos = new String[textos.length];
        for(int i=0; i<textos.length; i++){
            this.textos[i] = textos[i];
        }
    }

    public void setBotoDetall(){
        botoDetall = new BotoIcona(CODI_ICONA_CERCADOR, x + w - h, y +5, h-10, h-10);
        botoDetall.setColorsFonts(colors, fonts);
    }
    
    public void display(PApplet p5){
        p5.pushStyle();
        
        p5.fill(255);
        p5.stroke(50);
        p5.rect(this.x, this.y, this.w, this.h, 5);

        p5.line(this.x + this.h, this.y, this.x + this.h, this.y + this.h);
        
        // Número
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_NIVELL1);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(this.num, this.x + this.h/2, this.y + this.h/2);

        // Textos Detall
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_NIVELL2);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text(this.textos[0]+"    "+ textos[1]+"    "+ textos[2]+"    "+ textos[3]+"    "+ textos[4], this.x + this.h + this.margeEsq, this.y + this.h/2);

        // Text Vers
        p5.fill(50);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_NIVELL3);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text(this.vers, this.x + this.h + this.margeEsq, this.y + 3*this.h/4);

        // Boto
        if(botoDetall!=null){
            botoDetall.display(p5);
        }
        
        p5.popStyle();
    }
    
}
