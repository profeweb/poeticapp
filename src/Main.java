import gui.Gui;
import parser.Autor;
import parser.DadesPoemaris;
import parser.Poemari;
import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;

public class Main extends PApplet {

    Gui gui;

    ArrayList<Autor> autors;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        //size(1920, 1080);
        fullScreen();
        smooth(10);
    }

    public void setup(){
        surface.setTitle("PoeticAPP");

        processaFitxers();
        gui = new Gui(this, autors);

    }

    public void processaFitxers() {

            // Carrega poemaris
            autors = new ArrayList<>();
            String rutaCarpetaArrel = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\";
            File carpetaArrel = new File(rutaCarpetaArrel);
            File[] carpetesAutors = carpetaArrel.listFiles();
            for(File carpetaAutor: carpetesAutors) {
                int posParentesiObert = carpetaAutor.getName().indexOf("(");
                int posParentesiTancat = carpetaAutor.getName().indexOf(")");
                String nomAutor = carpetaAutor.getName().substring(0, posParentesiObert).trim();
                System.out.println(nomAutor);
                int anyAutor = Integer.valueOf(carpetaAutor.getName().substring(posParentesiObert + 1, posParentesiTancat).trim());
                System.out.println(anyAutor);
                Autor autor = new Autor(nomAutor, anyAutor);
                File[] carpetesPoemarisAutor = carpetaAutor.listFiles();
                for (File carpetaPoemari : carpetesPoemarisAutor) {

                    int posParentesiObertAnyPoemari = carpetaPoemari.getName().indexOf("(");
                    int posParentesiTancatAnyPoemari = carpetaPoemari.getName().indexOf(")");

                    String titolPoemari = carpetaPoemari.getName();
                    int anyPoemari = anyAutor;
                    int numPoemes = carpetaPoemari.listFiles().length;

                    if(posParentesiObertAnyPoemari!=-1 && posParentesiTancatAnyPoemari!=-1) {
                        titolPoemari = carpetaPoemari.getName().substring(0, posParentesiObertAnyPoemari).trim();
                        anyPoemari = Integer.valueOf(carpetaPoemari.getName().substring(posParentesiObertAnyPoemari + 1, posParentesiTancatAnyPoemari).trim());
                    }
                    System.out.println(titolPoemari + " , " + anyPoemari);
                    Poemari poemari = new Poemari(titolPoemari, nomAutor, anyPoemari);
                    poemari.parsePoemes(numPoemes, rutaCarpetaArrel + carpetaAutor.getName() + "\\" + carpetaPoemari.getName() + "\\");
                    autor.afegirPoemari(poemari);
                }
                autors.add(autor);
            }
            println("DONE! "+ autors.size());

        String[][] info = DadesPoemaris.getAutorsInfo(autors);
        DadesPoemaris.printArray2D(info);
    }

    public void draw(){
        background(255);
        gui.dibuixaGUI();
    }

    public void keyPressed(){
        gui.keyPressedEvent(this);
    }

    public void keyTyped(){
        gui.keyTypedEvent(this);
    }

    public void mousePressed(){
        if(gui.currentPantalla.equals(gui.pantallaAutorEdita) && gui.selectorImatgeAutor.carregarImatge(this)){
            selectInput("Selecciona una imatge ...", "seleccionarImatgeAutor");
        }
        else if(gui.currentPantalla.equals(gui.pantallaLlibreEdita) && gui.selectorImatgeLlibre.carregarImatge(this)){
            selectInput("Selecciona una imatge ...", "seleccionarImatgeLlibre");
        }
        else {
            gui.mouseEvents(this);
        }
    }


    // Carrega Imatge
    public void seleccionarImatgeAutor(File selection) {
        println("SELECCTING AUTOR IMAGE");
        if (selection == null) {
            println("No s'ha seleccionat cap fitxer.");
        } else {
            gui.selectorImatgeAutor.update(this, selection);
        }
    }

    public void seleccionarImatgeLlibre(File selection) {
        println("SELECCTING BOOK IMAGE");
        if (selection == null) {
            println("No s'ha seleccionat cap fitxer.");
        } else {
            gui.selectorImatgeLlibre.update(this, selection);
        }
    }
}