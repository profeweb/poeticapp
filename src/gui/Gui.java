package gui;

import processing.core.PApplet;

import static gui.Mides.*;

public class Gui {

    public enum PANTALLA {INICI, AUTOR_INICI, AUTOR_ESTADISTICA, AUTOR_VISUAL };
    public PANTALLA pantallaActual;
    PApplet p5;

    TarjaResum tarjaResum;
    Tarja tarja;
    Taula taula;
    Boto boto;
    Desplegable desplegable;

    Colors colors;
    Fonts fonts;

    public Gui(PApplet p5){
        this.p5 = p5;
        this.colors = new Colors(p5);
        this.fonts = new Fonts(p5);

        setElementsGUI();
    }


    public void setElementsGUI(){

        tarja = new Tarja(100, 100, 300, 300);
        tarja.setTextos("titol", "subtitol");
        tarja.setColors(colors);
        tarja.setFonts(fonts);

        tarjaResum = new TarjaResum(400, 100, 300, 300);
        tarjaResum.setTextos("aaa", "bb", "cc");
        tarjaResum.setColors(colors);
        tarjaResum.setFonts(fonts);

        taula = new Taula(300, 300, 400, 400);
        String[] cols = { "col1", "col2", "col3"};
        String[][] dades = {{"1", "2", "3"}, {"4", "5", "6"}, {"4", "5", "6"}, {"4", "5", "6"}, {"4", "5", "6"}};
        float[] mides = {33, 33, 33};
        taula.setTitols(cols);
        taula.setDades(dades);
        taula.setMidaColumnes(mides);
        taula.setNumFilesPagina(3);
        taula.setColors(colors);
        taula.setFonts(fonts);

        boto = new Boto(p5, "OK", 50, 50, AMPLE_BOTO, ALT_BOTO);
        boto.setColors(colors);
        boto.setFonts(fonts);

        String[] opcions = {"opcioA", "opcioB"};
        desplegable = new Desplegable(opcions, 500, 100, AMPLE_DESPLEGABLE, ALT_DESPLEGABLE);
        desplegable.setColors(colors);
        desplegable.setFonts(fonts);

    }


    public void dibuixaPantalla(){
        tarja.display(p5);
        //taula.display(p5);
        boto.display(p5);
        desplegable.display(p5);
    }


    public void mouseEvents(PApplet p5){

        desplegable.update(p5);

        if(boto.mouseDins(p5)){
            p5.println("CLICK BOTÓ");
        }
    }

}
