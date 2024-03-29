package cosc202.andie;

import java.awt.image.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

/**
 * <p>
 * ImageOperation to apply the Random scattering effect on an image.
 * </p>
 * 
 * <p>
 * This effect replaces each pixel in the image with another randomly chosen pixel from within a user-given radius.
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
public class RandomScattering implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of the radius to choose a pixel for replacement. 
     */
    private int radius;

    /**
     * <p>
     * Build a matrix of this given radius. 
     * </p>
     * 
     * <p>
     * The size of the matrix is the 'radius'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the matrix to apply the effect.
     */
    RandomScattering(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a matrix to apply the Random Scattering effect. Takes in user input.
     * </p
     * >
     * <p>
     * By default, the matrix of radius 1, i.e. a 3x3 matrix.
     * </p>
     * 
     * @see MedianFilter(int). This code is refactored from ym code for the Median filter, which operates on a similar logic. 
     */
    RandomScattering() {
        this(1);
    }

    /**
     * <p>
     * Apply the Random Scattering effect to the image. 
     * </p>
     * 
     * @param input The image to apply the Random Scattering effect to.
     * @return The resulting image. It will look somewhat pixelated. 
     */
    public BufferedImage apply(BufferedImage input) {
        
        if(radius==0){
            return input;
        }

        int side = 2*radius+1; //The side of the matrix using the user-given radius. 
        

        //Arrays to hold the pixels in the matrix constructed from the radius. 
        
        int[] pixel = new int[side*side];

        int nr,ng,nb,na; //NEW rgba value taken from the sorted array for applying the median filter. 

        int argb; //argb values for transformation.
        
        //Makes a copy of input to apply the Random Scattering effect to. 
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        
        /* At this point we need to talk about boundary conditions. 
         * Like the median filter, this effect relies on the neighbourhood of the given filter. For a given radius r, this means that pixels located within r pixels of the edge of the image will
         * inevitably have a smaller neighbourhood to work with. Is this a problem? Unlike the median filter, the lab book doesn't explicitly mention that we can ignore the boundary condition.
         * I won't be accounting for this in the beginning simply because I don't think the impact will be noticeable. If it turns out it is, I will account for the boundary condition.  
         */

        //Random generator
        Random r = new Random();


        //int r,g,b;
        for(int i=0; i<input.getHeight(); i++){
            for(int j=0; j<input.getWidth(); j++){
                int a1 = 0; //Counter to help fit a square matrix into a 1-D array.
                for(int k=i-side/2; k<i+side/2; k++){
                    for(int l=j-side/2; l<j+side/2; l++){
                        if(k >0 && k < input.getHeight() && l >0 && l < input.getWidth()){
                            
                            pixel[a1] = input.getRGB(l,k);
                            

                            //Incrementing the counter.
                            a1++;
                        }
                    }
                }

                //Apply the filter now.
                int chosenNum = r.nextInt(a1);
                //System.out.println("chosenNum: "+ chosenNum + "\ni: " + i + "\nj: " + j + "\na1: " + a1);
         
             }
        }
    
        
    return output;

    }

}
