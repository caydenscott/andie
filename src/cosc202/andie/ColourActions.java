package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoundedRangeModel;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();

        //actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        //actions.add(new InvertImageAction("Invert", null, "Invert colours", Integer.valueOf(KeyEvent.VK_G)));
        //actions.add(new BrightnessAndContrastAction("Brightness and Contrast",null, "Apply Brightness and Contrast", Integer.valueOf(KeyEvent.VK_B)));

        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        actions.add(new ConvertToGreyAction(bundle.getString("colour_1"), null, bundle.getString("colour_1_desc"), Integer.valueOf(KeyEvent.VK_C)));
        actions.add(new InvertImageAction(bundle.getString("colour_2"), null, bundle.getString("colour_2_desc"), Integer.valueOf(KeyEvent.VK_I)));
        actions.add(new BrightnessAndContrastAction(bundle.getString("colour_3"), null, bundle.getString("colour_2_desc"), Integer.valueOf(KeyEvent.VK_B)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle"); 
        JMenu colourMenu = new JMenu(bundle.getString("colour_tt"));

        for(Action action: actions) {
            colourMenu.add(new JMenuItem(action));
        }

        return colourMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            try{
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            } catch(NullPointerException NPEx){
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"), bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

            }
        }

    }

    public class InvertImageAction extends ImageAction {

        InvertImageAction(String name, ImageIcon icon, String desc, Integer mneumonic){
            super(name, icon, desc, mneumonic);
        }
        
        public void actionPerformed(ActionEvent e){
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            try{
                target.getImage().apply(new InvertImage());
                target.repaint();
                target.getParent().revalidate(); 
            }catch(NullPointerException NPEx){
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"), bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

            }
        }
    }

    public class BrightnessAndContrastAction extends ImageAction{

        BrightnessAndContrastAction(String name, ImageIcon Icon, String desc, Integer mneumonic){
            super(name, Icon, desc, mneumonic);
        }

        public void actionPerformed(ActionEvent e){
            int brightness = 0;
            int contrast = 0;

            //i need to still add pop up box to ask for two inputs in UI, using fixed values here for testing

            BoundedRangeModel brightnessModel =  new DefaultBoundedRangeModel(0, 0, -100, 100);
            BoundedRangeModel contrastModel =  new DefaultBoundedRangeModel(0, 0, -100, 100);
            JSlider brightnessSlider = new JSlider();
            brightnessSlider.setModel(brightnessModel);
            JSlider contrastSlider = new JSlider();
            contrastSlider.setModel(contrastModel);

            brightnessSlider.setMajorTickSpacing(50);
            brightnessSlider.setMinorTickSpacing(50);
            brightnessSlider.setPaintTicks(true);
            brightnessSlider.setPaintLabels(true);

            contrastSlider.setMajorTickSpacing(50);
            contrastSlider.setMinorTickSpacing(50);
            contrastSlider.setPaintTicks(true);
            contrastSlider.setPaintLabels(true);


            Object[] message = {
                ("brightness"), brightnessSlider,
                ("contrast"), contrastSlider
            };

            int option = JOptionPane.showOptionDialog(null, message, "Enter Brightness and Contrast Radius", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightness = brightnessModel.getValue();
                contrast = contrastModel.getValue();
            }

            target.getImage().apply(new BrightnessAndContrast(brightness, contrast));
            target.repaint();
            target.getParent().revalidate();
            
        }

    }

}
