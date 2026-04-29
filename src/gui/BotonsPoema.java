package gui;

import parser.Estrofa;
import parser.Poema;
import processing.core.PApplet;

public class BotonsPoema extends GuiElement {

    Poema poema;

    BotonsPaginacio botonsPaginacioEstrofa;
    BotonsEstrofa botonsVersosEstrofa;

    public BotonsPoema(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBotonsPoema(Poema poema){

        this.poema = poema;

        botonsPaginacioEstrofa = new BotonsPaginacio(x, y - h*1.5f, h);
        botonsPaginacioEstrofa.setColorsFonts(colors, fonts);
        botonsPaginacioEstrofa.setBotonsPagines(poema.getNumEstrofes());
        botonsPaginacioEstrofa.setBotonsSegAnt();

        Estrofa estrofaActual = poema.getEstrofaAt(botonsPaginacioEstrofa.getPaginaActual());
        setBotonsVersosEstrofa(estrofaActual);
    }

    public void setBotonsVersosEstrofa(Estrofa estrofaActual){
        botonsVersosEstrofa = new BotonsEstrofa(x, y + h*1.5f, w, h);
        botonsVersosEstrofa.setColorsFonts(colors, fonts);
        botonsVersosEstrofa.setBotonsEstrofa(estrofaActual, 10);
        botonsVersosEstrofa.setBotonsPaginacio();
    }

    public void display(PApplet p5){
        botonsVersosEstrofa.display(p5);
        botonsPaginacioEstrofa.display(p5);
    }

    public void updateClick(PApplet p5){
        botonsVersosEstrofa.updateClick(p5);
        botonsPaginacioEstrofa.updateClick(p5);
        if(botonsPaginacioEstrofa.canviPagina){
            int numEstrofaActual = botonsPaginacioEstrofa.getPaginaActual();
            Estrofa estrofaActual = poema.getEstrofaAt(numEstrofaActual);
            setBotonsVersosEstrofa(estrofaActual);
            botonsPaginacioEstrofa.canviPagina = false;
        }
    }
}
