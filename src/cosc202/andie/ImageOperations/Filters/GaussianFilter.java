package cosc202.andie.ImageOperations.Filters;

import java.awt.image.*;

import cosc202.andie.ImageOperations.ImageOperation;

public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a gaussian filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianFilter
     */
    public GaussianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a gaussian filter with the default size.
     * </p
     * >
     * <p>
     * By default, a gaussian filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    GaussianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting gaussian blured image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2 * radius + 1); // calculates size of filter
        float[] array = new float[size * size]; // array to store filter values
        float sigma = radius / 3.0f; // calculate sigma value
        float sum = 0.0f; // variable to keep track of sum of filter values
        //populate array with calculated values
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                int index = (y + radius) * size + (x + radius);
                array[index] = (float) ((1 / (2 * Math.PI * sigma * sigma))
                        * (Math.exp((-(x * x + y * y)) / (2 * sigma * sigma))));
                sum += array[index];
            }
        }
        //normalise values by dividing by sum
        for (int i = 0; i < array.length; i++) {
            array[i] /= sum;
        }
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }

}
