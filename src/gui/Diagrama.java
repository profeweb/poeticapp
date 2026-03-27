package gui;

public class Diagrama extends GuiElement{

    // Informació del diagrama (textos, valors, percentatges i colors)
    String[] categories;
    float[] valors, percentages;
    int[] colors;

    // Valors estadístics
    float totalValors, minValor, maxValor, mitjanaValors;

    public Diagrama(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setCategories(String[] t){
        this.categories = t;
    }

    public void setValors(float[] v){
        this.valors = v;
        this.totalValors = 0;
        this.maxValor = this.valors[0];
        this.minValor = this.valors[0];
        for(int i = 0; i< valors.length; i++){
            this.totalValors += this.valors[i];
            if(this.valors[i]> maxValor){
                maxValor = this.valors[i];
            }
            if(this.valors[i]< minValor){
                minValor = this.valors[i];
            }
        }
        this.mitjanaValors = this.totalValors / valors.length;

        setPercentages();
    }

    public void setPercentages(){
        this.percentages = new float[valors.length];
        for(int i=0; i<percentages.length; i++){
            this.percentages[i] = (this.valors[i] / this.totalValors)*100f;
        }
    }

    public void setColors(int[] c){
        this.colors = c;
    }


}
