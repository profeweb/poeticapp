package parser.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Comptador de síl·labes en català (criteri gràfic).
 *
 * Implementa les regles de divisió sil·làbica de Softcatalà:
 * https://www.softcatala.org/sillabes/
 *
 * ── Regles de diftong ──────────────────────────────────────────────────────
 *
 * VOCALS FORTES:  a, e, o  (amb qualsevol accent: à, á, è, é, ò, ó)
 * VOCALS FEBLES TÒNIQUES: í, ú, ï, ü  → sempre formen hiat
 * VOCALS FEBLES ÀTONES:   i, u sense accent ni dièresi → marge de diftong
 *
 * HIAT (síl·labes separades):
 *   – Qualsevol vocal amb dièresi (ï, ü) amb la vocal adjacent.
 *   – Vocal feble accentuada (í, ú) amb la vocal adjacent.
 *   – Dues vocals fortes adjacents.
 *
 * DIFTONG DECREIXENT (V + i/u febles):
 *   – Qualsevol vocal + i/u feble, tret que la i/u sigui l'inici
 *     d'un diftong creixent amb la vocal adjacent següent.
 *   Exemples: ai, ei, oi, au, eu, ou, iu, ui.
 *
 * DIFTONG CREIXENT (i/u febles + V):
 *   – La i/u feble és a l'inici efectiu de paraula (precedida només per h),
 *     o immediatament precedida per una altra vocal adjacent (entre vocals).
 *   Exemples: io-, ie-, ia- (iogurt, hiena, noia, feia…).
 *
 * ── Casos especials ────────────────────────────────────────────────────────
 *   – La 'u' de «gu» i «qu» davant de vocal és muda (no compta com a vocal).
 *   – La 'l·l' (el·la geminada) es tracta com a doble consonant.
 *   – La 'h' és transparent als efectes d'adjacència vocàlica.
 */

public class ComptadorSilabesCatala {

    // ── Classificació de vocals ────────────────────────────────────────────

    /** Totes les vocals catalanes (amb accentuades i dièresi). */
    private static final String VOCALS_CATALÀ = "aeiouàáèéòóíúïü";

    public static boolean esVocal(char c) {
        return VOCALS_CATALÀ.indexOf(c) >= 0;
    }

    /**
     * Vocals FORTES: a, e, o (amb qualsevol accent) + febles tòniques í, ú, ï, ü.
     * IMPORTANT: la 'i' i la 'u' sense accent ni dièresi NO són fortes;
     * son febles àtones i poden formar el marge d'un diftong.
     */
    private static boolean esVocalForta(char c) {
        return "aeoàáèéòóíúïü".indexOf(c) >= 0;
    }

    /** Retorna true per a la 'i' i la 'u' sense accent ni dièresi. */
    private static boolean esVocalDebilNoAccentuada(char c) {
        return c == 'i' || c == 'u';
    }

    // ── Preprocessat ──────────────────────────────────────────────────────

    private static String preProcessament(String word) {
        word = word.toLowerCase().trim();
        word = word.replace("l·l", "ll");  // el·la geminada → doble consonant
        word = word.replace("\u00b7", ""); // punt volat residual
        return word;
    }

    // ── Vocals fonètiques ─────────────────────────────────────────────────

    /**
     * Indica quines posicions contenen vocals fonèticament actives.
     * La 'u' de «gu/qu» davant de vocal és muda i s'exclou.
     */
    private static boolean[] mascaraVocalsFonetiques(String word) {
        int n = word.length();
        boolean[] mask = new boolean[n];
        for (int i = 0; i < n; i++) {
            char c = word.charAt(i);
            if (!esVocal(c)) continue;
            if (c == 'u' && i > 0) {
                char prev = word.charAt(i - 1);
                if ((prev == 'g' || prev == 'q') && i + 1 < n && esVocal(word.charAt(i + 1))) {
                    continue; // u muda en gu+vocal i qu+vocal
                }
            }
            mask[i] = true;
        }
        return mask;
    }

    // ── Adjacència ────────────────────────────────────────────────────────

    /**
     * Retorna true si no hi ha cap consonant (lletra que no sigui 'h')
     * entre les posicions p1 i p2.
     */
    private static boolean adjacent(String word, int p1, int p2) {
        for (int k = p1 + 1; k < p2; k++) {
            if (word.charAt(k) != 'h') return false;
        }
        return true;
    }

    /**
     * Retorna true si totes les lletres anteriors a la posició pos
     * són 'h' mudes (la vocal és a l'inici efectiu de paraula).
     */
    private static boolean alIniciParaula(String word, int pos) {
        for (int k = 0; k < pos; k++) {
            if (word.charAt(k) != 'h') return false;
        }
        return true;
    }

    // ── Hiat ──────────────────────────────────────────────────────────────

    /**
     * Retorna true si v1 i v2 (adjacents) formen un hiat.
     *
     * Casos:
     *  1. Vocal amb dièresi (ï, ü) → sempre hiat.
     *  2. Vocal feble accentuada (í, ú) → sempre hiat.
     *  3. Dues vocals fortes (a/e/o amb o sense accent).
     */
    private static boolean esHiat(char v1, char v2) {
        if (v1 == 'ï' || v1 == 'ü' || v2 == 'ï' || v2 == 'ü') return true;
        if (v1 == 'í' || v1 == 'ú' || v2 == 'í' || v2 == 'ú') return true;
        if (esVocalForta(v1) && esVocalForta(v2)) return true;
        return false;
    }

    // ── Algorisme principal ───────────────────────────────────────────────

    /**
     * Compta el nombre de síl·labes d'una paraula en català
     * seguint el criteri gràfic de Softcatalà.
     *
     * @param  paraula  paraula en català (accepta accents, dièresis i l·l)
     * @return nombre de síl·labes (mínim 1 per a paraules no buides)
     */
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
        if (numVocals == 0) return 1; // paraula sense vocals → 1 síl·laba

        // Punt de partida: una síl·laba per vocal fonètica
        int silabes = numVocals;

        for (int vi = 0; vi < numVocals - 1; vi++) {
            int p1 = posicionsVocals.get(vi);
            int p2 = posicionsVocals.get(vi + 1);
            char v1 = paraula.charAt(p1);
            char v2 = paraula.charAt(p2);

            // Vocals no adjacents (consonant entremig) → no diftong possible
            if (!adjacent(paraula, p1, p2)) continue;

            // ── Cas 1: Hiat ──────────────────────────────────────────────
            if (esHiat(v1, v2)) continue;

            // ── Cas 2: Diftong decreixent  V + [i/u] ────────────────────
            // v2 és feble àtona i podria ser la coda del diftong.
            // Però si v2 és l'inici d'un diftong CREIXENT amb v3, no l'apliquem aquí.
            if (esVocalDebilNoAccentuada(v2)) {
                boolean v2DigtongCreixent = false;
                if (vi + 2 < numVocals) {
                    int p3 = posicionsVocals.get(vi + 2);
                    // v2 pot iniciar diftong creixent si:
                    //  · v2 i v3 són adjacents, I
                    //  · v2 és a l'inici de paraula O precedida per vocal adjacent (p1)
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

            // ── Cas 3: Diftong creixent  [i/u] + V ──────────────────────
            // v1 és feble àtona; v2 és fort (o altra vocal no feble àtona).
            if (esVocalDebilNoAccentuada(v1)) {
                // Condicions per al diftong creixent:
                //  (a) v1 és a l'inici efectiu de la paraula: io-, ie-, ia-… (iogurt, hiena)
                //  (b) v1 és immediatament precedida per una altra vocal adjacent
                //      («entre vocals»): noia (o+i+a), feia (e+i+a), creueu…
                boolean diftongCreixent = alIniciParaula(paraula, p1);
                if (!diftongCreixent && vi > 0) {
                    diftongCreixent = adjacent(paraula, posicionsVocals.get(vi - 1), p1);
                }
                if (diftongCreixent) {
                    silabes--; // diftong creixent: io, ie, ia, ua, ue, uo…
                }
                // Si cap condició: hiat gràfic (dia, família, religió, raó…)
            }
        }

        return Math.max(1, silabes);
    }

    // ── Tests ─────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=== Comptador de síl·labes en català ===\n");

        Object[][] tests = {
                // ── 1 síl·laba (monosíl·labs) ────────────────────────────────
                {"pa",          1}, {"gat",         1}, {"cel",         1},
                {"res",         1}, {"cor",         1}, {"sol",         1},
                {"llet",        1}, {"temps",       1}, {"blat",        1},
                {"cuc",         1}, {"bot",         1}, {"sang",        1},
                {"or",          1}, {"ull",         1}, {"pla",         1},
                {"set",         1}, {"sec",         1}, {"gel",         1},
                {"mar",         1}, {"foc",         1}, {"got",         1},
                {"vas",         1}, {"flor",        1}, {"fill",        1},
                {"llum",        1}, {"breu",        1}, {"deu",         1},
                {"peu",         1}, {"nou",         1}, {"vell",        1},
                {"greu",        1}, {"creu",        1}, {"rei",         1},
                {"mai",         1}, {"meu",         1}, {"teu",         1},
                {"seu",         1}, {"ple",         1}, {"fruit",       1},
                {"buit",        1}, {"viu",         1}, {"cau",         1},
                {"gai",         1},

                // ── 2 síl·labes (bisíl·labs) ─────────────────────────────────
                {"casa",        2}, {"vida",        2}, {"porta",       2},
                {"arbre",       2}, {"feina",       2}, {"causa",       2},
                {"llengua",     2}, {"truita",      2}, {"Pasqua",      2},
                {"finca",       2}, {"cotxe",       2}, {"aire",        2},
                {"sauna",       2}, {"anar",        2}, {"carta",       2},
                {"corda",       2}, {"prova",       2}, {"taula",       2},
                {"veí",         2}, {"iogurt",      2}, {"hiena",       2},
                {"noia",        2}, {"feia",        2}, {"dia",         2},
                {"iode",        2}, {"parlar",      2}, {"capçal",      2},
                {"creueu",      2}, {"cuina",       2}, {"llenços",     2},
                {"pluja",       2}, {"hia",         1},

                // ── 3 síl·labes (trisíl·labs) ────────────────────────────────
                {"animal",      3}, {"casada",      3}, {"paraula",     3},
                {"màquina",     3}, {"bisturí",     3}, {"cafetó",      3},
                {"càpsula",     3}, {"alcohol",     3}, {"nació",       3},
                {"caminar",     3}, {"abella",      3}, {"aïllat",      3},
                {"veïna",       3}, {"escaient",    3}, {"paràgua",     3},
                {"paraigua",    3}, {"ràpida",      3}, {"fórmula",     3},

                // ── 4 síl·labes (tetrasíl·labs) ──────────────────────────────
                {"professora",  4}, {"dificultat",  4}, {"abracada",    4},
                {"estació",     4}, {"ciència",     4}, {"família",     4},
                {"ordinador",   4}, {"agradable",   4},

                // ── 5 síl·labes (pentasíl·labs) ──────────────────────────────
                {"confirmació", 5}, {"transparència", 5},

                // ── Casos amb l·l i h ─────────────────────────────────────────
                {"col·legi",    3}, {"il·lusió",    4},
        };

        int pass = 0, fail = 0;
        for (Object[] t : tests) {
            String word     = (String) t[0];
            int    expected = (int)    t[1];
            int    got      = comptaSilabes(word);
            boolean ok      = got == expected;
            if (ok) pass++; else fail++;
            System.out.printf("  %-22s  esperat=%-2d  obtingut=%-2d  %s%n",
                    word, expected, got, ok ? "✓" : "✗ ←");
        }
        System.out.printf("%n  ─── %d / %d correctes (%d fallades) ───%n",
                pass, pass + fail, fail);
    }
}