package cosc202.andie.SelectActions;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import cosc202.andie.ImagePanel;

import javax.swing.BorderFactory;

/**
 * <p>
 * Listener to get when user selects an area of the image.
 * </p>
 * 
 * <p>
 * Extends on {@link MouseListener} which handles when user clicks screen.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public abstract class SelectAction implements MouseListener, MouseMotionListener {

    private Point startPoint;
    private Point endPoint;
    protected ImagePanel target;
    private JButton confirmButton;

    public SelectAction(ImagePanel target) {
        this.target = target;
    }



    public void mousePressed(MouseEvent e) {
        // if there is already a selected area, dont do anything, as user may want to cancel
        if (startPoint != null) {
            return;
        }
        // add the start point relative to the image
        startPoint = getPosition(e);
     }
 
     public void mouseReleased(MouseEvent e) {
        // first check that if for some reason there is no start point (possibly draged from outside window)
        // if there is already a selected area, dont do anything, as user may want to cancel
        if (startPoint == null || endPoint != null) {
            return;
        }

        // add the end point relative to the image
        endPoint = getPosition(e);

        SelectedArea sa = new SelectedArea(startPoint, endPoint);

        // show preview - which also allows user to confirm selection - or cancel
        showPreview(sa);
        
     }

     // just to satisfy implementation
     public void mouseEntered(MouseEvent e) {
        
     }
 
     public void mouseExited(MouseEvent e) {
        
     }
 
     public void mouseClicked(MouseEvent e) {
        // cancel the selection if user selects outside area
        clear();
     }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startPoint == null) {
            return;
        }
        showPreview(new SelectedArea(startPoint, e.getPoint()));
        endPoint = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    
    private void showPreview(SelectedArea sa) {

        // remove any previous instances
        if (confirmButton != null) {
            target.remove(confirmButton);
        }

        confirmButton = new JButton("✓");

        confirmButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check that the selection hasnt been cancelled
                if (startPoint == null || endPoint == null) {
                    return;
                }
                // draw the shape, and repaint, note this is checks start and end again
                apply(new SelectedArea(target.relativeToImagePoint(startPoint), target.relativeToImagePoint(endPoint)));
                

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


        target.add(confirmButton);

        

        //confirmButton.repaint();
        //confirmButton.getParent().revalidate();

        

        target.repaint();
        
        target.getParent().revalidate();

        target.setVisible(true);

        
    }

    protected abstract void apply(SelectedArea sa);

    /**
     * Removes all elements added to the screen in the selection process, does not however
     * undo any changes to the image. Also resets the selected area.
     */
    public void clear() {
        // reset the selection
        startPoint = null;
        endPoint = null;

        if (confirmButton != null) {
            target.remove(confirmButton);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    // was for getting relative to image coords, now doimg that when drawing
    private Point getPosition(MouseEvent e) {
        return e.getPoint();
    }
 
}
