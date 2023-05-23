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
import cosc202.andie.ImageOperations.Filters.GaussianFilter;
import cosc202.andie.ImageOperations.Filters.MeanFilter;
import cosc202.andie.ImageOperations.Filters.MedianFilter;
import cosc202.andie.ImageOperations.Filters.SharpenImage;
import cosc202.andie.ImageOperations.Filters.SoftBlur;
import cosc202.andie.ImageOperations.Filters.EmbossFilter;
import cosc202.andie.ImageOperations.Filters.SobelFilter;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */

    // Integer.valueOf(KeyEvent.VK_M), KeyEvent.getKeyStroke(KeyEvent.VK_M,
    // CTRL_DOWN_MASK)));

    public FilterActions() {
        actions = new ArrayList<Action>();
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        actions.add(new MeanFilterAction(bundle.getString("filter_1"), null, bundle.getString("filter_1_desc"),
                Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SoftBlurAction(bundle.getString("filter_2"), null, bundle.getString("filter_2_desc"),
                Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new SharpenImageAction(bundle.getString("filter_3"), null, bundle.getString("filter_3_desc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new GaussianFilterAction(bundle.getString("filter_4"), null, bundle.getString("filter_4_desc"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new MedianFilterAction(bundle.getString("filter_5"), null, bundle.getString("filter_5_desc"),
                Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new EmbossFilterAction(bundle.getString("filter_6"), null, bundle.getString("filter_6_desc"),
                Integer.valueOf(KeyEvent.VK_J)));
        actions.add(new SobelFilterAction(bundle.getString("filter_7"), null, bundle.getString("filter_7_desc"),
                Integer.valueOf(KeyEvent.VK_F)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        JMenu filterMenu = new JMenu(bundle.getString("filter_tt"));

        for (Action action : actions) {
            filterMenu.add(new JMenuItem(action));
        }

        return filterMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            // Determine the radius - ask the user.

            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, null);
            }

            int radius = 1;

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1)); // Adjust the layout as needed

            JSlider radiusSlider = new JSlider(1, 10, 1);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(1, new JLabel("1"));
            labelTable.put(5, new JLabel("5"));
            labelTable.put(10, new JLabel("10"));
            radiusSlider.setLabelTable(labelTable);

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

            panel.add(imageLabel);
            panel.add(radiusSlider);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int value = source.getValue();

                        // Apply the filter to the resized image copy
                        MeanFilter mF = new MeanFilter(value);
                        BufferedImage filteredImage = mF.apply(resizedImage);

                        // Update the image label with the filtered image
                        ImageIcon newIcon = new ImageIcon(filteredImage);
                        imageLabel.setIcon(newIcon);
                    }
                }
            });

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            } else {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class SoftBlurAction extends ImageAction {

        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
            
            // Apply the filter to the original resized image
            SoftBlur sF = new SoftBlur();
            BufferedImage filteredImage = sF.apply(resizedImage);
            

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel);
            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {

                // Create and apply the filter
                target.getImage().apply(new SoftBlur());
                target.repaint();
                target.getParent().revalidate();

            }
        }
    }

    public class SharpenImageAction extends ImageAction {

        SharpenImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
            SharpenImage sI = new SharpenImage();
            BufferedImage filteredImage = sI.apply(resizedImage);

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel);
            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {

                // Create and apply the filter
                target.getImage().apply(new SharpenImage());
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new gaussian filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            // Determine the radius - ask the user.
            int radius = 1;

            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1)); // Adjust the layout as needed

            JSlider radiusSlider = new JSlider(1, 10, 1);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(1, new JLabel("1"));
            labelTable.put(5, new JLabel("5"));
            labelTable.put(10, new JLabel("10"));
            radiusSlider.setLabelTable(labelTable);

            
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

            panel.add(imageLabel);
            panel.add(radiusSlider);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int value = source.getValue();
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
                        GaussianFilter gF = new GaussianFilter(value);
                        BufferedImage filteredImage = gF.apply(resizedImage);

                        // Update the image label with the filtered image
                        ImageIcon newIcon = new ImageIcon(filteredImage);
                        imageLabel.setIcon(newIcon);
                    }
                }
            });

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            } else {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new GaussianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to blur an image with a 4132w filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
            // Determine the radius - ask the user.
            int radius = 1;

            if (target.getImage().getCurrentImage() == null) {
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, bundle.getString("no_file_error"),
                        bundle.getString("filter_error_1"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1)); // Adjust the layout as needed

            JSlider radiusSlider = new JSlider(1, 10, 1);
            radiusSlider.setMajorTickSpacing(1);
            radiusSlider.setPaintTicks(true);
            radiusSlider.setPaintLabels(true);
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(1, new JLabel("1"));
            labelTable.put(5, new JLabel("5"));
            labelTable.put(10, new JLabel("10"));
            radiusSlider.setLabelTable(labelTable);
            
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

            panel.add(imageLabel);
            panel.add(radiusSlider);

            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int value = source.getValue();
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

                        MedianFilter mF = new MedianFilter(value);
                        BufferedImage filteredImage = mF.apply(resizedImage);

                        // Update the image label with the filtered image
                        ImageIcon newIcon = new ImageIcon(filteredImage);
                        imageLabel.setIcon(newIcon);
                    }
                }
            });

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_radius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                radius = radiusSlider.getValue();
            } else {
                return;
            }
            // Create and apply the filterÒ
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();

        }

    }

    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link EmbossFilter}.
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

            // Determine the radius - ask the user.
            int selection = 1;
            String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8" };
            String message = bundle.getString("filter_emboss");

            // Create a panel to hold the components
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout()); // Change the layout to BorderLayout

            JPanel innerJPanel = new JPanel();
            innerJPanel.setLayout(new GridLayout(2, 1, 0, 1)); // Adjust the layout as needed

            JComboBox<String> comboBox = new JComboBox<>(numbers);
            int[] dimensions = target.getImage().previewSizeCalculator(); // Get new dimensions which maintain image aspect ratio
            int desiredWidth = dimensions [0]; 
            int desiredHeight = dimensions [1];
            Image scaledImage = target.getImage().getCurrentImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();
            EmbossFilter eF = new EmbossFilter(1);
            BufferedImage filteredImage = eF.apply(resizedImage);

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel, BorderLayout.NORTH); // Add the imageLabel to the top of the panel
            innerJPanel.add(new JLabel(message));
            innerJPanel.add(comboBox);
            panel.add(innerJPanel, BorderLayout.CENTER); // Add the innerJPanel to the center of the panel

            comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    @SuppressWarnings("unchecked")
                    JComboBox<String> source = (JComboBox<String>) e.getSource();
                    int value = Integer.parseInt((String) source.getSelectedItem());

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
                    EmbossFilter eF = new EmbossFilter(value);
                    BufferedImage filteredImage = eF.apply(resizedImage);

                    // Update the image label with the filtered image
                    ImageIcon newIcon = new ImageIcon(filteredImage);
                    imageLabel.setIcon(newIcon);
                }
            });

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_emboss_title"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                selection = Integer.parseInt((String) comboBox.getSelectedItem());
            } else {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new EmbossFilter(selection));
            target.repaint();
            target.getParent().revalidate();

        }

    }

    public class SobelFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * It prompts the user to scelect a sobel time, then applys an appropriate
         * kernel
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

            // Determine the radius - ask the user.
            String selection = bundle.getString("filter_vertical");

            String[] numbers = { bundle.getString("filter_vertical"), bundle.getString("filter_horizontal") };
            String message = bundle.getString("filter_sobel");

            // Create a panel to hold the components
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout()); // Change the layout to BorderLayout

            JPanel innerJPanel = new JPanel();
            innerJPanel.setLayout(new GridLayout(2, 1, 0, 1)); // Adjust the layout as needed

            JComboBox<String> comboBox = new JComboBox<>(numbers);
            int[] dimensions = target.getImage().previewSizeCalculator(); // Get new dimensions which maintain image aspect ratio
            int desiredWidth = dimensions [0]; 
            int desiredHeight = dimensions [1];
            Image scaledImage = target.getImage().getCurrentImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(scaledImage, 0, 0, null);
            graphics.dispose();
            SobelFilter sF = new SobelFilter(bundle.getString("filter_vertical"));
            BufferedImage filteredImage = sF.apply(resizedImage);

            JLabel imageLabel = new JLabel(new ImageIcon(filteredImage));

            panel.add(imageLabel, BorderLayout.NORTH); // Add the imageLabel to the top of the panel
            innerJPanel.add(new JLabel(message));
            innerJPanel.add(comboBox);
            panel.add(innerJPanel, BorderLayout.CENTER); // Add the innerJPanel to the center of the panel

            comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    @SuppressWarnings("unchecked")
                    JComboBox<String> source = (JComboBox<String>) e.getSource();
                    String value = (String) source.getSelectedItem();

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
                    SobelFilter sI = new SobelFilter(value);
                    BufferedImage filteredImage = sI.apply(resizedImage);

                    // Update the image label with the filtered image
                    ImageIcon newIcon = new ImageIcon(filteredImage);
                    imageLabel.setIcon(newIcon);
                }
            });

            int option = JOptionPane.showOptionDialog(null, panel, bundle.getString("filter_emboss_title"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.OK_OPTION) {
                selection = (String) comboBox.getSelectedItem();
            } else {
                return;
            }
            // Create and apply the filter
            target.getImage().apply(new SobelFilter(selection));
            target.repaint();
            target.getParent().revalidate();

        }

    }


}
