package diec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ConsultaWebDIEC {

    private static final String USER_AGENT      = "Mozilla/5.0";
    private static final String GET_URL_TERME   = "https://dlc.iec.cat/Results?DecEntradaText=";
    private static final String GET_URL_ID      = "https://dlc.iec.cat/Results/PrintAccepcio?id=";

    public static void main(String[] args) {

        try {

            String word = "casa";
            String html = obteSignificatTerme(word);
            String primeraLinea = html.split("\n")[1];  // Primera acepció
            System.out.println("PRIMERA LINEA: \n" + primeraLinea);

            System.out.println("CATEGORIA GRAMATICAL: ");
            ArrayList<Categoria.CategoriaGramatical> cgs = Categoria.CategoriaGramatical.obteCategoriesDelHTML(primeraLinea);
            for(Categoria.CategoriaGramatical cg : cgs) {
                System.out.println(cg.getNomCatala());
            }

            //System.out.println("CATEGORIA TEMÀTICA: ");
            //Categoria.CategoriaTematica ct1 = Categoria.CategoriaTematica.obteCategoriaDelHTML(primeraLinea);
            //System.out.println(ct1.getNomCatala());

            System.out.println("CATEGORIES TEMÀTIQUES: ");
            ArrayList<Categoria.CategoriaTematica> cts = Categoria.CategoriaTematica.obteCategoriesDelHTML(primeraLinea);
            for(Categoria.CategoriaTematica ct : cts) {
                System.out.println(ct.getNomCatala());
            }
        }
        catch(IOException e){

        }

    }

    private static HttpURLConnection creaConnexioHttpTermeDIEC(String terme) throws IOException{
        StringBuilder termeCodificat = new StringBuilder(URLEncoder.encode(terme, "UTF-8"));
        URL urlTerme = new URL(GET_URL_TERME + termeCodificat);
        HttpURLConnection connexio = (HttpURLConnection) urlTerme.openConnection();
        connexio.setRequestMethod("GET");
        connexio.setRequestProperty("User-Agent", USER_AGENT);
        return connexio;
    }

    private static HttpURLConnection creaConnexioHttpIdTermeDIEC(String idTerme) throws IOException{
        URL urlID = new URL(GET_URL_ID + idTerme);
        HttpURLConnection connexio = (HttpURLConnection) urlID.openConnection();
        connexio.setRequestMethod("GET");
        connexio.setRequestProperty("User-Agent", USER_AGENT);
        return connexio;
    }

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

    private static String esborraEtiquetesHTML(String html){

        String sortidaSenseHtml = html;
        int indexStartTag = -1, indexEndTag = -1;

        do {
            indexStartTag = sortidaSenseHtml.indexOf("<");
            indexEndTag = sortidaSenseHtml.indexOf(">");
            if (indexStartTag != -1 && indexEndTag != -1) {
                String tag = sortidaSenseHtml.substring(indexStartTag, indexEndTag + 1);
                sortidaSenseHtml = sortidaSenseHtml.replace(tag, "");
            }
        } while (indexStartTag != -1 && indexEndTag != -1);

        return sortidaSenseHtml;
    }
}
