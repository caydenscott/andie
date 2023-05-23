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
                apply(new SelectedArea(startPoint, endPoint));
                

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
