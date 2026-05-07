package stats;

import java.io.File;
import java.util.ArrayList;

import static processing.core.PApplet.log;

// https://medium.com/@adityamdk/tf-idf-implementation-in-java-f6c4d1d97e3b
// term frequency–inverse document frequency
public class TF_IDF {

    // Freqüència d'un terme t en un document d: tf(t, d)
    public static float termFreq(String term, File document){
        WordCounter wc = new WordCounter();
        wc.processaPoemaStopWords(document);
        return wc.getNumOcurrenciesTerme(term) / wc.getNumTerms();
    }

    // Freqüència de Document Invers: df(t,D) = log (N/( n))
    public static float inverseDocFreq(String term, ArrayList<File> documents){
        WordCounter wc = new WordCounter();
        for(File document : documents) {
            wc.processaPoemaStopWords(document);
        }
        int N = wc.getNumPoemes();
        int n = wc.getNumPoemesContain(term);
        return log (N/( n));
    }

    public static float tfIdf(String term, File document, ArrayList<File> documents){
        return termFreq(term, document) * inverseDocFreq(term, documents);
    }


}
