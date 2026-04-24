package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class GraellaTarjaCerca extends GuiElement {

    String titol;

    String[][] dadesTarges;    // Dades de les Cards
    TarjaCerca[] targes;            // Cards

    int numTarges;            // Número total de Cards
    int numFiles, numColumnes;
    int numTargesPagina;        // Número de Cards en 1 Pàgina

    int numPaginaActual;
    int numTotalPagines;

    float ampleTarja, altTarja;
    float margeHoritzontal = 5, margeVertical = 5;

    BotoIcona bSeg, bAnt;
    Desplegable selOrdre;

    // Constructor
    public GraellaTarjaCerca(int numFiles, int numCols, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.numFiles = numFiles;
        this.numColumnes = numCols;
        this.numTargesPagina = numFiles * numCols;
        this.numPaginaActual = 0;
        this.ampleTarja =( w - margeHoritzontal *(numColumnes -1)) / numColumnes;
        this.altTarja = (h - margeHoritzontal *(this.numFiles -1)) / this.numFiles;
    }

    // Setters
    public void setData(String[][] d) {
        this.dadesTarges = d;
        this.numTarges = d.length;
        this.numTotalPagines = d.length / this.numTargesPagina;
        if(d.length % this.numTargesPagina !=0){
            this.numTotalPagines++;
        }
    }


    public void setTitol(String t){ this.titol = t; }

    public void setBotons(PApplet p5){
        this.bAnt = new BotoIcona(CODI_ANTERIOR, this.x + this.w/2 - BOTO_FAVORIT - margeHoritzontal, this.y + this.h + margeVertical , BOTO_FAVORIT, BOTO_FAVORIT);
        this.bAnt.setColors(colors);
        this.bAnt.setFonts(fonts);

        this.bSeg = new BotoIcona(CODI_SEGUENT, this.x + this.w/2 +margeHoritzontal, this.y + this.h + margeHoritzontal , BOTO_FAVORIT, BOTO_FAVORIT);
        this.bSeg.setColors(colors);
        this.bSeg.setFonts(fonts);
    }


    public void setTarges(PApplet p5) {

        targes = new TarjaCerca[numTarges];

        for(int numTarja = 0; numTarja< dadesTarges.length; numTarja++){

            // Calculam el número de fila i columna dins la graella
            int nr = (numTarja / numColumnes) % numFiles;
            int nc = numTarja % numColumnes;

            // Calculam la posició de la tarja
            float yCard = y + (altTarja + margeVertical) * nr;
            float xCard = x + (ampleTarja + margeHoritzontal)* nc;

            targes[numTarja] = new TarjaCerca(numTarja+1, dadesTarges[numTarja], dadesTarges[numTarja][5], xCard, yCard, ampleTarja, altTarja);
            targes[numTarja].setColors(colors);
            targes[numTarja].setFonts(fonts);
            targes[numTarja].setBotoDetall();
        }

    }


    public void paginaSeguent() {
        if (this.numPaginaActual < this.numTotalPagines-1) {
            this.numPaginaActual++;
        }
    }

    public void paginaAnterior() {
        if (this.numPaginaActual >0) {
            this.numPaginaActual--;
        }
    }

    // Dibuixa taula
    public void display(PApplet p5) {

        p5.pushStyle();

        // Dibuixa Cards corresponent a la Pàgina
        int primeraTarjaPagina = numTargesPagina * numPaginaActual;
        int darreraTarjaPagina = numTargesPagina * (numPaginaActual +1) - 1;

        for(int numTarja = 0; numTarja< targes.length; numTarja++) {
            if(numTarja>=primeraTarjaPagina && numTarja<= darreraTarjaPagina) {
                if (numTarja < this.numTarges && targes[numTarja] != null) {
                    targes[numTarja].display(p5);
                }
            }
        }

        if(this.bSeg!=null){
            this.bSeg.display(p5);
        }
        if(this.bAnt!=null){
            this.bAnt.display(p5);
        }

        if(this.selOrdre!=null){
            this.selOrdre.display(p5);
        }

        if(titol!=null){
            p5.fill(0);
            p5.textFont(fonts.getFontPrimaria());
            p5.textSize(TEXT_INFO_GUI);
            p5.textAlign(p5.LEFT);
            p5.text(titol, x, y - margeHoritzontal);
        }

        // Informació de la Pàgina
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_INFO_GUI);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        String textPaginacio = "(" + (this.numPaginaActual * numTargesPagina +1 ) + " - " + ((this.numPaginaActual+1) * numTargesPagina -1)+ ", Pag: "+(this.numPaginaActual + 1)+" / "+ this.numTotalPagines +")";
        p5.text(textPaginacio, this.x + 125, this.y - margeHoritzontal);

        p5.popStyle();
    }

    public void clickBotoSeguent(PApplet p5){
        if(bSeg!=null && bSeg.mouseDins(p5)){
            paginaSeguent();
        }
    }

    public void clickBotoAnterior(PApplet p5){
        if(bAnt!=null && bAnt.mouseDins(p5)){
            paginaAnterior();
        }
    }


    public void updateClick(PApplet p5){
        this.clickBotoAnterior(p5);
        this.clickBotoSeguent(p5);

    }


}
