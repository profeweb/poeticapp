package gui;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static gui.Mides.*;

public class SelectorImatge extends GuiElement {

    PImage img;
    String titol="";
    File file;
    String rutaCarpeta = "C:\\Users\\tonim\\Desktop\\imatges\\";

    Boto bLoad;

    public SelectorImatge(float x, float y, float w, float h) {
        super(x, y, w, h);
        bLoad = new BotoIcona( CODI_ICONA_CERCADOR, x, y + h + 15, AMPLE_BOTO, ALT_BOTO);
    }

    public void setColorsFonts(Colors c, Fonts f){
        this.colors = c;
        this.fonts = f;
        bLoad.setColorsFonts(colors, fonts);
    }

    public void display(PApplet p5){
        p5.pushStyle();
        // Dibuixa la imatge
        if(img!=null){
            p5.image(img, x, y, w, h);
            p5.textSize(34); p5.textAlign(p5.RIGHT);
            p5.fill(0);
            p5.text(titol, x + w, y + h + 15);
        }
        else{
            p5.fill(255);
            p5.rect(x, y, w, h);
            p5.textSize(34); p5.textAlign(p5.RIGHT);
            p5.text("Sense imatge", x + w, y + h + 15);
        }

        // Dibuixa el botó
        bLoad.display(p5);
        p5.popStyle();
    }

    public boolean carregarImatge(PApplet p5){
        return bLoad.mouseDins(p5);
    }


    public void update(PApplet p5, File selection){
        // Obtenim la ruta del fitxer seleccionat
        String rutaImatge = selection.getAbsolutePath();
        img = p5.loadImage(rutaImatge);  // Actualitzam imatge
        titol = selection.getName();  // Actualitzam títol (igual)
    }

    // Copia un fitxer a una altra ubicació
    public void copiar(File file, String rutaCopia, String titol){
        Path original = Paths.get(file.getAbsolutePath());
        Path copia    = Paths.get(rutaCopia+"/"+titol);
        try{
            Files.copy(original, copia);
            System.out.println("OK: fitxer copiat a la carpeta.");
        } catch (IOException e) {
            System.out.println("ERROR: No s'ha pogut copiar el fitxer.");
        }
    }
}
