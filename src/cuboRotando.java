import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class cuboRotando extends JFrame implements KeyListener, Runnable{
    private BufferedImage buff, buffer;
    private JPanel zonaTeclado;
    private JTextArea teclado;

    public static Color rojoEspecial = new Color(236, 68, 92);

    double[] repintarRotacion = {500,120,50};
    int[] repintarTraslacion = {200, 200, 0};

    public int xp = -100, yp = -70, zp = 500;

    int[][] cube = {{-50, -50, -23, 1}, {-50, 50, -23, 1},{50, 50, -23, 1},{50, -50, -23, 1},{-50, -50, 75, 1},{-50, 50, 75, 1},{50, 50, 75, 1},{50, -50, 75, 1}
    };

    int cuboDub[][] = new int[8][4];

    public Thread hilo;

    public cuboRotando(){
        zonaTeclado = new JPanel();
        teclado = new JTextArea();
        teclado.addKeyListener(this);
        getContentPane().add(teclado, BorderLayout.CENTER);
        getContentPane().add(zonaTeclado, BorderLayout.CENTER);

        setTitle("Cubo Rotando");
        setResizable(true);
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buff = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);

        paint();
        hilo = new Thread(this);
        hilo.start();
    }

    public void putPixel(int x, int y, Color c)
    {
        buff.setRGB(0, 0, c.getRGB());
        buffer.getGraphics().drawImage(buff, x, y, this);
    }

    public void puntoMedio(int x0, int y0, int x1, int y1, Color c){

        float x, y, dx, dy, incx = 1, incy = 1, incE, incNE, p = 0;

        x = x0;
        y = y0;

        dx = x1 - x0;
        dy = y1 - y0;

        if(dx < 0){
            dx = -dx;
            incx = -1;
        }
        if(dy < 0){
            dy = -dy;
            incy = -1;
        }

        if(Math.abs(dx) > Math.abs(dy)){
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            while(x != x1){
                putPixel((int)Math.round(x), (int)Math.round(y), c);
                x = x + incx;
                if(2 * (p + dy) < dx){
                    p = p + incE;
                }else{
                    p = p + incNE;
                    y = y + incy;
                }
            }
        }else{
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            while(y != y1){
                putPixel((int)Math.round(x), (int)Math.round(y), c);
                y = y + incy;
                if(2 * (p + dx) < dy){
                    p = p + incE;
                }else{
                    p = p + incNE;
                    x = x + incx;
                }
            }
        }

    }

    public void trazado(int cube[][], int xp, int yp, int zp){
        int coordCubo[][] = new int [8][2];

        for (int i = 0; i < 8; i++) {
            coordCubo[i] = calcular2D(cube[i][0], cube[i][1], cube[i][2],xp, yp, zp);
        }

        //Vertices cuadrado frontal
        puntoMedio(coordCubo[0][0], coordCubo[0][1], coordCubo[1][0], coordCubo[1][1], rojoEspecial);
        puntoMedio(coordCubo[1][0], coordCubo[1][1], coordCubo[2][0], coordCubo[2][1], rojoEspecial);
        puntoMedio(coordCubo[2][0], coordCubo[2][1], coordCubo[3][0], coordCubo[3][1], rojoEspecial);
        puntoMedio(coordCubo[3][0], coordCubo[3][1], coordCubo[0][0], coordCubo[0][1], rojoEspecial);

        //Vertices cuadrado posterior
        puntoMedio(coordCubo[0][0], coordCubo[0][1], coordCubo[4][0], coordCubo[4][1], rojoEspecial);
        puntoMedio(coordCubo[1][0], coordCubo[1][1], coordCubo[5][0], coordCubo[5][1], rojoEspecial);
        puntoMedio(coordCubo[2][0], coordCubo[2][1], coordCubo[6][0], coordCubo[6][1], rojoEspecial);
        puntoMedio(coordCubo[3][0], coordCubo[3][1], coordCubo[7][0], coordCubo[7][1], rojoEspecial);

        //Aristas entre los cuadrados
        puntoMedio(coordCubo[4][0], coordCubo[4][1], coordCubo[5][0], coordCubo[5][1], rojoEspecial);
        puntoMedio(coordCubo[5][0], coordCubo[5][1], coordCubo[6][0], coordCubo[6][1], rojoEspecial);
        puntoMedio(coordCubo[6][0], coordCubo[6][1], coordCubo[7][0], coordCubo[7][1], rojoEspecial);
        puntoMedio(coordCubo[7][0], coordCubo[7][1], coordCubo[4][0], coordCubo[4][1], rojoEspecial);

    }

    public void paint(){
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                cuboDub[i][j] = cube[i][j];
            }
        }

        rot(cuboDub, repintarRotacion[0], repintarRotacion[1], repintarRotacion[2]);
        tras(cuboDub, repintarTraslacion[0], repintarTraslacion[1], repintarTraslacion[2]);
        trazado(cuboDub, xp, yp, zp);
        zonaTeclado.getGraphics().drawImage(buffer, 0, 0, this);
    }

    public int[] calcular2D(int x1, int y1, int z1, int xp, int yp, int zp){
        int x2, y2;

        x2 = x1 + ((xp * z1)/zp);
        y2 = y1 + ((yp * z1)/zp);

        int dot[] = {(int)x2, (int)y2};
        return dot;
    }

    public void rot(int [][] cube, double Ax, double Ay, double Az){

        for(int x1=0; x1<=7; x1++){
            double r[]={0,0,0,0};
            double [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
            double [][] T = {
                    {Math.cos(Ax),-Math.sin(Ax),0,0},
                    {Math.sin(Ax),Math.cos(Ax),0,0},
                    {0,0,1,0},
                    {0,0,0,1}
            };
            int i,j;
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    r[i] += P[j]*T[i][j];
                }
            }
            cube[x1][0]=(int)r[0];
            cube[x1][1]=(int)r[1];
            cube[x1][2]=(int)r[2];
            cube[x1][3]=(int)r[3];
        }


        for(int x1=0; x1<=7; x1++){
            double r[]={0,0,0,0};
            double [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
            double [][] T = {
                    {Math.cos(Ay), 0, Math.sin(Ay), 0},
                    {0, 1, 0, 0},
                    {-Math.sin(Ay), 0, Math.cos(Ay), 0},
                    {0, 0, 0, 1}
            };
            int i,j;
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    r[i] += P[j]*T[i][j];
                }
            }
            cube[x1][0]=(int)r[0];
            cube[x1][1]=(int)r[1];
            cube[x1][2]=(int)r[2];
            cube[x1][3]=(int)r[3];
        }


        for(int x1=0; x1<=7; x1++){
            double r[]={0,0,0,0};
            double [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
            double [][] T = {
                    {1,0,0,0},
                    {0,Math.cos(Az),-Math.sin(Az),0},
                    {0,Math.sin(Az),Math.cos(Az),0},
                    {0,0,0,1}
            };
            int i,j;
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    r[i] += P[j]*T[i][j];
                }
            }
            cube[x1][0]=(int)r[0];
            cube[x1][1]=(int)r[1];
            cube[x1][2]=(int)r[2];
            cube[x1][3]=(int)r[3];
        }
    }

    public void tras(int [][] cube, int dx,int dy, int dz){
        for(int x1=0; x1<=7; x1++){
            int r[]={0,0,0,0};
            int [] P = {cube[x1][0], cube[x1][1],cube[x1][2], cube[x1][3]};
            int [][] T = {{1,0,0,dx},
                    {0,1,0,dy},
                    {0,0,1,dz},
                    {0,0,0,1}};
            int i,j;
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    r[i] += P[j]*T[i][j];
                }
            }
            cube[x1][0]=r[0];
            cube[x1][1]=r[1];
            cube[x1][2]=r[2];
            cube[x1][3]=r[3];
        }
    }

    public void run() {
        while (true) {
            repintarRotacion[0] = repintarRotacion[0] + Math.PI/64;
            repintarRotacion[1] = repintarRotacion[1] + Math.PI/64;
            repintarRotacion[2] = repintarRotacion[2] + Math.PI/64;

            paint();

            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        cuboRotando b = new cuboRotando();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}