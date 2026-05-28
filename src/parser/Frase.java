package parser;

import java.util.ArrayList;

public class Frase {

    int numFrase;   // Número de frase dins l'estrofa
    boolean finalAmbPunt;   // Acaba amb punt final
    ArrayList<Vers> versos; // Llista de versos de la frase

    // Constructor
    public Frase(int n){
        this.numFrase = n;
        this.versos = new ArrayList<>();
    }

    // Getters
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
