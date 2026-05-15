package diec;

import stats.ParaulesBuidesCatala;

import java.util.ArrayList;

public class Categoria {

    public enum CategoriaGramatical {

        // Categories gramaticals al DIEC
        ADJECTIU             ("adj. ",      "adjectiu"),
        ADJECTIU_PLURAL      ("adj. pl.", "adjectiu plural"),
        ADVERBI              ("adv. ",      "adverbi"),
        ARTICLE              ("art. ",      "article"),
        CONDICIONAL          ("cond. ",      "condicional"),
        CONJUNCIO            ("conj. ",      "conjunció"),
        SUBSTANTIU_FEMENI    ("f. ",        "substantiu femení"),
        SUBSTANTIU_FEMENI_PLURAL    ("f. pl. ",        "substantiu femení plural"),
        VERB_GERUNDI       ("ger. ",     "gerundi"),
        VERB_IMPERATIU       ("imper. ",     "imperatiu"),
        VERB_IMPERFET       ("imperf. ",     "imperfet"),
        VERB_INDICATIU       ("ind. ",     "indicatiu"),
        VERB_INFINITIU       ("inf. ",     "infinitiu"),
        INTERJECCIO         ("interj. ",     "interjecció"),
        LOCUCIO_ADJECTIVAL     ("loc. adj. ",     "locució adjectival"),
        LOCUCIO_ADVERBIAL     ("loc. adv. ",     "locució adverbial"),
        LOCUCIO_CONJUNTIVA     ("loc. conj. ",     "locució conjuntiva"),
        LOCUCIO_PREPOSITIVA    ("loc. prep. ",     "locució prepositiva"),
        SUBSTANTIU_MASCULI   ("m. ",        "substantiu masculí"),
        SUBSTANTIU_MASCULI_PLURAL   ("m. pl. ",        "substantiu masculí plural"),
        VERB_PARTICIPI_PASSAT   ("p. p. ",        "participi passat"),
        VERB_PARTICIPI_SIMPLE  ("p. s. ",        "participi simple"),
        VERB_PRESENT  ("pr. ",        "present"),
        PREPOSICIO  ("prep. ",        "preposició"),
        PRONOM  ("pron. ",        "pronom"),
        VERB_SUBJUNTIU  ("subj. ",        "subjuntiu"),
        VERB_AUXILIAR ("v. aux. / aux. ",        "verb auxiliar"),
        VERB_INTRANSITIU ("v. intr. / intr. ",        "verb intransitiu"),
        VERB_INTRANSITIU_PRONOMINAL ("v. intr. pron. / intr. pron. ",        "verb intransitiu pronominal"),
        VERB_TRANSITIU("v. tr. ",        "verb transitiu"),
        VERB_TRANSITIU_PRONOMINAL ("v. tr. pron. / tr. pron. ",        "verb transitiu pronominal"),
        DESCONEGUT           ("?",        "categoria desconeguda");

        //Atributs
        private final String abreviatura;
        private final String nomCatala;

        // Constructor
        CategoriaGramatical(String abreviatura, String nomCatala) {
            this.abreviatura = abreviatura;
            this.nomCatala   = nomCatala;
        }

        // Getters
        public String getAbreviatura() { return abreviatura; }
        public String getNomCatala()   { return nomCatala; }

        // Retrona categoria a partir de l'abreviatura
        public static CategoriaGramatical obteCategoriaDeAbreviatura(String abrev) {

            if (abrev == null || abrev.isBlank()) return DESCONEGUT;

            String normalized = abrev.trim().toLowerCase();
            for (CategoriaGramatical categoria : values()) {
                if (categoria.abreviatura.equalsIgnoreCase(normalized)) return categoria;
            }
            // Cerca parcial: p.ex. "m" és prefix de "m i f"
            for (CategoriaGramatical cat : values()) {
                if (normalized.startsWith(cat.abreviatura.toLowerCase())) return cat;
            }
            return DESCONEGUT;
        }

        // Retorna categoria a partir de la resposta a la consulta HTTP a la web del DIEC
        public static CategoriaGramatical obteCategoriaDelHTML(String html) {

            if (html == null || html.isBlank()) return DESCONEGUT;

            for (CategoriaGramatical categoria : values()) {
                if (html.indexOf(categoria.abreviatura)!=-1) return categoria;
            }
            return DESCONEGUT;
        }

        // Retorna categories a partir de la resposta a la consulta HTTP a la web del DIEC
        public static ArrayList<CategoriaGramatical> obteCategoriesDelHTML(String html) {

            ArrayList<CategoriaGramatical> categories = new ArrayList<>();

            for (CategoriaGramatical categoria : values()) {
                if (html.indexOf(categoria.abreviatura)!=-1 && !categoria.equals(DESCONEGUT)) {
                    categories.add(categoria);
                }
            }

            if(categories.size()==0){
                categories.add(DESCONEGUT);
            }
            return categories;
        }
    }

    public enum CategoriaTematica {

        // Categories temàtiques al DIEC

        // A
        ADMINISTRATIU          ("AD",        "llenguatge administratiu"),
        ARTS_GRAFIQUES          ("AF",        "arts gràfiques"),
        AGRICUTURA          ("AGA",        "agricultura"),
        AGRICUTURA_FORESTAL          ("AGF",        "ciència forestal"),
        AGRICUTURA_PESCA          ("AGP",        "pesca"),
        AGRICUTURA_RAMADARIA          ("AGR",        "explotació animal"),
        ANTROPOLOGIA         ("AN",        "antropologia"),
        ARQUITECTURA        ("AQ",        "arquitectura"),
        ART        ("AR",        "art"),
        // B
        BIBLIOTECONOMIA        ("BB",        "biblioteconomia"),
        BIOLOGIA        ("BI",        "biologia"),
        BOTANICA        ("BO",        "botànica"),
        BOTANICA_B        ("BOB",        "fongs i líquens"),
        BOTANICA_COLLECTIUS        ("BOC",        "col\\·lectius vegetals"),
        BOTANICA_INFERIOR        ("BOI", "plantes inferiors"),
        BOTANICA_SUPERIOR        ("BOS", "plantes superiors"),
        // C
        COMUNICACIO        ("CO", "comunicació"),
        // D
        DEFENSA        ("DE", "defensa"),
        DRET        ("DR", "dret"),
        // E
        ECO        ("ECO", "oficines"),
        ECONOMIA_TEORIA        ("ECT", "teoria econòmica"),
        ECONOMIA_DOMESTICA        ("ED", "economia domèstica"),
        ENGINYERIA_ELECTRICA        ("EE", "enginyeria elèctrica"),
        ENGINYERIA_INDUSTRIAL        ("EI", "enginyeria industrial"),
        ENGINYERIA_ELECTRONICA        ("EL", "enginyeria electrònica"),
        // F
        FISICA_ASTRONOMIA        ("FIA", "astronomia"),
        FISICA_GENERAL       ("FIF", "física en general"),
        FISICA_METROLOGIA       ("FIM", "metrologia"),
        FISICA_NUCLEAR       ("FIN", "física nuclear"),
        FILOLOGIA       ("FIL", "fílologia"),
        FILOLOGIA_LITERATURA       ("FLL", "literatura"),
        FILOSOFIA      ("FS", "filosofia"),
        // G
        GEOGRAFIA        ("GG", "geografia"),
        GEOLOGIA        ("GL", "geologia"),
        GEOLOGIA_GENERAL       ("GLG", "mineralogia general"),
        GEOLOGIA_MINERAL        ("GLM", "minerals"),
        GEOLOGIA_PALEO        ("GLP", "paleontologia"),
        // H
        HISTORIA_ARQUEOLOGIA        ("HIA", "arqueologia"),
        HISTORIA_GENEALOGIA        ("HIG", "genealogia i heràldica"),
        HISTORIA_GENERAL       ("HIH", "història en general"),
        HOTELERIA       ("HO", "hoteleria"),
        // I
        INDUSTRIA_FUSTA        ("IMF", "indústria de la fusta"),
        INDUSTRIA_GENERAL        ("IMI", "indústria en general"),
        INFORMÀTICA        ("INF", "informàtica"),
        INDUSTRIA_QUIMICA        ("IQ", "indústria química"),
        INDUSTRIA_ADOBERIA        ("IQA", "adoberia"),
        ISLAM        ("ISL", "islam"),
        INDUSTRIA_TEXTIL        ("IQA", "indústria tèxtil"),
        // J
        JOCS        ("JE", "jocs i espectacles"),
        // L
        LEXIC_COMU        ("LC", "lèxic comú"),
        // M
        MEDICINA        ("MD", "medicina"),
        METEREOLOGIA        ("ME", "meteorologia"),
        MINERIA        ("MI", "mineria"),
        METALLURGICA       ("ML", "metal·lúrgica"),
        MATEMATIQUES       ("MT", "matemàtiques"),
        MUSICA       ("MU", "música"),
        // N
        NUMISMATICA        ("NU", "numismàtica"),
        // O
        OBSOLET        ("OBS.", "obsolet"),
        OBRES_PUBLIQUES        ("OP", "obres públiques"),
        //P
        PEDAGOGIA        ("PE", "pedagogia"),
        POLITICA        ("PO", "política"),
        PROFESSIONS        ("PR", "professions"),
        PSICOLOGIA        ("PS", "psicologia"),
        //Q
        QUIMICA        ("QU", "química"),
        //R
        RELIGIO        ("RE", "religió"),
        //S
        SOCIOLOGIA        ("SO", "sociologia"),
        ESPORTS        ("SP", "esports"),
        //T
        TELECOMUNICACIO        ("TC", "telecomunicació"),
        TRANSPORTS_AIGUA        ("TRA", "transports per aigua"),
        TRANSPORTS_GENERAL        ("TRG", "transports en general"),
        //V
        VETERINARIA        ("VE", "veterinària"),
        //Z
        ZOOLOGIA_GENERAL        ("ZOA", "zoologia en general"),
        ZOOLOGIA_INVERTEBRATS        ("ZOI", "invertebrats"),
        ZOOLOGIA_MAMIFERS      ("ZOM", "mamífers"),
        ZOOLOGIA_OCELLS      ("ZOO", "ocells"),
        ZOOLOGIA_PEIXOS      ("ZOP", "peixos"),
        ZOOLOGIA_REPTILS      ("ZOR", "ambifibis i rèptils"),

        DESCONEGUT           ("?",        "categoria desconeguda");

        //Atributs
        private final String abreviatura;
        private final String nomCatala;

        // Constructor
        CategoriaTematica(String abreviatura, String nomCatala) {
            this.abreviatura = abreviatura;
            this.nomCatala   = nomCatala;
        }

        // Getters
        public String getAbreviatura() { return abreviatura; }
        public String getNomCatala()   { return nomCatala; }

        // Retorna categoria a partir de l'abreviatura
        public static CategoriaTematica obteCategoriaDeAbreviatura(String abreviatura) {

            if (abreviatura == null || abreviatura.isBlank()) return DESCONEGUT;

            String normalized = abreviatura.trim().toLowerCase();
            for (CategoriaTematica categoria : values()) {
                if (categoria.abreviatura.equalsIgnoreCase(normalized)) return categoria;
            }
            // Cerca parcial: p.ex. "m" és prefix de "m i f"
            for (CategoriaTematica cat : values()) {
                if (normalized.startsWith(cat.abreviatura.toLowerCase())) return cat;
            }
            return DESCONEGUT;
        }

        // Retorna categoria a partir de la resposta a la consulta HTTP a la web del DIEC
        public static CategoriaTematica obteCategoriaDelHTML(String html) {

            if (html == null || html.isBlank()) return DESCONEGUT;

            for (CategoriaTematica categoria : values()) {
                if (html.indexOf(categoria.abreviatura)!=-1) return categoria;
            }
            return DESCONEGUT;
        }

        // Retorna categories a partir de la resposta a la consulta HTTP a la web del DIEC
        public static ArrayList<CategoriaTematica> obteCategoriesDelHTML(String html) {

            ArrayList<CategoriaTematica> categories = new ArrayList<>();

            for (CategoriaTematica categoria : values()) {
                if (html!=null && html.indexOf(categoria.abreviatura)!=-1 && !categoria.equals(DESCONEGUT)) {
                    categories.add(categoria);
                }
            }

            if(categories.size()==0){
                categories.add(DESCONEGUT);
            }
            return categories;
        }

    }

}
