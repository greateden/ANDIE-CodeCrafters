package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 
 * 
 * <p>
 * ImageOperation to apply drawing functions.
 * </p>
 * 
 * <p>
 * The image drawing operation takes and image and draw some
 * basic shapes(rectangle oval and line) on the image
 * </p>
 * 
 * 
 * </p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 */

public class DrawingOperations implements ImageOperation, java.io.Serializable {

  private char shape;
  public static boolean isDrawingRect;
  public static boolean isDrawingOval;
  public static boolean isDrawingLine;



  /**
   * Construct an DrawingOperations with no parameters.
   */
  public DrawingOperations(char shape) {

    this.shape = shape;

  }

  /**
   * Apply a shape to the given image.
   *
   * @param input The image to resize.
   * @return The resulting image.
   */
  @Override
  public BufferedImage apply(BufferedImage input) {
  
     
    if (shape == 'r') {
    System.out.println("Drawing rect");

   Graphics2D g = input.createGraphics();

    Point startPoint=MouseSelection.getStartPoint();
    Point endPoint= MouseSelection.getEndPoint();
    if (startPoint != null && endPoint != null) {
      int x = Math.min(startPoint.x, endPoint.x);
      int y = Math.min(startPoint.y, endPoint.y);
      int width = Math.abs(startPoint.x - endPoint.x);
      int height = Math.abs(startPoint.y - endPoint.y);
      if(ColourWheel.getChosenColour()!=null){
        Color color=ColourWheel.getChosenColour();
        g.setColor(color);
      }
      else{
        g.setColor(Color.green);
      }
      g.drawRect(x, y, width, height);
      g.dispose();
    }
  
  }else if (shape == 'o') {// draw an oval
      Graphics2D g2d = input.createGraphics();
      int xStart = MouseSelection.getStartPoint().x;
      int yStart = MouseSelection.getStartPoint().y;
      int xEnd = MouseSelection.getEndPoint().x;

      int yEnd = MouseSelection.getEndPoint().y;

      int x = Math.min(xStart, xEnd);
      int y = Math.min(yStart, yEnd);
      int width;
      int height;
      if (xStart > xEnd) {
        width = xStart - xEnd;
      } else {
        width = xEnd - xStart;
      }
      if (yStart > yEnd) {
        height = yStart - yEnd;
      } else {
        height = yEnd - yStart;
      }

      if(ColourWheel.getChosenColour()!=null){
        Color color=ColourWheel.getChosenColour();
        g2d.setColor(color);
      }
      else{
        g2d.setColor(Color.green);
      }
      g2d.fillOval(x, y, width, height); // (x, y, width, height)


      g2d.dispose();

    } else if (shape == 'l') {// draw a line
      Graphics2D g2d = input.createGraphics();

      int xStart = MouseSelection.getStartPoint().x;
      int yStart = MouseSelection.getStartPoint().y;
      int xEnd = MouseSelection.getEndPoint().x;
      int yEnd = MouseSelection.getEndPoint().y;

      if(ColourWheel.getChosenColour()!=null){
        Color color=ColourWheel.getChosenColour();
        g2d.setColor(color);
      }
      else{
        g2d.setColor(Color.green);
      }
      g2d.drawLine(xStart, yStart,xEnd, yEnd); // (x, y,x2, y2)
      g2d.dispose();
    }
return input;

}
}

//       int xStart = MouseSelection.getStartPoint().x;
//       int yStart = MouseSelection.getStartPoint().y;
//       int xEnd = MouseSelection.getEndPoint().x;

//       int yEnd = MouseSelection.getEndPoint().y;

//       int x = Math.min(xStart, xEnd);
//       int y = Math.min(yStart, yEnd);
//       int width;
//       int height;
//       if (xStart > xEnd) {
//         width = xStart - xEnd;
//       } else {
//         width = xEnd - xStart;
//       }
//       if (yStart > yEnd) {
//         height = yStart - yEnd;
//       } else {
//         height = yEnd - yStart;
//       }

//       g2d.setColor(Color.GREEN);

//       g2d.fillRect(x, y, width, height); // (x, y, width, height)
//       g2d.dispose();
//     }