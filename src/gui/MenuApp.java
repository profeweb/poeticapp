package gui;

import static gui.Mides.*;

public class MenuApp extends Menu {

    public MenuApp(float x, float y, float w, float h){
        super("PoèticAPP", x, y, w, h);
    }

    public void setMenuApp(){

        SubMenu sb1 = new SubMenu("Menú", 10, y + 100, w-10, 150);
        sb1.setColors(colors);
        sb1.setFonts(fonts);
        sb1.afegirOpcioMenu("Inici", CODI_INICI);
        sb1.afegirOpcioMenu("Explorar", CODI_EXPLORAR);
        sb1.afegirOpcioMenu("Favorits", CODI_FAVORITS);
        subMenus.add(sb1);

        SubMenu sb2 = new SubMenu("Biblioteca", 10,  y + 300, w-10, 150);
        sb2.setColors(colors);
        sb2.setFonts(fonts);
        sb2.afegirOpcioMenu("Autors", CODI_AUTORS);
        sb2.afegirOpcioMenu("Llibres", CODI_LLIBRES);
        sb2.afegirOpcioMenu("Poemes", CODI_POEMES);
        subMenus.add(sb2);

        SubMenu sb3 = new SubMenu("Visualitzacions", 10, y + 500, w-10, 350);
        sb3.setColors(colors);
        sb3.setFonts(fonts);
        sb3.afegirOpcioMenu("Quantitats", CODI_QUANTITATS);
        sb3.afegirOpcioMenu("Qualitats", CODI_QUALITATS);
        sb3.afegirOpcioMenu("Relacions", CODI_RELACIONS);
        sb3.afegirOpcioMenu("Temes", CODI_TEMES);
        sb3.afegirOpcioMenu("Temps", CODI_TEMPS);
        sb3.afegirOpcioMenu("Altres", CODI_ALTRES);
        subMenus.add(sb3);

        sb1.setOpcioSeleccionada(0);
    }


}
