package gui;

import processing.core.PApplet;

import static gui.Mides.ALT_BOTO;
import static gui.Mides.AMPLE_BOTO;

public class TaulaPaginada extends Taula {

    BotoIcona bSeg, bAnt;

    public TaulaPaginada(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBotonsPaginacio(){

        bAnt = new BotoIcona(Mides.CODI_ANTERIOR, x + w - ALT_BOTO*2 - 25, y-ALT_BOTO -5, ALT_BOTO, ALT_BOTO);
        bAnt.setColors(colors);
        bAnt.setFonts(fonts);

        bSeg = new BotoIcona(Mides.CODI_SEGUENT, x + w - ALT_BOTO , y - ALT_BOTO -5, ALT_BOTO, ALT_BOTO);
        bSeg.setColors(colors);
        bSeg.setFonts(fonts);
    }


    public void display(PApplet p5){

        super.display(p5);

        if(bSeg!=null) {
            bSeg.display(p5);
        }

        if(bAnt !=null) {
            bAnt.display(p5);
        }
    }

    public void updateClick(PApplet p5){
        if(bSeg.mouseDins(p5) && paginaActual!=numTotalPagines){
            paginaSeguent();
            if(paginaActual == numTotalPagines){
                bSeg.setActivat(false);
            }
            bAnt.setActivat(true);
        }
        else if(bAnt.mouseDins(p5) && paginaActual!=0){
            paginaAnterior();
            if(paginaActual == 0){
                bAnt.setActivat(false);
            }
            bSeg.setActivat(true);
        }

    }
}
