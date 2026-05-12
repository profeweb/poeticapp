package sentiments;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VersAnalitzat {

    private final int                  numero;
    private final String               text;
    private final List<TokenAnalitzat> tokens;
    private final double               puntuacio;
    private final AnalisiSentiments.Sentiment sentiment;

    public VersAnalitzat(int numero, String text, List<TokenAnalitzat> tokens) {
            this.numero  = numero;
            this.text    = text;
            this.tokens  = Collections.unmodifiableList(tokens);

            // Puntuació normalitzada pel nombre de tokens lexicals
            long   nLex = tokens.stream().filter(t -> t.getRol() == AnalisiSentiments.Rol.LEXIC).count();
            double suma = tokens.stream().mapToDouble(TokenAnalitzat::getPuntuacioFinal).sum();
            this.puntuacio = (nLex > 0) ? suma / nLex : 0.0;
            this.sentiment = AnalisiSentiments.Sentiment.de(this.puntuacio);
        }

        public int                  getNumero()    { return numero;    }
        public String               getText()      { return text;      }
        public List<TokenAnalitzat> getTokens()    { return tokens;    }
        public double               getPuntuacio() { return puntuacio; }
        public AnalisiSentiments.Sentiment getSentiment() { return sentiment; }

        /** Retorna tots els tokens lexicals (amb puntuació diferent de 0). */
        public List<TokenAnalitzat> getTokensSignificatius() {
            return tokens.stream()
                .filter(TokenAnalitzat::esSignificatiu)
                .collect(Collectors.toList());
        }

}
