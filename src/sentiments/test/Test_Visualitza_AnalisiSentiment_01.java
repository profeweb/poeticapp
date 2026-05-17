package sentiments.test;

import parser.Poema;
import parser.Poemari;
import processing.core.PApplet;
import sentiments.AnalisiSentiments;
import sentiments.DiccionariSentiments;
import sentiments.TokenAnalitzat;
import sentiments.VersAnalitzat;

import java.util.*;
import java.util.stream.Collectors;

public class Test_Visualitza_AnalisiSentiment_01 extends PApplet {

    public static void main(String[] args) {

        Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");
        Poema p1 = poemari.getPoemaAt(8);

        DiccionariSentiments diccionariSentiments = new DiccionariSentiments();
        diccionariSentiments.imprimeix();

        AnalisiSentiments analisiSentiments = new AnalisiSentiments(diccionariSentiments);
        analisiSentiments.analitzaPoema(p1);

        System.out.println("[ TOP PARAULES PER POLARITAT FINAL ]");

        System.out.println("POSITIVES:");
        ArrayList<TokenAnalitzat> topPos = analisiSentiments.topLexicPositives(10);
        for(TokenAnalitzat ta : topPos){
            System.out.println(ta.getOriginal() + ", " +ta.getPuntuacioBase());
        }

        System.out.println("NEGATIVES:");
        ArrayList<TokenAnalitzat> topNeg = analisiSentiments.topLexicNegatives(10);
        for(TokenAnalitzat ta : topNeg){
            System.out.println(ta.getOriginal() + ", punts base: (" +ta.getPuntuacioBase()+"), punts final: (" + ta.getPuntuacioFinal()+")");
        }


    }

}
