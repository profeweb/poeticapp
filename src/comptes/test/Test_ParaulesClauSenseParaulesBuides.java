package comptes.test;

import comptes.ComptadorParaules;
import comptes.ParaulesBuidesCatala;
import comptes.TermeFreq;
import processing.core.PApplet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test_ParaulesClauSenseParaulesBuides extends PApplet {

    int numParaulesClaus = 25;
    ComptadorParaules wcCorpus;
    ParaulesBuidesCatala paraulesBuidesCatala;

    public static String rutaCarpetaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
    public static String rutaJsonParalesBuides = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\diccionaris\\paraules_buides_ca.json";
    public static String rutaFitxerSortida = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\stats\\clausSenseBuides.txt";

    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_ParaulesClauSenseParaulesBuides");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        paraulesBuidesCatala = new ParaulesBuidesCatala(rutaJsonParalesBuides);

        try {

            FileWriter fitxer = new FileWriter(rutaFitxerSortida);

            wcCorpus = new ComptadorParaules();

            File carpetaArrel = new File(rutaCarpetaArrel);
            File[] subcarpetes = carpetaArrel.listFiles();

            if (subcarpetes != null) {
                for (File poemari : subcarpetes) {
                    System.out.println("POEMARI: " + poemari.getName() + "********************************************************");
                    wcCorpus.processaPoemariParaulesBuides(poemari, paraulesBuidesCatala);
                }
            }

            System.out.println("Total poemaris processats:" + wcCorpus.getNumPoemaris());
            fitxer.append("Total poemaris processats:" + wcCorpus.getNumPoemaris()+"\n");
            System.out.println("Total Tokens del Corpus:" + wcCorpus.getNumTerms());
            fitxer.append("Total Tokens del Corpus:" + wcCorpus.getNumTerms()+"\n\n");

            for(int numPoemari = 0; numPoemari< wcCorpus.getNumPoemaris(); numPoemari++) {

                File poemari = wcCorpus.getPoemariAt(numPoemari);
                System.out.println("Poemari: "+ poemari.getAbsolutePath() + " "+poemari.getName());
                fitxer.append("Poemari: "+ poemari.getName()+"\n");

                ComptadorParaules wcPoemari = new ComptadorParaules();
                wcPoemari.processaPoemariParaulesBuides(poemari, paraulesBuidesCatala);

                System.out.println("Total Tokens del Poemari:" + wcPoemari.getNumTerms());
                fitxer.append("Total Tokens del Poemari:" + wcPoemari.getNumTerms()+"\n");


                System.out.println("\nParaules Clau: ");
                fitxer.append("Paraules Clau: ");

                TermeFreq[] claus = wcCorpus.getParaulesClauPoemari(numParaulesClaus, wcPoemari);
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

        noLoop();

    }

}
