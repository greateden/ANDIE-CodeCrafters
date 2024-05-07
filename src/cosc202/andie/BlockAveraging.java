package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * <p>
 * ImageOperation to apply a Block Average filter.
 * </p>
 * 
 * <p>
 * The block averaging operation takes and image and replaces 
 * blocks on a regular grid with the average
 * pixel value within that region.
 * </p>
 * 
 
 * </p>
 * 
 * @author YUXING ZHANG 
 * @version 1.0
 */
public class BlockAveraging implements ImageOperation, java.io.Serializable {
    private int blockSizeHeight;
    private int blockSizeWidth;

    public BlockAveraging(int blockSizeHeigh, int blockSizeWidth) {
        this.blockSizeWidth = blockSizeWidth;
        this.blockSizeHeight=blockSizeHeigh;
    }


    /**
     * <p>
     * apply a blcok averaging action.
     * </p>
     * 
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
      
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // iterates over the image
        for (int xpixel = 0; xpixel < width; xpixel += blockSizeWidth) {
            for (int ypixel = 0; ypixel < height; ypixel += blockSizeHeight) {
                int totalR = 0;
                int totalG = 0;
                int totalB = 0;
                int a = 0;
                int count=0;
                for (int inSidex = xpixel; inSidex < xpixel + blockSizeWidth; inSidex++) {
                    for (int inSidey = ypixel; inSidey < ypixel + blockSizeHeight; inSidey++) {
                        if (inSidex < width && inSidey < height) {
                            int rgb = input.getRGB(inSidex, inSidey);
                            a = (rgb & 0xFF000000) >> 24;
                            int r = (rgb & 0x00FF0000) >> 16;
                            int g = (rgb & 0x0000FF00) >> 8;
                            int b = (rgb & 0x000000FF);

                            totalR += r;
                            totalG += g;
                            totalB += b;
                            count++;

                        }

                    }
                }
                totalR/=count;
                totalG/=count;
                totalB/=count;

                for (int inSidex = xpixel; inSidex < xpixel + blockSizeWidth; inSidex++) {
                    for (int inSidey = ypixel; inSidey < ypixel + blockSizeHeight; inSidey++) {
                        if (inSidex < width && inSidey < height) {

                            int avrageRGB = (a << 24) | (totalR << 16)
                                    | (totalG  << 8) | totalB ;

                            output.setRGB(inSidex, inSidey, avrageRGB);

                        }

                    }
                }
            }
        }

        return output;
    }

}