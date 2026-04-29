package parser;

import java.util.ArrayList;

public class Seccio {

    int numSeccio;
    boolean finalAmbPunt;
    ArrayList<Vers> versos;

    public Seccio(int n){
        this.numSeccio = n;
        this.versos = new ArrayList<>();
    }

    public int getNumVersos(){ return this.versos.size(); }

    public Vers getVersAt(int i){ return  this.versos.get(i); }

    public int getPrimerVersSeccio(){ return this.versos.get(0).numVers; }

    public int getDarrerVersSeccio(){ return this.versos.get(this.versos.size()-1).numVers; }

    public void printSeccio(){
        System.out.println("\nSeccio #"+ numSeccio + " ("+versos.size()+ " versos):");
        for(Vers vers : versos){
            vers.printVers();
        }
        System.out.println();
    }
}
