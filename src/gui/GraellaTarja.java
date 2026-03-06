package gui;

import processing.core.PApplet;
import processing.core.PImage;

public class GraellaTarja extends GuiElement {

    String[][] dadesTarges;    // Dades de les Cards
    PImage[] imatgesTarges;
    Tarja[] targes;            // Cards

    int numTarges;            // Número total de Cards
    int numFilesPagina;
    int numTargesFila;
    int numTargesPagina;        // Número de Cards en 1 Pàgina

    int numPaginaActual;
    int numTotalPages;

    float ampleTarja, altTarja;
    float margeHoritzontal = 15, margeVertical = 80;
    int numTarjaSeleccionada = -1;

    // Constructor
    public GraellaTarja(int numRows, int numCols, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.numFilesPagina = numRows;
        this.numTargesFila = numCols;
        this.numTargesPagina = numRows * numCols;
        this.numPaginaActual = 0;
        this.ampleTarja =( w - margeHoritzontal *(numTargesFila -1)) / numTargesFila;
        this.altTarja = (h - margeHoritzontal *(numFilesPagina -1)) / numFilesPagina;
    }

    // Setters

    public void setData(String[][] d) {
        this.dadesTarges = d;
        this.numTarges = d.length;
        this.numTotalPages = d.length / this.numTargesPagina;
    }

    public void setImatges(PApplet p5, PImage[] imatges) {
        for(int numTarja=0; numTarja<targes.length; numTarja++){
            targes[numTarja].setImatge(p5, imatges[numTarja]);
        }
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
            int nr = (numTarja / numTargesFila) % numFilesPagina;
            int nc = numTarja % numTargesFila;

            // Calculam la posició de la tarja
            float yCard = y + (altTarja + margeVertical) * nr;
            float xCard = x + (ampleTarja + margeHoritzontal)* nc;

            targes[numTarja] = new Tarja(numTarja, dadesTarges[numTarja][0], dadesTarges[numTarja][1], xCard, yCard, ampleTarja, altTarja);
            targes[numTarja].setColors(colors);
            targes[numTarja].setFonts(fonts);
        }

    }


    public void paginaSeguent() {
        if (this.numPaginaActual <this.numTotalPages) {
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
        p5.text("Pag: "+(this.numPaginaActual + 1)+" / "+(this.numTotalPages + 1), x + w + 50, y+10);

        p5.popStyle();
    }

    public void clickSobreTarges(PApplet p5){

        boolean selected = false;

        int primeraTarjaPagina = numTargesPagina * numPaginaActual;
        int darreraTarjaPagina = numTargesPagina * (numPaginaActual +1) - 1;

        for(int numTarja=primeraTarjaPagina; numTarja<=darreraTarjaPagina; numTarja++){
            if (numTarja < targes.length && targes[numTarja] != null && targes[numTarja].mouseDins(p5)) {
                numTarjaSeleccionada = numTarja;
                selected = true;
                break;
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


}
