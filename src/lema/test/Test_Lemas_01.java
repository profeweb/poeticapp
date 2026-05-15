package lema.test;

import lema.DiccionariLemesCatalà;
import lema.Lematitzador;
import lema.ResultatToken;

public class Test_Lemas_01 {

    public static void main(String[] args) {
        String fitxer = "data/lemmatization-ca.txt";
        DiccionariLemesCatalà dicLemes = new DiccionariLemesCatalà(fitxer);

        String[] paraules = {"cantaria", "espardenyes", "donaria", "ahgh", "ser", "caps", "cans"};

        for(String paraula : paraules){
            ResultatToken resultatToken = Lematitzador.lematitzarToken(paraula, dicLemes);
            System.out.println(paraula + " -> "+ resultatToken.toString());
        }
    }
}
