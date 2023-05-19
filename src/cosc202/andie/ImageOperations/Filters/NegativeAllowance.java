package cosc202.andie.ImageOperations.Filters;

import java.awt.image.BufferedImage;

public class NegativeAllowance {

     /**
     * Applies the Negative Allowance filter to the given image using the specified kernel and midValue.
     * This filter calculates the new pixel values by convolving the kernel with the neighborhood around each pixel.
     * The resulting pixel values are then adjusted by adding the midValue and clamping them to the valid range [0, 255].
     *
     * @param image    The input image to apply the filter to.
     * @param kernel   The kernel to convolve with the image.
     * @param midValue The value to add to the convolved pixel values.
     * @return The filtered image with the Negative Allowance effect applied.
     */

    public static BufferedImage applyFilter(BufferedImage image, Number[][] kernel, int midValue) {
        // Get dimensions of image
        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate padding and create a new image to store the filtered image
        int kernelSize = kernel.length;
        int padAmount = kernelSize / 2;

        // Create a new image to store the filtered image
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Apply the filter to each pixel in the image
        for (int y = padAmount; y < height - padAmount; y++) {
            for (int x = padAmount; x < width - padAmount; x++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                // Apply the kernel to the neighborhood
                for (int ky = 0; ky < kernelSize; ky++) {
                    for (int kx = 0; kx < kernelSize; kx++) {
                        int imageX = x - padAmount + kx;
                        int imageY = y - padAmount + ky;

                        int rgb = image.getRGB(imageX, imageY);
                        int pixelR = (rgb >> 16) & 0xFF;
                        int pixelG = (rgb >> 8) & 0xFF;
                        int pixelB = rgb & 0xFF;

                        int kernelValue = kernel[ky][kx].intValue();
                        sumR += pixelR * kernelValue;
                        sumG += pixelG * kernelValue;
                        sumB += pixelB * kernelValue;
                    }
                }

                // Calculate the new pixel values
                int newR = midValue + sumR;
                int newG = midValue + sumG;
                int newB = midValue + sumB;
                newR = Math.max(0, Math.min(255, newR)); // Ensure the value is within the valid range [0, 255]
                newG = Math.max(0, Math.min(255, newG));
                newB = Math.max(0, Math.min(255, newB));

                int newRGB = (newR << 16) | (newG << 8) | newB;
                filteredImage.setRGB(x, y, newRGB);
            }
        }

        return filteredImage;
    }

    /**
     * Retrieves the pixel value at the specified coordinates in the given image.
     * If the coordinates are outside the image bounds, it clamps them to the valid range.
     *
     * @param image The image to retrieve the pixel value from.
     * @param x     The x-coordinate of the pixel.
     * @param y     The y-coordinate of the pixel.
     * @return The pixel value at the specified coordinates.
     */
    public static int getPixelValue(BufferedImage image, int x, int y) {
        x = Math.max(0, Math.min(x, image.getWidth() - 1));
        y = Math.max(0, Math.min(y, image.getHeight() - 1));
        return image.getRGB(x, y) & 0xFF;
    }

    /**
     * Clamps the given value to the valid range [0, 255].
     *
     * @param value The value to clamp.
     * @return The clamped value.
     */
    public static int clamp(int value) {
        return Math.min(Math.max(value, 0), 255);
    }
}
