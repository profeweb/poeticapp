package visuals;

import processing.core.PApplet;

public class Visuals_Simbols extends PApplet {

    public static String POEMA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
            "Aliquam pretium elit vel est luctus ornare.\n" +
            "Proin eu urna in ipsum mollis tincidunt eu quis urna.\n" +
            "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae;\n" +
            "Curabitur ornare blandit iaculis. Suspendisse posuere ut dolor et molestie.\n" +
            "Sed pretium turpis dui, quis fringilla enim sagittis at. Nunc rutrum nunc nec vehicula eleifend.\n" +
            "Nullam egestas accumsan hendrerit. Praesent eget massa eget dolor rutrum ultricies congue eu tellus.\n" +
            "Proin pharetra vehicula purus vitae mattis.\n" +
            "\n" +
            "Curabitur enim justo, malesuada sit amet faucibus non, rhoncus et leo.\n" +
            "Phasellus lobortis turpis id elit condimentum, at faucibus nunc molestie.\n" +
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
        if(llarg>0) {
            float xTros = x;
            int posPunt = vers.indexOf('.');
            if(posPunt!=-1){

                int llargTros = posPunt;

                p5.stroke(0);
                p5.strokeWeight(2);
                p5.line(x, y, x + llargTros * factor, y);
            }



            p5.fill(0);
            p5.textSize(12);
            p5.textAlign(p5.RIGHT, p5.CENTER);
            p5.text(numVers + 1, x - 10, y);

            p5.textAlign(p5.LEFT, p5.CENTER);
            p5.text(llarg, x + llarg * factor + 10, y);
        }
    }

    public void dibuixaPoema(PApplet p5, float x, float y, float yStep, float factor){
        int num = POEMA.split("\n").length;
        for(int v=0; v<num; v++){
            dibuixaVers(p5, v, x, y + yStep*v, factor);
        }
    }


}
