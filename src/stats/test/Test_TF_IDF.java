package stats.test;

import processing.core.PApplet;
import stats.StopWordsCatala;
import stats.TF_IDF;
import stats.WordCounter;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Test_TF_IDF extends PApplet {

    WordCounter wc;
    ArrayList<File> documents;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_TF_IDF");
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
                if (poemes != null) {
                    for (File poema : poemes) {
                        documents.add(poema);
                        System.out.println("Processant " + poema.getAbsoluteFile());
                        wc.processDocument(poema.getAbsoluteFile(), sw);
                    }
                }
                System.out.println();
            }
        }

        System.out.println("DOCUMENTS PROCESSATS:" +documents.size());

        List<String> mesOcurrents = wc.ordenarPerOcurrenciaDesc();
        for(int i=0; i<15; i++){
            String terme = mesOcurrents.get(i);
            float tf = wc.termFreq(terme, documents.get(1));
            float tfidf = wc.tfIdf(terme, documents.get(1));
            boolean conte = wc.contains(terme, documents.get(1));
            System.out.println(i +": " + terme + "(" + wc.getCount(terme)+") conté:" + conte +", tf: " + tf+", tfidf: " + tfidf);
        }

    }

    public void draw(){
        background(255);
        wc.display(this);
        noLoop();
    }
}
