package parser;

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
        //poemari.getPoemaAt(2).printInfo();
        poemari.getPoemaAt(2).getVersAt(15).printVers();
    }

    public static Poema parse(File file) throws IOException {

        int numEstrofa = 1;
        int numSeccio = 1;
        int numVers = 1;

        Poema poema = new Poema("itol poema", 1);
        Estrofa estrofaActual = new Estrofa(numEstrofa);
        Seccio seccioActual = new Seccio(numSeccio);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    // final d'estrofa
                    flushSeccio(estrofaActual, seccioActual);
                    numSeccio++;
                    seccioActual = new Seccio(numSeccio);

                    if (!estrofaActual.seccions.isEmpty()) {
                        poema.estrofes.add(estrofaActual);
                    }

                    numEstrofa++;
                    estrofaActual = new Estrofa(numEstrofa);
                    continue;
                }

                Vers vers = parseVers(line, numVers);
                seccioActual.versos.add(vers);
                numVers++;

                if (line.trim().endsWith(".")) {
                    seccioActual.finalAmbPunt = true;
                    estrofaActual.seccions.add(seccioActual);
                    numSeccio++;
                    seccioActual = new Seccio(numSeccio);
                }
            }
        }

        // flush final
        if (!seccioActual.versos.isEmpty()) {
            estrofaActual.seccions.add(seccioActual);
        }

        if (!estrofaActual.seccions.isEmpty()) {
            poema.estrofes.add(estrofaActual);
        }

        return poema;
    }

    private static void flushSeccio(Estrofa e, Seccio s) {
        if (!s.versos.isEmpty()) {
            e.seccions.add(s);
        }
    }

    public static Vers parseVers(String line, int numVers) {

        Vers vers = new Vers(numVers, line);
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (isLetter(c)) {
                buffer.append(c);
            }

            else if (Character.isWhitespace(c)) {
                flushWord(vers, buffer);
            }

            else {
                // símbol = token independent
                flushWord(vers, buffer);

                vers.tokens.add(
                        new Token(Token.Tipus.SEPARADOR, String.valueOf(c))
                );
            }
        }

        flushWord(vers, buffer);

        return vers;
    }

    private static void flushWord(Vers vers, StringBuilder buffer) {
        if (buffer.length() > 0) {
            vers.tokens.add(
                    new Token(Token.Tipus.PARAULA, buffer.toString())
            );
            buffer.setLength(0);
        }
    }

    private static boolean isLetter(char c) {
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
