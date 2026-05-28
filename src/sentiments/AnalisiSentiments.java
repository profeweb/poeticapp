package sentiments;

import parser.Estrofa;
import parser.Poema;
import parser.Token;
import parser.Vers;

import java.util.*;
import java.util.stream.Collectors;

public class AnalisiSentiments {

    // Catalogació dels sentiments segons polarització
    public enum Sentiment {
        MOLT_POSITIU ("Molt positiu",          1.2,  Double.MAX_VALUE),
        POSITIU      ("Positiu",               0.4,  1.2),
        LLEU_POSITIU ("Lleugerament positiu",  0.1,  0.4),
        NEUTRE       ("Neutre",               -0.1,  0.1),
        LLEU_NEGATIU ("Lleugerament negatiu", -0.4, -0.1),
        NEGATIU      ("Negatiu",             -1.2, -0.4),
        MOLT_NEGATIU ("Molt negatiu",  Double.NEGATIVE_INFINITY, -1.2);

        final String etiqueta;  // Text del sentiment
        final double llindarInf, llindarSup;    // Rang de l'interval

        // Constructor
        Sentiment(String etiqueta, double inf, double sup) {
            this.etiqueta   = etiqueta;
            this.llindarInf = inf;
            this.llindarSup = sup;
        }

        // Retorna la descripció textual
        public String getEtiqueta(){ return  this.etiqueta; }

        // Retorna el sentiment corresponent a la puntuació
        public static Sentiment de(double p) {
            for (Sentiment s : values())
                if (p >= s.llindarInf && p < s.llindarSup) return s;
            return MOLT_NEGATIU;
        }

        // Símbol ASCII de 3 caràcters per a la sortida compacta
        public String simbol() {
            return switch (this) {
                case MOLT_POSITIU -> "[+++]";
                case POSITIU      -> "[ ++]";
                case LLEU_POSITIU -> "[  +]";
                case NEUTRE       -> "[  0]";
                case LLEU_NEGATIU -> "[  -]";
                case NEGATIU      -> "[ --]";
                case MOLT_NEGATIU -> "[---]";
            };
        }
    }

    // Catalogació del tipus de rol dels tokens en l'anàlisi de sentiments
    public enum Rol {
        LEXIC          ("LEX", "Paraula al lexicó amb puntuació"),
        NEGADOR        ("NEG", "Negador — inverteix la polaritat (x-1)"),
        INTENSIFICADOR ("INT", "Intensificador — amplifica la polaritat (x1.5)"),
        DIMINUIDOR     ("DIM", "Diminuïdor — redueix la polaritat (x0.5)"),
        NEUTRE         (" — ", "Sense càrrega afectiva (no al lexicó)");

        final String codi;  // Etiqueta del rol
        final String descripcio;    // Text descriptiu del rol

        // Constructor
        Rol(String codi, String descripcio) {
            this.codi = codi;
            this.descripcio = descripcio;
        }

        // Retorna el codi del Rol
        public String getCodi() { return codi; }
    }


    // Llista de versos analitzats per estrofes i agrupats
    public List<List<VersAnalitzat>> estrofes;
    public List<VersAnalitzat>       totalsVersos;

    DiccionariSentiments diccionariSentiments;

    // Finestra de negació: nombre màxim de tokens endavant afectats per un negador.
    private static final int FINESTRA_NEGACIO = 3;

    // Constructor
    public AnalisiSentiments(DiccionariSentiments diccionariSentiments) {
        this.diccionariSentiments = diccionariSentiments;
    }

    // Normalitza: minúscules i suprimeix puntuació als extrems
    public static String normalitza(String s) {
        return s.toLowerCase()
                .replaceAll("^[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+", "")
                .replaceAll("[.,;:!?¡¿\"«»()\\[\\]{}'\\-–—/·]+$",  "")
                .trim();
    }

    // Retorna els tokens d'una línia de text
    public static String[] tokenitza(String linia) {
        List<String> tokens = new ArrayList<>();
        for (String part : linia.trim().split("\\s+")) {
            if (part.isEmpty()) continue;
            int ap = -1;
            for (int k = 1; k < part.length() - 1; k++) {
                char c = part.charAt(k);
                if (c == '\'' || c == '\u2019') { ap = k; break; }
            }
            if (ap > 0) {
                tokens.add(part.substring(0, ap + 1)); // "d'"
                tokens.add(part.substring(ap + 1));    // "amor"
            } else {
                tokens.add(part);
            }
        }
        return tokens.toArray(new String[0]);
    }

    // Retorna el vers analitzat
    public VersAnalitzat analitzaVers(String text, int numero) {
        String[] parts = tokenitza(text);
        List<TokenAnalitzat> resultat = new ArrayList<>();

        int    finestraNegacio = 0;   // tokens que resten de negació activa
        double factorMod       = 1.0; // factor pendent (intensificador/diminuïdor)

        for (String part : parts) {
            if (part.isEmpty()) continue;
            String forma = normalitza(part);
            if (forma.isEmpty()) continue;

            boolean esNegador      = diccionariSentiments.negadors.contains(forma);
            boolean esModificador  = diccionariSentiments.modificadors.containsKey(forma);
            boolean esLexic        = diccionariSentiments.lexic.containsKey(forma);

            if (esNegador) {
                // Negador
                finestraNegacio = FINESTRA_NEGACIO;
                resultat.add(new TokenAnalitzat(part, forma, 0.0, 1.0, Rol.NEGADOR));

            } else if (esModificador) {
                // Intensificador o Diminuïdor
                factorMod = diccionariSentiments.modificadors.get(forma);
                Rol rol   = (factorMod > 1.0) ? Rol.INTENSIFICADOR : Rol.DIMINUIDOR;
                resultat.add(new TokenAnalitzat(part, forma, 0.0, factorMod, rol));

            } else if (esLexic) {
                // ── Token lexical amb puntuació
                double base      = diccionariSentiments.lexic.get(forma);
                double signe     = (finestraNegacio > 0) ? -1.0 : 1.0;
                double factorFin = signe * factorMod;

                resultat.add(new TokenAnalitzat(part, forma, base, factorFin, Rol.LEXIC));

                factorMod = 1.0;  // consumeix el modificador pendent
                if (finestraNegacio > 0) finestraNegacio--;

            } else {
                // Token neutre (no al diccionari de sentiments)
                if (finestraNegacio > 0) finestraNegacio--;
                // El factorMod NO es reseteja: persisteix fins al proper token lexical
                resultat.add(new TokenAnalitzat(part, forma, 0.0, 1.0, Rol.NEUTRE));
            }
        }

        return new VersAnalitzat(numero, text, resultat);
    }

    // Retorna el vers analitzat
    public VersAnalitzat analitzaVers(Vers vers) {

        List<TokenAnalitzat> resultat = new ArrayList<>();

        int    finestraNegacio = 0;   // tokens que resten de negació activa
        double factorMod       = 1.0; // factor pendent (intensificador/diminuïdor)

        for (Token token: vers.getParaules()) {
            if (token.getValor().isEmpty()) continue;
            String forma = normalitza(token.getValor());
            if (forma.isEmpty()) continue;

            boolean esNegador      = diccionariSentiments.negadors.contains(forma);
            boolean esModificador  = diccionariSentiments.modificadors.containsKey(forma);
            boolean alLexic        = diccionariSentiments.lexic.containsKey(forma);

            if (esNegador) {
                // Negador
                finestraNegacio = FINESTRA_NEGACIO;
                resultat.add(new TokenAnalitzat(token.getValor(), forma, 0.0, 1.0, Rol.NEGADOR));

            } else if (esModificador) {
                // Intensificador o Diminuïdor
                factorMod = diccionariSentiments.modificadors.get(forma);
                Rol rol   = (factorMod > 1.0) ? Rol.INTENSIFICADOR : Rol.DIMINUIDOR;
                resultat.add(new TokenAnalitzat(token.getValor(), forma, 0.0, factorMod, rol));

            } else if (alLexic) {
                // Token lexical amb puntuació
                double base      = diccionariSentiments.lexic.get(forma);
                double signe     = (finestraNegacio > 0) ? -1.0 : 1.0;
                double factorFin = signe * factorMod;

                resultat.add(new TokenAnalitzat(token.getValor(), forma, base, factorFin, Rol.LEXIC));

                factorMod = 1.0;                         // consumeix el modificador pendent
                if (finestraNegacio > 0) finestraNegacio--;

            } else {
                // Token neutre (no apareix en el diccionari de sentiments)
                if (finestraNegacio > 0) finestraNegacio--;
                // El factorMod NO es reset: persisteix fins al proper token lexical
                resultat.add(new TokenAnalitzat(token.getValor(), forma, 0.0, 1.0, Rol.NEUTRE));
            }
        }

        return new VersAnalitzat(vers.getNumVers(), vers.getText(), resultat);
    }

    // Analitza els sentiments d'un text
     public void analitzaPoema(String text) {

        this.estrofes    = new ArrayList<>();

        List<VersAnalitzat> estrofaActual = new ArrayList<>();

        int numVers = 0;

        for (String linia : text.split("\n")) {
            if (linia.isBlank()) {
                if (!estrofaActual.isEmpty()) {
                    estrofes.add(new ArrayList<>(estrofaActual));
                    estrofaActual.clear();
                }
            } else {
                estrofaActual.add(analitzaVers(linia, ++numVers));
            }
        }
        if (!estrofaActual.isEmpty()) estrofes.add(estrofaActual);

         totalsVersos = estrofes.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    // Analitza els sentiments d'un poema
    public void analitzaPoema(Poema poema) {

        this.estrofes    = new ArrayList<>();

        for (Estrofa estrofa : poema.getEstrofes()) {
            List<VersAnalitzat> estrofaActual = new ArrayList<>();
            for(Vers vers : estrofa.getVersos()){
                estrofaActual.add(analitzaVers(vers.getText(), vers.getNumVers()));
            }
            if (!estrofaActual.isEmpty()) estrofes.add(estrofaActual);
        }

        totalsVersos = estrofes.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }


    // Retorna les paraules més positives del lèxic
    public ArrayList<TokenAnalitzat> topLexicPositives(int num){

        ArrayList<TokenAnalitzat> lexicPositius = new ArrayList<>();

        List<TokenAnalitzat> lexicals = totalsVersos.stream()
            .flatMap(rv -> rv.getTokens().stream())
            .filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC)
            .collect(Collectors.toList());

        lexicals.stream()
                .filter(t -> t.getPuntuacioFinal() > 0)
                .sorted(Comparator.comparingDouble(TokenAnalitzat::getPuntuacioFinal).reversed())
                .limit(num)
                .forEach(t -> {
                    lexicPositius.add(t);
                });

        return lexicPositius;
    }

    // Retorna les paraules més negatives del lèxic
    public ArrayList<TokenAnalitzat> topLexicNegatives(int num){

        ArrayList<TokenAnalitzat> lexicPositius = new ArrayList<>();

        List<TokenAnalitzat> lexicals = totalsVersos.stream()
                .flatMap(rv -> rv.getTokens().stream())
                .filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC)
                .collect(Collectors.toList());

        lexicals.stream()
                .filter(t -> t.getPuntuacioFinal() < 0)
                .sorted(Comparator.comparingDouble(TokenAnalitzat::getPuntuacioFinal).reversed())
                .limit(num)
                .forEach(t -> {
                    lexicPositius.add(t);
                });

        return lexicPositius;
    }

    // Retorna la puntuació total del poema
    public double getPuntuacioPoema(){
        float sumaTotal = 0;
        for(VersAnalitzat vers : totalsVersos){
            sumaTotal += vers.getPuntuacio();
        }
        return sumaTotal / totalsVersos.size();
    }

    // Retorna el sentiment global del poema
    public Sentiment getSentimentPoema(){
        return Sentiment.de(getPuntuacioPoema());
    }

}
