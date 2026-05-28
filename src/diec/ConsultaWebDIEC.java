package diec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ConsultaWebDIEC {

    // Paràmetres de la petició HTTP
    private static final String USER_AGENT      = "Mozilla/5.0";
    private static final String GET_URL_TERME   = "https://dlc.iec.cat/Results?DecEntradaText=";
    private static final String GET_URL_ID      = "https://dlc.iec.cat/Results/PrintAccepcio?id=";

    // Retorna la connexió HTTP al DIEC per a la cerca del terme
    private static HttpURLConnection creaConnexioHttpTermeDIEC(String terme) throws IOException{
        StringBuilder termeCodificat = new StringBuilder(URLEncoder.encode(terme, "UTF-8"));
        URL urlTerme = new URL(GET_URL_TERME + termeCodificat);
        HttpURLConnection connexio = (HttpURLConnection) urlTerme.openConnection();
        connexio.setRequestMethod("GET");
        connexio.setRequestProperty("User-Agent", USER_AGENT);
        return connexio;
    }

    // Retorna la connexió HTTP al DIEC per a la cerca a partir del identificador del terme
    private static HttpURLConnection creaConnexioHttpIdTermeDIEC(String idTerme) throws IOException{
        URL urlID = new URL(GET_URL_ID + idTerme);
        HttpURLConnection connexio = (HttpURLConnection) urlID.openConnection();
        connexio.setRequestMethod("GET");
        connexio.setRequestProperty("User-Agent", USER_AGENT);
        return connexio;
    }

    // Retorna l'identificador del terme mitjançant connexió HTTP al DIEC
    private static String obteIdTerme(String terme) throws IOException {

        // Dissenya la petició HTTP al DIEC
        HttpURLConnection connexio = creaConnexioHttpTermeDIEC(terme);
        int codiResposta = connexio.getResponseCode();
        //System.out.println("GET Response Code :: " + responseCode);

        // Si la resposta a la petició HTPP és un èxit
        if (codiResposta == HttpURLConnection.HTTP_OK) { // success

            // Llegeix la reposta línia per línia
            String respostaHTTP = obteTextRespostaHTTP(connexio);

            String sortidaRespostaTerme     = respostaHTTP.toString();
            String sortidaRespostaIndexos   = respostaHTTP.toString();
            //System.out.println(sortidaRespostaTerme);

            // Cerca les posicions on apareixen les definicions dels termes
            String paraulaClau = "onclick=\"GetDefinition('";
            ArrayList<Integer> indexos = new ArrayList<>();
            int caractersEliminats = 0;
            while(sortidaRespostaIndexos.indexOf(paraulaClau)!=-1){
                int index = sortidaRespostaIndexos.indexOf(paraulaClau);
                indexos.add(index +  caractersEliminats);
                sortidaRespostaIndexos = sortidaRespostaIndexos.substring(index + paraulaClau.length());
                caractersEliminats += index + paraulaClau.length();
            }

            // Trobada
            if(indexos.size()>0) {

                // Apareixen més d'un terme (derivats)
                String idTerme = null;
                for (int i = 0; i < indexos.size(); i++) {

                    String part = sortidaRespostaTerme.substring(indexos.get(i));
                    int indexIniciTerme = part.indexOf(">");
                    int indexFinalTerme = part.indexOf("</a>");
                    String termeTrobat = part.substring(indexIniciTerme + 1, indexFinalTerme).trim();
                    //System.out.println("CHECKING WORD: " + termeTrobat);

                    // Comprova que és el terme cercat (i no un derivat).
                    if (termeTrobat.equals(terme)) {
                        int indexIniciID = indexos.get(i) + paraulaClau.length();
                        int indexFinalID = sortidaRespostaTerme.substring(indexIniciID).indexOf("')");
                        idTerme = sortidaRespostaTerme.substring(indexIniciID, indexIniciID + indexFinalID);
                        //System.out.println("ID WORD: " + idTerme);
                        break;
                    }
                }

                // Si no l'ha trobat, ho comprova amb superíndexos
                if (idTerme == null) {

                    for (int i = 0; i < indexos.size(); i++) {
                        String part = sortidaRespostaTerme.substring(indexos.get(i));
                        int indexIniciTerme = part.indexOf(">");
                        int indexFinalTerme = part.indexOf("</a>");
                        String termeTrobat = part.substring(indexIniciTerme + 1, indexFinalTerme);
                        //System.out.println("CHECKING WORD: " + termeTrobat);
                        if (termeTrobat.equals(terme + "¹")) {
                            int indexIniciID = indexos.get(i) + paraulaClau.length();
                            int indexFinalID = sortidaRespostaTerme.substring(indexIniciID).indexOf("')");
                            idTerme = sortidaRespostaTerme.substring(indexIniciID, indexIniciID + indexFinalID);
                            //System.out.println("ID WORD: " + idTerme);
                            break;
                        }
                    }
                }

                // Si no l'ha trobat, ho comprova amb plurals o derivats (n'gafa el 1r).
                if (idTerme == null) {
                    int indexIniciID = indexos.get(0) + paraulaClau.length();
                    int indexFinalID = sortidaRespostaTerme.substring(indexIniciID).indexOf("')");
                    idTerme = sortidaRespostaTerme.substring(indexIniciID, indexIniciID + indexFinalID);
                }
                return idTerme;
            }
            return null;
        }
        else {
            return null;
        }
    }

    // Retorna el significat d'un terme a través de consulta HTTP al DIEC
    public static String obteSignificatTerme(String terme) throws IOException{

        // Obté l'identificador del terme de cerca
        String idTerme = obteIdTerme(terme);

        if(idTerme!=null){
            // Crea la petició HTTP al DIEC
            HttpURLConnection connexio = creaConnexioHttpIdTermeDIEC(idTerme);
            int codiResposta = connexio.getResponseCode();
            //System.out.println("GET Response Code :: " + codiResposta);

            // Si la resposta és d'èxit
            if (codiResposta == HttpURLConnection.HTTP_OK) { // success

                // Llegeix la reposta línia per línia
                String respostaHTTP = obteTextRespostaHTTP(connexio);

                // Descarta les línies que no contenen la definició del terme
                String sortidaResposta = respostaHTTP.replace("<br xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">", "\n");
                int indexIniciDefinicio = sortidaResposta.indexOf("<div id=\"Definition\" class=\"resultDefinition\">");
                int indexFinalDefinicio = sortidaResposta.substring(indexIniciDefinicio).indexOf("</div");
                sortidaResposta = sortidaResposta.substring(indexIniciDefinicio, indexIniciDefinicio + indexFinalDefinicio);

                // Descarta les etiquetes HTML
                sortidaResposta = esborraEtiquetesHTML(sortidaResposta);

                return sortidaResposta;
            }
            else {
                return null;
            }
        }
        return null;
    }

    // Retorna la resposta HTTP d'una consulta al DIEC
    private static String obteTextRespostaHTTP(HttpURLConnection connexio) throws IOException{

        StringBuffer respostaText = new StringBuffer();

        BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
        String liniaEntrada;
        while ((liniaEntrada = bufferEntrada.readLine()) != null) {
            respostaText.append(liniaEntrada);
        }
        bufferEntrada.close();

        return respostaText.toString();
    }

    // Filtra les etiquetes HTML (<>...</>) de la resposta a la consulta HTTP al DIEC
    private static String esborraEtiquetesHTML(String html){

        String sortidaSenseHtml = html;
        int indexIniciTag = -1, indexFinalTag = -1;

        do {
            indexIniciTag = sortidaSenseHtml.indexOf("<");
            indexFinalTag = sortidaSenseHtml.indexOf(">");
            if (indexIniciTag != -1 && indexFinalTag != -1) {
                String tag = sortidaSenseHtml.substring(indexIniciTag, indexFinalTag + 1);
                sortidaSenseHtml = sortidaSenseHtml.replace(tag, "");
            }
        } while (indexIniciTag != -1 && indexFinalTag != -1);

        return sortidaSenseHtml;
    }
}
