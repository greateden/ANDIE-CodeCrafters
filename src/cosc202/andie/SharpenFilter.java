package cosc202.andie;

import java.awt.image.*;
import java.util.*;
import javax.swing.ImageIcon;

/**
 * <p>
 * ImageOperation to apply a Sharpen (enhancing) filter.
 * </p>
 * 
 * <p>
 * This filter is the reverse of a blur filter, by enhancing the differences between neighbouring values. 
 * Implimented by using a convoultion.
 * </p>
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
     * Applys sharpen filter to an image
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
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }

}
