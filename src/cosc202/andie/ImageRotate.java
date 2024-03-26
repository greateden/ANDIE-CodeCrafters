package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.awt.*;

/**
 * <p>
 * ImageOperation to invert an image's colours.
 * </p>
 * 
 * <p>
 * The images produced by this operation are still technically colour images,
 * in that they have red, green, and blue values, but each pixel has the inverse of its value.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImageRotate implements ImageOperation, java.io.Serializable {

    private double deg;
    private int countDegrees=0;
    private int attempted = 0;
    

    

    ImageRotate(double deg, int rotateAttempt){
        this.deg = deg;
        countDegrees += deg;
        countDegrees = countDegrees%360;
        this.attempted = rotateAttempt;
    }
    /**
     * <p>
     * Create a new ImageInvert operation.
     * </p>
     */
    ImageRotate() {}

    /**
     * <p>
     * Apply image invert conversion to an image.
     * </p>
     * 
     * <p>
     * 
     * </p>
     * 
     * @param input The image to be inverted.
     * @return The resulting inverted image.
     */
    public BufferedImage apply(BufferedImage input) {
       
        double rad = Math.toRadians(deg); // converts the input degress to radians 
        int newWidth = (int) Math.floor(input.getWidth() * Math.abs(Math.cos(rad)) + input.getHeight() * Math.abs(Math.sin(rad)));
        int newHeight = (int) Math.floor(input.getWidth() * Math.abs(Math.sin(rad)) + input.getHeight() * Math.abs(Math.cos(rad)));
        // Image rotation Caculation from NGLN (https://math.stackexchange.com/users/17336/ngln), Rotating a rectangle, URL (version: 2017-04-13): https://math.stackexchange.com/q/71069

       

        int radius = (int)Math.floor((Math.sqrt(Math.pow(input.getWidth(),2)+Math.pow(input.getHeight(),2)))/2);    //Calculate the radius of the bounding circle on which the rectangle is inscribed using Pythagoras.
        
        BufferedImage transformedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB); // creates a new image with a new w,h to adjust for transfomration
        
        Graphics2D graphics = transformedImg.createGraphics(); // creates a graphics object to redraw the image

        graphics.setPaint(new Color(233,233,233));
        graphics.fillRect(0,0,transformedImg.getWidth(), transformedImg.getHeight());

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Image was becoming pixalated due to Interpolation so hot fix this makes it blur more then pixalate when the pixels don't fully match
        //if(attempted==1){
        //graphics.translate((newWidth - input.getWidth())/2 , (newHeight - input.getHeight())/2); 
        //graphics.translate((radius*Math.cos(Math.toRadians(countDegrees))-radius), radius - (radius*Math.sin(Math.toRadians(countDegrees))));
        graphics.translate((newWidth - input.getWidth()) /2 , (newHeight - input.getHeight()) /2);        
        //}
        graphics.rotate(rad, input.getWidth()/2 , input.getHeight()/2);

        graphics.drawRenderedImage(input, null); // draws the image onto the canvas
        System.out.println(transformedImg.getTileGridXOffset()+ ", "+transformedImg.getTileGridYOffset());
        //graphics.translate(-(newWidth - input.getWidth())/2 , -(newHeight - input.getHeight())/2); 
        //System.out.println(transformedImg.getMinX() + "," + transformedImg.getMinY());

        graphics.dispose(); // closes the graphics good practics and I belive it helps to stop memory leaks
        

        
        return transformedImg; // returns the transformed image no point returning input 
    }
    
}
