package parser;

import processing.core.PApplet;

import java.util.ArrayList;

public class Estrofa {

    int numero;
    ArrayList<Frase> frases;

    public Estrofa(int posicio) {
        this.numero = posicio;
        this.frases = new ArrayList<>();
    }

    public int getNumero(){ return this.numero; }

    public int getNumFrases(){ return this.frases.size(); }

    public int getNumVersos(){
        int num = 0;
        for(Frase frase : frases){
            num += frase.getNumVersos();
        }
        return  num;
    }

    public int getPrimerVersEstrofa(){ return this.frases.get(0).getPrimerVersFrase(); }

    public int getDarrerVersEstrofa(){ return this.frases.get(frases.size()-1).getDarrerVersFrase(); }

    public Vers getVersAt(int i){
        int n=0;
        for(int s = 0; s< frases.size(); s++){
            Frase fraseActual = frases.get(s);
            if( i < n + fraseActual.getNumVersos()){
                return fraseActual.getVersAt(i - n);
            }
            n += fraseActual.getNumVersos();
        }
        return null;
    }

    public ArrayList<Vers> getVersosSeccioAt(int s){ return frases.get(s).versos; }

    public ArrayList<Vers> getVersos(){
        ArrayList<Vers> versos = new ArrayList<>();
        for(Frase s : frases){
            versos.addAll(s.versos);
        }
        return versos;
    }

    public float calcMaxParaulesVersos(){
        float maxParaules = 0;
        for(Vers vers : getVersos()){
            if(vers.getNumParaules() > maxParaules){
                maxParaules = vers.getNumParaules();
            }
        }
        return maxParaules;
    }

    public void printEstrofa(){
        System.out.println("\nEstrofa #"+ numero + " ("+ frases.size()+ " frases):");
        for(Frase frase : frases){
            frase.printSeccio();
        }
        System.out.println();
    }

    public void dibuixaEstrofa(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int quantitat){
        p5.pushStyle();

        // Capsa de l'estrofa
        p5.noStroke(); p5.fill(colorEstrofa);
        p5.rect(x, y, w, h, 10);
        p5.fill(colorEstrofa);

        // Número d'estrofa
        p5.fill(colorEstrofa);
        p5.textSize(28); p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.text(numero, x-10, y + h/2);

        // Quantitat
        p5.fill(colorEstrofa);
        p5.textSize(12); p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(quantitat, x + w/2 , y + h + 18);

        p5.popStyle();
    }

    public void dibuixaVersosEstrofaLinia(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers, int maxParaulesVersPoema, float mitjanaParaules){

        float marge = 25;

        float he = h * (getNumVersos() + 1);
        dibuixaEstrofa(p5, x, y, w, he, colorEstrofa, getNumVersos());

        int numVers = 1;
        for(Vers vers : getVersos()){
            float wLinia = p5.map(vers.getNumParaules(), 0, maxParaulesVersPoema, 0, w - 2*marge);
            float gruixa = vers.getNumParaules() >= mitjanaParaules ? 3 : 1.5f;
            int colorLina = vers.getNumParaules() == maxParaulesVersPoema ? p5.color(0) : colorVers;
            vers.dibuixaVersLinea(p5, x + marge , y + h*numVers, gruixa, wLinia, w, colorLina, vers.getNumParaules());
            numVers++;
        }
    }
}
