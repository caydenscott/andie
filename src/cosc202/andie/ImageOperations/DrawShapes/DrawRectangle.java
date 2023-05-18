package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.Graphics2D;
import java.awt.Color;

import cosc202.andie.SelectActions.SelectedArea;

/**
 * <p>
 * Draws rectangle shape to image.
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

public class DrawRectangle extends DrawShape {

    private boolean isFilled;

    public DrawRectangle(SelectedArea selectedArea, Color FillColour, boolean isFilled, int outlineThickness) {
        super(selectedArea, FillColour, outlineThickness);
        this.isFilled = isFilled;
    }

    protected void draw(Graphics2D g2d, SelectedArea sa) {
        if (isFilled) {
            g2d.fillRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight()); // (x, y, width, height) 
        } else {
            g2d.drawRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight());
        }
        
        g2d.dispose();
    }
}
