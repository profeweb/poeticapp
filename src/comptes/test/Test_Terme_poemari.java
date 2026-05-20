package comptes.test;

import comptes.ComptadorParaules;
import processing.core.PApplet;

import java.io.File;

public class Test_Terme_poemari extends PApplet {

    ComptadorParaules comptadorCorpus;
    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_Terme_poemari");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        comptadorCorpus = new ComptadorParaules();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName());
                comptadorCorpus.processaPoemari(poemari);
                System.out.println();
            }
        }

        String terme = "mà";
        int numPoemari = 5;

        System.out.println("TERME: " + terme);
        int numDocuments = comptadorCorpus.getNumPoemaris();
        System.out.println("NUM DOCUMENTS CORPUS: "+ numDocuments);

        int numOcurrencies = comptadorCorpus.getNumPoemarisContenenTerme(terme);
        System.out.println("APAREIX EN DOCUMENTS: "+ numOcurrencies);

        double idf = comptadorCorpus.freqPoemariInvers(terme);
        System.out.println("IDF: " + idf);

        File documentConcret =subcarpetes[numPoemari];
        System.out.println("\nDOCUMENT: "+ documentConcret.getAbsolutePath() + " "+documentConcret.getName());
        ComptadorParaules compteDoc = new ComptadorParaules();
        compteDoc.processaPoemari(documentConcret);
        System.out.println("NUM PARAULES POEMARI: " + compteDoc.getNumTerms());

        float vegades = compteDoc.getNumOcurrenciesTerme(terme);
        System.out.println("NUM APARICIONS TERME: " + vegades);

        float tf = comptadorCorpus.freqTermePoemari(terme, documentConcret);
        System.out.println("TF: " + tf);


        double tfIdf = comptadorCorpus.tfIdfPoemari(terme, compteDoc);
        System.out.println("TF-IDF: " + tfIdf);

    }

    public void draw(){
        background(255);
        comptadorCorpus.display(this);
        noLoop();
    }
}
