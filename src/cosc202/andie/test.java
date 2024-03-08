package cosc202.andie;
import java.util.Arrays;
import java.util.Scanner;

public class test {
    
    public static void main(String[] args) throws Exception {
        //double middle = GaussianEquation(0,0,);
        //double x1 = GaussianEquation(1,0,(double)1/3);
        //double adjust = (middle + (4*x1));
        //System.out.printf("%.3f \n",middle / adjust);
        //System.out.printf("%.3f",x1 / adjust);
       

        int radius = 1;
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        for(int i = 0; i < size ; i++){
           // String[] xyStrings = String.split(getpos(i,(2*radius+1)), ",");

        }
        



    }

    public static double GaussianEquation(int x , int y, double sd){
        double sdPow = Math.pow(sd, 2);
        double l1 = 1/(2 * Math.PI * sdPow);
        double el1 = Math.pow(x, 2) + Math.pow(y, 2);
        double e = Math.pow(Math.E, -(el1 / (2 * sdPow)));

        return l1 * e;
    }

    public static String getpos(int num , int height){
        int center = (height*height) / 2;
        int x = num % height -center;
        int y = center - num / height;
        return x +","+ y;
    }
}

