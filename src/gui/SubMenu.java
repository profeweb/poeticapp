package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class SubMenu extends GuiElement{

    String titol;
    ArrayList<BotoIcona> botons;
    float margeVertical = 5;
    int opcioSeleccionada = -1;

    public SubMenu(String titol, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.titol = titol;
        botons = new ArrayList<>();
    }

    public void afegirOpcioMenu(String text, int codiIcona){

        BotoIcona nouBoto = new BotoIcona(text, codiIcona, x, y, w, h);
        nouBoto.setColors(colors);
        nouBoto.setFonts(fonts);

        botons.add(nouBoto);

        float altBoto = (h - (margeVertical*botons.size()-1)) / botons.size();

        for(int i=0; i<botons.size(); i++){
            float yBoto = this.y + i * altBoto + margeVertical*(i-1);
            botons.get(i).setY(yBoto);
            botons.get(i).setH(altBoto);
        }

    }

    public String getTitol(){ return  this.titol; }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.textFont(fonts.getFontSecundaria());
        p5.fill(colors.getColorPrimari());
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text(this.titol, this.x, this.y - margeVertical);
        for(BotoIcona boto: botons){
            boto.display(p5);
        }
    }

    public void updateClick(PApplet p5){
        int numOpcio = 0;
        for(BotoIcona boto : botons){
            if(boto.mouseDins(p5)){
                opcioSeleccionada = numOpcio;
            }
            numOpcio++;
        }
    }

    public void setBotonsActius(boolean b){
        for(BotoIcona boto : botons){
            boto.activat = b;
        }
    }

    public void setOpcioSeleccionada(int i){
        this.opcioSeleccionada = i;
        botons.get(i).setActivat(false);
    }

    public void resetOpcioSeleccionada(){ this.opcioSeleccionada = -1; }

    public int getOpcioSeleccionada(){ return  this.opcioSeleccionada; }

    public BotoIcona getBotoIcona(){ return botons.get(opcioSeleccionada); }

    public String getTitolOpcioSeleccionada(){ return getBotoIcona().textBoto; }
}
