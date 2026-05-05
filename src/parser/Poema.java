package parser;

import processing.core.PApplet;

import java.util.ArrayList;

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

    public float mitjanaParaulesVersPoema(){
        int sumaParaules = 0;
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


    public void dibuixaEstrofesBloc(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers, float mitjanaPoemari, int maxParaulesVers){

        p5.pushStyle();
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(numero, x + w/2, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(titol.substring(0, 15) + "...", x + w/2, y);

        float yEstrofa = y;
        for(Estrofa estrofa : estrofes){

            estrofa.dibuixaVersosEstrofaLinia(p5, x, yEstrofa, w, h, colorEstrofa, colorVers, maxParaulesVers, mitjanaPoemari);

            p5.stroke(255);
            float xMitja = x + 25 + p5.map(mitjanaPoemari, 0, maxParaulesVers, 0, w-50);
            p5.line(xMitja, yEstrofa, xMitja, yEstrofa + h * (estrofa.getNumVersos() + 1));
            yEstrofa += (h * estrofa.getNumVersos() + 1) + 35;
        }

        p5.popStyle();
    }


    public void dibuixaEstrofesArc(PApplet p5, float x, float y, float minRadi, float maxRadi){

        p5.pushStyle();
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(numero, x, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(titol.substring(0, 15) + "...", x, y);

        p5.noFill(); p5.stroke(0);
        p5.circle(x, y, minRadi*2);


        p5.popStyle();

    }


}
