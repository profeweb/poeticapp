package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class EntradaTextLlista extends GuiElement {

    EntradaText entradaText;
    String[] texts;
    int selectedIndex;         // Fila seleccionada
    String selectedId;         // Id Seleccionat
    String selectedValue;      // Valor Seleccionat

    boolean enabled;           // Abilitat / desabilitat

    int numMatchs = 0;
    int maxNumMatches = 5;
    ArrayList<Boto> buttons;

    public EntradaTextLlista(String textEtiqueta, String[] opcions, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.texts = opcions;
        this.selectedId = "";
        this.selectedValue = "";
        this.enabled = true;

        this.entradaText = new EntradaText( textEtiqueta, x, y, w, h);
        this.buttons = new ArrayList<>();

    }

    public EntradaTextLlista(String[] opcions, float x, float y, float w, float h) {

        super(x, y, w, h);
        this.texts = opcions;
        this.selectedId = "";
        this.selectedValue = "";
        this.enabled = true;

        this.entradaText = new EntradaText( "", x, y, w, h);
        this.buttons = new ArrayList<>();

    }

    public void setColorsFonts(Colors colors, Fonts fonts){
        this.colors = colors;
        this.fonts = fonts;
        this.entradaText.setColorsFonts(colors, fonts);
    }

    public void display(PApplet p5) {
        p5.pushStyle();
        entradaText.display(p5);

        for(Boto b : buttons){
            b.display(p5);
        }
        p5.popStyle();
    }

    public String getSelectedValue(){
        return this.selectedValue;
    }

    public EntradaText getEntradaText(){
        return  this.entradaText;
    }

    public void update(PApplet p5) {

        String searchFor = this.entradaText.text;
        System.out.println("SEARCH FOR: "+searchFor);

        this.numMatchs = 0;
        this.buttons = new ArrayList<>();

        if (searchFor.length() > 0) {
            for (int i=0; i<texts.length; i++) {
                if (texts[i].startsWith(searchFor)) {
                    Boto b = new Boto(texts[i], x, y + h + 10 + (h + 10)*numMatchs, w, h);
                    b.setColorsFonts(colors, fonts);
                    buttons.add(b);
                    this.numMatchs++;
                    if (this.numMatchs==maxNumMatches) {
                        break;
                    }
                }
            }
        }
    }

    public boolean mouseOverButtons(PApplet p5){
        for(Boto b : buttons){
            if(b.mouseDins(p5)){
                return true;
            }
        }
        return false;
    }

    public void buttonPressed(PApplet p5){
        boolean pressed = false;
        for(Boto b : buttons){
            if(b.mouseDins(p5)){
                entradaText.text = b.textBoto;
                this.selectedValue = b.textBoto;
                pressed = true;
            }
        }
        if(pressed){
            buttons.clear();
        }
    }

    public void keyPressed(PApplet p5){
        if(entradaText.mouseDins(p5)){
            entradaText.updateKeyPressed(p5.keyCode);
            this.update(p5);
        }
    }

    public void keyTyped(PApplet p5){
        if(entradaText.mouseDins(p5)){
            entradaText.updateKeyTyped(p5.key);
            this.update(p5);
        }
    }

    public void updateClick(PApplet p5){
        entradaText.updateClick(p5);
        this.buttonPressed(p5);
    }
}
