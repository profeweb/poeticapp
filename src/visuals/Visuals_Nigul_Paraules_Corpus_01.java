package visuals;

import comptes.ComptadorParaules;
import comptes.ParaulesBuidesCatala;
import comptes.TermeFreq;
import gui.NigulParaules;
import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;

public class Visuals_Nigul_Paraules_Corpus_01 extends PApplet {

    ComptadorParaules comptadorParaules;
    ParaulesBuidesCatala paraulesBuidesCatala;
    ArrayList<TermeFreq> termsFreqs;
    boolean exportaPDF = false;

    NigulParaules nigulParaules;
    int numParaules = 150;
    int minMidaText = 10, maxMidaText = 100;
    int[] paletaColors;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Nigul_Paraules_Corpus_01");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        paraulesBuidesCatala = new ParaulesBuidesCatala();
        comptadorParaules = new ComptadorParaules();

        String rutaCareptaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\";
        File carpetaArrel = new File(rutaCareptaArrel);
        File[] subcarpetes = carpetaArrel.listFiles();

        if (subcarpetes != null) {
            for (File poemari : subcarpetes) {
                System.out.println("POEMARI: " + poemari.getName() + "********************************************************");
                comptadorParaules.processaPoemariParaulesBuides(poemari, paraulesBuidesCatala);
            }
        }

        System.out.println("DOCUMENTS PROCESSATS:" + comptadorParaules.getNumPoemaris());
        System.out.println("TOTAL TOKENS CORPUS:" + comptadorParaules.getNumTerms());

        termsFreqs = comptadorParaules.getTermesFreqs();

        paletaColors = new int[5];
        paletaColors[0] = color(100, 180, 220);   // blau clar   (baix)
        paletaColors[1] = color( 80, 160, 130);   // verd menta
        paletaColors[2] = color(240, 200,  60);   // groc daurat
        paletaColors[3] = color(230, 120,  40);   // taronja
        paletaColors[4] = color(200,  40,  60);   // vermell fosc (alt)

        nigulParaules = new NigulParaules(0, 0, width, height);
        nigulParaules.setTermes(termsFreqs);
        nigulParaules.situaParaulesNigul(this, numParaules, minMidaText, maxMidaText, paletaColors);
    }

    public void draw(){
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/Corpus MA Riera - nígul paraules.pdf";
            beginRecord(PDF, nomPDF);
        }

        textFont(createFont("Georgia", 18));
        textSize(18);
        textAlign(CENTER, TOP); fill(0);
        text("Miquel Àngel Riera", width/2, 0);
        nigulParaules.display(this);
        nigulParaules.dibuixaLlegenda(this, "Ocurrències", paletaColors, minMidaText, maxMidaText, width/2f - 110, 50, 220, 18);


        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }

        noLoop();
    }

    public void keyPressed(){
        if(key=='s' || key=='S'){
            exportaPDF = true;
        }
    }
}
