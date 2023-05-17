package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cosc202.andie.SelectActions.SelectedArea;

public class DrawRectangle extends DrawShape {

    private boolean isFilled;
    private int outlineThickness;

    public DrawRectangle(SelectedArea selectedArea, java.awt.Color FillColour, boolean isFilled, int outlineThickness) {
        super(selectedArea, FillColour);
        this.isFilled = isFilled;
        this.outlineThickness = outlineThickness;
    }

    protected void draw(Graphics2D g2d, SelectedArea sa) {
        if (isFilled) {
            g2d.fillRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight()); // (x, y, width, height) 
        } else {
            g2d.setStroke(new BasicStroke(outlineThickness));
            g2d.drawRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight());
        }
        
        g2d.dispose();
    }
}
