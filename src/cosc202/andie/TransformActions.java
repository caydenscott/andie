package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Translate menu.
 * </p>
 * 
 * <p>
 * The Translate menu provides actions to manipulate the size and orientation of the image.
 * This includes resizing, rotating and flipping.
 * Based on code by Steven Mills.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class TransformActions {
    /** A list of actions for the Translate menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     */
    public TransformActions() {
        actions = new ArrayList<Action>();
        actions.add(new ResizeTransformationAction("Resize", null, "Scale the image", Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new ClockwiseRotateTransformationAction("Rotate (clockwise)", null, "Rotate the image clockwise onto its side", Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new AnticlockwiseRotateTransformationAction("Rotate (anticlockwise)", null, "Rotate the image anticlockwise onto its side", Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new HorizontalFlipTransformationAction("Horizontal Flip", null, "Flip the image horizontally", Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new VerticalFlipTransformationAction("Vertical Flip", null, "Flip the image vertically", Integer.valueOf(KeyEvent.VK_R)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Transform actions.
     * </p>
     * 
     * @return The transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Transform");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    // --- action classes ---

    /**
     * <p>
     * Action to resize image by inputted percentage.
     * </p>
     * 
     * @see ResizeTransformation
     */
    public class ResizeTransformationAction extends ImageAction {

        /**
         * <p>
         * Create a new resize action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ResizeTransformationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Resize is triggered.
         * It transforms the images scale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Determine the radius - ask the user.
            int scale = 1;

            // Pop-up dialog box for asking user how much to scale
            SpinnerNumberModel resizeModel = new SpinnerNumberModel(100, 1, 1000, 1);
            JSpinner resizeSpinner = new JSpinner(resizeModel);
            int option = JOptionPane.showOptionDialog(null, resizeSpinner, "Enter scale:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                scale = resizeModel.getNumber().intValue();
            }

            target.getImage().apply(new ResizeTransformation(scale));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to rotate image clockwise.
     * </p>
     * 
     * @see RotateTransformation
     */
    public class ClockwiseRotateTransformationAction extends ImageAction {

        /**
         * <p>
         * Create a new rotate clockwise action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ClockwiseRotateTransformationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate-clockwise action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ClockwiseRotateTransformationAction is triggered.
         * It rotates the image by 90 degrees clockwise
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // rotate the image 90 degrees clockwise

            target.getImage().apply(new RotateTransformation(RotateTransformation.ROTATECLOCKWISE));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to rotate image anticlockwise.
     * </p>
     * 
     * @see RotateTransformation
     */
    public class AnticlockwiseRotateTransformationAction extends ImageAction {

        /**
         * <p>
         * Create a new rotate anticlockwise action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        AnticlockwiseRotateTransformationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate-anticlockwise action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the AnticlockwiseRotateTransformationAction is triggered.
         * It rotates the image by 90 degrees anticlockwise, ie 270 clockwise
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // rotate the image 90 degrees anticlockwise

            target.getImage().apply(new RotateTransformation(RotateTransformation.ROTATEANTICLOCKWISE));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to flip images horizontally.
     * </p>
     * 
     * @see HorizontalFlipTransformation
     */
    public class HorizontalFlipTransformationAction extends ImageAction {

        /**
         * <p>
         * Create a new horizontal flip action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        HorizontalFlipTransformationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the horizontal flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the HorizontalFlipTransformationAction is triggered.
         * It flips image horizontally.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new HorizontalFlipTransformation());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to flip images vertically.
     * </p>
     * 
     * @see VerticalFlipTransformation
     */
    public class VerticalFlipTransformationAction extends ImageAction {

        /**
         * <p>
         * Create a new vertical flip action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        VerticalFlipTransformationAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the vertical flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the VerticalFlipTransformationAction is triggered.
         * It flips image vertically.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new VerticalFlipTransformation());
            target.repaint();
            target.getParent().revalidate();
        }

    }
    
}
