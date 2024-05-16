package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**A class that simulates a pencil drawing on a canvas 
 * @author Kevin Steve Sathyanath
 * @version 1.0
*/
public class Pencil extends JPanel implements ImageOperation, java.io.Serializable, MouseListener, MouseMotionListener {

    private Graphics2D g;
    private BufferedImage out;

    /**The constructor */
    public Pencil(){

    }
    
    /**The apply method. */
    @Override
    public BufferedImage apply(BufferedImage in){

        out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
        g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2f));

        if(ColourWheel.getChosenColour()!=null){
            g.setColor(ColourWheel.getChosenColour());
        }
        else{
            g.setColor(Color.BLACK);
        }

        
        


        return in;
    }

    
    @Override
    public void mousePressed(MouseEvent e) {
        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override 
    public void mouseClicked(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
            repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e){

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(out, 0, 0, this);
    }


}//End of class Body
