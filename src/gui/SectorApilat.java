package gui;

import processing.core.PApplet;

import static processing.core.PConstants.TWO_PI;

public class SectorApilat extends Sector{

    Sector[] subSectors;
    float[] subPercentatges;

    public SectorApilat(float x, float y, float w, float minAngle, float maxAngle) {
        super(x, y, w, minAngle, maxAngle);
    }

    public void setSubPercentages(int totalSector, float[] valorsSubsectors){
        this.subPercentatges = new float[subSectors.length];
        for(int i=0; i<subPercentatges.length; i++){
            this.subPercentatges[i] = (valorsSubsectors[i] / totalSector)*100f;
        }
    }

    public void setSubSectors(int totalSector, float[] valorsSubsectors){

        setSubPercentages(totalSector, valorsSubsectors);

        subSectors = new Sector[valorsSubsectors.length];
        float angle = minAngle;
        for(int i=0; i<subSectors.length; i++){

            float minAngleSubSector = angle;
            float maxAngle = minAngleSubSector + subPercentatges[i]/100f * (this.maxAngle - this.minAngle);
            subSectors[i] = new Sector(this.x, this.y, this.w, minAngleSubSector, maxAngle);
            subSectors[i].setFonts(fonts);
            subSectors[i].setCategoria(subSectors[i].categoria);
            subSectors[i].setValor(subSectors[i].valor);
            subSectors[i].setPercentatge(subSectors[i].percentatge);
            subSectors[i].setColor(subSectors[i].color);
            angle = maxAngle;
        }
    }

    public void display(PApplet p5){
        for(Sector subSector : subSectors){
            subSector.display(p5);
        }
    }
}
