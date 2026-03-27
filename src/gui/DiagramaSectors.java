package gui;

import processing.core.PApplet;

import static processing.core.PConstants.TWO_PI;

public class DiagramaSectors extends Diagrama {

    Sector[] sectors;

    public DiagramaSectors(float x, float y, float d) {
        super(x, y, d, d);
    }

    public void setSectors(){

        sectors = new Sector[categories.length];
        float angle = 0;
        for(int i=0; i<sectors.length; i++){

            float minAngle = angle;
            float maxAngle = minAngle + percentages[i]/100f * TWO_PI;
            sectors[i] = new Sector(this.x, this.y, this.w, minAngle, maxAngle);
            sectors[i].setFonts(fonts);
            sectors[i].setCategoria(categories[i]);
            sectors[i].setValor(valors[i]);
            sectors[i].setPercentatge(percentages[i]);
            sectors[i].setColor(colors[i]);
            angle = maxAngle;
        }

    }

    // Dibuixa el Diagrama de Sectors

    public void display(PApplet p5){
        for(Sector s : sectors){
            s.display(p5);
        }
    }
}
