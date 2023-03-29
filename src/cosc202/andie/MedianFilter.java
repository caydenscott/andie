package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.util.*;

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
    MedianFilter(int radius) {
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
        int height = input.getHeight();
        int width = input.getWidth();
        
        /**
         * had issues with black chunk being cut off at bottom of image. to fix, i added padding pixels around the outside
         * of the image, such that they would get cut off instead. then just crop the image back to its original size. 
         * a padding amout of 1 has shown to be sufficient.
         */

        // Choose how many extra pixels to add around the edges
        int padAmount = 1;
        
        // Create a new image with padding around the edges
        BufferedImage padding = new BufferedImage(width + padAmount * 2, height + padAmount * 2, input.getType());
        Graphics2D g = padding.createGraphics();
        g.drawImage(input, padAmount, padAmount, null);
        g.dispose();
        
        // Get the pixels of the padded image
        int[] pixels = padding.getRGB(0, 0, width + padAmount * 2, height + padAmount * 2, null, 0, width + padAmount * 2);
        int[] neighbourhood;
        
        // Create a new output image with padding around the edges
        BufferedImage output = new BufferedImage(width + padAmount * 2, height + padAmount * 2, input.getType());
        int[] pixelsOutput = output.getRGB(0, 0, width + padAmount * 2, height + padAmount * 2, null, 0, width + padAmount * 2);
        
        // Loop through each pixel in the input image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int startX = Math.max(x - radius, 0);
                int endX = Math.min(x + radius, width - 1);
                int startY = Math.max(y - radius, 0);
                int endY = Math.min(y + radius, height - 1);
                
                // Create a neighbourhood array of pixel values
                neighbourhood = new int[(endX - startX + 1) * (endY - startY + 1)];
                int index = 0;
                for (int i = startY; i <= endY; i++) {
                    for (int j = startX; j <= endX; j++) {
                        int pixel = pixels[j + i * (width + padAmount * 2)];
                        neighbourhood[index] = pixel;
                        index++;
                    }
                }
                
                // Sort the neighbourhood array and set the pixel value in the output image
                Arrays.sort(neighbourhood);
                pixelsOutput[(y + padAmount) * (width + padAmount * 2) + x + padAmount] = neighbourhood[neighbourhood.length / 2];
            }
        }
        
        // Set the pixels of the output image and crop the padded edges
        output.setRGB(0, 0, width + padAmount * 2, height + padAmount * 2, pixelsOutput, 0, width + padAmount * 2);
        BufferedImage croppedOutput = output.getSubimage(padAmount, padAmount, width, height);
        return croppedOutput;
    }

}
