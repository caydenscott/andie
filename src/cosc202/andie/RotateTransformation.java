package cosc202.andie;

import java.awt.geom.AffineTransform;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to rotate the image.
 * </p>
 * 
 * <p>
 * The images produced by this operation have the pixels moved to different positions
 * to rotate the image around a point.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */
public class RotateTransformation implements ImageOperation, java.io.Serializable {

    public static int ROTATECLOCKWISE = 90;
    public static int ROTATEANTICLOCKWISE = 270;

    /**
     * The angle to rotate to, in radians.
     */
    private double rotateAngle;

    /**
     * <p>
     * Construct a Rotate transformation with input angle.
     * </p>
     * 
     * <p>
     * The rotate angle is defined in degrees and is how much the
     * image will be rotated by. So far only for integer multiples
     * of 90. (ie 90, 180, 270)
     * </p>
     * 
     * @param rotateAngle how far the image should be rotated (degrees)
     */
    public RotateTransformation(int rotateAngle) {
        // NOTE THAT HERE THE INPUT (DEGREES) IS CONVERTED TO RADIANS FOR WORKING
        this.rotateAngle = Math.toRadians(rotateAngle);
    }

    // 

    /**
    * Rotate the image.
    *
    * Works through affine transformation manipulation.
    *
    * @param input the image to be converted
    * @return resulting rotated image
    */

    public BufferedImage apply(BufferedImage input) {
        //return input;

        // calculate new height and width

        double cosTheta = Math.cos(rotateAngle);
        double sinTheta = Math.sin(rotateAngle);

        int nWidth = (int)Math.abs(input.getHeight()*sinTheta + input.getWidth()*cosTheta);

        int nHeight = (int)Math.abs(input.getHeight()*cosTheta + input.getWidth()*sinTheta);

        // when rotating clockwise we pivot around half the input height, but if anticlockwise we rotate about 
        // half the input width. noting that x, y pivot point are the same - square
        double rotatePoint = (cosTheta > 0 ? input.getHeight()/2 : input.getWidth()/2);


        // can't do in memory manipulation, as the size changes so need to make a new output image
        // Therefore need to make new buffered image to output to, scaling heigh and width appropriately and
        // using the current image type.
        BufferedImage output = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);

        

        //Just messing around with Matrix maths stuff might come back to it later
        //float cosThetaf = (float)Math.cos(rotateAngle);
        //float sinThetaf = (float)Math.sin(rotateAngle);
        //AffineTransform transform = new AffineTransform(new float[] {cosThetaf, (-1.0f)*sinThetaf, sinThetaf, cosThetaf, 0.0f, 0.0f});
        //transform.translate(1.0f, 1.0f);
        


        // make matrix
        AffineTransform transform = new AffineTransform();
        // set affine transform to rotate by give angle about the centre of the image
        transform.rotate(rotateAngle, rotatePoint, rotatePoint);
        // create operation to carry out matrix affine transform
        AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        transformOp.filter(input, output);

        return output;
    }
}
