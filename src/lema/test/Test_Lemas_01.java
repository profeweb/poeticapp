package lema.test;

import lema.DiccionariLemesCatalà;
import lema.Lematitzador;
import lema.ResultatToken;

public class Test_Lemas_01 {

    // Font: http://www.github.com/michmech/lemmatization-lists/blob/master/lemmatization-ca.txt
    public static final String fitxer = "data/diccionaris/lemmatization-ca.txt";

    public static String[] paraules = {"cantaria", "espardenyes", "donaria", "ah", "ser", "caps", "cans"};

    public static void main(String[] args) {

        // Crea el diccionari de lemes en català
        DiccionariLemesCatalà dicLemes = new DiccionariLemesCatalà(fitxer);

        System.out.println("\nLEMATITZACIÓ DELS TERMES:");
        for(String paraula : paraules){
            ResultatToken resultatToken = Lematitzador.lematitzarToken(paraula, dicLemes);
            System.out.println(paraula + " -> "+ resultatToken);
        }
    }
}
