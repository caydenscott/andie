package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Shape menu.
 * </p>
 * 
 * <p>
 * 
 * Based on code by Steven Mills.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class ShapeActions {
    /** A list of actions for the Shape menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>

     * </p>
     */
    public ShapeActions() {
        actions = new ArrayList<Action>();
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        // for multilingual support
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");

        //actions.add(new ResizeTransformationAction(bundle.getString("transform_1"), null,
        //        bundle.getString("transform_1_desc"), Integer.valueOf(KeyEvent.VK_R)));

        actions.add(new DrawRectangleAction("[TESTING] draw rect", null,
                "", Integer.valueOf(KeyEvent.VK_R)));
        
    }

    /**
     * <p>
     * Create a menu contianing the list of Transform actions.
     * </p>
     * 
     * @return The transform menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        JMenu transformMenu = new JMenu("[TESTING] shapes");

        for (Action action : actions) {
            transformMenu.add(new JMenuItem(action));
        }

        return transformMenu;
    }

    // --- action classes ---

    public class DrawRectangleAction extends ImageAction {

        /**
         * <p>
         * Create a new draw rectangle action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DrawRectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }


        public void actionPerformed(ActionEvent e) {
            //JToolBar toolBar = new JToolBar("Still draggable");

            //target.add(toolBar);

            SelectAction sa = new SelectAction(target);

            target.addMouseListener(sa);

            // wait for the user to select an area

        }

    }

}
