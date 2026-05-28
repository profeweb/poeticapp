package lema;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Lematitzador {

    // Retorna el resultat de la lematització per a un token a partir del diccionari de lemes
    public static ResultatToken lematitzarToken(String token, DiccionariLemesCatalà dicc) {

        // Cerca principal
        List<String> lemesInflectius = dicc.indexFormes.get(token);
        boolean      esLema          = dicc.conjuntLemes.contains(token);

        // Cerca sense diacrítics si no es troba per cap via
        if (lemesInflectius == null && !esLema) {
            String senseDiacritics = eliminarDiacritics(token);
            if (!senseDiacritics.equals(token)) {
                lemesInflectius = dicc.indexFormes.get(senseDiacritics);
                esLema          = dicc.conjuntLemes.contains(senseDiacritics);
            }
        }

        // Cas JA_ES_LEMA (prioritat sobre TROBAT/AMBIGU)
        if (esLema) {
            List<String> tots = new ArrayList<>();
            tots.add(token); // el lema és ell mateix
            if (lemesInflectius != null)
                lemesInflectius.stream()
                        .filter(l -> !l.equalsIgnoreCase(token))
                        .forEach(tots::add);
            return new ResultatToken(token, token, tots, ResultatToken.Estat.JA_ES_LEMA);
        }

        // Cas DESCONEGUT
        if (lemesInflectius == null || lemesInflectius.isEmpty())
            return new ResultatToken(token, token,
                    Collections.emptyList(), ResultatToken.Estat.DESCONEGUT);

        // Eliminar duplicats mantenint ordre
        List<String> unics = lemesInflectius.stream()
                .distinct().collect(Collectors.toList());

        ResultatToken.Estat estat = (unics.size() == 1) ? ResultatToken.Estat.TROBAT : ResultatToken.Estat.AMBIGU;
        return new ResultatToken(token, unics.get(0), unics, estat);
    }

    // Elimina els diacrítics d'una paraula
    private static String eliminarDiacritics(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}", "");
    }

}
