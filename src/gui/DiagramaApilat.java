package gui;

public class DiagramaApilat extends GuiElement{

    // Informació del diagrama (textos, valors, percentatges i colors)
    String[] categories, piles;
    float[][] valors, percentages;
    int[] colorsCategories;

    // Valors estadístics
    float[] totalValors;
    float minValor, maxValor, mitjanaValors;

    public DiagramaApilat(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setPiles(String[] t){
        this.piles = t;
    }

    public void setCategories(String[] t){
        this.categories = t;
    }

    public void setValors(float[][] v){

        valors = v;
        totalValors =  new float[v.length];

        maxValor = 0;
        minValor = 0;
        mitjanaValors = 0;

        for(int pila = 0; pila< valors.length; pila++){
            totalValors[pila] = 0;
            for(int categoria=0; categoria<valors[pila].length; categoria++){
                totalValors[pila] += valors[pila][categoria];

                if(valors[pila][categoria]> maxValor){
                    maxValor = valors[pila][categoria];
                }
                if(valors[pila][categoria]< minValor){
                    minValor = valors[pila][categoria];
                }
                mitjanaValors += valors[pila][categoria];
            }

        }

        mitjanaValors = mitjanaValors / (valors.length * valors[0].length);

        setPercentages();
    }

    public void setColorCategories(int[] colorsCategories){ this.colorsCategories = colorsCategories; }

    public void setPercentages(){
        this.percentages = new float[valors.length][valors[0].length];
        for(int pila = 0; pila< valors.length; pila++) {
            for (int categoria = 0; categoria < valors[pila].length; categoria++) {
                percentages[pila][categoria] = valors[pila][categoria] / totalValors[pila];
            }
        }
    }

    public void setColorsCategories(int[] c){
        this.colorsCategories = c;
    }


}
