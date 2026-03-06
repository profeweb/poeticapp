package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class Gui {

    public enum PANTALLA {INICI, AUTOR_INICI, AUTOR_ESTADISTICA, AUTOR_VISUAL };
    public PANTALLA pantallaActual;
    PApplet p5;

    TarjaResum tarjaResum;
    Tarja tarja;
    Taula taula;
    Boto boto;
    BotoIcona botoIcona;
    Desplegable desplegable;
    GraellaTarja graellaTarja;

    Colors colors;
    Fonts fonts;

    PImage img;

    public Gui(PApplet p5){
        this.p5 = p5;
        this.colors = new Colors(p5);
        this.fonts = new Fonts(p5);

        img = p5.loadImage("data/img/foto.jpg");

        setElementsGUI();
    }


    public void setElementsGUI(){

        tarja = new Tarja(50, 100, 300, 300);
        tarja.setTextos("titol", "subtitol");
        tarja.setImatge(p5, p5.loadImage("data/img/foto.jpg"));
        tarja.setColors(colors);
        tarja.setFonts(fonts);

        tarjaResum = new TarjaResum(400, 100, 300, 300);
        tarjaResum.setTextos("aaa", "bb", "cc");
        tarjaResum.setColors(colors);
        tarjaResum.setFonts(fonts);

        taula = new Taula(750, 100, 400, 400);
        String[] cols = { "col1", "col2", "col3"};
        String[][] dades = {{"1", "2", "3"}, {"4", "5", "6"}, {"4", "5", "6"}, {"4", "5", "6"}, {"4", "5", "6"}};
        float[] mides = {33, 33, 33};
        taula.setTitols(cols);
        taula.setDades(dades);
        taula.setMidaColumnes(mides);
        taula.setNumFilesPagina(3);
        taula.setColors(colors);
        taula.setFonts(fonts);

        //taula.paginaSeguent();

        boto = new Boto(p5, "OK", 50, 50, AMPLE_BOTO, ALT_BOTO);
        boto.setColors(colors);
        boto.setFonts(fonts);

        String[] opcions = {"opcioA", "opcioB"};
        desplegable = new Desplegable(opcions, 200, 50, AMPLE_DESPLEGABLE, ALT_DESPLEGABLE);
        desplegable.setColors(colors);
        desplegable.setFonts(fonts);


        graellaTarja = new GraellaTarja(2, 4, 100, 100, 600, 400);
        String[][] dadesGraella = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaTarja.setData(dadesGraella);
        graellaTarja.setColors(colors);
        graellaTarja.setFonts(fonts);
        graellaTarja.setTarges(p5);
        graellaTarja.setImatges(p5,img);

        botoIcona = new BotoIcona(p5, "Hola", 0x1F600, 600, 50, AMPLE_BOTO_MENU, ALT_BOTO);
        botoIcona.setColors(colors);
        botoIcona.setFonts(fonts);

    }


    public void dibuixaPantalla(){
        tarjaResum.display(p5);
        tarja.display(p5);
        taula.display(p5);
        boto.display(p5);
        desplegable.display(p5);
        graellaTarja.display(p5);

        botoIcona.display(p5);
    }


    public void mouseEvents(PApplet p5){

        desplegable.update(p5);

        if(boto.mouseDins(p5)){
            p5.println("CLICK BOTÓ");
        }

        graellaTarja.clickSobreTarges(p5);
    }

}
