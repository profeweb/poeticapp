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
        Poema p1 = poemari.getPoemaAt(0);

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


        System.out.println("[ TOP PARAULES PER POLARITAT FINAL ]");

        List<TokenAnalitzat> lexicals = analisiSentiments.totalsVersos.stream()
                .flatMap(rv -> rv.getTokens().stream())
                .filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC)
                .collect(Collectors.toList());

        System.out.println("  Top positives:");
        lexicals.stream()
                .filter(t -> t.getPuntuacioFinal() > 0)
                .sorted(Comparator.comparingDouble(TokenAnalitzat::getPuntuacioFinal).reversed())
                .limit(5)
                .forEach(t -> {
                    String bar = "#".repeat((int)(t.getPuntuacioFinal() / 2.0 * 20));
                    System.out.printf("    %+5.1f  %-16s  |%-20s|%n",
                            t.getPuntuacioFinal(), t.getOriginal(), bar);
                });

        System.out.println("  Top negatives:");
        lexicals.stream()
                .filter(t -> t.getPuntuacioFinal() < 0)
                .sorted(Comparator.comparingDouble(TokenAnalitzat::getPuntuacioFinal))
                .limit(5)
                .forEach(t -> {
                    String bar = "#".repeat((int)(Math.abs(t.getPuntuacioFinal()) / 2.0 * 20));
                    System.out.printf("    %+5.1f  %-16s  |%-20s|%n",
                            t.getPuntuacioFinal(), t.getOriginal(), bar);
                });
        System.out.println();


        System.out.println("[ EVOLUCIO DEL SENTIMENT (vers a vers) ]");
        System.out.println("  Eix X: Puntuacio  |  neg <---[0]---> pos  |  Eix Y: Vers");
        System.out.printf("  %-4s |%-40s| %s%n", "Vers", "     -2   -1    0   +1   +2", "Score");

        for (VersAnalitzat rv : analisiSentiments.totalsVersos) {
            System.out.printf("  V%-2d  |%s| %+.2f  %s%n",
                    rv.getNumero(),
                    barraSentiment(rv.getPuntuacio()),
                    rv.getPuntuacio(),
                    iconaSentiment(rv.getSentiment()));
        }
        System.out.println();

        System.out.println("[ RESUM GLOBAL DEL POEMA ]");

        double puntGlobal = analisiSentiments.totalsVersos.stream()
                .mapToDouble(VersAnalitzat::getPuntuacio).average().orElse(0.0);
        AnalisiSentiments.Sentiment sentGlobal = AnalisiSentiments.Sentiment.de(puntGlobal);

        System.out.printf("  Versos analitzats   : %d%n", analisiSentiments.totalsVersos.size());
        System.out.printf("  Tokens totals       : %d%n",
                analisiSentiments.totalsVersos.stream().mapToInt(rv -> rv.getTokens().size()).sum());
        System.out.printf("  Tokens al lexic     : %d%n",
                analisiSentiments.totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
                        .filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC).count());
        System.out.printf("  Negadors detectats  : %d%n",
                analisiSentiments.totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
                        .filter(t -> t.getRol() == AnalisiSentiments.Rol.NEGADOR).count());
        System.out.printf("  Intensificadors     : %d%n",
                analisiSentiments.totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
                        .filter(t -> t.getRol() == AnalisiSentiments.Rol.INTENSIFICADOR).count());
        System.out.println();
        System.out.printf("  Puntuacio global    : %+.3f%n", puntGlobal);
        System.out.printf("  Sentiment global    : %s  %s%n",
                sentGlobal.getEtiqueta(), iconaSentiment(sentGlobal));
        System.out.printf("  Barra visual        : [%s]%n",
                barraSentiment(puntGlobal));
        System.out.println();

        // Distribució per categoría
        System.out.println("  Distribucio de versos per categoria:");
        Map<AnalisiSentiments.Sentiment, Long> dist = analisiSentiments.totalsVersos.stream()
                .collect(Collectors.groupingBy(VersAnalitzat::getSentiment, Collectors.counting()));
        for (AnalisiSentiments.Sentiment s : AnalisiSentiments.Sentiment.values()) {
            long c = dist.getOrDefault(s, 0L);
            if (c > 0) {
                String bar = "#".repeat((int)(c * 4));
                System.out.printf("    %-28s: %2d vers  |%-16s|%n",
                        s.getEtiqueta(), c, bar);
            }
        }

        // Rang de puntuació
        OptionalDouble max = analisiSentiments.totalsVersos.stream()
                .mapToDouble(VersAnalitzat::getPuntuacio).max();
        OptionalDouble min = analisiSentiments.totalsVersos.stream()
                .mapToDouble(VersAnalitzat::getPuntuacio).min();
        System.out.printf("%n  Vers mes positiu    : %+.2f  (V%d)%n",
                max.orElse(0),
                analisiSentiments.totalsVersos.stream()
                        .max(Comparator.comparingDouble(VersAnalitzat::getPuntuacio))
                        .map(VersAnalitzat::getNumero).orElse(0));
        System.out.printf("  Vers mes negatiu    : %+.2f  (V%d)%n",
                min.orElse(0),
                analisiSentiments.totalsVersos.stream()
                        .min(Comparator.comparingDouble(VersAnalitzat::getPuntuacio))
                        .map(VersAnalitzat::getNumero).orElse(0));
    }

    public static String barraSentiment(double puntuacio) {
        final int    AMPLE  = 40;
        final int    CENTRE = AMPLE / 2;
        final double MAX    = 2.0;

        char[] barra = new char[AMPLE];
        Arrays.fill(barra, ' ');
        barra[CENTRE] = '|';

        int fills = (int) Math.round(Math.abs(puntuacio) / MAX * CENTRE);
        fills = Math.min(fills, CENTRE);

        if (puntuacio >= 0) {
            for (int i = CENTRE + 1; i <= CENTRE + fills && i < AMPLE; i++)
                barra[i] = '#';
        } else {
            for (int i = CENTRE - fills; i < CENTRE; i++)
                barra[i] = '#';
        }
        return new String(barra);
    }

    /** Retorna la icona ASCII corresponent al tipus de Sentiment. */
    private static String iconaSentiment(AnalisiSentiments.Sentiment s) {
        return switch (s) {
            case MOLT_POSITIU -> "(+++)";
            case POSITIU      -> "( ++) ";
            case LLEU_POSITIU -> "( +) ";
            case NEUTRE       -> "( 0) ";
            case LLEU_NEGATIU -> "( -) ";
            case NEGATIU      -> "( --)";
            case MOLT_NEGATIU -> "(---)";
        };
    }

}
