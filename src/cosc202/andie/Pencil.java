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
public class Pencil implements ImageOperation, java.io.Serializable {

    private Graphics2D g;

    /**The constructor */
    public Pencil(){

    }
    
    /**The apply method. */
    @Override
    public BufferedImage apply(BufferedImage in){

        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
        g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2f));
        g.setColor(Color.BLACK);

        addMouseListener(new mouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                repaint();
            }
        });
        


        return in;
    }


}//End of class Body
