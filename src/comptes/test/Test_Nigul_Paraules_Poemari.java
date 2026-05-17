package comptes.test;

import processing.core.PApplet;
import comptes.ComptadorParaules;
import comptes.ParaulaNigul;
import comptes.ParaulesBuidesCatala;
import comptes.TermeFreq;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Test_Nigul_Paraules_Poemari extends PApplet {

    ComptadorParaules wc;
    ParaulesBuidesCatala sw;
    ArrayList<TermeFreq> termsFreqs;

    public static void main(String[] args) {
        PApplet.main("comptes.test.Test_Nigul_Paraules_Poemari");
    }

    public void settings(){
        size(1920, 1080);
    }

    public void setup(){

        sw = new ParaulesBuidesCatala();

        wc = new ComptadorParaules();

        String rutaPoemari = "C:\\Users\\tonim\\Documents\\CODE\\Poetica\\data\\poems\\Miquel Àngel Riera (1930)\\llibre de benaventurances\\";
        File carpetaPoemari = new File(rutaPoemari);
        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {
                wc.processaPoemaParaulesBuides(poema.getAbsoluteFile(), sw);
                System.out.println();
            }
        }

        termsFreqs = wc.getTermesFreqs();
        Collections.sort(termsFreqs, new Comparator<TermeFreq>(){
            @Override
            public int compare(TermeFreq o1, TermeFreq o2) {
                return (int)(o1.getFrequencia() - o2.getFrequencia());
            }
        });
    }

    int freqToColor(float normFreq) {
        // normFreq ∈ [0, 1]:  0 = poc freqüent · 1 = molt freqüent
        int[] palette = {
                color(100, 180, 220),   // blau clar   (baix)
                color( 80, 160, 130),   // verd menta
                color(240, 200,  60),   // groc daurat
                color(230, 120,  40),   // taronja
                color(200,  40,  60)    // vermell fosc (alt)
        };

        float scaled = normFreq * (palette.length - 1);
        int   idx    = (int) scaled;
        float t      = scaled - idx;

        if (idx >= palette.length - 1) return palette[palette.length - 1];

        // Interpolació lineal entre dos colors de la paleta
        return lerpColor(palette[idx], palette[idx + 1], t);
    }

    public void drawWordCloud(ArrayList<TermeFreq> termes, int numParaules, float minSize, float maxSize) {

        if (termes == null || termes.isEmpty()) return;

        // ── 1. Ordenar per freqüència descendent ──────────────────────────
        termes.sort((a, b) -> Float.compare(b.getFrequencia(), a.getFrequencia()));

        // ── 2. Calcular rang de freqüències per normalitzar ───────────────
        float maxFreq = termes.get(0).getFrequencia();
        //float minFreq = termes.get(termes.size() - 1).getFrequencia();
        float minFreq = termes.get(numParaules).getFrequencia();
        float logMax   = log(maxFreq + 1);
        float logMin   = log(minFreq + 1);   // +1 evita log(0)
        float freqRange = (logMax == logMin) ? 1 : (logMax - logMin);
        //float freqRange = (maxFreq == minFreq) ? 1 : (maxFreq - minFreq);

        // ── 3. Paràmetres de l'espiral de col·locació ─────────────────────
        float centerX      = width  / 2.0f;
        float centerY      = height / 2.0f;
        float spiralStep   = 0.05f;   // px per iteració de l'espiral
        float angleStep    = 0.005f;  // radians per iteració
        int   maxAttempts  = 100000;  // límit d'iteracions per paraula

        ArrayList<ParaulaNigul> placed = new ArrayList<>();

        background(15, 15, 25); // fons fosc
        textAlign(LEFT, TOP);

        // ── 4. Col·locar cada paraula ──────────────────────────────────────
        int nt=0;
        for (TermeFreq tf : termes) {
            //float normFreq = (tf.getFrequencia() - minFreq) / freqRange;
            float normFreq = (log(tf.getFrequencia() + 1) - logMin) / freqRange;
            float fs       = map(normFreq, 0, 1, minSize, maxSize);
            int col      = freqToColor(normFreq);

            textFont(createFont("Georgia", fs));
            textSize(fs);

            float wordW = textWidth(tf.getTerme());
            float wordH = fs * 1.2f; // ascendents + descendents aproximats

            // ── Espiral d'Arquimedes des del centre ────────────────────────
            float angle  = random(TWO_PI); // angle inicial aleatori → varietat
            float radius = 0;
            boolean positioned = false;

            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                float tryX = centerX + radius * cos(angle) - wordW / 2;
                float tryY = centerY + radius * sin(angle) - wordH / 2;

                // Comprova que la paraula cabria dins de la finestra
                if (tryX < 50 || tryY < 50 ||
                        tryX + wordW > width-50 || tryY + wordH > height-50) {
                    // Segueix espiral encara que surti: potser un angle diferent hi cap
                    radius += spiralStep;
                    angle  += angleStep;
                    continue;
                }

                // Comprova solapament amb totes les paraules ja col·locades
                boolean overlapping = false;
                for (ParaulaNigul pw : placed) {
                    if (pw.solapament(tryX, tryY, wordW, wordH)) {
                        overlapping = true;
                        break;
                    }
                }

                if (!overlapping) {
                    // ✅ Posició lliure trobada → dibuixar i registrar
                    placed.add(new ParaulaNigul(tf.getTerme(), tryX, tryY, wordW, wordH, fs, col));

                    // Ombra subtil per llegibilitat
                    fill(0, 0, 0, 80);
                    text(tf.getTerme(), tryX + 2, tryY + 2);

                    // Text principal
                    fill(col);
                    text(tf.getTerme(), tryX, tryY);

                    positioned = true;
                    break;
                }

                radius += spiralStep;
                angle  += angleStep;
            }

            if (!positioned) {
                println("Avís: no s'ha pogut col·locar '" + tf.getTerme() + "'");
            }

            nt++;
            if(nt>= numParaules){
                System.out.println("FINAL");
                break;
            }
        }

        dibuixaLlegenda((int)minFreq, (int)maxFreq, width/2 - 220/2f, height-90, 220, 18);
    }

    public void dibuixaLlegenda(int minOcc, int maxOcc, float x, float y, float barW, float barH) {

        final int   TICK_COUNT    = 5;    // nombre de marques intermèdies
        final float TICK_H        = 6;    // alçada de les marques (px)
        final float LABEL_OFFSET  = 10;   // separació etiqueta–barra (px)
        final float TITLE_OFFSET  = 18;   // separació títol–barra (px)
        final float FONT_SIZE_LBL = 11;
        final float FONT_SIZE_TTL = 12;


        // ── 2. Barra de gradient (escala logarítmica, igual que el núvol) ─────
        float logMin = log(minOcc + 1);
        float logMax = log(maxOcc + 1);

        for (int px = 0; px < (int) barW; px++) {
            float t        = px / barW;                           // posició lineal [0,1]
            float logVal   = logMin + t * (logMax - logMin);      // valor log interpolat
            float normFreq = (logVal - logMin) / (logMax == logMin ? 1 : logMax - logMin);
            stroke(freqToColor(normFreq));
            line(x + px, y, x + px, y + barH);
        }

        // ── 3. Marc de la barra ───────────────────────────────────────────────
        noFill();
        stroke(255, 255, 255, 60);
        strokeWeight(1);
        rect(x, y, barW, barH, 2);
        noStroke();

        // ── 4. Marques i etiquetes numèriques ─────────────────────────────────
        textFont(createFont("Georgia", FONT_SIZE_LBL));
        textSize(FONT_SIZE_LBL);
        textAlign(CENTER, TOP);

        for (int i = 0; i <= TICK_COUNT; i++) {
            float t        = i / (float) TICK_COUNT;
            float tickX    = x + t * barW;

            // Valor d'ocurrències corresponent a aquesta posició (escala log inversa)
            float logVal   = logMin + t * (logMax - logMin);
            int   occValue = round(exp(logVal) - 1);

            // Marca vertical
            stroke(255, 255, 255, 180);
            strokeWeight(1);
            line(tickX, y + barH, tickX, y + barH + TICK_H);
            noStroke();

            // Etiqueta numèrica
            fill(220, 220, 220);
            text(occValue, tickX, y + barH + LABEL_OFFSET);
        }

        // ── 5. Títol de la llegenda ───────────────────────────────────────────
        textFont(createFont("Georgia", FONT_SIZE_TTL));
        textSize(FONT_SIZE_TTL);
        textAlign(CENTER, BASELINE);
        fill(200, 200, 200);
        text("Ocurrències", x + barW / 2, y - TITLE_OFFSET * 0.3f);

        // Restaurar estat de text
        textAlign(LEFT, TOP);
        strokeWeight(1);
        noStroke();
    }

    public void draw(){
        background(255);
        drawWordCloud(termsFreqs, 150,24, 120);
        noLoop();
    }
}
