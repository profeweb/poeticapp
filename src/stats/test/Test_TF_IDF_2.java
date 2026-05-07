package stats.test;

import processing.core.PApplet;
import stats.StopWordsCatala;
import stats.TermeFreq;
import stats.WordCounter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test_TF_IDF_2 extends PApplet {

    WordCounter wcCorpus;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_TF_IDF_2");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        try {

            FileWriter myWriter = new FileWriter("C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\keywords.txt");


            wcCorpus = new WordCounter();

            String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\mariera\\";
            File carpetaArrel = new File(rutaCareptaArrel);
            File[] subcarpetes = carpetaArrel.listFiles();

            if (subcarpetes != null) {
                for (File poemari : subcarpetes) {
                    System.out.println("POEMARI: " + poemari.getName() + "********************************************************");
                    wcCorpus.processaPoemari(poemari);
                }
            }

            System.out.println("DOCUMENTS PROCESSATS:" + wcCorpus.getNumPoemaris());
            System.out.println("TOTAL TOKENS CORPUS:" + wcCorpus.getNumTerms());

            myWriter.append("TOTAL TOKENS CORPUS:" + wcCorpus.getNumTerms()+"\n");

            for(int numPoemari = 0; numPoemari< wcCorpus.getNumPoemaris(); numPoemari++) {

                File poemari = wcCorpus.getPoemariAt(numPoemari);
                System.out.println("DOCUMENT: "+ poemari.getAbsolutePath() + " "+poemari.getName());
                myWriter.append("DOCUMENT: "+ poemari.getName()+"\n");

                WordCounter wcPoemari = new WordCounter();
                wcPoemari.processaPoemari(poemari);
                System.out.println("TOTAL TOKENS POEMARI:" + wcPoemari.getNumTerms());
                myWriter.append("TOTAL TOKENS POEMARI:" + wcPoemari.getNumTerms()+"\n");


                myWriter.append("KEYWORDS: ");

                TermeFreq[] claus = wcCorpus.getParaulesClauPoemari(10, wcPoemari);
                for (TermeFreq clau : claus) {
                    System.out.print("\t " + clau);

                    myWriter.append("\t " + clau +",");

                }
                System.out.println();
                myWriter.append("\n");
            }

            myWriter.close();

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
