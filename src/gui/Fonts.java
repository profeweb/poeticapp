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
        this.fonts = new PFont[4];
        this.fonts[0] = p5.createFont("data/fonts/Astila.ttf", midaTitol);
        this.fonts[1] = p5.createFont("data/fonts/GameOver.ttf", midaSubtitol);
        this.fonts[2] = p5.createFont("data/fonts/Graffiti.ttf", midaParagraf);
        this.fonts[3] = p5.createFont("data/fonts/NotoEmoji-Bold.ttf", TEXT_BOTO);
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
        return  this.fonts[3];
    }

    // Getter de la font i-èssima
    public PFont getFontAt(int i){
        return this.fonts[i];
    }
}
