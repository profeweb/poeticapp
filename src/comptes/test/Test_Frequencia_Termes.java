package comptes.test;

import processing.core.PApplet;
import comptes.ParaulesBuidesCatala;
import comptes.ComptadorParaules;

import java.io.File;
import java.util.List;

public class Test_Frequencia_Termes extends PApplet {

    ComptadorParaules wc;
    ParaulesBuidesCatala sw;

    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_Frequencia_Termes");
    }

    public void settings(){
        size(1920, 1080);
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

        int numTermes = 25;
        List<String> mesOcurrents = wc.ordenarPerOcurrenciaDesc();
        for(int i=0; i<numTermes; i++){
            int num = i +1;
            String terme = mesOcurrents.get(i);
            int numVegades = wc.getNumOcurrenciesTerme(terme);
            System.out.println(num +": " + terme + "(" + numVegades+")");
        }

    }

    public void draw(){
        background(255);
        wc.display(this);
        noLoop();
    }
}
