package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Macro menu.
 * </p>
 * 
 * <p>
 * The Macro menu contains actions that create a macro and load one to apply to images.
 * </p>
 */
public class MacroActions {
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of Macro menu actions.
     * </p>
     */
    public MacroActions() {
        actions = new ArrayList<Action>(); 
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        actions.add(new StartAction(bundle.getString("macro_1"), null, bundle.getString("macro_1_desc"), Integer.valueOf(KeyEvent.VK_HOME)));
        actions.add(new StopAction(bundle.getString("macro_2"), null, bundle.getString("macro_2_desc"), Integer.valueOf(KeyEvent.VK_END)));
        actions.add(new LoadAction(bundle.getString("macro_3"), null, bundle.getString("macro_3_desc"), Integer.valueOf(KeyEvent.VK_L)));
    }

    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        JMenu macroMenu = new JMenu(bundle.getString("macro_tt"));

        for (Action action: actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

    public class StartAction extends ImageAction {

        /**
         * <p>
         * Create a new start action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        StartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the start action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the StartAction is triggered.
         * It will start to recording operations.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(EditableImage.getRecord()){
                ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
                //Warning window
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("macro_warning"),
                    bundle.getString("macro_tt"), JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            else {
                ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
                //Notice window
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("macro_notice"),
                    bundle.getString("macro_tt"), JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                EditableImage.record(1); 
            }
        }
    }

    public class StopAction extends ImageAction {

        /**
         * <p>
         * Create a new stop action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        StopAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the stop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the StopAction is triggered.
         * It will stop recording operations until the <code>.ops</code> file is create.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {     
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    EditableImage.createMacro(opsFilepath);
                    EditableImage.record(0);
                } catch (Exception ex) {
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                        bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
            }
        }
    }

    public class LoadAction extends ImageAction {

        /**
         * <p>
         * Create a new load action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        LoadAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the load action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the LoadAction is triggered.
         * It will load a macro to apply operations to an opened image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {     
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    EditableImage.changeMade(0);
                    target.getImage().load(imageFilepath);
                } catch (Exception ex) {
                    Object[] options1 = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                        bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }
    }
}
