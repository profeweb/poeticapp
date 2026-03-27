package gui;

public class Diagrama extends GuiElement{

    // Informació del diagrama (textos, valors, percentatges i colors)
    String[] texts;
    float[] values, percentages;
    int[] colors;

    // Valors estadístics
    float total;
    float minValue, maxValue;

    public Diagrama(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setTexts(String[] t){
        this.texts = t;
    }

    public void setValues(float[] v){
        this.values = v;
        this.total = 0;
        this.maxValue = this.values[0];
        this.minValue = this.values[0];
        for(int i=0; i<values.length; i++){
            this.total += this.values[i];
            if(this.values[i]>maxValue){
                maxValue = this.values[i];
            }
            if(this.values[i]<minValue){
                minValue = this.values[i];
            }
        }
    }

    public void setPercentages(){
        this.percentages = new float[values.length];
        for(int i=0; i<percentages.length; i++){
            this.percentages[i] = (this.values[i] / this.total)*100f;
        }
    }

    public void setColors(int[] c){
        this.colors = c;
    }


}
