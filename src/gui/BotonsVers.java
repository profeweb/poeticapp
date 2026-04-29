package gui;

import parser.Token;
import parser.Vers;
import processing.core.PApplet;

import java.util.ArrayList;

public class BotonsVers extends GuiElement {

    int numVers;
    ArrayList<BotoToken> botons;
    BotoToken botoVers;

    float widthCharacter = 15;
    float margeEntreBotons = 10;

    public BotonsVers(float x, float y, float h) {
        super(x, y, 0, h);
    }

    public void setBotons(Vers vers){

        this.numVers = vers.getNumVers();

        this.botons = new ArrayList<>();
        float xwb = x + margeEntreBotons;

        for(Token token : vers.getTokens()){
            String text = token.getValor();
            float ww = widthCharacter * (1 + text.length()) + 5;
            BotoToken wb = new BotoToken(text, xwb, y + 5, ww, h - 10);
            if(token.getTipus() == Token.Tipus.SEPARADOR){
                wb.setActivat(false);
            }
            wb.setColorsFonts(colors, fonts);
            //wb.setSizeTextBoto(widthCharacter);
            botons.add(wb);
            xwb += (ww + margeEntreBotons);
            this.w +=  (ww + margeEntreBotons);
        }

        this.w += margeEntreBotons;

        this.botoVers = new BotoToken(String.valueOf(numVers), this.x - h*1.25f, this.y, this.h, this.h);
        botoVers.setColorsFonts(colors, fonts);
        botoVers.setArrodoniment(this.h);
        if(numVers%5==0){
            //botoVers.setActivat(false);
        }
    }

    public void setBotons(String[] tokens){

        this.botons = new ArrayList<>();
        float xwb = x + margeEntreBotons;

        for(String token: tokens){
            float ww = widthCharacter * token.length() + 0;
            BotoToken wb = new BotoToken(token, xwb, y + 5, ww, h - 10);
            wb.setColorsFonts(colors, fonts);
            //wb.setSizeTextBoto(widthCharacter);
            botons.add(wb);
            xwb += (ww + margeEntreBotons);
            this.w +=  (ww + margeEntreBotons);
        }

        this.w += margeEntreBotons;

        this.botoVers = new BotoToken(String.valueOf(numVers), this.x - h*1.25f, this.y, this.h, this.h);
        botoVers.setColorsFonts(colors, fonts);
        if(numVers%5==0){
            botoVers.setActivat(false);
        }
    }

    public void display(PApplet p5){
        p5.pushStyle();

        // Botons de Tokens

        p5.fill(200);
        p5.noStroke();
        p5.rectMode(p5.CORNER);
        p5.rect(this.x, this.y, this.w, h, 5);
        for(Boto boto : botons){
            boto.display(p5);
        }

        // Botó de Número de Vers
        botoVers.display(p5);

        p5.popStyle();
    }

    public void activaBotons(boolean b){
        for(BotoToken boto: botons){
            boto.setActivat(b);
        }
    }

    public void seleccionaBotons(boolean b){
        for(BotoToken boto: botons){
            boto.setSelected(b);
        }
    }

    public void updateClick(PApplet p5){

        for(BotoToken boto : botons){
            boto.updateClick(p5);
        }

        if(botoVers.mouseDins(p5)){
            botoVers.updateClick(p5);
            seleccionaBotons(botoVers.selected);
        }
    }
}
