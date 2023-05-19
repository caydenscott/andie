package cosc202.andie.ImageActions;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.EditableImage;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        actions.add(new FileOpenAction(bundle.getString("file_1"), null, bundle.getString("file_1_desc"),
                Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(bundle.getString("file_2"), null, bundle.getString("file_2_desc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(bundle.getString("file_3"), null, bundle.getString("file_3_desc"),
                Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(bundle.getString("file_4"), null, bundle.getString("file_4_desc"),
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(bundle.getString("file_5"), null, bundle.getString("file_5_desc"),
                Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        JMenu fileMenu = new JMenu(bundle.getString("file_tt"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                if(!EditableImage.getChanges()){
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        EditableImage.clearStack();
                        EditableImage.changeMade(0);
                        target.getImage().open(imageFilepath);
                    } catch (NullPointerException nEx) {
                        Object[] options1 = { "OK" };
                        JOptionPane.showOptionDialog(null, bundle.getString("file_open_error_2"),
                                bundle.getString("file_open_error_1"), JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                    } catch (Exception ex) {
                        Object[] options1 = { "OK" };
                        JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                                bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                    }
                }else{
                    if(!EditableImage.getRecord()){
                        Object[] options = { bundle.getString("open_no_save_option_1"), bundle.getString("open_no_save_option_2"), bundle.getString("no_save_option_2") }; // Modify options here
                        int choice = JOptionPane.showOptionDialog(null,
                        bundle.getString("open_no_save_1"), bundle.getString("no_save_warning"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if(choice == 0){
                            try {
                                ImageAction.target.getImage().save(); 
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            try {
                                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                                EditableImage.clearStack();
                                EditableImage.changeMade(0);
                                target.getImage().open(imageFilepath);
                            } catch (NullPointerException nEx) {
                                Object[] options1 = { "OK" };
                                JOptionPane.showOptionDialog(null, bundle.getString("file_open_error_2"),
                                    bundle.getString("file_open_error_1"), JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                            } catch (Exception ex) {
                                Object[] options1 = { "OK" };
                                JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                                    bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                            }
                        }else if(choice == 1){
                            try {
                                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                                EditableImage.clearStack();
                                EditableImage.changeMade(0);
                                target.getImage().open(imageFilepath);
                            } catch (NullPointerException nEx) {
                                Object[] options1 = { "OK" };
                                JOptionPane.showOptionDialog(null, bundle.getString("file_open_error_2"),
                                    bundle.getString("file_open_error_1"), JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                            } catch (Exception ex) {
                                Object[] options1 = { "OK" };
                                JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                                    bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                            } 
                        }
                    }else{
                        try {
                            String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                            target.getImage().open(imageFilepath);
                        } catch (NullPointerException nEx) {
                            Object[] options1 = { "OK" };
                            JOptionPane.showOptionDialog(null, bundle.getString("file_open_error_2"),
                                bundle.getString("file_open_error_1"), JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                        } catch (Exception ex) {
                            Object[] options1 = { "OK" };
                            JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                                bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                        }
                    }
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            try {
                target.getImage().save();
                EditableImage.changeMade(0);
            } catch (NullPointerException NEx) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("file_save_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            } catch (IllegalArgumentException IAEx) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("file_save_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }catch (Exception ex) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                        bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);

            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
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
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                    EditableImage.changeMade(0);
                } catch (IllegalArgumentException IAEx) {
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                            bundle.getString("file_save_error_1"), JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } catch (Exception ex) {
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                            bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
            }
        }

    }

    /**
     * <p>
     * Action to export the image to a new file location.
     * </p>
     * 
     * @see EditableImage#export(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and export the image to it.
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
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().export(imageFilepath);
                    EditableImage.changeMade(0);
                } catch (IllegalArgumentException iAEx) {
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                            bundle.getString("file_export_error_1"), JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } catch (Exception ex) {
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, bundle.getString("file_error_unknown_2") + ":  " + ex,
                            bundle.getString("file_error_unknown_1"), JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            if(!EditableImage.getChanges()){
                System.exit(0);
            }else{
                if(!EditableImage.getRecord()){
                    Object[] options = { bundle.getString("exit_no_save_option_1"), bundle.getString("exit_no_save_option_2"), bundle.getString("no_save_option_2") }; // Modify options here
                    int choice = JOptionPane.showOptionDialog(null,
                        bundle.getString("exit_no_save_1"), bundle.getString("no_save_warning"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (choice == 0) { // Save and Exit
                        try {
                            ImageAction.target.getImage().save();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.exit(0);
                    } else if (choice == 1) { // Exit
                        System.exit(0);
                    }
                }
                else{
                    int userOption = JOptionPane.showConfirmDialog(null, bundle.getString("macro_warning_2"), 
                    bundle.getString("macro_tt"), JOptionPane.OK_OPTION);
                    if(userOption != JOptionPane.OK_OPTION) return;
                    
                    Object[] options = { bundle.getString("exit_no_save_option_1"), bundle.getString("exit_no_save_option_2"), bundle.getString("no_save_option_2") }; // Modify options here
                    int choice = JOptionPane.showOptionDialog(null,
                        bundle.getString("exit_no_save_1"), bundle.getString("no_save_warning"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (choice == 0) { // Save and Exit
                        try {
                            ImageAction.target.getImage().save();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.exit(0);
                    } else if (choice == 1) { // Exit
                        System.exit(0);
                    }
                }
            }
        }

    }

}
