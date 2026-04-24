package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class Pantalla {

    ArrayList<GuiElement> elements;
    Gui.PANTALLA pantallaNom;

    public Pantalla(Gui.PANTALLA pantallaNom){
        this.pantallaNom = pantallaNom;
        this.elements = new ArrayList<>();
    }


    public void addElement(GuiElement element){
        elements.add(element);
    }

    public void addElements(GuiElement ... elements){
        for(GuiElement element : elements) {
            this.elements.add(element);
        }
    }

    public void display(PApplet p5){

        p5.fill(0);
        p5.text(pantallaNom.toString(), 600, 100);

        for(GuiElement element: elements){
            element.display(p5);
        }
    }

    public void mouseEvents(PApplet p5){
        for(GuiElement element: elements){
            if(element instanceof GraellaTarja){
                ((GraellaTarja)element).updateClick(p5);
            }
            else if(element instanceof GraellaTarjaCerca){
                ((GraellaTarjaCerca)element).updateClick(p5);
            }
            else if(element instanceof TaulaPaginada){
                ((TaulaPaginada)element).updateClick(p5);
            }
            else if(element instanceof EntradaText){
                ((EntradaText)element).updateClick(p5);
            }
            else if(element instanceof EntradaTextLlista){
                ((EntradaTextLlista)element).updateClick(p5);
            }
            else if(element instanceof MenuApp){
                ((MenuApp)element).updateClick(p5);
            }
            else if(element instanceof BotoOpcioGrup){
                ((BotoOpcioGrup)element).updateClick(p5);
            }
        }
    }

    public void keyPressedEvents(PApplet p5){
        for(GuiElement element: elements) {
            if (element instanceof EntradaText) {
                ((EntradaText) element).updateKeyPressed(p5.keyCode);
            }
            else if (element instanceof EntradaTextLlista) {
                ((EntradaTextLlista) element).updateKeyPressed(p5);
            }
        }
    }

    public void keyTypedEvents(PApplet p5){
        for(GuiElement element: elements) {
            if (element instanceof EntradaText) {
                ((EntradaText) element).updateKeyTyped(p5.key);
            }
            else if (element instanceof EntradaTextLlista) {
                ((EntradaTextLlista) element).updateKeyTyped(p5);
            }
        }
    }
}
