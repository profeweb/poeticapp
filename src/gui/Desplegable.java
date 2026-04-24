package gui;

import processing.core.PApplet;

import static gui.Mides.TEXT_ENTRADA;

public class Desplegable extends GuiElement {

    String[] opcions;            // Valors possibles
    String opcioSeleccionada;      // Valor Seleccionat

    String textEtiqueta;
    boolean desplegat = false;  // Plegat / Desplegat
    boolean activat;           // Abilitat / desabilitat

    float lineSpace = 15;      // Espai entre línies

    public Desplegable(String[] texts, float x, float y, float w, float h){
        super(x, y, w, h);
        this.opcions = texts;
        this.opcioSeleccionada = "";
        this.activat = true;
        this.desplegat = false;
    }


    public  boolean isActivat(){
        return  this.activat;
    }

    public  boolean isDesplegat(){
        return  this.desplegat;
    }

    public String getOpcioSeleccionada(){
        return  this.opcioSeleccionada;
    }

    public void display(PApplet p5){

        p5.pushStyle();

        p5.stroke(0); p5.strokeWeight(2); p5.fill(255);
        p5.rect(this.x, this.y, this.w, this.h, 5);

        p5.fill(this.colors.getColorPrimari());
        p5.rect(x + w - h, y, h, h, 5);

        p5.fill(0); p5.stroke(0);
        p5.triangle(x + w - h + 10, y+10, x + w -h/2, y + 25, x + w - 10 , y+10);

        p5.fill(0); p5.textSize(14);
        p5.textAlign(p5.LEFT);
        p5.text(this.opcioSeleccionada, x + 10, y + 20);

        if(this.desplegat){

            p5.fill(this.colors.getColorPrimari()); p5.stroke(0);
            p5.rect(x, y+h, w, (h + lineSpace)* opcions.length);

            for(int i = 0; i< opcions.length; i++){

                if(i== numOpcioSeleccionada(p5)){
                    p5.fill(200); p5.noStroke();
                    p5.rect(x+4, y+4 + h + (h + lineSpace)*i - 2, w -8, h + lineSpace - 8);
                }

                p5.fill(0);
                p5.text(opcions[i], x + 10, y + h + 25 + (h + lineSpace)*i);
            }
        }

        // Text Etiqueta
        // Etiqueta
        p5.fill(colors.getColorEntradaTextText());
        p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.textFont(fonts.getFontSecundaria());
        p5.text(textEtiqueta, x - 15, y + h - TEXT_ENTRADA);
        p5.popStyle();


        p5.popStyle();
    }

    public void setTextEtiqueta(String text){ this.textEtiqueta = text; }

    public void setDesplegat(boolean b){
        this.desplegat = b;
    }

    public void toggle(){
        this.desplegat = !this.desplegat;
    }


    public void update(PApplet p5){
        if(mouseDins(p5)) {
            if(p5.mouseY > this.y + this.h) {
                int option = numOpcioSeleccionada(p5);
                opcioSeleccionada = opcions[option];
            }
            toggle();
        }

    }

    // Indica si el cursor està sobre el select
    public boolean mouseDins(PApplet p5){
        if(this.desplegat){
            return (p5.mouseX>= x) &&
                    (p5.mouseX<= x + w) &&
                    (p5.mouseY>= y) &&
                    (p5.mouseY<= y + h + (h + lineSpace)* opcions.length);
        }
        else {
            return (p5.mouseX >= x) &&
                    (p5.mouseX <= x + w) &&
                    (p5.mouseY >= y) &&
                    (p5.mouseY <= y + h);
        }
    }

    int getNumOpcioSeleccionada(){
        for(int i=0; i<opcions.length; i++){
            if(opcions[i].equals(opcioSeleccionada)){
                return i;
            }
        }
        return -1;
    }

    int numOpcioSeleccionada(PApplet p5){
        return (int)p5.map(p5.mouseY, y + h, y + h + (h + lineSpace)* opcions.length,
                0, opcions.length);
    }

}
