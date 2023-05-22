package cosc202.andie.ImageActions;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.EditableImage;
import cosc202.andie.ImageOperations.ImageOperation;
import cosc202.andie.ImageOperations.Filters.EmbossFilter;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class EditActions {

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        actions.add(new UndoAction(bundle.getString("edit_1"), null, bundle.getString("edit_1_desc"),
                Integer.valueOf(KeyEvent.VK_U)));
        actions.add(new RedoAction(bundle.getString("edit_2"), null, bundle.getString("edit_2_desc"),
                Integer.valueOf(KeyEvent.VK_R)));

        // add actions
        actions.add(new ToolbarAction(bundle.getString("edit_3"), null, bundle.getString("edit_3_desc"),
                Integer.valueOf(KeyEvent.VK_R)));

                
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        JMenu editMenu = new JMenu(bundle.getString("edit_tt"));

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class ToolbarAction extends ImageAction {

        private JToolBar toolbar;

        /** variables for when adding the shape */

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
        ToolbarAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            // load up multiligual text
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");

            // check if the image is loaded up first
            if (!target.getImage().hasImage()) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("select_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                return;
            }

            // if there is already a toolbar open dont open a new one
            // if (toolbar != null) {
            // return;
            // }

            makeToolBar(bundle);

            // add the toolbar which adds all options to make shapes
            target.addToolbar(toolbar);

        }

        // cleans up when closing the add shape toolbar, stops shape selection and
        // removes toolbar ui
        private void close() {

            target.removeToolbar();

            toolbar = null;
        }

        private void makeToolBar(ResourceBundle bundle) {

            toolbar = new JToolBar();
            toolbar.setLayout(new FlowLayout());

            // Color Selector Button ------------------

            // Shape Radio Buttons ---------------------
            JButton undoButton = new JButton("←");
            JButton redoButton = new JButton("→");
            JButton embossButton = new JButton(bundle.getString("filter_6"));
            

            // undoButton.addActionListener(new ActionListener() {
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         target.getImage().undo();
            //         target.repaint();
            //         target.getParent().revalidate();

            //     }
            // });

            undoButton.addActionListener(new UndoAction(bundle.getString("edit_1"), null, bundle.getString("edit_1_desc"),
            Integer.valueOf(KeyEvent.VK_U)));
            redoButton.addActionListener(new RedoAction(bundle.getString("edit_2"), null, bundle.getString("edit_2_desc"),
                Integer.valueOf(KeyEvent.VK_R)));


            // embossButton.addActionListener(new ActionListener() {
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            // // Determine the radius - ask the user.
            // int selection = 1;


            // String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8" };
            // String message = bundle.getString("filter_emboss");

            // // Create a panel to hold the components
            // JPanel panel = new JPanel();
            // panel.setLayout(new GridLayout(2, 1)); // Adjust the layout as needed
            // panel.add(new JLabel(message));
            // JComboBox<String> comboBox = new JComboBox<>(numbers);
            // panel.add(comboBox);

            // int optionType = JOptionPane.DEFAULT_OPTION;
            // int messageType = JOptionPane.INFORMATION_MESSAGE;

            // int choice = JOptionPane.showOptionDialog(null, panel, "Title", optionType, messageType, null, null, null);

            // // Check the return value from the dialog box.
            // if (choice == JOptionPane.OK_OPTION) {
            //     selection = Integer.parseInt((String) comboBox.getSelectedItem());
            // } else {
            //     return;
            // }
            // // Create and apply the filter
            // try {
            //     target.getImage().apply(new EmbossFilter(selection));
            //     target.repaint();
            //     target.getParent().revalidate();
            // } catch (NullPointerException NPEx) {
            //     Object[] options = { "OK" };
            //     JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
            //             bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            //             null, options, options[0]);
            // }

        
            //         target.repaint();
            //         target.getParent().revalidate();

            //     }
            // });

            FilterActions filterActions = new FilterActions();

            ImageAction embossAction = filterActions.new EmbossFilterAction(bundle.getString("filter_6"), null, null, null);

            

            //ImageAction.setTarget(target);
            

            embossButton.addActionListener(embossAction);





            JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            // shapePanel.add(new JLabel("Select Shape: "));
            editPanel.add(undoButton);
            editPanel.add(embossButton);
            editPanel.add(redoButton);

            toolbar.addSeparator();
            toolbar.add(editPanel);

            // Cancel Button --------------------------
            JButton cancelButton = new JButton(UIManager.getDefaults().getIcon("InternalFrame.closeIcon"));

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    close();
                }
            });

            toolbar.addSeparator();
            toolbar.add(cancelButton);
        }

    }

}
