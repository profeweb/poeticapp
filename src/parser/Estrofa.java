package parser;

import processing.core.PApplet;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.PI;

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

    public float getMaxParaulesVersos(){
        float maxParaules = 0;
        for(Vers vers : getVersos()){
            if(vers.getNumParaules() > maxParaules){
                maxParaules = vers.getNumParaules();
            }
        }
        return maxParaules;
    }

    public float getMitjanaParaulesVersosEstrofa(){
        float numParaules = 0;
        for(Vers vers : getVersos()){
            numParaules += vers.getNumParaules();
        }
        return numParaules / getNumVersos();
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
        p5.textSize(28); p5.textAlign(p5.RIGHT, p5.BOTTOM);
        p5.text("E"+numero, x-10, y + h/2 - 5);

        // Quantitat
        p5.fill(colorEstrofa);
        p5.textSize(12); p5.textAlign(p5.RIGHT, p5.TOP);
        p5.text(quantitat, x -15 , y + h/2 + 5);

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


    public void dibuixaEstrofaArc(PApplet p5, float x, float y, float radiMin, float radiMax, float angleMin, float angleMax, float mitjanaParaules, int maxParaulesVersPoema, int colorEstrofa, int colorVers){

        float migAngle = (angleMin + angleMax) / 2f;
        float migRadi = (radiMin + radiMax) /2f;
        float margeAng = PI/100f;
        float numPasses = 20;

        p5.pushStyle();
            p5.fill(colorEstrofa);
            p5.strokeWeight(1.5f);
            p5.beginShape();
                for(float i=0; i<=numPasses; i++) {
                    float angle = p5.lerp(angleMin + margeAng/2f, angleMax - margeAng/2f, i/numPasses);
                    p5.vertex(x + radiMin * cos(angle), y + radiMin*sin(angle));
                }

                for(float i=0; i<=numPasses; i++) {
                    float angle = p5.lerp(angleMax - margeAng/2f, angleMin + margeAng/2f, i/numPasses);
                    p5.vertex(x + radiMax * cos(angle), y + radiMax*sin(angle));
                }
            p5.endShape(p5.CLOSE);

                // Text a,n múmero d'estrofa
            p5.fill(0); p5.textAlign(p5.CENTER, p5.CENTER); p5.textSize(18);
            p5.text("E" + numero, x + migRadi * cos(migAngle), y + migRadi*sin(migAngle));

            // Text amb número de versos de l'estrofa
            p5.fill(50); p5.textAlign(p5.CENTER, p5.CENTER); p5.textSize(14);
            p5.text(getNumVersos(), x + (radiMin -15) * cos(migAngle), y + (radiMin -15)*sin(migAngle));

            for(Vers vers : getVersos()){
                float angle = (angleMax + angleMin)/2f;
                if(getPrimerVersEstrofa() != getDarrerVersEstrofa()) {
                    angle = p5.map(vers.numVers, getPrimerVersEstrofa(), getDarrerVersEstrofa(), angleMin + margeAng / 2f, angleMax - margeAng / 2f);
                }
                float radiQuant = p5.map(vers.getNumParaules(), 0, maxParaulesVersPoema, 0, 300);
                float gruixa = vers.getNumParaules() >= mitjanaParaules ? 2.5f : 1f;
                int colorRadi = vers.getNumParaules() >= mitjanaParaules ? p5.color(0) : colorVers;
                vers.dibuixaVersRadial(p5, x, y, radiMax, angle, radiQuant, gruixa, colorRadi, vers.getNumParaules());
            }


            float mitjanaEstrofa = getMitjanaParaulesVersosEstrofa();


            p5.noFill(); p5.stroke(0, 255, 0);
            float radiMitjana2 = p5.map(mitjanaEstrofa, 0, maxParaulesVersPoema, radiMax, radiMax + 300);
            p5.beginShape();
            for(int i=0; i<=25; i++){
                float angleV = p5.lerp(angleMin, angleMax, i/25f);
                p5.vertex(x + radiMitjana2*cos(angleV), y + radiMitjana2*sin(angleV));
            }
            p5.endShape();

        p5.popStyle();
    }
}
