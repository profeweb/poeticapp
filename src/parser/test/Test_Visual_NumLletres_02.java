package parser.test;

import parser.Poemari;
import parser.Visualitzacions;
import processing.core.PApplet;

public class Test_Visual_NumLletres_02 extends PApplet {

    Poemari poemari;
    int[] colors;
    int numPoema =  0;

    public static void main(String[] args) {
        PApplet.main("parser.test.Test_Visual_NumLletres_02");
    }

    public void settings(){ size(1920, 1080); }

    public void setup(){
        poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");

        colors = new int[22];
        for(int i=0; i<colors.length; i++){
            colors[i] = lerpColor(color(200), color(100), ((float)i)/colors.length);
        }
    }

    public void draw(){
        background(255);

        int maxLletresVers = poemari.getMaxLletresVersosPoemari();
        float mitjanaLletresVers = poemari.getMitjanaLletresVersosPoemari();

        textAlign(LEFT); fill(255, 0, 0); textSize(14);
        text(mitjanaLletresVers + " lletres / vers (llibre)", 100, height-150);

        float angInic   = numPoema%2 == 0 ? PI : PI;
        float angFi     = numPoema%2 ==0  ? 0 : TWO_PI;

        Visualitzacions.dibuixaEstrofesArc(this, poemari.getPoemaAt(numPoema), Visualitzacions.QUANTITAT.LLETRES,  width/2, height/2, 120, 200, angInic, angFi, mitjanaLletresVers, maxLletresVers, colors, color(200, 100, 100));


    }

    public void keyPressed(){
        if(keyCode==UP){
            if(numPoema<poemari.getNumPoemes()-1){
                numPoema++;
            }
        }

        else if(keyCode==DOWN){
            if(numPoema>0){
                numPoema--;
            }
        }

    }
}
