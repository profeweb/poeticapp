package parser;

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Poema {

    int numero;
    String titol;
    ArrayList<Estrofa> estrofes;

    public Poema(int numero) {
        this.titol = "";
        this.numero = numero;
        this.estrofes = new ArrayList<>();
    }

    public Poema(String titol, int numero) {
        this.titol = titol;
        this.numero = numero;
        this.estrofes = new ArrayList<>();
    }

    public void setTitol(String titol){ this.titol = titol; }

    public ArrayList<Estrofa> getEstrofes(){ return this.estrofes; }

    public int getNumEstrofes(){ return this.estrofes.size(); }

    public int getNumVersos(){
        int num = 0;
        for(Estrofa estrofa : estrofes){
            num += estrofa.getNumVersos();
        }
        return  num;
    }

    public int getMaxVersosEstrofes(){
        int maxVersos = 0;
        for(Estrofa estrofa: estrofes){
            if(estrofa.getNumVersos() > maxVersos){
                maxVersos = estrofa.getNumVersos();
            }
        }
        return maxVersos;
    }

    public int getMaxParaulesVersos(){
        int maxParaules = 0;
        for(Estrofa estrofa: estrofes){
            for(Vers vers: estrofa.getVersos()){
                if(vers.getNumParaules() > maxParaules){
                    maxParaules = vers.getNumParaules();
                }
            }
        }
        return maxParaules;
    }

    public float getMitjanaParaulesVersPoema(){
        float sumaParaules = 0;
        for(Estrofa estrofa : estrofes){
            for(Vers vers : estrofa.getVersos()){
                sumaParaules += vers.getNumParaules();
            }
        }
        return sumaParaules / getNumVersos();
    }

    public ArrayList<Vers> getVersos(){
        ArrayList<Vers> versos = new ArrayList<>();
        for(Estrofa estrofa : estrofes){
            versos.addAll(estrofa.getVersos());
        }
        return versos;
    }

    public Vers getVersAt(int i){
        int n = 0;
        for(int e = 0; e < estrofes.size(); e++){
            Estrofa estrofaActual = estrofes.get(e);
            if(i < n + estrofaActual.getNumVersos()){
                return estrofaActual.getVersAt(i - n);
            }
            n += estrofaActual.getNumVersos();
        }
        return null;
    }

    public Estrofa getEstrofaAt(int i){ return  this.estrofes.get(i); }

    public void printInfo(){
        System.out.println("Poema "+ numero+" - "+ titol +" (" + estrofes.size()+ " estrofes):\n");
        for(Estrofa e : this.estrofes){
            e.printEstrofa();
        }
        System.out.println();
    }


    public void dibuixaEstrofesBloc(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers, float mitjanaPoemari, float mitjanaPoema, int maxParaulesVers){

        p5.pushStyle();
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text("P" + numero, x + w/2, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(titol.substring(0, 15) + "...", x + w/2, y);
        p5.textSize(14); p5.fill(0, 0, 255);
        p5.text(nf(mitjanaPoema, 0, 2) +" paraules / vers (poema)", x + w/2, y + 25);

        float xMitjaPoemari = x + 25 + p5.map(mitjanaPoemari, 0, maxParaulesVers, 0, w-50);
        float xMitjaPoema = x + 25 + p5.map(mitjanaPoema, 0, maxParaulesVers, 0, w-50);

        float yEstrofa = y + 25;
        for(Estrofa estrofa : estrofes){

            float mitjanaEstrofa = estrofa.getMitjanaParaulesVersosEstrofa();

            estrofa.dibuixaVersosEstrofaLinia(p5, x, yEstrofa, w, h, colorEstrofa, colorVers, maxParaulesVers, mitjanaPoemari);

            p5.stroke(255, 0, 0);
            p5.line(xMitjaPoemari, yEstrofa, xMitjaPoemari, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 0, 255);
            p5.line(xMitjaPoema, yEstrofa, xMitjaPoema, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 255, 0);
            float xMitjaEstrofa = x + 25 + p5.map(mitjanaEstrofa, 0, maxParaulesVers, 0, w-50);
            p5.line(xMitjaEstrofa, yEstrofa, xMitjaEstrofa, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.fill(0, 255, 0); p5.textSize(14);
            p5.textAlign(p5.CENTER, p5.BOTTOM);
            p5.text(nf(mitjanaEstrofa, 0, 2) + " paraules / vers (estrofa)", x + w/2, yEstrofa + h * (estrofa.getNumVersos() + 1) + 25);

            yEstrofa += (h * estrofa.getNumVersos() + 1) + 50;
        }

        p5.popStyle();
    }


    public void dibuixaEstrofesArc(PApplet p5, float x, float y, float minRadi, float maxRadi, float angInici, float angFi, float mitjanaPoemari, int maxParaulesVers, int[] colorEstrofa, int colorVers){

        p5.pushStyle();

        // Número i títol del Poema
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text("P" + numero, x, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(titol.substring(0, min(15, titol.length())) + "...", x, y);

        p5.noFill(); p5.stroke(0);
        p5.circle(x, y, minRadi*1.5f);

        float angleVers = (angFi - angInici) / getNumVersos();
        float angle = angInici;
        int numEstrofa = 0;
        for(Estrofa estrofa : estrofes){
            float angleEstrofa = angleVers * estrofa.getNumVersos();
            estrofa.dibuixaEstrofaArc(p5, x, y, minRadi, maxRadi, angle , angle +  angleEstrofa, mitjanaPoemari, maxParaulesVers, colorEstrofa[numEstrofa], colorVers);
            angle += (angleEstrofa);
            numEstrofa++;
        }
        p5.popStyle();

        // Dibuixa la mitjana  a nivell de poemari
        p5.noFill(); p5.stroke(255, 0, 0);
        float radiMitjana = p5.map(mitjanaPoemari, 0, maxParaulesVers, maxRadi, maxRadi + 300);
        p5.beginShape();
        for(int i=0; i<=25; i++){
            float angleV = p5.lerp(angInici, angFi, i/25f);
            p5.vertex(x + radiMitjana*cos(angleV), y + radiMitjana*sin(angleV));
        }
        p5.endShape();


        // Dibuixa la mitjana a nivell de poema
        float mitjanaPoema = getMitjanaParaulesVersPoema();
        p5.fill(0, 0, 255);
        p5.text(mitjanaPoema+ " paraules / vers (poema)", 100, p5.height-100);

        p5.noFill(); p5.stroke(0, 0, 255);
        float radiMitjana2 = p5.map(mitjanaPoema, 0, maxParaulesVers, maxRadi, maxRadi + 300);
        p5.beginShape();
        for(int i=0; i<=25; i++){
            float angleV = p5.lerp(angInici, angFi, i/25f);
            p5.vertex(x + radiMitjana2*cos(angleV), y + radiMitjana2*sin(angleV));
        }
        p5.endShape();

    }


}
