package cosc202.andie.ImageOperations.DrawShapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import cosc202.andie.SelectActions.SelectedArea;

/**
 * <p>
 * Draws triangle shape to image.
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

public class DrawTriangle extends DrawShape {
    private boolean isFilled;

    public DrawTriangle(SelectedArea selectedArea, Color FillColour, boolean isFilled, int outlineThickness) {
        super(selectedArea, FillColour, outlineThickness);
        this.isFilled = isFilled;
    }

    protected void draw(Graphics2D g2d, SelectedArea sa) {

        // get edges of triangle
        //     C
        //    / \
        //   A - B
        int Ax = sa.getStart()[0];
        int Ay = sa.getEnd()[1];

        int Bx = sa.getEnd()[0];
        int By = sa.getEnd()[1];

        int Cx = sa.getStart()[0] + (sa.getWidth()/2);
        int Cy = sa.getStart()[1];

        Path2D triangleOutline = new Path2D.Double();

        // make triangle in path
        triangleOutline.moveTo(Ax, Ay);
        triangleOutline.lineTo(Bx, By);
        triangleOutline.lineTo(Cx, Cy);
        triangleOutline.closePath();

        // solution from stackoverflow question: How do I draw a triangle? answered Apr 4, 2015 at 15:15 Hovercraft Full Of Eels
        // smooths edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        if (isFilled) {
            g2d.fill(triangleOutline);
        } else {
            g2d.draw(triangleOutline);
        }
        
        g2d.dispose();
    }
}
