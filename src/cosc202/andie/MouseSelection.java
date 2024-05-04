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
 * @version 2.0
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
    public MouseSelection(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
        this.imageWidth = imagePanel.getImage().getCurrentImage().getWidth();
        this.imageHeight = imagePanel.getImage().getCurrentImage().getHeight();
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
        this.imageWidth = imagePanel.getImage().getCurrentImage().getWidth();
        this.imageHeight = imagePanel.getImage().getCurrentImage().getHeight();
    }

    /**
     * Records end coordinates of selection when mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isSelecting = false;
        imagePanel.setIsSelecting(isSelecting);

        if (selectionRect != null && selectionRect.width != 0 && selectionRect.height != 0) {
            // Ensure selection rectangle is normalized
            selectionRect = new Rectangle(
                    Math.min(start.x, end.x),
                    Math.min(start.y, end.y),
                    Math.abs(end.x - start.x),
                    Math.abs(end.y - start.y)
            );
        } else {
            // Reset selection rectangle if it's just a click
            selectionRect = null;
        }
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
        this.imageWidth = imagePanel.getImage().getCurrentImage().getWidth();
        this.imageHeight = imagePanel.getImage().getCurrentImage().getHeight();
        end = e.getPoint();
        imagePanel.setEndPoint(end);
        System.out.println(imagePanel.getImage());
        if (imagePanel.getImage() != null) {
            // int imageWidth = Math.min(
            //     imagePanel.getImage().getCurrentImage().getWidth(imagePanel.getImage().getCurrentImage()),
            //     imagePanel.getImage().getCurrentImage().getWidth());
            // int imageHeight = Math.min(
            //     imagePanel.getImage().getCurrentImage().getHeight(imagePanel.getImage().getCurrentImage()),
            //     imagePanel.getImage().getCurrentImage().getHeight());
            if (end.x < 0) {
                end.x = 0;
            } else if (end.x > imageWidth) {
                end.x = imageWidth;
            }
            if (end.y < 0) {
                end.y = 0;
            } else if (end.y > imageHeight) {
                end.y = imageHeight;
            }
            if (start.x < 0) {
                start.x = 0;
            } else if (start.x > imageWidth) {
                start.x = imageWidth;
            }
            if (start.y < 0) {
                start.y = 0;
            } else if (start.y > imageHeight) {
                start.y = imageHeight;
            }
            if (isSelecting) {
                // Create the selection rectangle within the image bounds
                selectionRect = new Rectangle(
                        Math.min(start.x, end.x),
                        Math.min(start.y, end.y),
                        Math.abs(end.x - start.x),
                        Math.abs(end.y - start.y)
                );
                imagePanel.setSelectionRect(selectionRect);
            }
        }
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
