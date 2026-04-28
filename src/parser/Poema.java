package parser;

import java.util.ArrayList;

public class Poema {

    String titol;
    int numero;
    ArrayList<Estrofa> estrofes;

    public Poema(String titol, int numero) {
        this.titol = titol;
        this.numero = numero;
        this.estrofes = new ArrayList<>();
    }
}
