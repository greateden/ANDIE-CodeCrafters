package cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


/**
 * <p>
 * This class implements the ImageOperation interface and applies Image embossing to a BufferedImage.
 * </p>
 * 
 * <p>
 *      Image embossing is when an images pixel is replaced with a light or dark color depening on the boundries of an input image
 * </p>
 * 
 * 
 * @see java.awt.image.ConvolveOp
 * @see ImageOperation
 * @see BufferedImage
 * @author Angus Lyall
 * @version 1.0
*/

public class EmbossFilter implements ImageOperation, java.io.Serializable{
    /**
    * The radius of the Emboss filter. This currently is not implmented but will allow for a larger emboss matrix
    */
    private int radius;

    /**
     * Constructor that sets the radius of the Emboss filter.
     * @param radius the radius of the filter
    */

    EmbossFilter(int radius){
        this.radius = radius;
    }
    
    /**
    * Default constructor that creates a Emboss Filter with a radius of 2.
    */
    EmbossFilter() {
        this(2);
    }

    /**
     * Applies the Emboss filter to a BufferedImage.
     * 
     * @param input the BufferedImage to apply the filter to
     * @return a new BufferedImage with the applied Emboss filter and adjusted rgb values
    */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage embossedImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB); // creates a new image 
        int size = (2*radius+1) * (2*radius+1); // not implmented but to set matrix size
        float[] embossMatrix = { // a hardcoded embossing matrix 
            -1.0f, 0.0f, 0.0f,
            0.0f,  0.0f, 0.0f,
            0.0f,  0.0f, 1.0f
        };
        Kernel embossKernel = new Kernel(3, 3, embossMatrix); // creates a 3x3 kernal with hard coded matrix
        ConvolveOp convOp = new ConvolveOp(embossKernel); // Creates a convolution operation that applies the emboss kernal

        //Create an instance of the class that creates image with border
        FilterBorder borderedImage = new FilterBorder(input, radius);
        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderedImage.applyBorder(), null);
        //Crops image back to original size
        embossedImage = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

        for (int y = 0; y < input.getHeight(); y++) { // Loops though by height then width
            for (int x = 0; x < input.getWidth(); x++) {
                int rgb = embossedImage.getRGB(x, y); // gets the rgb values of the filtered image
                int gray = (rgb >> 16) & 0xFF;  // the values for gray
                int adjustedValue = gray  + 127; // sets the middle point from lab book (127 or 128) and adds the grey value
                adjustedValue = Math.min(Math.max(adjustedValue, 0), 255); // checks that the adjusted rgb is still within 0-255
                int adjustedRGB = (adjustedValue << 16) | (adjustedValue << 8) | adjustedValue; // Sets the adjusted rgb to be applied
                embossedImage.setRGB(x, y, adjustedRGB); // applys the adjusted rgb to the embossed image output
            }
        }

        return embossedImage; // returns the embossed and color adjusted image
    }

}
