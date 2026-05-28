package sentiments;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VersAnalitzat {

    private final int numero;   // Número de vers
    private final String text;  // Text del vers
    private final List<TokenAnalitzat> tokens;  // Llista de tokens del vers
    private final double puntuacio; // Puntuació global del vers
    private final AnalisiSentiments.Sentiment sentiment;    // Sentiment global del vers

    // Constructor
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

    // Getters
    public int getNumero(){ return numero;}

    public String getText(){ return text; }

    public List<TokenAnalitzat> getTokens(){ return tokens; }

    public double getPuntuacio() { return puntuacio; }

    public AnalisiSentiments.Sentiment getSentiment() { return sentiment; }

    // Retorna tots els tokens lexicals amb puntuació diferent de 0
    public List<TokenAnalitzat> getTokensSignificatius() {
        return tokens.stream()
                .filter(TokenAnalitzat::esSignificatiu)
                .collect(Collectors.toList());
    }

}
