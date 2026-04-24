package gui;

import processing.core.PApplet;

public class SeleccionablesGrup extends GuiElement {

    Seleccionable[] seleccionables;

    public SeleccionablesGrup(int n){
        super(0,0,0,0);
        seleccionables = new Seleccionable[n];
    }

    public Seleccionable getSeleccionableAT(int i){ return  this.seleccionables[i]; }

    public void setSeleccionables(Seleccionable ... sels){
        for(int i=0; i<sels.length; i++){
            this.seleccionables[i] = sels[i];
        }
    }

    public void display(PApplet p5){
        for(int i=0; i<seleccionables.length; i++){
            if(seleccionables[i]!=null){
                seleccionables[i].display(p5);
            }
        }
    }

    public void updateClick(PApplet p5){
        if(clicSobreSeleccionables(p5)){
            for(int i=0; i<seleccionables.length; i++){
                if(seleccionables[i]!=null && seleccionables[i].mouseDins(p5)){
                    seleccionables[i].toggleSeleccionat();
                }
            }
        }
    }

    public boolean clicSobreSeleccionables(PApplet p5){
        for(int i=0; i<seleccionables.length; i++){
            if(seleccionables[i]!=null && seleccionables[i].mouseDins(p5)){
                return true;
            }
        }
        return false;
    }

}
