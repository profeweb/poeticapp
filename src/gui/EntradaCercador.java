package gui;

import processing.core.PApplet;

import static gui.Mides.*;
import static gui.Mides.TEXT_ENTRADA;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.ENTER;

public class EntradaCercador extends EntradaText{

    String textCerca ="";
    boolean textConfirmat = false;
    boolean esborraEnConfirmar = true;

    public EntradaCercador(float x, float y, float w, float h) {
        super("Cercar", x, y, w, h);
    }

    public EntradaCercador(String textEtiqueta, float x, float y, float w, float h, Colors colors, Fonts fonts) {
        super(textEtiqueta, x, y, w, h, colors, fonts);
    }

    public void setEsborraEnConfirmar(boolean b){ this.esborraEnConfirmar = b; }

    // Dibuixa el botó
    public void display(PApplet p5){

        p5.pushStyle();

        // Rectangle
        p5.stroke(colors.getColorEntradaTextContorn());
        p5.strokeWeight(Mides.GRUIXA_ENTRADA_TEXT);
        if(!actiu){
            p5.fill(colors.getColorEntradaTextDesactivat());
        }
        else {
            p5.fill(colors.getColorEntradaTextActivat());
        }
        p5.rect(x, y, w, h, 5);

        // Text
        p5.fill(colors.getColorEntradaTextText());
        p5.textSize(TEXT_ENTRADA);
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.text(text, x + w-5, y + h - TEXT_ENTRADA);

        // Etiqueta
        p5.fill(colors.getColorEntradaTextText());
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.text(textEtiqueta, x - 15, y + h - TEXT_ENTRADA);
        p5.popStyle();

        // Icona
        p5.pushStyle();
        p5.fill(255, 100, 100);
        p5.textFont(this.fonts.getFontEmoji());
        p5.textSize(TEXT_ICONA_BOTO); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(new String(Character.toChars(CODI_ICONA_CERCADOR)), this.x + 10, this.y + this.h/2 - 2);
        p5.popStyle();
    }


    // Gestiona tecles especials
    public void updateKeyPressed(int keyCode) {

        if (!actiu) return ;

        if (keyCode == BACKSPACE) {
            esborrarText();
        }
        else if(keyCode == ENTER){
            this.textConfirmat = true;
            this.textCerca = this.text;
            if(esborraEnConfirmar) {
                this.setActiu(false);
                esborrraTotText();
            }
        }
    }
}
