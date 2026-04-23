package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class Pantalla {

    ArrayList<GuiElement> elements;
    Gui.PANTALLA pantallaNom;
    Gui.PANTALLA pantallaActual;

    public Pantalla(Gui.PANTALLA pantallaNom, Gui.PANTALLA pantallaActual){
        this.pantallaNom = pantallaNom;
        this.elements = new ArrayList<>();
        this.pantallaActual = pantallaActual;
    }



    public void addElement(GuiElement element){
        elements.add(element);
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
        }
    }
}
