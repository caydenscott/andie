package cosc202.andie.ImageOperations.Filters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import cosc202.andie.ImageOperations.ImageOperation;

public class EmbossFilter implements ImageOperation, java.io.Serializable {

    private int embossType;

    /**
     * <p>
     * Construct a Emboss filter with the given type.
     * </p>
     * 
     * 
     * @param embossType The type of filter is a number 1 - 8, representing the 8
     *                   different types of emboss filters.
     */
    public EmbossFilter(int embossType) {
        this.embossType = embossType;
    }

    /**
     * <p>
     * Construct an emboss filter with the default embossType.
     * </p
     * >
     * <p>
     * By default, an emboss filter has type 1.
     * </p>
     * 
     * @see GaussianFilter(int)
     */
    public EmbossFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply an emboss filter to an image.
     * </p>
     * 
     * @param input The image to apply the emboss filter to.
     * @return The resulting emboss filtered image.
     */
    public BufferedImage apply(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();

        int padAmount = 2; // Increase padding size for mirror padding

        // Create a new image with mirror padding around the edges
        BufferedImage paddedInput = new BufferedImage(width + padAmount * 2, height + padAmount * 2, input.getType());
        Graphics2D g = paddedInput.createGraphics();
        g.drawImage(input, padAmount, padAmount, null);
        // Mirror edges
        for (int i = 0; i < padAmount; i++) {
            g.drawImage(input, 0, padAmount + i, padAmount, padAmount + i + 1, 0, 0, padAmount, 1, null); // Mirror left
                                                                                                          // edge
            g.drawImage(input, width + padAmount, padAmount + i, width + padAmount * 2, padAmount + i + 1, width - 1, 0,
                    width, 1, null); // Mirror right edge
            g.drawImage(input, padAmount + i, 0, padAmount + i + 1, padAmount, 0, 0, 1, padAmount, null); // Mirror top
                                                                                                          // edge
            g.drawImage(input, padAmount + i, height + padAmount, padAmount + i + 1, height + padAmount * 2, 0,
                    height - 1, 1, height, null); // Mirror bottom edge
        }
        // Mirror corners
        for (int i = 0; i < padAmount; i++) {
            for (int j = 0; j < padAmount; j++) {
                g.drawImage(input, i, j, i + 1, j + 1, 0, 0, 1, 1, null); // Mirror top-left corner
                g.drawImage(input, width + padAmount + i, j, width + padAmount + i + 1, j + 1, width - 1, 0, width, 1,
                        null); // Mirror top-right corner
                g.drawImage(input, i, height + padAmount + j, i + 1, height + padAmount + j + 1, 0, height - 1, 1,
                        height, null); // Mirror bottom-left corner
                g.drawImage(input, width + padAmount + i, height + padAmount + j, width + padAmount + i + 1,
                        height + padAmount + j + 1, width - 1, height - 1, width, height, null); // Mirror bottom-right
                                                                                                 // corner
            }
        }
        g.dispose();

        BufferedImage output = new BufferedImage(width, height, input.getType());
        int[][] embossKernel;
        switch (embossType) {
            case 1:
                embossKernel = new int[][] {
                        { 0, 0, 0 },
                        { +1, 0, -1 },
                        { 0, 0, 0 }
                };
                break;
            case 2:
                embossKernel = new int[][] {
                        { +1, 0, 0 },
                        { 0, 0, 0 },
                        { 0, 0, -1 }
                };
                break;
            case 3:
                embossKernel = new int[][] {
                        { 0, +1, 0 },
                        { 0, 0, 0 },
                        { 0, -1, 0 }
                };
                break;
            case 4:
                embossKernel = new int[][] {
                        { 0, 0, 0 },
                        { -1, 0, +1 },
                        { 0, 0, 0 }
                };
                break;
            case 5:
                embossKernel = new int[][] {
                        { 0, 0, 0 },
                        { +1, 0, -1 },
                        { 0, 0, 0 }
                };
                break;
            case 6:
                embossKernel = new int[][] {
                        { -1, 0, 0 },
                        { 0, 0, 0 },
                        { 0, 0, +1 }
                };
                break;
            case 7:
                embossKernel = new int[][] {
                        { 0, 0, 0 },
                        { +1, 0, -1 },
                        { 0, 0, 0 }
                };
                break;
            case 8:
                embossKernel = new int[][] {
                        { 0, -1, 0 },
                        { 0, 0, 0 },
                        { 0, +1, 0 }
                };
                break;
            default:
                embossKernel = new int[][] {
                        { 0, 0, 0 },
                        { +1, 0, -1 },
                        { 0, 0, 0 }
                };

        }

        int midValue = 128;
        Number[][] kernelNumber = new Number[embossKernel.length][embossKernel[0].length];
        for (int i = 0; i < embossKernel.length; i++) {
            for (int j = 0; j < embossKernel[0].length; j++) {
                kernelNumber[i][j] = embossKernel[i][j];
            }
        }

        BufferedImage filteredImage = NegativeAllowance.applyFilter(paddedInput, kernelNumber, midValue);

        // Create a new image to store the filtered pixels without padding
        output = new BufferedImage(width, height, input.getType());

        // Find the maximum pixel value in the filtered image
        int maxPixelValue = 0;
        for (int y = 0; y < filteredImage.getHeight(); y++) {
            for (int x = 0; x < filteredImage.getWidth(); x++) {
                int pixelValue = filteredImage.getRGB(x, y);
                int red = (pixelValue >> 16) & 0xFF;
                int green = (pixelValue >> 8) & 0xFF;
                int blue = pixelValue & 0xFF;
                int intensity = Math.max(Math.max(red, green), blue);
                maxPixelValue = Math.max(maxPixelValue, intensity);
            }
        }

        // Set the filtered pixel values in the output image with normalization and
        // clamping
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = filteredImage.getRGB(x + padAmount, y + padAmount);
                int red = (pixelValue >> 16) & 0xFF;
                int green = (pixelValue >> 8) & 0xFF;
                int blue = pixelValue & 0xFF;
                int intensity = Math.max(Math.max(red, green), blue);
                // Normalize the intensity value by scaling it to the range 0-255
                int normalizedIntensity = (int) (255.0 * intensity / maxPixelValue);
                // Clamp the normalized intensity value to the range 0-255
                int clampedIntensity = Math.max(0, Math.min(normalizedIntensity, 255));
                // Set the clamped pixel value in the output image
                int clampedPixelValue = (clampedIntensity << 16) | (clampedIntensity << 8) | clampedIntensity;
                output.setRGB(x, y, clampedPixelValue);
            }
        }

        return output;
    }

}
