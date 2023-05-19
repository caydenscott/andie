package cosc202.andie.ImageOperations.Filters;

import java.awt.image.*;

import cosc202.andie.ImageOperations.ImageOperation;

public class InvertImage implements ImageOperation, java.io.Serializable{

    /**
     * Create a new InvertImage operation
     */

     public InvertImage(){

     }

     /**
      * Apply inverted colour to an image
      *
      * rest of commenting goes here
      *
      * @param input the image to be converted
      * @return resulting inverted image
      */

      public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                r = 255 - r;
                g = 255 - g;
                b = 255 - b; 

                argb =  (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        
        return input;
    }

}
