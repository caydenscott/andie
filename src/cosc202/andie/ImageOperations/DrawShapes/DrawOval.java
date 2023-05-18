package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

import cosc202.andie.SelectActions.SelectedArea;

/**
 * <p>
 * Draws oval shape to image.
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

public class DrawOval extends DrawShape {

    private boolean isFilled;

    public DrawOval(SelectedArea selectedArea, Color FillColour, boolean isFilled, int outlineThickness) {
        super(selectedArea, FillColour, outlineThickness);
        this.isFilled = isFilled;
    }

    protected void draw(Graphics2D g2d, SelectedArea sa) {

        // solution from stackoverflow question: How do I draw a triangle? answered Apr 4, 2015 at 15:15 Hovercraft Full Of Eels
        // smooths edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            
        if (isFilled) {
            g2d.fillOval(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight()); // (x, y, width, height) 
        } else {
            g2d.drawOval(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight());
        }
        
        g2d.dispose();
    }
}
