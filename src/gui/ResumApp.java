package gui;

public class ResumApp extends Resum {

    public ResumApp(float x, float y, float w, float h) {
        super(5, x, y, w, h);
    }

    public void setResumAutors(int numAutors, int minAny, int maxAny){
        setResum(0, "Autors", String.valueOf(numAutors), minAny + " - " + maxAny);
    }

    public void setResumLlibres(int numLlibres, float mitjanaLlibresAutor){
        setResum(1, "Llibres", String.valueOf(numLlibres), mitjanaLlibresAutor + " Llibres/Autor");
    }

    public void setResumPoemes(int numPoemes, float mitjanaPoemesLlibre){
        setResum(2, "Poemes", String.valueOf(numPoemes), mitjanaPoemesLlibre + " Poemes/Llibre");
    }

    public void setResumEstrofes(int numEstrofes, float mitjanaEstrofesPoema){
        setResum(3, "Estrofes", String.valueOf(numEstrofes), mitjanaEstrofesPoema + " Estrofes/Poema");
    }

    public void setResumVersos(int numVersos, float mitjanaVersosEstrofa){
        setResum(4, "Versos", String.valueOf(numVersos), mitjanaVersosEstrofa + " Versos/Estrofa");
    }

}
