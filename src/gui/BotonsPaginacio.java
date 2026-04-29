package gui;

import processing.core.PApplet;

import java.util.ArrayList;


public class BotonsPaginacio extends GuiElement {

    boolean canviPagina = false;
    int paginaActual, numTotalPagines;
    ArrayList<BotoSeleccionable> botonsPagines;
    BotoIcona bSeg, bAnt;

    float margeHoritzontalEntreBotons = 15f;

    public BotonsPaginacio(float x, float y, float h) {
        super(x, y, 0, h);
    }

    public void setBotonsPagines(int numTotalPagines){
        this.paginaActual = 0;
        this.numTotalPagines = numTotalPagines;
        this.botonsPagines = new ArrayList<>();
        for(int i=0; i<numTotalPagines; i++){
            float xBoto = this.x + i*(h + margeHoritzontalEntreBotons);
            BotoSeleccionable boto = new BotoSeleccionable(String.valueOf(i+1), xBoto, y, h, h);
            boto.setColorsFonts(colors, fonts);
            this.botonsPagines.add(boto);
        }

        this.botonsPagines.get(this.paginaActual).setSelected(true);
    }

    public int getPaginaActual(){ return  this.paginaActual; }

    public void setCanviPagina(boolean b){ this.canviPagina = b; }

    public void setBotonsSegAnt(){

        bAnt = new BotoIcona(Mides.CODI_ANTERIOR, x  - h - margeHoritzontalEntreBotons, y, h, h);
        bAnt.setColors(colors);
        bAnt.setFonts(fonts);

        this.w = this.h * numTotalPagines + margeHoritzontalEntreBotons * (numTotalPagines -1);
        bSeg = new BotoIcona(Mides.CODI_SEGUENT, this.x + this.w + margeHoritzontalEntreBotons, y, h, h);
        bSeg.setColors(colors);
        bSeg.setFonts(fonts);
    }


    public void paginaSeguent(){
        if(this.paginaActual < this.numTotalPagines-1){
            botonsPagines.get(this.paginaActual).toggleSelection();
            this.paginaActual++;
            botonsPagines.get(this.paginaActual).toggleSelection();
            setCanviPagina(true);
        }
    }

    public void paginaAnterior(){
        if(this.paginaActual >0){
            botonsPagines.get(this.paginaActual).toggleSelection();
            this.paginaActual--;
            botonsPagines.get(this.paginaActual).toggleSelection();
            setCanviPagina(true);
        }
    }

    public void display(PApplet p5){

        p5.pushStyle();
        p5.fill(0);
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        //p5.text("estrofa "+ this.numEstrofa, this.x, this.y);
        p5.popStyle();

        for(Boto boto : botonsPagines){
                boto.display(p5);
        }

        if(bSeg!=null) {
            bSeg.display(p5);
        }

        if(bAnt !=null) {
            bAnt.display(p5);
        }
    }

    public void updateClick(PApplet p5){

        for(BotoSeleccionable boto: botonsPagines){
            if(boto.mouseDins(p5)) {
                botonsPagines.get(paginaActual).setSelected(false);
                boto.setSelected(true);
                this.paginaActual = Integer.valueOf(boto.textBoto).intValue() - 1;
                this.canviPagina = true;
            }
        }

        if(bSeg.mouseDins(p5)){
            paginaSeguent();
        }
        else if(bAnt.mouseDins(p5)){
            paginaAnterior();
        }
    }
}
