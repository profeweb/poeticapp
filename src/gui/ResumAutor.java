package gui;

import processing.core.PApplet;

public class ResumAutor extends GuiElement {

    TarjaResum[] resums;
    float wResum;
    float margeHoritzontal = 50;

    public ResumAutor(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.wResum = (w - 3*margeHoritzontal) / 4f;
        this.resums = new TarjaResum[4];
    }

    public void setResum(int numResum, String titolResum, String valorResum, String detallResum){
        resums[numResum] = new TarjaResum(x + numResum*(wResum + margeHoritzontal), y, wResum, h);
        resums[numResum].setFonts(fonts);
        resums[numResum].setColors(colors);
        resums[numResum].setTextos(titolResum, valorResum, detallResum);
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


    public void display(PApplet p5){
        for(TarjaResum r: resums){
            r.display(p5);
        }
    }
}
