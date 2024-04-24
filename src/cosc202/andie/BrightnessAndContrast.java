package cosc202.andie;

import java.awt.image.*;

/**This class is written to implement the Brightness and Contrast operation in ANDIE. 
 * @author Kevin Steve Sathyanath
 * @date 19/04/2024
 */

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

    private static final int MAX = 255;     //The maximum 255 on the RGB scale, used for readability
    private static final int MIN = 0;      //The minimum 0 on the RGB scale, used for readability
    private int b; //Brightness value
    private int c; //Contrast value

    public BrightnessAndContrast(){ //Default constructor
    }

    public BrightnessAndContrast(int brightness, int contrast){
        this.b = brightness;
        this.c = contrast;
    }

    /**This method must be implemented when the class implements ImageOperation. It will apply the output of the equation()
     * here while taking care of the bounds.
     * @return output: A BufferedImage that has the B&C operation applied to it.
     * @param input A BufferedImage that is to be operated on. 
     */
    public BufferedImage apply(BufferedImage input){

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB); 
        
        int nr,ng,nb; //New values after applying the equation.
        int argb; //Resolved value of the pixel
        int a,r,g,b; //Broken down values or each pixel

        for(int i=0; i<output.getHeight(); i++){
            for(int j=0; j<output.getWidth(); j++){

                argb = input.getRGB(j,i);
                a = (argb & 0xFF000000) >> 24;
                r = (argb & 0x00FF0000) >> 16;
                g = (argb & 0x0000FF00) >> 8;
                b = (argb & 0x000000FF);

                nr = equation(r);
                ng = equation(g);
                nb = equation(b);

                argb = (a << 24) | (nr << 16) | (ng << 8) | nb;
                output.setRGB(j,i, argb);
            }
        }


        return output;
    }//End of apply().

    /**A function to apply the equation given in the lab book.
     * @param c The parameter representing the RGB value of the pixel. 
     * @param b The parameter passed to represent a change in brightness.
     * @param c The parameter passed to represent a change in contrast.
     * @return The output integer from applying the equation using in. 
     */
    public int equation(int v){

        int vOut = 0;
        vOut = (int)((1 + (c/100.0))*(v-127.5)+(127.5)*(1+(b/100.0))); //Explicity typecasting since pixel value cannot be a float.
        if(vOut>MAX) {vOut = MAX;}
        if(vOut<MIN) {vOut = MIN;}
        return vOut;

    }// End of equation()

    
} //End of class
