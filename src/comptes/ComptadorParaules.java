package comptes;

import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static processing.core.PApplet.*;

public class ComptadorParaules {

    // Delimitadors de paraules
    final String DELIMITADORS = "\n\r\t \".,:;...?!¿¡'''-—–()[]{}«»/\\@#&*+=<>|%~^`";

    // Carpetes dels poemaris i fitxers dels poemes
    ArrayList<File> poemes, poemaris;

    // Mapa de hashing per a paraules i comptes
    HashMap<String, Integer> comptes;

    // Constructor: inicialitza propietats
    public ComptadorParaules() {
        this.comptes = new HashMap<>();
        this.poemes = new ArrayList<>();
        this.poemaris = new ArrayList<>();
    }

    // Tokenitza un fitxer corresponent a un poema
    public String[] getTokens(File fitxer){
        String[] linies  = loadStrings(fitxer);
        String textJunt = join(linies, "\n");
        String[] tokens = splitTokens(textJunt, DELIMITADORS);
        return tokens;
    }

    // Retorna true si un poema conté el terme
    public boolean poemaConteTerme(String terme, File fitxer){

        String termeNormalitzat = terme.toLowerCase().trim();
        String[] tokens       = getTokens(fitxer);

        for (String token : tokens) {
            String paraula = token.toLowerCase().trim();
            if (paraula.equals(termeNormalitzat)) {
                return true;
            }
        }

        return false;
    }

    // Processa totes les paraules d'un poema
    public void processaPoema(File fitxer) {

        String[] tokens = getTokens(fitxer);

        for (int i = 0; i < tokens.length; i++) {
            String paraula = tokens[i].toLowerCase().trim();
            if (comptes.containsKey(paraula)) {
                int nouValor = comptes.get(paraula) + 1;
                comptes.replace(paraula, nouValor);
            } else {
                comptes.put(paraula, 1);
            }
        }
        poemes.add(fitxer);
    }

    // Processa totes les paraules dels poemes del poemari
    public void processaPoemari(File carpetaPoemari) {

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {

                System.out.println("Processant " + poema.getAbsoluteFile());
                String[] tokens = getTokens(poema);

                for (int i = 0; i < tokens.length; i++) {
                    String paraula = tokens[i].toLowerCase().trim();
                    if (comptes.containsKey(paraula)) {
                        int nouValor = comptes.get(paraula) + 1;
                        comptes.replace(paraula, nouValor);
                    } else {
                        comptes.put(paraula, 1);
                    }
                }
            }
        }

        poemaris.add(carpetaPoemari);
    }

    // Retorna una llista amb els termes d'un poema
    public List<String> getTermesPoema(File poema) {

        String[] tokens = getTokens(poema);
        ArrayList<String> termesDocument = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String paraula = tokens[i].toLowerCase();
            if (!termesDocument.contains(paraula)) {
                termesDocument.add(paraula);
            }
        }

        return termesDocument;
    }

    // Retorna una llista amb els termes d'un poemari
    public List<String> getTermesPoemari(File carpetaPoemari){

        ArrayList<String> termesPoemari = new ArrayList<>();
        File[] poemes = carpetaPoemari.listFiles();

        for(File poema : poemes) {
            termesPoemari.addAll(getTermesPoema(poema));
        }

        return termesPoemari;
    }

    // Retorna els termes d'un poema excloent les paraules buides
    public ArrayList<String> getTermesPoemaParaulesBuides(File poema, ParaulesBuidesCatala paraulesBuidesCatala) {

        String[] tokens = getTokens(poema);
        ArrayList<String> termesPoema = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String paraula = tokens[i].toLowerCase();
            if (!termesPoema.contains(paraula) &&
                    !paraulesBuidesCatala.esParaulaBuida(paraula)) {
                termesPoema.add(paraula);
            }
        }

        return termesPoema;
    }

    // Retorna els termes d'un poemari excloent les paraules buides
    public static ArrayList<String> getTermesPoemariParaulesBuides(ComptadorParaules wcPoemari, ParaulesBuidesCatala paraulesBuidesCatala){

        ArrayList<String> termesPoemari = new ArrayList<>();

        for(String terme : wcPoemari.getTermes()){
           if(!paraulesBuidesCatala.esParaulaBuida(terme)){
               termesPoemari.add(terme);
           }
        }
        return termesPoemari;
    }

    // Retorna els termes d'un poemari excloent les paraules buides
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

    // Processa totes les paraules del poema excloent les paraules buides
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

    // Processa totes les paraules del poemari excloent les paraules buides
    public void processaPoemariParaulesBuides(File carpetaPoemari, ParaulesBuidesCatala paraulesBuidesCatala) {

        File[] poemes = carpetaPoemari.listFiles();

        if (poemes != null) {
            for (File poema : poemes) {

                String[] tokens = getTokens(poema);

                for (int i = 0; i < tokens.length; i++) {
                    String paraula = tokens[i].toLowerCase().trim();
                    if(!paraulesBuidesCatala.esParaulaBuida(paraula)) {
                        if (comptes.containsKey(paraula)) {
                            int nouValor = comptes.get(paraula) + 1;
                            comptes.replace(paraula, nouValor);
                        } else {
                            comptes.put(paraula, 1);
                        }
                    }
                }
            }
        }

        poemaris.add(carpetaPoemari);
    }

    // Retorna el número de termes processats
    public int getNumTerms(){
        return this.comptes.size();
    }

    // Retorna el número de poemes processats
    public int getNumPoemes(){
        return this.poemes.size();
    }

    // Retorna el fitxer corresponent al poema i-èssim
    public File getPoemaAt(int i){ return this.poemes.get(i); }

    // Retorna el número de poemaris processats
    public int getNumPoemaris(){
        return this.poemaris.size();
    }

    // Retorna la carpeta corresponent al poemari i-èssim
    public File getPoemariAt(int i){ return this.poemaris.get(i); }

    // Retorna el número de poemes que contenen el terme
    public int getNumPoemesContenenTerme(String terme){
        int num = 0;
        for(File document : poemes){
            if(poemaConteTerme(terme, document)){
                num++;
            }
        }
        return num;
    }

    // Retorna el número de poemaris que contenen el terme
    public int getNumPoemarisContenenTerme(String terme){
        int num = 0;
        for(File poemari : poemaris){
            File[] poemes = poemari.listFiles();
            for(File poema : poemes) {
                if (poemaConteTerme(terme, poema)) {
                    num++;
                    break;
                }
            }
        }
        return num;
    }

    // Retorna el número d'ocurrències d'un terme processat
    public int getNumOcurrenciesTerme(String terme) {
        if(comptes.containsKey(terme)) {
            return comptes.get(terme);
        }
        else {
            return 0;
        }
    }

    // Retorna el número d'ocurrències d'un conjunt de termes processats
    public int getNumOcurrenciesTermes(String ... termes) {
        int numOcurrencies =0;
        for(String terme : termes){
            numOcurrencies += getNumOcurrenciesTerme(terme);
        }
        return  numOcurrencies;
    }

    // Retorna un array amb el text de tots els termes processats
    public String[] getTermes() {
        String[] termes = new String[comptes.size()];
        int i = 0;
        for (String paraula : comptes.keySet()) {
            termes[i] = paraula;
            i++;
        }
        return termes;
    }

    // Mostra els termes i número d'ocurrències
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

    // Retorna una llista ordenada dels termes processats en ordre alfabètic descendent
    public List<String> ordenarPerOcurrenciaDesc() {
        return comptes.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Retorna una llista ordenada dels termes processats en ordre alfabètic ascendent
    public List<String> ordenarPerOcurrenciaAsc() {
        return comptes.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // TF IDF *******************************************************************************************************

    // Calcula la freqüència d'un terme en un poema
    public float freqTermePoema(String terme, File poema){
        ComptadorParaules cpPoema = new ComptadorParaules();
        cpPoema.processaPoema(poema);
        return (float)(cpPoema.getNumOcurrenciesTerme(terme)) / cpPoema.getNumTerms();
    }

    // Calcula la freqüència d'un terme en un poemari
    public float freqTermePoemari(String term, File carpetaPoemari){
        ComptadorParaules cpPoemari = new ComptadorParaules();
        cpPoemari.processaPoemari(carpetaPoemari);
        return (float)(cpPoemari.getNumOcurrenciesTerme(term)) / cpPoemari.getNumTerms();
    }

    // Calcula la freqüència d'un terme a partir del comptador de paraules del poemari
    public static float freqTermePoemari(String term, ComptadorParaules wcPoemari){
        return ((float) wcPoemari.getNumOcurrenciesTerme(term)) / wcPoemari.getNumTerms();
    }

    // Calcula la freqüència d'un terme a partir del comptador de paraules del poema excloent paraules buides
    public float freqTermePoemaParaulesBuides(String term, File poema, ParaulesBuidesCatala paraulesBuidesCatala){
        ComptadorParaules cpPoema = new ComptadorParaules();
        cpPoema.processaPoemaParaulesBuides(poema, paraulesBuidesCatala);
        return (float)(cpPoema.getNumOcurrenciesTerme(term)) / cpPoema.getNumTerms();
    }

    // Calcula la freqüència d'un terme a partir del comptador de paraules del poemari
    public float freqTermePoemariParaulesBuides(String term, ComptadorParaules cpPoemari){
        return (float)(cpPoemari.getNumOcurrenciesTerme(term)) / getNumTerms();
    }

    // Calcula la freqüència de Document (Poema) Invers: df(t,D) = log (N/( n))
    public float freqPoemaInvers(String term){
        int N = getNumPoemes();
        int n = getNumPoemesContenenTerme(term);
        return log (N/((float) n));
    }

    // Calcula la freqüència de Document (Poemari) Invers: df(t,D) = log (N/( n))
    public double freqPoemariInvers(String term){
        int N = getNumPoemaris();
        int n = getNumPoemarisContenenTerme(term);
        return Math.log ((double) N /((double) n));
    }

    // Calcula el tfIdf en el poema d'un terme
    public float tfIdfPoema(String terme, File poema){
        return freqTermePoema(terme, poema) * freqPoemaInvers(terme);
    }

    // Calcula el tfIdf en el poemari d'un terme
    public double tfIdfPoemari(String terme, File carpetaPoemari){
        return freqTermePoemari(terme, carpetaPoemari) * freqPoemariInvers(terme);
    }

    // Calcula el tfIdf en el poemari d'un terme excloent paraules buides
    public double tfIdfPoemari(String term, ComptadorParaules wcPoemari){
        return freqTermePoemari(term, wcPoemari) * freqPoemariInvers(term);
    }

    // Calcula el tfIdf en el poemari d'un terme excloent paraules buides
    public float tfIdfPoemaParaulesBuides(String terme, File poema, ParaulesBuidesCatala paraulesBuidesCatala){
        return freqTermePoemaParaulesBuides(terme, poema, paraulesBuidesCatala) * freqPoemaInvers(terme);
    }

    // Calcula el tfIdf en el poemari d'un terme excloent paraules buides
    public double tfIdfPoemariParaulesBuides(String terme, ComptadorParaules wcPoemari){
        return freqTermePoemariParaulesBuides(terme, wcPoemari) * freqPoemariInvers(terme);
    }


    // Retorna un array amb les paraules clau d'un poema ordenades per TF-IDF descendent
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


    // Retorna un array amb les paraules clau d'un poema ordenades per TF-IDF descendent excloent les paraules buides
    public TermeFreq[] getParaulesClauPoemaParaulesBuides(int numParaulesClau, File poema, ParaulesBuidesCatala paraulesBuidesCatala){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        for(String terme : getTermesPoemaParaulesBuides(poema, paraulesBuidesCatala)){
            float tfIdfTerme = tfIdfPoemaParaulesBuides(terme, poema, paraulesBuidesCatala);
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

    // Retorna un array amb les paraules clau d'un poemari ordenades per TF-IDF descendent excloent les paraules buides
    public TermeFreq[] getParaulesClauPoemariParaulesBuides(int numParaulesClau, ParaulesBuidesCatala swc, ComptadorParaules wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];

        ArrayList<String> termesPoemari = ComptadorParaules.getTermesPoemariParaulesBuides(wcPoemari, swc);

        for(String terme : termesPoemari){


            double tfIdfTerme = tfIdfPoemariParaulesBuides(terme, wcPoemari);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, (float)tfIdfTerme);
            }

        }
        return paraulesClau;
    }

    // Retorna un array amb les paraules clau d'un poemari ordenades per TF-IDF descendent
    public TermeFreq[] getParaulesClauPoemari(int numParaulesClau, ComptadorParaules wcPoemari){

        TermeFreq[] paraulesClau = new TermeFreq[numParaulesClau];
        String[] termesPoemari = wcPoemari.getTermes();

        for(String terme : termesPoemari){
            double tfIdfTerme = tfIdfPoemari(terme, wcPoemari);
            int i=0;
            while(i<paraulesClau.length && paraulesClau[i]!=null && paraulesClau[i].frequencia > tfIdfTerme){
                i++;
            }
            if(i<paraulesClau.length){
                for(int k=paraulesClau.length-1; k>i; k--){
                    paraulesClau[k] = paraulesClau[k-1];
                }
                paraulesClau[i] = new TermeFreq(terme, (float)tfIdfTerme);
            }
        }
        return paraulesClau;
    }

    // Retorna un array de termes processats i freqüències
    public ArrayList<TermeFreq> getTermesFreqs(){
        ArrayList<TermeFreq> termesFreqs = new ArrayList<>();
        for(String terme : comptes.keySet()){
            float freq = comptes.get(terme);
            termesFreqs.add(new TermeFreq(terme, freq));
        }
        return termesFreqs;
    }


    // Cerques ****************************************************************************************

    // Retorna un array amb tots els termes processats que comencen amb un prefix
    public ArrayList<String> termesComencenAmb(String prefix){
        ArrayList<String> termesPrefix = new ArrayList<>();
        for(String terme : getTermes()){
            if(terme.startsWith(prefix.toLowerCase())){
                termesPrefix.add(terme);
            }
        }
        return termesPrefix;
    }

    // Retorna un array amb tots els termes processats que acaben amb un sufix
    public ArrayList<String> termesAcabenAmb(String sufix){
        ArrayList<String> termesPrefix = new ArrayList<>();
        for(String terme : getTermes()){
            if(terme.endsWith(sufix.toLowerCase())){
                termesPrefix.add(terme);
            }
        }
        return termesPrefix;
    }

    // Retorna un array amb tots els termes processats que acaben amb un dels sufixos
    public ArrayList<String> termesAcabenAmb(String ... sufixos){
        ArrayList<String> termesSufix = new ArrayList<>();
        for(String sufix : sufixos){
            termesSufix.addAll(termesAcabenAmb(sufix));
        }
        return termesSufix;
    }

    // Retorna un array amb tots els termes processats que contenen un afix
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