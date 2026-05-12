package ner.test;

import ner.DiccionariEntitats;
import ner.EtiquetadorEntitats;

public class Test_NER_01 {

    // ── Poema d'exemple (inspirat en la tradició poètica catalana) ────
    public static String rutaFitxer = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\poemes ocasionals\\poema03.txt";

    public static void main(String[] args) {

        DiccionariEntitats diccionariEntitats = new DiccionariEntitats();
        diccionariEntitats.imprimeixEntradesDiccionari();

        EtiquetadorEntitats etiquetadorEntitats = new EtiquetadorEntitats(diccionariEntitats);
        etiquetadorEntitats.tokenitzaPoema(rutaFitxer);
        etiquetadorEntitats.mostraTokens();
        etiquetadorEntitats.extrauEntitats();
        etiquetadorEntitats.mostraEntitats();
        etiquetadorEntitats.estadistiquesEtiquetador();

    }
}
