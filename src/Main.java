import gui.Gui;
import processing.core.PApplet;

import java.io.File;

public class Main extends PApplet {

    Gui gui;
    File file;

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

        gui.mouseEvents(this);

        if(gui.currentPantalla.equals(gui.pantallaAutorEdita) && gui.selectorImatge.carregarImatge(this)){
            selectInput("Selecciona una imatge ...", "seleccionarImatgeAutor");
        }
    }


    // Carrega Imatge
    public void seleccionarImatgeAutor(File selection) {
        if (selection == null) {
            println("No s'ha seleccionat cap fitxer.");
        } else {
            gui.selectorImatge.update(this, selection);
        }
    }
}