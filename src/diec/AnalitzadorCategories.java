package diec;

import parser.Poema;
import parser.Poemari;
import parser.Token;
import parser.Vers;
import comptes.ParaulesBuidesCatala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static diec.ConsultaWebDIEC.obteSignificatTerme;

public class AnalitzadorCategories {

    public static ArrayList<Categoria.CategoriaGramatical> consultaCategoriesGramaticals(String terme) throws IOException {
        String html = obteSignificatTerme(terme);
        if(html!=null) {
            String primeraLinea = html.split("\n")[1];
            return Categoria.CategoriaGramatical.obteCategoriesDelHTML(primeraLinea);
        }
        else {
            return null;
        }
    }

    public static ArrayList<Categoria.CategoriaTematica> consultaCategoriesTemàtiquesTerme(String terme) throws IOException {
        String html = obteSignificatTerme(terme);
        if(html!=null) {
            String primeraLinea = html.split("\n")[1];
            return Categoria.CategoriaTematica.obteCategoriesDelHTML(primeraLinea);
        }
        else {
            return null;
        }
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesVers(Vers vers) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Token token : vers.getTokensTipus(Token.Tipus.PARAULA)){
            ArrayList<Categoria.CategoriaTematica> categoriesToken = consultaCategoriesTemàtiquesTerme(token.getValor());
            if(categoriesToken!=null) {
                for (Categoria.CategoriaTematica tema : categoriesToken) {
                    System.out.println("Terme: " + token.getValor() +  " (" + tema + ")");
                    if (cgs.containsKey(tema)) {
                        int noucompte = cgs.get(tema) + 1;
                        cgs.replace(tema, noucompte);
                    } else {
                        cgs.put(tema, 1);
                    }
                }
            }
        }
        return cgs;
    }

    public static Set<String> consultaTermesCategoriaTematicaVers(Vers vers, Categoria.CategoriaTematica categoriaTematica, ParaulesBuidesCatala paraulesBuidesCatala) throws IOException{
        Set<String> termes = new HashSet<>();
        for(Token token : vers.getTokensTipus(Token.Tipus.PARAULA)){
            if(!paraulesBuidesCatala.esParaulaBuida(token.getValor())) {
                ArrayList<Categoria.CategoriaTematica> categoriesToken = consultaCategoriesTemàtiquesTerme(token.getValor());
                if(categoriesToken.contains(categoriaTematica)){
                    termes.add(token.getValor());
                }
            }
        }
        return termes;
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesVers(Vers vers, ParaulesBuidesCatala paraulesBuidesCatala) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Token token : vers.getTokensTipus(Token.Tipus.PARAULA)){
            if(!paraulesBuidesCatala.esParaulaBuida(token.getValor())) {
                ArrayList<Categoria.CategoriaTematica> categoriesToken = consultaCategoriesTemàtiquesTerme(token.getValor());
                if (categoriesToken != null) {
                    for (Categoria.CategoriaTematica tema : categoriesToken) {
                        //if(tema.getNomCatala().equals("música")) {
                            System.out.println("Terme: " + token.getValor() + " (" + tema + ")");
                        //}
                        if (cgs.containsKey(tema)) {
                            int noucompte = cgs.get(tema) + 1;
                            cgs.replace(tema, noucompte);
                        } else {
                            cgs.put(tema, 1);
                        }
                    }
                }
            }
        }
        return cgs;
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesPoema(Poema poema) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Vers vers : poema.getVersos()) {
            HashMap<Categoria.CategoriaTematica, Integer> cgsVers = consultaFreqTemesVers(vers);
            cgsVers.forEach((clau, valor) -> cgs.merge(clau, valor, Integer::sum));
        }
        return cgs;
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesPoema(Poema poema, ParaulesBuidesCatala paraulesBuidesCatala) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Vers vers : poema.getVersos()) {
            HashMap<Categoria.CategoriaTematica, Integer> cgsVers = consultaFreqTemesVers(vers, paraulesBuidesCatala);
            cgsVers.forEach((clau, valor) -> cgs.merge(clau, valor, Integer::sum));
        }
        return cgs;
    }

    public static Set<String> consultaTermesCategoriaTematicaPoema(Poema poema, Categoria.CategoriaTematica categoriaTematica, ParaulesBuidesCatala paraulesBuidesCatala) throws IOException{
        Set<String> termes = new HashSet<>();
        for(Vers vers : poema.getVersos()) {
            for (Token token : vers.getTokensTipus(Token.Tipus.PARAULA)) {
                if (!paraulesBuidesCatala.esParaulaBuida(token.getValor())) {
                    ArrayList<Categoria.CategoriaTematica> categoriesToken = consultaCategoriesTemàtiquesTerme(token.getValor());
                    if (categoriesToken!=null && categoriesToken.contains(categoriaTematica)) {
                        termes.add(token.getValor());
                    }
                }
            }
        }
        return termes;
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesPoemari(Poemari poemari) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Poema poema: poemari.getPoemes()) {
            HashMap<Categoria.CategoriaTematica, Integer> cgsPoema = consultaFreqTemesPoema(poema);
            cgsPoema.forEach((clau, valor) -> cgs.merge(clau, valor, Integer::sum));

        }
        return cgs;
    }

    public static HashMap<Categoria.CategoriaTematica, Integer> consultaFreqTemesPoemari(Poemari poemari, ParaulesBuidesCatala paraulesBuidesCatala) throws IOException {
        HashMap<Categoria.CategoriaTematica, Integer> cgs = new HashMap<>();
        for(Poema poema: poemari.getPoemes()) {
            HashMap<Categoria.CategoriaTematica, Integer> cgsPoema = consultaFreqTemesPoema(poema, paraulesBuidesCatala);
            cgsPoema.forEach((clau, valor) -> cgs.merge(clau, valor, Integer::sum));

        }
        return cgs;
    }
}
