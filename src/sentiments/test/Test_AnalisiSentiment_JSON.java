package sentiments.test;

import sentiments.AnalisiSentiments;
import sentiments.DiccionariSentiments;
import sentiments.TokenAnalitzat;
import sentiments.VersAnalitzat;

import java.util.*;
import java.util.stream.Collectors;

public class Test_AnalisiSentiment_JSON {

    public static String rutaJSON = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\diccionaris\\diccionari_sentiments_ca.json";

    public static void main(String[] args) {

        final String POEMA =
                "A Catalunya, terra bella i plena d'amor,\n" +
                        "el sol porta alegria i pau al cor,\n" +
                        "Verdaguer canta amb molta tendresa,\n" +
                        "i la llum de l'esperança ens abraça.\n" +
                        "\n" +
                        "Però la mort i la guerra assolen,\n" +
                        "l'angoixa i el dolor cremen el cor,\n" +
                        "no hi ha pau ni joia en la foscor,\n" +
                        "i la tristesa omple el silenci fred.\n" +
                        "\n" +
                        "Però l'esperança torna, bella i forta,\n" +
                        "la primavera retorna amb molt d'amor,\n" +
                        "la llibertat floreix, joia per a tots,\n" +
                        "i la vida és l'única pau del cor.";

        DiccionariSentiments diccionariSentiments = new DiccionariSentiments(rutaJSON);
        diccionariSentiments.imprimeix();

        AnalisiSentiments analisiSentiments = new AnalisiSentiments(diccionariSentiments);
        analisiSentiments.analitzaPoema(POEMA);



    }



}
