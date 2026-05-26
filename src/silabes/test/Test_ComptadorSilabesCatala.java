package silabes.test;

import static silabes.ComptadorSillabes.comptaSilabes;

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

public class Test_ComptadorSilabesCatala {

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