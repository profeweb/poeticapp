package diec.test;

import comptes.ParaulesBuidesCatala;
import diec.AnalitzadorCategories;
import diec.Categoria;
import parser.Poema;
import parser.Poemari;
import parser.Vers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static diec.AnalitzadorCategories.consultaTermesCategoriaTematicaPoema;
import static diec.AnalitzadorCategories.consultaTermesCategoriaTematicaVers;

public class Test_Termes_Categoriea_Tematica_Poema {

    public static void main(String[] args) {

        try {

            ParaulesBuidesCatala paraulesBuidesCatala = new ParaulesBuidesCatala();

            Poemari poemari = new Poemari("Poemes a Nai", "MA Rieria", 1988);
            poemari.parsePoemes(13, "data/poems/Miquel Àngel Riera (1930)/El pis de la badia (1993)/");
            Poema p1 = poemari.getPoemaAt(0);
            Vers v1 = p1.getVersAt(0);

            Categoria.CategoriaTematica c = Categoria.CategoriaTematica.LEXIC_COMU;
            System.out.println("\nTERMES DE LA CATEGORIA " + c + " EN EL VERS");
            Set<String> termesCategoria = consultaTermesCategoriaTematicaVers(v1, c, paraulesBuidesCatala);
            for(String terme : termesCategoria){
                System.out.println(terme);
            }

            System.out.println("\nTERMES DE LA CATEGORIA " + c + " EN EL POEMA");
            Set<String> termesCategoriaPoema = consultaTermesCategoriaTematicaPoema(p1, c, paraulesBuidesCatala);
            for(String terme : termesCategoriaPoema){
                System.out.println(terme);
            }

        }
        catch(IOException e){

        }

    }


}
