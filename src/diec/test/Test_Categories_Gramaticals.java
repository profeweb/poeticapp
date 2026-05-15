package diec.test;

import diec.Categoria;
import parser.Poema;
import parser.Poemari;
import parser.Token;
import parser.Vers;

import java.io.IOException;
import java.util.ArrayList;

import static diec.AnalitzadorCategories.consultaCategoriesGramaticals;
import static diec.ConsultaWebDIEC.obteSignificatTerme;

public class Test_Categories_Gramaticals {

    public static void main(String[] args) throws IOException {

        Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
        //poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/Poemes a Nai (1960)/");
        poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");

        Poema p1 = poemari.getPoemaAt(2);
        Vers v1 = p1.getVersAt(0);

        for(Token token : v1.getParaules()){
            System.out.println("PARAULA: " + token.getValor());
            System.out.println("CATEGORIES GRAMATICALS: " );
            ArrayList<Categoria.CategoriaGramatical> cgs = consultaCategoriesGramaticals(token.getValor());
            for(Categoria.CategoriaGramatical cg : cgs){
                System.out.print(cg.getNomCatala()+ "\t");
            }
            System.out.println();
        }

    }


}
