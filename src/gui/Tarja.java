package gui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import static gui.Mides.*;

public class Tarja extends GuiElement {

    int num;
    String titol;
    String subtitol;

    PImage imatge;
    float margeEsq = 15;

    BotoFavorit bFavorit;

    public Tarja(float x, float y, float w, float h){
        super(x, y, w, h);
        this.num =  0;
        this.titol = ""; this.subtitol = "";
        this.imatge = null;
    }

    public Tarja(int num, String titol, String subtitol, float x, float y, float w, float h){
        super(x, y, w, h);
        this.num = num;
        this.titol = titol;
        this.subtitol = subtitol;
        this.imatge = null;
    }

    public void setBotoFavorit(){
        this.bFavorit = new BotoFavorit(this.x + this.w - BOTO_FAVORIT/2 - MARGE_FAVORIT, this.y + this.h - BOTO_FAVORIT/2 - MARGE_FAVORIT, BOTO_FAVORIT);
        this.bFavorit.setColors(colors);
        this.bFavorit.setFonts(fonts);
    }

    public void setNum(int n){ this.num = n; }

    public void setTextos(String t1, String t2){
        this.titol= t1;
        this.subtitol = t2;
    }

    public void setImatge(PApplet p5, PImage img){

        // Màscara amb caires arrodonits
        PGraphics mask = p5.createGraphics((int)this.w, (int)this.h);
        mask.beginDraw();
        mask.background(0);
        mask.fill(255);
        mask.rect(0, 0, this.w, this.h, 5);
        mask.endDraw();

        PGraphics inputImage= p5.createGraphics((int)this.w, (int)this.h);
        inputImage.beginDraw();
        inputImage.image(img, 0, 0);
        inputImage.endDraw();

        PImage maskImage = mask.get();
        inputImage.mask(maskImage);

        PGraphics pg = p5.createGraphics((int)this.w, (int)this.h);
        pg.beginDraw();
        pg.image(inputImage, 0,0);
        pg.endDraw();
        this.imatge = pg.get();
    }

    public void display(PApplet p5){

        p5.pushStyle();

        p5.fill(200);
        p5.rect(this.x, this.y, this.w, this.h, 5);

        // Imatge
        if(this.imatge!=null){
            p5.image(this.imatge, this.x, this.y, this.w, this.h);
        }

        // Número
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_NUM);
        p5.text(this.num, this.x + this.margeEsq, this.y + TEXT_NUM / 2f);

        // Text Títol
        p5.fill(0);
        p5.textFont(fonts.getFontPrimaria());
        p5.textSize(TEXT_TITOL);
        p5.text(this.titol, this.x + this.margeEsq, this.y + this.h + TEXT_TITOL/1.25f);

        // Text Subtítol
        p5.fill(50);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_SUBTITOL);
        p5.text(this.subtitol, this.x + this.margeEsq, this.y + this.h + TEXT_TITOL/1.5f + TEXT_SUBTITOL/1.5f);

        // Boto Favorit
        if(this.bFavorit!=null){
            this.bFavorit.display(p5);
        }

        p5.popStyle();
    }

    public void displaySelected(PApplet p5){

        p5.pushStyle();

        p5.fill(200);
        p5.strokeWeight(10);
        p5.rect(this.x, this.y, this.w, this.h, 5);

        // Imatge
        if(this.imatge!=null){
            p5.tint(255, 150, 150);
            p5.image(this.imatge, this.x, this.y, this.w, this.h);
            p5.noTint();
        }

        // Número
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_NUM);
        p5.text(this.num, this.x + this.margeEsq, this.y + TEXT_NUM / 2f);

        // Text Títol
        p5.fill(0);
        p5.textFont(fonts.getFontPrimaria());
        p5.textSize(TEXT_TITOL);
        p5.text(this.titol, this.x + this.margeEsq, this.y + this.h + TEXT_TITOL/1.25f);

        // Text Subtítol
        p5.fill(50);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_SUBTITOL);
        p5.text(this.subtitol, this.x + this.margeEsq, this.y + this.h + TEXT_TITOL/1.5f + TEXT_SUBTITOL/1.5f);

        // Boto Favorit
        if(this.bFavorit!=null){
            this.bFavorit.display(p5);
        }

        p5.popStyle();
    }

    public boolean mouseDins(PApplet p5){
        return this.x < p5.mouseX && p5.mouseX < this.x + this.w &&
                this.y < p5.mouseY && p5.mouseY < this.y + this.h;
    }


}
