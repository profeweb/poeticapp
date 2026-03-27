package gui;

public class ElementDades extends GuiElement{

    String categoria;
    float valor, percentatge;
    int color;

    public ElementDades(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setPercentatge(float percentatge) {
        this.percentatge = percentatge;
    }

    public void setColor(int color){ this.color = color; }

}
