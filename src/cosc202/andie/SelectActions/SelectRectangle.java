package cosc202.andie.SelectActions;

import java.awt.Color;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.DrawShapes.DrawRectangle;

public class SelectRectangle extends SelectAction {
    private Color colour;
    private boolean filled;
    private int outlineThickness;

    public SelectRectangle(ImagePanel target, Color colour, boolean filled, int outlineThickness) {
        super(target);
        this.colour = colour;
        this.filled = filled;
        this.outlineThickness = outlineThickness;
    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new DrawRectangle(sa, colour, filled, outlineThickness));
    }
    
}
