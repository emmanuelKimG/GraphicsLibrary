import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DDA {
    GraphLib canvas;

    public DDA(){
        canvas = new GraphLib(600,600, BufferedImage.TYPE_INT_RGB);
    }

    public void dda(int x0, int y0, int x1, int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;
        float steps = steps = Math.abs(dy);

        if(Math.abs(dx) > Math.abs(dy)){
            steps =  Math.abs(dx);
        }
        float xinc =  dx/steps;
        float yinc =  dy/steps;
        float x  = x0;
        float y = y0;

        for(int i = 1 ; i<=steps ; i++){
            canvas.putPixel(Math.round(x), Math.round(y), Color.black);
            x = x+xinc;
            y = y+yinc;
        }
    }


    public static void main(String[] args) {
        DDA line = new DDA();
        line.dda(10,100,300,200);
    }
}
