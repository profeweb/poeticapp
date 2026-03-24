package gui;

import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;

public class Menu extends GuiElement {

    String titol;
    PShape logo;
    ArrayList<SubMenu> subMenus;

    public Menu (String titol, float x, float y, float w, float h){
        super(x, y, w, h);
        this.titol = titol;
        this.subMenus = new ArrayList<>();
    }

    public void setLogo(PShape logo){ this.logo = logo; }

    public void afegirSubMenu(SubMenu subMenu){
        this.subMenus.add(subMenu);
    }

    public void display(PApplet p5){

        p5.pushStyle();

            // Titol
            p5.fill(colors.getColorEntradaTextText());
            p5.textFont(fonts.getFontTerciaria());
            p5.text(titol, x + w/2, y);

            // Logo
            if(logo!=null){
                p5.shapeMode(p5.CENTER);
                p5.shape(logo, x + w/2, h + 50, 200, 200);
            }

        p5.popStyle();

        // Dibuixa els submenus
        for(SubMenu subMenu : subMenus){
            subMenu.display(p5);
        }
    }

    public int[] opcioClicada(PApplet p5){

        for(int numSubMenu =0; numSubMenu<subMenus.size(); numSubMenu++) {
            SubMenu sbi = subMenus.get(numSubMenu);
            int numOpcions = subMenus.get(numSubMenu).botons.size();
            for (int numOpcio=0; numOpcio<numOpcions; numOpcio++) {
                BotoIcona bi = sbi.botons.get(numOpcio);
                if (bi.mouseDins(p5)) {
                    int[] opcio = {numSubMenu, numOpcio};
                    return opcio;
                }
            }
        }
        return null;
    }

}
