package gui.test;

import gui.Boto;
import gui.Colors;
import gui.EntradaText;
import gui.Fonts;
import processing.core.PApplet;

public class TestEntradesText extends PApplet {

    EntradaText etUser, etPassword;
    Boto bLogin;

    Colors colors;
    Fonts fonts;
    String textLogin;

    public static void main(String[] args) {
        PApplet.main("gui.test.TestEntradesText");
    }

    public void settings(){
        size(800, 800);
    }

    public void setup(){

        colors = new Colors(this);
        fonts = new Fonts(this);

        etUser = new EntradaText("USER:", 250, 100, 300, 50);
        etUser.setColors(colors);
        etUser.setFonts(fonts);

        etPassword = new EntradaText("PASSWORD:", 250, 200, 300, 50);
        etPassword.setColors(colors);
        etPassword.setFonts(fonts);

        bLogin = new Boto("LOG IN", 250, 300, 300, 50);
        bLogin.setColors(colors);
        bLogin.setFonts(fonts);

        textLogin = "";
    }

    public void draw(){
        background(255);

        etUser.display(this);
        etPassword.display(this);
        bLogin.display(this);

        fill(0);
        textSize(48); textAlign(CENTER);
        text(textLogin,width/2, height/2);
    }

    public void mousePressed(){
        etUser.updateClick(this);
        etPassword.updateClick(this);

        if(bLogin.mouseDins(this)){
            if(etUser.getText().equals("admin") && etPassword.getText().equals("12345")){
                textLogin = "CORRECTE!!!";
            }
            else {
                textLogin = "INCORRECTE!!!";
            }
        }
        else {
            textLogin="";
        }
    }

    public void keyPressed(){
        etUser.updateKeyPressed(keyCode);
        etPassword.updateKeyPressed(keyCode);
    }

    public void keyTyped(){
        etUser.updateKeyTyped(key);
        etPassword.updateKeyTyped(key);
    }


}
