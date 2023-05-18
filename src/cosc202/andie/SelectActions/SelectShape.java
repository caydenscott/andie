package cosc202.andie.SelectActions;

import java.awt.Color;

import cosc202.andie.ImagePanel;

/**
 * <p>
 * Abstract class for all shape selecting actions to extend.
 * </p>
 * 
 * <p>
 * Extends on {@link SelectAction} which handles when user selecting area of screen.
 * </p>
 * 
 * <p>
 *  Ensures interface with colour, isfilled and outline thickness for all shapes.
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

public abstract class SelectShape extends SelectAction {

    protected Color colour;
    protected boolean filled;
    protected int outlineThickness;

    /**
     * Constructor to construct basic shape with outline thickness (which is not used if filled), if the shape
     * is filled and the shape colour.
     * @param target
     * @param colour
     * @param isFilled
     * @param outlineThickness
     */
    public SelectShape(ImagePanel target, Color colour, boolean isFilled, int outlineThickness) {
        super(target);
        this.colour = colour;
        this.filled = isFilled;
        this.outlineThickness = outlineThickness;
    }
    
    public void setColour(Color colour) {
        this.colour = colour;
    }
    public void setFilled(boolean isFilled) {
        this.filled = isFilled;
    }
    public void setOutlineThickness(int outlineThickness) {
        this.outlineThickness = outlineThickness;
    }

}
