package gui;

import processing.core.PApplet;

public class Colors {

    private int[] colors;

    public Colors(PApplet p5){
        this.setColors(p5);
    }

    // Estableix colors de l'App
    void setColors(PApplet p5){

        this.colors = new int[6];

        this.colors[0] = p5.color(0xFF000000);

        this.colors[1] = p5.color(0xFF53599A);
        this.colors[2] = p5.color(0xFF6D9DC5);
        this.colors[3] = p5.color(0xFF80DED9);
        this.colors[4] = p5.color(0xFFAEECEF);
        this.colors[5] = p5.color(0xFF068D9D);
    }

    // Getter del número de colors
    public int getNumColors(){
        return this.colors.length;
    }

    // Getter del color primari
    public int getColorPrimari(){
        return  getColorAt(0);
    }

    // Getter del color secundari
    public int getColorSecundari(){
        return  getColorAt(1);
    }

    // Getter del color terciari
    public int getColorTerciari(){
        return  getColorAt(2);
    }

    // Getter del color i-èssim
    public int getColorAt(int i){
        return this.colors[i];
    }

    // Getters Colors Botons
    public int getColorBotoText(){ return this.colors[0]; }
    public int getColorBotoContorn(){ return this.colors[0]; }
    public int getColorBotoFarcimentDesactivat(){ return this.colors[1]; }
    public int getColorBotoFarcimentFora(){ return this.colors[2]; }
    public int getColorBotoFarcimentDins(){ return this.colors[3]; }


    // COLORS ENTRADA TEXT
    public int getColorEntradaTextContorn(){ return this.colors[0]; }
    public int getColorEntradaTextDesactivat(){ return this.colors[1]; }
    public int getColorEntradaTextActivat(){ return this.colors[3]; }
    public int getColorEntradaTextText(){ return this.colors[0]; }

}
