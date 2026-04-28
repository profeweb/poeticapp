package parser;

public class Metrica {


    public static int comptarSilabes(String paraula) {
        if (paraula == null || paraula.isEmpty()) return 0;

        String p = paraula.toLowerCase();

        int vocals = 0;

        // 1. comptar vocals reals
        for (int i = 0; i < p.length(); i++) {
            if (esVocal(p.charAt(i))) {
                vocals++;
            }
        }

        // 2. restar diftongs segurs
        int diftongs = 0;

        for (int i = 0; i < p.length() - 1; i++) {
            char a = p.charAt(i);
            char b = p.charAt(i + 1);

            if (esDiftongSegur(a, b)) {
                diftongs++;
                i++; // evitem doble comptatge
            }
        }

        return Math.max(vocals - diftongs, 1);
    }

    private static boolean esVocal(char c) {
        return "aeiouàèéíòóúïü".indexOf(c) >= 0;
    }

    private static boolean esDiftongSegur(char a, char b) {

        if (!esVocal(a) || !esVocal(b)) return false;

        // dièresi trenca sempre
        if (a == 'ï' || a == 'ü' || b == 'ï' || b == 'ü') return false;

        // diftongs catalans segurs (reduït i correcte)
        String pair = "" + a + b;

        return pair.equals("ai") || pair.equals("ei") ||
               pair.equals("oi") || pair.equals("au") ||
               pair.equals("eu") || pair.equals("iu") ||
               pair.equals("ui");
    }

}
