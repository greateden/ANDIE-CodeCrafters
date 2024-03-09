package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * The median filter takes all of the pixel values in a local neighbourhood and sorts them. The new pixel value is the middle value 
 * from the sorted list.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter.
     */
    MedianFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        
        int side = 2*radius+1; //The side of the kernel using the radius. 
        int kernelWidth = side;
        int kernelHeight = side;

        //Arrays to hold the argb elements in a kernel; size determined by given radius. 
        int[] rMedian = new int[kernelWidth*kernelHeight];
        int[] gMedian = new int[kernelWidth*kernelHeight];
        int[] bMedian = new int[kernelWidth*kernelHeight];
        int[] aMedian = new int[kernelWidth*kernelHeight];
        
        int nr,ng,nb,na; //NEW rgba value taken from the sorted array for applying the median filter. 

        int argb; //argb values for transformation.

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
    
        //int r,g,b;
        for(int i=0; i<input.getHeight(); i++){
            for(int j=0; j<input.getWidth(); j++){
                int a1 = 0; //Counter to help fit a square kernel into a 1-D array.
                for(int k=i-side/2; k<i+side/2; k++){
                    for(int l=j-side/2; l<j+side/2; l++){
                        if(k >0 && k < input.getWidth() && l >0 && l <= input.getHeight()){
                            //Taken from MeanFilter.
                            argb = input.getRGB(k, l);
                            int a = (argb & 0xFF000000) >> 24;
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);

                            //Filling the arrays.
                            rMedian[a1] = r;
                            gMedian[a1] = g;
                            bMedian[a1] = b;
                            aMedian[a1] = a;

                            //Incrementing the counter.
                            a1++;
                        }
                    }
                }

                //Ok. Time to sort these arrays and see what happens. 
                Arrays.sort(rMedian);
                Arrays.sort(gMedian);
                Arrays.sort(bMedian);
                Arrays.sort(aMedian);

                //Find the middle? Must be the radius probably.
                nr = rMedian[radius];
                ng = gMedian[radius];
                nb = bMedian[radius];
                na = aMedian[radius];

                //Apply the filter now.
                argb = (na << 24) | (nr << 16) | (ng << 8) | nb;
                output.setRGB(j,i, argb);
            }
        }

        
        return output;
    }


}
