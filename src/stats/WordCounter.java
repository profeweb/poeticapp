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

    ArrayList<File> documents;
    HashMap<String, Integer> count;

    public WordCounter() {
        this.count = new HashMap<>();
        this.documents = new ArrayList<>();
    }

    public boolean contains(String term, File file){
        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        return allwords.indexOf(term)!=-1;
    }

    public void processDocument(File file) {

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, "\n\" ,;.?!'");

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (count.containsKey(word)) {
                int newValue = count.get(word) + 1;
                count.replace(word, newValue);
            } else {
                count.put(word, 1);
            }
        }


        documents.add(file);
    }

    public void processDocument(File file, StopWordsCatala stopWordsCatala) {

        String[] lines = loadStrings(file);
        String allwords = join(lines, "\n");
        String[] tokens = splitTokens(allwords, "\n\" ,;.?!'");

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();

            if(!stopWordsCatala.esParaulaBuida(word)) {
                //println("COMPTABILITZA: " + word);
                if (count.containsKey(word)) {
                    int newValue = count.get(word) + 1;
                    count.replace(word, newValue);
                } else {
                    count.put(word, 1);
                }
            }
            else {
                //println("DESCARTA: " + word);
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
        wcd.processDocument(document);
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
}