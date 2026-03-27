package gui.test;

import gui.Boto;
import gui.BotonsGrup;
import gui.Colors;
import gui.Fonts;
import processing.core.PApplet;

public class TestBotonsGrup extends PApplet {

    BotonsGrup bGrup;
    Colors colors;
    Fonts fonts;
    int n = 5;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestBotonsGrup");
    }

    public void settings(){ size(800, 800); }

    public void setup(){
        colors = new Colors(this);
        fonts = new Fonts(this);

        bGrup = new BotonsGrup(100, height/2 - 50, width-200, 100);
        bGrup.setColors(colors);
        bGrup.setFonts(fonts);

        String [] nums = creaArrayNums(n);
        bGrup.setBotons(nums);

    }

    public String[] creaArrayNums(int n){
        String[] array = new String[n];
        for(int i=0; i<n; i++){
            array[i] = String.valueOf(i);
        }
        return array;
    }

    public void draw(){
        background(255, 100, 100);

        bGrup.display(this);

        if(bGrup.mouseDins(this)){
            cursor(HAND);
        }
        else {
            cursor(ARROW);
        }
    }

    public void mousePressed(){
        bGrup.clickBotons(this);
    }
}
