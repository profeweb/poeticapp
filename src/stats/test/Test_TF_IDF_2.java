package stats.test;

import processing.core.PApplet;
import stats.StopWordsCatala;
import stats.TermeFreq;
import stats.WordCounter;

import java.io.File;
import java.util.ArrayList;

public class Test_TF_IDF_2 extends PApplet {

    WordCounter wc;
    ArrayList<File> documents;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_TF_IDF_2");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        documents = new ArrayList<>();

        StopWordsCatala sw = new StopWordsCatala();

        wc = new WordCounter();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\mariera\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName());
                File[] poemes = poemari.listFiles();
                wc.processaPoemari(poemes);
                /*
                if (poemes != null) {
                    for (File poema : poemes) {
                        documents.add(poema);
                        System.out.println("Processant " + poema.getAbsoluteFile());
                        wc.processaPoema(poema.getAbsoluteFile(), sw);
                    }
                }
                System.out.println();

                 */
            }

        }

    }

    public void draw(){
        background(255);
        wc.display(this);
        noLoop();
    }
}
