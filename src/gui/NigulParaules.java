package gui;


import comptes.ParaulaNigul;
import comptes.TermeFreq;
import processing.core.PApplet;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PConstants.TWO_PI;

public class NigulParaules extends GuiElement{

    ArrayList<TermeFreq> termes;
    ArrayList<ParaulaNigul> paraulesNiguls;

    public NigulParaules(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setTermes(ArrayList<TermeFreq> termes){
        this.termes = termes;
        this.termes.sort((a, b) -> Float.compare(b.getFrequencia(), a.getFrequencia()));
    }

    public int freqAColor(PApplet p5, float normFreq, int[] paletaColors) {

        float scaled = normFreq * (paletaColors.length - 1);
        int   idx    = (int) scaled;
        float t      = scaled - idx;

        if (idx >= paletaColors.length - 1) return paletaColors[paletaColors.length - 1];

        // Interpolació lineal entre dos colors de la paleta
        return p5.lerpColor(paletaColors[idx], paletaColors[idx + 1], t);
    }

    public void situaParaulesNigul(PApplet p5, int numParaules, float minSize, float maxSize, int[] paletaColors){

        paraulesNiguls = new ArrayList<>();

        ArrayList<ParaulaNigul> colocades = new ArrayList<>();

        // 1. Calcula rang de freqüències per normalitzar
        float maxFreq = termes.get(0).getFrequencia();
        float minFreq = termes.get(numParaules).getFrequencia();
        float logMax   = log(maxFreq + 1);
        float logMin   = log(minFreq + 1);   // +1 evita log(0)
        float freqRange = (logMax == logMin) ? 1 : (logMax - logMin);

        // 2. Paràmetres de l'espiral de col·locació
        float centerX      = this.x + this.w/2.0f;
        float centerY      = this.y + this.h/2.0f;
        float spiralStep   = 0.05f;   // px per iteració de l'espiral
        float angleStep      = 0.005f;  // radians per iteració
        int   maxNumIntents  = 100000;  // límit d'iteracions per paraula


        // 3. Col·locar cada paraula sense solapament
        int nt=0;
        for (TermeFreq tf : termes) {

            float normFreq = (log(tf.getFrequencia() + 1) - logMin) / freqRange;
            float fs       = p5.map(normFreq, 0, 1, minSize, maxSize);
            int col      = freqAColor(p5, normFreq, paletaColors);


            p5.textFont(p5.createFont("Georgia", fs));
            p5.textSize(fs);
            float ampleParaula = p5.textWidth(tf.getTerme());
            float altParaula = fs * 1.2f; // ascendents + descendents aproximats

            // Espiral d'Arquimedes des del centre
            float angle  = p5.random(TWO_PI); // angle inicial aleatori -> varietat
            float radi = 0;
            boolean posicionada = false;

            for (int intent = 0; intent < maxNumIntents; intent++) {

                float tryX = centerX + radi * cos(angle) - ampleParaula / 2f;
                float tryY = centerY + radi * sin(angle) - altParaula / 2f;

                // Comprova que la paraula cab dins de la finestra
                if (tryX < x+50 || tryY < y+50 ||
                        tryX + ampleParaula > x+w-50 || tryY + altParaula > y+h-50) {
                    // Segueix espiral encara que surti: potser un angle diferent hi cap
                    radi += spiralStep;
                    angle  += angleStep;
                    continue;
                }

                // Comprova solapament amb les paraules ja col·locades
                boolean overlapping = false;
                for (ParaulaNigul pw : colocades) {
                    if (pw.solapament(tryX, tryY, ampleParaula, altParaula)) {
                        overlapping = true;
                        break;
                    }
                }

                if (!overlapping) {
                    // Posició lliure trobada -> dibuixar i registrar
                    colocades.add(new ParaulaNigul(tf.getTerme(), tryX, tryY, ampleParaula, altParaula, fs, col));
                    paraulesNiguls.add(new ParaulaNigul(tf.getTerme(), tryX, tryY, ampleParaula, altParaula, fs, col));

                    posicionada = true;
                    break;
                }

                radi += spiralStep;
                angle  += angleStep;
            }

            if (!posicionada) {
                println("Avís: no s'ha pogut col·locar '" + tf.getTerme() + "'");
            }

            nt++;
            if(nt>= numParaules){
                System.out.println("FINAL");
                break;
            }
        }
    }

    public void display(PApplet p5){

        for(ParaulaNigul paraulaNigul : paraulesNiguls){
            paraulaNigul.display(p5);
        }
    }

    public void dibuixaLlegenda(PApplet p5, String etiqueta, int[] paletaColors, int minOcc, int maxOcc, float x, float y, float barW, float barH) {

        final int   TICK_COUNT    = 5;    // nombre de marques intermèdies
        final float TICK_H        = 6;    // alçada de les marques (px)
        final float LABEL_OFFSET  = 10;   // separació etiqueta–barra (px)
        final float TITLE_OFFSET  = 18;   // separació títol–barra (px)
        final float FONT_SIZE_LBL = 11;
        final float FONT_SIZE_TTL = 12;


        // ── 2. Barra de gradient (escala logarítmica, igual que el núvol) ─────
        float logMin = log(minOcc + 1);
        float logMax = log(maxOcc + 1);

        p5.pushStyle();

        for (int px = 0; px < (int) barW; px++) {
            float t        = px / barW;                           // posició lineal [0,1]
            float logVal   = logMin + t * (logMax - logMin);      // valor log interpolat
            float normFreq = (logVal - logMin) / (logMax == logMin ? 1 : logMax - logMin);
            p5.stroke(freqAColor(p5, normFreq, paletaColors));
            p5.line(x + px, y, x + px, y + barH);
        }

        // ── 3. Marc de la barra ───────────────────────────────────────────────
        p5.noFill();
        p5.stroke(255, 255, 255, 60);
        p5.strokeWeight(1);
        p5.rect(x, y, barW, barH, 2);
        p5.noStroke();

        // ── 4. Marques i etiquetes numèriques ─────────────────────────────────
        p5.textFont(p5.createFont("Georgia", FONT_SIZE_LBL));
        p5.textSize(FONT_SIZE_LBL);
        p5.textAlign(CENTER, TOP);

        for (int i = 0; i <= TICK_COUNT; i++) {
            float t        = i / (float) TICK_COUNT;
            float tickX    = x + t * barW;

            // Valor d'ocurrències corresponent a aquesta posició (escala log inversa)
            float logVal   = logMin + t * (logMax - logMin);
            int   occValue = round(exp(logVal) - 1);

            // Marca vertical
            p5.stroke(50, 180);
            p5.strokeWeight(1);
            p5.line(tickX, y + barH, tickX, y + barH + TICK_H);
            p5.noStroke();

            // Etiqueta numèrica
            p5.fill(50);
            p5.text(occValue, tickX, y + barH + LABEL_OFFSET);
        }

        // ── 5. Títol de la llegenda ───────────────────────────────────────────
        p5.textFont(p5.createFont("Georgia", FONT_SIZE_TTL));
        p5.textSize(FONT_SIZE_TTL);
        p5.textAlign(CENTER, BASELINE);
        p5.fill(50);
        p5.text(etiqueta, x + barW / 2, y - TITLE_OFFSET * 0.3f);

        p5.popStyle();

    }

}
