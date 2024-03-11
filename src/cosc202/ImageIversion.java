/**Author  yuxing zhang 10th march */
package cosc202;

import java.awt.image.*;

import cosc202.andie.ImageOperation;


public class ImageInversion implements ImageOperation, java.io.Serializable {
    

public ImageInversion(){

}
   



public BufferedImage apply(BufferedImage input) {
  
    for (int y = 0; y < input.getHeight(); ++y) {
        for (int x = 0; x < input.getWidth(); ++x) {
            int argb = input.getRGB(x, y);
            int a = (argb & 0xFF000000) >> 24;
            int r = (argb & 0x00FF0000) >> 16;
            int g = (argb & 0x0000FF00) >> 8;
            int b = (argb & 0x000000FF);


            argb = (a << 24) | (255-r<< 16) | (255-g << 8) | b;
            input.setRGB(x, y, argb);
        }
    }
    
    return input;


}
}
