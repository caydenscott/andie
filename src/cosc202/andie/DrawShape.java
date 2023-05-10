package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class DrawShape implements ImageOperation, java.io.Serializable {

    private SelectedArea selectedArea;
    private Color FillColour;

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
    public DrawShape(SelectedArea selectedArea, Color FillColour) {

        this.selectedArea = selectedArea;
        this.FillColour = FillColour;
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
        Graphics2D g2d = input.createGraphics(); 
        g2d.setColor(FillColour);
        /*g2d.fillRect(selectedArea.getStart()[0], selectedArea.getStart()[1], selectedArea.getWidth(), selectedArea.getHeight()); // (x, y, width, height) 
        g2d.dispose();*/

        draw(g2d, selectedArea);
        
        return input;
    }

    protected abstract void draw(Graphics2D g2d, SelectedArea sa);
    
}
