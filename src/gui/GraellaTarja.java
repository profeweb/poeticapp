package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class GraellaTarja extends GuiElement {

    String[][] dadesTarges;    // Dades de les Cards
    PImage[] imatgesTarges;
    Tarja[] targes;            // Cards

    int numTarges;            // Número total de Cards
    int numFiles, numColumnes;
    int numTargesPagina;        // Número de Cards en 1 Pàgina

    int numPaginaActual;
    int numTotalPagines;

    float ampleTarja, altTarja;
    float margeHoritzontal = 15, margeVertical = 80;
    int numTarjaSeleccionada = -1;

    BotoIcona bSeg, bAnt;

    // Constructor
    public GraellaTarja(int numFiles, int numCols, float x, float y, float w, float h) {
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

    public void setImatges(PApplet p5, PImage[] imatges) {
        for(int numTarja=0; numTarja<targes.length; numTarja++){
            targes[numTarja].setImatge(p5, imatges[numTarja]);
        }
    }

    public void setBotons(PApplet p5){
        this.bAnt = new BotoIcona(p5,CODI_ANTERIOR, this.x + this.w - 2*BOTO_FAVORIT - margeHoritzontal, this.y - BOTO_FAVORIT - margeHoritzontal , BOTO_FAVORIT, BOTO_FAVORIT);
        this.bAnt.setColors(colors);
        this.bAnt.setFonts(fonts);

        this.bSeg = new BotoIcona(p5,CODI_SEGUENT, this.x + this.w - BOTO_FAVORIT, this.y - BOTO_FAVORIT - margeHoritzontal , BOTO_FAVORIT, BOTO_FAVORIT);
        this.bSeg.setColors(colors);
        this.bSeg.setFonts(fonts);
    }

    public void setImatges(PApplet p5, PImage imatge) {
        for(Tarja tarja : targes){
            tarja.setImatge(p5, imatge);
        }
    }

    public void setTarges(PApplet p5) {

        targes = new Tarja[numTarges];

        for(int numTarja = 0; numTarja< dadesTarges.length; numTarja++){

            // Calculam el número de fila i columna dins la graella
            int nr = (numTarja / numColumnes) % numFiles;
            int nc = numTarja % numColumnes;

            // Calculam la posició de la tarja
            float yCard = y + (altTarja + margeVertical) * nr;
            float xCard = x + (ampleTarja + margeHoritzontal)* nc;

            targes[numTarja] = new Tarja(numTarja+1, dadesTarges[numTarja][0], dadesTarges[numTarja][1], xCard, yCard, ampleTarja, altTarja);
            targes[numTarja].setColors(colors);
            targes[numTarja].setFonts(fonts);
            targes[numTarja].setBotoFavorit();
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
                    if(numTarja==numTarjaSeleccionada) {
                        targes[numTarja].displaySelected(p5);
                    }
                    else {
                        targes[numTarja].display(p5);
                    }
                }
            }
        }

        // Informació de la Pàgina
        p5.fill(0);
        p5.textFont(fonts.getFontSecundaria());
        p5.textSize(TEXT_INFO_GUI);
        p5.textAlign(p5.RIGHT);
        p5.text("Pag: "+(this.numPaginaActual + 1)+" / "+ this.numTotalPagines, this.x + this.w, this.y - margeHoritzontal);

        if(this.bSeg!=null){
            this.bSeg.display(p5);
        }
        if(this.bAnt!=null){
            this.bAnt.display(p5);
        }

        p5.popStyle();
    }

    public void clickSobreTarges(PApplet p5){

        boolean selected = false;

        int primeraTarjaPagina = numTargesPagina * numPaginaActual;
        int darreraTarjaPagina = numTargesPagina * (numPaginaActual +1) - 1;

        for(int numTarja=primeraTarjaPagina; numTarja<=darreraTarjaPagina; numTarja++){
            if (numTarja < targes.length && targes[numTarja] != null && targes[numTarja].mouseDins(p5)) {

                if(targes[numTarja].bFavorit!=null && targes[numTarja].bFavorit.mouseDins(p5)) {
                    targes[numTarja].bFavorit.clickFavorit(p5);
                    break;
                }
                else {
                    numTarjaSeleccionada = numTarja;
                    selected = true;
                    break;
                }
            }
        }
        if(!selected){
            numTarjaSeleccionada = -1;
        }
    }

    public boolean mouseSobreTarges(PApplet p5){

        int primeraTarjaPagina = numTargesPagina * numPaginaActual;
        int darreraTarjaPagina = numTargesPagina * (numPaginaActual +1) - 1;

        for(int numTarja=primeraTarjaPagina; numTarja<=darreraTarjaPagina; numTarja++){
            if (numTarja < targes.length && targes[numTarja] != null && targes[numTarja].mouseDins(p5)) {
                return true;
            }
        }
        return false;
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


}
