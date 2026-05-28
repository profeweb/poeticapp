package parser;

import java.util.ArrayList;

public class Estrofa {

    int numero;     // Número de l'estrofa dins el poema
    ArrayList<Frase> frases;    // Llista de frases de l'estrofa

    // Constructor
    public Estrofa(int posicio) {
        this.numero = posicio;
        this.frases = new ArrayList<>();
    }

    // Getters
    public int getNumero(){ return this.numero; }

    public int getNumFrases(){ return this.frases.size(); }

    public int getNumVersos(){
        int num = 0;
        for(Frase frase : frases){
            num += frase.getNumVersos();
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
                num += ((Paraula) token).getNumSilabes();
            }
        }
        return num;
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

    public float getMitjanaLletresVersosEstrofa(){
        float numLletres = 0;
        for(Vers vers : getVersos()){
            numLletres += vers.getNumLletres();
        }
        return numLletres / getNumVersos();
    }

    public float getMitjanaSillabesVersosEstrofa(){
        float numSillabes = 0;
        for(Vers vers : getVersos()){
            numSillabes += vers.getNumSillabes();
        }
        return numSillabes / getNumVersos();
    }

    public void printEstrofa(){
        System.out.println("\nEstrofa #"+ numero + " ("+ frases.size()+ " frases):");
        for(Frase frase : frases){
            frase.printSeccio();
        }
        System.out.println();
    }

}
