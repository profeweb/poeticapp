package parser;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.min;

public class DadesPoemaris {

    public static ArrayList<Autor> carregaPoemarisAutors(){

        ArrayList<Autor> autors = new ArrayList<>();

        String rutaCarpetaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\";
        File carpetaArrel = new File(rutaCarpetaArrel);
        File[] carpetesAutors = carpetaArrel.listFiles();
        for(File carpetaAutor: carpetesAutors) {
            int posParentesiObert = carpetaAutor.getName().indexOf("(");
            int posParentesiTancat = carpetaAutor.getName().indexOf(")");
            String nomAutor = carpetaAutor.getName().substring(0, posParentesiObert).trim();
            System.out.println(nomAutor);
            int anyAutor = Integer.valueOf(carpetaAutor.getName().substring(posParentesiObert + 1, posParentesiTancat).trim());
            System.out.println(anyAutor);
            Autor autor = new Autor(nomAutor, anyAutor);
            File[] carpetesPoemarisAutor = carpetaAutor.listFiles();
            for (File carpetaPoemari : carpetesPoemarisAutor) {

                int posParentesiObertAnyPoemari = carpetaPoemari.getName().indexOf("(");
                int posParentesiTancatAnyPoemari = carpetaPoemari.getName().indexOf(")");

                String titolPoemari = carpetaPoemari.getName();
                int anyPoemari = anyAutor;
                int numPoemes = carpetaPoemari.listFiles().length;

                if(posParentesiObertAnyPoemari!=-1 && posParentesiTancatAnyPoemari!=-1) {
                    titolPoemari = carpetaPoemari.getName().substring(0, posParentesiObertAnyPoemari).trim();
                    anyPoemari = Integer.valueOf(carpetaPoemari.getName().substring(posParentesiObertAnyPoemari + 1, posParentesiTancatAnyPoemari).trim());
                }
                System.out.println(titolPoemari + " , " + anyPoemari);
                Poemari poemari = new Poemari(titolPoemari, nomAutor, anyPoemari);
                poemari.parsePoemes(numPoemes, rutaCarpetaArrel + carpetaAutor.getName() + "\\" + carpetaPoemari.getName() + "\\");
                autor.afegirPoemari(poemari);
            }
            autors.add(autor);
        }

        return autors;
    }

    // Estadístiques Poemaris +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static int getNumAutors(ArrayList<Autor> autors){ return autors.size(); }

    public static String[] getNomsAutors(ArrayList<Autor> autors){
        String[] noms = new String[autors.size()];
        int numAutor = 0;
        for(Autor autor: autors){
            noms[numAutor] = autor.getNom();
            numAutor++;
        }
        return noms;
    }

    public static int getNumPoemaris(ArrayList<Autor> autors){
        int num = 0;
        for(Autor autor : autors){
            num += autor.getNumPoemaris();
        }
        return num;
    }

    public static int getNumPoemes(ArrayList<Autor> autors){
        int num = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                num += poemari.getNumPoemes();
            }
        }
        return num;
    }

    public static int getNumEstrofes(ArrayList<Autor> autors){
        int num = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                for(Poema poema : poemari.getPoemes()) {
                    num += poema.getNumEstrofes();
                }
            }
        }
        return num;
    }

    public static int getNumVersos(ArrayList<Autor> autors){
        int num = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                for(Poema poema : poemari.getPoemes()) {
                    num += poema.getNumVersos();
                }
            }
        }
        return num;
    }

    public static int getMinAnyAutors(ArrayList<Autor> autors){
        int minAny = Integer.MAX_VALUE;
        for(Autor autor : autors){
            if(autor.getAny() < minAny){
                minAny = autor.getAny();
            }
        }
        return minAny;
    }

    public static int getMaxAnyAutors(ArrayList<Autor> autors){
        int maxAny = Integer.MIN_VALUE;
        for(Autor autor : autors){
            if(autor.getAny() > maxAny){
                maxAny = autor.getAny();
            }
        }
        return maxAny;
    }

    public static float getMitjanaLlibresAutors(ArrayList<Autor> autors){
        float numLlibres = 0;
        for(Autor autor : autors){
            numLlibres += autor.getNumPoemaris();
        }
        return numLlibres / autors.size();
    }

    public static float getMitjanaPoemesAutors(ArrayList<Autor> autors){
        float num = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                num += poemari.getNumPoemes();
            }
        }
        return num / autors.size();
    }

    public static float getMitjanaEstrofesPoemes(ArrayList<Autor> autors){
        float numEstrofes = 0, numPoemes = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                numEstrofes += poemari.getNumEstrofes();
                numPoemes += poemari.getNumPoemes();
            }
        }
        return numEstrofes / numPoemes;
    }

    public static float getMitjanaVersosEstrofes(ArrayList<Autor> autors){
        float numEstrofes = 0, numVersos = 0;
        for(Autor autor : autors){
            for(Poemari poemari: autor.getPoemaris()){
                numEstrofes += poemari.getNumEstrofes();
                numVersos += poemari.getNumVersos();
            }
        }
        return numVersos/ numEstrofes;
    }

    // Estadístiques Autor/a +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static int getNumPoemaris(Autor autor){
        return autor.getNumPoemaris();
    }

    public static String[] getTitolsSencersPoemaris(Autor autor){
        String[] titols = new String[autor.getNumPoemaris()];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()){
            titols[numPoemari] = poemari.getTitol() + " (" + poemari.getAny() + ")";
            numPoemari++;
        }
        return titols;
    }

    public static String[] getTitolsPoemaris(Autor autor){
        String[] titols = new String[autor.getNumPoemaris()];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()){
            titols[numPoemari] = poemari.getTitol().substring(0, min(15, poemari.getTitol().length()));
            numPoemari++;
        }
        return titols;
    }

    public static String[] getTitolAnysPoemaris(Autor autor){
        String[] titols = new String[autor.getNumPoemaris()];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()){
            titols[numPoemari] = poemari.getTitol() + "(" + poemari.getAny() +")";
            numPoemari++;
        }
        return titols;
    }

    public static int getMinAnyAutor(Autor autor){
        int minAny = Integer.MAX_VALUE;
        for(Poemari poemari : autor.getPoemaris()){
            if(poemari.getAny() < minAny){
                minAny = poemari.getAny();
            }
        }
        return minAny;
    }

    public static int getMaxAnyAutor(Autor autor){
        int maxAny = Integer.MIN_VALUE;
        for(Poemari poemari : autor.getPoemaris()){
            if(poemari.getAny() > maxAny){
                maxAny = poemari.getAny();
            }
        }
        return maxAny;
    }

    public static int getNumPoemes(Autor autor){
        int num = 0;
        for(Poemari poemari : autor.getPoemaris()){
            num += poemari.getNumPoemes();
        }
        return num;
    }

    public static int getNumEstrofes(Autor autor){
        int num = 0;
        for(Poemari poemari : autor.getPoemaris()){
            for(Poema poema : poemari.getPoemes()) {
                num += poema.getNumEstrofes();
            }
        }
        return num;
    }

    public static int getNumVersos(Autor autor){
        int num = 0;
        for(Poemari poemari : autor.getPoemaris()){
            for(Poema poema : poemari.getPoemes()) {
                num += poema.getNumVersos();
            }
        }
        return num;
    }

    public static float[] getNumVersosPoemaris(Autor autor){
        float[] numVersos = new float[autor.getNumPoemaris()];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()){
            numVersos[numPoemari] = poemari.getNumVersos();
            numPoemari++;
        }
        return numVersos;
    }

    public static float[][] getNumVersosPoemesPoemaris(Autor autor){
        int numMaxPoemes = autor.getMaxNumPoemes();
        float[][] numVersosPoemes = new float[autor.getNumPoemaris()][numMaxPoemes];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()) {
            int numPoema = 0;
            for (Poema poema : poemari.getPoemes()){
                numVersosPoemes[numPoemari][numPoema] = poema.getNumVersos();
                numPoema++;
            }
            numPoemari++;
        }
        return numVersosPoemes;
    }

    public static float getMitjanaPoemesLlibre(Autor autor){
        float num = 0;
        for(Poemari poemari : autor.getPoemaris()){
            num += poemari.getNumPoemes();
        }
        return num / autor.getNumPoemaris();
    }

    public static float getMitjanaEstrofesPoema(Autor autor){
        float numEstrofes = 0, numPoemes = 0;
        for(Poemari poemari : autor.getPoemaris()){
            for(Poema poema : poemari.getPoemes()) {
                numEstrofes += poema.getNumEstrofes();
                numPoemes++;
            }
        }
        return numEstrofes / numPoemes;
    }

    public static float getMitjanaVersosEstrofa(Autor autor){
        float numEstrofes = 0, numVersos = 0;
        for(Poemari poemari : autor.getPoemaris()){
            for(Poema poema : poemari.getPoemes()) {
                for(Estrofa estrofa: poema.getEstrofes()) {
                    numEstrofes++;
                    numVersos+= estrofa.getNumVersos();
                }
            }
        }
        return numVersos / numEstrofes;
    }

    // Estadístiques Poemari ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static String[] getTitolsPoemes(Poemari poemari){
        String[] titols = new String[poemari.getNumPoemes()];
        int numPoema = 0;
        for(Poema poema : poemari.getPoemes()){
            titols[numPoema] = poema.getNumero() + ". " + poema.getTitol().substring(0, min(10, poema.getTitol().length()));
            numPoema++;
        }
        return titols;
    }

    public static String[] getNumerosPoemes(Poemari poemari){
        String[] titols = new String[poemari.getNumPoemes()];
        int numPoema = 0;
        for(Poema poema : poemari.getPoemes()){
            titols[numPoema] = String.valueOf(poema.getNumero());
            numPoema++;
        }
        return titols;
    }

    public static float getMitjanaEstrofesPoema(Poemari poemari){
        float num = 0;
        for(Poema poema : poemari.getPoemes()){
            num += poema.getNumEstrofes();
        }
        return num / poemari.getNumPoemes();
    }

    public static float getMitjanaVersosEstrofes(Poemari poemari){
        float numEstrofes = 0, numVersos = 0;
        for(Poema poema : poemari.getPoemes()){
            numEstrofes += poema.getNumEstrofes();
            numVersos += poema.getNumVersos();
        }
        return numVersos / numEstrofes;
    }

    // Dades Poemaris ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ArrayList<String> getAutors(ArrayList<Poemari> poemaris){
        ArrayList<String> autors = new ArrayList<>();
        for(Poemari poemari: poemaris){
            if(!autors.contains(poemari.autor)){
                autors.add(poemari.autor);
            }
        }
        return autors;
    }

    public static String[][] getLlibresInfo(ArrayList<Poemari> poemaris){
        String[][] info = new String[poemaris.size()][4];
        int numPoemari = 0;
        for(Poemari poemari : poemaris){
            info[numPoemari][0] = poemari.titol;
            info[numPoemari][1] = String.valueOf(poemari.any);
            info[numPoemari][2] = String.valueOf(poemari.getNumPoemes());
            info[numPoemari][3] = poemari.autor;
            numPoemari++;
        }
        return info;
    }

    public static String[][] getAutorsInfo(ArrayList<Autor> autors){
        String[][] info = new String[autors.size()][2];
        int numAutor = 0;
        for(Autor autor : autors){
            info[numAutor][0] = autor.getNom();
            info[numAutor][1] = String.valueOf(autor.any);
            numAutor++;
        }
        return info;
    }

    public static String[][] getAutorInfo(Autor autor){

        String[][] info = new String[autor.getNumPoemaris()][7];
        int numPoemari = 0;
        for(Poemari poemari : autor.getPoemaris()){
            if(poemari.autor.equals(autor.nom)){
                info[numPoemari][0] = poemari.titol;
                info[numPoemari][1] = String.valueOf(poemari.any);
                info[numPoemari][2] = String.valueOf(poemari.getNumPoemes());
                info[numPoemari][3] = String.valueOf(poemari.getNumEstrofes());
                info[numPoemari][4] = String.valueOf(poemari.getNumVersos());
                info[numPoemari][5] = String.valueOf(poemari.getNumParaules());
                info[numPoemari][6] = String.valueOf(poemari.getNumSilabes());
            }
            numPoemari++;
        }
        return info;
    }

    public int[] getResumLlibresAutor(Autor autor){
        int[] resum = new int[3];
        resum[0] = autor.getNumPoemaris();
        resum[1] = autor.getPrimerPoemari();
        resum[2] = autor.getDarrerPoemari();
        return resum;
    }

    public static String[][] getLlibresAutorsInfo(ArrayList<Autor> autors){
        String[][] info = new String[getNumPoemaris(autors)][2];
        int numPoemari = 0;
        for(Autor autor : autors){
            for(Poemari poemari : autor.getPoemaris()) {
                info[numPoemari][0] = poemari.getTitol();
                info[numPoemari][1] = poemari.getAutor();
                numPoemari++;
            }
        }
        return info;
    }

    public static String[][] getPoemesAutorsInfo(ArrayList<Autor> autors){
        String[][] info = new String[getNumPoemes(autors)][2];
        int numPoema = 0;
        for(Autor autor : autors){
            for(Poemari poemari : autor.getPoemaris()) {
                for(Poema poema : poemari.getPoemes()) {
                    String textPoema = poema.getVersAt(0).text;
                    info[numPoema][0] = poema.numero + ". " + textPoema.substring(0, min(20, textPoema.length())) +" ...";
                    info[numPoema][1] = poemari.getTitol();
                    numPoema++;
                }
            }
        }
        return info;
    }

    // Dades Poemari
    public static String[][] getLlibreInfo(Poemari poemari){
        String[][] info = new String[poemari.getNumPoemes()][6];
        int numPoema = 0;
        for(Poema poema : poemari.getPoemes()){
            info[numPoema][0] = poema.getTitol();
            info[numPoema][1] = String.valueOf(poema.numero);
            info[numPoema][2] = String.valueOf(poema.getNumEstrofes());
            info[numPoema][3] = String.valueOf(poema.getNumVersos());
            info[numPoema][4] = String.valueOf(poema.getNumParaules());
            info[numPoema][5] = String.valueOf(poema.getNumSillabes());
            numPoema++;
        }
        return info;
    }

    // Dades Poemari
    public static String[][] getPoemaInfo(Poema poema){
        String[][] info = new String[poema.getNumEstrofes()][5];
        int numPoema = 0;
        for(Estrofa estrofa : poema.getEstrofes()){
            info[numPoema][0] = estrofa.getVersAt(0).text + " ...";
            info[numPoema][1] = String.valueOf(estrofa.numero);
            info[numPoema][2] = String.valueOf(estrofa.getNumVersos());
            info[numPoema][3] = String.valueOf(estrofa.getNumParaules());
            info[numPoema][4] = String.valueOf(estrofa.getNumSillabes());
            numPoema++;
        }
        return info;
    }

    //public static float[][] getResumsAutor(ArrayList<Poemari> poemaris, String autor){
        /*
        resumAutor = new ResumAutor(300, 260, p5.width-350, 200);
        resumAutor.setColorsFonts(colors, fonts);
        ((ResumAutor)resumAutor).setResumLlibres(12, 1856, 2010);
        ((ResumAutor)resumAutor).setResumPoemes(28, 12);
        ((ResumAutor)resumAutor).setResumEstrofes(256, 12);
        ((ResumAutor)resumAutor).setResumVersos(13873, 234);
         */

    //
    // }


    // Dades estadístiques gràfiques

    public static float[] getNumVersosPoemes(Poemari poemari){
        float[] num = new float[poemari.getNumPoemes()];
        int numPoema = 0;
        for(Poema poema : poemari.getPoemes()){
            num[numPoema] = poema.getNumVersos();
            numPoema++;
        }
        return num;
    }

    public static float[][] getNumOcurrenciesTermes(String[] termes, ArrayList<Poemari> poemaris){
        float[][] info = new float[poemaris.size()][termes.length];
        int numPoemari = 0;
        for(Poemari poemari : poemaris){
            int numTerme =  0;
            for(String terme: termes) {
                info[numPoemari][numTerme] = poemari.getNumOcurrenciesTerme(terme);
                numTerme++;
            }
        }
        return info;
    }

    public static int getNumTotalOcurrenciesTerme(String terme, ArrayList<Poemari> poemaris){
        int num = 0;
        for(Poemari poemari : poemaris){
            num += poemari.getNumOcurrenciesTerme(terme);
        }
        return num;
    }

    public static float[] getNumOcurrenciesTerme(String terme, ArrayList<Poemari> poemaris){
        float[] info = new float[poemaris.size()];
        int numPoemari = 0;
        for(Poemari poemari : poemaris){
            info[numPoemari] = poemari.getNumOcurrenciesTerme(terme);
            numPoemari++;
        }
        return info;
    }

    public static void printArray2D(String[][] info){
        for(int i=0; i<info.length; i++){
            for(int j=0; j<info[0].length; j++){
                System.out.print(info[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void printArray2D(float[][] info){
        for(int i=0; i<info.length; i++){
            for(int j=0; j<info[0].length; j++){
                System.out.print(info[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Carrega dades

}
