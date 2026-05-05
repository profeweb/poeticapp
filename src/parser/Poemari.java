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

    public void printInfo(){
        System.out.println("\nPoemari "+ titol +" (" + autor + ". "+any+"):\n");
        for(Poema poema : this.poemes){
            poema.printInfo();
        }
    }

    public void dibuixaPoemesBlocs(PApplet p5, float x, float y, float w, float h, int colorEstrofa, int colorVers){

        float mitjanaParaules = getMitjanaParaulesVersosPoemari();
        int maxParaules = getMaxParaulesVersosPoemari();
        float xPoema = x;
        for(Poema poema : poemes){
            poema.dibuixaEstrofesBloc(p5, xPoema, y + 48 , w, h, colorEstrofa, colorVers, mitjanaParaules, maxParaules);
            xPoema += w + 50;
        }

        p5.pushStyle();
        p5.fill(0); p5.textSize(48);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(titol, (x + xPoema)/2f, y);

        p5.fill(0); p5.textAlign(p5.LEFT); p5.textSize(18);
        p5.text(getMitjanaParaulesVersosPoemari() + " paraules / vers.", x, y + p5.height -100);
        p5.popStyle();
    }

}
