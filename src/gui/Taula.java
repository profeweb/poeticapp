package gui;

import processing.core.PApplet;

public class Taula extends GuiElement {

    String[] titolsTaula;   // Títols de les columnes
    String[][] dadesTaula;    // Dades de la taula
    float[] midaColumnes;    // Amplades de les columnes (%)

    int numColumnes, numFilesPagina;  // Número de files i columnes

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

        if(d.length <= this.numFilesPagina){
            this.numTotalPagines = 1;
        }
        else if(d.length % this.numFilesPagina == 0){
            this.numTotalPagines = (d.length / this.numFilesPagina);
        }
        else {
            this.numTotalPagines = (d.length / this.numFilesPagina) + 1 ;
        }
    }

    public void setNumFilesPagina(int numFiles){ this.numFilesPagina = numFiles; }

    public void setValueAt(String value, int nr, int nc){
        this.dadesTaula[nr][nc] = value;
    }

    public void setMidaColumnes(float[] w){
        this.midaColumnes = w;
    }

    public void paginaSeguent(){
        if(this.paginaActual < this.numTotalPagines-1){
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

        float rowHeight = h / (numFilesPagina);

        // Dibuixa files
        p5.stroke(0);
        for(int r = 1; r < numFilesPagina; r++){
            if(r==1){ p5.strokeWeight(3); }
            else {    p5.strokeWeight(1); }
            p5.line(x, y + r*rowHeight, x + w, y + r*rowHeight);
        }

        // Dibuixa textos
        p5.fill(0); p5.textSize(24);
        for(int r = 0; r < numFilesPagina; r++){
            float xCol = x;
            for(int c = 0; c< numColumnes; c++){
                if(r==0){
                    p5.textFont(fonts.getFontCapçaleraTaula());
                    p5.textSize(Mides.midaTextCapçaleraTaula);
                    p5.textAlign(p5.LEFT, p5.BOTTOM);
                    p5.text(titolsTaula[c], xCol + 10, y + (r+1)*rowHeight - 0);
                }
                else{
                    int dr = r-1;
                    int k = (numFilesPagina - 1) * paginaActual + dr;
                    if(k < dadesTaula.length && dadesTaula[k][c]!=null){
                        p5.textFont(fonts.getFontFilaTaula());
                        p5.textSize(Mides.midaTextFilaTaula);
                        p5.textAlign(p5.LEFT, p5.BOTTOM);
                        p5.text(dadesTaula[k][c], xCol + 10, y + (r+1)*rowHeight - 5);
                    }
                }
                xCol += w* midaColumnes[c]/100.0;
            }
        }

        // Informació de la Pàgina
        p5.fill(0);
        p5.textFont(fonts.getFontPeuTaula());
        p5.textSize(Mides.midaTextPeuTaula);
        p5.textAlign(p5.LEFT, p5.TOP);
        p5.text("Pàg: "+(this.paginaActual +1)+" / "+(this.numTotalPagines), x, y + h + 5);

        p5.popStyle();
    }

}
