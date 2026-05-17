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
        public boolean solapament(float ox, float oy, float ow, float oh) {
            float marge = 6; // marge mínim entre paraules (px)
            return !(ox + ow + marge < x        ||
                    ox - marge      > x + w    ||
                    oy + oh + marge < y        ||
                    oy - marge      > y + h);
        }

}
