package visuals;

import comptes.ComptadorParaules;
import comptes.ParaulesBuidesCatala;
import comptes.TermeFreq;
import gui.NigulParaules;
import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;

public class Visuals_Nigul_Paraules_Poemari_01 extends PApplet {

    ComptadorParaules comptadorParaules;
    ParaulesBuidesCatala paraulesBuidesCatala;
    ArrayList<TermeFreq> termsFreqs;
    NigulParaules nigulParaules;
    int[] paletaColors;
    File carpetaPoemari;
    boolean exportaPDF = false;

    int numParaules = 150;
    int minMidaText = 10, maxMidaText = 100;

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Nigul_Paraules_Poemari_01");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        paraulesBuidesCatala = new ParaulesBuidesCatala();
        comptadorParaules = new ComptadorParaules();

        String rutaPoemari = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\Llibre de Benaventurances (1977)\\";
        carpetaPoemari = new File(rutaPoemari);
        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {
                comptadorParaules.processaPoemaParaulesBuides(poema.getAbsoluteFile(), paraulesBuidesCatala);
                System.out.println();
            }
        }

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
            String nomPDF = "data/pdfs/" + carpetaPoemari.getName() + " - nígul paraules.pdf";
            beginRecord(PDF, nomPDF);
        }

        textFont(createFont("Georgia", 18));
        textSize(18);
        textAlign(CENTER, TOP); fill(0);
        text("Llibre de Benaventurances (1977)", width/2, 0);
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
