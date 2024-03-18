package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Soft blur filter.
 * </p>
 * 
 * <p>
 * This filter blurs an image by mixing a little bit of neighbouring pixels, to a lesser extent of the mean filter 
 * Implimented by using a convoultion.
 * </p>
 */
public class SoftBlurFilter implements ImageOperation, java.io.Serializable{

    /**
     * <p>
     * Create a Soft blur filter operation
     * </p>
     */
    SoftBlurFilter() {
    
    }
    
    /**
     * <p>
     * Applys soft blur filter to an image
     * </p>
     * 
     * <p>
     * The soft blur filter is applied via convolution
     * </p>
     * 
     * @param input The image to apply the soft blur filter to.
     */
    public BufferedImage apply (BufferedImage input) {
        // Values for the kernal
        float [] array = {0, 1/8.0f, 0, 1/8.0f, 1/2.0f, 1/8.0f, 0, 1/8.0f, 0};
        // Make filter from 3x3 array
        Kernel kernel = new Kernel(3, 3, array);
        // Apply as a convolution
        ConvolveOp convOp = new ConvolveOp(kernel);
        
        //Makes a transpearent border to stop convolution from being applied to non-existant pixels

        //Creates image one pixel bigger then original image
        int borderWidth = input.getWidth() + 2;
        int borderHeight = input.getHeight() + 2;
        BufferedImage borderImage = new BufferedImage(borderWidth, borderHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = borderImage.createGraphics();

        //fills image with transparent pixels
        g2.setColor(new Color(0,0,0,0)); 
        g2.fillRect(0, 0, borderWidth, borderHeight);

        //Adding original image to the middle
        g2.drawImage(input,1,1, null);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderImage, null);

        //Crops image back to original size
        output = output.getSubimage(1, 1, input.getWidth(), input.getHeight());

        return output;
    }
}
