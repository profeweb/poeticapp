package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ParserPoema {

    public static void main(String[] args) {
        ArrayList<String> liniesPoema = getLinies("data/poems/mariera/biografia/poema01.txt");
        for(int i=0; i<liniesPoema.size(); i++){
            System.out.println(i + " : " + liniesPoema.get(i));
        }
    }

    public static ArrayList<String> getLinies(String fitxerPoema){
        ArrayList<String> linies = new ArrayList<>();
        try {
            File fitxer = new File(fitxerPoema);
            Scanner scanner = new Scanner(fitxer);
            while(scanner.hasNextLine()){
                linies.add(scanner.nextLine());
            }
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        return linies;
    }

}
