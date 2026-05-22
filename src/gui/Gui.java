package gui;

import parser.Autor;
import parser.DadesPoemaris;
import parser.Poema;
import parser.Poemari;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import static gui.Mides.*;
import static java.lang.Math.min;
import static parser.DadesPoemaris.*;

public class Gui {

    // PANTALLES
    public enum PANTALLA { INICI, EXPLORAR, FAVORITS, LLIBRES, POEMES, AUTORS,
        AUTOR_OBRA, AUTOR_RESUM, AUTOR_VISUAL, AUTOR_EDITA,
        QUANTITATIVES, QUALITATIVES, CRONOLOGIQUES, RELACIONALS, TEMATIQUES, ALTRES,
        LLIBRE_RESUM, LLIBRE_OBRA, LLIBRE_VISUAL, LLIBRE_EDITA,
        POEMA_RESUM, POEMA_EDITA
    };

    String[][] dadesVisuals = { {"Quant 01", "Quantitativa"},{"Quant 02", "Quantitativa"},{"Rela 03", "Relacional"},{"Tema 04", "Temàtica"},{"c", "Qualitativa"},{"Quant 03", "Quantitativa"},{"d", "d1"},{"v", "d1"},{"e", "d1"},{"t1", "d1"},{"t1", "d1"},{"t1", "d1"} };

    public Pantalla currentPantalla;
    PApplet p5;

    // ELEMENTS GUI
    int posTitularX = 380, posTitularY = 100;

    TaulaPaginada taulaAutors, taulaResumAutor, taulaResumLlibre, taulaLlibres, taulaResumPoema;
    public BotoIcona botoEditarAutor, botoEditarLlibre, botoEditarPoema, botoAfegirAutor, botoAfegirLlibre, botoAfegirPoema, botoFiltrar, botoGuardar;
    EntradaTextLlista llistaAutors, llistaAutorLlibre, llistaLlibres, llistaPoemes;
    GraellaTarja graellaAutorsFavorits, graellaLlibresFavorits, graellaLlibres, graellaAutors, graellaPoemes;
    GraellaTarja graellaVisualitzacions;
    BotonsGrup botonsAutor, botonsLlibre;

    EntradaText entradaNomAutor, entradaAnyAutor, entradaTitolLlibre, entradaAnyLlibre;
    EntradaCercador entradaCercador, entradaCercadorGlobal;
    GraellaTarjaCerca gtc;
    BotoOpcioGrup opcionsCercador;

    Resum resumAutor, resumLlibre, resumPoema, resumApp;
    Titol titolAutor, titolLlibre, titolPoema;

    MenuApp menuApp;

    Pantalla pantallaInici, pantallaExplorar, pantallaFavorits, pantallaAutors, pantallaLlibres, pantallaPoemes;
    public Pantalla pantallaAutorResum, pantallaAutorObra, pantallaAutorVisual, pantallaAutorEdita;
    public Pantalla pantallaLlibreResum, pantallaLlibreObra, pantallaLlibreVisual, pantallaLlibreEdita;
    Pantalla pantallaPoemaResum, pantallaPoemaEdita;
    Pantalla pantallaQuantitatives, pantallaQualitatives, pantallaRelacionals, pantallaTematiques, pantallaCronologiques, pantallaAltres;

    // MEDIA
    Colors colors;
    Fonts fonts;
    PImage imgAutor, imgLlibre, imgPoema, imgVisuals, imgLogo;
    public SelectorImatge selectorImatgeAutor, selectorImatgeLlibre;
    int colorAutor, colorLlibre, colorPoema, colorVisuals;

    // Hooks
    ArrayList<Autor> autors;
    ArrayList<Poemari> poemaris;
    Autor autorSeleccionat;
    Poemari poemariSeleccionat;
    Poema poemaSeleccionat;

    public Gui(PApplet p5, ArrayList<Autor> autors){
        this.p5 = p5;
        this.autors = autors;
        this.poemaris = autors.get(0).getPoemaris();
        this.autorSeleccionat = autors.get(0);
        this.poemariSeleccionat = this.poemaris.get(0);
        this.poemaSeleccionat = this.poemariSeleccionat.getPoemaAt(0);
        setMedia(p5);
        setElementsGUI();
        setPantalles();
        this.currentPantalla = pantallaInici;

    }

    public void setMedia(PApplet p5){
        this.colors = new Colors(p5);
        this.fonts = new Fonts(p5);

        this.imgAutor = p5.loadImage("data/img/imgAutor.png");
        this.imgLlibre = p5.loadImage("data/img/llibre.png");
        this.imgPoema = p5.loadImage("data/img/poema.png");
        this.imgVisuals = p5.loadImage("data/img/poema.png");
        this.imgLogo = p5.loadImage("data/img/logoPoetica.png");

        colorAutor = p5.color(255, 255, 200);
        colorLlibre = p5.color(200, 250, 150);
        colorPoema = p5.color(150, 200, 250);
        colorVisuals= p5.color(150, 150, 250);
    }


    public void setElementsGUI(){

        taulaAutors = new TaulaPaginada(300, 540, 600, 500);
        String[] colsAutors1 = { "Autor", "Any"};
        float[] midesAutors1 = {75, 25};
        taulaAutors.setTitols(colsAutors1);
        taulaAutors.setNumFilesPagina(10);
        taulaAutors.setDades(DadesPoemaris.getAutorsInfo(autors));
        taulaAutors.setMidaColumnes(midesAutors1);
        taulaAutors.setColorsFonts(colors, fonts);
        taulaAutors.setBotonsPaginacio();

        taulaResumAutor = new TaulaPaginada(300, 540, p5.width-350, 500);
        String[] cols = { "Títol", "Any", "Poemes", "Estrofes", "Versos", "Paraules", "Síl·labes"};
        float[] mides = {40, 10, 10, 10, 10, 10, 10};
        taulaResumAutor.setTitols(cols);
        taulaResumAutor.setNumFilesPagina(10);
        taulaResumAutor.setDades(DadesPoemaris.getAutorInfo(autorSeleccionat));
        taulaResumAutor.setMidaColumnes(mides);
        taulaResumAutor.setColorsFonts(colors, fonts);
        taulaResumAutor.setBotonsPaginacio();

        taulaLlibres = new TaulaPaginada(950, 540, 920, 500);
        String[] colsTaulaLlibre1 = { "Llibre", "Any", "Poemes", "Autor"};
        float[] midesTaulaLlibre1 = {40, 10, 20, 30};
        taulaLlibres.setTitols(colsTaulaLlibre1);
        taulaLlibres.setNumFilesPagina(10);
        taulaLlibres.setDades(DadesPoemaris.getLlibresInfo(poemaris));
        taulaLlibres.setMidaColumnes(midesTaulaLlibre1);
        taulaLlibres.setColorsFonts(colors, fonts);
        taulaLlibres.setBotonsPaginacio();

        taulaResumLlibre = new TaulaPaginada(300, 540, p5.width-350, 500);
        String[] colsTaulaLlibre = { "Poema", "Número", "Estrofes", "Versos", "Paraules", "Síl·labes"};
        float[] midesTaulaLlibre = {50, 10, 10, 10, 10, 10};
        taulaResumLlibre.setTitols(colsTaulaLlibre);
        taulaResumLlibre.setNumFilesPagina(10);
        taulaResumLlibre.setDades(getLlibreInfo(poemariSeleccionat));
        taulaResumLlibre.setMidaColumnes(midesTaulaLlibre);
        taulaResumLlibre.setColorsFonts(colors, fonts);
        taulaResumLlibre.setBotonsPaginacio();

        taulaResumPoema = new TaulaPaginada(300, 540, p5.width-350, 500);
        String[] colsTaulaPoema = { "Estrofa", "Número", "Versos", "Paraules", "Síl·labes"};
        float[] midesTaulaPoema = {60, 10, 10, 10, 10};
        taulaResumPoema.setTitols(colsTaulaPoema);
        taulaResumPoema.setNumFilesPagina(10);
        taulaResumPoema.setDades(getPoemaInfo(poemaSeleccionat));
        taulaResumPoema.setMidaColumnes(midesTaulaPoema);
        taulaResumPoema.setColorsFonts(colors, fonts);
        taulaResumPoema.setBotonsPaginacio();

        // LLISTES AUTOCOMPLETAR /////////////////////////////////////////////////////////////////////////////

        llistaAutors = new EntradaTextLlista("Autor", getNomsAutors(autors), 400, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaAutors.setColorsFonts(colors, fonts);

        llistaAutorLlibre = new EntradaTextLlista("Autor", getNomsAutors(autors), 1100, 300, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaAutorLlibre.setColorsFonts(colors, fonts);

        llistaLlibres = new EntradaTextLlista("Llibre", getTitolsPoemaris(autorSeleccionat), 800, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaLlibres.setColorsFonts(colors, fonts);

        llistaPoemes = new EntradaTextLlista("Poema", getTitolsPoemes(poemariSeleccionat), 1200, 150, AMPLE_ENTRADA_LLISTA, ALT_ENTRADA_LLISTA);
        llistaPoemes.setColorsFonts(colors, fonts);


        // GRAELLES ///////////////////////////////////////////////////////////////////////////////////////////

        // Graella Autors 1 linea
        graellaAutorsFavorits = setGraella(getAutorsInfo(autors), "Autors", imgAutor, colorAutor, 1, 5, 310, 300, 1550, 292);
        graellaAutorsFavorits.amagaSubtitols();

        // Graella Llibres 1 línea
        graellaLlibresFavorits = setGraella(getLlibresAutorsInfo(autors), "Llibres", imgLlibre, colorLlibre, 1, 5, 310, 730, 1550, 292);
        graellaLlibresFavorits.amagaSubtitols();

        // Graella Autors 2 lineas
        graellaAutors = setGraella(getAutorsInfo(autors), "Autors",imgAutor,colorAutor, 2, 5, 310, 300, 1550, 600);

        // Graella Llibres 2 líneas
        graellaLlibres = setGraella(getLlibresAutorsInfo(autors), "Llibres",imgLlibre,colorLlibre, 2, 5, 310, 300, 1550, 600);

        // Graella Poemes 2 líneas
        graellaPoemes = setGraella(getPoemesAutorsInfo(autors), "Poemes",imgPoema,colorPoema, 2, 5, 310, 300, 1550, 600);

        // Graella Visuals 2 líneas
        graellaVisualitzacions = setGraellaVisuals(dadesVisuals, "Quantitativa", "Visuals Quantitatius", imgVisuals, colorVisuals, 2, 5, 310, 300, 1550, 600);

        botoEditarAutor = new BotoIcona("Autor", CODI_AUTORS, p5.width - 250 -50, 150, 250, ALT_BOTO);
        botoEditarAutor.setColorsFonts(colors, fonts);

        botoEditarLlibre = new BotoIcona("Llibre", CODI_LLIBRES, p5.width - 250 -50, 150, 250, ALT_BOTO);
        botoEditarLlibre.setColorsFonts(colors, fonts);

        botoEditarPoema = new BotoIcona("Poema", CODI_POEMES, p5.width - 250 -50, 150, 250, ALT_BOTO);
        botoEditarPoema.setColorsFonts(colors, fonts);

        botoAfegirAutor = new BotoIcona("Autor", CODI_AUTORS, p5.width - 500 -50 -25, 150, 250, ALT_BOTO);
        botoAfegirAutor.setColorsFonts(colors, fonts);

        botoAfegirLlibre = new BotoIcona("Llibre", CODI_LLIBRES, p5.width - 250 - 50, 150, 250, ALT_BOTO);
        botoAfegirLlibre.setColorsFonts(colors, fonts);

        botoAfegirPoema = new BotoIcona("Poema", CODI_POEMES, p5.width - 250 - 50, 150, 250, ALT_BOTO);
        botoAfegirPoema.setColorsFonts(colors, fonts);

        botoFiltrar = new BotoIcona("Filtrar", CODI_EXPLORAR, p5.width - 250  -50, 150, 250, ALT_BOTO);
        botoFiltrar.setColorsFonts(colors, fonts);

        botoGuardar = new BotoIcona("Guardar", 0x1F600, p5.width - 250  -50, 300, 250, ALT_BOTO);
        botoGuardar.setColorsFonts(colors, fonts);

        entradaNomAutor = new EntradaText("Nom", 1100, 300, AMPLE_ENTRADA_TEXT*2, ALT_ENTRADA_TEXT);
        entradaNomAutor.setColorsFonts(colors, fonts);

        entradaTitolLlibre = new EntradaText("Títol", 1100, 400, AMPLE_ENTRADA_TEXT*2, ALT_ENTRADA_TEXT);
        entradaTitolLlibre.setColorsFonts(colors, fonts);

        entradaAnyAutor = new EntradaText("Any", 1100, 400, AMPLE_ENTRADA_TEXT/2, ALT_ENTRADA_TEXT);
        entradaAnyAutor.setColorsFonts(colors, fonts);

        entradaAnyLlibre = new EntradaText("Any", 1100, 500, AMPLE_ENTRADA_TEXT/2, ALT_ENTRADA_TEXT);
        entradaAnyLlibre.setColorsFonts(colors, fonts);

        selectorImatgeAutor = new SelectorImatge(400, 300, 500, 500);
        selectorImatgeAutor.setColorsFonts(colors, fonts);

        selectorImatgeLlibre = new SelectorImatge(400, 300, 500, 500);
        selectorImatgeLlibre.setColorsFonts(colors, fonts);

        botonsAutor = new BotonsGrup( 600, 20, 850, ALT_BOTO);
        botonsAutor.setColorsFonts(colors, fonts);
        botonsAutor.setBotons("RESUM", "OBRA", "VISUALITZACIONS");

        botonsLlibre= new BotonsGrup( 600, 20, 850, ALT_BOTO);
        botonsLlibre.setColorsFonts(colors, fonts);
        botonsLlibre.setBotons("RESUM", "OBRA", "VISUALITZACIONS");

        menuApp = new MenuApp(10, 200, 270, 840);
        menuApp.setColorsFonts(colors, fonts);
        menuApp.setLogo(imgLogo);
        menuApp.setMenuApp();

        // EXPLORADOR ////////////////////////////////////////////////////////////////////////////

        entradaCercador = new EntradaCercador("", p5.width - AMPLE_ENTRADA_CERCADOR -50, 20, AMPLE_ENTRADA_CERCADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercador.setColors(colors);
        entradaCercador.setFonts(fonts);

        entradaCercadorGlobal = new EntradaCercador("Cerca", 400, 225, AMPLE_ENTRADA_EXPLORADOR, BOTO_FAVORIT, colors, fonts);
        entradaCercadorGlobal.setEsborraEnConfirmar(false);


        BotoOpcio opcioTerme = new BotoOpcio("Terme", 500, 325, midaCercleBotoOpcio);
        opcioTerme.setColorsFonts(colors, fonts);

        BotoOpcio opcioLema = new BotoOpcio("Lema", 680, 325, midaCercleBotoOpcio);
        opcioLema.setColorsFonts(colors, fonts);

        opcionsCercador = new BotoOpcioGrup(2);
        opcionsCercador.setSeleccionables(opcioTerme, opcioLema);
        opcionsCercador.getSeleccionableAT(0).setSeleccionat(true);

        String[][] textosCerca = {
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},
                {"Poema", "Llibre", "Autor", "12", "56", "versosssssssssssssssssssssssss"},

        };

        gtc = new GraellaTarjaCerca(10, 1, 300, 400, 1580, 600);
        gtc.setColorsFonts(colors, fonts);
        gtc.setData(textosCerca);
        gtc.setTitol("Resultats cerca");
        gtc.setTarges(p5);
        gtc.setBotons(p5);


        // RESUMS //////////////////////////////////////////////////////////////////////

        resumAutor = new ResumAutor(300, 260, p5.width-350, 200);
        resumAutor.setColorsFonts(colors, fonts);
        ((ResumAutor)resumAutor).setResumLlibres(getNumPoemaris(autorSeleccionat), getMinAnyAutor(autorSeleccionat), getMaxAnyAutor(autorSeleccionat));
        ((ResumAutor)resumAutor).setResumPoemes(getNumPoemes(autorSeleccionat), getMitjanaPoemesLlibre(autorSeleccionat));
        ((ResumAutor)resumAutor).setResumEstrofes(getNumEstrofes(autorSeleccionat), getMitjanaEstrofesPoema(autorSeleccionat));
        ((ResumAutor)resumAutor).setResumVersos(getNumVersos(autorSeleccionat), getMitjanaVersosEstrofa(autorSeleccionat));

        resumLlibre = new ResumLlibre(300, 260, p5.width-350, 200);
        resumLlibre.setColorsFonts(colors, fonts);
        ((ResumLlibre)resumLlibre).setResumAutor(poemariSeleccionat.getAutor(), getMinAnyAutor(autorSeleccionat), getMaxAnyAutor(autorSeleccionat));
        ((ResumLlibre)resumLlibre).setResumPoemes(poemariSeleccionat.getNumPoemes(), getMitjanaPoemesLlibre(autorSeleccionat));
        ((ResumLlibre)resumLlibre).setResumEstrofes(poemariSeleccionat.getNumEstrofes(), getMitjanaEstrofesPoema(poemariSeleccionat));
        ((ResumLlibre)resumLlibre).setResumVersos(poemariSeleccionat.getNumVersos(), getMitjanaVersosEstrofes(poemariSeleccionat));
        resumLlibre.resums[0].setTextLlarg(true);

        resumPoema = new ResumPoema(300, 260, p5.width-350, 200);
        resumPoema.setColorsFonts(colors, fonts);
        ((ResumPoema)resumPoema).setResumAutor(autorSeleccionat.getNom(), getMinAnyAutor(autorSeleccionat), getMaxAnyAutor(autorSeleccionat));
        ((ResumPoema)resumPoema).setResumLlibre(poemariSeleccionat.getTitol(), getNumPoemaris(autorSeleccionat));
        ((ResumPoema)resumPoema).setResumEstrofes(poemariSeleccionat.getNumEstrofes(), poemaSeleccionat.getMaxVersosEstrofes());
        ((ResumPoema)resumPoema).setResumVersos(poemariSeleccionat.getNumVersos(), poemariSeleccionat.getMaxLletresVersosPoemari());
        resumPoema.resums[0].setTextLlarg(true);
        resumPoema.resums[1].setTextLlarg(true);

        resumApp = new ResumApp(300, 260, p5.width-350, 200);
        resumApp.setColorsFonts(colors, fonts);
        ((ResumApp)resumApp).setResumAutors(getNumAutors(autors), getMinAnyAutors(autors), getMaxAnyAutors(autors));
        ((ResumApp)resumApp).setResumLlibres(getNumPoemaris(autors), getMitjanaLlibresAutors(autors));
        ((ResumApp)resumApp).setResumPoemes(getNumPoemes(autors), getMitjanaPoemesAutors(autors));
        ((ResumApp)resumApp).setResumEstrofes(getNumEstrofes(autors), getMitjanaEstrofesPoemes(autors));
        ((ResumApp)resumApp).setResumVersos(getNumVersos(autors), getMitjanaVersosEstrofes(autors));

    }



    public void setPantalles(){
        pantallaInici = new Pantalla(PANTALLA.INICI);
        pantallaInici.addElements(menuApp, entradaCercador, resumApp, botoAfegirAutor, botoAfegirLlibre, taulaAutors, taulaLlibres);
        pantallaInici.addElement(new Titol(posTitularX - 60, posTitularY + 120, colors, fonts, "Resum General"));
        pantallaInici.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_INICI, "Inici", "Poètica"));

        pantallaExplorar = new Pantalla(PANTALLA.EXPLORAR);
        pantallaExplorar.addElements(menuApp, entradaCercadorGlobal, opcionsCercador, llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, gtc);
        pantallaExplorar.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_EXPLORAR, "Explorar", "Poètica"));

        pantallaFavorits = new Pantalla(PANTALLA.FAVORITS);
        pantallaFavorits.addElements(menuApp, entradaCercador, graellaAutorsFavorits, graellaLlibresFavorits, botoAfegirAutor, botoAfegirLlibre);
        pantallaFavorits.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_FAVORITS, "Favorits", "Poètica"));

        pantallaAutors = new Pantalla(PANTALLA.AUTORS);
        pantallaAutors.addElements(menuApp, entradaCercador, llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaAutors);
        pantallaAutors.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_AUTORS, "Autors", "Poètica"));


        pantallaLlibres = new Pantalla(PANTALLA.LLIBRES);
        pantallaLlibres.addElements(menuApp, entradaCercador, llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaLlibres);
        pantallaLlibres.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_LLIBRES, "Llibres", "Poètica"));

        pantallaPoemes = new Pantalla(PANTALLA.POEMES);
        pantallaPoemes.addElements(menuApp,entradaCercador, llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaPoemes);
        pantallaPoemes.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_POEMES, "Poemes", "Poètica"));

        titolAutor = new Titol(posTitularX - 60, posTitularY + 120, colors, fonts, autorSeleccionat.getNom());
        pantallaAutorResum = new Pantalla(PANTALLA.AUTOR_RESUM);
        pantallaAutorResum.addElements(menuApp, entradaCercador, botoEditarAutor, botonsAutor, resumAutor, taulaResumAutor, titolAutor);
        pantallaAutorResum.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_AUTORS, "Autor", "Resum"));

        pantallaAutorObra = new Pantalla(PANTALLA.AUTOR_OBRA);
        pantallaAutorObra.addElements(menuApp, entradaCercador, botoAfegirLlibre, botonsAutor, graellaLlibres, titolAutor);
        pantallaAutorObra.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_AUTORS, "Autor", "Obra"));

        pantallaAutorVisual = new Pantalla(PANTALLA.AUTOR_VISUAL);
        pantallaAutorVisual.addElements(menuApp, entradaCercador, botonsAutor, graellaVisualitzacions, titolAutor);
        pantallaAutorVisual.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_AUTORS, "Autor", "Visualitzacions"));

        pantallaAutorEdita = new Pantalla(PANTALLA.AUTOR_EDITA);
        pantallaAutorEdita.addElements(menuApp, entradaCercador, botonsAutor, entradaNomAutor, entradaAnyAutor, selectorImatgeAutor, botoGuardar, titolAutor);
        pantallaAutorEdita.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_AUTORS, "Autor", "Edita"));

        titolLlibre = new Titol(posTitularX - 60, posTitularY + 120, colors, fonts, poemariSeleccionat.getTitol() + " ("+poemariSeleccionat.getAny()+")");
        pantallaLlibreResum = new Pantalla(PANTALLA.LLIBRE_RESUM);
        pantallaLlibreResum.addElements(menuApp, entradaCercador, botoEditarLlibre, botonsLlibre, resumLlibre, taulaResumLlibre, titolLlibre);
        pantallaLlibreResum.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_LLIBRES, "Llibre", "Resum"));

        pantallaLlibreObra = new Pantalla(PANTALLA.LLIBRE_OBRA);
        pantallaLlibreObra.addElements(menuApp, entradaCercador, botoAfegirPoema, botonsLlibre, graellaPoemes, titolLlibre);
        pantallaLlibreObra.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_LLIBRES, "Llibre", "Obra"));

        pantallaLlibreVisual = new Pantalla(PANTALLA.LLIBRE_VISUAL);
        pantallaLlibreVisual.addElements(menuApp, entradaCercador, botonsAutor, titolLlibre);
        pantallaLlibreVisual.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_LLIBRES, "Llibre", "Visualitzacions"));

        pantallaLlibreEdita = new Pantalla(PANTALLA.LLIBRE_EDITA);
        pantallaLlibreEdita.addElements(menuApp, entradaCercador, botonsLlibre, selectorImatgeLlibre, llistaAutorLlibre, entradaTitolLlibre, entradaAnyLlibre, botoGuardar, titolLlibre);
        pantallaLlibreEdita.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_LLIBRES, "Llibre", "Edita"));

        String textTitolPoema = poemaSeleccionat.getNumero() + " - " + poemaSeleccionat.getTitol().substring(0, min(25, poemaSeleccionat.getTitol().length()));
        titolPoema = new Titol(posTitularX - 60, posTitularY + 120, colors, fonts, textTitolPoema);
        pantallaPoemaResum = new Pantalla(PANTALLA.POEMA_RESUM);
        pantallaPoemaResum.addElements(menuApp, entradaCercador, botoEditarPoema, resumPoema, taulaResumPoema, titolPoema);
        pantallaPoemaResum.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_POEMES, "Poema", "Resum"));

        pantallaPoemaEdita = new Pantalla(PANTALLA.POEMA_EDITA);
        pantallaPoemaEdita.addElements(menuApp, entradaCercador, titolPoema);
        pantallaPoemaEdita.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_POEMES, "Poema", "Edita"));

        pantallaQuantitatives = new Pantalla(PANTALLA.QUANTITATIVES);
        pantallaQuantitatives.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaQuantitatives.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_QUANTITATS, "Visualitzacions", "Quantitatives"));

        pantallaQualitatives = new Pantalla(PANTALLA.QUALITATIVES);
        pantallaQualitatives.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaQualitatives.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_QUALITATS, "Visualitzacions", "Qualitatives"));

        pantallaRelacionals = new Pantalla(PANTALLA.RELACIONALS);
        pantallaRelacionals.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaRelacionals.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_RELACIONS, "Visualitzacions", "Relacionals"));

        pantallaTematiques = new Pantalla(PANTALLA.TEMATIQUES);
        pantallaTematiques.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaTematiques.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_TEMES, "Visualitzacions", "Temàtiques"));

        pantallaCronologiques = new Pantalla(PANTALLA.CRONOLOGIQUES);
        pantallaCronologiques.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaCronologiques.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_TEMPS, "Visualitzacions", "Cronològiques"));

        pantallaAltres = new Pantalla(PANTALLA.ALTRES);
        pantallaAltres.addElements(menuApp, entradaCercador,  llistaAutors, llistaLlibres, llistaPoemes, botoFiltrar, graellaVisualitzacions);
        pantallaAltres.addElement(new Titulars(posTitularX, posTitularY, colors, fonts, CODI_ALTRES, "Visualitzacions", "Altres"));

    }

    public GraellaTarja setGraella(String[][] dades, String titol, PImage img, int colorFons, int numFiles, int numColumnes, float x, float y, float w, float h){
        GraellaTarja graella = new GraellaTarja(numFiles, numColumnes, x, y, w, h);
        graella.setData(dades);
        //graella.ordenaTargesPerTitolAsc();
        graella.setTitol(titol);
        graella.setColorsFonts(colors, fonts);
        graella.setTarges(p5);
        graella.setImatges(p5, img, colorFons);
        graella.setBotons(p5);
        graella.setSelecccionableOrdre(p5);
        return graella;
    }

    public GraellaTarja setGraellaVisuals(String[][] dades, String valorFiltre, String titol, PImage img, int colorFons, int numFiles, int numColumnes, float x, float y, float w, float h){
        GraellaTarja graella = new GraellaTarja(numFiles, numColumnes, x, y, w, h);
        graella.setData(dades);
        graella.setTitol(titol);
        graella.setColorsFonts(colors, fonts);
        graella.setTarges(p5);
        graella.setImatges(p5, img, colorFons);
        graella.setBotons(p5);
        return graella;
    }

    public void updateGraellaFiltre(String[][] dades,String valorFiltre, PImage img, int colorFons){
        graellaVisualitzacions.setData(dades);
        if(!valorFiltre.equals("Totes")) {
            graellaVisualitzacions.filtraDades(valorFiltre);
            graellaVisualitzacions.setTitol(valorFiltre);
        }
        graellaVisualitzacions.setColorsFonts(colors, fonts);
        graellaVisualitzacions.setTarges(p5);
        graellaVisualitzacions.setImatges(p5, img, colorFons);
        graellaVisualitzacions.setBotons(p5);
    }

    public void dibuixaGUI(){
        currentPantalla.display(p5);
    }

    public void updatePantallesAutor(PApplet p5){
        botonsAutor.clickBotons(p5);
        if(botonsAutor.botoSeleccionat!=-1) {
            if (botonsAutor.getTextBotoActivat().equals("OBRA")) {
                currentPantalla = pantallaAutorObra;
            } else if (botonsAutor.getTextBotoActivat().equals("RESUM")) {
                currentPantalla = pantallaAutorResum;
            } else if (botonsAutor.getTextBotoActivat().equals("VISUALITZACIONS")) {
                currentPantalla = pantallaAutorVisual;
            }
        }
    }

    public void updatePantallesLlibre(PApplet p5){
        botonsLlibre.clickBotons(p5);
        if(botonsLlibre.botoSeleccionat!=-1) {
            if (botonsLlibre.getTextBotoActivat().equals("OBRA")) {
                currentPantalla = pantallaLlibreObra;
            } else if (botonsLlibre.getTextBotoActivat().equals("RESUM")) {
                currentPantalla = pantallaLlibreResum;
            } else if (botonsLlibre.getTextBotoActivat().equals("VISUALITZACIONS")) {
                currentPantalla = pantallaLlibreVisual;
            }
        }
    }

    public void updatePantallaMenu(PApplet p5){
        menuApp.updateClick(p5);
        if(menuApp.mouseDins(p5) && menuApp.opcioSeleccionada[0]!=-1){
            switch(menuApp.getTitolOpcioSeleccionada()){
                case "Inici":           currentPantalla = pantallaInici; break;
                case "Explorar":        currentPantalla = pantallaExplorar; break;
                case "Favorits":        currentPantalla = pantallaFavorits; break;
                case "Autors":          currentPantalla = pantallaAutors; break;
                case "Llibres":         currentPantalla = pantallaLlibres; break;
                case "Poemes":          currentPantalla = pantallaPoemes; break;
                case "Quantitats":   currentPantalla = pantallaQuantitatives;
                                        updateGraellaFiltre(dadesVisuals, "Quantitativa", imgVisuals, colorVisuals);
                                        break;
                case "Qualitats":    currentPantalla = pantallaQualitatives;
                                        updateGraellaFiltre(dadesVisuals, "Qualitativa", imgVisuals, colorVisuals);
                                        break;
                case "Relacions":     currentPantalla = pantallaRelacionals;
                                        updateGraellaFiltre(dadesVisuals, "Relacional", imgVisuals, colorVisuals);
                                        break;
                case "Temes":      currentPantalla = pantallaTematiques;
                                        updateGraellaFiltre(dadesVisuals, "Temàtica", imgVisuals, colorVisuals);
                                        break;
                case "Temps":   currentPantalla = pantallaCronologiques;
                                        updateGraellaFiltre(dadesVisuals, "Cronològica", imgVisuals, colorVisuals);
                                        break;
                case "Altres":          currentPantalla = pantallaAltres;
                                        updateGraellaFiltre(dadesVisuals, "Altres", imgVisuals, colorVisuals);
                                        break;
                default:
            }
        }
    }


    public void mouseEvents(PApplet p5){

        currentPantalla.mouseEvents(p5);

        if((currentPantalla.equals(pantallaInici) || currentPantalla.equals(pantallaFavorits)) && botoAfegirAutor.mouseDins(p5)){
            currentPantalla = pantallaAutorEdita;
        }
        else if((currentPantalla.equals(pantallaInici) || currentPantalla.equals(pantallaFavorits)) && botoAfegirLlibre.mouseDins(p5)){
            currentPantalla = pantallaLlibreEdita;
        }
        else if(currentPantalla.equals(pantallaFavorits) && graellaAutorsFavorits.isTarjaSeleccionada()){
            currentPantalla = pantallaAutorResum;
        }
        else if(currentPantalla.equals(pantallaAutors) && graellaAutors.isTarjaSeleccionada()){
            currentPantalla = pantallaAutorResum;
        }
        else if(currentPantalla.equals(pantallaLlibres) && graellaLlibres.isTarjaSeleccionada()){
            currentPantalla = pantallaLlibreResum;
        }
        else if(currentPantalla.equals(pantallaPoemes) && graellaPoemes.isTarjaSeleccionada()){
            currentPantalla = pantallaPoemaResum;
        }
        else if(currentPantalla.equals(pantallaQualitatives) && graellaVisualitzacions.isTarjaSeleccionada()){
            currentPantalla = pantallaAutorVisual;
            updateGraellaFiltre(dadesVisuals, "Qualitativa", imgVisuals,colorVisuals);
            System.out.println("VISUALITZACIÓ QUALITATIVA: " + graellaVisualitzacions.numTarjaSeleccionada);
        }
        else  if(currentPantalla.equals(pantallaAutorResum) ||
                currentPantalla.equals(pantallaAutorObra) ||
                currentPantalla.equals(pantallaAutorVisual) ||
                currentPantalla.equals(pantallaAutorEdita)){

            if(currentPantalla.equals(pantallaAutorResum)){
                if(botoEditarAutor.mouseDins(p5)){
                    currentPantalla = pantallaAutorEdita;
                    botonsAutor.desactivaTotesOpcions();
                }
            }
            else if(currentPantalla.equals(pantallaAutorObra)){
                if(botoAfegirLlibre.mouseDins(p5)){
                    currentPantalla = pantallaLlibreEdita;
                    botonsAutor.desactivaTotesOpcions();
                }
            }
            updatePantallesAutor(p5);
        }
        else  if(currentPantalla.equals(pantallaLlibreResum) ||
                currentPantalla.equals(pantallaLlibreObra) ||
                currentPantalla.equals(pantallaLlibreVisual) ||
                currentPantalla.equals(pantallaLlibreEdita)) {

            if (currentPantalla.equals(pantallaLlibreResum)) {
                if (botoEditarLlibre.mouseDins(p5)) {
                    currentPantalla = pantallaLlibreEdita;
                    botonsLlibre.desactivaTotesOpcions();
                }
            }
            else if (currentPantalla.equals(pantallaLlibreObra)) {
                if (botoAfegirPoema.mouseDins(p5)) {
                    currentPantalla = pantallaPoemaEdita;
                    botonsLlibre.desactivaTotesOpcions();
                }
            }

            updatePantallesLlibre(p5);
        }
        else if(currentPantalla.equals(pantallaPoemaResum)){
            if(botoEditarPoema.mouseDins(p5)){
                currentPantalla = pantallaPoemaEdita;
            }
        }
        updatePantallaMenu(p5);

    }

    public void keyPressedEvent(PApplet p5){
        currentPantalla.keyPressedEvents(p5);
        //pantallaActual = entradaCercador.updateKeyPressed(p5.keyCode, pantallaActual, entradaCercadorGlobal);
        if(entradaCercador.textCerca.length()>0){
            currentPantalla = pantallaExplorar;
            entradaCercadorGlobal.text = entradaCercador.textCerca;
            entradaCercador.textCerca = "";
        }
    }

    public void keyTypedEvent(PApplet p5){
        currentPantalla.keyTypedEvents(p5);
    }

}
