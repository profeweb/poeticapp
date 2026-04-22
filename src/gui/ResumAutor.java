package gui;

import processing.core.PApplet;

public class ResumAutor extends Resum {

    public ResumAutor(float x, float y, float w, float h) {
        super(4, x, y, w, h);
    }

    public void setResumLlibres(int numLlibres, int minAny, int maxAny){
        setResum(0, "Llibres", String.valueOf(numLlibres), minAny + " - " + maxAny);
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
