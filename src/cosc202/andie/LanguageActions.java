package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;

public class LanguageActions {
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of Language menu actions.
     * </p>
     */
    public LanguageActions() {
        actions = new ArrayList<Action>(); 
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        actions.add(new EnglishAction(bundle.getString("language_1"), null, bundle.getString("language_1_desc"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new MaoriAction(bundle.getString("language_2"), null, bundle.getString("language_2_desc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new ItalianoAction(bundle.getString("language_3"), null, bundle.getString("language_3_desc"), Integer.valueOf(KeyEvent.VK_I)));
    }

    /**
     * <p>
     * Create a menu contianing the list of language actions.
     * </p>
     * 
     * @return The language menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        JMenu languageMenu = new JMenu(bundle.getString("language_tt"));

        for (Action action: actions) {
            languageMenu.add(new JMenuItem(action));
        }

        return languageMenu;
    }

    /**
     * <p>
     * Action to change the language to English.
     * </p>
     * 
     * @see 
     */
    public class EnglishAction extends ImageAction {

        /**
         * <p>
         * Create a new English action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        EnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the English action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the EnglishAction is triggered.
         * It change the language to English.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {     
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");      
            prefs.remove("language");
            prefs.remove("country");
            //Confirm window and end option
            int userOption = JOptionPane.showConfirmDialog(null, bundle.getString("language_tt_desc"), bundle.getString("language_tt"), JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (userOption == JOptionPane.OK_OPTION) {
                System.exit(1);
            }
        }
    }

    /**
     * <p>
     * Action to change the language to Maori.
     * </p>
     * 
     * @see 
     */
    public class MaoriAction extends ImageAction {

        /**
         * <p>
         * Create a new Maori action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MaoriAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Maori action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MaoriAction is triggered.
         * It change the language to Maori.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");      
            prefs.put("language", "mi");
            prefs.put("country", "NZ");
            //Confirm window and end option
            int userOption = JOptionPane.showConfirmDialog(null, bundle.getString("language_tt_desc"), bundle.getString("language_tt"), JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (userOption == JOptionPane.OK_OPTION) {
                System.exit(1);
            }
        }
    }

    /**
     * <p>
     * Action to change the language to Italiano.
     * </p>
     * 
     * @see 
     */
    public class ItalianoAction extends ImageAction {

        /**
         * <p>
         * Create a new Italiano action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ItalianoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Italiano action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ItalianoAction is triggered.
         * It change the language to Italiano.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {         
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");      
            prefs.put("language", "it");
            prefs.put("country", "IT");
            //Confirm window and end option
            int userOption = JOptionPane.showConfirmDialog(null, bundle.getString("language_tt_desc"), bundle.getString("language_tt"), JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (userOption == JOptionPane.OK_OPTION) {
                System.exit(1);
            }
        }
    }
}
