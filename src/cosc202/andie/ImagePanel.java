package cosc202.andie;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import cosc202.andie.ImageOperations.Filters.BrightnessAndContrast;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    /** current opened toolbar on the panel */
    private JToolBar toolbar;

    private KeyListener keyListener;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
        this.setLayout(null); 
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }


    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();
        }
    }

    public BrightnessAndContrast getInteger() {
        return null;
    }

    /**
     * <p>
     * Convert a point in the ImagePanel to a point in the image. Taking scale into account.
     * </p>
     * 
     * @param p Point relative to the ImagePanel
     * @return Point relative to the image
     */
    public Point relativeToImagePoint(Point p) {
        return new Point((int) (p.x/scale), (int) (p.y/scale));
    }

    /**
     * <p>
     * Handles adding a toolbar to the panel, only opens one at a time.
     * </p>
     * @param toolBar toolbar to add
     */
    public void addToolbar(JToolBar toolBar) {
        // remove the current toolbar
        removeToolbar();
        // handle sizing and bounds on panel
        toolBar.setBounds(getInsets().top, getInsets().left, (int)toolBar.getPreferredSize().getWidth(), (int)toolBar.getPreferredSize().getHeight());
        // set the new one and add it to screen
        this.toolbar = toolBar;

        // add new toolbar
        add(toolBar);

        this.requestFocus();

        repaint();
        getParent().revalidate();
        setVisible(true);
    }

    /**
     * <p>
     * Handles removing the currently open toolbar from the screen. Also removes any mouse listeners.
     * </p>
     */
    public void removeToolbar() {
        if (toolbar == null) {
            return;
        }
        // if the toolbar is currently floating add it back to image panel so we can remove it
        // solution from stackoverflow question sited below
        if (isFloating(toolbar)) {
            BasicToolBarUI basicToolbarUI = (BasicToolBarUI) toolbar.getUI();
            basicToolbarUI.setFloating(false, null);
        }
        
        remove(toolbar);
        repaint();
        getParent().revalidate();

        toolbar = null;

        // remove all mouse listeners
        for (MouseListener ms : getMouseListeners()) {
            if (ms != keyListener) {
                removeMouseListener(ms);
            }
        }
        for (MouseMotionListener mml : getMouseMotionListeners()) {
            if (mml != keyListener) {
                removeMouseMotionListener(mml);
            }
        }

        this.requestFocus();
    }

    /**
     * 
     * @param keyListener key listener to add to panel
     */
    public void addKeyShortcutListener(KeyAdapter keyListener) {
        this.keyListener = keyListener;
        this.addKeyListener(this.keyListener);
    }

    // method/solution sourced from stackoverflow question: Remove floating JToolbar answered Feb 19, 2021 at 19:48 Aldo Canepa
    private boolean isFloating(JToolBar toolbar) {
        ToolBarUI ui = toolbar.getUI();
        return ui instanceof BasicToolBarUI && ( (BasicToolBarUI) ui).isFloating();
    }

}
