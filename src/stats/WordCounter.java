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

    ArrayList<File> documents;
    HashMap<String, Integer> count;

    public WordCounter() {
        this.count = new HashMap<>();
        this.documents = new ArrayList<>();
    }

    public boolean contains(String term, File file){

        String[] lines = loadStrings(file);

        if (lines == null || lines.length == 0) {
            return false;
        }

        String normalizedTerm = term.toLowerCase().trim();
        String allWords       = join(lines, "\n");
        String[] tokens       = splitTokens(allWords, DELIMITADORS);

        for (String token : tokens) {
            String word = token.toLowerCase().trim();
            if (word.equals(normalizedTerm)) {
                return true;
            }
        }

        return false;
    }

    public void processaPoema(File file) {

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, DELIMITADORS);

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase().trim();
            if (count.containsKey(word)) {
                int newValue = count.get(word) + 1;
                count.replace(word, newValue);
            } else {
                count.put(word, 1);
            }
        }
        documents.add(file);
    }

    public void processaPoemari(File[] file) {

        /*

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, DELIMITADORS);

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase().trim();
            if (count.containsKey(word)) {
                int newValue = count.get(word) + 1;
                count.replace(word, newValue);
            } else {
                count.put(word, 1);
            }
        }
        documents.add(file);

         */
    }

    public List<String> getTermesDocument(File file) {

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, DELIMITADORS);

        ArrayList<String> termesDocument = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (!termesDocument.contains(word)) {
                termesDocument.add(word);
            }
        }

        return termesDocument;
    }

    public List<String> getTermesDocumentStopWords(File file) {

        StopWordsCatala swc = new StopWordsCatala();

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, DELIMITADORS);

        ArrayList<String> termesDocument = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (!termesDocument.contains(word) && !swc.esParaulaBuida(word)) {
                termesDocument.add(word);
            }
        }

        return termesDocument;
    }

    public void processaPoema(File file, StopWordsCatala stopWordsCatala) {

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, "\n\" ,;.?!'-");

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if(!stopWordsCatala.esParaulaBuida(word)) {
                if (count.containsKey(word)) {
                    int newValue = count.get(word) + 1;
                    count.replace(word, newValue);
                } else {
                    count.put(word, 1);
                }
            }
        }

        documents.add(file);
    }

    public int getNumTerms(){
        return this.count.size();
    }

    public int getNumDocuments(){
        return this.documents.size();
    }

    public int getNumDocumentsContain(String term){
        int num = 0;
        for(File document : documents){
            if(contains(term, document)){
                num++;
            }
        }
        return num;
    }

    public int getCount(String term) {
        if(count.containsKey(term)) {
            return count.get(term);
        }
        else {
            return 0;
        }
    }

    public String[] getKeys() {
        String[] words = new String[count.size()];
        int i = 0;
        for (String word : count.keySet()) {
            words[i] = word;
            i++;
        }
        return words;
    }

    public void display(PApplet p5) {
        String[] keys = getKeys();
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

    public float termFreq(String term, File document){
        WordCounter wcd = new WordCounter();
        wcd.processaPoema(document);
        return (float)(wcd.getCount(term)) / wcd.getNumTerms();
    }

    public float termFreqStopWords(String term, File document){
        StopWordsCatala swc = new StopWordsCatala();
        WordCounter wcd = new WordCounter();
        wcd.processaPoema(document, swc);
        return (float)(wcd.getCount(term)) / wcd.getNumTerms();
    }

    // Freqüència de Document Invers: df(t,D) = log (N/( n))
    public float inverseDocFreq(String term){
        int N = getNumDocuments();
        int n = getNumDocumentsContain(term);
        return log (N/((float) n));
    }

    public float tfIdf(String term, File document){
        return termFreq(term, document) * inverseDocFreq(term);
    }

    public float tfIdfStopWords(String term, File document){
        return termFreqStopWords(term, document) * inverseDocFreq(term);
    }


    public TermeFreq[] getParaulesClau(int numParaulesClau, File document){
        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];
        for(String terme : getTermesDocument(document)){
            float tfIdfTerme = tfIdf(terme, document);
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

    public TermeFreq[] getParaulesClauStopWords(int numParaulesClau, File document){
        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        for(String terme : getTermesDocumentStopWords(document)){
            float tfIdfTerme = tfIdfStopWords(terme, document);
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
}