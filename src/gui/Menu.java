package gui;

import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;

public class Menu extends GuiElement {

    String titol;
    PShape logo;
    ArrayList<SubMenu> subMenus;
    int[] opcioSeleccionada;

    public Menu (String titol, float x, float y, float w, float h){
        super(x, y, w, h);
        this.titol = titol;

        this.subMenus = new ArrayList<>();

        opcioSeleccionada = new int[2];
        opcioSeleccionada[0] = -1;
        opcioSeleccionada[1] = -1;
    }

    public void setLogo(PShape logo){ this.logo = logo; }

    public void afegirSubMenu(SubMenu subMenu){
        this.subMenus.add(subMenu);
    }

    public void display(PApplet p5){

        p5.pushStyle();

            p5.stroke(0);  p5.strokeWeight(2);
            p5.line(this.x + this.w, 0, this.x + this.w, p5.height);

            // Titol
            p5.fill(colors.getColorEntradaTextText());
            p5.textFont(fonts.getFontTerciaria());
            p5.textAlign(p5.CENTER);
            p5.text(titol, x + w/2, y);

            // Logo
            if(logo!=null){
                p5.shapeMode(p5.CENTER);
                p5.shape(logo, x + w/2, h + 50, 200, 200);
            }
            else{
                p5.circle(x + w/2, y - 80, 100);
            }

        p5.popStyle();

        // Dibuixa els submenus
        for(SubMenu subMenu : subMenus){
            subMenu.display(p5);
        }
    }

    public void updateClick(PApplet p5){
        for(int numSubMenu =0; numSubMenu<subMenus.size(); numSubMenu++) {
            SubMenu sbi = subMenus.get(numSubMenu);
            int numOpcions = subMenus.get(numSubMenu).botons.size();
            for (int numOpcio=0; numOpcio<numOpcions; numOpcio++) {
                BotoIcona bi = sbi.botons.get(numOpcio);
                if (bi.mouseDins(p5)) {
                    opcioSeleccionada[0] = numSubMenu;
                    opcioSeleccionada[1] = numOpcio;
                }
            }
        }

        for(int numSubMenu =0; numSubMenu<subMenus.size(); numSubMenu++) {
            SubMenu sbi = subMenus.get(numSubMenu);
            sbi.setBotonsActius(true);
            if(opcioSeleccionada[0]== numSubMenu) {
                int numOpcions = subMenus.get(numSubMenu).botons.size();
                for (int numOpcio = 0; numOpcio < numOpcions; numOpcio++) {
                    BotoIcona bi = sbi.botons.get(numOpcio);
                    if (numOpcio == opcioSeleccionada[1]) {
                        bi.setActivat(false);
                    }
                }
            }
        }
    }

    public int[] getOpcioSeleccionada(){ return this.opcioSeleccionada; }

    public String getTitolOpcioSeleccionada(){ return subMenus.get(opcioSeleccionada[0]).getTitolOpcioSeleccionada(); }

}
