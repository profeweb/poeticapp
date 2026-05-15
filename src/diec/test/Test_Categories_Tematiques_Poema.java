package diec.test;

import diec.AnalitzadorCategories;
import diec.Categoria;
import parser.Poema;
import parser.Poemari;
import parser.Vers;
import stats.ParaulesBuidesCatala;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static diec.ConsultaWebDIEC.obteSignificatTerme;

public class Test_Categories_Tematiques_Poema {

    public static void main(String[] args) {

        try {

            ParaulesBuidesCatala paraulesBuidesCatala = new ParaulesBuidesCatala();

            Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
            //poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
            poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");

            Poema p1 = poemari.getPoemaAt(0);

            System.out.println("CATEGORIES TEMÀTIQUES: ");
            HashMap<Categoria.CategoriaTematica, Integer> freqTematica = AnalitzadorCategories.consultaFreqTemesPoema(p1, paraulesBuidesCatala);


            LinkedHashMap<Categoria.CategoriaTematica, Integer> freqOrdenadaAlfabeticament = freqTematica
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(e -> e.getKey().getNomCatala()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            LinkedHashMap::new
                    ));

            System.out.println("\nCATEGORIES TEMÀTIQUES ORDENADES ALFABÈTICAMENT: ");
            for(Categoria.CategoriaTematica categoria : freqOrdenadaAlfabeticament.keySet()) {
                System.out.println(categoria.getNomCatala() + ": " + freqOrdenadaAlfabeticament.get(categoria));
            }

            LinkedHashMap<Categoria.CategoriaTematica, Integer> freqOrdenadaNumericament = freqTematica
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(e -> e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            LinkedHashMap::new
                    ));
            System.out.println("\nCATEGORIES TEMÀTIQUES ORDENADES NUMÈRICAMENT: ");
            for(Categoria.CategoriaTematica categoria : freqOrdenadaNumericament.keySet()) {
                System.out.println(categoria.getNomCatala() + ": " + freqOrdenadaNumericament.get(categoria));
            }
        }
        catch(IOException e){

        }

    }


}
