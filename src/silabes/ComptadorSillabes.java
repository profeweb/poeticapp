package silabes;

import java.util.ArrayList;
import java.util.List;

public class ComptadorSillabes {

    // Utilitzam les regles de SoftCatalà: https://www.softcatala.org/sillabes/

    // Totes les vocals catalanes (accentuades i dièresi)
    private static final String VOCALS_CATALÀ = "aeiouàáèéòóíúïü";

    // Retorna vertader si el caràcter és una vocal catalana
    public static boolean esVocal(char c) {
        return VOCALS_CATALÀ.indexOf(c) >= 0;
    }

    // Vocals FORTES: a, e, o (amb accent) + febles tòniques í, ú, ï, ü.
    private static boolean esVocalForta(char c) {
        return "aeoàáèéòóíúïü".indexOf(c) >= 0;
    }

    // Retorna true per a la 'i' i la 'u' sense accent ni dièresi.
    private static boolean esVocalDebilNoAccentuada(char c) {
        return c == 'i' || c == 'u';
    }

    // Preprocessament
    private static String preProcessament(String paraula) {
        paraula = paraula.toLowerCase().trim();
        paraula = paraula.replace("l·l", "ll");  // el·la geminada -> doble consonant
        paraula = paraula.replace("\u00b7", ""); // punt volat residual
        return paraula;
    }

    // Vocals fonètiques

    // Retorna posicions que contenen vocals fonèticament actives. La 'u' de «gu/qu» davant de vocal és muda i s'exclou.
    private static boolean[] mascaraVocalsFonetiques(String paraula) {
        int n = paraula.length();
        boolean[] mascara = new boolean[n];
        for (int i = 0; i < n; i++) {
            char c = paraula.charAt(i);
            if (!esVocal(c)) continue;
            if (c == 'u' && i > 0) {
                char lletraAnterior = paraula.charAt(i - 1);
                if ((lletraAnterior == 'g' || lletraAnterior == 'q') && i + 1 < n && esVocal(paraula.charAt(i + 1))) {
                    continue; // u muda en gu+vocal i qu+vocal
                }
            }
            mascara[i] = true;
        }
        return mascara;
    }

    // Retorna true si no hi ha cap consonant (lletra que no sigui 'h') entre les posicions p1 i p2.
    private static boolean adjacent(String paraula, int p1, int p2) {
        for (int k = p1 + 1; k < p2; k++) {
            if (paraula.charAt(k) != 'h') return false;
        }

        return true;
    }

    // Retorna true si totes les lletres anteriors a la posició pos són 'h' mudes (la vocal és a l'inici efectiu de paraula).
    private static boolean alIniciParaula(String word, int pos) {

        for (int k = 0; k < pos; k++) {
            if (word.charAt(k) != 'h') return false;
        }
        return true;
    }

    // Retorna true si v1 i v2 (adjacents) formen un hiat.
    private static boolean esHiat(char v1, char v2) {
        if (v1 == 'ï' || v1 == 'ü' || v2 == 'ï' || v2 == 'ü') return true;
        if (v1 == 'í' || v1 == 'ú' || v2 == 'í' || v2 == 'ú') return true;
        if (esVocalForta(v1) && esVocalForta(v2)) return true;
        return false;
    }

    // Compta el número de síl·labes d'una paraula en català seguint el criteri gràfic de Softcatalà.
    public static int comptaSilabes(String paraula) {

        if (paraula == null || paraula.trim().isEmpty()) return 0;

        paraula = preProcessament(paraula);
        int numLletres = paraula.length();
        boolean[] fonetica = mascaraVocalsFonetiques(paraula);

        List<Integer> posicionsVocals = new ArrayList<>();
        for (int i = 0; i < numLletres; i++) {
            if (fonetica[i]) posicionsVocals.add(i);
        }

        int numVocals = posicionsVocals.size();
        if (numVocals == 0) return 1; // paraula sense vocals -> 1 síl·laba

        // Punt de partida: una síl·laba per vocal fonètica
        int silabes = numVocals;

        for (int vi = 0; vi < numVocals - 1; vi++) {
            int p1 = posicionsVocals.get(vi);
            int p2 = posicionsVocals.get(vi + 1);
            char v1 = paraula.charAt(p1);
            char v2 = paraula.charAt(p2);

            // Vocals no adjacents (consonant entremig) -> no diftong possible
            if (!adjacent(paraula, p1, p2)) continue;

            // Cas 1: Hiat
            if (esHiat(v1, v2)) continue;

            // Cas 2: Diftong decreixent  V + [i/u]
            if (esVocalDebilNoAccentuada(v2)) {
                boolean v2DigtongCreixent = false;
                if (vi + 2 < numVocals) {
                    int p3 = posicionsVocals.get(vi + 2);
                    if (adjacent(paraula, p2, p3)) {
                        if (alIniciParaula(paraula, p2) || adjacent(paraula, p1, p2)) {
                            v2DigtongCreixent = true;
                        }
                    }
                }
                if (!v2DigtongCreixent) {
                    silabes--; // diftong decreixent: ai, ei, oi, au, eu, ou, iu, ui…
                }
                continue;
            }

            // Cas 3: Diftong creixent  [i/u] + V
            if (esVocalDebilNoAccentuada(v1)) {
                boolean diftongCreixent = alIniciParaula(paraula, p1);
                if (!diftongCreixent && vi > 0) {
                    diftongCreixent = adjacent(paraula, posicionsVocals.get(vi - 1), p1);
                }
                if (diftongCreixent) {
                    silabes--; // diftong creixent: io, ie, ia, ua, ue, uo…
                }
                // Si cap condició: hiat gràfic
            }
        }

        return Math.max(1, silabes);
    }

}
