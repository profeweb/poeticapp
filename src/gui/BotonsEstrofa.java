package gui;

import parser.Estrofa;
import parser.Vers;
import processing.core.PApplet;

import java.util.ArrayList;

import static gui.Mides.ALT_BOTO;
import static java.lang.Math.min;

public class BotonsEstrofa extends GuiElement{

    int numEstrofa;
    int paginaActual = 0;
    int numVersosPagina, numTotalPagines;
    ArrayList<BotonsVers> botonsVersos;

    float margeEntreVersos = 10;

    BotoIcona bSeg, bAnt;

    public BotonsEstrofa(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setBotonsEstrofa(Estrofa estrofa, int numVersosPagina){

        this.numEstrofa = estrofa.getNumero();
        this.numVersosPagina = numVersosPagina;
        this.numTotalPagines = estrofa.getNumVersos() / numVersosPagina;
        if(estrofa.getNumVersos() % numVersosPagina !=0){
            this.numTotalPagines++;
        }
        this.botonsVersos = new ArrayList<>();

        int numVers = 0;
        for(Vers vers: estrofa.getVersos()){

            float yVers = y + numVers*(h + margeEntreVersos);
            BotonsVers botonsVers = new BotonsVers(x, yVers, h);
            botonsVers.setColorsFonts(colors, fonts);
            botonsVers.setBotons(vers);
            this.botonsVersos.add(botonsVers);
            
            numVers++;
            if(numVers >= numVersosPagina){
                numVers = 0;
            }
        }
    }

    public void setBotonsPaginacio(){

        bAnt = new BotoIcona(Mides.CODI_ANTERIOR, x + w - ALT_BOTO*2 - 25, y-ALT_BOTO -5, ALT_BOTO, ALT_BOTO);
        bAnt.setColors(colors);
        bAnt.setFonts(fonts);

        bSeg = new BotoIcona(Mides.CODI_SEGUENT, x + w - ALT_BOTO , y - ALT_BOTO -5, ALT_BOTO, ALT_BOTO);
        bSeg.setColors(colors);
        bSeg.setFonts(fonts);
    }

    public int primerVersMostrat(){ return this.botonsVersos.get(primerBotoMostrat()).numVers; }
    public int darrerVersMostrat(){ return this.botonsVersos.get(darrerBotoMostrat()).numVers; }

    public int primerBotoMostrat(){
        return paginaActual * numVersosPagina;
    }

    public int darrerBotoMostrat(){
        return min((paginaActual+1)*numVersosPagina -1, botonsVersos.size()-1);
    }

    public boolean mostrarBotonsVers(int numVers){
        return (numVers >= primerBotoMostrat() && numVers <= darrerBotoMostrat());
    }

    public void display(PApplet p5){

        p5.pushStyle();
        p5.fill(0);
        p5.textSize(Mides.midaSubtitol);
        p5.textAlign(p5.LEFT, p5.BOTTOM);
        p5.text("Estrofa "+ this.numEstrofa + " (" + primerVersMostrat() + " - " + darrerVersMostrat() +")", this.x, this.y);
        p5.popStyle();

        int numVers = 0;
        for(BotonsVers botonsVers : botonsVersos){
            if(mostrarBotonsVers(numVers)) {
                botonsVers.display(p5);
            }
            numVers++;
        }

        if(bSeg!=null) {
            bSeg.display(p5);
        }

        if(bAnt !=null) {
            bAnt.display(p5);
        }
    }

    public void updateClick(PApplet p5){
        int numVers = 0;
        for(BotonsVers botonsVers: botonsVersos){
            if(mostrarBotonsVers(numVers)) {
                botonsVers.updateClick(p5);
            }
            numVers++;
        }

        if(bSeg.mouseDins(p5)){
            paginaSeguent();
            if(paginaActual == numTotalPagines){
                bSeg.setActivat(false);
            }
            bAnt.setActivat(true);
        }
        else if(bAnt.mouseDins(p5)){
            paginaAnterior();
            if(paginaActual == 0){
                bAnt.setActivat(false);
            }
            bSeg.setActivat(true);
        }
    }

    public void paginaSeguent(){
        if(this.paginaActual < this.numTotalPagines-1){
            this.paginaActual++;
        }
    }

    public void paginaAnterior(){
        if(this.paginaActual > 0){
            this.paginaActual--;
        }
    }

}
