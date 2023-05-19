package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import cosc202.andie.ImageOperations.ImageOperation;


public class DrawLine implements ImageOperation, java.io.Serializable {

    private Shape path;
    private Color FillColour;
    private int outlineThickness;

    /**
     * <p>
     * Construct a draw line action.
     * </p>
     * 
     * 
     * @param selectedArea the area to draw the shape in
     */
    public DrawLine(Shape path, Color FillColour, int outlineThickness) {

        this.path = path;
        this.FillColour = FillColour;
        this.outlineThickness = outlineThickness;
    }

    // 

    /**
    * Draw on the image.
    *
    *
    * @param input the image to be converted
    * @return resulting image with drawn line
    */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics(); 
        g2d.setColor(FillColour);

        g2d.setStroke(new BasicStroke(outlineThickness));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.draw(path);

        g2d.dispose();
        
        return input;
    }
    
}
