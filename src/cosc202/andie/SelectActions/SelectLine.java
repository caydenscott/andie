package cosc202.andie.SelectActions;

import java.awt.Color;
import java.awt.Shape;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.DrawShapes.DrawLine;

public class SelectLine extends SelectPathAction {

    protected Color colour;
    protected int outlineThickness;

    public SelectLine(ImagePanel target, Color colour, int outlineThickness) {
        super(target);

        this.colour = colour;
        this.outlineThickness = outlineThickness;
    }

    @Override
    protected void apply(Shape path) {
        target.getImage().apply(new DrawLine(path, colour, outlineThickness));
    }
    
    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setOutlineThickness(int outlineThickness) {
        this.outlineThickness = outlineThickness;
    }
}

