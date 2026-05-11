package parser;

import java.util.ArrayList;

public class Autor {

    String nom;
    int any;
    ArrayList<Poemari> poemaris;

    public Autor(String nom, int any) {
        this.nom = nom;
        this.any = any;
        this.poemaris = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public int getAny() {
        return any;
    }

    public ArrayList<Poemari> getPoemaris() {
        return poemaris;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAny(int any) {
        this.any = any;
    }

    public void afegirPoemari(Poemari poemari){
        poemaris.add(poemari);
    }

    // Dades estadistiques


    public int getNumPoemaris(){ return this.poemaris.size(); }

    public int getPrimerPoemari(){
        int any = Integer.MAX_VALUE;
        for(Poemari poemari : poemaris){
            if(poemari.any < any){
                any = poemari.any;
            }
        }
        return any;
    }

    public int getDarrerPoemari(){
        int any = Integer.MIN_VALUE;
        for(Poemari poemari : poemaris){
            if(poemari.any > any){
                any = poemari.any;
            }
        }
        return any;
    }

}
