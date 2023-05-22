package cosc202.andie.ImageOperations.Filters;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.util.*;

import cosc202.andie.ImageOperations.ImageOperation;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a convoloution.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class MeanFilter implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MeanFilter
     */
    public MeanFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    MeanFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
    
        int padAmount = radius; // Increase padding size for mirror padding
    
        // Create a new image with mirror padding around the edges
        BufferedImage paddedInput = new BufferedImage(width + padAmount * 2, height + padAmount * 2, input.getType());
        Graphics2D g = paddedInput.createGraphics();
        g.drawImage(input, padAmount, padAmount, null);
        // Mirror left and right edges
        g.drawImage(input, 0, padAmount, padAmount, height + padAmount, 0, 0, padAmount, height, null);
        g.drawImage(input, width + padAmount, padAmount, width + padAmount * 2, height + padAmount, width - padAmount, 0, width, height, null);
        // Mirror top and bottom edges
        g.drawImage(input, padAmount, 0, width + padAmount, padAmount, 0, 0, width, padAmount, null);
        g.drawImage(input, padAmount, height + padAmount, width + padAmount, height + padAmount * 2, 0, height - padAmount, width, height, null);
        // Mirror corners
        g.drawImage(input, 0, 0, padAmount, padAmount, 0, 0, padAmount, padAmount, null);
        g.drawImage(input, width + padAmount, 0, width + padAmount * 2, padAmount, width - padAmount, 0, width, padAmount, null);
        g.drawImage(input, 0, height + padAmount, padAmount, height + padAmount * 2, 0, height - padAmount, padAmount, height, null);
        g.drawImage(input, width + padAmount, height + padAmount, width + padAmount * 2, height + padAmount * 2, width - padAmount, height - padAmount, width, height, null);
        g.dispose();
    
        BufferedImage output = new BufferedImage(width, height, input.getType());
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage filteredImage = convOp.filter(paddedInput, null);
        
        // Create a new image to store the filtered pixels without padding
        output = new BufferedImage(width, height, input.getType());
        g = output.createGraphics();
        g.drawImage(filteredImage, -padAmount, -padAmount, null);
        g.dispose();
        
        return output;
    }


}