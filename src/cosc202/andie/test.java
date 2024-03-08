package cosc202.andie;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class test {
    
    public static void main(String[] args) throws Exception {

       

        int radius = 1;
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
         /** 
        Scanner scanner = new Scanner(System.in);
        while (true) {
           getpos(scanner.nextInt(),2*radius+1);
        }
        */
        double sum =0;
        for(int i = 0; i < size ; i++){
           String[] posString = getpos(i,(2*radius+1)).split(",");
           int x = Integer.parseInt(posString[0]);
           int y = Integer.parseInt(posString[1]);
           double result = GaussianEquation(x,y, (double)1 / 3);
           sum = sum + result;
           array[i] = (float)result;
        }
        for(int i = 0; i < size ; i++){
            array[i] = array[i]/ (float)sum;
        }
    
        System.out.println("check");
        



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

