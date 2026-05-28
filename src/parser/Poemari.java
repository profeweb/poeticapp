package parser;

import processing.core.PApplet;

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

    public String getTitol(){ return  this.titol; }
    public String getAutor(){ return  this.autor; }
    public int getAny(){ return  this.any; }

    public int getNumPoemes(){ return this.poemes.size(); }

    public ArrayList<Poema> getPoemes(){ return this.poemes; }

    public int getNumEstrofes(){
        int num = 0;
        for(Poema poema : poemes){
            num += poema.getNumEstrofes();
        }
        return num;
    }

    public int getNumVersos(){
        int num = 0;
        for(Poema poema : poemes){
            num += poema.getNumVersos();
        }
        return num;
    }

    public int getNumParaules(){
        int num = 0;
        for(Poema poema : poemes) {
            for (Estrofa estrofa : poema.getEstrofes()) {
                for (Vers vers : estrofa.getVersos()) {
                    num += vers.getNumParaules();
                }
            }
        }
        return num;
    }

    public int getNumSilabes(){
        int num = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.getEstrofes()){
                for(Vers vers : estrofa.getVersos()){
                    for(Token paraula : vers.getParaules()){
                        num += ((Paraula) paraula).getNumSilabes();
                    }
                }
            }
        }
        return num;
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
                poema.numero = i;
                this.poemes.add(poema);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public int getMaxVersosEstrofes(){
        int maxVersos = 0;
        for(Poema poema: poemes){
            if(poema.getMaxVersosEstrofes() > maxVersos){
                maxVersos = poema.getMaxVersosEstrofes();
            }
        }
        return maxVersos;
    }

    public int getMaxParaulesVersosPoemari(){
        int maxParaules = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    if(vers.getNumParaules() > maxParaules){
                        maxParaules = vers.getNumParaules();
                    }
                }
            }
        }
        return maxParaules;
    }

    public int getMaxLletresVersosPoemari(){
        int maxLletres = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    if(vers.getNumLletres() > maxLletres){
                        maxLletres = vers.getNumLletres();
                    }
                }
            }
        }
        return maxLletres;
    }

    public int getMaxSillabesVersosPoemari(){
        int maxSillabes = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    if(vers.getNumSillabes() > maxSillabes){
                        maxSillabes = vers.getNumSillabes();
                    }
                }
            }
        }
        return maxSillabes;
    }

    public float getMitjanaParaulesVersosPoemari(){
        int numParaules =  0;
        int numVersos = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    numParaules += vers.getNumParaules();
                    numVersos++;
                }
            }
        }
        return ((float)numParaules) / numVersos;
    }

    public float getMitjanaLletresVersosPoemari(){
        int numLletres =  0;
        int numVersos = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    numLletres += vers.getText().length();
                    numVersos++;
                }
            }
        }
        return ((float)numLletres) / numVersos;
    }

    public float getMitjanaSillabesVersosPoemari(){
        int numSillabes =  0;
        int numVersos = 0;
        for(Poema poema : poemes){
            for(Estrofa estrofa : poema.estrofes){
                for(Vers vers: estrofa.getVersos()){
                    numSillabes += vers.getNumSillabes();
                    numVersos++;
                }
            }
        }
        return ((float)numSillabes) / numVersos;
    }

    public int getNumOcurrenciesTerme(String terme){
        int num = 0;
        for(Poema poema : poemes){
            for(Vers vers : poema.getVersos()){
                for(Token token : vers.getParaules()){
                    if (token.getValor().toLowerCase().equals(terme)){
                        num++;
                    }
                }
            }
        }
        return num;
    }

    public ArrayList<Integer> getVersosOcurrenciesTerme(String terme){
        ArrayList<Integer> numVersos = new ArrayList<>();
        for(Poema poema : poemes){
            for(Vers vers : poema.getVersos()){
                for(Token token : vers.getParaules()){
                    if (token.getValor().toLowerCase().equals(terme)){
                        numVersos.add(vers.getNumVers());
                    }
                }
            }
        }
        return numVersos;
    }

    public void printInfo(){
        System.out.println("\nPoemari "+ titol +" (" + autor + ". "+any+"):\n");
        for(Poema poema : this.poemes){
            poema.printInfo();
        }
    }

}
