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
    TaulaPaginada taula;
    Boto boto;
    BotoIcona botoIcona;
    BotoFavorit botoFavorit;
    Desplegable desplegable;
    GraellaTarja graellaAutors, graellaLlibres;
    BotonsGrup botonsGrup;
    EntradaText entradaText;
    EntradaCercador entradaCercador;

    ResumAutor resumAutor;

    MenuApp menuApp;

    // MEDIA
    Colors colors;
    Fonts fonts;
    PImage img;


    public Gui(PApplet p5){
        this.p5 = p5;
        this.pantallaActual = PANTALLA.AUTOR_INICI;
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

        taula = new TaulaPaginada(300, 500, p5.width-350, 500);
        String[] cols = { "Títol", "Any", "Poemes", "Estrofes", "Versos", "Paraules", "Síl·labes"};
        String[][] dades = {{"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},
                {"1", "2", "3", "4", "5", "6", "7"},


        };
        float[] mides = {40, 10, 10, 10, 10, 10, 10};
        taula.setTitols(cols);
        taula.setDades(dades);
        taula.setMidaColumnes(mides);
        taula.setNumFilesPagina(10);
        taula.setColors(colors);
        taula.setFonts(fonts);
        taula.setBotonsPaginacio();

        taula.paginaSeguent();

        boto = new Boto("OK", 50, 50, AMPLE_BOTO, ALT_BOTO);
        boto.setColors(colors);
        boto.setFonts(fonts);

        String[] opcions = {"opcioA", "opcioB"};
        desplegable = new Desplegable(opcions, 200, 25, AMPLE_DESPLEGABLE, ALT_DESPLEGABLE);
        desplegable.setColors(colors);
        desplegable.setFonts(fonts);


        graellaAutors = new GraellaTarja(1, 5, 310, 180, 1550, 300);
        String[][] dadesGraella = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaAutors.setData(dadesGraella);
        graellaAutors.ordenaTargesPerTitolAsc();
        graellaAutors.setTitol("Autors");
        graellaAutors.setColors(colors);
        graellaAutors.setFonts(fonts);
        graellaAutors.setTarges(p5);
        graellaAutors.setImatges(p5,img);
        graellaAutors.setBotons(p5);
        graellaAutors.setSelecccionableOrdre(p5);


        graellaLlibres = new GraellaTarja(1, 5, 310, 650, 1550, 300);
        String[][] dadesGraella2 = { {"a", "d1"},{"f", "d1"},{"b", "d1"},{"g", "d1"},{"c", "d1"},{"k", "d1"},{"d", "d1"},{"v", "d1"},{"e", "d1"} };
        graellaLlibres.setData(dadesGraella2);
        graellaLlibres.ordenaTargesPerTitolAsc();
        graellaLlibres.setTitol("Llibres");
        graellaLlibres.setColors(colors);
        graellaLlibres.setFonts(fonts);
        graellaLlibres.setTarges(p5);
        graellaLlibres.setImatges(p5,img);
        graellaLlibres.setBotons(p5);
        graellaLlibres.setSelecccionableOrdre(p5);

        //graellaTarja.paginaSeguent();

        botoIcona = new BotoIcona("Hola", 0x1F600, 600, 50, AMPLE_BOTO_MENU, ALT_BOTO);
        botoIcona.setColors(colors);
        botoIcona.setFonts(fonts);

        botoFavorit = new BotoFavorit(1200, 50, BOTO_FAVORIT);
        botoFavorit.setColors(colors);
        botoFavorit.setFonts(fonts);

        String[] titols = {"OBRA", "ESTADÍSTICA", "VISUALITZACIONS"};
        botonsGrup = new BotonsGrup( 300, 20, 850, ALT_BOTO);
        botonsGrup.setColors(colors);
        botonsGrup.setFonts(fonts);
        botonsGrup.setBotons(titols);

        entradaText = new EntradaText("NOM", 100, 100, AMPLE_ENTRADA_TEXT, ALT_ENTRADA_TEXT, colors, fonts);
        entradaText.setTextEtiqueta("NOM");
        entradaText.setColors(colors);
        entradaText.setFonts(fonts);

        menuApp = new MenuApp(10, 200, 250, 600);
        menuApp.setColors(colors);
        menuApp.setFonts(fonts);
        menuApp.setMenuApp();

        entradaCercador = new EntradaCercador("", p5.width - AMPLE_ENTRADA_CERCADOR -50, 20, AMPLE_ENTRADA_CERCADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercador.setColors(colors);
        entradaCercador.setFonts(fonts);

        resumAutor = new ResumAutor(300, 220, p5.width-350, 200);
        resumAutor.setColors(colors);
        resumAutor.setFonts(fonts);
        resumAutor.setResumLlibres(12, 1856, 2010);
        resumAutor.setResumPoemes(28, 12);
        resumAutor.setResumEstrofes(256, 12);
        resumAutor.setResumVersos(13873, 234);

    }


    public void dibuixaPantalla(){

        tarjaResum.display(p5);
        tarja.display(p5);
        taula.display(p5);
        boto.display(p5);
        desplegable.display(p5);
        graellaAutors.display(p5);
        graellaLlibres.display(p5);

        botoIcona.display(p5);

        botoFavorit.display(p5);
        botonsGrup.display(p5);



        entradaText.display(p5);


        menuApp.display(p5);
    }

    public void dibuixaPantallaInici(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        graellaAutors.display(p5);
        graellaLlibres.display(p5);
    }

    public void dibuixaPantallaAutor(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botonsGrup.display(p5);
        resumAutor.display(p5);
        taula.display(p5);
    }

    public void dibuixaGUI(){
        switch (pantallaActual){
            case INICI: dibuixaPantallaInici(); break;
            case AUTOR_INICI: dibuixaPantallaAutor(); break;
            default:
        }
    }


    public void mouseEvents(PApplet p5){

        desplegable.update(p5);

        if(boto.mouseDins(p5)){
            p5.println("CLICK BOTÓ");
        }

        graellaAutors.updateClick(p5);
        graellaLlibres.updateClick(p5);


        botoFavorit.clickFavorit(p5);

        botonsGrup.clickBotons(p5);

        entradaText.updateClick(p5);
        entradaCercador.updateClick(p5);

        taula.updateClick(p5);

        menuApp.updateClick(p5);

        if(menuApp.opcioSeleccionada[0]!=-1){
            System.out.println("OPCIÓ "+ menuApp.opcioSeleccionada[0]+", "+menuApp.opcioSeleccionada[1]);
        }


    }

    public void keyPressedEvent(PApplet p5){
        entradaText.updateKeyPressed(p5.keyCode);
        entradaCercador.updateKeyPressed(p5.keyCode);
    }

    public void keyTypedEvent(PApplet p5){
        entradaText.updateKeyTyped(p5.key);
        entradaCercador.updateKeyTyped(p5.key);
    }

}
