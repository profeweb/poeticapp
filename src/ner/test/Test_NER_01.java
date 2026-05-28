package ner.test;

import ner.DiccionariEntitats;
import ner.EtiquetadorEntitats;

public class Test_NER_01 {

    public static String rutaFitxer = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\Poemes ocasionals (1981)\\poema03.txt";

    public static void main(String[] args) {

        DiccionariEntitats diccionariEntitats = new DiccionariEntitats();
        diccionariEntitats.imprimeixEntradesDiccionari();

        EtiquetadorEntitats etiquetadorEntitats = new EtiquetadorEntitats(diccionariEntitats);
        etiquetadorEntitats.etiquetaPoema(rutaFitxer);
        //etiquetadorEntitats.mostraTokens();
        etiquetadorEntitats.extrauEntitats();
        //etiquetadorEntitats.mostraEntitats();
        etiquetadorEntitats.estadistiquesEtiquetador();

    }
}
