package parser;

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
