package cosc202.andie;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import cosc202.andie.ImageActions.ImageAction;

public class CustomWindowAdapter extends WindowAdapter {
    
    /**
     * Handles the window closing event.
     * If there are unsaved changes, a prompt is displayed to save before exiting.
     * If there are no changes, the program exits immediately.
     * 
     * @param e The window event.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (!EditableImage.getChanges()) {
            System.exit(0);
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            if (!EditableImage.getRecord()) {
                Object[] options = { bundle.getString("exit_no_save_option_1"),
                                     bundle.getString("exit_no_save_option_2"),
                                     bundle.getString("no_save_option_2") };
                int choice = JOptionPane.showOptionDialog(null,
                        bundle.getString("exit_no_save_1"), bundle.getString("no_save_warning"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (choice == 0) { // Save and Exit
                    try {
                        ImageAction.getTarget().getImage().save();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                } else if (choice == 1) { // Exit
                    System.exit(0);
                }
            } else {
                int userOption = JOptionPane.showConfirmDialog(null, bundle.getString("macro_warning_2"), 
                        bundle.getString("macro_tt"), JOptionPane.OK_OPTION);
                if (userOption != JOptionPane.OK_OPTION) return;
                    
                Object[] options = { bundle.getString("exit_no_save_option_1"),
                                     bundle.getString("exit_no_save_option_2"),
                                     bundle.getString("no_save_option_2") };
                int choice = JOptionPane.showOptionDialog(null,
                        bundle.getString("exit_no_save_1"), bundle.getString("no_save_warning"),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (choice == 0) { // Save and Exit
                    try {
                        ImageAction.getTarget().getImage().save();
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
