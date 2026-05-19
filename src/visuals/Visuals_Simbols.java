package visuals;

import processing.core.PApplet;

public class Visuals_Simbols extends PApplet {

    public static String POEMA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
            "Aliquam. pretium elit vel. est luctus. ornare.\n" +
            "Proin eu urna in ipsum mollis tincidunt eu quis urna.\n" +
            "Vestibulum ante ipsum primis; in faucibus orci luctus et ultrices, posuere cubilia curae;\n" +
            "Curabitur ornare blandit iaculis. Suspendisse posuere ut dolor et molestie.\n" +
            "Sed pretium turpis dui, quis fringilla enim sagittis at. Nunc rutrum nunc nec vehicula eleifend.\n" +
            "Nullam egestas accumsan hendrerit. Praesent eget massa eget dolor rutrum ultricies congue eu tellus.\n" +
            "Proin pharetra vehicula purus vitae mattis.\n" +
            "\n" +
            "Curabitur enim justo, malesuada sit amet faucibus non, rhoncus et leo.\n" +
            "Phasellus lobortis; turpis id elit condimentum, at faucibus nunc molestie.\n" +
            "Pellentesque consequat elit eu rutrum ultricies. Duis sed efficitur ante.\n" +
            "Maecenas diam nunc, congue sit amet ligula sollicitudin, sagittis elementum metus.\n" +
            "Quisque et volutpat sem, non convallis nulla. Vivamus hendrerit scelerisque lobortis.\n" +
            "Vivamus nisi ante, condimentum eget massa sit amet, mollis molestie tortor.\n" +
            "Sed suscipit ac elit vel blandit. Donec in justo lectus. Sed ac cursus purus.\n" +
            "Nullam congue tellus libero, in semper eros feugiat at. Aliquam gravida tortor et turpis ornare gravida.";

    public static void main(String[] args) {
        PApplet.main("visuals.Visuals_Simbols");
    }

    public void settings(){
        size(800, 800);
    }

    public void setup(){
        String vers = POEMA.split("\n")[0];
        print(vers);
    }

    public void draw(){
        background(255);
        dibuixaPoema(this, 100, 100, 20, 2.5f);
    }

    public void dibuixaVers(PApplet p5, int numVers, float x, float y, float factor){
        String vers = POEMA.split("\n")[numVers];
        int llarg = vers.length();

        String partVers = vers;

        if(llarg>0) {

            int llargTros = 0;
            float xTros = x;

            int posPunt = posSimbol(vers);

            // Linia sencera
            if( posPunt == -1 || posPunt == vers.length()-1){
                llargTros = vers.length();
                p5.stroke(0);
                p5.strokeWeight(2);
                p5.line(xTros, y, xTros + llargTros * factor, y);

                if(posPunt == vers.length()-1){
                    p5.circle(xTros + llargTros * factor + 7.5f, y, 5);
                }

                xTros = xTros + llargTros * factor + 5;
            }
            // Punts dins el vers (més d'una línea)
            else if(posPunt!=-1){

                while (posPunt != -1) {

                    llargTros = posPunt;

                    p5.stroke(0);
                    p5.strokeWeight(2);
                    p5.line(xTros, y, xTros + llargTros * factor, y);

                    int colSimbol = colorSimbol(this, partVers, posPunt);
                    p5.fill(colSimbol); p5.noStroke();
                    p5.circle(xTros + llargTros * factor + 7.5f , y, 5);

                    xTros += (llargTros * factor ) + 15;
                    partVers = partVers.substring(posPunt + 1);
                    posPunt = posSimbol(partVers);
                }
            }


            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(numVers + 1, x - 10, y);

            p5.textAlign(p5.LEFT, p5.CENTER);
            p5.text(llarg, xTros + 10, y);
        }
    }

    public void dibuixaPoema(PApplet p5, float x, float y, float yStep, float factor){
        int num = POEMA.split("\n").length;
        for(int v=0; v<num; v++){
            dibuixaVers(p5, v, x, y + yStep*v, factor);
        }
    }

    public static int colorSimbol(PApplet p5, String vers, int posSimbol){
        char c = vers.charAt(posSimbol);
        if(c=='.'){ return p5.color(255, 0, 0);}
        else if(c==';'){ return p5.color(0, 255, 0);}
        else if(c==','){ return p5.color(0, 0, 255);}
        else{ return p5.color(0);}
    }

    public static int posSimbol(String vers){
        int posPunt = vers.indexOf('.');
        int posPunticoma = vers.indexOf(';');
        int posComa = vers.indexOf(',');

        if(posPunt == -1 && posComa == -1 && posPunticoma == -1){
            return -1;
        }
        else if(posPunt != -1 && posComa== -1 && posPunticoma == -1){
            return posPunt;
        }
        else if(posPunt == -1 && posComa!= -1 && posPunticoma == -1){
            return posComa;
        }
        else if(posPunt == -1 && posComa == -1 && posPunticoma != -1){
            return posPunticoma;
        }
        else if(posPunt != -1 && posComa != -1 && posPunticoma == -1){
            return min(posPunt, posComa);
        }
        else if(posPunt != -1 && posComa == -1 && posPunticoma != -1){
            return min(posPunt, posPunticoma);
        }
        else if(posPunt == -1 && posComa != -1 && posPunticoma != -1){
            return min(posComa, posPunticoma);
        }
        else {
            return min(posPunt, posComa, posPunticoma);
        }
    }


}
