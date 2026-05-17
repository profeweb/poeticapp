package lema.test;

import lema.DiccionariLemesCatalà;

public class Test_DIccionariLemes {

    public static void main(String[] args) {
        String fitxer = "data/diccionaris/lemmatization-ca.txt";
        DiccionariLemesCatalà dicLemes = new DiccionariLemesCatalà(fitxer);
    }
}
