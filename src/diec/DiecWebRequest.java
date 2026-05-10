package diec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DiecWebRequest {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL_WORD ="https://dlc.iec.cat/Results?DecEntradaText=";
    private static final String GET_URL_ID = "https://dlc.iec.cat/Results/PrintAccepcio?id=";

    public static void main(String[] args) {

        try {
            String word = "àcids";
            //System.out.println("Word ID: " + getWordID(word));
            //System.out.println(getMeaningWord(word));
            String primeraLinea = getMeaningWord(word).split("\n")[1];  // Primera acepció
            System.out.println("PRIMERA LINEA: \n" + primeraLinea);

            ArrayList<Categoria.CategoriaGramatical> cgs = Categoria.CategoriaGramatical.obteCategoriesDeHTML(primeraLinea);
            for(Categoria.CategoriaGramatical cg : cgs) {
                System.out.println(cg.getNomCatala());
            }
        }
        catch(IOException e){

        }

    }


    private static String getWordID(String word) throws IOException {

        StringBuilder encodedWord = new StringBuilder(URLEncoder.encode(word, "UTF-8"));
        URL urlWord = new URL(GET_URL_WORD + encodedWord);
        System.out.println(urlWord);
        HttpURLConnection con = (HttpURLConnection) urlWord.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Charset", "UTF-8");

        int responseCode = con.getResponseCode();

        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            String outputWebWord = response.toString();
            String outputWebWordIndex = response.toString();
            System.out.println(outputWebWord);

            String keyWord = "onclick=\"GetDefinition('";
            ArrayList<Integer> indexos = new ArrayList<>();
            int removedChars = 0;
            while(outputWebWordIndex.indexOf(keyWord)!=-1){
                int index = outputWebWordIndex.indexOf(keyWord);
                indexos.add(index +  removedChars);
                outputWebWordIndex = outputWebWordIndex.substring(index+ keyWord.length());
                removedChars += index + keyWord.length();
            }
            if(indexos.size()>0) {
                String idWord = null;
                for (int i = 0; i < indexos.size(); i++) {
                    String part = outputWebWord.substring(indexos.get(i));
                    int indexStartWord = part.indexOf(">");
                    int indexEndWord = part.indexOf("</a>");
                    String checkWord = part.substring(indexStartWord + 1, indexEndWord).trim();
                    System.out.println("CHECKING WORD: " + checkWord);
                    if (checkWord.equals(word)) {
                        int indexID = indexos.get(i) + keyWord.length();
                        int indexEndID = outputWebWord.substring(indexID).indexOf("')");
                        idWord = outputWebWord.substring(indexID, indexID + indexEndID);
                        System.out.println("ID WORD: " + idWord);
                        break;
                    }
                }

                // Amb superíndexos
                if (idWord == null) {
                    //¹

                    for (int i = 0; i < indexos.size(); i++) {
                        String part = outputWebWord.substring(indexos.get(i));
                        int indexStartWord = part.indexOf(">");
                        int indexEndWord = part.indexOf("</a>");
                        String checkWord = part.substring(indexStartWord + 1, indexEndWord);
                        System.out.println("CHECKING WORD: " + checkWord);
                        if (checkWord.equals(word + "¹")) {
                            int indexID = indexos.get(i) + keyWord.length();
                            int indexEndID = outputWebWord.substring(indexID).indexOf("')");
                            idWord = outputWebWord.substring(indexID, indexID + indexEndID);
                            System.out.println("ID WORD: " + idWord);
                            break;
                        }
                    }
                }

                // Plurals o derivats
                if (idWord == null) {
                    int indexID = indexos.get(0) + keyWord.length();
                    int indexEndID = outputWebWord.substring(indexID).indexOf("')");
                    idWord = outputWebWord.substring(indexID, indexID + indexEndID);
                }


                return idWord;
            }
            return null;
        }
        else {
            return null;
        }
    }

    public static String getMeaningWord(String word) throws IOException{
        String idWord = getWordID(word);
        if(idWord!=null){
            URL url = new URL(GET_URL_ID + idWord);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();

            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                // print result

                String output = response.toString().replace("<br xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">", "\n");
                int indexStartDefinition = output.indexOf("<div id=\"Definition\" class=\"resultDefinition\">");
                int indexEndDefinition = output.substring(indexStartDefinition).indexOf("</div");
                output = output.substring(indexStartDefinition, indexStartDefinition + indexEndDefinition);

                int indexStartTag = -1, indexEndTag = -1;

                do {
                    indexStartTag = output.indexOf("<");
                    indexEndTag = output.indexOf(">");
                    if (indexStartTag != -1 && indexEndTag != -1) {
                        //System.out.println(indexStartTag +", "+indexEndTag);
                        String tag = output.substring(indexStartTag, indexEndTag + 1);
                        //System.out.println(tag);
                        output = output.replace(tag, "");
                    }

                } while (indexStartTag != -1 && indexEndTag != -1);

                //System.out.println(output);
                return output;
            }
            else {
                return null;
            }
        }
        return null;
    }
}
