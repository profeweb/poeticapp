package gui;

import processing.core.PApplet;

public class ResumLlibre extends Resum {

    public ResumLlibre(float x, float y, float w, float h) {
        super(4, x, y, w, h);
    }

    public void setResumAutor(String nomAutor, int minAny, int maxAny){
        setResum(0, "Autor", nomAutor, minAny + " - " + maxAny);
    }

    public void setResumPoemes(int numPoemes, float mitjanaPoemesLlibre){
        setResum(1, "Poemes", String.valueOf(numPoemes), PApplet.nf(mitjanaPoemesLlibre, 0, 2) + " Poemes/Llibre");
    }

    public void setResumEstrofes(int numEstrofes, float mitjanaEstrofesPoema){
        setResum(2, "Estrofes", String.valueOf(numEstrofes), PApplet.nf(mitjanaEstrofesPoema, 0, 2) + " Estrofes/Poema");
    }

    public void setResumVersos(int numVersos, float mitjanaVersosEstrofa){
        setResum(3, "Versos", String.valueOf(numVersos), PApplet.nf(mitjanaVersosEstrofa, 0, 2) + " Versos/Estrofa");
    }

}
