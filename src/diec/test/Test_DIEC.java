package diec.test;

import diec.Categoria;

import java.io.IOException;
import java.util.ArrayList;

import static diec.ConsultaWebDIEC.obteSignificatTerme;

public class Test_DIEC {

    public static void main(String[] args) {

        try {

            String word = "casa";
            String html = obteSignificatTerme(word);
            String primeraLinea = html.split("\n")[1];  // Primera acepció
            System.out.println("PRIMERA LINEA: \n" + primeraLinea);

            System.out.println("CATEGORIA GRAMATICAL: ");
            ArrayList<Categoria.CategoriaGramatical> cgs =  Categoria.CategoriaGramatical.obteCategoriesDelHTML(primeraLinea);
            for(Categoria.CategoriaGramatical cg : cgs) {
                System.out.println(cg.getNomCatala());
            }

            System.out.println("CATEGORIES TEMÀTIQUES: ");
            ArrayList<Categoria.CategoriaTematica> cts = Categoria.CategoriaTematica.obteCategoriesDelHTML(html);
            for(Categoria.CategoriaTematica ct : cts) {
                System.out.println(ct.getNomCatala());
            }
        }
        catch(IOException e){

        }

    }


}
