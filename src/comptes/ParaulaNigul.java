package comptes;

import processing.core.PApplet;

public class ParaulaNigul {

    String textParaula; // Text de la paraula
    float x, y, w, h;   // Posició i dimensions
    float midaFont;     // Mida de la font
    int col;            // Colro del text

    // Constructor
    public ParaulaNigul(String textParaula, float x, float y, float w, float h, float midaFont, int col) {
            this.textParaula = textParaula;
            this.x = x; this.y = y;
            this.w = w; this.h = h;
            this.midaFont = midaFont;
            this.col = col;
    }

    // Comprova si el rectangle de la paraula se sol·lapa amb un altre
    public boolean solapament(float altreX, float altreY, float altreW, float altreH) {
            float marge = 6; // marge mínim entre paraules (px)
            return !(altreX + altreW + marge < x    ||
                    altreX - marge > x + w          ||
                    altreY + altreH + marge < y     ||
                    altreY - marge > y + h);
    }

    // Dibuixa la paraula
    public void display(PApplet p5){

        p5.pushStyle();
            p5.fill(col);
            p5.textFont(p5.createFont("Georgia", midaFont));
            p5.textSize(midaFont);
            p5.textAlign(p5.LEFT, p5.TOP);
            p5.text(textParaula, x, y);
        p5.popStyle();

    }

}
