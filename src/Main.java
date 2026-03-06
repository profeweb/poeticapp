import gui.Gui;
import processing.core.PApplet;

public class Main extends PApplet {

    Gui gui;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        size(1920, 1080);
        smooth(10);
    }

    public void setup(){
        surface.setTitle("PoeticAPP");
        gui = new Gui(this);
    }

    public void draw(){
        background(255);
        gui.dibuixaPantalla();
    }

    public void keyPressed(){

    }

    public void mousePressed(){
        gui.mouseEvents(this);
    }

}