package parser;

import java.util.ArrayList;

public class Estrofa {

    int posicio;
    int numLletres;
    ArrayList<Seccio> seccions;

    public Estrofa(int posicio) {
        this.posicio = posicio;
        this.seccions = new ArrayList<>();
    }
}
