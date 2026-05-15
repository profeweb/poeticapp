package gui;

import processing.core.PApplet;
import processing.core.PFont;

import static gui.Mides.*;

public class Fonts {

    // Array de tipografies
    PFont[] fonts;

    // Constructor de les Fonts de l'App
    public Fonts(PApplet p5){
        this.setFonts(p5);
    }

    // Estableix les fonts de l'App
    public void setFonts(PApplet p5){
        this.fonts = new PFont[6];
        this.fonts[0] = p5.createFont("data/fonts/Roboto.ttf", 250);
        this.fonts[1] = p5.createFont("data/fonts/OpenSans.ttf", 250);
        this.fonts[2] = p5.createFont("data/fonts/OpenSans-Italic.ttf", 250);
        this.fonts[3] = p5.createFont("data/fonts/Astila.ttf", 250);
        this.fonts[4] = p5.createFont("data/fonts/NotoEmoji-Bold.ttf", 250);
        this.fonts[5] = p5.createFont("data/fonts/GameOver.ttf", 250);
    }

    // Getter del número de fonts
    public int getNumFonts(){
        return this.fonts.length;
    }

    // Getter de la font primaria
    public PFont getFontPrimaria(){
        return  this.fonts[0];
    }

    // Getter del font secundaria
    public PFont getFontSecundaria(){
        return  this.fonts[1];
    }

    // Getter del la font terciaria
    public PFont getFontTerciaria(){
        return  this.fonts[2];
    }

    // Getter del la font emoji
    public PFont getFontEmoji(){
        return  this.fonts[4];
    }

    // Getter de la font i-èssima
    public PFont getFontAt(int i){
        return this.fonts[i];
    }


    // Getters de la font de la taula
    public PFont getFontCapçaleraTaula(){ return this.fonts[0]; }
    public PFont getFontFilaTaula(){ return this.fonts[1]; }
    public PFont getFontPeuTaula(){ return this.fonts[1]; }

    // Getters de la font del resum
    public PFont getFontResumNivell1(){ return this.fonts[0]; }
    public PFont getFontResumNivell2(){ return this.fonts[1]; }
    public PFont getFontResumNivell3(){ return this.fonts[2]; }

    // Getters de la font del botó
    public PFont getFontTextBoto(){ return this.fonts[5];}

    // Getters de la font del menu
    public PFont getFontCapçaleraMenu(){ return this.fonts[2]; }
    public PFont getFontCapçaleraSubMenu(){ return this.fonts[1]; }

    // Getters de les fonts dels titulars
    public PFont getFontTitular(){ return this.fonts[0]; }
    public PFont getFontSubTitular(){ return this.fonts[2]; }

    // Getters de les fonts de l'entrada de text
    public PFont getFontEtiquetaEntradaText(){ return this.fonts[5];}
    public PFont getFontEntradaText(){ return this.fonts[1];}
}
