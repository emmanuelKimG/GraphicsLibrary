import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Rectangle extends GraphLib{

    public Rectangle(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public void rectangle(int x, int y, int length , int height){
        dda(x,y,x+length,y);
        dda(x,y,x,y+height);
        dda(x+length,y,x+length,y+height);
        dda(x,y+height,x+length,y+height);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Proyecto Graficas");
        frame.setSize(600,600);
        frame.setVisible(true);
        GraphLib draw = new GraphLib(frame.getWidth(), frame.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics frameGraphics = frame.getGraphics();
        frameGraphics.drawLine(100,100,500,500);
        draw.dda(100,100,500,500);
//        frameGraphics.drawImage(draw.getImage(),0,0,null);
        frameGraphics.setColor(Color.green);
    }
}
