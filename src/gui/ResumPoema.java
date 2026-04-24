package gui;

public class ResumPoema extends Resum {

    public ResumPoema(float x, float y, float w, float h) {
        super(4, x, y, w, h);
    }

    public void setResumAutor(String nomAutor, int minAny, int maxAny){
        setResum(0, "Autor", nomAutor, minAny + " - " + maxAny);
    }

    public void setResumLlibre(String titolLlibre, int numLlibres){
        setResum(1, "Llibre", titolLlibre, numLlibres + " Llibres");
    }

    public void setResumEstrofes(int numEstrofes, float mitjanaEstrofesPoema){
        setResum(2, "Estrofes", String.valueOf(numEstrofes), mitjanaEstrofesPoema + " Estrofes/Poema");
    }

    public void setResumVersos(int numVersos, float mitjanaVersosEstrofa){
        setResum(3, "Versos", String.valueOf(numVersos), mitjanaVersosEstrofa + " Versos/Estrofa");
    }

}
