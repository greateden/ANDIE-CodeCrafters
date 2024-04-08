package cosc202.andie;

import java.awt.image.BufferedImage;

public class FilterBorder {
    
    private int radius;
    private BufferedImage input;
    
    public FilterBorder(BufferedImage input, int radius){
        this.radius = radius;
        this.input = input;
    }

    public BufferedImage applyBorder(){
        //Makes a border that is a copy of the outter ring of pixels of image, to stop convolution from being applied to non-existant pixels
        //Creates image one pixel bigger then original image
        int borderWidth = input.getWidth() + 2;
        int borderHeight = input.getHeight() + 2;
        BufferedImage borderImage = new BufferedImage(borderWidth, borderHeight, input.getType());

        //copies current image to middle of the new image
        for(int y=0; y< input.getHeight(); y++){
            for(int x=0; x<input.getWidth(); x++){
                borderImage.setRGB(x+1, y+1, input.getRGB(x, y));
            }
        }

        //fills edges with same values as original outter edge pixels
        for(int y=0; y<input.getHeight(); y++){
            borderImage.setRGB(0, y+1, input.getRGB(0, y)); //left side
            borderImage.setRGB(borderWidth-1, y+1, input.getRGB(input.getWidth()-1, y)); //right side
        }
        for(int x=0; x<input.getWidth(); x++){
            borderImage.setRGB(x+1, 0, input.getRGB(x, 0)); //Top side
            borderImage.setRGB(x+1, borderHeight-1, input.getRGB(x, input.getHeight()-1)); //Bottom side
        }

        //Deal with the corner pieces
        borderImage.setRGB(0, 0, input.getRGB(0, 0)); // top left corner
        borderImage.setRGB(borderWidth-1, 0, input.getRGB(input.getWidth()-1, 0)); // top right corner
        borderImage.setRGB(0, borderHeight-1, input.getRGB(0, input.getHeight()-1)); // bottom left corner
        borderImage.setRGB(borderWidth-1, borderHeight-1, input.getRGB(input.getWidth()-1, input.getHeight()-1)); // bottom right corner
      
        return borderImage;
    }
}
