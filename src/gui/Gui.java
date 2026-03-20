package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class Gui {

    // PANTALLES
    public enum PANTALLA {INICI, AUTOR_INICI, AUTOR_ESTADISTICA, AUTOR_VISUAL };
    public PANTALLA pantallaActual;
    PApplet p5;


    // ELEMENTS GUI
    TarjaResum tarjaResum;
    Tarja tarja;
    Taula taula;
    Boto boto;
    BotoIcona botoIcona;
    BotoFavorit botoFavorit;
    Desplegable desplegable;
    GraellaTarja graellaTarja;
    BotonsGrup botonsGrup;
    EntradaText entradaText;
    SubMenu subMenu1;

    // MEDIA
    Colors colors;
    Fonts fonts;
    PImage img;


    public Gui(PApplet p5){
        this.p5 = p5;
        setMedia(p5);
        setElementsGUI();
    }

    public void setMedia(PApplet p5){
        this.colors = new Colors(p5);
        this.fonts = new Fonts(p5);
        img = p5.loadImage("data/img/foto.jpg");
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

        taula = new Taula(50, 600, 600, 400);
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


        graellaTarja = new GraellaTarja(2, 4, 750, 100, 1100, 600);
        String[][] dadesGraella = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaTarja.setData(dadesGraella);
        graellaTarja.setColors(colors);
        graellaTarja.setFonts(fonts);
        graellaTarja.setTarges(p5);
        graellaTarja.setImatges(p5,img);
        graellaTarja.setBotons(p5);

        graellaTarja.paginaSeguent();

        botoIcona = new BotoIcona(p5, "Hola", 0x1F600, 600, 50, AMPLE_BOTO_MENU, ALT_BOTO);
        botoIcona.setColors(colors);
        botoIcona.setFonts(fonts);

        botoFavorit = new BotoFavorit(1200, 50, BOTO_FAVORIT);
        botoFavorit.setColors(colors);
        botoFavorit.setFonts(fonts);

        String[] titols = {"AUTOR", "LLIBRE", "POEMARI", "ESTADISTIQUES"};
        botonsGrup = new BotonsGrup( 100, 800, AMPLE_BOTO_MENU, ALT_BOTO);
        botonsGrup.setColors(colors);
        botonsGrup.setFonts(fonts);
        botonsGrup.setBotons(p5, titols);

        entradaText = new EntradaText(p5, 100, 100, AMPLE_ENTRADA_TEXT, ALT_ENTRADA_TEXT, colors, fonts);
        entradaText.setTextEtiqueta("NOM");
        entradaText.setColors(colors);
        entradaText.setFonts(fonts);

        subMenu1 = new SubMenu("Submenu1", 100, 100, 400, 300);
        subMenu1.setColors(colors);
        subMenu1.setFonts(fonts);
        subMenu1.afegirOpcioMenu("Opció 1", 0x1F600);
        subMenu1.afegirOpcioMenu("Opció 2", 0x1F600);
        subMenu1.afegirOpcioMenu("Opció 3", 0x1F600);

    }


    public void dibuixaPantalla(){

        /*

        tarjaResum.display(p5);
        tarja.display(p5);
        taula.display(p5);
        boto.display(p5);
        desplegable.display(p5);
        graellaTarja.display(p5);

        botoIcona.display(p5);

        botoFavorit.display(p5);
        botonsGrup.display(p5);



        entradaText.display(p5);


         */
        subMenu1.display(p5);
    }


    public void mouseEvents(PApplet p5){

        desplegable.update(p5);

        if(boto.mouseDins(p5)){
            p5.println("CLICK BOTÓ");
        }

        graellaTarja.clickSobreTarges(p5);
        graellaTarja.clickBotoAnterior(p5);
        graellaTarja.clickBotoSeguent(p5);

        botoFavorit.clickFavorit(p5);

        botonsGrup.clickBotons(p5);

        entradaText.updateClick(p5);

        int opcio = subMenu1.opcioClicada(p5);
        if(opcio!=-1){
            System.out.println("OPCIÓ "+ opcio);
        }


    }

    public void keyPressedEvent(PApplet p5){
        entradaText.updateKeyPressed(p5.keyCode);
    }

    public void keyTypedEvent(PApplet p5){
        entradaText.updateKeyTyped(p5.key);
    }

}
