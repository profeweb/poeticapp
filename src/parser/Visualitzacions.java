package parser;

import processing.core.PApplet;

import static processing.core.PApplet.*;

public class Visualitzacions {


    public enum QUANTITAT { PARAULES, LLETRES, SILABES };

    // VISUALITZACIONS QUANTITATIVES *********************************************************************************

    public static void dibuixaVersLinea(PApplet p5, Vers vers, float x, float y, float gruixa, float wLinia, float w, int color, int quantitat){
        p5.pushStyle();

        // Línia
        p5.stroke(color);
        p5.strokeWeight(gruixa);
        p5.strokeCap(p5.ROUND);
        p5.line(x, y, x + wLinia, y);

        // Núm. vers
        if(vers.numVers%5==0) {
            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(vers.numVers, x - 5, y);
        }

        // Quantitat
        p5.fill(color);
        p5.textSize(12); p5.textAlign(p5.RIGHT, p5.CENTER);
        p5.text(quantitat, x + w - 30, y);
        p5.popStyle();
    }

    public static void dibuixaVersRadial(PApplet p5, Vers vers, float x, float  y, float radiShift, float angle, float radiQuant, float gruixa, int color, int quantitat){
        p5.pushStyle();

        // Línia
        p5.stroke(color);
        p5.strokeWeight(gruixa);
        p5.strokeCap(p5.ROUND);
        p5.line(x +  radiShift*cos(angle), y +  radiShift*sin(angle), x +  (radiShift + radiQuant)*cos(angle), y +  (radiShift + radiQuant)*sin(angle));

        // Núm. vers
        if(vers.numVers%5==0) {
            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.CENTER, p5.CENTER);
            p5.text(vers.numVers, x +  (radiShift - 15)*cos(angle), y +  (radiShift -15)*sin(angle));
        }

        // Quantitat
        p5.fill(color);
        p5.textSize(12); p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(quantitat, x +  (radiShift + radiQuant + 15)*cos(angle), y +  (radiShift + radiQuant + 15)*sin(angle));
        p5.popStyle();
    }

    public static void dibuixaEstrofaLinea(PApplet p5, Estrofa estrofa, float x, float y, float w, float h, int colorEstrofa, int quantitat){
        p5.pushStyle();

        // Capsa de l'estrofa
        p5.noStroke(); p5.fill(colorEstrofa);
        p5.rect(x, y, w, h, 10);
        p5.fill(colorEstrofa);

        // Número d'estrofa
        p5.fill(colorEstrofa);
        p5.textSize(28); p5.textAlign(p5.RIGHT, p5.BOTTOM);
        p5.text("E"+estrofa.numero, x-10, y + h/2 - 5);

        // Quantitat
        p5.fill(colorEstrofa);
        p5.textSize(12); p5.textAlign(p5.RIGHT, p5.TOP);
        p5.text(quantitat, x -15 , y + h/2 + 5);

        p5.popStyle();
    }

    public static void dibuixaEstrofaArc(PApplet p5, Estrofa estrofa, QUANTITAT quantitat, float x, float y, float radiMin, float radiMax, float angleMin, float angleMax, float mitjanaParaules, int maxParaulesVersPoema, int colorEstrofa, int colorVers){

        float migAngle = (angleMin + angleMax) / 2f;
        float migRadi = (radiMin + radiMax) /2f;
        float margeAng = PI/100f;
        float numPasses = 20;

        p5.pushStyle();
        p5.fill(colorEstrofa);
        p5.strokeWeight(1.5f);
        p5.beginShape();
        for(float i=0; i<=numPasses; i++) {
            float angle = p5.lerp(angleMin + margeAng/2f, angleMax - margeAng/2f, i/numPasses);
            p5.vertex(x + radiMin * cos(angle), y + radiMin*sin(angle));
        }

        for(float i=0; i<=numPasses; i++) {
            float angle = p5.lerp(angleMax - margeAng/2f, angleMin + margeAng/2f, i/numPasses);
            p5.vertex(x + radiMax * cos(angle), y + radiMax*sin(angle));
        }
        p5.endShape(p5.CLOSE);

        // Text a,n múmero d'estrofa
        p5.fill(0); p5.textAlign(p5.CENTER, p5.CENTER); p5.textSize(18);
        p5.text("E" + estrofa.numero, x + migRadi * cos(migAngle), y + migRadi*sin(migAngle));

        // Text amb número de versos de l'estrofa
        p5.fill(50); p5.textAlign(p5.CENTER, p5.CENTER); p5.textSize(14);
        p5.text(estrofa.getNumVersos(), x + (radiMin -15) * cos(migAngle), y + (radiMin -15)*sin(migAngle));

        for(Vers vers : estrofa.getVersos()){
            float angle = (angleMax + angleMin)/2f;
            if(estrofa.getPrimerVersEstrofa() != estrofa.getDarrerVersEstrofa()) {
                angle = p5.map(vers.numVers, estrofa.getPrimerVersEstrofa(), estrofa.getDarrerVersEstrofa(), angleMin + margeAng / 2f, angleMax - margeAng / 2f);
            }
            float q = quantitat == QUANTITAT.PARAULES ? vers.getNumParaules() : vers.getNumLletres();
            float radiQuant = p5.map(q, 0, maxParaulesVersPoema, 0, 300);
            float gruixa = q >= mitjanaParaules ? 2.5f : 1f;
            int colorRadi = q >= mitjanaParaules ? p5.color(0) : colorVers;
            if(q==1){ colorRadi = p5.color(255); }
            dibuixaVersRadial(p5, vers, x, y, radiMax, angle, radiQuant, gruixa, colorRadi, (int) q);
        }


        float mitjanaEstrofa = quantitat == QUANTITAT.PARAULES ? estrofa.getMitjanaParaulesVersosEstrofa() : estrofa.getMitjanaLletresVersosEstrofa();

        p5.noFill(); p5.stroke(0, 255, 0);
        float radiMitjana2 = p5.map(mitjanaEstrofa, 0, maxParaulesVersPoema, radiMax, radiMax + 300);
        p5.beginShape();
        for(int i=0; i<=25; i++){
            float angleV = p5.lerp(angleMin, angleMax, i/25f);
            p5.vertex(x + radiMitjana2*cos(angleV), y + radiMitjana2*sin(angleV));
        }
        p5.endShape();

        p5.popStyle();
    }


    // BLOCS: números de paraules

    public static void dibuixaNumParaulesPoemariBlocs(PApplet p5, Poemari poemari, float x, float y, float w, float h, int colorEstrofa, int colorVers){

        float mitjanaParaulesPoemari = poemari.getMitjanaParaulesVersosPoemari();
        int maxParaules = poemari.getMaxParaulesVersosPoemari();
        float xPoema = x;
        for(Poema poema : poemari.poemes){
            dibuixaNumParaulesEstrofesBloc(p5, poema, xPoema, y + 80 , w, h, colorEstrofa, colorVers, mitjanaParaulesPoemari, poema.getMitjanaParaulesVersPoema(), maxParaules);
            xPoema += w + 75;
        }

        p5.pushStyle();
        p5.fill(0); p5.textSize(48);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(poemari.titol, (x + xPoema)/2f, y);

        // Dibuixa Text de Mitjana per Poemari
        p5.fill(255, 0, 0); p5.textSize(18);
        p5.text(nf(mitjanaParaulesPoemari, 0, 2) + " paraules / vers (llibre)", (x + xPoema)/2f, y + 24);
        p5.popStyle();
    }

    public static void dibuixaNumParaulesEstrofesBloc(PApplet p5, Poema poema, float x, float y, float w, float h, int colorEstrofa, int colorVers, float mitjanaPoemari, float mitjanaPoema, int maxParaulesVers){

        p5.pushStyle();
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text("P" + poema.numero, x + w/2, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(poema.titol.substring(0, 15) + "...", x + w/2, y);
        p5.textSize(14); p5.fill(0, 0, 255);
        p5.text(nf(mitjanaPoema, 0, 2) +" paraules / vers (poema)", x + w/2, y + 25);

        float xMitjaPoemari = x + 25 + p5.map(mitjanaPoemari, 0, maxParaulesVers, 0, w-50);
        float xMitjaPoema = x + 25 + p5.map(mitjanaPoema, 0, maxParaulesVers, 0, w-50);

        float yEstrofa = y + 25;
        for(Estrofa estrofa : poema.getEstrofes()){

            float mitjanaEstrofa = estrofa.getMitjanaParaulesVersosEstrofa();

            dibuixaNumParaulesVersosEstrofaLinia(p5, estrofa, x, yEstrofa, w, h, colorEstrofa, colorVers, maxParaulesVers, mitjanaPoemari);

            p5.stroke(255, 0, 0);
            p5.line(xMitjaPoemari, yEstrofa, xMitjaPoemari, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 0, 255);
            p5.line(xMitjaPoema, yEstrofa, xMitjaPoema, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 255, 0);
            float xMitjaEstrofa = x + 25 + p5.map(mitjanaEstrofa, 0, maxParaulesVers, 0, w-50);
            p5.line(xMitjaEstrofa, yEstrofa, xMitjaEstrofa, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.fill(0, 255, 0); p5.textSize(14);
            p5.textAlign(p5.CENTER, p5.BOTTOM);
            p5.text(nf(mitjanaEstrofa, 0, 2) + " paraules / vers (estrofa)", x + w/2, yEstrofa + h * (estrofa.getNumVersos() + 1) + 25);

            yEstrofa += (h * estrofa.getNumVersos() + 1) + 50;
        }

        p5.popStyle();
    }


    // BLOCS: números de lletres

    public static void dibuixaNumLletresPoemariBlocs(PApplet p5, Poemari poemari, float x, float y, float w, float h, int colorEstrofa, int colorVers){

        float mitjanaLletresPoemari = poemari.getMitjanaLletresVersosPoemari();
        int maxLletres = poemari.getMaxLletresVersosPoemari();
        float xPoema = x;
        for(Poema poema : poemari.poemes){
            dibuixaNumLletresEstrofesBloc(p5, poema, xPoema, y + 80 , w, h, colorEstrofa, colorVers, mitjanaLletresPoemari, poema.getMitjanaLletresVersPoema(), maxLletres);
            xPoema += w + 75;
        }

        p5.pushStyle();
        p5.fill(0); p5.textSize(48);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text(poemari.titol, (x + xPoema)/2f, y);

        // Dibuixa Text de Mitjana per Poemari
        p5.fill(255, 0, 0); p5.textSize(18);
        p5.text(nf(mitjanaLletresPoemari, 0, 2) + " lletres / vers (llibre)", (x + xPoema)/2f, y + 24);
        p5.popStyle();
    }

    public static void dibuixaNumLletresEstrofesBloc(PApplet p5, Poema poema, float x, float y, float w, float h, int colorEstrofa, int colorVers, float mitjanaPoemari, float mitjanaPoema, int maxLletresVers){

        p5.pushStyle();
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.BOTTOM);
        p5.text("P" + poema.numero, x + w/2, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(poema.titol.substring(0, 15) + "...", x + w/2, y);
        p5.textSize(14); p5.fill(0, 0, 255);
        p5.text(nf(mitjanaPoema, 0, 2) +" lletres / vers (poema)", x + w/2, y + 25);

        float xMitjaPoemari = x + 25 + p5.map(mitjanaPoemari, 0, maxLletresVers, 0, w-50);
        float xMitjaPoema = x + 25 + p5.map(mitjanaPoema, 0, maxLletresVers, 0, w-50);

        float yEstrofa = y + 25;
        for(Estrofa estrofa : poema.getEstrofes()){

            float mitjanaEstrofa = estrofa.getMitjanaLletresVersosEstrofa();

            dibuixaNumLletresVersosEstrofaLinia(p5, estrofa, x, yEstrofa, w, h, colorEstrofa, colorVers, maxLletresVers, mitjanaPoemari);

            p5.stroke(255, 0, 0);
            p5.line(xMitjaPoemari, yEstrofa, xMitjaPoemari, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 0, 255);
            p5.line(xMitjaPoema, yEstrofa, xMitjaPoema, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.stroke(0, 255, 0);
            float xMitjaEstrofa = x + 25 + p5.map(mitjanaEstrofa, 0, maxLletresVers, 0, w-50);
            p5.line(xMitjaEstrofa, yEstrofa, xMitjaEstrofa, yEstrofa + h * (estrofa.getNumVersos() + 1));

            p5.fill(0, 255, 0); p5.textSize(14);
            p5.textAlign(p5.CENTER, p5.BOTTOM);
            p5.text(nf(mitjanaEstrofa, 0, 2) + " lletres / vers (estrofa)", x + w/2, yEstrofa + h * (estrofa.getNumVersos() + 1) + 25);

            yEstrofa += (h * estrofa.getNumVersos() + 1) + 50;
        }

        p5.popStyle();
    }

    public static void dibuixaNumLletresVersosEstrofaLinia(PApplet p5, Estrofa estrofa, float x, float y, float w, float h, int colorEstrofa, int colorVers, int maxLletresVersPoema, float mitjanaLletres){

        float marge = 25;

        float he = h * (estrofa.getNumVersos() + 1);
        dibuixaEstrofaLinea(p5, estrofa, x, y, w, he, colorEstrofa, estrofa.getNumVersos());

        int numVers = 1;
        for(Vers vers : estrofa.getVersos()){
            float wLinia = p5.map(vers.getNumLletres(), 0, maxLletresVersPoema, 0, w - 2*marge);
            float gruixa = vers.getNumLletres() >= mitjanaLletres ? 3 : 1.5f;
            int colorLina = vers.getNumLletres() == maxLletresVersPoema ? p5.color(0) : colorVers;
            dibuixaVersLinea(p5, vers, x + marge , y + h*numVers, gruixa, wLinia, w, colorLina, vers.getNumLletres());
            numVers++;
        }
    }

    // RADIAL: número de paraules
    public static void dibuixaNumParaulesVersosEstrofaLinia(PApplet p5, Estrofa estrofa, float x, float y, float w, float h, int colorEstrofa, int colorVers, int maxParaulesVersPoema, float mitjanaParaules){

        float marge = 25;

        float he = h * (estrofa.getNumVersos() + 1);
        dibuixaEstrofaLinea(p5, estrofa, x, y, w, he, colorEstrofa, estrofa.getNumVersos());

        int numVers = 1;
        for(Vers vers : estrofa.getVersos()){
            float wLinia = p5.map(vers.getNumParaules(), 0, maxParaulesVersPoema, 0, w - 2*marge);
            float gruixa = vers.getNumParaules() >= mitjanaParaules ? 3 : 1.5f;
            int colorLina = vers.getNumParaules() == maxParaulesVersPoema ? p5.color(0) : colorVers;
            dibuixaVersLinea(p5, vers, x + marge , y + h*numVers, gruixa, wLinia, w, colorLina, vers.getNumParaules());
            numVers++;
        }
    }

    public static void dibuixaEstrofesArc(PApplet p5, Poema poema, QUANTITAT quantitat, float x, float y, float minRadi, float maxRadi, float angInici, float angFi, float mitjanaPoemari, int maxQuantitatVers, int[] colorEstrofa, int colorVers){

        p5.pushStyle();

        // Número i títol del Poema
        p5.textSize(24); p5.fill(0);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text("P" + poema.numero, x, y - 25);
        p5.textSize(18); p5.fill(100);
        p5.text(poema.titol.substring(0, min(15, poema.titol.length())) + "...", x, y);

        p5.noFill(); p5.stroke(0);
        p5.circle(x, y, minRadi*1.5f);

        float angleVers = (angFi - angInici) / poema.getNumVersos();
        float angle = angInici;
        int numEstrofa = 0;
        for(Estrofa estrofa : poema.estrofes){
            float angleEstrofa = angleVers * estrofa.getNumVersos();
            dibuixaEstrofaArc(p5, estrofa, quantitat, x, y, minRadi, maxRadi, angle , angle +  angleEstrofa, mitjanaPoemari, maxQuantitatVers, colorEstrofa[numEstrofa], colorVers);
            angle += (angleEstrofa);
            numEstrofa++;
        }
        p5.popStyle();

        // Dibuixa la mitjana  a nivell de poemari
        p5.noFill(); p5.stroke(255, 0, 0);
        float radiMitjana = p5.map(mitjanaPoemari, 0, maxQuantitatVers, maxRadi, maxRadi + 300);
        p5.beginShape();
        for(int i=0; i<=25; i++){
            float angleV = p5.lerp(angInici, angFi, i/25f);
            p5.vertex(x + radiMitjana*cos(angleV), y + radiMitjana*sin(angleV));
        }
        p5.endShape();


        // Dibuixa la mitjana a nivell de poema
        float mitjanaPoema = quantitat == QUANTITAT.PARAULES ? poema.getMitjanaParaulesVersPoema() : poema.getMitjanaLletresVersPoema();
        p5.fill(0, 0, 255);
        String etiqueta = quantitat == QUANTITAT.PARAULES ? "paraules" : "lletres";
        p5.text(mitjanaPoema + " " + etiqueta + " / vers (poema)", 100, p5.height-100);

        p5.noFill(); p5.stroke(0, 0, 255);
        float radiMitjana2 = p5.map(mitjanaPoema, 0, maxQuantitatVers, maxRadi, maxRadi + 300);
        p5.beginShape();
        for(int i=0; i<=25; i++){
            float angleV = p5.lerp(angInici, angFi, i/25f);
            p5.vertex(x + radiMitjana2*cos(angleV), y + radiMitjana2*sin(angleV));
        }
        p5.endShape();

    }

}
