
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Element extends JComponent implements GraphContract{

    BufferedImage pixel;
    BufferedImage image;
    Graphics gI ;
    Color color;

    public Element(int w,int h,Color c){
        setSize(w,h);
        pixel = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        pixel.setRGB(0,0,c.getRGB());
        image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        gI = image.createGraphics();
        color = c;
        setVisible(true);
    }

    public void clearBuffer(){
        image = new BufferedImage(this.getWidth()/2,this.getHeight()/2,BufferedImage.TYPE_INT_RGB);
        gI = image.createGraphics();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image,0,0,null);
    }


    public void makePixel(int x, int y){
        gI.drawImage(pixel,x,y,null);
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
            makePixel(Math.round(x), Math.round(y));
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
            makePixel(i,round(y));
            makePixel(i,round(miny));
        }
    }

    public void bressenhamCirle(int x0,int y0, int r){
        int x = - r;
        int y = 0;
        int error = 2 - 2 * r;

        do {
            makePixel(x0 - y, y0 - x);
            makePixel(x0 + x, y0 - y);
            makePixel(x0 + y, y0 + x);
            makePixel(x0 - x, y0 + y);
            r = error;
            if (r > x) {
                error += ++x * 2 + 1;
            }
            if (r <= y) {
                error += ++y * 2 + 1;
            }
        } while (x < 0);
    }

    public void bressenhamLine(int x0, int y0, int x1 , int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;

        int A = 2 * dy;
        int B = 2 * dy - 2 * dx;
        int p = 2 * dy - dx;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xinc = (float) dx / steps;
        float yinc = (float) dy / steps;

        float x = x0;
        float y = y0;

        for(int k = 1; k <= steps; ++k){
            if(p < 0){
                makePixel( Math.round(x) + 1, Math.round(y));
                p = p + A;
            } else {
                makePixel( Math.round(x) + 1, Math.round(y) + 1);
                p = p + B;
            }
            x = x + xinc;
            y = y + yinc;
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
            makePixel(x0 + x,y0 + y);
            makePixel(x0 + x,y0 - y);
            makePixel(x0 - x,y0 + y);
            makePixel(x0 - x,y0 - y);
            makePixel(x0 + y, y0 + x);
            makePixel(x0 - y, y0 + x);
            makePixel(x0 + y, y0 - x);
            makePixel(x0 - y, y0 - x);
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
            makePixel(x, y);
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
            makePixel(x0 - x, y0 + y);
            makePixel(x0 - y, y0 - x);
            makePixel(x0 + x, y0 - y);
            makePixel(x0 + y, y0 + x);

            r = error;
            if (r > x) {
                error += ++x * 2 + 1;
            }
            if (r <= y) {
                error += ++y * 2 + 1;
            }
        } while (x < 0);
    }

    public void startAnimation(){
        Runnable thread = () -> {
            try {
                while(true){
                    dda(100+1,100,150+1,150);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(thread).start();
    }

    public static void main(String[] args) {
        JFrame frame  =  new JFrame("Animation");
        frame.setSize(700,700);
//        frame.getContentPane().setBackground(Color.black);
        Element animation = new Element(frame.getWidth()/2 ,frame.getHeight()/2,Color.green);
        frame.setVisible(true);
        frame.add(animation);
        animation.startAnimation();
    }

}
