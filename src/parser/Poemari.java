package parser;

import java.io.File;
import java.util.ArrayList;

import static processing.core.PApplet.nf;

public class Poemari {

    String titol;
    String autor;
    int any;

    ArrayList<Poema> poemes;

    public Poemari(String titol, String autor, int any) {
        this.titol = titol;
        this.autor = autor;
        this.any = any;
        this.poemes = new ArrayList<>();
    }

    public Poema getPoemaAt(int i){ return this.poemes.get(i); }

    public Poema getPoema(String titolPoema){
        for(Poema poema : poemes){
            if(poema.titol.equals(titolPoema)){
                return poema;
            }
        }
        return null;
    }

    public void parsePoemes(int numPoemes, String rutaCarpetaPoemes){
        for(int i=1; i<=numPoemes; i++) {
            try {
                String rutaPoema = rutaCarpetaPoemes + "poema" + nf(i, 2) + ".txt";
                System.out.println("Parsing " + rutaPoema);
                Poema poema = ParserPoema.parse(new File(rutaPoema));
                poema.setTitol(poema.getVersAt(0).text);
                this.poemes.add(poema);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void printInfo(){
        System.out.println("\nPoemari "+ titol +" (" + autor + ". "+any+"):\n");
        for(Poema poema : this.poemes){
            poema.printInfo();
        }
    }
}
