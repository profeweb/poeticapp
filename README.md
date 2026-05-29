# PoeticAPP

**PoeticAPP** és una aplicació de codi obert desenvolupada en **Java** i **Processing** per a l'anàlisi i la visualització de dades no estructurades de poemaris en llengua catalana. El projecte aplica tècniques de processament del llenguatge natural (PLN/NLP) per extreure coneixement dels textos poètics: freqüències, paraules clau, sentiments, entitats i estructura mètrica.

![Pantalla Resum](https://github.com/profeweb/poeticapp/blob/master/data/grafics/poeticapp01.png)
---

## Taula de continguts

- [Descripció del projecte](#descripció-del-projecte)
- [Corpus literari](#corpus-literari)
- [Estructura del projecte](#estructura-del-projecte)
- [Paquets i classes principals](#paquets-i-classes-principals)
- [Visualitzacions](#visualitzacions)
- [Fitxers de dades](#fitxers-de-dades)
- [Dependències](#dependències)
- [Instal·lació i execució](#installació-i-execució)

---

## Descripció del projecte

PoeticAPP permet explorar i analitzar poemaris en català a través d'una interfície gràfica interactiva construïda amb Processing. Les funcionalitats principals inclouen:

- **Segmentació i tokenització** de textos poètics (versos, estrofes, frases)
- **Recompte de paraules** i freqüències de termes
- **Filtratge per paraules buides** en català
- **Lematització** basada en un diccionari de lemes del català
- **Càlcul del TF-IDF** (Term Frequency – Inverse Document Frequency) per identificar les paraules clau de cada poema o poemari
- **Anàlisi de sentiments** amb detecció de polaritat, negadors i modificadors
- **Reconeixement d'entitats nomenades (NER)** amb etiquetatge BIO (persones, llocs, organitzacions)
- **Còmput de síl·labes** en català seguint les regles de diftongs i hiats (Softcatalà)
- **Consulta al DIEC** (Diccionari de l'Institut d'Estudis Catalans) per a categories gramaticals
- **Visualització** de dades amb diagrames de barres, línies, sectors i núvols de paraules

---

## Corpus literari

El corpus inclou poemes de la lírica catalana moderna i contemporània, organitzat per **autor** i **poemari** amb l'any de publicació. L'únic autor amb poemaris en text complet és **Miquel Àngel Riera (1930–1996)**, que compta amb vuit poemaris digitalitzats:

| Poemari | Any |
|---|---|
| Poemes a Nai | 1960 |
| Biografia | 1970 |
| La bellesa de l'home | 1972 |
| Poemes de l'enyorament | 1972 |
| Paràbola i clam de la cosa humana | 1974 |
| Llibre de Benaventurances | 1977 |
| Poemes ocasionals | 1981 |
| El pis de la badia | 1993 |

La base de dades també registra metadades d'altres poetes mallorquins i catalans com Miquel Costa i Llobera, Joan Alcover, Maria Antònia Salvà, Bartomeu Rosselló-Pòrcel, Josep Maria Llompart, Blai Bonet, Bartomeu Fiol, Antònia Vicens, Damià Huguet, Ponç Pons i Sebastià Alzamora.

---

## Estructura del projecte

```
poeticapp/
├── src/                        # Codi font Java
│   ├── Main.java               # Punt d'entrada de l'aplicació
│   ├── parser/                 # Segmentació i model de dades
│   ├── comptes/                # Recompte de paraules i TF-IDF
│   ├── lema/                   # Lematització
│   ├── ner/                    # Reconeixement d'entitats nomenades
│   ├── sentiments/             # Anàlisi de sentiments
│   ├── silabes/                # Còmput de síl·labes
│   ├── diec/                   # Consultes al diccionari DIEC
│   ├── gui/                    # Interfície gràfica (Processing)
│   └── visuals/                # Visualitzacions específiques
├── data/
│   ├── poems/                  # Textos dels poemes (.txt)
│   ├── diccionaris/            # Diccionaris NLP (JSON, TXT)
│   ├── fonts/                  # Fonts tipogràfiques (.ttf)
│   └── img/                    # Imatges de la interfície (.png, .jpg)
├── libs/
│   └── json-20240303.jar       # Biblioteca org.json
└── Poetica.iml                 # Fitxer de projecte IntelliJ IDEA
```

---

## Paquets i classes principals

### `parser` — Model de dades i segmentació

Conté el model jeràrquic del corpus poètic i el processador que transforma els fitxers de text en objectes Java. És el nucli estructural del projecte: tota l'anàlisi posterior depèn d'aquest paquet.

| Classe | Descripció |
|---|---|
| `ParserPoema` | Llegeix un fitxer `.txt` i el segmenta en estrofes, frases i versos. Detecta línies en blanc com a separadors d'estrofa, el punt final com a delimitador de frase i reconeix els caràcters vàlids del català (inclou la l·l geminada i tots els diacrítics). |
| `Poema` | Objecte que representa un poema: número, títol i llista d'estrofes. Proporciona mètodes per obtenir el nombre de versos, estrofes i la longitud màxima. |
| `Poemari` | Agrupació de poemes d'un mateix llibre. Disposa del mètode `parsePoemes()` per llegir tots els fitxers `.txt` d'una carpeta. |
| `Autor` | Representa un poeta amb nom, any de naixement i llista de poemaris. |
| `Estrofa` | Contenidor de frases (`Frase`). Numerades seqüencialment dins el poema. |
| `Frase` | Seqüència de versos que acaba en punt. |
| `Vers` | Línia del poema amb la seva llista de `Token` (paraules i símbols). |
| `Token` | Interfície comuna per a paraules i símbols. |
| `Paraula` | Token de tipus paraula: conté el text i permet la lematització. |
| `Simbol` | Token de tipus puntuació o signe especial. |
| `DadesPoemaris` | Utilitats per agregar estadístiques de tots els poemaris (nombre de poemes, versos, paraules, etc.). |
| `Visualitzacions` | Genera representacions visuals dels poemes directament amb Processing. |

---

### `comptes` — Recompte de paraules i TF-IDF

Implementa les tècniques estadístiques fonamentals del PLN aplicades als poemes i poemaris. Permet calcular freqüències, identificar paraules clau i construir núvols de paraules.

| Classe | Descripció |
|---|---|
| `ComptadorParaules` | Classe central del paquet. Tokenitza poemes i poemaris, acumula freqüències en un `HashMap<String, Integer>`, filtra paraules buides i calcula TF, IDF i TF-IDF. Inclou cerques per prefix, sufix i contingut. |
| `TF_IDF` | Implementació independent i estàtica del càlcul TF-IDF (Term Frequency – Inverse Document Frequency). Calcula la rellevància d'un terme en un document respecte a una col·lecció. |
| `TermeFreq` | Parell (terme, freqüència/puntuació TF-IDF). Usat per ordenar i mostrar les paraules clau. |
| `ParaulaNigul` | Representa una paraula per al núvol visual amb posició, mida i color proporcionals a la freqüència. |
| `ParaulesBuidesCatala` | Carrega el diccionari JSON de paraules buides i proporciona el mètode `esParaulaBuida(String)` per filtrar articles, preposicions, conjuncions, pronoms, etc. |

**Tècnica TF-IDF implementada:**

```
TF(t, d)  = ocurrències_de_t_en_d / total_termes_en_d
IDF(t, D) = log(N / n)   on N = total de documents, n = documents que contenen t
TF-IDF    = TF × IDF
```

---

### `lema` — Lematització

Redueix les formes flexionades a la seva forma base (lema) gràcies a un gran diccionari de lemes del català. Permet treballar amb formes canòniques i evitar la fragmentació lèxica.

| Classe | Descripció |
|---|---|
| `DiccionariLemesCatalà` | Carrega el fitxer `lemmatization-ca.txt` (591.534 entrades) i construeix un índex invers: `forma_flexionada → llista_de_lemes` i un conjunt de lemes canònics. |
| `Lematitzador` | Aplica la lematització a un token: cerca la forma al diccionari, gestiona ambigüitats (un token pot tenir múltiples lemes), i fa un *fallback* sense diacrítics si la forma accentuada no es troba. |
| `ResultatToken` | Encapsula el resultat de la lematització: token original, lema principal, llista de lemes possibles i estat (`JA_ES_LEMA`, `TROBAT`, `AMBIGU`, `DESCONEGUT`). |

---

### `ner` — Reconeixement d'entitats nomenades

Implementa un etiquetador d'entitats nomenades basat en diccionari amb esquema d'etiquetatge **BIO** (Begin – Inside – Outside), estàndard en PLN.

| Classe | Descripció |
|---|---|
| `EtiquetadorEntitats` | Processa vers per vers, tokenitza tractant les contraccions catalanes (apòstrof: `l'Empordà` → `l'` + `Empordà`), i etiqueta els tokens amb el format CoNLL: `B-PER`, `I-PER`, `B-LOC`, `O`, etc. Extreu i mostra estadístiques de cobertura NER. |
| `DiccionariEntitats` | Carrega el fitxer JSON d'entitats nomenades i construeix la llista d'entrades ordenades per longitud (per prioritzar expressions multiparaula). |
| `EntradaDiccionari` | Representa una entrada del diccionari: tokens normalitzats, tipus d'entitat i flag de majúscula (per distingir `Rosa` (nom propi) de `rosa` (flor)). |
| `EntitatNomenada` | Defineix els tipus d'entitat: `PER` (persona), `LOC` (lloc), `ORG` (organització), `OBR` (obra artística), `DAT` (data). |
| `TokenEtiquetat` | Token amb la seva etiqueta BIO i la forma normalitzada. Proporciona mètodes `esEntitat()`, `esInici()`, `getTipus()`. |

---

### `sentiments` — Anàlisi de sentiments

Analitza la càrrega emocional i la polaritat dels versos i poemes, tenint en compte negadors i modificadors de la intensitat.

| Classe | Descripció |
|---|---|
| `DiccionariSentiments` | Gestiona tres estructures: **lexicó de polaritat** (paraula → puntuació de −2.0 a +2.0), **negadors** (paraules que inverteixen la polaritat: *no, mai, tampoc, sense…*) i **modificadors** (intensificadors com *molt, força, extremadament* i diminuïdors com *poc, quasi, gairebé*). Es pot construir des de codi o llegint el fitxer JSON. |
| `AnalisiSentiments` | Processa un vers token per token: aplica el lexicó, detecta negadors en la finestra prèvia i escala la puntuació amb els modificadors. Calcula la polaritat global del vers i del poema. |
| `TokenAnalitzat` | Resultat de l'anàlisi d'un token: forma, puntuació de polaritat, si ha estat negat i el factor modificador aplicat. |
| `VersAnalitzat` | Agrupa tots els `TokenAnalitzat` d'un vers i calcula la puntuació total i el sentiment resultant (MOLT_POSITIU, POSITIU, NEUTRE, NEGATIU, MOLT_NEGATIU). |

**Escala de polaritat:**

| Rang | Sentiment |
|---|---|
| > 1.2 | MOLT_POSITIU |
| 0.4 … 1.2 | POSITIU |
| −0.1 … 0.4 | NEUTRE |
| −0.4 … −1.2 | NEGATIU |
| < −1.2 | MOLT_NEGATIU |

---

### `silabes` — Còmput de síl·labes

Implementa un algorisme de còmput sil·làbic per al català seguint les regles ortogràfiques de Softcatalà.

| Classe | Descripció |
|---|---|
| `ComptadorSilabes` | Compta les síl·labes d'una paraula catalana: gestiona vocals fortes (a, e, o) i febles (i, u sense accent), diftongs creixents i decreixents, hiats (vocals amb dièresi, vocals febles accentuades, dues vocals fortes adjacents), la `u` muda de `gu/qu`, i la l·l geminada. Retorna un mínim d'1 síl·laba per paraula. |

---

### `diec` — Consulta al Diccionari de l'IEC

Permet fer consultes en temps real al Diccionari de la Llengua Catalana de l'Institut d'Estudis Catalans per obtenir informació gramatical i temàtica dels termes.

| Classe | Descripció |
|---|---|
| `ConsultaWebDIEC` | Fa peticions HTTP GET al portal `dlc.iec.cat` per a un terme donat, analitza la resposta HTML i extreu l'identificador d'entrada i la definició. |
| `AnalitzadorCategories` | Analitza el contingut HTML del DIEC i extreu categories gramaticals (substantiu, verb, adjectiu…) i categories temàtiques (música, botànica, medicina…). |
| `Categoria` | Representa una categoria gramatical o temàtica amb el nom i el tipus. |

---

### `gui` — Interfície gràfica (Processing)

Conté tots els components visuals de l'aplicació, implementats amb la llibreria Processing. L'arquitectura segueix el patró **pantalla–component**, on cada pantalla agrupa un conjunt de components reutilitzables.

**Pantalles principals** (`Gui.PANTALLA`): `INICI`, `EXPLORAR`, `AUTORS`, `AUTOR_RESUM`, `AUTOR_OBRA`, `AUTOR_VISUAL`, `AUTOR_EDITA`, `LLIBRES`, `LLIBRE_RESUM`, `LLIBRE_OBRA`, `LLIBRE_VISUAL`, `POEMES`, `POEMA_RESUM`, `QUANTITATIVES`, `QUALITATIVES`, `CRONOLOGIQUES`, `RELACIONALS`, `TEMATIQUES`.

| Classe | Descripció |
|---|---|
| `Gui` | Classe principal que inicialitza i coordina tots els components i pantalles. Gestiona els events de ratolí i teclat. |
| `GuiElement` | Classe base abstracta per a tots els components de la interfície. |
| `Pantalla` | Contenidor d'elements GUI que es mostren i oculten conjuntament. |
| `Boto`, `BotoIcona`, `BotoSeleccionable`, `BotoOpcio`, `BotoFavorit` | Variants de botons amb diferents comportaments i estils visuals. |
| `BotonsGrup`, `BotoOpcioGrup`, `BotonsPoema`, `BotonsEstrofa`, `BotonsVers` | Grups de botons associats a unitats poètiques. |
| `BotonsPaginacio` | Controls de navegació paginada. |
| `EntradaText`, `EntradaCercador`, `EntradaTextLlista` | Camps d'entrada de text i cerca. |
| `GraellaTarja`, `GraellaTarjaCerca` | Graelles de targes per mostrar llistes d'autors, poemaris o poemes. |
| `Tarja`, `TarjaCerca`, `TarjaResum` | Components de tarja individual. |
| `Taula`, `TaulaPaginada` | Taules de dades paginades. |
| `Diagrama`, `DiagramaBarres`, `DiagramaBarresApilat`, `DiagramaLinies`, `DiagramaLiniesApilat`, `DiagramaSectors` | Visualitzacions de dades: barres, línies i sectors circulars. |
| `Eix`, `EixQuantitatiu`, `ElementDades`, `PuntDades`, `Barra`, `BarraApilada`, `Sector` | Components interns dels diagrames. |
| `Menu`, `MenuApp`, `SubMenu`, `Desplegable` | Elements de navegació. |
| `Resum`, `ResumApp`, `ResumAutor`, `ResumLlibre`, `ResumPoema` | Panells de resum amb estadístiques. |
| `Titol`, `Titulars` | Components de capçalera i text prominent. |
| `CasellaSeleccio`, `CasellaSeleccioGrup`, `Seleccionable`, `SeleccionablesGrup` | Caselles de selecció múltiple. |
| `SelectorImatge` | Component per seleccionar i carregar imatges d'autors i poemaris. |
| `Colors` | Paleta de colors de l'aplicació. |
| `Fonts` | Gestió de les fonts tipogràfiques (inclou Noto Emoji per a emojis). |
| `Mides` | Constants de dimensions i espaiat de la interfície. |

---

### `visuals` — Visualitzacions específiques

Conté visualitzacions concretes que combinen les dades dels poemes amb diagrames renderitzats amb Processing.

| Classe | Descripció |
|---|---|
| `DiagramaLinies_Anys` | Diagrama de línies que mostra l'evolució temporal de dades al llarg dels anys de publicació dels poemaris. |
| `Visuals_Cites_Parsed` | Visualitza cites i fragments de poemes obtinguts del parser. |
| `Visuals_Enunciacions_Parsed` | Representa visualment les enunciacions i frases dels poemes. |
| `Visuals_Simbols` | Visualitza la distribució i freqüència dels símbols tipogràfics als poemes. |
| `Visuals_Simbols_Parsed` | Versió que treballa sobre dades extretes pel parser. |

---

## Visualitzacions

### Visualitzacions quantitatives

**Diagrama de barres — Longitud de versos (lletres per vers)**

![](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Poemes%20a%20Nai%20-%20P1-4%20num%20lletres%20(blocs)-01.png)

Cada barra horitzontal representa un vers del poema, i la seva longitud és proporcional al nombre de lletres. Les línies de referència marquen la mitjana del poemari (vermell), del poema (blau) i de l'estrofa (verd). Permet visualitzar d'una ullada el ritme visual i les variacions de longitud dins i entre estrofes.

---

**Diagrama de barres — Ocurrències d'un terme als poemaris**

![Ocurrències del terme vida als 8 poemaris](https://github.com/profeweb/poeticapp/blob/master/data/grafics/vida%20-%20num%20Ocurrencies%20(corpus)-01.png)

El diagrama mostra la distribució del terme *«vida»* (103 ocurrències totals) al llarg dels 8 poemaris. *Biografia* (1970) concentra el 38,83% de les ocurrències (40 aparicions), seguit d'*El pis de la badia* (23, 22,33%) i *La bellesa de l'home* (15, 14,56%). El terme té presència en tots els poemaris, cosa que evidencia la seva condició de tema transversal de l'obra.

---

**Diagrama de barres — Versos per poema**

![Nombre de versos en cada poema del poemari La Bellesa de l'home](https://github.com/profeweb/poeticapp/blob/master/data/grafics/La%20bellesa%20de%20l'home%20-%20num%20versos%20(poemes)-01.png)

La distribució de versos per poema revela l'estructura formal del poemari. A *La bellesa de l'home*, el 7è poema és el més llarg (127 versos, 19,01%), mentre que el 9è és el més curt (4 versos, 0,60%). Aquesta variabilitat indica que Riera no segueix una estructura mètrica regular.

---

**Diagrama de sectors — Distribució de versos al corpus**

![Diagrama de sectors amb la distribució de versos entre els poemaris](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Coprus%20MA%20Riera%20-%20Num%20Versos%20x%20Poemari%20-%20sectors%20.png)

El diagrama circular mostra el pes relatiu de cada poemari en el conjunt de l'obra. *Biografia* (1970) conté el sector més gran amb 989 versos (22,33%), seguit d'*El pis de la badia* (700, 15,80%) i *Paràbola i clam* (707, 15,96%). *Poemes de l'enyorament* és el poemari més breu (224 versos, 5,06%).

---

**Diagrama de línies — Evolució de termes clau al llarg dels poemaris**

![Evolució dels termes amor, cos, home i vida al llarg dels 8 poemaris](https://github.com/profeweb/poeticapp/blob/master/data/grafics/termes%20amor%2C%20cos%2C%20home%2C%20vida.%20-%20num%20Ocurrencies%20(corpus)-01.png)

El diagrama de línies permet comparar l'evolució de fins a quatre termes simultàniament al llarg de la cronologia dels poemaris. *Vida* (groc) assoleix el màxim a *Biografia* (40), mentre que *amor* (vermell) ho fa a *Paràbola* i *El pis de la badia*. *Home* (taronja) concentra les aparicions a *La bellesa de l'home* i *Paràbola*.

---

**Visualització radial — Paraules per vers**

![Visualització radial de paraules per vers del poema 15 d'El pis de la badia](https://github.com/profeweb/poeticapp/blob/master/data/grafics/El%20pis%20de%20la%20badia%20-%20P15-01.png)

La visualització radial ad hoc combina un diagrama de sectors (l'angle proporcional al nombre de versos de cada estrofa) amb barres radials (la longitud proporcional al nombre de paraules de cada vers). Les línies de referència de color marquen les mitjanes del poemari (vermell), del poema (blau) i de l'estrofa (verd). Permet detectar d'un cop d'ull les variacions internes de cada estrofa.

---

**Visualització radial — Síl·labes per vers**

![Visualització radial de síl·labes per vers del poema 8 de Poemes a Nai](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Poemes%20a%20Nai%20-%20P8%20-%20num%20sil.labes%20(radial)-01.png)

La mateixa estructura radial aplicada al còmput de síl·labes. En el 8è poema de *Poemes a Nai*, les estrofes presenten una regularitat relativa al voltant de les 28–31 síl·labes per vers, amb algunes excepcions puntuals. Les mitjanes per estrofa (de 22,00 a 28,91 síl·labes) mostren la variació entre les sis estrofes del poema.

---

### Visualitzacions qualitatives

**Núvol de paraules — Llibre de Benaventurances (150 paraules)**

![Núvol de 150 paraules del Llibre de Benaventurances](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Llibre%20de%20Benaventurances%20(1977)%20-%20n%C3%ADgul%20paraules-01.png)

El núvol de paraules representa les 150 paraules més freqüents del poemari, amb la mida proporcional a la freqüència. Els termes dominants —*món*, *vida*, *cos*, *amor*, *veritat*, *benaventurat*— defineixen el camp semàntic central d'aquest poemari de marcat to filosòfic i humanista. Les paraules buides ja han estat eliminades prèviament.

---

**Núvol de paraules — Corpus complet de Miquel Àngel Riera (250 paraules)**

![Núvol de 250 paraules del corpus lèxic complet de M.A. Riera](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Corpus%20MA%20Riera%20-%20n%C3%ADgul%20paraules.png)

El núvol del corpus complet (250 paraules) revela el vocabulari nuclear de l'obra de Riera. *Vida*, *home*, *amor*, *món* i *cos* dominen amb diferència, confirmant que el cos humà, l'existència i el món social constitueixen els tres eixos temàtics centrals de tota la seva producció poètica.

---

**Símbols de puntuació i ritme**

![Visualització dels símbols de puntuació del poema 9 de Poemes a Nai](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Poemes%20a%20Nai%20-%20P1%2C2%2C%203%20i%209%20-%20s%C3%ADmbols-01.png)

Cada línia representa un vers, amb els tokens de paraula com a guions i els símbols de puntuació com a punts de color: vermell (punt final), verd (punt i coma), blau (coma) i gris (altres). La distribució dels símbols permet visualitzar el ritme pausat o intens del poema i detectar l'estructura sintàctica sense llegir el text.

---

**Citacions textuals**

![Visualització de les citacions del poema 2 de Poemes a Nai](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Poemes%20a%20Nai%20-%20P2%20-%20cites%20textuals-01.png)

La visualització de citacions destaca en groc els tokens que formen part de cites textuals o expressions marcades tipogràficament dins del poema. Permet localitzar d'una ullada la presència i densitat de veus alienes o referències intertextuals en l'obra de Riera.

---

**Entitats nomenades**

![Visualització d'entitats nomenades en els primers 20 versos del poema 3 de Poemes ocasionals](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Poemes%20ocasionals%20-%20P3%20-%20ners-01.png)

Cada vers es representa com una línia de tokens. Les entitats nomenades es ressalten amb colors: vermell per a persones (PER), verd per a localitzacions (LOC) i blau per a miscel·lània (MISC). En el 3r poema de *Poemes ocasionals*, la densitat d'entitats és especialment alta, reflectint el caràcter de dedicatòria del poema a Rafael Alberti.

---

**Relacions temàtiques**

![Visualització de les relacions entre termes i àrees temàtiques](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Biografia%20-%20termes%20relacionats%20amb%20ART-01.png)

El gràfic relacional connecta els tokens del vers amb les categories temàtiques a les quals pertanyen (natura, temps, cos humà, emoció, etc.). Cada línia representa un vincle semàntic, i el gruix és proporcional a la intensitat de la relació. Permet identificar quins camps semàntics s'activen simultàniament en un poema donat.

---

**Visualització de l'anàlisi de sentiments**

![Visualització de l'anàlisi de sentiments del poema 3 de Poemes ocasionals](https://github.com/profeweb/poeticapp/blob/master/data/grafics/Biografia%20-%20P6%20-%20sentiments-01.png)

La visualització de sentiments mostra, vers a vers, la puntuació de polaritat. Els tokens responsables de la puntuació apareixen ressaltats. Permet seguir l'arc emocional del poema i identificar els moments de màxima intensitat afectiva.

---

## Fitxers de dades

### Diccionaris NLP (`data/diccionaris/`)

#### `diccionari_sentiments_ca.json`
Diccionari d'anàlisi de sentiments en català estructurat en tres seccions:

```json
{
  "lexic": [
    { "paraula": "excel·lent", "puntuacio": 2.0, "sentiment": "MOLT_POSITIU" },
    { "paraula": "mort",       "puntuacio": -2.0, "sentiment": "MOLT_NEGATIU" }
  ],
  "negadors": ["no", "mai", "tampoc", "ni", "sense", "cap", "ningú", "res"],
  "modificadors": [
    { "paraula": "molt", "factor": 1.5, "rol": "INTENSIFICADOR" },
    { "paraula": "poc",  "factor": 0.5, "rol": "DIMINUIDOR" }
  ]
}
```

Cobreix les categories: MOLT_POSITIU (> 1.2), POSITIU (0.4–1.2), NEUTRE, NEGATIU (−0.4 a −1.2) i MOLT_NEGATIU (< −1.2).

#### `paraules_buides_ca.json`
Llista de paraules gramaticals del català que s'exclouen de les anàlisis de contingut, classificades per categoria:

```json
{
  "paraules_buides": [
    { "tipus": "ARTICLE",     "termes": ["el", "la", "els", "les", "un", "una", ...] },
    { "tipus": "PREPOSICIO",  "termes": ["a", "amb", "de", "per", "sobre", ...] },
    { "tipus": "CONJUNCIO",   "termes": ["i", "o", "però", "perquè", "si", ...] },
    { "tipus": "PRONOM",      "termes": ["jo", "tu", "ell", "ella", "ho", ...] }
  ]
}
```

#### `entitats_nomenades.json`
Diccionari per al reconeixement d'entitats nomenades (NER), amb entitats multiparaula:

```json
{
  "entitats": [
    { "text": "Miquel Àngel Riera",  "tipus": "PER" },
    { "text": "Pau Casals",          "tipus": "PER" },
    { "text": "Joan Miró",           "tipus": "PER" },
    { "text": "Anton Vivaldi",       "tipus": "PER" },
    { "text": "Anna Magdalena Bach", "tipus": "PER" }
  ]
}
```

Tipus d'entitat: `PER` (persona), `LOC` (lloc), `ORG` (organització), `OBR` (obra), `DAT` (data).

#### `lemmatization-ca.txt`
Fitxer TSV (tabulador) amb **591.534 entrades** de lemes del català. Cada línia conté:

```
lema    forma_flexionada
```

Exemples:
```
amar    ama
amar    amava
amar    amaran
àbac    àbacs
```

Cobreix formes nominals, verbals i adjectivals de la llengua catalana.

---

### Textos dels poemes (`data/poems/`)

Organitzats per carpetes amb el format `Nom Autor (any_naixement)/Títol Poemari (any)/poema01.txt`:

```
data/poems/
└── Miquel Àngel Riera (1930)/
    ├── Poemes a Nai (1960)/                (13 poemes)
    ├── Biografia (1970)/                   (13 poemes)
    ├── La bellesa de l'home (1972)/        (13 poemes)
    ├── Poemes de l'enyorament (1972)/      (14 poemes)
    ├── Paràbola i clam... (1974)/          (13 poemes)
    ├── Llibre de Benaventurances (1977)/   (26 poemes)
    ├── Poemes ocasionals (1981)/           (05 poemes)
    └── El pis de la badia (1993)/          (30 poemes)
```

Cada fitxer `.txt` conté el text del poema en text pla UTF-8, amb línies en blanc com a separadors d'estrofa.

---

### Fonts tipogràfiques (`data/fonts/`)

| Fitxer | Ús |
|---|---|
| `Astila.ttf` | Font decorativa per a títols |
| `GameOver.ttf` | Font estilitzada alternativa |
| `Graffiti.ttf` | Font de display |
| `NotoEmoji-Regular.ttf` i variants | Suport complet d'emojis Unicode |
| `NotoColorEmoji.ttf` | Emojis en color |

---

## Dependències

### Llibreries Java

| Biblioteca | Versió | Ús |
|---|---|---|
| **Processing Core** | 3.5.4 | Motor gràfic principal: renderitzat 2D, gestió de events, fonts i imatges |
| **org.json** | 20240303 | Lectura i escriptura de fitxers JSON (diccionaris NLP) |

### Requisits del sistema

| Requisit | Versió mínima |
|---|---|
| Java JDK | 11 o superior |
| Processing | 3.5.4 |
| IntelliJ IDEA | 2021 o superior (recomanat) |
| Sistema operatiu | Windows, macOS o Linux |

---

## Instal·lació i execució

**1. Clonar el repositori:**
```bash
git clone https://github.com/profeweb/poeticapp.git
cd poeticapp
```

**2. Configurar les dependències:**

Afegiu al classpath del projecte:
- `libs/json-20240303.jar`
- El directori `core/library` de la vostra instal·lació de Processing 3.5.4
- `libs/jsoup-1.21.2.jar` (si useu les consultes DIEC)

**3. Configurar la ruta del corpus:**

Al fitxer `src/Main.java`, actualitzeu la variable `rutaCarpetaArrel` amb la ruta absoluta a la carpeta `data/poems/` del vostre sistema:
```java
String rutaCarpetaArrel = "/ruta/al/vostre/projecte/data/poems/";
```

**4. Executar l'aplicació:**

Compileu i executeu la classe `Main`. L'aplicació s'inicia en mode pantalla completa.

---

## Arquitectura NLP del projecte

```
Fitxers .txt (corpus)
        │
        ▼
   ParserPoema                ← Segmentació: estrofes, versos, tokens
        │
   ┌────┴────┬──────────┬──────────┬──────────┬──────────┐
   ▼         ▼          ▼          ▼          ▼          ▼
ComptadorP  Lematitz.  NER        Sentiments Síl·labes  DIEC
TF-IDF      DicLemes   DicEntit.  DicSentim. ComptSil.  Web
ParBuides   ResultTok  EtiqBIO    VersAnal.             Categ.
        │
        ▼
   Visualitzacions (Processing GUI)
   DiagramaBarres | DiagramaLinies | DiagramaSectors | NúvolParaules
```

---

## Llicència

Projecte acadèmic de codi obert. Consultar l'autor per a usos derivats.

---
