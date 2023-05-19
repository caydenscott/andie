package cosc202.andie.ImageOperations.Filters;

import java.awt.image.*;
import java.util.*;

import cosc202.andie.ImageOperations.ImageOperation;

/**
 * @see java.awt.image.ConvolveOp
 * @author Cayden Scott
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    public MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a median filter has radius 1.
     * </p>
     * 
     * @see medianfilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * Apply a median filter to an image.
     * 
     * @param input The image to apply the median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = 2*radius+1; //calculates size of filter
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);//creates new image with same charateristics as original to serve as output 

        //loops over every pixel in the input image
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                //create arrays to store the rgb values of the pixels
                int[] r = new int[size*size];
                int[] g = new int[size*size];
                int[] b = new int[size*size];
                int index = 0;
                //loops over every pixel
                for(int i = -radius; i <= radius; i++){
                    for(int j = -radius; j <= radius; j++){
                        int pixelX = x + j;
                        int pixelY = y + i;
                        //ensures pixel is inside input image, then gets rgb values and stores them
                        if (pixelX >= 0 && pixelX < input.getWidth() && pixelY >= 0 && pixelY < input.getHeight()) {
                            int argb = input.getRGB(pixelX, pixelY);
                            r[index] = (argb & 0x00FF0000) >> 16;
                            g[index] = (argb & 0x0000FF00) >> 8;
                            b[index] = (argb & 0x000000FF);
                            index++;
                            
                        }
                    }
                }
                //sorts arrays, and then gets median values
                Arrays.sort(r, 0, index);
                Arrays.sort(g, 0, index);
                Arrays.sort(b, 0, index);
                int rMedian = 0;
                int gMedian = 0;
                int bMedian = 0;
                if(index % 2 == 0){
                    rMedian = r[index / 2];
                    gMedian = g[index / 2];
                    bMedian = b[index / 2];
                }else{
                    rMedian = r[(index - 1)/ 2];
                    gMedian = g[(index - 1)/ 2];
                    bMedian = b[(index - 1)/ 2];
                }
                
                //combines rgb values into single argb value, sets these values to output
                int argb = (input.getRGB(x, y) & 0xFF000000) | (rMedian << 16) | (gMedian << 8) | bMedian;
                output.setRGB(x, y, argb);
            }
        }
        
        
        return output;
    }

}
