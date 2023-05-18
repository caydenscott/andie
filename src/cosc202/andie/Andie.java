package cosc202.andie;

import java.awt.*;
import javax.swing.*;

import cosc202.andie.ImageActions.ColourActions;
import cosc202.andie.ImageActions.EditActions;
import cosc202.andie.ImageActions.FileActions;
import cosc202.andie.ImageActions.FilterActions;
import cosc202.andie.ImageActions.ImageAction;
import cosc202.andie.ImageActions.LanguageActions;
import cosc202.andie.ImageActions.MacroActions;
import cosc202.andie.ImageActions.ShapeActions;
import cosc202.andie.ImageActions.TransformActions;
import cosc202.andie.ImageActions.ViewActions;
import cosc202.andie.ImageOperations.ImageOperation;

import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggerd via 
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * timothy
     * team_Name
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see LanguageActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Makes sure file won't close automatically, inscase there are changes.
        frame.addWindowListener(new CustomWindowAdapter()); // Windows listener that sees if close button is pressed, and if so it checks if user wants to save

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // Actions to create or load a macro.
        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that translate the image
        TransformActions translateActions = new TransformActions();
        menuBar.add(translateActions.createMenu());

        // Actions to change the language 
        LanguageActions languageActions = new LanguageActions();
        menuBar.add(languageActions.createMenu());

        // shapes
        ShapeActions shapeActions = new ShapeActions();
        menuBar.add(shapeActions.createMenu());
        
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
