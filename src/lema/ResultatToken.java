package lema;

import java.util.List;
import java.util.stream.Collectors;

public class ResultatToken {

    enum Estat { TROBAT, AMBIGU, JA_ES_LEMA, DESCONEGUT }

    final String       forma;           // Forma normalitzada del tokem
    final String       lemaPreferit;    // Lema escollit
    final List<String> lemes;           // Conjunt de tots els lemes possibles
    final Estat        estat;           // Resultat de la lematització

    public ResultatToken(String forma, String lemaPreferit, List<String> lemes, Estat estat) {
        this.forma        = forma;
        this.lemaPreferit = lemaPreferit;
        this.lemes        = lemes;
        this.estat        = estat;
    }

    public String toString(){
        if(estat==Estat.AMBIGU){
            return lemaPreferit + " (" + estat + ") : " + lemes.stream().collect(Collectors.joining(", "))+".";
        }
        else {
            return lemaPreferit + " (" + estat + ")";
        }
    }
}
