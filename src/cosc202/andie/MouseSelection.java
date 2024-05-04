package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;

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
public class MouseSelection implements MouseListener, MouseMotionListener{

    private Point start;
    private Point end;
    private ImagePanel imagePanel;
    private int imageWidth;
    private int imageHeight;
    private Rectangle selectionRect;
    private boolean isSelecting;
    private int rotationAngle = 0;

    /**
     * Initilises MouseSelection
     * @param imagePanel The image panel that the selction is drawn on
     */
    public MouseSelection(ImagePanel imagePanel,int imageWidth, int imageHeight) {
        this.imagePanel = imagePanel;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePanel.addMouseListener(this);
        this.imagePanel.addMouseMotionListener(this);
    }

    /**
     * Records start coordinates of selection when mouse is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        imagePanel.setStartPoint(start);
        end = start;
        imagePanel.setEndPoint(end);
        isSelecting = true;
        imagePanel.setIsSelecting(isSelecting);
        rotationAngle = 0;
        imagePanel.setRotationAngle(rotationAngle);
        //clearSelection();
        imagePanel.repaint();
    }

    /**
     * Records end coordinates of selection when mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isSelecting = false;
        imagePanel.setIsSelecting(isSelecting);
        //end = e.getPoint();
        //imagePanel.setEndPoint(end);
        //drawSelection();

        selectionRect = new Rectangle(Math.min(start.x, end.x), Math.min(start.y, end.y),
                        Math.abs(end.x - start.x), Math.abs(end.y - start.y));
        imagePanel.setSelectionRect(selectionRect);
        imagePanel.repaint();
    }

    /**
     * Calculates and draws the selction box
     */
    private void drawSelection() {
        //Calculating selction
        //Finds starting y and x values of selction
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        //finds width and height of selction, bounded by the image dimensions
        int width = Math.min(imageWidth - x, Math.abs(start.x - end.x));
        int height = Math.min(imageHeight - y, Math.abs(start.y - end.y));

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

    @Override
    public void mouseDragged(MouseEvent e) {
        end = e.getPoint();
        imagePanel.setEndPoint(end);
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

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
