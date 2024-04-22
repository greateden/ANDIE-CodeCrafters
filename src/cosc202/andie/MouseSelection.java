package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * <p>
 * MouseSelection to apply a selection box to the image panel 
 * using the users input of mouse click and release
 * </p>
 * 
 * <p>
 * This class uses the Mouselisner to record the coordinates of whne the mouse is clicked and released
 * in order to draw a selction box on top of the image panel
 * </p>
 * 
 * @see java.awt.event.MouseListener
 * @author Emma
 * @version 1.0
 */
public class MouseSelection implements MouseListener{

    private Point start;
    private Point end;
    private ImagePanel imagePanel;

    /**
     * Initilises MouseSelection
     * @param imagePanel The image panel that the selction is drawn on
     */
    public MouseSelection(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
        this.imagePanel.addMouseListener(this);
    }

    /**
     * Records start coordinates of selection when mouse is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        clearSelection();
    }
    
    /**
     * Records end coordinates of selection when mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        drawSelection();
    }

    /**
     * Calculates and draws the selction box
     */
    private void drawSelection() {
        //Calculating selction 
        //Finds starting y and x values of selction
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        //finds width and height of selction
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);

        //Draws the selction box on panel
        Graphics g = imagePanel.getGraphics();
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
    }

    /**
     * Clears the selection box from the image panel
     */
    private void clearSelection() {
        //Removes the selection box
        imagePanel.repaint();
    }
    
    /**
     * Not needed for this implimentation 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        clearSelection();
    }

    /**
     * Not needed for this implimentation 
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Not needed for this implimentation 
     */
    @Override
    public void mouseExited(MouseEvent e) {}
}
