import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLOutput;

import static java.lang.Math.round;

public class Element3D extends Element{

    int xc = -150;
    int yc = -150;
    int zc = 250;

    public Element3D(int w, int h, Color c) {
        super(w, h, c);
    } 
    
    
    public void line3D(int x1,int y1, int z1,int x2,int y2, int z2)
    {
        float xi = x1 + ((float)(xc*z1)/ zc);
        float yi = y1 + ((float)(yc*z1)/ zc);
        float xf = x2 + ((float)(xc*z2) / zc );
        float yf = y2 + ((float)(yc*z2) / zc );

        dda(round(xi),round(yi),round(xf),round(yf));
    }

    public void rectangle3D(int x, int y,int z, int length , int height, int anchor)
    {
        float xs = x + ((float)(xc*z) / zc );
        float ys = y + ((float)(yc*z) / zc );

        float xp =  (x+anchor + (float)(xc*(z+anchor)/zc));
        float yp =  (y+anchor + (float)(yc*(z+anchor)/zc));

        rectangle(round(xs),round(ys),length,height);
        rectangle(round(xp),round(yp),length,height);
        dda(round(xs),round(ys),round(xp),round(yp));
        dda(round(xs),round(ys+height),round(xp),round(yp+height));
        dda(round(xs+length),round(ys),round(xp+length),round(yp));
        dda(round(xs+length),round(ys+length),round(xp+length),round(yp+height));
    }

    public void cube3D(int x, int y, int l)
    {
        rectangle3D(x,y,0,l,l,l);
    }

//    public void rot(double Ax, double Ay, double Az)
//    {
//
//        for(int x1=0; x1<=7; x1++){
//            double r[]={0,0,0,0};
//
//            double [][] T = {
//                    {Math.cos(Ax),-Math.sin(Ax),0,0},
//                    {Math.sin(Ax),Math.cos(Ax),0,0},
//                    {0,0,1,0},
//                    {0,0,0,1}
//            };
//            int i,j;
//            for(i=0;i<4;i++){
//                for(j=0;j<4;j++){
//                    r[i] += P[j]*T[i][j];
//                }
//            }
//            cube[x1][0]=(int)r[0];
//            cube[x1][1]=(int)r[1];
//            cube[x1][2]=(int)r[2];
//            cube[x1][3]=(int)r[3];
//        }
//
//
//        for(int x1=0; x1<=7; x1++){
//            double r[]={0,0,0,0};
//            double [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
//            double [][] T = {
//                    {Math.cos(Ay), 0, Math.sin(Ay), 0},
//                    {0, 1, 0, 0},
//                    {-Math.sin(Ay), 0, Math.cos(Ay), 0},
//                    {0, 0, 0, 1}
//            };
//            int i,j;
//            for(i=0;i<4;i++){
//                for(j=0;j<4;j++){
//                    r[i] += P[j]*T[i][j];
//                }
//            }
//            cube[x1][0]=(int)r[0];
//            cube[x1][1]=(int)r[1];
//            cube[x1][2]=(int)r[2];
//            cube[x1][3]=(int)r[3];
//        }
//
//
//        for(int x1=0; x1<=7; x1++){
//            double r[]={0,0,0,0};
//            double [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
//            double [][] T = {
//                    {1,0,0,0},
//                    {0,Math.cos(Az),-Math.sin(Az),0},
//                    {0,Math.sin(Az),Math.cos(Az),0},
//                    {0,0,0,1}
//            };
//            int i,j;
//            for(i=0;i<4;i++){
//                for(j=0;j<4;j++){
//                    r[i] += P[j]*T[i][j];
//                }
//            }
//            cube[x1][0]=(int)r[0];
//            cube[x1][1]=(int)r[1];
//            cube[x1][2]=(int)r[2];
//            cube[x1][3]=(int)r[3];
//        }
//    }




    public static void main(String[] args) {

        JFrame frame = new JFrame("Cube");
        frame.setSize(800,800);
        Element3D element3D = new Element3D(frame.getWidth(),frame.getHeight(),Color.red);
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                element3D.xc  = e.getX();
                element3D.yc = e.getY();

                element3D.cube3D(200,200,50);
                element3D.cube3D(250,200,50);
            }

        });

        frame.add(element3D);

        frame.setVisible(true);
    }

}
