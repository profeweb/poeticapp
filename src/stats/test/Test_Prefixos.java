package stats.test;

import processing.core.PApplet;
import stats.ComptadorParaules;
import stats.ParaulesBuidesCatala;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test_Prefixos extends PApplet {

    ComptadorParaules wc;
    ParaulesBuidesCatala sw;

    public static void main(String[] args) {
        PApplet.main("stats.test.Test_Prefixos");
    }

    public void settings(){
        size(100, 100);
    }

    public void setup(){

        sw = new ParaulesBuidesCatala();

        wc = new ComptadorParaules();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        int numDocuments = 0;

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName());
                File[] poemes = poemari.listFiles();
                if (poemes != null) {
                    for (File poema : poemes) {
                        System.out.println("Processant " + poema.getAbsoluteFile());
                        wc.processaPoemaParaulesBuides(poema.getAbsoluteFile(), sw);
                        numDocuments++;
                    }
                }
                System.out.println();
            }
        }

        System.out.println("POEMES PROCESSATS:" +numDocuments);

        System.out.println("\nTERMES AMB PREFIX estim-:" );
        ArrayList<String> termesPrefix = wc.termesComencenAmb("estim");
        for(int i=0; i<termesPrefix.size(); i++){
            System.out.println(termesPrefix.get(i) + "\t");
        }

        System.out.println("\nTERMES AMB SUFIX -íssim:" );
        String[] sufixos = {"íssim", "íssima"};
        ArrayList<String> termesSufix = wc.termesAcabenAmb(sufixos);
        Collections.sort(termesSufix);
        for(int i=0; i<termesSufix.size(); i++){
            System.out.println(termesSufix.get(i));
        }

    }

    public void draw(){
        background(255);
        noLoop();
    }
}
