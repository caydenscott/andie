package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.BorderFactory;

public class SelectAction implements MouseListener {

    private Point startPoint;
    private Point endPoint;
    private ImagePanel target;

    public SelectAction(ImagePanel target) {
        this.target = target;
    }


    /**
     * <p>
     * Constructor for ImageActions.
     * </p>
     * 
     * <p>
     * To construct an ImageAction you provide the information needed to integrate it with the interface.
     * Note that the target is not specified per-action, but via the static member {@link target}
     * via {@link setTarget}.
     * </p>
     * 
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action  (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
     *
    SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon);
        if (desc != null) {
           putValue(SHORT_DESCRIPTION, desc);
        }
        if (mnemonic != null) {
            putValue(MNEMONIC_KEY, mnemonic);
        }
    }*/

    public void mousePressed(MouseEvent e) {
        // if there is already a selected area, dont do anything, as user may want to cancel
        if (startPoint != null) {
            return;
        }
        // add the start point relative to the image
        startPoint = getPosition(e);
     }
 
     public void mouseReleased(MouseEvent e) {
        // if there is already a selected area, dont do anything, as user may want to cancel
        if (endPoint != null) {
            return;
        }

        // add the end point relative to the image
        endPoint = getPosition(e);

        SelectedArea sa = getSelectedArea();

        if (sa != null) {
            showPreview(sa);
        }
        
     }

     // just to satisfy implementation
     public void mouseEntered(MouseEvent e) {
        
     }
 
     public void mouseExited(MouseEvent e) {
        
     }
 
     public void mouseClicked(MouseEvent e) {
        // SelectedArea sa = getSelectedArea();

        // if (sa == null) {
        //     return;
        // }
        
        // // if the user double clicks cancel or confirm the selection, depending on if they are inside the selection
        // if (e.getClickCount() == 2) {
        //     // if inside the selection, they confirm
        //     if (sa.contains(getPosition(e).x, getPosition(e).y)) {
        //         // draw the shape, and repaint TESTING
        //         target.getImage().apply(new DrawRectangle(sa, Color.BLACK));
        //         target.repaint();
        //         target.getParent().revalidate();
        //     }

        //     // reset the selection either way
        //     startPoint = null;
        //     endPoint = null;

        // }
     }

     private SelectedArea getSelectedArea() {
        // if it isnt selected yet return null
        if (endPoint == null || startPoint == null) {
            return null;
        }

        // cant just return the points gained as startPoint
        // not necissarily top left of endPoint

        SelectedArea sa = new SelectedArea();

        // set the start and end points
        if (startPoint.x < endPoint.x) {
            sa.setStartX(startPoint.x);
            sa.setEndX(endPoint.x);
        } else {
            sa.setStartX(endPoint.x);
            sa.setEndX(startPoint.x);
        }
        if (startPoint.y < endPoint.y) {
            sa.setStartY(startPoint.y);
            sa.setEndY(endPoint.y);
        } else {
            sa.setStartY(endPoint.y);
            sa.setEndY(startPoint.y);
        }

        return sa;
    }

    private SelectedArea selectedAreaRelImage() {

        SelectedArea sar = new SelectedArea();

        sar.setEndX((int)target.relativeToImagePoint(endPoint).getX());
        sar.setEndY((int)target.relativeToImagePoint(endPoint).getY());
        sar.setStartX((int)target.relativeToImagePoint(startPoint).getX());
        sar.setStartY((int)target.relativeToImagePoint(startPoint).getY());

        return sar;
    }
    
    private void showPreview(SelectedArea sa) {
        // if there is a selected area then draw it TESTING
        //target.getImage().apply(new DrawRectangle(sa, Color.PINK));

        /*Graphics2D g = (Graphics2D)target.getGraphics().create();

        g.setColor(Color.PINK);
        Rectangle rect = new Rectangle(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight());

        g.draw(rect);
        g.fillRect(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight()); // (x, y, width, height) 
        g.dispose();*/

        JButton confirmButton = new JButton("✓");

        confirmButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // draw the shape, and repaint, note this is checks start and end again
                target.getImage().apply(new DrawRectangle(selectedAreaRelImage(), Color.BLACK));
                //target.repaint();
                //target.getParent().revalidate();

                // reset the selection
                startPoint = null;
                endPoint = null;

                // remove the button
                target.remove(confirmButton);
                target.repaint();
                target.getParent().revalidate();
            }
        });

        confirmButton.setOpaque(false);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        confirmButton.setBounds(sa.getStart()[0], sa.getStart()[1], sa.getWidth(), sa.getHeight());


        target.add(confirmButton, BorderLayout.CENTER);

        

        //confirmButton.repaint();
        //confirmButton.getParent().revalidate();

        

        target.repaint();
        
        target.getParent().revalidate();

        target.setVisible(true);

        
    }

    // was for getting relative to image coords, now doimg that when drawing
    private Point getPosition(MouseEvent e) {
        return e.getPoint();
    }
 
}
