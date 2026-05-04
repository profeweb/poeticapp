package parser;

public class DadesPoema {


    public static float mitjanaParaulesVersosPoema(Poema poema){
        float numVersos = poema.getNumVersos();
        float sumaParaules = 0;
        for(Estrofa estrofa: poema.estrofes){
            for(Vers vers : estrofa.getVersos()){
                sumaParaules += vers.getNumParaules();
            }
        }
        return sumaParaules / numVersos;
    }

    public static float mitjanaLletresVersosPoema(Poema poema){
        float numVersos = poema.getNumVersos();
        float sumaCaracters = 0;
        for(Estrofa estrofa: poema.estrofes){
            for(Vers vers : estrofa.getVersos()){
                sumaCaracters += vers.getNumLletres();
            }
        }
        return sumaCaracters / numVersos;
    }

    public static float mitjanaSilabesVersosPoema(Poema poema){
        float numVersos = poema.getNumVersos();
        float sumaSilabes = 0;
        for(Estrofa estrofa: poema.estrofes){
            for(Vers vers : estrofa.getVersos()){
                for(Token token : vers.tokens){
                    if(token instanceof Paraula){
                        sumaSilabes += ((Paraula)token).getNumSilabes();
                    }
                }
            }
        }
        return sumaSilabes / numVersos;
    }

}
