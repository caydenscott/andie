package cosc202.andie.SelectActions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import javax.swing.JPanel;


import cosc202.andie.ImagePanel;


/**
 * <p>
 * Listener to get when user clicks and drags a path accross the screen.
 * </p>
 * 
 * <p>
 * Extends on {@link MouseMotionListener} which handles when user clicks screen and drags, and
 * {@link MouseListener} which handles click events.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public abstract class SelectPathAction implements MouseMotionListener, MouseListener {

    private Path2D path;
    protected ImagePanel target;
    private JPanel preview;

    public SelectPathAction(ImagePanel target) {
        this.target = target;
        this.path = new Path2D.Double();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        path.moveTo(e.getX(), e.getY());

     }
 
     @Override
     public void mouseReleased(MouseEvent e) {
        if (path == null) {
            return;
        }

        apply(relativeToImage());

        clear();
     }

     @Override
     // just to satisfy implementation
     public void mouseEntered(MouseEvent e) {
        
     }
 
     @Override
     public void mouseExited(MouseEvent e) {
        
     }
     
     @Override
     public void mouseClicked(MouseEvent e) {
        
     }

     @Override
    public void mouseDragged(MouseEvent e) {
        path.lineTo(e.getX(), e.getY());
        showPreview();
    }

    
    
    private void showPreview() {
        // remove previous instance if there is one
        if (preview != null) {
            target.remove(preview);
        }
        

        preview = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                Path2D previewLine = new Path2D.Double(path);

                g2d.setColor(Color.GRAY);

                g2d.setStroke(new BasicStroke(2));

                g2d.draw(previewLine);

                g2d.dispose();
            }
        };

        
        
       //preview.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        preview.setOpaque(false);
        preview.setBounds(target.getBounds());
        
        
        target.add(preview);

        target.repaint();
        
        target.getParent().revalidate();

        target.setVisible(true);

        
    }

    protected abstract void apply(Shape path);

    /**
     * Removes all elements added to the screen in the selection process, does not however
     * undo any changes to the image. Also resets the selected area.
     */
    public void clear() {
        // if there is a preview remove it
        if (preview != null) {
            target.remove(preview);

            target.repaint();
        
            target.getParent().revalidate();

            target.setVisible(true);
        }
        
        // reset the selection
        path = new Path2D.Double();
    }


    private Shape relativeToImage() {
        // converts to images coordinate system (note scaled needs to be inverted for image POV and comes as percentage)
        AffineTransform af = new AffineTransform();
        af.scale(100/target.getZoom(), 100/target.getZoom());
        //af.scale(2, 2);
        return path.createTransformedShape(af);
    }
 
}

