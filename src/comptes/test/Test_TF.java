package comptes.test;

import comptes.ComptadorParaules;
import comptes.ParaulesBuidesCatala;
import comptes.TermeFreq;
import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;

public class Test_TF extends PApplet {

    ComptadorParaules wc;
    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_TF");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        wc = new ComptadorParaules();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName());
                wc.processaPoemari(poemari);
                System.out.println();
            }
        }

        String terme = "ai";

        System.out.println("TERME: " + terme);
        int numDocuments = wc.getNumPoemaris();
        System.out.println("NUM DOCUMENTS: "+ numDocuments);
        int numOcurrencies = wc.getNumPoemarisContenenTerme(terme);
        System.out.println("APAREIX EN DOCUMENTS: "+ numOcurrencies);

        double idf = wc.freqPoemariInvers(terme);
        System.out.println("IDF: " + idf);

        for(int numDocument=0; numDocument<subcarpetes.length; numDocument++) {

            File documentConcret =subcarpetes[numDocument];
            System.out.println("\nDOCUMENT: "+ documentConcret.getAbsolutePath() + " "+documentConcret.getName());
            ComptadorParaules compteDoc = new ComptadorParaules();
            compteDoc.processaPoemari(documentConcret);
            System.out.println("NUM PARAULES DOCUMENT: " + compteDoc.getNumTerms());

            int vegades = compteDoc.getNumOcurrenciesTerme(terme);
            System.out.println("NUM APARICIONS TERME: " + vegades);

            float tf = wc.freqTermePoemari(terme, documentConcret);
            System.out.println("TF: " + tf);
        }



    }

    public void draw(){
        background(255);
        wc.display(this);
        noLoop();
    }
}
