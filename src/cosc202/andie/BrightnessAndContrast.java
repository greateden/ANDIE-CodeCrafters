package cosc202.andie;

import java.awt.image.*;

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

    //Global variables that hold brightness and contrast percentage change values.
    private int b;
    private int c;
    
    //Default constructor
    public BrightnessAndContrast(){


    }

    //Constructor that handles percentage values for b and c
    public BrightnessAndContrast(int b, int c){

        this.b = b;
        this.c = c;
    }

    /**
    *<p>
    *This method applies the Brightness and Contrast values. It is refactored from the code from convertToGrey().
    *</p>
    *
    *<p>
    *Like the greyscale method, this will split each pixel into its RGB values and 
    *then apply the given equation to it depending upon user input.
    *
    *@author Kevin Steve Sathyanath
    *@date 31/03/2024
    **/
    public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                argb = adjust(argb);
                
                //Apply this.
                input.setRGB(x, y, argb);
            }
        }
        
        return input;
    }



    /*A method to calculate the new value of a pixel when contrast and brightness are altered. */
    public int adjust(int p){

                int argb = p;
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                //a is left alone. RGB are adjusted.

                int nr = equation(r);
                int ng = equation(g);
                int nb = equation(b);

                //Combine them again

                argb = (a << 24) | (nr << 16) | (ng << 8) | nb;

                return argb;
    }

    /*A method that holds the given brightness and contrast equation */
    public int equation(int in){

        double out=0;

        out = (1+(c/100)*(in-127.5)+127.5*(1+(b/100)));

        return (int)out;  //Explicitly cast as int. The difference is probably not that big. 
    }

}//End of class
