package gui;

import processing.core.PApplet;

public class GraellaTarja extends GuiElement {

    String[][] cardsData;    // Dades de les Cards
    Tarja[] cards;            // Cards
    int numCards;            // Número total de Cards
    int numRowsPage;
    int numCardsRow;
    int numCardsPage;        // Número de Cards en 1 Pàgina

    int numPage;
    int numTotalPages;

    float wc, hc, margin = 5;
    int selectedCard = -1;

    // Constructor
    public GraellaTarja(int numRows, int numCols, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.numRowsPage = numRows;
        this.numCardsRow = numCols;
        this.numCardsPage = numRows * numCols;
        this.numPage = 0;
        this.wc =( w - margin*(numCardsRow-1)) / numCardsRow;
        this.hc = (h - margin*(numRowsPage -1)) / numRowsPage;
    }

    // Setters

    public void setData(String[][] d) {
        this.cardsData = d;
        this.numCards = d.length;
        this.numTotalPages = d.length / this.numCardsPage;
    }

    public void setTarges() {

        cards = new Tarja[numCards];

        for(int numCard=0; numCard<cardsData.length; numCard++){

            int nr = (numCard / numCardsRow) % numRowsPage;
            int nc = numCard % numCardsRow;

            float yCard = y + (hc + margin) * nr;
            float xCard = x + (wc + margin)* nc;

            cards[numCard] = new Tarja(numCard, cardsData[numCard][0], cardsData[numCard][1], xCard, yCard, wc, hc);
            cards[numCard].setColors(colors);
            cards[numCard].setFonts(fonts);
        }

    }


    public void nextPage() {
        if (this.numPage<this.numTotalPages) {
            this.numPage++;
        }
    }

    public void prevPage() {
        if (this.numPage>0) {
            this.numPage--;
        }
    }

    // Dibuixa taula
    public void display(PApplet p5) {

        p5.pushStyle();

        // Dibuixa Cards corresponent a la Pàgina
        int firstCardPage = numCardsPage*numPage;
        int lastCardPage  = numCardsPage*(numPage+1) - 1;

        for(int numCard=0; numCard<cards.length; numCard++) {
            if(numCard>=firstCardPage && numCard<= lastCardPage) {
                if (numCard < this.numCards && cards[numCard] != null) {
                    cards[numCard].display(p5);
                }
            }
        }

        // Informació de la Pàgina
        p5.fill(0);
        p5.text("Pag: "+(this.numPage+1)+" / "+(this.numTotalPages+1), x + w + 50, y+10);

        p5.popStyle();
    }


}
