package stats;

import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static processing.core.PApplet.*;

public class ComptadorParaules {

    final String DELIMITADORS = "\n\r\t \".,:;...?!¿¡'''-—–()[]{}«»/\\@#&*+=<>|%~^`";

    ArrayList<File> poemes;
    ArrayList<File> poemaris;
    HashMap<String, Integer> comptes;

    public ComptadorParaules() {
        this.comptes = new HashMap<>();
        this.poemes = new ArrayList<>();
        this.poemaris = new ArrayList<>();
    }

    public String[] getTokens(File file){
        String[] linies  = loadStrings(file);
        String textJunt = join(linies, "\n");
        String[] tokens = splitTokens(textJunt, DELIMITADORS);
        return tokens;
    }

    public boolean poemaConteTerme(String term, File file){

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
            if (comptes.containsKey(word)) {
                int newValue = comptes.get(word) + 1;
                comptes.replace(word, newValue);
            } else {
                comptes.put(word, 1);
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
                    if (comptes.containsKey(word)) {
                        int newValue = comptes.get(word) + 1;
                        comptes.replace(word, newValue);
                    } else {
                        comptes.put(word, 1);
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

        for(File poema : poemes) {
            termesPoemari.addAll(getTermesPoema(poema));
        }

        return termesPoemari;
    }

    public ArrayList<String> getTermesPoemaParaulesBuides(File poema, ParaulesBuidesCatala paraulesBuidesCatala) {

        String[] tokens = getTokens(poema);
        ArrayList<String> termesPoema = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase();
            if (!termesPoema.contains(word) &&
                    !paraulesBuidesCatala.esParaulaBuida(word)) {
                termesPoema.add(word);
            }
        }

        return termesPoema;
    }


    public static ArrayList<String> getTermesPoemariStopWords(ComptadorParaules wcPoemari, ParaulesBuidesCatala paraulesBuidesCatala){

        ArrayList<String> termesPoemari = new ArrayList<>();

        for(String terme : wcPoemari.getTermes()){
           if(!paraulesBuidesCatala.esParaulaBuida(terme)){
               termesPoemari.add(terme);
           }
        }
        return termesPoemari;
    }

    public ArrayList<String> getTermesPoemariStopWords(File carpetaPoemari, ParaulesBuidesCatala paraulesBuidesCatala) {

        ArrayList<String> termesPoemari = new ArrayList<>();

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for(File poema : poemes){
                termesPoemari.addAll(getTermesPoemaParaulesBuides(poema, paraulesBuidesCatala));
            }
        }

        return termesPoemari;
    }

    public void processaPoemaParaulesBuides(File poema, ParaulesBuidesCatala paraulesBuidesCatala) {

        String[] tokens = getTokens(poema);

        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i].toLowerCase().trim();
            if(!paraulesBuidesCatala.esParaulaBuida(word)) {
                if (comptes.containsKey(word)) {
                    int noucompte = comptes.get(word) + 1;
                    comptes.replace(word, noucompte);
                } else {
                    comptes.put(word, 1);
                }
            }
        }

        poemes.add(poema);
    }

    public void processaPoemariParaulesBuides(File carpetaPoemari, ParaulesBuidesCatala paraulesBuidesCatala) {

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {

                String[] tokens = getTokens(poema);

                for (int i = 0; i < tokens.length; i++) {
                    String word = tokens[i].toLowerCase().trim();
                    if(!paraulesBuidesCatala.esParaulaBuida(word)) {
                        if (comptes.containsKey(word)) {
                            int newValue = comptes.get(word) + 1;
                            comptes.replace(word, newValue);
                        } else {
                            comptes.put(word, 1);
                        }
                    }
                }
            }
        }

        poemaris.add(carpetaPoemari);
    }

    public int getNumTerms(){
        return this.comptes.size();
    }

    public int getNumPoemes(){
        return this.poemes.size();
    }

    public File getPoemaAt(int i){ return this.poemes.get(i); }

    public int getNumPoemaris(){
        return this.poemaris.size();
    }

    public File getPoemariAt(int i){ return this.poemaris.get(i); }

    public int getNumPoemesContenenTerme(String term){
        int num = 0;
        for(File document : poemes){
            if(poemaConteTerme(term, document)){
                num++;
            }
        }
        return num;
    }

    public int getNumPoemarisContenenTerme(String term){
        int num = 0;
        for(File poemari : poemaris){
            File[] poemes = poemari.listFiles();
            for(File poema : poemes) {
                if (poemaConteTerme(term, poema)) {
                    num++;
                }
            }
        }
        return num;
    }

    public int getNumOcurrenciesTerme(String term) {
        if(comptes.containsKey(term)) {
            return comptes.get(term);
        }
        else {
            return 0;
        }
    }

    public int getNumOcurrenciesTermes(String ... termes) {
        int numOcurrencies =0;
        for(String terme : termes){
            numOcurrencies += getNumOcurrenciesTerme(terme);
        }
        return  numOcurrencies;
    }

    public String[] getTermes() {
        String[] words = new String[comptes.size()];
        int i = 0;
        for (String word : comptes.keySet()) {
            words[i] = word;
            i++;
        }
        return words;
    }

    public void display(PApplet p5) {
        String[] keys = getTermes();
        for (String k : keys) {
            int value = comptes.get(k);
            p5.textSize(10 + value * 5);
            float x = p5.random(p5.width);
            float y = p5.random(p5.height);
            p5.fill(0);
            p5.text(k + "(" + value + ")", x, y);
        }
    }

    public List<String> ordenarPerOcurrenciaDesc() {
        return comptes.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> ordenarPerOcurrenciaAsc() {
        return comptes.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // TF IDF

    public float freqTermePoema(String term, File poema){
        ComptadorParaules cpPoema = new ComptadorParaules();
        cpPoema.processaPoema(poema);
        return (float)(cpPoema.getNumOcurrenciesTerme(term)) / cpPoema.getNumTerms();
    }

    public float freqTermePoemari(String term, File carpetaPoemari){
        ComptadorParaules cpPoemari = new ComptadorParaules();
        cpPoemari.processaPoemari(carpetaPoemari);
        return (float)(cpPoemari.getNumOcurrenciesTerme(term)) / cpPoemari.getNumTerms();
    }

    public float freqTermePoemari(String term, ComptadorParaules wcPoemari){
        return (float)(wcPoemari.getNumOcurrenciesTerme(term)) / getNumTerms();
    }

    public float freqTermePoemaParaulesBuides(String term, File poema, ParaulesBuidesCatala paraulesBuidesCatala){
        ComptadorParaules cpPoema = new ComptadorParaules();
        cpPoema.processaPoemaParaulesBuides(poema, paraulesBuidesCatala);
        return (float)(cpPoema.getNumOcurrenciesTerme(term)) / cpPoema.getNumTerms();
    }

    public float freqTermePoemariParaulesBuides(String term, ComptadorParaules cpPoemari){
        return (float)(cpPoemari.getNumOcurrenciesTerme(term)) / getNumTerms();
    }

    // Freqüència de Document Invers: df(t,D) = log (N/( n))
    public float freqPoemaInvers(String term){
        int N = getNumPoemes();
        int n = getNumPoemesContenenTerme(term);
        return log (N/((float) n));
    }

    public float freqPoemariInvers(String term){
        int N = getNumPoemaris();
        int n = getNumPoemarisContenenTerme(term);
        return log (N/((float) n));
    }

    public float tfIdfPoema(String term, File poema){
        return freqTermePoema(term, poema) * freqPoemaInvers(term);
    }

    public float tfIdfPoemari(String term, File carpetaPoemari){
        return freqTermePoemari(term, carpetaPoemari) * freqPoemariInvers(term);
    }

    public float tfIdfPoemari(String term, ComptadorParaules wcPoemari){
        return freqTermePoemari(term, wcPoemari) * freqPoemariInvers(term);
    }

    public float tfIdfPoemaParaulesBuides(String term, File poema, ParaulesBuidesCatala swc){
        return freqTermePoemaParaulesBuides(term, poema, swc) * freqPoemaInvers(term);
    }

    public float tfIdfPoemariParaulesBuides(String term, ComptadorParaules wcPoemari){
        return freqTermePoemariParaulesBuides(term, wcPoemari) * freqPoemariInvers(term);
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


    public TermeFreq[] getParaulesClauPoemaParaulesBuides(int numParaulesClau, File poema, ParaulesBuidesCatala swc){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        for(String terme : getTermesPoemaParaulesBuides(poema, swc)){
            float tfIdfTerme = tfIdfPoemaParaulesBuides(terme, poema, swc);
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

    public TermeFreq[] getParaulesClauPoemariParaulesBuides(int numParaulesClau, ParaulesBuidesCatala swc, ComptadorParaules wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        ArrayList<String> termesPoemari = ComptadorParaules.getTermesPoemariStopWords(wcPoemari, swc);

        for(String terme : termesPoemari){


            float tfIdfTerme = tfIdfPoemariParaulesBuides(terme, wcPoemari);
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

    public TermeFreq[] getParaulesClauPoemari(int numParaulesClau, ComptadorParaules wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        String[] termesPoemari = wcPoemari.getTermes();

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
        }
        return paraulesClau;
    }

    public ArrayList<TermeFreq> getTermesFreqs(){
        ArrayList<TermeFreq> termesFreqs = new ArrayList<>();
        for(String terme : comptes.keySet()){
            float freq = comptes.get(terme);
            termesFreqs.add(new TermeFreq(terme, freq));
        }
        return termesFreqs;
    }


    // Cerques

    public ArrayList<String> termesComencenAmb(String prefix){
        ArrayList<String> termesPrefix = new ArrayList<>();
        for(String terme : getTermes()){
            if(terme.startsWith(prefix.toLowerCase())){
                termesPrefix.add(terme);
            }
        }
        return termesPrefix;
    }

    public ArrayList<String> termesAcabenAmb(String sufix){
        ArrayList<String> termesPrefix = new ArrayList<>();
        for(String terme : getTermes()){
            if(terme.endsWith(sufix.toLowerCase())){
                termesPrefix.add(terme);
            }
        }
        return termesPrefix;
    }

    public ArrayList<String> termesContenen(String afix){
        ArrayList<String> termesPrefix = new ArrayList<>();
        for(String terme : getTermes()){
            if(terme.contains(afix.toLowerCase())){
                termesPrefix.add(terme);
            }
        }
        return termesPrefix;
    }
}