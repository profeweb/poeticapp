package parser;

import silabes.ComptadorSilabes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParserPoema {

    public static void main(String[] args) throws IOException {

        Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        poemari.parsePoemes(3, "data/poems/mariera/poemes a nai/");
        poemari.getPoemaAt(2).printInfo();
        //poemari.getPoemaAt(2).getVersAt(15).printVers();
    }

    public static Poema parse(File file) throws IOException {

        int numEstrofa = 1, numSeccio = 1, numVers = 1;

        Poema poema = new Poema(1);
        Estrofa estrofaActual = new Estrofa(numEstrofa);
        Frase fraseActual = new Frase(numSeccio);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

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

        // flush final
        if (!fraseActual.versos.isEmpty()) {
            estrofaActual.frases.add(fraseActual);
        }

        if (!estrofaActual.frases.isEmpty()) {
            poema.estrofes.add(estrofaActual);
        }

        return poema;
    }



    private static void confirmaFrase(Estrofa e, Frase s) {
        if (!s.versos.isEmpty()) {
            e.frases.add(s);
        }
    }

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

    private static void confirmaParaula(Vers vers, StringBuilder buffer) {
        if (buffer.length() > 0) {
            String textParaula = buffer.toString();
            Paraula paraula = new Paraula(textParaula);
            paraula.setNumSilabes(ComptadorSilabes.comptaSilabes(textParaula));
            vers.tokens.add(paraula);
            buffer.setLength(0);
        }
    }

    private static boolean esLletraCatalà(char c) {
        return Character.isLetter(c)
                || "àèéíòóúïüçÀÈÉÍÒÓÚÏÜÇ".indexOf(c) >= 0
                || c == '·'; // en el cas de l·l
    }

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
