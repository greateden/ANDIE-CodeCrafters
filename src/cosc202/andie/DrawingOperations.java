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
  private Point start;
  private Point end;
  private Color color;


  /**
   * Construct an DrawingOperations with no parameters.
   */
  public DrawingOperations(char shape, Point start, Point end,Color color) {
this.start=start;
this.end=end;
    this.shape = shape;
    this.color=color;

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

    Point startPoint= start;//MouseSelection.getStartPoint();
    Point endPoint= end; //MouseSelection.getEndPoint();
    if (startPoint != null && endPoint != null) {
      int x = Math.min(startPoint.x, endPoint.x);
      int y = Math.min(startPoint.y, endPoint.y);
      int width = Math.abs(startPoint.x - endPoint.x);
      int height = Math.abs(startPoint.y - endPoint.y);

     
     //  color=ColourWheel.getChosenColour();
      g.setColor(color);

     
      g.drawRect(x, y, width, height);
      //g.dispose();

      DrawingOperations.isDrawingRect=false;
    }
  
  }else if (shape == 'o') {// draw an oval

    System.out.println("Drawing oval");

      Graphics2D g2d = input.createGraphics();
      int xStart = start.x;//MouseSelection.getStartPoint().x;
      int yStart =start.y; //MouseSelection.getStartPoint().y;
      int xEnd =end.x;// MouseSelection.getEndPoint().x;

      int yEnd = end.y;//MouseSelection.getEndPoint().y;

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

    
      g2d.setColor(color);
      g2d.fillOval(x, y, width, height); // (x, y, width, height)

      //g2d.dispose();

    } else if (shape == 'l') {// draw a line

    System.out.println("Drawing line");

      Graphics2D g2d = input.createGraphics();

      int xStart = start.x;//MouseSelection.getStartPoint().x;
      int yStart =start.y; //MouseSelection.getStartPoint().y;
      int xEnd =end.x;// MouseSelection.getEndPoint().x;

      int yEnd = end.y;//MouseSelection.getEndPoint().y;


     
      g2d.setColor(color);
      g2d.drawLine(xStart, yStart,xEnd, yEnd); // (x, y,x2, y2)

     //g2d.dispose();
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