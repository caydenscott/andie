package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cosc202.andie.ImageOperations.ImageOperation;
import cosc202.andie.SelectActions.SelectedArea;

/**
 * <p>
 * Abstract draw shape class, handles shape outline and colour.
 * </p>
 * 
 * <p>
 * This class extends {@link DrawShape}.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public abstract class DrawShape implements ImageOperation, java.io.Serializable {

    private SelectedArea selectedArea;
    private Color FillColour;
    private int outlineThickness;

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
    public DrawShape(SelectedArea selectedArea, Color FillColour, int outlineThickness) {

        this.selectedArea = selectedArea;
        this.FillColour = FillColour;
        this.outlineThickness = outlineThickness;
    }

    // 

    /**
    * Draw on the image.
    *
    *
    * @param input the image to be converted
    * @return resulting rotated image
    */

    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics(); 
        g2d.setColor(FillColour);
        /*g2d.fillRect(selectedArea.getStart()[0], selectedArea.getStart()[1], selectedArea.getWidth(), selectedArea.getHeight()); // (x, y, width, height) 
        g2d.dispose();*/

        g2d.setStroke(new BasicStroke(outlineThickness));

        draw(g2d, selectedArea);
        
        return input;
    }

    protected abstract void draw(Graphics2D g2d, SelectedArea sa);
    
}
