package comptes;

public class ParaulaNigul {

    // Classe auxiliar interna per guardar paraules ja col·locades
        String text;
        float x, y, w, h;
        float fontSize;
        int col;

    public ParaulaNigul(String text, float x, float y, float w, float h, float fontSize, int col) {
            this.text     = text;
            this.x        = x;
            this.y        = y;
            this.w        = w;
            this.h        = h;
            this.fontSize = fontSize;
            this.col      = col;
        }

        // Comprova si el rectangle d'aquesta paraula se solapa amb un altre
        public boolean overlaps(float ox, float oy, float ow, float oh) {
            float padding = 6; // marge mínim entre paraules (px)
            return !(ox + ow + padding < x        ||
                    ox - padding      > x + w    ||
                    oy + oh + padding < y        ||
                    oy - padding      > y + h);
        }

}
