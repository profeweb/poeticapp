package stats.test;

import processing.core.PApplet;
import stats.ParaulesBuidesCatala;
import stats.TermeFreq;
import stats.ComptadorParaules;

import java.io.File;
import java.util.ArrayList;

public class Test_TF_IDF extends PApplet {

    ComptadorParaules wc;
    ArrayList<File> documents;
    ParaulesBuidesCatala sw;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_TF_IDF");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        documents = new ArrayList<>();

        sw = new ParaulesBuidesCatala();

        wc = new ComptadorParaules();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
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
                        wc.processaPoemaParaulesBuides(poema.getAbsoluteFile(), sw);
                    }
                }
                System.out.println();
            }
        }

        System.out.println("DOCUMENTS PROCESSATS:" +documents.size());

        for(int numDocument=0; numDocument<documents.size(); numDocument++) {

            File documentConcret = documents.get(numDocument);
            System.out.println("DOCUMENT: "+ documentConcret.getAbsolutePath() + " "+documentConcret.getName());

            /*
            List<String> mesOcurrents = wc.ordenarPerOcurrenciaDesc();
            for (int i = 0; i < 15; i++) {
                String terme = mesOcurrents.get(i);
                float tf = wc.termFreq(terme, documentConcret);
                float tfidf = wc.tfIdf(terme, documentConcret);
                boolean conte = wc.contains(terme, documentConcret);
                System.out.println(i + ": " + terme + "(" + wc.getCount(terme) + ") conté:" + conte + ", tf: " + tf + ", tfidf: " + tfidf);
            }
             */

            /*
            int numTortova = wc.getNumDocumentsContain("tortova");
            System.out.println("Num tortova" + numTortova);
            boolean bTortova = wc.contains("Tortova", documentConcret);
            System.out.println("Conté tortova=" + bTortova);
             */

            System.out.println("PARAULES CLAU ("+numDocument+"): ");
            TermeFreq[] claus = wc.getParaulesClauPoemaParaulesBuides(10, documentConcret, sw);
            for (TermeFreq clau : claus) {
                System.out.print("\t " + clau);
            }
            System.out.println();
        }

    }

    public void draw(){
        background(255);
        wc.display(this);
        noLoop();
    }
}
