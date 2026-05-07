package stats;

import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static processing.core.PApplet.*;

public class WordCounter {

    final String DELIMITADORS = "\n\r\t \",:;.·...?!¿¡'''-—–()[]{}«»/\\@#&*+=<>|%~^`";

    ArrayList<File> poemes;
    ArrayList<File> poemaris;
    HashMap<String, Integer> count;

    public WordCounter() {
        this.count = new HashMap<>();
        this.poemes = new ArrayList<>();
        this.poemaris = new ArrayList<>();
    }

    public String[] getTokens(File file){
        String[] linies  = loadStrings(file);
        String textJunt = join(linies, "\n");
        String[] tokens = splitTokens(textJunt, DELIMITADORS);
        return tokens;
    }

    public boolean contains(String term, File file){

        String normalizedTerm = term.toLowerCase().trim();
        String[] tokens       = getTokens(file);

        for (String token : tokens) {
            String word = token.toLowerCase().trim();
            if (word.equals(normalizedTerm)) {
                return true;
            }
        }

        return false;
    }

    public void processaPoema(File file) {

        String[] tokens = getTokens(file);

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase().trim();
            if (count.containsKey(word)) {
                int newValue = count.get(word) + 1;
                count.replace(word, newValue);
            } else {
                count.put(word, 1);
            }
        }
        poemes.add(file);
    }

    public void processaPoemari(File carpetaPoemari) {

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {

                System.out.println("Processant " + poema.getAbsoluteFile());
                String[] tokens = getTokens(poema);

                for (int i = 0; i < tokens.length; i++) {
                    String word = tokens[i].toLowerCase().trim();
                    if (count.containsKey(word)) {
                        int newValue = count.get(word) + 1;
                        count.replace(word, newValue);
                    } else {
                        count.put(word, 1);
                    }
                }
            }
        }

        poemaris.add(carpetaPoemari);
    }

    public List<String> getTermesPoema(File poema) {

        String[] tokens = getTokens(poema);

        ArrayList<String> termesDocument = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (!termesDocument.contains(word)) {
                termesDocument.add(word);
            }
        }

        return termesDocument;
    }

    public List<String> getTermesPoemari(File carpetaPoemari){

        ArrayList<String> termesPoemari = new ArrayList<>();

        File[] poemes = carpetaPoemari.listFiles();
        System.out.println("POEMES:" + poemes.length);

        for(File poema : poemes) {
            System.out.println("POEMA: " + poema.getAbsoluteFile());
            //termesPoemari.addAll(getTermesPoema(poema));
        }

        return termesPoemari;
    }

    public ArrayList<String> getTermesPoemaStopWords(File poema, StopWordsCatala swc) {

        String[] tokens = getTokens(poema);
        ArrayList<String> termesPoema = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (!termesPoema.contains(word) && !swc.esParaulaBuida(word)) {
                termesPoema.add(word);
            }
        }

        return termesPoema;
    }


    public static ArrayList<String> getTermesPoemariStopWords(WordCounter wcPoemari, StopWordsCatala swc){

        ArrayList<String> termesPoemari = new ArrayList<>();

        for(String terme : wcPoemari.getTermes()){
           if(!swc.esParaulaBuida(terme)){
               termesPoemari.add(terme);
           }
        }
        return termesPoemari;
    }

    public ArrayList<String> getTermesPoemariStopWords(File carpetaPoemari, StopWordsCatala swc) {

        ArrayList<String> termesPoemari = new ArrayList<>();

        File[] poemes = carpetaPoemari.listFiles();
        System.out.println("POEMES: " + poemes.length);

        if (poemes != null) {
            for (int i=0; i<poemes.length; i++) {
                File poema = poemes[i];
                System.out.println("Processant getTermesPoemariStopWords"+i+": " + poema.getAbsoluteFile());
                termesPoemari.addAll(getTermesPoemaStopWords(poema, swc));
            }
        }

        return termesPoemari;
    }

    public void processaPoemaStopWords(File poema, StopWordsCatala stopWordsCatala) {

        String[] tokens = getTokens(poema);

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase().trim();
            if(!stopWordsCatala.esParaulaBuida(word)) {
                if (count.containsKey(word)) {
                    int newValue = count.get(word) + 1;
                    count.replace(word, newValue);
                } else {
                    count.put(word, 1);
                }
            }
        }

        poemes.add(poema);
    }

    public void processaPoemariStopWords(File carpetaPoemari, StopWordsCatala stopWordsCatala) {

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {

                System.out.println("Processant " + poema.getAbsoluteFile());
                String[] tokens = getTokens(poema);

                for (int i = 0; i < tokens.length; i++) {
                    String word = tokens[i].toLowerCase().trim();
                    if(!stopWordsCatala.esParaulaBuida(word)) {
                        if (count.containsKey(word)) {
                            int newValue = count.get(word) + 1;
                            count.replace(word, newValue);
                        } else {
                            count.put(word, 1);
                        }
                    }
                }
            }
        }

        poemaris.add(carpetaPoemari);
    }

    public int getNumTerms(){
        return this.count.size();
    }

    public int getNumPoemes(){
        return this.poemes.size();
    }

    public File getPoemaAt(int i){ return this.poemes.get(i); }

    public int getNumPoemaris(){
        return this.poemaris.size();
    }

    public File getPoemariAt(int i){ return this.poemaris.get(i); }

    public int getNumPoemesContain(String term){
        int num = 0;
        for(File document : poemes){
            if(contains(term, document)){
                num++;
            }
        }
        return num;
    }

    public int getNumPoemarisContain(String term){
        int num = 0;
        for(File poemari : poemaris){
            File[] poemes = poemari.listFiles();
            for(File poema : poemes) {
                if (contains(term, poema)) {
                    num++;
                }
            }
        }
        return num;
    }

    public int getNumOcurrenciesTerme(String term) {
        if(count.containsKey(term)) {
            return count.get(term);
        }
        else {
            return 0;
        }
    }

    public int getNumOcurrenciesTermes(String ...termes) {
        int numOcurrencies =0;
        for(String terme : termes){
            numOcurrencies += getNumOcurrenciesTerme(terme);
        }
        return  numOcurrencies;
    }

    public String[] getTermes() {
        String[] words = new String[count.size()];
        int i = 0;
        for (String word : count.keySet()) {
            words[i] = word;
            i++;
        }
        return words;
    }

    public void display(PApplet p5) {
        String[] keys = getTermes();
        for (String k : keys) {
            int value = count.get(k);
            p5.textSize(10 + value * 5);
            float x = p5.random(p5.width);
            float y = p5.random(p5.height);
            p5.fill(0);
            p5.text(k + "(" + value + ")", x, y);
        }
    }

    public List<String> ordenarPerOcurrenciaDesc() {
        return count.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // TF IDF

    public float termFreqPoema(String term, File poema){
        WordCounter wcd = new WordCounter();
        wcd.processaPoema(poema);
        return (float)(wcd.getNumOcurrenciesTerme(term)) / wcd.getNumTerms();
    }

    public float termFreqPoemari(String term, File carpetaPoemari){
        WordCounter wcd = new WordCounter();
        wcd.processaPoemari(carpetaPoemari);
        return (float)(wcd.getNumOcurrenciesTerme(term)) / wcd.getNumTerms();
    }

    public float termFreqPoemari(String term, WordCounter wcPoemari){
        return (float)(wcPoemari.getNumOcurrenciesTerme(term)) / getNumTerms();
    }

    public float termFreqPoemaStopWords(String term, File poema, StopWordsCatala swc){
        WordCounter wcd = new WordCounter();
        wcd.processaPoemaStopWords(poema, swc);
        return (float)(wcd.getNumOcurrenciesTerme(term)) / wcd.getNumTerms();
    }

    public float termFreqPoemariStopWords(String term, WordCounter wcd){
        return (float)(wcd.getNumOcurrenciesTerme(term)) / getNumTerms();
    }

    // Freqüència de Document Invers: df(t,D) = log (N/( n))
    public float inverseDocFreqPoema(String term){
        int N = getNumPoemes();
        int n = getNumPoemesContain(term);
        return log (N/((float) n));
    }

    public float inverseDocFreqPoemari(String term){
        int N = getNumPoemaris();
        int n = getNumPoemarisContain(term);
        return log (N/((float) n));
    }

    public float tfIdfPoema(String term, File poema){
        return termFreqPoema(term, poema) * inverseDocFreqPoema(term);
    }

    public float tfIdfPoemari(String term, File carpetaPoemari){
        return termFreqPoemari(term, carpetaPoemari) * inverseDocFreqPoemari(term);
    }

    public float tfIdfPoemari(String term, WordCounter wcPoemari){
        return termFreqPoemari(term, wcPoemari) * inverseDocFreqPoemari(term);
    }

    public float tfIdfPoemaStopWords(String term, File poema, StopWordsCatala swc){
        return termFreqPoemaStopWords(term, poema, swc) * inverseDocFreqPoema(term);
    }

    public float tfIdfPoemariStopWords(String term, WordCounter wcPoemari){
        return termFreqPoemariStopWords(term, wcPoemari) * inverseDocFreqPoemari(term);
    }


    public TermeFreq[] getParaulesClauPoema(int numParaulesClau, File poema){
        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];
        for(String terme : getTermesPoema(poema)){
            float tfIdfTerme = tfIdfPoema(terme, poema);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, tfIdfTerme);
            }
        }
        return paraulesClau;
    }

    /*

    public TermeFreq[] getParaulesClauPoemari(int numParaulesClau, File carpetaPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        List<String> termesPoemari = getTermesPoemari(carpetaPoemari);
        System.out.println("TERMES POEMARI: "+ termesPoemari.size());


        for(String terme : termesPoemari){
            float tfIdfTerme = tfIdfPoemari(terme, carpetaPoemari);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, tfIdfTerme);
            }
        }


        return paraulesClau;
    }

     */



    public TermeFreq[] getParaulesClauPoemaStopWords(int numParaulesClau, File poema, StopWordsCatala swc){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        for(String terme : getTermesPoemaStopWords(poema, swc)){
            float tfIdfTerme = tfIdfPoemaStopWords(terme, poema, swc);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, tfIdfTerme);
            }
        }
        return paraulesClau;
    }

    public TermeFreq[] getParaulesClauPoemariStopWords(int numParaulesClau,StopWordsCatala swc, WordCounter wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        ArrayList<String> termesPoemari = WordCounter.getTermesPoemariStopWords(wcPoemari, swc);
        System.out.println("POEMARI: "+termesPoemari.size());

        int numTerme = 0;
        for(String terme : termesPoemari){

            //System.out.println("TERME ("+numTerme+"/"+ termesPoemari.size()+"):"+ terme);
            float tfIdfTerme = tfIdfPoemariStopWords(terme, wcPoemari);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, tfIdfTerme);
            }

            numTerme++;
        }
        return paraulesClau;
    }

    public TermeFreq[] getParaulesClauPoemari(int numParaulesClau, WordCounter wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        String[] termesPoemari = wcPoemari.getTermes();
        //System.out.println("POEMARI: "+termesPoemari.length);

        int numTerme = 0;
        for(String terme : termesPoemari){

            float tfIdfTerme = tfIdfPoemari(terme, wcPoemari);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, tfIdfTerme);
            }

            //System.out.println("TERME ("+numTerme+"/"+ termesPoemari.length+"):"+ terme + " tfidf: "+ tfIdfTerme);
            numTerme++;
        }
        return paraulesClau;
    }
}