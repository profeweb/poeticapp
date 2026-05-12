package sentiments;

public class TokenAnalitzat {

    private final String original;        // forma original al text  ("d'amor,")
        private final String forma;           // forma normalitzada       ("amor")
        private final double puntuacioBase;   // puntuació al lexicó     (+2.0)
        private final double factor;          // modificador aplicat     (−1.0, 1.5, 0.5, 1.0)
        private final double puntuacioFinal;  // base × factor           (−2.0)
        private final AnalisiSentiments.Rol rol;

        public TokenAnalitzat(String original, String forma,
                              double base, double factor, AnalisiSentiments.Rol rol) {
            this.original      = original;
            this.forma         = forma;
            this.puntuacioBase = base;
            this.factor        = factor;
            this.puntuacioFinal = base * factor;
            this.rol           = rol;
        }

        public String getOriginal()       { return original;       }
        public String getForma()          { return forma;          }
        public double getPuntuacioBase()  { return puntuacioBase;  }
        public double getFactor()         { return factor;         }
        public double getPuntuacioFinal() { return puntuacioFinal; }
        public AnalisiSentiments.Rol getRol()            { return rol;            }

        public boolean esSignificatiu()   { return rol == AnalisiSentiments.Rol.LEXIC && puntuacioFinal != 0; }


}
