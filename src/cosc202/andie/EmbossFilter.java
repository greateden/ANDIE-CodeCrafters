package cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class EmbossFilter implements ImageOperation, java.io.Serializable{
    private int radius;

    EmbossFilter(int radius){
        this.radius = radius;
    }
    
    EmbossFilter() {
        this(2);
    }


    public BufferedImage apply(BufferedImage input) {
        BufferedImage embossedImage = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        int size = (2*radius+1) * (2*radius+1);
        float[] embossMatrix = {
            -1.0f, 0.0f, 0.0f,
            0.0f,  0.0f, 0.0f,
             0.0f,  0.0f, 1.0f
        };
        Kernel embossKernel = new Kernel(3, 3, embossMatrix);
        ConvolveOp embossOp = new ConvolveOp(embossKernel);
        embossOp.filter(input, embossedImage);

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                int rgb = embossedImage.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; 
                int adjustedValue = gray  + 127;
                adjustedValue = Math.min(Math.max(adjustedValue, 0), 255);
                int adjustedRGB = (adjustedValue << 16) | (adjustedValue << 8) | adjustedValue;
                embossedImage.setRGB(x, y, adjustedRGB);
            }
        }

        return embossedImage;
    }

}
