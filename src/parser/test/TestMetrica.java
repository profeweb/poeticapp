package parser.test;

import static parser.Metrica.comptarSilabes;

public class TestMetrica {

    public static void main(String[] args) {
        System.out.println(comptarSilabes("raïm") + " == 2");    // 2
        System.out.println(comptarSilabes("veïna")+ " == 3");   // 3
        System.out.println(comptarSilabes("poesia")+ " == 4");  // 4
        System.out.println(comptarSilabes("viure")+ " == 2");   // 2
        System.out.println(comptarSilabes("casa")+ " == 2");    // 2
        System.out.println(comptarSilabes("qüestió")+ " == 3"); // 3 Falla (dona 4)
        System.out.println(comptarSilabes("aigüa") + " == 3"); // 3
        System.out.println(comptarSilabes("guitarra") + " == 3"); // 3
    }
}
