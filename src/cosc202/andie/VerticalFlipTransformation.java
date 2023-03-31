package cosc202.andie;


import java.awt.image.*;

/**
 * <p>
 * ImageOperation to flip the image.
 * </p>
 * 
 * <p>
 * The images produced by this operation have the pixels moved to different positions
 * to flip the image across x axis, ie ony y coordinates change.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class VerticalFlipTransformation implements ImageOperation, java.io.Serializable {


    /**
     * <p>
     * Construct a Flip transformation whuch will flip across x-axis 
     * </p>
     * 
     */
    public VerticalFlipTransformation() {
        
    }

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

        for (int y = 0; y < input.getHeight()/2; ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int pixel = input.getRGB(x, y);
                int oppositePixel = input.getRGB(x, input.getHeight() - y - 1);
                // set pixel to oposite y value, swap them
                input.setRGB(x, input.getHeight() - y - 1, pixel);
                input.setRGB(x, y, oppositePixel);
            }
        }

        return input;
    }
}

