package gui;

public class ResumLlibre extends Resum {

    public ResumLlibre(float x, float y, float w, float h) {
        super(4, x, y, w, h);
    }

    public void setResumAutor(String nomAutor, int minAny, int maxAny){
        setResum(0, "Autor", nomAutor, minAny + " - " + maxAny);
    }

    public void setResumPoemes(int numPoemes, float mitjanaPoemesLlibre){
        setResum(1, "Poemes", String.valueOf(numPoemes), mitjanaPoemesLlibre + " Poemes/Llibre");
    }

    public void setResumEstrofes(int numEstrofes, float mitjanaEstrofesPoema){
        setResum(2, "Estrofes", String.valueOf(numEstrofes), mitjanaEstrofesPoema + " Estrofes/Poema");
    }

    public void setResumVersos(int numVersos, float mitjanaVersosEstrofa){
        setResum(3, "Versos", String.valueOf(numVersos), mitjanaVersosEstrofa + " Versos/Estrofa");
    }

}
