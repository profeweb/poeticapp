package gui;

public class MenuApp extends Menu {

    public MenuApp(float x, float y, float w, float h){
        super("PoèticAPP", x, y, w, h);
    }

    public void setMenuApp(){

        SubMenu sb1 = new SubMenu("Menú", 10, y + 100, w-20, 150);
        sb1.setColors(colors);
        sb1.setFonts(fonts);
        sb1.afegirOpcioMenu("Inici", 0x1F600);
        sb1.afegirOpcioMenu("Explorar", 0x1F600);
        sb1.afegirOpcioMenu("Favorits", 0x1F600);
        subMenus.add(sb1);

        SubMenu sb2 = new SubMenu("Biblioteca", 10,  y + 300, w-20, 150);
        sb2.setColors(colors);
        sb2.setFonts(fonts);
        sb2.afegirOpcioMenu("Llibres", 0x1F600);
        sb2.afegirOpcioMenu("Poemes", 0x1F600);
        sb2.afegirOpcioMenu("Autors", 0x1F600);
        subMenus.add(sb2);

        SubMenu sb3 = new SubMenu("Visualitzacions", 10, y + 500, w-20, 350);
        sb3.setColors(colors);
        sb3.setFonts(fonts);
        sb3.afegirOpcioMenu("Quantitatives", 0x1F600);
        sb3.afegirOpcioMenu("Qualitatives", 0x1F600);
        sb3.afegirOpcioMenu("Relacionals", 0x1F600);
        sb3.afegirOpcioMenu("Temàtiques", 0x1F600);
        sb3.afegirOpcioMenu("Cronològiques", 0x1F600);
        sb3.afegirOpcioMenu("Altres", 0x1F600);
        subMenus.add(sb3);

        sb1.setOpcioSeleccionada(0);
    }


}
