package diec.test;

import diec.AnalitzadorCategories;
import diec.Categoria;
import parser.Poema;
import parser.Poemari;
import parser.Vers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static diec.ConsultaWebDIEC.obteSignificatTerme;

public class Test_Categories_Tematiques_Poema {

    public static void main(String[] args) {

        try {

            Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
            //poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
            poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");

            Poema p1 = poemari.getPoemaAt(0);

            System.out.println("CATEGORIES TEMÀTIQUES: ");
            HashMap<Categoria.CategoriaTematica, Integer> freqTematica = AnalitzadorCategories.consultaFreqTemesPoema(p1);
            for(Categoria.CategoriaTematica categoria : freqTematica.keySet()) {
                System.out.println(categoria.getNomCatala() + ": " + freqTematica.get(categoria));
            }
        }
        catch(IOException e){

        }

    }


}
