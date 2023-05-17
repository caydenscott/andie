package cosc202.andie.ImageOperations.Transformations;

import java.awt.image.*;

import cosc202.andie.ImageOperations.ImageOperation;

/**
 * <p>
 * ImageOperation to flip the image.
 * </p>
 * 
 * <p>
 * The images produced by this operation have the pixels moved to different positions
 * to flip the image across y axis, ie ony x coordinates change.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class HorizontalFlipTransformation implements ImageOperation, java.io.Serializable {


    /**
     * <p>
     * Construct a Flip transformation whuch will flip across y-axis 
     * </p>
     * 
     */
    public HorizontalFlipTransformation() {
        
    }

    // 

    /**
    * FLip the image.
    *
    * Works through direct pixel manipulation.
    *
    * @param input the image to be converted
    * @return resulting flipped image
    */

    public BufferedImage apply(BufferedImage input) {
        /*// can't do in memory manipulation, as the size changes so need to make a new output image
        // Therefore need to make new buffered image to output to, maintaining height and width and
        // using the current image type.
        BufferedImage output = new BufferedImage((int)(input.getWidth()), (int)(input.getHeight()), input.getType());

        // make matrix
        AffineTransform transform = new AffineTransform(new float[] {-1.0f, 0.0f , 0.0f, 1.0f, 0.0f, 0.0f});
        // set affine transform to move to left, rotate across x-axis and move back again
        //transform.translate((-1)*input.getWidth()/2, 0);
        //transform.scale(1, 1);
        //transform.translate(input.getWidth()/2, 0);
        // create operation to carry out matrix affine transform
        AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        transformOp.filter(input, output);

        //return output;
        return output;*/

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth()/2; ++x) {
                int pixel = input.getRGB(x, y);
                int oppositePixel = input.getRGB(input.getWidth() - x - 1, y);
                // set pixel to oposite x value, swap them
                input.setRGB(input.getWidth() - x - 1, y, pixel);
                input.setRGB(x, y, oppositePixel);
            }
        }

        return input;
    }
}

