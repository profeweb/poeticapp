package lema;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DiccionariLemesCatalà {

    private static final Locale CA  = Locale.forLanguageTag("ca");

    Set<String> conjuntLemes;
    Map<String, List<String>> indexFormes;

    public DiccionariLemesCatalà(String fitxer) {
        construirDiccionari(fitxer);
    }

    private void construirDiccionari(String fitxer) {

        indexFormes  = new HashMap<>(700_000);
        conjuntLemes = new HashSet<>(200_000);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fitxer), StandardCharsets.UTF_8))) {

            String linia;
            long   numLinia = 0;

            while ((linia = br.readLine()) != null) {
                numLinia++;

                linia = linia.strip();
                if (linia.isEmpty() || linia.startsWith("#")) continue;

                // Separar per tabulador: lema TAB forma
                int tab = linia.indexOf('\t');
                if (tab < 0) continue;

                String lema  = linia.substring(0, tab).strip();
                String forma = linia.substring(tab + 1).strip();
                if (lema.isEmpty() || forma.isEmpty()) continue;

                // Índex invers: forma_lower → lemes
                String clauForma = forma.toLowerCase(CA);
                indexFormes.computeIfAbsent(clauForma, k -> new ArrayList<>()).add(lema);

                // Conjunt de lemes (en minúscules per a la cerca)
                conjuntLemes.add(lema.toLowerCase(CA));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("Diccionari carregat:%n");
        System.out.printf("  Formes indexades : %,d%n", indexFormes.size());
        System.out.printf("  Lemes únics      : %,d%n", conjuntLemes.size());
    }

}
