package ner.test;

import ner.DiccionariEntitats;
import ner.EtiquetadorEntitats;
import processing.core.PApplet;

public class Test_NER_JSON_01 extends PApplet {

    public static String rutaFitxer = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\Poemes ocasionals (1981)\\poema03.txt";
    public static String rutaJSON = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\diccionaris\\entitats_nomenades.json";

    public static void main(String[] args) {

        PApplet.main("ner.test.Test_NER_JSON_01");

    }

    public void setup(){

        DiccionariEntitats diccionariEntitats = new DiccionariEntitats(rutaJSON);
        diccionariEntitats.imprimeixEntradesDiccionari();

        EtiquetadorEntitats etiquetadorEntitats = new EtiquetadorEntitats(diccionariEntitats);
        etiquetadorEntitats.etiquetaPoema(rutaFitxer);
        //etiquetadorEntitats.mostraTokens();
        etiquetadorEntitats.extrauEntitats();
        //etiquetadorEntitats.mostraEntitats();
        etiquetadorEntitats.estadistiquesEtiquetador();
    }
}
