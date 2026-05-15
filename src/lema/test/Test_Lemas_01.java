package lema.test;

import lema.DiccionariLemesCatalà;
import lema.Lematitzador;
import lema.ResultatToken;

public class Test_Lemas_01 {

    public static final String fitxer = "data/lemmatization-ca.txt";

    public static String[] paraules = {"cantaria", "espardenyes", "donaria", "ahgh", "ser", "caps", "cans"};

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
