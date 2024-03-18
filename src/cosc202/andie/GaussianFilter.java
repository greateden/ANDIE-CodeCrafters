package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.util.*;


public class GaussianFilter implements ImageOperation, java.io.Serializable {

    private int radius;

    GaussianFilter(int radius){
        this.radius = radius;
    }
    
    GaussianFilter() {
        this(2);
    }

    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        double sum =0;
        for(int i = 0; i < size ; i++){
            String[] posString = getpos(i,(2*radius+1)).split(",");
            int x = Integer.parseInt(posString[0]);
            int y = Integer.parseInt(posString[1]);
            double result = GaussianEquation(x,y, (double)radius / 3);
            sum = sum + result;
            array[i] = (float)result;
         }
        for(int i = 0; i < size ; i++){
             array[i] = array[i]/ (float)sum;
         }

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        //Makes a transpearent border to stop convolution from being applied to non-existant pixels

        //Creates image one pixel bigger then original image
        int borderWidth = input.getWidth() + 2;
        int borderHeight = input.getHeight() + 2;
        BufferedImage borderImage = new BufferedImage(borderWidth, borderHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = borderImage.createGraphics();

        //fills image with transparent pixels
        g2.setColor(new Color(0,0,0,0)); 
        g2.fillRect(0, 0, borderWidth, borderHeight);

        //Adding original image to the middle
        g2.drawImage(input,1,1, null);

        //Applies convolution to bordered image
        BufferedImage output = convOp.filter(borderImage, null);

        //Crops image back to original size
        output = output.getSubimage(1, 1, input.getWidth(), input.getHeight());

        return output;
    }


    public static double GaussianEquation(int x , int y, double sd){
        double sdPow = Math.pow(sd, 2);
        double l1 = 1/(2 * Math.PI * sdPow);
        double el1 = Math.pow(x, 2) + Math.pow(y, 2);
        double e = Math.pow(Math.E, -(el1 / (2 * sdPow)));

        return l1 * e;
    }

    public static String getpos(int num , int height){
        int center = (height) / 2;
        int x = num % height -center;
        int y = center - num / height;
        return x +","+ y;
    }


}




    