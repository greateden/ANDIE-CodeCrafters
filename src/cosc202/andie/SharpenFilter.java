package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen (enhancing) filter.
 * </p>
 * 
 * <p>
 * This filter is the reverse of a blur filter, by enhancing the differences between neighbouring values. 
 * Implimented by using a convoultion.
 * </p>
 * @author Emma
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable{

    /**
     * <p>
     * Create a Sharpen filter operation
     * </p>
     */
    SharpenFilter() {}

    /**
     * <p>
     * Applies sharpen filter to an image
     * </p>
     * 
     * <p>
     * The sharpen filter is applied via convolution
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     */
    public BufferedImage apply(BufferedImage input) {
        //Values for Kernal in an array
        float [] array = {0, (-(1/2.0f)), 0, (-(1/2.0f)), 3, (-(1/2.0f)), 0, (-(1/2.0f)), 0};
        //Make filter from array
        Kernel kernel = new Kernel(3, 3, array);
        //Applying as a convolution
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Makes a transpearent border to stop convolution from being applied to non-existant pixels
        //Creates image one pixel bigger then original image
        int borderWidth = input.getWidth() + 2;
        int borderHeight = input.getHeight() + 2;
        BufferedImage borderImage = new BufferedImage(borderWidth, borderHeight, input.getType());

        //copies current image to middle of the new image
        for(int y=0; y< input.getHeight(); y++){
            for(int x=0; x<input.getWidth(); x++){
                borderImage.setRGB(x+1, y+1, input.getRGB(x, y));
            }
        }

        //fills edges with same values as original outter edge pixels
        for(int y=0; y<input.getHeight(); y++){
            borderImage.setRGB(0, y+1, input.getRGB(0, y)); //left side
            borderImage.setRGB(borderWidth-1, y+1, input.getRGB(input.getWidth()-1, y)); //right side
        }
        for(int x=0; x<input.getWidth(); x++){
            borderImage.setRGB(x+1, 0, input.getRGB(x, 0)); //Top side
            borderImage.setRGB(x+1, borderHeight-1, input.getRGB(x, input.getHeight()-1)); //Bottom side
        }

        //Deal with the corner pieces
        borderImage.setRGB(0, 0, input.getRGB(0, 0)); // top left corner
        borderImage.setRGB(borderWidth-1, 0, input.getRGB(input.getWidth()-1, 0)); // top right corner
        borderImage.setRGB(0, borderHeight-1, input.getRGB(0, input.getHeight()-1)); // bottom left corner
        borderImage.setRGB(borderWidth-1, borderHeight-1, input.getRGB(input.getWidth()-1, input.getHeight()-1)); // bottom right corner
      

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderImage, null);

        //Crops image back to original size
        output = output.getSubimage(1, 1, input.getWidth(), input.getHeight());

        return output;
    }

}
