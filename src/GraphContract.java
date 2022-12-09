
public interface GraphContract {

    void clearBuffer();
    void makePixel(int x, int y);
    void dda(int x1, int y1, int x2, int y2);
    void rectangle(int x, int y, int length , int height);
    void makeCircle(int x, int y, int radius);
    void bressenhamLine(int x0, int y0, int x1 , int y1);
    void bressenhamCirle(int x0,int y0, int r);
    void circuloPuntoMedio(int x0, int y0, int radio);
    void lineaPuntoMedio(int x0,int y0,int x1,int y1);
    void eightPointsCircle(int x0, int y0, int r);

}
