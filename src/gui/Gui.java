package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class Gui {

    // PANTALLES
    public enum PANTALLA {INICI, EXPLORAR, FAVORITS, LLIBRES, POEMES, AUTORS,
                            QUANTITATIVES, QUALITATIVES, CRONOLOGIQUES, RELACIONALS, TEMATIQUES, ALTRES,
                            AUTOR_OBRA, AUTOR_ESTADISTICA, AUTOR_VISUAL, AUTOR_EDITA,
                            LLIBRE_INFO, LLIBRE_EDITA};
    public PANTALLA pantallaActual;
    PApplet p5;


    // ELEMENTS GUI
    Titulars titulars;
    TarjaResum tarjaResum;
    Tarja tarja;
    TaulaPaginada taula;
    Boto boto;
    BotoIcona botoEditar, botoAfegir;
    BotoFavorit botoFavorit;
    Desplegable desplegableAutor, desplegableLlibre, desplegablePoema;
    GraellaTarja graellaAutorsResum, graellaLlibresResum, graellaLlibres, graellaAutors, graellaPoemes;
    BotonsGrup botonsGrup;
    EntradaText entradaText;
    EntradaCercador entradaCercador;

    ResumAutor resumAutor;

    MenuApp menuApp;

    // MEDIA
    Colors colors;
    Fonts fonts;
    PImage imgAutor, imgLlibre, imgPoema;
    int colorAutor, colorLlibre, colorPoema;

    // Hooks
    String autorSeleccionat;


    public Gui(PApplet p5){
        this.p5 = p5;
        this.pantallaActual = PANTALLA.INICI;
        setMedia(p5);
        setElementsGUI();
    }

    public void setMedia(PApplet p5){
        this.colors = new Colors(p5);
        this.fonts = new Fonts(p5);

        this.imgAutor = p5.loadImage("data/img/autor.png");
        imgLlibre = p5.loadImage("data/img/llibre.png");
        imgPoema = p5.loadImage("data/img/poema.png");

        colorAutor = p5.color(250, 220, 200);
        colorLlibre = p5.color(200, 250, 150);
        colorPoema = p5.color(150, 200, 250);
    }


    public void setElementsGUI(){

        tarja = new Tarja(50, 100, 300, 300);
        tarja.setTextos("titol", "subtitol");
        tarja.setImatge(p5, p5.loadImage("data/img/foto.jpg"), p5.color(100, 100, 100));
        tarja.setColorsFonts(colors, fonts);

        tarjaResum = new TarjaResum(400, 100, 300, 300);
        tarjaResum.setTextos("aaa", "bb", "cc");
        tarjaResum.setColorsFonts(colors, fonts);

        taula = new TaulaPaginada(300, 540, p5.width-350, 500);
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
        taula.setColorsFonts(colors, fonts);
        taula.setBotonsPaginacio();

        taula.paginaSeguent();

        boto = new Boto("OK", 50, 50, AMPLE_BOTO, ALT_BOTO);
        boto.setColors(colors);
        boto.setFonts(fonts);

        String[] opcionsAutors = {"Autor A", "Autor B", "Autor C", "Autor D"};
        desplegableAutor = new Desplegable(opcionsAutors, 200, 25, AMPLE_DESPLEGABLE, ALT_DESPLEGABLE);
        desplegableAutor.setColorsFonts(colors, fonts);

        String[] opcionsLlibres = {"Llibre A", "Llibre B", "Llibre C", "Llibre D"};
        desplegableLlibre = new Desplegable(opcionsLlibres, 200, 25, AMPLE_DESPLEGABLE, ALT_DESPLEGABLE);
        desplegableLlibre.setColorsFonts(colors, fonts);

        // Graella Autors 1 linea
        String[][] dadesAutorsResum = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaAutorsResum = setGraella(dadesAutorsResum, "Autors", imgAutor, colorAutor, 1, 5, 310, 300, 1550, 292);
        graellaAutorsResum.amagaSubtitols();

        // Graella Llibres 1 línea
        String[][] dadesLlibresResum = { {"a", "d1"},{"f", "d1"},{"b", "d1"},{"g", "d1"},{"c", "d1"},{"k", "d1"},{"d", "d1"},{"v", "d1"},{"e", "d1"} };
        graellaLlibresResum = setGraella(dadesLlibresResum, "Llibres", imgLlibre, colorLlibre, 1, 5, 310, 730, 1550, 292);
        graellaLlibresResum.amagaSubtitols();

        // Graella Autors 2 lineas
        String[][] dadesAutors = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaAutors = setGraella(dadesAutors, "Autors",imgAutor,colorAutor, 2, 5, 310, 300, 1550, 600);

        // Graella Llibres 2 líneas
        String[][] dadesLlibres= { {"a", "d1"},{"f", "d1"},{"b", "d1"},{"g", "d1"},{"c", "d1"},{"k", "d1"},{"d", "d1"},{"v", "d1"},{"e", "d1"} };
        graellaLlibres = setGraella(dadesLlibres, "Llibres",imgLlibre,colorLlibre, 2, 5, 310, 300, 1550, 600);

        // Graella Poemes 2 líneas
        String[][] dadesPoemes= { {"a", "d1"},{"f", "d1"},{"b", "d1"},{"g", "d1"},{"c", "d1"},{"k", "d1"},{"d", "d1"},{"v", "d1"},{"e", "d1"} };
        graellaPoemes = setGraella(dadesPoemes, "Poemes",imgPoema,colorPoema, 2, 5, 310, 300, 1550, 600);

        //graellaTarja.paginaSeguent();

        botoEditar = new BotoIcona("", 0x1F600, p5.width - 200 -25 -50, 150, 100, ALT_BOTO);
        botoEditar.setColorsFonts(colors, fonts);

        botoAfegir = new BotoIcona("", 0x1F600, p5.width - 100 -50, 150, 100, ALT_BOTO);
        botoAfegir.setColorsFonts(colors, fonts);

        botoFavorit = new BotoFavorit(1200, 50, BOTO_FAVORIT);
        botoFavorit.setColorsFonts(colors, fonts);

        String[] titols = {"OBRA", "ESTADÍSTICA", "VISUALITZACIONS"};
        botonsGrup = new BotonsGrup( 300, 20, 850, ALT_BOTO);
        botonsGrup.setColorsFonts(colors, fonts);
        botonsGrup.setBotons(titols);

        entradaText = new EntradaText("NOM", 100, 100, AMPLE_ENTRADA_TEXT, ALT_ENTRADA_TEXT, colors, fonts);
        entradaText.setTextEtiqueta("NOM");
        entradaText.setColorsFonts(colors, fonts);

        menuApp = new MenuApp(10, 200, 250, 840);
        menuApp.setColorsFonts(colors, fonts);
        menuApp.setMenuApp();

        entradaCercador = new EntradaCercador("", p5.width - AMPLE_ENTRADA_CERCADOR -50, 20, AMPLE_ENTRADA_CERCADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercador.setColors(colors);
        entradaCercador.setFonts(fonts);

        resumAutor = new ResumAutor(300, 260, p5.width-350, 200);
        resumAutor.setColorsFonts(colors, fonts);
        resumAutor.setResumLlibres(12, 1856, 2010);
        resumAutor.setResumPoemes(28, 12);
        resumAutor.setResumEstrofes(256, 12);
        resumAutor.setResumVersos(13873, 234);

        titulars = new Titulars(300, 200);
        titulars.setTitulars("Autor", "1965");
        titulars.setColorsFonts(colors, fonts);

    }

    public GraellaTarja setGraella(String[][] dades, String titol, PImage img, int colorFons, int numFiles, int numColumnes, float x, float y, float w, float h){
        GraellaTarja graella = new GraellaTarja(numFiles, numColumnes, x, y, w, h);
        graella.setData(dades);
        graella.ordenaTargesPerTitolAsc();
        graella.setTitol(titol);
        graella.setColorsFonts(colors, fonts);
        graella.setTarges(p5);
        graella.setImatges(p5, img, colorFons);
        graella.setBotons(p5);
        graella.setSelecccionableOrdre(p5);
        return graella;
    }


    public void dibuixaPantalla(){

        tarjaResum.display(p5);
        tarja.display(p5);
        taula.display(p5);
        boto.display(p5);
        desplegableAutor.display(p5);

        graellaAutorsResum.display(p5);
        graellaLlibresResum.display(p5);

        botoEditar.display(p5);

        botoFavorit.display(p5);
        botonsGrup.display(p5);



        entradaText.display(p5);


        menuApp.display(p5);
    }


    public void dibuixaPantallaInici(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        menuApp.display(p5);
        entradaCercador.display(p5);

        titulars.display(p5);

        graellaAutorsResum.display(p5);
        graellaLlibresResum.display(p5);
    }

    public void dibuixaPantallaExplorar(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

    }

    public void dibuixaPantallaFavorits(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

    }

    public void dibuixaPantallaAutors(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        graellaAutors.display(p5);

    }

    public void dibuixaPantallaLlibres(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        graellaLlibres.display(p5);

    }

    public void dibuixaPantallaPoemes(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        graellaPoemes.display(p5);

    }

    public void dibuixaPantallaAutorObra(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);
        botonsGrup.display(p5);
        titulars.display(p5);

        botoEditar.display(p5);
        botoAfegir.display(p5);

        graellaLlibres.display(p5);
    }

    public void dibuixaPantallaAutorEstadistica(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botonsGrup.display(p5);
        resumAutor.display(p5);
        taula.display(p5);
        titulars.display(p5);
    }

    public void dibuixaPantallaAutorVisuals(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botonsGrup.display(p5);
        titulars.display(p5);
    }

    public void dibuixaGUI(){
        switch (pantallaActual){
            case INICI: dibuixaPantallaInici(); break;
            case EXPLORAR: dibuixaPantallaExplorar(); break;
            case FAVORITS: dibuixaPantallaFavorits(); break;
            case AUTORS: dibuixaPantallaAutors(); break;
            case LLIBRES: dibuixaPantallaLlibres(); break;
            case POEMES: dibuixaPantallaPoemes(); break;
            case AUTOR_OBRA: dibuixaPantallaAutorObra(); break;
            case AUTOR_ESTADISTICA: dibuixaPantallaAutorEstadistica(); break;
            case AUTOR_VISUAL: dibuixaPantallaAutorVisuals(); break;
            default: dibuixaPantallaExplorar();
        }
    }

    public void updatePantallesAutor(PApplet p5){
        botonsGrup.clickBotons(p5);
        if(botonsGrup.getTextBotoActivat().equals("OBRA")){
            pantallaActual = PANTALLA.AUTOR_OBRA;
        }
        else if(botonsGrup.getTextBotoActivat().equals("ESTADÍSTICA")){
            pantallaActual = PANTALLA.AUTOR_ESTADISTICA;
        }
        else if(botonsGrup.getTextBotoActivat().equals("VISUALITZACIONS")){
            pantallaActual = PANTALLA.AUTOR_VISUAL;
        }
    }

    public void updatePantallaMenu(PApplet p5){
        menuApp.updateClick(p5);
        if(menuApp.mouseDins(p5) && menuApp.opcioSeleccionada[0]!=-1){
            System.out.println("OPCIÓ "+ menuApp.opcioSeleccionada[0]+", "+menuApp.opcioSeleccionada[1]);
            System.out.println(menuApp.getTitolOpcioSeleccionada());
            switch(menuApp.getTitolOpcioSeleccionada()){
                case "Inici":       pantallaActual = PANTALLA.INICI; break;
                case "Explorar":    pantallaActual = PANTALLA.EXPLORAR; break;
                case "Favorits":    pantallaActual = PANTALLA.FAVORITS; break;
                case "Llibres":     pantallaActual = PANTALLA.LLIBRES; break;
                case "Poemes":      pantallaActual = PANTALLA.POEMES; break;
                case "Autors":      pantallaActual = PANTALLA.AUTORS; break;
                case "Quantitatives": pantallaActual = PANTALLA.QUANTITATIVES; break;
                case "Qualitatives":    pantallaActual = PANTALLA.QUALITATIVES; break;
                case "Relacionals":     pantallaActual = PANTALLA.RELACIONALS; break;
                case "Temàtiques":      pantallaActual = PANTALLA.TEMATIQUES; break;
                case "Cronològiques":   pantallaActual = PANTALLA.CRONOLOGIQUES; break;
                case "Altres":          pantallaActual = PANTALLA.ALTRES; break;
                default:
            }
        }
    }


    public void mouseEvents(PApplet p5){

        if(pantallaActual == PANTALLA.INICI){
            graellaAutorsResum.updateClick(p5);
            if(graellaAutorsResum.numTarjaSeleccionada!=-1){
                pantallaActual = PANTALLA.AUTOR_OBRA;
            }
            graellaLlibresResum.updateClick(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_OBRA){
            updatePantallesAutor(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_ESTADISTICA){
            updatePantallesAutor(p5);
            taula.updateClick(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_VISUAL){
            updatePantallesAutor(p5);
        }

        desplegableAutor.update(p5);

        if(boto.mouseDins(p5)){
            p5.println("CLICK BOTÓ");
        }

        // Elements comuns en totes les pantalles
        entradaCercador.updateClick(p5);
        updatePantallaMenu(p5);

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
