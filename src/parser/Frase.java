package parser;

import java.util.ArrayList;

public class Frase {

    int numFrase;
    boolean finalAmbPunt;
    ArrayList<Vers> versos;

    public Frase(int n){
        this.numFrase = n;
        this.versos = new ArrayList<>();
    }

    public int getNumVersos(){ return this.versos.size(); }

    public Vers getVersAt(int i){ return  this.versos.get(i); }

    public ArrayList<Vers> getVersos(){ return this.versos; }

    public int getPrimerVersFrase(){ return this.versos.get(0).numVers; }

    public int getDarrerVersFrase(){ return this.versos.get(this.versos.size()-1).numVers; }

    public void printSeccio(){
        System.out.println("\nSeccio #"+ numFrase + " ("+versos.size()+ " versos):");
        for(Vers vers : versos){
            vers.printVers();
        }
        System.out.println();
    }
}
