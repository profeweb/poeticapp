package parser;

import silabes.ComptadorSillabes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParserPoema {

    // Retorna el poema segmentat (poema, estrofes, frases, versos i tokens) del poema
    public static Poema parse(File fitxer) throws IOException {

        // Inicialitza números
        int numEstrofa = 1, numSeccio = 1, numVers = 1;

        Poema poema = new Poema(1);
        Estrofa estrofaActual = new Estrofa(numEstrofa);
        Frase fraseActual = new Frase(numSeccio);

        try (BufferedReader br = new BufferedReader(new FileReader(fitxer))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    // final d'estrofa
                    confirmaFrase(estrofaActual, fraseActual);
                    numSeccio++;
                    fraseActual = new Frase(numSeccio);

                    if (!estrofaActual.frases.isEmpty()) {
                        poema.estrofes.add(estrofaActual);
                    }

                    numEstrofa++;
                    estrofaActual = new Estrofa(numEstrofa);
                    continue;
                }

                Vers vers = parseVers(line, numVers);
                fraseActual.versos.add(vers);
                numVers++;

                if (line.trim().endsWith(".")) {
                    // final de frase
                    fraseActual.finalAmbPunt = true;
                    estrofaActual.frases.add(fraseActual);

                    numSeccio++;
                    fraseActual = new Frase(numSeccio);
                }
            }
        }

        // confirmació final
        if (!fraseActual.versos.isEmpty()) {
            estrofaActual.frases.add(fraseActual);
        }

        if (!estrofaActual.frases.isEmpty()) {
            poema.estrofes.add(estrofaActual);
        }

        return poema;
    }

    // Confirma la frase dins l'estrofa
    private static void confirmaFrase(Estrofa estrofa, Frase frase) {
        if (!frase.versos.isEmpty()) {
            estrofa.frases.add(frase);
        }
    }

    // Retorna el vers segmentat (vers, tokens)
    public static Vers parseVers(String line, int numVers) {

        Vers vers = new Vers(numVers, line);
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (esLletraCatalà(c)) {
                buffer.append(c);
            }

            else if (Character.isWhitespace(c)) {
                confirmaParaula(vers, buffer);
            }

            else {
                // símbol = token independent
                confirmaParaula(vers, buffer);
                vers.tokens.add(new Simbol(String.valueOf(c)));
            }
        }

        confirmaParaula(vers, buffer);

        return vers;
    }

    // Confirma la paraula en el vers
    private static void confirmaParaula(Vers vers, StringBuilder buffer) {
        if (buffer.length() > 0) {
            String textParaula = buffer.toString();
            Paraula paraula = new Paraula(textParaula);
            paraula.setNumSilabes(ComptadorSillabes.comptaSilabes(textParaula));
            vers.tokens.add(paraula);
            buffer.setLength(0);
        }
    }

    // Retorna vertader si el caràcter és una lletra catalana
    private static boolean esLletraCatalà(char c) {
        return Character.isLetter(c)
                || "àèéíòóúïüçÀÈÉÍÒÓÚÏÜÇ".indexOf(c) >= 0
                || c == '·'; // en el cas de l·l
    }

    // Retorna una llista amb les línies del poema
    public static ArrayList<String> getLinies(String fitxerPoema){
        ArrayList<String> linies = new ArrayList<>();
        try {
            File fitxer = new File(fitxerPoema);
            Scanner scanner = new Scanner(fitxer);
            while(scanner.hasNextLine()){
                linies.add(scanner.nextLine());
            }
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        return linies;
    }

}
