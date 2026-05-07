package stats.test;

import processing.core.PApplet;
import stats.StopWordsCatala;
import stats.WordCounter;

import java.io.File;
import java.util.ArrayList;

public class Test_WordSearch extends PApplet {

    WordCounter wcCorpus;
    ArrayList<File> documents;
    StopWordsCatala sw;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_WordSearch");
    }

    public void settings(){
        size(100, 100);
    }

    public void setup(){

        documents = new ArrayList<>();

        sw = new StopWordsCatala();

        wcCorpus = new WordCounter();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\mariera\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName());
                wcCorpus.processaPoemari(poemari);
                System.out.println();
            }
        }

        String[] termeCerca = {"mà", "mans", "dit", "peu", "cos"};

        int numVegadesCorpus = wcCorpus.getNumOcurrenciesTermes(termeCerca);
        System.out.println("NUM CORPUS: " + numVegadesCorpus);

        for(int numPoemari=0; numPoemari<wcCorpus.getNumPoemaris(); numPoemari++) {

            File poemari = wcCorpus.getPoemariAt(numPoemari);
            System.out.println("POEMARI: "+ poemari.getAbsolutePath() + " "+poemari.getName());

            WordCounter wcPoemari = new WordCounter();
            wcPoemari.processaPoemariStopWords(poemari, sw);
            int numVegadesPoemari = wcPoemari.getNumOcurrenciesTermes(termeCerca);
            System.out.println("NUM POEMARI: " + numVegadesPoemari);

            if(numVegadesPoemari>0){
                File[] poemes = poemari.listFiles();
                int np=1;
                for(File poema : poemes){
                    WordCounter wcPoema = new WordCounter();
                    wcPoema.processaPoemaStopWords(poema, sw);
                    int numVegadesPoema = wcPoema.getNumOcurrenciesTermes(termeCerca);
                    if(numVegadesPoema>0) {
                        System.out.print(np + ": " + numVegadesPoema + ", ");
                    }
                    np++;
                }

            }

            System.out.println();
        }

    }

    public void draw(){
        background(255);
        //wc.display(this);
        noLoop();
    }
}
