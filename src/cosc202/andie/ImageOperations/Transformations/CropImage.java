package cosc202.andie.ImageOperations.Transformations;

import java.awt.image.BufferedImage;

import cosc202.andie.ImageOperations.ImageOperation;
import cosc202.andie.SelectActions.SelectedArea;

/**
 * <p>
 * ImageOperation to crop image.
 * </p>
 * 
 * <p>
 * The images produced by this operation has width and heigh specified in selectedArea
 * with coordinates for top left also specified.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class CropImage implements ImageOperation, java.io.Serializable {

    private SelectedArea selectedArea;

    /**
     * <p>
     * Construct a draw shape action.
     * </p>
     * 
     * <p>
     * The rotate angle is defined in degrees and is how much the
     * image will be rotated by. So far only for integer multiples
     * of 90. (ie 90, 180, 270)
     * </p>
     * 
     * @param selectedArea the area to draw the shape in
     */
    public CropImage(SelectedArea selectedArea) {

        this.selectedArea = selectedArea;
    }

    // 

    /**
    * Crop the image.
    *
    *
    * @param input the image to be converted
    * @return resulting rotated image
    */

    public BufferedImage apply(BufferedImage input) {
        int s_x = selectedArea.getStart()[0];
        int s_y = selectedArea.getStart()[1];
        int width = Math.min(selectedArea.getWidth(), input.getWidth() - s_x);
        int height = Math.min(selectedArea.getHeight(), input.getHeight() - s_y);

        // check that the selected area is within the image
        if (width <=0 || height <= 0) {
            // do nothing
            return input;
        }

        return input.getSubimage(s_x, s_y, width, height);
    }
    
}

