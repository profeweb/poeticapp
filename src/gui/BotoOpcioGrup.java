package gui;

import processing.core.PApplet;

public class BotoOpcioGrup extends SeleccionablesGrup {

    int opcioSeleccionada;

    public BotoOpcioGrup(int n) {
        super(n);
        this.opcioSeleccionada = -1;
    }

    public void updateClick(PApplet p5){
        if(clicSobreSeleccionables(p5)){
            opcioSeleccionada = -1;
            for(int i=0; i<seleccionables.length; i++){
                if(seleccionables[i]!=null && seleccionables[i].mouseDins(p5)){
                    seleccionables[i].setSeleccionat(true);
                    opcioSeleccionada = i;
                }
                else {
                    seleccionables[i].setSeleccionat(false);
                }
            }
        }
    }
}
