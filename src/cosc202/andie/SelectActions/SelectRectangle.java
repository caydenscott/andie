package cosc202.andie.SelectActions;

import java.awt.Color;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.DrawShapes.DrawRectangle;

/**
 * <p>
 * Select rectangle class handles user selecting area and adding rectangle to image
 * </p>
 * <p>
 * Extends on {@link SelectShape} which handles when user selecting area of screen.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class SelectRectangle extends SelectShape {
    /**
     * Constructor for select rectange.
     * @param target
     * @param colour
     * @param filled
     * @param outlineThickness
     */
    public SelectRectangle(ImagePanel target, Color colour, boolean filled, int outlineThickness) {
        super(target, colour, filled, outlineThickness);
    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new DrawRectangle(sa, colour, filled, outlineThickness));
    }
    
}
