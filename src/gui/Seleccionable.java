package gui;

public class Seleccionable extends GuiElement {

    String textOpcio;
    boolean seleccionat;

    public Seleccionable(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.seleccionat = false;
        this.textOpcio = "";
    }

    public Seleccionable(String text, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.seleccionat = false;
        this.textOpcio = text;
    }

    public void setSeleccionat(boolean b){ this.seleccionat = b; }

    public void toggleSeleccionat(){ this.seleccionat = !this.seleccionat; }

    public boolean isSeleccionat(){ return  this.seleccionat; }
}
