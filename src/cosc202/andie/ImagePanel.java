package cosc202.andie;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 *
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * This class has been modified with code taken from: https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
 * This is because Kevin wanted to emulate what Jackson's team did in their ANDIE implementation. Most of this code is lifted verbatim and modified <i>very</i> slightly.
 * Kevin will ask Dave about the Student Honor Code thing on Friday. 5/5/2024
 * @version 1.0
 */
public class ImagePanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener{

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    private Rectangle selectionRect;
    private boolean isSelecting;
    private int rotationAngle = 0;
    private Point startPoint;
    private Point endPoint;

    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;

    private Graphics2D g2d;

    public void setSelectionRect(Rectangle selectionRect) {
        this.selectionRect = selectionRect;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setIsSelecting(boolean isSelecting) {
        this.isSelecting = isSelecting;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }


    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     *
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     *
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;

        Timer timer = new Timer(100, e -> {
            if (!isSelecting) {
                rotationAngle -= 1; // Decrease rotation angle (clockwise rotation)
                //repaint();
            }
        });
        timer.start();
        initComponent();
    }

    /**Method that initializes the mouseListeners to make active scrolling in the ImagePanel a reality.
     * Code taken from: https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
     * No modifications necessary. I mean it's just 3 lines.  Kevin Steve Sathyanath
     * date: 5/5/2024
     */
    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**A replacement constructor to be used in displaying a new imagepanel. We can set this as a new target in imageAction. TESTING
     * @author Kevin Steve Sathyanath
     * @date 23/04/2024
     */
    public ImagePanel(EditableImage working){
        image = working;
        scale = 0.2;
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    public boolean imageHasImage(){
        return image.hasImage();
    }


    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     *
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     *
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     *
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     *
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     *
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     *
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(ImageAction.target.getImage().getCurrentImage().getWidth() * scale),
                    (int) Math.round(ImageAction.target.getImage().getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    //ADD SETPREFERREDSIZE().



    /**
     * <p>
     * (Re)draw the component in the GUI. Code taken from: https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
     * Minor editing done. Kevin Steve Sathyanath.
     * </p>
     *
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting behavior.

        if (image.hasImage()) { // Check if there is an image to display.
            Color onyx = new Color(38,38,38); //Hey that's my cat's name!
            setBackground(onyx);
            Graphics2D g2 = (Graphics2D) g.create(); // Create a new Graphics2D object for transformations.
            g2.scale(scale, scale); // Scale the graphics context.
            //g2.drawImage(EditableImage.getCurrentImage(), null, 0, 0); // Draw the current image at (0, 0).

            // if (zoomer) {
            //     AffineTransform at = new AffineTransform();

            //     double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            //     double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            //     double zoomDiv = zoomFactor / prevZoomFactor;

            //     xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            //     yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            //     at.translate(xOffset, yOffset);
            //     at.scale(zoomFactor, zoomFactor);
            //     prevZoomFactor = zoomFactor;
            //     g2.transform(at);

            //     if(g2d !=null){      //TEST CODE
            //         g2d.dispose();
            //     }

            //     zoomer = false;
            // }

            // if (dragger) {
            //     AffineTransform at = new AffineTransform();
            //     at.translate(xOffset + xDiff, yOffset + yDiff);
            //     at.scale(zoomFactor, zoomFactor);
            //     g2.transform(at);

            //     if (released) {
            //         xOffset += xDiff;
            //         yOffset += yDiff;
            //         dragger = false;
            //     }

            // }

            // All drawings go here

            g2.drawImage(image.getCurrentImage(), 0, 0, this);

            g2.dispose(); // Dispose of the Graphics2D object to release resources.
        }

        if (isSelecting || selectionRect != null) { // Check if there is a selection or a selection rectangle is present.
            g2d = (Graphics2D) g; // Cast the graphics context to Graphics2D for additional drawing capabilities.
            g2d.setColor(Color.BLACK); // Set the drawing color to black.

            if (selectionRect != null) { // Check if a selection rectangle exists.
                // Draw the selection rectangle
                g2d.setStroke(new BasicStroke(2)); // Set the stroke width for drawing the rectangle.
                g2d.drawRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height); // Draw the selection rectangle.

                // Draw alternating black and white segments on the border
                for (int i = 0; i < selectionRect.width; i++) { // Iterate over the width of the selection rectangle.
                    if (((i + rotationAngle) / 8) % 2 == 0) { // Determine the color of the segment based on rotation angle.
                        g2d.setColor(Color.WHITE); // Set the color to white for even segments.
                    } else {
                        g2d.setColor(Color.BLACK); // Set the color to black for odd segments.
                    }
                    // Draw horizontal segments on the border.
                    g2d.drawLine(selectionRect.x + i, selectionRect.y, selectionRect.x + i, selectionRect.y + 1);
                    g2d.drawLine(selectionRect.x + i, selectionRect.y + selectionRect.height - 1,
                            selectionRect.x + i, selectionRect.y + selectionRect.height);
                }
                for (int i = 0; i < selectionRect.height; i++) { // Iterate over the height of the selection rectangle.
                    if (((i + rotationAngle) / 8) % 2 == 0) { // Determine the color of the segment based on rotation angle.
                        g2d.setColor(Color.WHITE); // Set the color to white for even segments.
                    } else {
                        g2d.setColor(Color.BLACK); // Set the color to black for odd segments.
                    }
                    // Draw vertical segments on the border.
                    g2d.drawLine(selectionRect.x, selectionRect.y + i, selectionRect.x + 1, selectionRect.y + i);
                    g2d.drawLine(selectionRect.x + selectionRect.width - 1, selectionRect.y + i,
                            selectionRect.x + selectionRect.width, selectionRect.y + i);
                }
            }
        }
    }
    /**Code to program the expected behaviour when the mouseWheel is moved.
     * Code taken from: https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
     * Code kept verbatim. No editing done. Kevin Steve Sathyanath.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        zoomer = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }

    /**Code to program the expected behaviour when the mouse is dragged over the imagePanel. Likely needs to be modified later when Emma finished mouse Selection.
     * Code taken from: https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
     * Code kept verbatim. No editing done. Kevin Steve Sathyanath
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        dragger = true;
        repaint();

    }
    //You know the drill. COde taken from that stackOverFlow post etc.
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    //You know the drill. COde taken from that stackOverFlow post etc.
    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        repaint();
    }

    //You know the drill. COde taken from that stackOverFlow post etc.
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    //You know the drill. COde taken from that stackOverFlow post etc.
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
