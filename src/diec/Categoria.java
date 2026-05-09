package diec;

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
        VERB_TRANSITIU("v. tr. / tr. ",        "verb transitiu"),
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
        public static CategoriaGramatical obteCategoriaDeHTML(String html) {

            if (html == null || html.isBlank()) return DESCONEGUT;

            for (CategoriaGramatical categoria : values()) {
                if (html.indexOf(categoria.abreviatura)!=-1) return categoria;
            }
            return DESCONEGUT;
        }
    }

    public enum CategoriaTematica {

        // Categories temàtiques al DIEC

    }

}
