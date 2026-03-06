package gui;

import processing.core.PApplet;

public class Taula extends GuiElement {

    String[] titolsTaula;   // Títols de les columnes
    String[][] dadesTaula;    // Dades de la taula
    float[] midaColumnes;    // Amplades de les columnes (%)

    int numColumnes, numFiles;  // Número de files i columnes

    int paginaActual;
    int numTotalPagines;

    // Constructor
    public Taula(float x, float y, float w, float h){
        super(x, y, w, h);
        this.paginaActual = 0;
    }

    // Setters

    public void setTitols(String[] h){
        this.numColumnes = h.length;
        this.titolsTaula = h;
    }

    public void setDades(String[][] d){
        this.dadesTaula = d;
        this.numFiles = d.length;
        if(d.length % (this.numFiles -1)==0){
            this.numTotalPagines = (d.length / (this.numFiles -1)) -1;
        }
        else {
            this.numTotalPagines = (d.length / (this.numFiles -1)) ;
        }
        this.numFiles = d.length + 1;
    }

    public void setNumFilesPagina(int numFiles){ this.numFiles  = numFiles + 1; }

    public void setValueAt(String value, int nr, int nc){
        this.dadesTaula[nr][nc] = value;
    }

    public void setMidaColumnes(float[] w){
        this.midaColumnes = w;
    }

    public void paginaSeguent(){
        if(this.paginaActual < this.numTotalPagines){
            this.paginaActual++;
        }
    }

    public void paginaAnterior(){
        if(this.paginaActual >0){
            this.paginaActual--;
        }
    }

    // Dibuixa taula
    public void display(PApplet p5){

        p5.pushStyle();

        p5.fill(200, 50); p5.stroke(0); p5.strokeWeight(3);
        p5.rect(x, y, w, h, 5);

        float rowHeight = h / numFiles;

        // Dibuixa files
        p5.stroke(0);
        for(int r = 1; r < numFiles; r++){
            if(r==1){ p5.strokeWeight(3); }
            else {    p5.strokeWeight(1); }
            p5.line(x, y + r*rowHeight, x + w, y + r*rowHeight);
        }

        // Dibuixa Columnes

        float xCol = x;
        /*
        for(int c = 0; c< numColumnes; c++){
            xCol += w* midaColumnes[c]/100.0;
            p5.line(xCol, y, xCol, y + h);
        }

         */

        // Dibuixa textos
        p5.fill(0); p5.textSize(24);
        for(int r = 0; r < numFiles; r++){
            xCol = x;
            for(int c = 0; c< numColumnes; c++){
                if(r==0){
                    p5.textFont(fonts.getFontPrimaria());
                    p5.text(titolsTaula[c], xCol + 10, y + (r+1)*rowHeight - 10);
                }
                else{
                    int k = (numFiles -1)* paginaActual + (r-1);
                    if(k< dadesTaula.length){
                        p5.textFont(fonts.getFontSecundaria());
                        p5.text(dadesTaula[k][c], xCol + 10, y + (r+1)*rowHeight - 10);
                    }
                }
                xCol += w* midaColumnes[c]/100.0;
            }
        }

        // Informació de la Pàgina
        p5.fill(0);
        p5.text("Pag: "+(this.paginaActual +1)+" / "+(this.numTotalPagines +1), x, y + h + 50);

        p5.popStyle();
    }

}
