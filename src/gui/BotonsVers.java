package gui;

import processing.core.PApplet;

import java.util.ArrayList;

public class BotonsVers extends GuiElement {

    ArrayList<BotoToken> botons;

    float widthCharacter = 12;
    float marginButtons = 5;

    public BotonsVers(float x, float y, float h) {
        super(x, y, 0, h);
    }

    public void setBotons(String[] tokens){

        this.botons = new ArrayList<>();
        float xwb = x + marginButtons;

        for(String token: tokens){
            float ww = widthCharacter * token.length() + 0;
            BotoToken wb = new BotoToken(token, xwb, y + 5, ww, h - 10);
            //wb.setSizeTextBoto(widthCharacter);
            botons.add(wb);
            xwb += (ww + marginButtons);
            this.w +=  (ww + marginButtons);
        }

        this.w += marginButtons;
    }

    public void display(PApplet p5){
        p5.pushStyle();
        p5.fill(200);
        p5.noStroke();
        p5.rectMode(p5.CORNER);
        p5.rect(this.x, this.y, this.w, h, 5);
        for(Boto boto : botons){
            boto.display(p5);
        }
        p5.popStyle();
    }

    public void updateClick(PApplet p5){
        for(BotoToken boto : botons){
            if(boto.mouseDins(p5)){
                boto.toggleSelection();
                break;
            }
        }
    }
}
