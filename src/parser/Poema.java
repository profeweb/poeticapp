package parser;

import processing.core.PApplet;

import java.util.ArrayList;

import static processing.core.PApplet.*;

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

    public int getNumero(){ return this.numero; }

    public String getTitol(){ return  this.titol; }

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

    public int getNumParaules(){
       int num = 0;
       for(Vers vers : getVersos()){
           num += vers.getNumParaules();
       }
       return num;
    }

    public int getNumSillabes(){
        int num = 0;
        for(Vers vers : getVersos()){
            for(Token token : vers.getParaules()) {
                num += ((Paraula)token).getNumSilabes();
            }
        }
        return num;
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

    public float getMitjanaLletresVersPoema(){
        float sumaLletres = 0;
        for(Estrofa estrofa : estrofes){
            for(Vers vers : estrofa.getVersos()){
                sumaLletres += vers.getNumLletres();
            }
        }
        return sumaLletres / getNumVersos();
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

}
