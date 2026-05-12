package sentiments.test;

import sentiments.AnalisiSentiments;
import sentiments.TokenAnalitzat;
import sentiments.VersAnalitzat;

import java.util.*;
import java.util.stream.Collectors;

public class Test_AnalisiSentiment {

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

        AnalisiSentiments tagger = new AnalisiSentiments();

        List<List<VersAnalitzat>> estrofes    = tagger.analitzaPoema(POEMA);
        List<VersAnalitzat>       totalsVersos = estrofes.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        System.out.println("  Analisi de Sentiments  —  Poesia Catalana (Lexicon-Based)");
        System.out.printf("  Lexic: %d paraules  |  Negadors: %d  |  Modificadors: %d%n",
                tagger.lexic.size(), tagger.negadors.size(), tagger.modificadors.size());


        System.out.println("[ LEXIC DE POLARITATS (mostra) ]");

        // Paraules molt positives
        System.out.print("  Molt pos (>=+1.5): ");
        tagger.lexic.entrySet().stream()
            .filter(e -> e.getValue() >= 1.5)
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(8)
            .forEach(e -> System.out.printf("%s(%.1f) ", e.getKey(), e.getValue()));
        System.out.println();

        // Paraules positives
        System.out.print("  Positiu (+0.5/+1.4): ");
        tagger.lexic.entrySet().stream()
            .filter(e -> e.getValue() >= 0.5 && e.getValue() < 1.5)
            .limit(8)
            .forEach(e -> System.out.printf("%s(%.1f) ", e.getKey(), e.getValue()));
        System.out.println();

        // Paraules negatives
        System.out.print("  Negatiu (-0.5/-1.4): ");
        tagger.lexic.entrySet().stream()
            .filter(e -> e.getValue() <= -0.5 && e.getValue() > -1.5)
            .limit(8)
            .forEach(e -> System.out.printf("%s(%.1f) ", e.getKey(), e.getValue()));
        System.out.println();

        // Paraules molt negatives
        System.out.print("  Molt neg (<=-1.5): ");
        tagger.lexic.entrySet().stream()
            .filter(e -> e.getValue() <= -1.5)
            .sorted(Comparator.comparingDouble(Map.Entry::getValue))
            .limit(8)
            .forEach(e -> System.out.printf("%s(%.1f) ", e.getKey(), e.getValue()));
        System.out.println();

        System.out.print("  Negadors: ");
        tagger.negadors.forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("  Modificadors: ");
        tagger.modificadors.forEach((k, v) -> System.out.printf("%s(x%.1f) ", k, v));
        System.out.println("\n");




        System.out.println("[ ANALISI DE SENTIMENT PER VERS ]");
        System.out.printf("  neg <--------- 0 ---------> pos%n%n");

        int numEstrofa = 0;
        for (List<VersAnalitzat> estrofa : estrofes) {
            numEstrofa++;
            double mitjaEstrofa = estrofa.stream()
                    .mapToDouble(VersAnalitzat::getPuntuacio).average().orElse(0.0);
            AnalisiSentiments.Sentiment sentEstrofa = AnalisiSentiments.Sentiment.de(mitjaEstrofa);

            System.out.printf("  +-- Estrofa %d  (mit: %+.2f  |  %s) %s%n",
                    numEstrofa, mitjaEstrofa, sentEstrofa.getEtiqueta(),
                    iconaSentiment(sentEstrofa));
            System.out.println("  |");

            for (VersAnalitzat rv : estrofa) {
                String barra = barraSentiment(rv.getPuntuacio());
                System.out.printf("  |  V%-2d [%s] %+.2f  %s%n",
                        rv.getNumero(), barra, rv.getPuntuacio(),
                        iconaSentiment(rv.getSentiment()));
                System.out.printf("  |       \"%s\"%n", rv.getText());

                // Tokens significatius del vers (amb la seva puntuació)
                List<TokenAnalitzat> sig = rv.getTokensSignificatius();
                if (!sig.isEmpty()) {
                    System.out.print("  |       --> ");
                    for (TokenAnalitzat t : sig) {
                        String signe = t.getPuntuacioFinal() >= 0 ? "+" : "";
                        // Indica si hi ha hagut modificació (negació o factor ≠ 1)
                        String mod = "";
                        if (t.getFactor() == -1.0)          mod = "[NEG]";
                        else if (t.getFactor() > 1.0)       mod = String.format("[x%.1f]", t.getFactor());
                        else if (t.getFactor() < 0)         mod = String.format("[NEG x%.1f]", -t.getFactor());
                        else if (t.getFactor() < 1.0 && t.getFactor() > 0) mod = String.format("[x%.1f]", t.getFactor());
                        System.out.printf("'%s'%s%s%.1f  ",
                                t.getForma(), mod, signe, t.getPuntuacioFinal());
                    }
                    System.out.println();
                }
                System.out.println("  |");
            }
            System.out.printf("  +-- Punt. estrofa: [%s] %+.2f%n%n",
                    barraSentiment(mitjaEstrofa), mitjaEstrofa);
        }



        System.out.println("[ TAULA DE TOKENS LEXICALS IDENTIFICATS ]");
        System.out.printf("  %-20s  %-6s  %-6s  %-6s  %-14s  %s%n",
            "Token original", "Base", "Factor", "Final", "Rol", "Vers");

        for (VersAnalitzat rv : totalsVersos) {
            for (TokenAnalitzat t : rv.getTokens()) {
                if (t.getRol() == AnalisiSentiments.Rol.NEUTRE) continue;  // oculta els neutres
                String signe = t.getPuntuacioFinal() >= 0 ? "+" : "";
                System.out.printf("  %-20s  %+6.1f  %+6.1f  %s%5.1f  %-14s  V%d%n",
                    t.getOriginal(),
                    t.getPuntuacioBase(),
                    t.getFactor(),
                    signe, t.getPuntuacioFinal(),
                    t.getRol().getCodi(),
                    rv.getNumero());
            }
        }
        System.out.println();



        System.out.println("[ TOP PARAULES PER POLARITAT FINAL ]");

        List<TokenAnalitzat> lexicals = totalsVersos.stream()
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

        for (VersAnalitzat rv : totalsVersos) {
            System.out.printf("  V%-2d  |%s| %+.2f  %s%n",
                rv.getNumero(),
                barraSentiment(rv.getPuntuacio()),
                rv.getPuntuacio(),
                iconaSentiment(rv.getSentiment()));
        }
        System.out.println();

        System.out.println("[ RESUM GLOBAL DEL POEMA ]");

        double puntGlobal = totalsVersos.stream()
            .mapToDouble(VersAnalitzat::getPuntuacio).average().orElse(0.0);
        AnalisiSentiments.Sentiment sentGlobal = AnalisiSentiments.Sentiment.de(puntGlobal);

        System.out.printf("  Versos analitzats   : %d%n", totalsVersos.size());
        System.out.printf("  Tokens totals       : %d%n",
            totalsVersos.stream().mapToInt(rv -> rv.getTokens().size()).sum());
        System.out.printf("  Tokens al lexic     : %d%n",
            totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
                .filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC).count());
        System.out.printf("  Negadors detectats  : %d%n",
            totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
                .filter(t -> t.getRol() == AnalisiSentiments.Rol.NEGADOR).count());
        System.out.printf("  Intensificadors     : %d%n",
            totalsVersos.stream().flatMap(rv -> rv.getTokens().stream())
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
        Map<AnalisiSentiments.Sentiment, Long> dist = totalsVersos.stream()
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
        OptionalDouble max = totalsVersos.stream()
            .mapToDouble(VersAnalitzat::getPuntuacio).max();
        OptionalDouble min = totalsVersos.stream()
            .mapToDouble(VersAnalitzat::getPuntuacio).min();
        System.out.printf("%n  Vers mes positiu    : %+.2f  (V%d)%n",
            max.orElse(0),
            totalsVersos.stream()
                .max(Comparator.comparingDouble(VersAnalitzat::getPuntuacio))
                .map(VersAnalitzat::getNumero).orElse(0));
        System.out.printf("  Vers mes negatiu    : %+.2f  (V%d)%n",
            min.orElse(0),
            totalsVersos.stream()
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
