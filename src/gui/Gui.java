package gui;

import processing.core.PApplet;
import processing.core.PImage;

import static gui.Mides.*;

public class Gui {

    // PANTALLES
    public enum PANTALLA {  INICI, EXPLORAR, FAVORITS, LLIBRES, POEMES, AUTORS,
                            AUTOR_OBRA, AUTOR_RESUM, AUTOR_VISUAL, AUTOR_EDITA,
                            QUANTITATIVES, QUALITATIVES, CRONOLOGIQUES, RELACIONALS, TEMATIQUES, ALTRES,

                             LLIBRE_RESUM, LLIBRE_EDITA};
    public PANTALLA pantallaActual;

    // Titulars Pantalla
    String[][] titulars = {
            {"Poetica", "Inici", ""},{"Poetica", "Explorar", ""},{"Poetica", "Favorits", ""},{"Poetica", "Llibres", ""},{"Poetica", "Poemes", ""},{"Poetica", "Autors", ""},
            {"Autor", "Obra", ""},{"Autor", "Resum", ""},{"Autor", "Visualitzacions", ""},{"Autor", "Edita", ""},
            {"Visualitzacions", "Quantitatives", ""},{"Visualitzacions", "Qualitatives", ""},{"Visualitzacions", "Cronològiques", ""},{"Visualitzacions", "Relacionals", ""},{"Visualitzacions", "Temàtiques", ""},{"Visualitzacions", "Altres", ""},
            {"Llibre", "Resum", ""},{"Llibre", "Edita", ""},
    };

    int[] iconesTitulars ={CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT,
                            CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT,
            CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT,CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT,CODI_ANTERIOR, CODI_SEGUENT, CODI_ANTERIOR, CODI_SEGUENT,
                            CODI_ANTERIOR, CODI_SEGUENT};

    PApplet p5;


    // ELEMENTS GUI
    Titulars titularsPagina;

    TaulaPaginada taulaResumAutor, taulaResumLlibre, taulaResumApp;
    public BotoIcona botoEditarAutor, botoEditarLlibre, botoAfegirAutor, botoAfegirLlibre, botoFiltrar, botoGuardar;
    EntradaTextLlista llistaAutors, llistaLlibres, llistaPoemes;
    GraellaTarja graellaAutorsFavorits, graellaLlibresFavorits, graellaLlibres, graellaAutors, graellaPoemes;
    BotonsGrup botonsAutor;


    EntradaText entradaNomAutor, entradaAnyAutor, entradaTitolLlibre, entradaAnyLlibre;
    EntradaCercador entradaCercador, entradaCercadorGlobal;
    BotoOpcioGrup opcionsCercador;

    Resum resumAutor, resumLlibre, resumApp;

    MenuApp menuApp;

    // MEDIA
    Colors colors;
    Fonts fonts;
    PImage imgAutor, imgLlibre, imgPoema;
    public SelectorImatge selectorImatge;
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

        taulaResumAutor = new TaulaPaginada(300, 540, p5.width-350, 500);
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
        taulaResumAutor.setTitols(cols);
        taulaResumAutor.setDades(dades);
        taulaResumAutor.setMidaColumnes(mides);
        taulaResumAutor.setNumFilesPagina(10);
        taulaResumAutor.setColorsFonts(colors, fonts);
        taulaResumAutor.setBotonsPaginacio();

        taulaResumLlibre = new TaulaPaginada(300, 540, p5.width-350, 500);
        String[] colsTaulaLlibre = { "Poema", "Número", "Estrofes", "Versos", "Paraules", "Síl·labes"};
        String[][] dadesTaulaLlibre = {{"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
        };
        float[] midesTaulaLlibre = {50, 10, 10, 10, 10, 10};
        taulaResumLlibre.setTitols(colsTaulaLlibre);
        taulaResumLlibre.setDades(dadesTaulaLlibre);
        taulaResumLlibre.setMidaColumnes(midesTaulaLlibre);
        taulaResumLlibre.setNumFilesPagina(10);
        taulaResumLlibre.setColorsFonts(colors, fonts);
        taulaResumLlibre.setBotonsPaginacio();

        String[] opcionsAutors = {"Autor A", "Autor B", "Autor C", "Autor D"};
        llistaAutors = new EntradaTextLlista("Autor", opcionsAutors, 400, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaAutors.setColorsFonts(colors, fonts);

        String[] opcionsLlibres = {"Llibre A", "Llibre B", "Llibre C", "Llibre D"};
        llistaLlibres = new EntradaTextLlista("Llibre", opcionsLlibres, 800, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaLlibres.setColorsFonts(colors, fonts);

        String[] opcionsPoemes = {"Poema A", "Poema B", "Poema C", "Poema D"};
        llistaPoemes = new EntradaTextLlista("Poema", opcionsPoemes, 1200, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaPoemes.setColorsFonts(colors, fonts);

        // Graella Autors 1 linea
        String[][] dadesAutorsResum = { {"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };
        graellaAutorsFavorits = setGraella(dadesAutorsResum, "Autors", imgAutor, colorAutor, 1, 5, 310, 300, 1550, 292);
        graellaAutorsFavorits.amagaSubtitols();

        // Graella Llibres 1 línea
        String[][] dadesLlibresResum = { {"a", "d1"},{"f", "d1"},{"b", "d1"},{"g", "d1"},{"c", "d1"},{"k", "d1"},{"d", "d1"},{"v", "d1"},{"e", "d1"} };
        graellaLlibresFavorits = setGraella(dadesLlibresResum, "Llibres", imgLlibre, colorLlibre, 1, 5, 310, 730, 1550, 292);
        graellaLlibresFavorits.amagaSubtitols();

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

        botoEditarAutor = new BotoIcona("Autor", 0x1F600, p5.width - 250 -50, 150, 250, ALT_BOTO);
        botoEditarAutor.setColorsFonts(colors, fonts);

        botoEditarLlibre = new BotoIcona("Llibre", 0x1F600, p5.width - 250 -50, 150, 250, ALT_BOTO);
        botoEditarLlibre.setColorsFonts(colors, fonts);

        botoAfegirAutor = new BotoIcona("Autor", 0x1F600, p5.width - 500 -50 -25, 150, 250, ALT_BOTO);
        botoAfegirAutor.setColorsFonts(colors, fonts);

        botoAfegirLlibre = new BotoIcona("Llibre", 0x1F600, p5.width - 250 - 50, 150, 250, ALT_BOTO);
        botoAfegirLlibre.setColorsFonts(colors, fonts);

        botoFiltrar = new BotoIcona("Filtrar", 0x1F600, p5.width - 250  -50, 150, 250, ALT_BOTO);
        botoFiltrar.setColorsFonts(colors, fonts);

        botoGuardar = new BotoIcona("Guardar", 0x1F600, p5.width - 250  -50, 300, 250, ALT_BOTO);
        botoGuardar.setColorsFonts(colors, fonts);

        entradaNomAutor = new EntradaText("Nom", 1100, 300, AMPLE_ENTRADA_TEXT*2, ALT_ENTRADA_TEXT);
        entradaNomAutor.setColorsFonts(colors, fonts);

        entradaAnyAutor = new EntradaText("Any", 1100, 400, AMPLE_ENTRADA_TEXT/2, ALT_ENTRADA_TEXT);
        entradaAnyAutor.setColorsFonts(colors, fonts);

        selectorImatge = new SelectorImatge(400, 300, 500, 500);
        selectorImatge.setColorsFonts(colors, fonts);

        String[] opcionsAutor = {"RESUM", "OBRA", "VISUALITZACIONS"};
        botonsAutor = new BotonsGrup( 600, 20, 850, ALT_BOTO);
        botonsAutor.setColorsFonts(colors, fonts);
        botonsAutor.setBotons(opcionsAutor);


        menuApp = new MenuApp(10, 200, 250, 840);
        menuApp.setColorsFonts(colors, fonts);
        menuApp.setMenuApp();

        // EXPLORADOR ////////////////////////////////////////////////////////////////////////////

        entradaCercador = new EntradaCercador("", p5.width - AMPLE_ENTRADA_CERCADOR -50, 20, AMPLE_ENTRADA_CERCADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercador.setColors(colors);
        entradaCercador.setFonts(fonts);

        entradaCercadorGlobal = new EntradaCercador("Cerca", 400, 225, AMPLE_ENTRADA_EXPLORADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercadorGlobal.setColors(colors);
        entradaCercadorGlobal.setFonts(fonts);


        BotoOpcio opcioTerme = new BotoOpcio("Terme", 400, 150, 50, 50);
        opcioTerme.setColorsFonts(colors, fonts);

        BotoOpcio opcioLema = new BotoOpcio("Lema", 500, 300, 50, 50);
        opcioLema.setColorsFonts(colors, fonts);

        opcionsCercador = new BotoOpcioGrup(2);
        opcionsCercador.setSeleccionables(opcioTerme, opcioLema);



        resumAutor = new ResumAutor(300, 260, p5.width-350, 200);
        resumAutor.setColorsFonts(colors, fonts);
        ((ResumAutor)resumAutor).setResumLlibres(12, 1856, 2010);
        ((ResumAutor)resumAutor).setResumPoemes(28, 12);
        ((ResumAutor)resumAutor).setResumEstrofes(256, 12);
        ((ResumAutor)resumAutor).setResumVersos(13873, 234);

        resumLlibre = new ResumLlibre(300, 260, p5.width-350, 200);
        resumLlibre.setColorsFonts(colors, fonts);
        ((ResumLlibre)resumLlibre).setResumAutor("Nom de l'autor", 1856, 2010);
        ((ResumLlibre)resumLlibre).setResumPoemes(28, 12);
        ((ResumLlibre)resumLlibre).setResumEstrofes(256, 12);
        ((ResumLlibre)resumLlibre).setResumVersos(13873, 234);

        resumApp = new ResumApp(300, 260, p5.width-350, 200);
        resumApp.setColorsFonts(colors, fonts);
        ((ResumApp)resumApp).setResumAutors(2, 1988, 2010);
        ((ResumApp)resumApp).setResumLlibres(12, 8.9f);
        ((ResumApp)resumApp).setResumPoemes(28, 12);
        ((ResumApp)resumApp).setResumEstrofes(256, 12);
        ((ResumApp)resumApp).setResumVersos(13873, 234);

        titularsPagina = new Titulars(360, 100);
        titularsPagina.setTitulars("Autor", "1965");
        titularsPagina.setCodiIcona(CODI_NO_FAVORIT);
        titularsPagina.setColorsFonts(colors, fonts);

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


    public void dibuixaPantallaInici(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        menuApp.display(p5);
        entradaCercador.display(p5);

        titularsPagina.display(p5);
        resumApp.display(p5);

        botoAfegirAutor.display(p5);
        botoAfegirLlibre.display(p5);

    }

    public void dibuixaPantallaExplorar(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);

        entradaCercadorGlobal.display(p5);
        opcionsCercador.display(p5);
        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);

    }

    public void dibuixaPantallaFavorits(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        graellaAutorsFavorits.display(p5);
        graellaLlibresFavorits.display(p5);

    }

    public void dibuixaPantallaAutors(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);

        graellaAutors.display(p5);

    }

    public void dibuixaPantallaLlibres(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);

        graellaLlibres.display(p5);
    }

    public void dibuixaPantallaPoemes(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);

        graellaPoemes.display(p5);

    }

    public void dibuixaPantallaAutorObra(){

        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);
        botonsAutor.display(p5);
        titularsPagina.display(p5);


        botoAfegirLlibre.display(p5);

        graellaLlibres.display(p5);
    }

    public void dibuixaPantallaAutorResum(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botoEditarAutor.display(p5);
        botonsAutor.display(p5);
        resumAutor.display(p5);
        taulaResumAutor.display(p5);
        titularsPagina.display(p5);
    }

    public void dibuixaPantallaAutorVisuals(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botonsAutor.display(p5);
        titularsPagina.display(p5);

        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);
    }

    public void dibuixaPantallaAutorEdita(){
        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        entradaNomAutor.display(p5);
        entradaAnyAutor.display(p5);
        selectorImatge.display(p5);
        botoGuardar.display(p5);
        botonsAutor.display(p5);
    }

    public void dibuixaPantallaLlibreResum(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 600, 100);

        titularsPagina.display(p5);
        menuApp.display(p5);
        entradaCercador.display(p5);

        botoEditarLlibre.display(p5);
        resumLlibre.display(p5);
        taulaResumLlibre.display(p5);
        titularsPagina.display(p5);
    }

    public void dibuixaPantallaQuantitatives(){
        p5.fill(0);
        p5.text(pantallaActual.toString(), 300, 100);
        menuApp.display(p5);
        entradaCercador.display(p5);

        llistaAutors.display(p5);
        llistaLlibres.display(p5);
        llistaPoemes.display(p5);
        botoFiltrar.display(p5);
    }

    public void dibuixaGUI(){
        switch (pantallaActual){
            // MENU 1: INICI, EXPLORAR, FAVORITS
            case INICI: dibuixaPantallaInici(); break;
            case EXPLORAR: dibuixaPantallaExplorar(); break;
            case FAVORITS: dibuixaPantallaFavorits(); break;
            // MENU 2: AUTORS, LLIBRES, POEMES
            case AUTORS: dibuixaPantallaAutors(); break;
            case LLIBRES: dibuixaPantallaLlibres(); break;
            case POEMES: dibuixaPantallaPoemes(); break;
            // MENU 3: VISUALITZACIONS
            case QUANTITATIVES: dibuixaPantallaQuantitatives(); break;
            case QUALITATIVES: dibuixaPantallaQuantitatives(); break;
            case RELACIONALS: dibuixaPantallaQuantitatives(); break;
            case CRONOLOGIQUES: dibuixaPantallaQuantitatives(); break;
            case ALTRES: dibuixaPantallaQuantitatives(); break;
            case TEMATIQUES: dibuixaPantallaQuantitatives(); break;
            // AUTOR
            case AUTOR_OBRA: dibuixaPantallaAutorObra(); break;
            case AUTOR_RESUM: dibuixaPantallaAutorResum(); break;
            case AUTOR_VISUAL: dibuixaPantallaAutorVisuals(); break;
            case AUTOR_EDITA: dibuixaPantallaAutorEdita(); break;
            // LLIBRE
            case LLIBRE_EDITA: dibuixaPantallaLlibreResum(); break;
            case LLIBRE_RESUM: dibuixaPantallaLlibreResum(); break;
            default: dibuixaPantallaExplorar();
        }
    }

    public void updatePantallesAutor(PApplet p5){
        botonsAutor.clickBotons(p5);
        if(botonsAutor.botoSeleccionat!=-1) {
            if (botonsAutor.getTextBotoActivat().equals("OBRA")) {
                pantallaActual = PANTALLA.AUTOR_OBRA;
            } else if (botonsAutor.getTextBotoActivat().equals("RESUM")) {
                pantallaActual = PANTALLA.AUTOR_RESUM;
            } else if (botonsAutor.getTextBotoActivat().equals("VISUALITZACIONS")) {
                pantallaActual = PANTALLA.AUTOR_VISUAL;
            }
            updateTitular(pantallaActual);
        }
    }

    public void updateTitular(PANTALLA pantallaActual){
        int n = pantallaActual.ordinal();
        titularsPagina.setTitulars(titulars[n]);
        titularsPagina.setCodiIcona(iconesTitulars[n]);
    }


    public void updatePantallaMenu(PApplet p5){
        menuApp.updateClick(p5);
        if(menuApp.mouseDins(p5) && menuApp.opcioSeleccionada[0]!=-1){
            //System.out.println("OPCIÓ "+ menuApp.opcioSeleccionada[0]+", "+menuApp.opcioSeleccionada[1]);
            //System.out.println(menuApp.getTitolOpcioSeleccionada());
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
            updateTitular(pantallaActual);
        }
    }


    public void mouseEvents(PApplet p5){

        if(pantallaActual == PANTALLA.FAVORITS){
            graellaAutorsFavorits.updateClick(p5);
            if(graellaAutorsFavorits.numTarjaSeleccionada!=-1){
                pantallaActual = PANTALLA.AUTOR_RESUM;
            }
            graellaLlibresFavorits.updateClick(p5);
            if(graellaLlibresFavorits.numTarjaSeleccionada!=-1){
                pantallaActual = PANTALLA.LLIBRE_RESUM;
            }
        }
        else if(pantallaActual == PANTALLA.AUTOR_OBRA){
            updatePantallesAutor(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_RESUM){
            updatePantallesAutor(p5);
            taulaResumAutor.updateClick(p5);
            if(botoEditarAutor.mouseDins(p5)){
                pantallaActual = PANTALLA.AUTOR_EDITA;
                botonsAutor.desactivaTotesOpcions();
            }

        }
        else if(pantallaActual == PANTALLA.AUTOR_VISUAL){
            updatePantallesAutor(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_EDITA){
            updatePantallesAutor(p5);
            entradaNomAutor.updateClick(p5);
            entradaAnyAutor.updateClick(p5);
            if(botoGuardar.mouseDins(p5)){
                pantallaActual = PANTALLA.AUTOR_RESUM;
                botonsAutor.setBotoSeleccionat(0);
                updatePantallesAutor(p5);
            }
        }
        else if(pantallaActual == PANTALLA.LLIBRE_EDITA){


        }
        else if(pantallaActual == PANTALLA.AUTORS){

            graellaAutors.updateClick(p5);
            if(graellaAutors.numTarjaSeleccionada!=-1){
                pantallaActual = PANTALLA.AUTOR_RESUM;
            }
        }
        else if(pantallaActual == PANTALLA.LLIBRES){
            llistaAutors.updateClick(p5);
            llistaLlibres.updateClick(p5);

            graellaLlibres.updateClick(p5);
            if(graellaLlibres.numTarjaSeleccionada!=-1){
                pantallaActual = PANTALLA.LLIBRE_RESUM;
            }
        }

        // Elements comuns en totes les pantalles
        entradaCercador.updateClick(p5);
        updatePantallaMenu(p5);
        updateTitular(pantallaActual);

    }

    public void keyPressedEvent(PApplet p5){

        pantallaActual = entradaCercador.updateKeyPressed(p5.keyCode, pantallaActual, entradaCercadorGlobal);

        if(pantallaActual == PANTALLA.LLIBRES){
            llistaAutors.keyPressed(p5);
            llistaLlibres.keyPressed(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_EDITA){
            entradaNomAutor.updateKeyPressed(p5.keyCode);
            entradaAnyAutor.updateKeyPressed(p5.keyCode);
        }
    }

    public void keyTypedEvent(PApplet p5){

        entradaCercador.updateKeyTyped(p5.key);

        if(pantallaActual == PANTALLA.LLIBRES){
            llistaAutors.keyTyped(p5);
            llistaLlibres.keyTyped(p5);
        }
        else if(pantallaActual == PANTALLA.AUTOR_EDITA){
            entradaNomAutor.updateKeyTyped(p5.key);
            entradaAnyAutor.updateKeyTyped(p5.key);
        }
    }

}
