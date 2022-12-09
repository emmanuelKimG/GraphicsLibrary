import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Muse Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Has moved");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        var x = e.getX();
        var y = e.getY();
        System.out.println("EL MOUSE SE ESTA MOVIENDO POR" + x + " y " + y);
    }
}
