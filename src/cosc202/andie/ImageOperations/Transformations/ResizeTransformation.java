package cosc202.andie.ImageOperations.Transformations;

import java.awt.geom.AffineTransform;
import java.awt.image.*;

import cosc202.andie.ImageOperations.ImageOperation;

/**
 * <p>
 * ImageOperation to resize the image.
 * </p>
 * 
 * <p>
 * The images produced by this operation have the pixels averaged to 
 * decrease the size or extended. Note that decreasing the size of an image 
 * will cause loss of deffinition.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class ResizeTransformation implements ImageOperation, java.io.Serializable{
    /**
     * The scale to which the image is resized. 10 will give image of 10%.
     */
    private int scale;

    /**
     * <p>
     * Construct a Resize transformation with input scale.
     * </p>
     * 
     * <p>
     * The scale given defines by what percentage the image size will
     * be increased, when greater than 100 will increase size, and, when less 
     * than 100 will decrease the image size.
     * </p>
     * 
     * @param scale by how much the image should be scaled by the newly initiated transformation
     */
    public ResizeTransformation(int scale) {
        this.scale = scale;
    }

    // 

    /**
      * Resize the image.
      *
      * Works through affine transformation manipulation.
      *
      * @param input the image to be converted
      * @return resulting scaled image
      */

      public BufferedImage apply(BufferedImage input) throws NegativeArraySizeException{
        try{
          // get scale in double whereby 100%
          float s = (float)scale/100.0f;

          // can't do in memory manipulation, as the size changes so need to make a new output image
          // Therefore need to make new buffered image to output to, scaling heigh and width appropriately and
          // using the current image type.
          BufferedImage output = new BufferedImage((int)(input.getWidth()*s), (int)(input.getHeight()*s), BufferedImage.TYPE_INT_ARGB);

          //BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
          // set affine transform matrix to resize by scale

          // to standardise the input image converting all to ARGB type, this is because transform op doesnt seem
          // to work with png which has type 0
          BufferedImage convertedImg = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
          convertedImg.getGraphics().drawImage(input, 0, 0, null);
          
          AffineTransform transform = new AffineTransform(new float[] {s, 0.0f, 0.0f, s, 0.0f, 0.0f});

          //transform.scale(s, s);
          // create operation to carry out matrix affine transform
          AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

          transformOp.filter(convertedImg, output);
          transformOp.filter(convertedImg, output);
        BufferedImage convertedOutput = new BufferedImage(output.getWidth(), output.getHeight(), input.getType());
        convertedOutput.getGraphics().drawImage(output, 0, 0, null);

        return convertedOutput;

        } catch(NegativeArraySizeException NASEx){
          throw NASEx;
        }

        
      }
}
