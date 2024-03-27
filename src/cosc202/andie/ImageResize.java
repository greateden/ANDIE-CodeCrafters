package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;

/* A class that does image resize. Made by Yuxing Zhang */

public class ImageResize implements ImageOperation, java.io.Serializable {

  private int width;
  private int height;

public ImageResize(){};

  public ImageResize(int height, int width) {

    this.height = height;
    this.width = width;
    
    
  }

  @Override
  public BufferedImage apply(BufferedImage input) {
   
   

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();
    Image inp = (Image) input;
   

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Image scaled; 
    //= inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

    if (oriWidth > width && oriHeight > height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    } else if (oriWidth < width && oriHeight < height) {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    } else {
      scaled = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    }

    resultImage.getGraphics().drawImage(scaled, 0, 0, null);
             
    // Graphics2D g2d = resultImage.createGraphics();
    // g2d.drawImage(temp, 0, 0, null);
    // g2d.dispose();

    return resultImage;

  }
}