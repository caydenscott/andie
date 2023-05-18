package cosc202.andie.SelectActions;

import java.awt.Color;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.DrawShapes.DrawOval;

/**
 * <p>
 * Select oval class handles user selecting area and adding oval to image
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

public class SelectOval extends SelectShape {
    /**
     * Constructor for select oval.
     * @param target
     * @param colour
     * @param filled
     * @param outlineThickness
     */
    public SelectOval(ImagePanel target, Color colour, boolean filled, int outlineThickness) {
        super(target, colour, filled, outlineThickness);
    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new DrawOval(sa, colour, filled, outlineThickness));
    }
    
}