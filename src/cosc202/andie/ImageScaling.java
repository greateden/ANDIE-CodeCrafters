package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * ImageScaling class implements the ImageOperation interface and provides
 * functionality to scale an image by a given percentage.
 */
public class ImageScaling implements ImageOperation, java.io.Serializable {

  private double scalePercentage;
  // private int heightPer;

  /**
   * <p>
   * Constructs a new ImageScaling object.
   * </p>
   * 
   * @param scalePercentage The percentage to scale the image by.
   */
  public ImageScaling(double scalePercentage) {
    this.scalePercentage = scalePercentage;
    // this.widthPer = widthPer;

  }

  /**
   * <p>
   * Scales the image by the given percentage.
   * </p>
   * 
   * @param input The image to scale.
   * @return The scaled image.
   */
  public BufferedImage apply(BufferedImage input) {

    int oriWidth = input.getWidth();
    int oriHeight = input.getHeight();

    Image inp = (Image) input;

    int height = (int) (oriHeight * scalePercentage);
    int width = (int) (oriWidth * scalePercentage);

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Image scaled;

    // = inp.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

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
