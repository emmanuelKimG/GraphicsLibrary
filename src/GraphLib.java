import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class GraphLib extends BufferedImage {

    private final Graphics g2;

    public GraphLib(int width, int height, int imageType){
        super(width,height,imageType);
        g2 = this.createGraphics();
    }

    public Graphics thisGraphics(){ return g2;}

    public void putPixel(int x , int y, Color c)
    {
        this.setRGB(0,0,c.getRGB());
        g2.drawImage(this,x,y,null);
    }

    public void putLine(int x0,int y0,int x1, int y1){
        int difXs = abs(x1 -x0);
        int difYs = abs(y1-y0);
        int init = x0;
        float m = (float) difYs/difXs;
        float b = y0-(m*x0);
        float y = 0;

        for(var index = init; y <= y1 ; index++){
            y = (m*index) + b;
            putPixel(index,round(y),Color.BLACK);
        }
    }


    public void dda(int x0, int y0, int x1, int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;
        float steps = abs(dy);

        if(abs(dx) > abs(dy)){
            steps =  abs(dx);
        }
        float xinc =  dx/steps;
        float yinc =  dy/steps;
        float x  = x0;
        float y = y0;

        for(int i = 1 ; i<=steps ; i++){
            putPixel(Math.round(x), Math.round(y),Color.black);
            x = x+xinc;
            y = y+yinc;
        }
    }


    public void rectangle(int x, int y, int length , int height){
        dda(x,y,x+length,y);
        dda(x,y,x,y+height);
        dda(x+length,y,x+length,y+height);
        dda(x,y+height,x+length,y+height);
    }


    public void makeCircle(int x0, int y1, int radius){
        int end = x0+radius;

        for(int i = x0-radius; i<= end ; i++){
            float y = (float) (y1 + Math.sqrt(Math.pow(radius,2)- (Math.pow(i -x0,2))));
            float miny = (float) (y1 - Math.sqrt(Math.pow(radius,2)- (Math.pow(i-x0,2))));
            putPixel(i,round(y),Color.BLACK);
            putPixel(i,round(miny),Color.BLACK);
        }
    }

    public void makeCircleBressenham(int x0,int y0, int r){
        int x = - r;
        int y = 0;
        int error = 2 - 2 * r;

        do {
            putPixel(x0 - y, y0 - x, Color.black);
            putPixel(x0 + x, y0 - y, Color.black);
            putPixel(x0 + y, y0 + x, Color.black);
            putPixel(x0 - x, y0 + y, Color.black);
            r = error;
            if (r > x) {
                error += ++x * 2 + 1;
            }
            if (r <= y) {
                error += ++y * 2 + 1;
            }
        } while (x < 0);
    }


    public void bresenham(int x0, int y0, int x1 , int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;

        int A = 2 * dy;
        int B = 2 * dy - 2 * dx;
        int p = 2 * dy - dx;

        int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
        float xinc = (float) dx / steps;
        float yinc = (float) dy / steps;

        float x = x0;
        float y = y0;

        for(int k = 1; k <= steps; ++k){
            if(p < 0){
                putPixel( Math.round(x) + 1, Math.round(y), Color.black);
                p = p + A;
            } else {
                putPixel( Math.round(x) + 1, Math.round(y) + 1, Color.black);
                p = p + B;
            }
            x = x + xinc;
            y = y + yinc;
        }

    }

    public void lineaMejorado(int x0,int y0,int x1,int y1){
        float ady = (float) Float.max(x0, x1) - Float.min(x0, x1);
        float opt = (float) Float.max(y0, y1) - Float.min(y0, y1);
        float y = (float) opt / ady;
        int nX = 0;
        int nY = 0;
        y = abs(y);
        if (x0 < x1) {
            nX = 1;
        } else {
            nX = -1;
        }
        if (y0 < y1) {
            nY = 1;
        } else {
            nY = -1;
        }
        if (Math.toDegrees(Math.atan(y)) > 45) {
            y = (float) abs(ady / opt);
            for (int i = 0; i <= abs(opt); i++) {
                putPixel(x0 + (int) (i * y * nX),y0 + (i * nY),Color.black);
            }
        } else {
            for (int h = 0; h <= abs(ady); h++) {
                putPixel(x0 + h * nX,y0 + (int) (h * y * nY),Color.black);
            }
        }
    }

    public void circuloPuntoMedio(int x0, int y0, int radio){
        int x, y, p;
        x = 0;
        y = radio;
        p = 1 - radio;

        while (x < y){
            x = x + 1;
            if (p < 0){
                p = p + 2*x + 1;
            }
            else {
                y = y - 1;
                p = p + 2*(x - y) + 1;
            }
            putPixel(x0 + x,y0 + y, Color.black);
            putPixel(x0 + x,y0 - y, Color.black);
            putPixel(x0 - x,y0 + y, Color.black);
            putPixel(x0 - x,y0 - y, Color.black);
            putPixel(x0 + y, y0 + x, Color.black);
            putPixel(x0 - y, y0 + x, Color.black);
            putPixel(x0 + y, y0 - x, Color.black);
            putPixel(x0 - y, y0 - x, Color.black);
        }
    }

    public void lineaPuntoMedio(int x0,int y0,int x1,int y1){
        int x, y, dx, dy, xend, p, incE, incNE;
        dx = abs(x1 - x0);
        dy = abs(y1 - y0);
        p = 2 * dy - dx;
        incE = 2 * dy;
        incNE = 2 * (dy - dx);
        if (x0 > x1) {
            x = x1;
            y = y1;
            xend = x0;
        } else {
            x = x0;
            y = y0;
            xend = x1;
        }
        while (x <= xend) {
            putPixel(x, y, Color.black);
            x = x + 1;
            if (p <= 0) {
                p = p + incE;
            } else {
                y = y + 1;
                p = p + incNE;
            }
        }
    }

    public void eightPointsCircle(int x0, int y0, int r){
        int x = - r;
        int y = 0;
        int error = 2 - 2 * r;

        do {
           putPixel(x0 - x, y0 + y, Color.black);
           putPixel(x0 - y, y0 - x, Color.black);
           putPixel(x0 + x, y0 - y, Color.black);
           putPixel(x0 + y, y0 + x, Color.black);

            r = error;
            if (r > x) {
                error += ++x * 2 + 1;
            }
            if (r <= y) {
                error += ++y * 2 + 1;
            }
        } while (x < 0);
    }

    public void polarCircle(int xI, int yI, int radio){
        int x, y;

        for (int i = 0; i < 360; i++) {
            x = (int) (radio * Math.cos(Math.toRadians(i)));
            y = (int) (radio * Math.sin(Math.toRadians(i)));

            putPixel(xI + x, yI + y, Color.black);
            putPixel(xI - x, yI - y, Color.black);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation");
        Graphics frameGraphics = frame.getGraphics();
        frame.setSize(500,500);
        frame.setVisible(true);
        GraphLib graficas = new GraphLib(300,300,BufferedImage.TYPE_INT_RGB);
        graficas.polarCircle(100,100,100);
        graficas.setRGB(0,0,Color.red.getRGB());
        frameGraphics.drawImage(graficas,0,0,null);
        frameGraphics.drawLine(100,100,400,400);
        frame.paint(frame.getGraphics());
    }
}
