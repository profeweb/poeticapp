package comptes;

import processing.core.PApplet;

public class ParaulaNigul {

    // Classe auxiliar interna per guardar paraules ja col·locades
        String textParaula;
        float x, y, w, h;
        float fontSize;
        int col;

    public ParaulaNigul(String textParaula, float x, float y, float w, float h, float fontSize, int col) {
            this.textParaula = textParaula;
            this.x        = x;
            this.y        = y;
            this.w        = w;
            this.h        = h;
            this.fontSize = fontSize;
            this.col      = col;
    }

    // Comprova si el rectangle d'aquesta paraula se solapa amb un altre
    public boolean solapament(float ox, float oy, float ow, float oh) {
            float marge = 6; // marge mínim entre paraules (px)
            return !(ox + ow + marge < x        ||
                    ox - marge      > x + w    ||
                    oy + oh + marge < y        ||
                    oy - marge      > y + h);
    }

    public void display(PApplet p5){

        p5.pushStyle();

        p5.fill(col);
        p5.textFont(p5.createFont("Georgia", fontSize));
        p5.textSize(fontSize);
        p5.textAlign(p5.LEFT, p5.TOP);
        p5.text(textParaula, x, y);

        p5.popStyle();

    }

}
