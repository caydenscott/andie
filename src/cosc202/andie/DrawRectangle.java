package cosc202.andie;

import java.awt.Graphics2D;

public class DrawRectangle extends DrawShape {

    public DrawRectangle(SelectedArea selectedArea, java.awt.Color FillColour) {
        super(selectedArea, FillColour);
    }

    protected void draw(Graphics2D g2d, SelectedArea sa) {
        g2d.fillRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight()); // (x, y, width, height) 
        g2d.dispose();
    }
}
