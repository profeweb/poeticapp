package comptes.test;

import comptes.ComptadorParaules;
import comptes.TermeFreq;
import processing.core.PApplet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test_ParaulesClau extends PApplet {

    ComptadorParaules wcCorpus;

    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_ParaulesClau");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        try {

            FileWriter fitxer = new FileWriter("C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\stats\\claus2.txt");

            wcCorpus = new ComptadorParaules();

            String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
            File carpetaArrel = new File(rutaCareptaArrel);
            File[] subcarpetes = carpetaArrel.listFiles();

            if (subcarpetes != null) {
                for (File poemari : subcarpetes) {
                    System.out.println("POEMARI: " + poemari.getName() + "********************************************************");
                    wcCorpus.processaPoemari(poemari);
                }
            }

            System.out.println("DOCUMENTS PROCESSATS:" + wcCorpus.getNumPoemaris());
            fitxer.append("DOCUMENTS PROCESSATS:" + wcCorpus.getNumPoemaris()+"\n");
            System.out.println("TOTAL TOKENS CORPUS:" + wcCorpus.getNumTerms());
            fitxer.append("TOTAL TOKENS CORPUS:" + wcCorpus.getNumTerms()+"\n\n");

            for(int numPoemari = 0; numPoemari< wcCorpus.getNumPoemaris(); numPoemari++) {

                File poemari = wcCorpus.getPoemariAt(numPoemari);
                System.out.println("DOCUMENT: "+ poemari.getAbsolutePath() + " "+poemari.getName());
                fitxer.append("DOCUMENT: "+ poemari.getName()+"\n");

                ComptadorParaules wcPoemari = new ComptadorParaules();
                wcPoemari.processaPoemari(poemari);

                System.out.println("TOTAL TOKENS POEMARI:" + wcPoemari.getNumTerms());
                fitxer.append("TOTAL TOKENS POEMARI:" + wcPoemari.getNumTerms()+"\n");


                System.out.println("\nPARAULES CLAU: ");
                fitxer.append("PARAULES CLAU: ");

                TermeFreq[] claus = wcCorpus.getParaulesClauPoemari(10, wcPoemari);
                for (TermeFreq clau : claus) {
                    System.out.print("\t " + clau);
                    fitxer.append("\t " + clau +",");

                }
                System.out.println();
                fitxer.append("\n\n");
            }

            fitxer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(){
        background(255);
        wcCorpus.display(this);
        noLoop();
    }
}
