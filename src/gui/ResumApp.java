package gui;

import processing.core.PApplet;

public class ResumApp extends Resum {

    public ResumApp(float x, float y, float w, float h) {
        super(5, x, y, w, h);
    }

    public void setResumAutors(int numAutors, int minAny, int maxAny){
        setResum(0, "Autors", String.valueOf(numAutors), minAny + " - " + maxAny);
    }

    public void setResumLlibres(int numLlibres, float mitjanaLlibresAutor){
        setResum(1, "Llibres", String.valueOf(numLlibres), PApplet.nf(mitjanaLlibresAutor, 0, 2) + " Llibres/Autor");
    }

    public void setResumPoemes(int numPoemes, float mitjanaPoemesLlibre){
        setResum(2, "Poemes", String.valueOf(numPoemes), PApplet.nf(mitjanaPoemesLlibre, 0, 2) + " Poemes/Llibre");
    }

    public void setResumEstrofes(int numEstrofes, float mitjanaEstrofesPoema){
        setResum(3, "Estrofes", String.valueOf(numEstrofes), PApplet.nf(mitjanaEstrofesPoema, 0, 2) + " Estrofes/Poema");
    }

    public void setResumVersos(int numVersos, float mitjanaVersosEstrofa){
        setResum(4, "Versos", String.valueOf(numVersos), PApplet.nf(mitjanaVersosEstrofa, 0, 2) + " Versos/Estrofa");
    }

}
