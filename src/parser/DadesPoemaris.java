package parser;

import java.util.ArrayList;

public class DadesPoemaris {

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

    //public static float[][] getResumsAutor(ArrayList<Poemari> poemaris, String autor){
        /*
        resumAutor = new ResumAutor(300, 260, p5.width-350, 200);
        resumAutor.setColorsFonts(colors, fonts);
        ((ResumAutor)resumAutor).setResumLlibres(12, 1856, 2010);
        ((ResumAutor)resumAutor).setResumPoemes(28, 12);
        ((ResumAutor)resumAutor).setResumEstrofes(256, 12);
        ((ResumAutor)resumAutor).setResumVersos(13873, 234);
         */

    //}

    public static void printArray2D(String[][] info){
        for(int i=0; i<info.length; i++){
            for(int j=0; j<info[0].length; j++){
                System.out.print(info[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
