package stats;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWordsCatala {

    private final Set<String> paraulesBuides;

    public StopWordsCatala() {
        Set<String> conjunt = new HashSet<>();
        inicialitzarArticles(conjunt);
        inicialitzarPreposicions(conjunt);
        inicialitzarConjuncions(conjunt);
        inicialitzarPronoms(conjunt);
        inicialitzarDeterminants(conjunt);
        inicialitzarAdverbis(conjunt);
        inicialitzarAuxiliars(conjunt);
        this.paraulesBuides = Collections.unmodifiableSet(conjunt);
    }

    public boolean esParaulaBuida(String token) {
        if (token == null || token.isBlank()) return true;
        return paraulesBuides.contains(normalitza(token));
    }

    public Set<String> getParaulesBuides() {
        return paraulesBuides;
    }

    public Set<String> ambExtres(List<String> extres) {
        Set<String> ampliat = new HashSet<>(paraulesBuides);
        extres.stream()
              .map(this::normalitza)
              .forEach(ampliat::add);
        return Collections.unmodifiableSet(ampliat);
    }

    public int mida() {
        return paraulesBuides.size();
    }

    // -------------------------------------------------------------------------
    // Inicialització per categories
    // -------------------------------------------------------------------------

    /** Articles determinats i indeterminats (formes simples i contractes). */
    private void inicialitzarArticles(Set<String> s) {
        s.addAll(Set.of(
            // Determinats
            "el", "la", "els", "les", "l",
            // Salats (Balears)
            "es", "sa", "ses", "s", "so", "sos",
            // Personal
            "en", "na", "n",
            // Indeterminats
            "un", "una", "uns", "unes",
            // Article neutre / arcaic (poesia)
            "lo", "los",
            // Formes contractes (del, al, etc. → també cal tractar-les com a token separat)
            "al", "als", "del", "dels"
        ));
    }

    /** Preposicions simples i locucions prepositives freqüents. */
    private void inicialitzarPreposicions(Set<String> s) {
        s.addAll(Set.of(
            // Simples
            "a", "amb", "de", "d", "des", "en", "entre", "fins",
            "per", "per a", "pel", "pels", "sense", "sobre", "sota", "cap",
            "contra", "davant", "darrere", "durant", "envers",
            "excepte", "fora", "malgrat", "mitjançant", "segons",
            "tret", "vers", "via",
            // Locucions molt freqüents en poesia
            "a causa de", "a fi de", "a través de", "al llarg de",
            "d'acord amb", "davant de", "darrere de", "dins de",
            "fora de", "gràcies a", "junt amb", "prop de"
        ));
    }

    /** Conjuncions coordinants i subordinants. */
    private void inicialitzarConjuncions(Set<String> s) {
        s.addAll(List.of(
            // Coordinants
            "i", "e", "ni", "o", "u", "però", "sinó", "mes",
            "ara", "bé", "doncs", "llavors", "tanmateix",
            // Subordinants causals
            "perquè", "com que", "atès que", "ja que", "puix",
            // Subordinants condicionals
            "si", "sempre que", "tret que", "llevat que",
            // Subordinants concessives
            "tot i que", "malgrat que", "per bé que", "encara que",
            "si bé",
            // Subordinants finals
            "perquè", "a fi que", "per tal que",
            // Subordinants temporals
            "quan", "mentre", "fins que", "des que", "tan bon punt",
            "un cop", "cada vegada que",
            // Subordinants completives i relatives
            "que", "qui", "el qual", "la qual", "els quals", "les quals",
            "on", "com", "quan"
        ));
    }

    /** Pronoms personals febles i forts, relatius i interrogatius. */
    private void inicialitzarPronoms(Set<String> s) {
        s.addAll(List.of(
            // Personals forts
            "jo", "tu", "ell", "ella", "nosaltres", "vosaltres", "ells", "elles", "vós", "vostè", "vostès",
            // Pronoms febles (formes plenes i reduïdes)
            "em", "me", "m", "et", "t", "es", "se", "s", "el", "l", "la", "li", "ens", "us", "els", "les", "en", "hi", "ho", "n",
            // Relatius
            "que", "qui", "on", "qual",
            // Interrogatius / exclamatius
            "què", "qui", "on", "com", "quan", "quant", "quants", "quanta", "quantes",
            // Indefinits pronominats freqüents
            "res", "ningú", "algú", "alguna", "algunes", "alguns", "tothom", "cadascú", "cadascuna"
        ));
    }

    /** Determinants demostratius, possessius, indefinits i numerals bàsics. */
    private void inicialitzarDeterminants(Set<String> s) {
        s.addAll(Set.of(
            // Demostratius
            "aquest", "aquesta", "aquests", "aquestes",
            "aquell", "aquella", "aquells", "aquelles",
            "aqueix", "aqueixa", "aqueixos", "aqueixes",
            "això", "allò", "açò",
            // Possessius
            "meu", "meua", "meva", "meus", "meves",
            "teu", "teua", "teva", "teus", "teves",
            "seu", "seua", "seva", "seus", "seves",
            "nostre", "nostra", "nostres",
            "vostre", "vostra", "vostres",
            // Possessius Balear
            "ma", "mi", "mon",
            "ta", "ton","sa", "son",
            // Indefinits
            "tot", "tota", "tots", "totes",
            "altre", "altra", "altres",
            "mateix", "mateixa", "mateixos", "mateixes",
            "algun", "alguna", "alguns", "algunes", "qualque",
            "cap", "cada", "qualsevol", "qualssevol", "res",
            "ambdós", "ambdues", "cert", "certa",
            "poc", "poca", "pocs", "poques", "bastant", "bastants", "prou",
            "diversos", "diferents",
            // Numerals ordinals i cardinals freqüents
            "un", "una", "dos", "dues", "tres", "quatre", "cinc",
            "primer", "primera", "segon", "segona", "tercer"
        ));
    }

    /** Adverbis funcionals i locucions adverbials molt freqüents. */
    private void inicialitzarAdverbis(Set<String> s) {
        s.addAll(Set.of(
            // Lloc
            "aquí", "allí", "allà", "ací", "on", "arreu",
            "prop", "lluny", "amunt", "avall", "dins", "fora",
            "davant", "darrere", "dalt", "baix",
            // Temps
            "ara", "avui", "ahir", "demà", "sempre", "mai", "abans", "després",
            "aviat", "tard", "ja", "encara", "tot just",
            "sovint", "de vegades", "a vegades", "ben aviat",
            // Mode
            "bé", "mal", "així", "com", "tal", "igualment",
            "tanmateix", "també", "tampoc",
            // Quantitat
            "molt", "poc", "gens", "prou", "força", "massa",
            "tan", "tant", "tanta", "tants", "tantes", "més", "menys",
            // Afirmació / negació / dubte
            "sí", "no", "potser", "segurament", "certament",
            "efectivament", "pas"
        ));
    }

    /**
     * Formes verbals auxiliars i còpula molt freqüents que no aporten
     * contingut lèxic rellevant per al TF-IDF.
     */
    private void inicialitzarAuxiliars(Set<String> s) {
        s.addAll(Set.of(
            // Ser / ésser
            "soc", "ets", "és", "som", "sou", "són",
            "era", "eres", "érem", "éreu", "eren",
            "fer", "faig", "fa", "fas", "fan", "fui", "fores", "fou", "fórem", "fóreu", "foren", "fet",
            "sigui", "siguis", "siguem", "sigueu", "siguin",
            "ser", "ésser", "estat", "estada",
            // Estar
            "estic", "estàs", "està", "estem", "esteu", "estan",
            "estava", "estaves", "estàvem", "estàveu", "estaven",
            "estar",
            // Tenir
            "tenc", "tens", "té", "tenim", "teniu", "tenen",
            // Haver (auxiliar)
            "he", "has", "ha", "hem", "heu", "han",
            "havia", "havies", "havíem", "havíeu", "havien",
            "hagut", "haver",
            // Fer (auxiliar perifràstic)
            "vaig", "vas", "va", "vam", "vau", "van",
            // Dir
            "dir", "dic", "diu", "dit", "deien", "diuen"
        ));
    }

    // -------------------------------------------------------------------------
    // Utilitats internes
    // -------------------------------------------------------------------------

    /**
     * Normalitza un token: minúscules i eliminació d'espais superflus.
     * No elimina diacrítics catalans (accent greu/agut, trema, punt volat)
     * perquè el català els usa amb valor fonèmic distintiu.
     */
    private String normalitza(String token) {
        return token.strip().toLowerCase();
    }

    // -------------------------------------------------------------------------
    // Programa de prova mínim
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        StopWordsCatala sw = new StopWordsCatala();

        System.out.println("=== StopWordsCatala ===");
        System.out.println("Total paraules buides carregades: " + sw.mida());
        System.out.println();

        String[] mostres = {
            "el", "la", "amor", "llum", "de", "silenci",
            "però", "i", "mar", "per", "ànima", "que",
            "va", "hem", "ha", "cantar"
        };

        System.out.printf("%-15s %-10s%n", "Token", "Ignorar?");
        System.out.println("-".repeat(27));
        for (String token : mostres) {
            System.out.printf("%-15s %-10s%n",
                token,
                sw.esParaulaBuida(token) ? "✓ sí" : "✗ no");
        }

        // Exemple d'extensió amb paraules específiques del corpus
        List<String> extres = List.of("oh", "ah", "ai");
        Set<String> ampliat = sw.ambExtres(extres);
        System.out.println("\nConjunt ampliat amb interjeccions: " + ampliat.size() + " paraules");
    }

}
