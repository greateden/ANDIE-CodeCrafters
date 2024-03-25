package cosc202.andie;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class ImageScaling  implements ImageOperation, java.io.Serializable{
  
    private double scalePercentage;
   // private int heightPer;

   public ImageScaling(double scalePercentage){
    this.scalePercentage = scalePercentage;
   // this.widthPer = widthPer;

   }

   public BufferedImage apply(BufferedImage input) {

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();

    
    Image inp = (Image) input;
     
    int height=(int) (oriHeight*scalePercentage);
    int width=(int) (oriWidth*scalePercentage);



   

    BufferedImage resultImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

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
