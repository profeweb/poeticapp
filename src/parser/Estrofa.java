package parser;

import java.util.ArrayList;

public class Estrofa {

    int numero;
    ArrayList<Seccio> seccions;

    public Estrofa(int posicio) {
        this.numero = posicio;
        this.seccions = new ArrayList<>();
    }

    public int getNumero(){ return this.numero; }

    public int getNumSeccions(){ return this.seccions.size(); }

    public int getNumVersos(){
        int num = 0;
        for(Seccio seccio : seccions){
            num += seccio.getNumVersos();
        }
        return  num;
    }

    public Vers getVersAt(int i){
        int n=0;
        for(int s=0; s<seccions.size(); s++){
            Seccio seccioActual = seccions.get(s);
            if( i < n + seccioActual.getNumVersos()){
                return seccioActual.getVersAt(i - n);
            }
            n += seccioActual.getNumVersos();
        }
        return null;
    }

    public ArrayList<Vers> getVersosSeccioAt(int s){ return seccions.get(s).versos; }

    public ArrayList<Vers> getVersos(){
        ArrayList<Vers> versos = new ArrayList<>();
        for(Seccio s : seccions){
            versos.addAll(s.versos);
        }
        return versos;
    }

    public void printEstrofa(){
        System.out.println("\nEstrofa #"+ numero + " ("+seccions.size()+ " seccions):");
        for(Seccio seccio : seccions){
            seccio.printSeccio();
        }
        System.out.println();
    }
}
