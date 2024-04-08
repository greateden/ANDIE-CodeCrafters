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
        int borderWidth = input.getWidth() + 2 * radius;
        int borderHeight = input.getHeight() + 2 * radius;
        BufferedImage borderImage = new BufferedImage(borderWidth, borderHeight, input.getType());

        //copies current image to middle of the new image
        for(int y=0; y< input.getHeight(); y++){
            for(int x=0; x<input.getWidth(); x++){
                borderImage.setRGB(x+radius, y+radius, input.getRGB(x, y));
            }
        }

        //fills edges with same values as original outter edge pixels
        for(int y=0; y<input.getHeight(); y++){
            for(int x=0; x<radius; x++){
                borderImage.setRGB(x, y+radius, input.getRGB(0, y)); //left side
                borderImage.setRGB(borderWidth-1-x, y+radius, input.getRGB(input.getWidth()-1, y)); //right side
            }
        }
        for(int x=0; x<input.getWidth(); x++){
            for(int y=0; y<radius; y++){
                borderImage.setRGB(x+radius, y, input.getRGB(x, 0)); //Top side
                borderImage.setRGB(x+radius, borderHeight-1-y, input.getRGB(x, input.getHeight()-1)); //Bottom side
            }
        }

        //Deal with the corner pieces
        for(int y=0; y<radius; y++){
            for(int x=0; x<radius; x++){
                borderImage.setRGB(x, y, input.getRGB(0, 0)); // top left corner
                borderImage.setRGB(borderWidth-1-x, y, input.getRGB(input.getWidth()-1, 0)); // top right corner
                borderImage.setRGB(x, borderHeight-1-y, input.getRGB(0, input.getHeight()-1)); // bottom left corner
                borderImage.setRGB(borderWidth-1-x, borderHeight-1-y, input.getRGB(input.getWidth()-1, input.getHeight()-1)); // bottom right corner
            }
        }
      
        return borderImage;
    }
}
