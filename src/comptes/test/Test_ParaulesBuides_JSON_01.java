package comptes.test;

import processing.core.PApplet;
import comptes.ComptadorParaules;
import comptes.ParaulesBuidesCatala;

public class Test_ParaulesBuides_JSON_01 extends PApplet {

    public static String rutaJSON = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\diccionaris\\paraules_buides_ca.json";

    ComptadorParaules wc;
    ParaulesBuidesCatala sw;

    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_ParaulesBuides_JSON_01");
    }

    public void settings(){
        size(100, 100);
    }

    public void setup(){

        sw = new ParaulesBuidesCatala(rutaJSON);
        System.out.println("NUM PARAULES BUIDES: "+ sw.getNumParaulesBuides());
    }

    public void draw(){
        background(255);
        noLoop();
    }
}
