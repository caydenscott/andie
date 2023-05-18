package cosc202.andie.SelectActions;

import java.awt.Color;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.DrawShapes.DrawTriangle;

/**
 * <p>
 * Select triangle class handles user selecting area and adding triangle to image
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

public class SelectTriangle extends SelectShape {
    /**
     * Constructor for select rectange.
     * @param target
     * @param colour
     * @param filled
     * @param outlineThickness
     */
    public SelectTriangle(ImagePanel target, Color colour, boolean filled, int outlineThickness) {
        super(target, colour, filled, outlineThickness);
    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new DrawTriangle(sa, colour, filled, outlineThickness));
    }
    
}
