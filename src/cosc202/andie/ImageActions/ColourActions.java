package cosc202.andie.ImageActions;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cosc202.andie.Andie;
import cosc202.andie.ImageOperations.Filters.BrightnessAndContrast;
import cosc202.andie.ImageOperations.Filters.ConvertToGrey;
import cosc202.andie.ImageOperations.Filters.InvertImage;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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

        // actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to
        // greyscale", Integer.valueOf(KeyEvent.VK_G)));
        // actions.add(new InvertImageAction("Invert", null, "Invert colours",
        // Integer.valueOf(KeyEvent.VK_G)));
        // actions.add(new BrightnessAndContrastAction("Brightness and Contrast",null,
        // "Apply Brightness and Contrast", Integer.valueOf(KeyEvent.VK_B)));

        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        actions.add(new ConvertToGreyAction(bundle.getString("colour_1"), null, bundle.getString("colour_1_desc"),
                Integer.valueOf(KeyEvent.VK_C), KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)));
        actions.add(new InvertImageAction(bundle.getString("colour_2"), null, bundle.getString("colour_2_desc"),
                Integer.valueOf(KeyEvent.VK_I), KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK)));
        actions.add(new BrightnessAndContrastAction(bundle.getString("colour_3"), null,
                bundle.getString("colour_3_desc"), Integer.valueOf(KeyEvent.VK_B), KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK)));

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

        for (Action action : actions) {
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param accelerator An accelerator key to use as a short cut, added into the contructor (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic, KeyStroke accelerator) {
            super(name, icon, desc, mnemonic, accelerator);
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

            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }

            JPanel panel = new JPanel();
            
            int[] dimensions = target.getImage().previewSizeCalculator(); // Get new dimensions which maintain image aspect ratio
            int desiredWidth = dimensions [0]; 
            int desiredHeight = dimensions [1];

            Image scaledImage = target.getImage().getCurrentImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();
            // Apply the filter to the resized copy
            ConvertToGrey cTG = new ConvertToGrey();
            BufferedImage filteredImage = cTG.apply(resizedImage);

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel);
            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    public class InvertImageAction extends ImageAction {

        InvertImageAction(String name, ImageIcon icon, String desc, Integer mneumonic, KeyStroke accelerator) {
            super(name, icon, desc, mneumonic, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");

            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }

            JPanel panel = new JPanel();
            
            int[] dimensions = target.getImage().previewSizeCalculator(); // Get new dimensions which maintain image aspect ratio
            int desiredWidth = dimensions [0]; 
            int desiredHeight = dimensions [1];

            Image scaledImage = target.getImage().getCurrentImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();
            // Apply the filter to the resized copy
            InvertImage iI = new InvertImage();
            BufferedImage filteredImage = iI.apply(resizedImage);

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel);
            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                target.getImage().apply(new InvertImage());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    public class BrightnessAndContrastAction extends ImageAction {

        BrightnessAndContrastAction(String name, ImageIcon Icon, String desc, Integer mneumonic, KeyStroke accelerator) {
            super(name, Icon, desc, mneumonic, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        
            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                return;
            }
        
            int brightness = 0;
            int contrast = 0;
        
            // Create sliders and models
            BoundedRangeModel brightnessModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
            BoundedRangeModel contrastModel = new DefaultBoundedRangeModel(0, 0, -100, 100);
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
        
            // Create the message panel with sliders
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new GridLayout(2, 2));
            messagePanel.add(new JLabel(bundle.getString("colour_4")));
            messagePanel.add(brightnessSlider);
            messagePanel.add(new JLabel(bundle.getString("colour_5")));
            messagePanel.add(contrastSlider);
        
            
        
            // Create the image panel with the image label
            
            int[] dimensions = target.getImage().previewSizeCalculator(); // Get new dimensions which maintain image aspect ratio
            int desiredWidth = dimensions [0]; 
            int desiredHeight = dimensions [1];
            
            Image scaledImage = target.getImage().getCurrentImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();
        
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            JPanel imagePanel = new JPanel();
            imagePanel.add(imageLabel);
        
            // Create the main panel and add the image panel and message panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(imagePanel, BorderLayout.NORTH);
            mainPanel.add(messagePanel, BorderLayout.CENTER);

            ChangeListener sliderChangeListener = new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int brightness = brightnessSlider.getValue();
                        int contrast = contrastSlider.getValue();

                    BufferedImage resizedImageCopy = new BufferedImage(resizedImage.getWidth(),
                            resizedImage.getHeight(), resizedImage.getType());
                    Graphics2D copyGraphics = resizedImageCopy.createGraphics();
                    copyGraphics.drawImage(resizedImage, 0, 0, null);
                    copyGraphics.dispose();

                    // Reset the image to the original resized image
                    BufferedImage originalImage = new BufferedImage(resizedImage.getWidth(),
                            resizedImage.getHeight(), resizedImage.getType());
                    Graphics2D originalGraphics = originalImage.createGraphics();
                    originalGraphics.drawImage(resizedImage, 0, 0, null);
                    originalGraphics.dispose();

                    // Apply the filter to the resized image copy
                    BrightnessAndContrast bAC = new BrightnessAndContrast(brightness, contrast);
                    BufferedImage filteredImage = bAC.apply(resizedImage);

                    // Update the image label with the filtered image
                    ImageIcon newIcon = new ImageIcon(filteredImage);
                    imageLabel.setIcon(newIcon);
                    }
                }
            };
        
            brightnessSlider.addChangeListener(sliderChangeListener);
            contrastSlider.addChangeListener(sliderChangeListener);
        
            int option = JOptionPane.showOptionDialog(null, mainPanel, bundle.getString("colour_b_c"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        
            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                contrast = contrastModel.getValue();
                brightness = brightnessModel.getValue();
            } else {
                return;
            }
        
            target.getImage().apply(new BrightnessAndContrast(brightness, contrast));
            target.repaint();
            target.getParent().revalidate();
        }
        

    }

}
