package cosc202.andie;

import java.awt.*;
import javax.swing.*;

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
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    private Rectangle selectionRect;
    private boolean isSelecting;
    private int rotationAngle = 0;
    private Point startPoint;
    private Point endPoint;

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
                repaint();
            }
        });
        timer.start();
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
            return new Dimension((int) Math.round(EditableImage.getCurrentImage().getWidth() * scale),
                    (int) Math.round(EditableImage.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     *
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting behavior.

        if (image.hasImage()) { // Check if there is an image to display.
            Graphics2D g2 = (Graphics2D) g.create(); // Create a new Graphics2D object for transformations.
            g2.scale(scale, scale); // Scale the graphics context.
            g2.drawImage(EditableImage.getCurrentImage(), null, 0, 0); // Draw the current image at (0, 0).
            g2.dispose(); // Dispose of the Graphics2D object to release resources.
        }

        if (isSelecting || selectionRect != null) { // Check if there is a selection or a selection rectangle is present.
            Graphics2D g2d = (Graphics2D) g; // Cast the graphics context to Graphics2D for additional drawing capabilities.
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
}
