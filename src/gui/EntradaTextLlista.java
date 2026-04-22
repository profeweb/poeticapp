package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class EntradaTextLlista extends GuiElement {

    EntradaText textField;
    String[] texts;
    int selectedIndex;         // Fila seleccionada
    String selectedId;         // Id Seleccionat
    String selectedValue;      // Valor Seleccionat

    boolean enabled;           // Abilitat / desabilitat

    int numMatchs = 0;
    ArrayList<Boto> buttons;

    public EntradaTextLlista(float x, float y, float w, float h) {

        super(x, y, w, h);

    }

    public void display(PApplet p5) {
        p5.pushStyle();
        textField.display(p5);

        for(Boto b : buttons){
            b.display(p5);
        }
        p5.popStyle();
    }

    public String getSelectedValue(){
        return this.selectedValue;
    }

    public EntradaText getTextField(){
        return  this.textField;
    }

    public void update(PApplet p5) {

        String searchFor = this.textField.text;
        System.out.println("SEARCH FOR: "+searchFor);

        this.numMatchs = 0;
        this.buttons = new ArrayList<Boto>();

        if (searchFor.length() > 0) {
            for (int i=0; i<texts.length; i++) {
                if (texts[i].startsWith(searchFor)) {
                    Boto b = new Boto(texts[i], x + 10, y + h + 50 + (h + 50)*numMatchs, w, h);
                    buttons.add(b);
                    this.numMatchs++;
                    if (this.numMatchs==5) {
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
                textField.text = b.textBoto;
                this.selectedValue = b.textBoto;
                pressed = true;
            }
        }
        if(pressed){
            buttons.clear();
        }
    }
}
