package stats.test;

import processing.core.PApplet;
import stats.ComptadorParaules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test_WordSearch_2 extends PApplet {

    ComptadorParaules wcCorpus;

    String rutaCarpetaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\mariera\\";
    String rutaFitxerSortida = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\";

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_WordSearch_2");
    }

    public void settings(){
        size(100, 100);
    }

    public void setup() {

        String[] termesCerca = {"vida"};
        String nomFitxer = termesCerca[0] + (termesCerca.length > 1 ? " i Derivats" : "");
        System.out.println(nomFitxer);

        wcCorpus = new ComptadorParaules();

        try {

            FileWriter myWriter = new FileWriter(rutaFitxerSortida + nomFitxer +".txt");
            myWriter.append("TERME(S): ");
            for(String terme : termesCerca){
                myWriter.append(terme +"\t");
            }
            myWriter.append("\n");

            File carpetaArrel = new File(rutaCarpetaArrel);
            File[] subcarpetes = carpetaArrel.listFiles();

            if (subcarpetes != null) {
                for (File poemari : subcarpetes) {
                    System.out.println("POEMARI: " + poemari.getName());
                    wcCorpus.processaPoemari(poemari);
                    System.out.println();
                }
            }


            int numVegadesCorpus = wcCorpus.getNumOcurrenciesTermes(termesCerca);
            System.out.println("NUM CORPUS: " + numVegadesCorpus);
            myWriter.append("NUM CORPUS: " + numVegadesCorpus+"\n");

            for (int numPoemari = 0; numPoemari < wcCorpus.getNumPoemaris(); numPoemari++) {

                File poemari = wcCorpus.getPoemariAt(numPoemari);
                System.out.println("POEMARI: " + poemari.getAbsolutePath() + " " + poemari.getName());

                ComptadorParaules wcPoemari = new ComptadorParaules();
                wcPoemari.processaPoemari(poemari);
                int numVegadesPoemari = wcPoemari.getNumOcurrenciesTermes(termesCerca);
                System.out.println("NUM POEMARI: " + numVegadesPoemari);
                myWriter.append("POEMARI " + poemari.getName()+" : " + numVegadesPoemari+"\n");

                if (numVegadesPoemari > 0) {
                    File[] poemes = poemari.listFiles();
                    int np = 1;
                    for (File poema : poemes) {
                        ComptadorParaules wcPoema = new ComptadorParaules();
                        wcPoema.processaPoema(poema);
                        int numVegadesPoema = wcPoema.getNumOcurrenciesTermes(termesCerca);
                        if (numVegadesPoema > 0) {
                            System.out.print(np + ": " + numVegadesPoema + ", ");
                            myWriter.append(np + ": " + numVegadesPoema + ", ");
                        }
                        np++;
                    }

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
        noLoop();
    }
}
