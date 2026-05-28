package parser;

public class DadesPoema {

    // Retorna array amb el número de paraules en els versos d'una estrofa
    public static int[] numParaulesVersosEstrofa(Estrofa estrofa){
        int[] numParaules = new int[estrofa.getNumVersos()];
        for(int v=0; v<estrofa.getNumVersos(); v++){
            numParaules[v] = estrofa.getVersAt(v).getNumParaules();
        }
        return numParaules;
    }

    // Retorna la mitjana del número de paraules en els versos d'una estrofa
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

    // Retorna la mitjana del número de lletres en els versos d'una estrofa
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

    // Retorna la mitjana del número de síl·labes en els versos d'una estrofa
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
