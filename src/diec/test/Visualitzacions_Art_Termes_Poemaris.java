package diec.test;

import parser.Autor;
import parser.DadesPoemaris;
import parser.Poemari;
import processing.core.PApplet;

import java.util.ArrayList;

public class Visualitzacions_Art_Termes_Poemaris extends PApplet {

    int numPoemari =  0;
    Poemari poemari;
    ArrayList<Poemari> poemaris;
    boolean exportaPDF = false;
    
    public static String[][] termesArt = {
            // Biografia: 12 / 16
            {"color", "pedra" , "paret" , "finestra" , "retrat", "disseny" , "vasa" , "panorama" , "decorada" , "temple" , "portal" , "porta"},
            // El pis de la badia: 20 / 24
            {"burí" , "color" , "fris" , "pedra" , "parets" , "finestra" , "creativa" , "valors" , "mur" , "matisos" , "cloquer" , "llindar" , "temple", "esbossar" , "quadrícula" , "vitralls" , "torre" , "calcant" , "porta" , "decora"},
            // La bellesa de l'home: 18 / 20
            {"façana" , "pedra" , "paret" , "valor" , "pintats" , "mur" , "pedres" , "dissenys" , "ampolla" , "imatge" , "matisos" , "àmfores" , "panorama" , "autoretrat" , "portal" , "dibuix" , "porta" , "decora"},
            // Llibre de Benaventurances: 15
            {"color" , "imatges" , "pedra" , "façana" , "valor" , "llindars" , "mur" , "disseny" , "imatge" , "panorama" , "altar" , "decorada" , "portal" , "decora"},
            // Paràbola i clam de la cosa humana: 20
            {"color" , "pedra" , "façana" , "paret" , "finestra" , "matís" , "mur" , "càntirs" , "disseny" , "decorant" , "panorama" , "decorau" , "murs" , "estàtua" , "modelar" , "siluetes" , "perspectiva" , "dissenyar" , "cambril"},
            // Poemes a Nai: 4
            {"finestrals" , "pintar" , "circ" , "porta" },
            // Poemes de l'enyorament: 3/ 4
            {"color", "pedra", "murs"},
            // Poemes ocasionals: 13 / 15
            {"estampa", "mascarons" , "color" , "jardí" , "ceràmic" , "pedra" , "mur", "dissenys"  , "panorama" , "argila" , "llindar" , "dibuix" , "porta"},
    };

    public static void main(String[] args) {
        PApplet.main("diec.test.Visualitzacions_Art_Termes_Poemaris");
    }

    public void settings(){ size(1920, 1080);}

    public void setup(){
        ArrayList<Autor> autors = DadesPoemaris.carregaPoemarisAutors();
        poemaris = autors.get(0).getPoemaris();
        poemari = poemaris.get(numPoemari);
    }

    public void draw(){
        background(255);

        if(exportaPDF){
            String nomPDF = "data/pdfs/" + poemari.getTitol() +" - termes relacionats amb ART.pdf";
            beginRecord(PDF, nomPDF);
        }

        dibuixaRelacions(this, width/2, height/2, 200, poemari.getTitol() + "(" + poemari.getAny()+")", "ART");

        if(exportaPDF){
            endRecord();
            exportaPDF = false;
        }
    }

    public void dibuixaRelacions(PApplet p5, float x, float y, float d, String titolPoemari, String campTematic){

        p5.fill(255);
        p5.strokeWeight(2f);
        p5.circle(x, y, d);

        p5.fill(0);
        p5.textAlign(p5.LEFT, p5.TOP);
        p5.textSize(18);
        p5.text(titolPoemari, x - d/2 + 10, y - 50, d-15, 150);
        p5.textSize(36);
        p5.textAlign(p5.CENTER, p5.CENTER);
        p5.text(campTematic, x, y + 20);

        float angBot = TWO_PI / termesArt[numPoemari].length;
        float ang = 0;
        for(int i=0; i< termesArt[numPoemari].length; i++){
            float xp = x + (1.3f*d) * cos(ang);
            float yp = y + (1.3f*d) * sin(ang);
            p5.textSize(34); p5.fill(0);
            p5.textAlign(p5.CENTER, p5.CENTER);
            p5.text(termesArt[numPoemari][i], xp, yp);


            int numOcurrenciesTerme = poemari.getNumOcurrenciesTerme(termesArt[numPoemari][i]);

            p5.textSize(18);
            p5.text(numOcurrenciesTerme, x + (d/1.6f) * cos(ang), y + (d/1.6f) * sin(ang));
            p5.line(x + (0.7f*d) * cos(ang), y + (0.7f*d) * sin(ang), x + (1.0f*d) * cos(ang), y + (1.0f * d) * sin(ang));

            ArrayList<Integer> numVersos = poemari.getVersosOcurrenciesTerme(termesArt[numPoemari][i]);
            float angEntreVersos = PI/10f;
            float angVers = ang - (numVersos.size()/2) * angEntreVersos + (numVersos.size()%2==0? angEntreVersos/2f : 0f);
            for(int v=0; v<numVersos.size(); v++){
                p5.line(xp + 50 * cos(ang), yp + 50 * sin(ang), xp + (150) * cos(angVers), yp + (150) * sin(angVers));

                p5.fill(0);
                p5.circle(xp + (180) * cos(angVers), yp + (180) * sin(angVers), 50);

                p5.fill(255);
                p5.text(numVersos.get(v), xp + (180) * cos(angVers), yp + (180) * sin(angVers));
                angVers += angEntreVersos;
            }


            ang += angBot;
        }
    }


    public void keyPressed(){

        if(key=='s' || key=='S'){
            exportaPDF = true;
        }

        if(keyCode == UP && numPoemari < poemaris.size()-1){
            numPoemari++;
            poemari = poemaris.get(numPoemari);
        }
        else if(keyCode == DOWN && numPoemari > 0){
            numPoemari--;
            poemari = poemaris.get(numPoemari);
        }
    }
}
