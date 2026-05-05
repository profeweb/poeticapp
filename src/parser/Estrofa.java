package parser;

import java.util.ArrayList;

public class Estrofa {

    int numero;
    ArrayList<Frase> seccions;

    public Estrofa(int posicio) {
        this.numero = posicio;
        this.seccions = new ArrayList<>();
    }

    public int getNumero(){ return this.numero; }

    public int getNumSeccions(){ return this.seccions.size(); }

    public int getNumVersos(){
        int num = 0;
        for(Frase frase : seccions){
            num += frase.getNumVersos();
        }
        return  num;
    }

    public int getPrimerVersEstrofa(){ return this.seccions.get(0).getPrimerVersSeccio(); }

    public int getDarrerVersEstrofa(){ return this.seccions.get(seccions.size()-1).getDarrerVersSeccio(); }

    public Vers getVersAt(int i){
        int n=0;
        for(int s=0; s<seccions.size(); s++){
            Frase fraseActual = seccions.get(s);
            if( i < n + fraseActual.getNumVersos()){
                return fraseActual.getVersAt(i - n);
            }
            n += fraseActual.getNumVersos();
        }
        return null;
    }

    public ArrayList<Vers> getVersosSeccioAt(int s){ return seccions.get(s).versos; }

    public ArrayList<Vers> getVersos(){
        ArrayList<Vers> versos = new ArrayList<>();
        for(Frase s : seccions){
            versos.addAll(s.versos);
        }
        return versos;
    }

    public void printEstrofa(){
        System.out.println("\nEstrofa #"+ numero + " ("+seccions.size()+ " seccions):");
        for(Frase frase : seccions){
            frase.printSeccio();
        }
        System.out.println();
    }
}
