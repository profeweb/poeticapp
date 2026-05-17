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
        return (float)numParaules / numVersos;
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
        return (float)numLletres / numVersos;
    }

    public void printInfo(){
        System.out.println("\nPoemari "+ titol +" (" + autor + ". "+any+"):\n");
        for(Poema poema : this.poemes){
            poema.printInfo();
        }
    }

    public void dibuixaNumParaulesPoemesBlocs(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers){

        float mitjanaParaulesPoemari = getMitjanaParaulesVersosPoemari();
        int maxParaules = getMaxParaulesVersosPoemari();
        float xPoema = x;
        for(Poema poema : poemes){
            poema.dibuixaNumParaulesEstrofesBloc(p5, xPoema, y + 80 , w, h, colorEstrofa, colorVers, mitjanaParaulesPoemari, poema.getMitjanaParaulesVersPoema(), maxParaules);
            xPoema += w + 75;
        }

        p5.pushStyle();
        p5.fill(0); p5.textSize(48);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(titol, (x + xPoema)/2f, y);

        // Dibuixa Text de Mitjana per Poemari
        p5.fill(255, 0, 0); p5.textSize(18);
        p5.text(nf(mitjanaParaulesPoemari, 0, 2) + " paraules / vers (llibre)", (x + xPoema)/2f, y + 24);
        p5.popStyle();
    }

    public void dibuixaNumLletresPoemesBlocs(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers){

        float mitjanaLletresPoemari = getMitjanaLletresVersosPoemari();
        int maxLletres = getMaxLletresVersosPoemari();
        float xPoema = x;
        for(Poema poema : poemes){
            poema.dibuixaNumLletresEstrofesBloc(p5, xPoema, y + 80 , w, h, colorEstrofa, colorVers, mitjanaLletresPoemari, poema.getMitjanaLletresVersPoema(), maxLletres);
            xPoema += w + 75;
        }

        p5.pushStyle();
        p5.fill(0); p5.textSize(48);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(titol, (x + xPoema)/2f, y);

        // Dibuixa Text de Mitjana per Poemari
        p5.fill(255, 0, 0); p5.textSize(18);
        p5.text(nf(mitjanaLletresPoemari, 0, 2) + " lletres / vers (llibre)", (x + xPoema)/2f, y + 24);
        p5.popStyle();
    }

}
