package diec.test;

import comptes.ParaulesBuidesCatala;
import diec.Categoria;
import parser.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static diec.AnalitzadorCategories.*;

public class Test_Termes_Categoriea_Tematica_Poemaris {

    public static void main(String[] args) {

        try {

            ParaulesBuidesCatala paraulesBuidesCatala = new ParaulesBuidesCatala();

            ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
            ArrayList<Poemari> poemaris = autors.get(0).getPoemaris();

            Categoria.CategoriaTematica c = Categoria.CategoriaTematica.ART;

            Set<String> totsTermes = new HashSet<>();
            Set<String>[] termesCategoriaPoemari = new HashSet[poemaris.size()];

            int numPoemari = 0;
            for(Poemari poemari : autors.get(0).getPoemaris()) {
                termesCategoriaPoemari[numPoemari]  = consultaTermesCategoriaTematicaPoemari(poemari, paraulesBuidesCatala, c);
                System.out.println("\nTermes de la categoria " + c + " en el poemari " + poemari.getTitol() + ": " + termesCategoriaPoemari[numPoemari].size());
                totsTermes.addAll(termesCategoriaPoemari[numPoemari]);
                for (String terme : termesCategoriaPoemari[numPoemari]) {
                    System.out.println(terme);
                }
                System.out.println();
                numPoemari++;
            }

        }
        catch(IOException e){

        }

    }


}
