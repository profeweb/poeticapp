import gui.Gui;
import parser.Poemari;
import processing.core.PApplet;

import java.io.File;

public class Main extends PApplet {

    Gui gui;
    Poemari poemari;

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
        gui = new Gui(this);


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