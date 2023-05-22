package cosc202.andie.ImageActions;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.*;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cosc202.andie.Andie;
import cosc202.andie.SelectActions.SelectLine;
import cosc202.andie.SelectActions.SelectOval;
import cosc202.andie.SelectActions.SelectRectangle;
import cosc202.andie.SelectActions.SelectTriangle;
import cosc202.andie.SelectActions.SelectShape;



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

public class DrawActions {
    /** A list of actions for the Shape menu. */
    protected ArrayList<Action> actions;
    protected Preferences prefs = Preferences.userNodeForPackage(Andie.class);

    /**
     * <p>

     * </p>
     */
    public DrawActions() {
        actions = new ArrayList<Action>();
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        // for multilingual support
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");

        //actions.add(new ResizeTransformationAction(bundle.getString("transform_1"), null,
        //        bundle.getString("transform_1_desc"), Integer.valueOf(KeyEvent.VK_R)));

        actions.add(new DrawShapeAction(bundle.getString("draw_1"), null,
            bundle.getString("draw_1_desc"), Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new DrawFreeAction(bundle.getString("draw_2"), null,
            bundle.getString("draw_2_desc"), Integer.valueOf(KeyEvent.VK_R)));
        
    }

    /**
     * <p>
     * Create a menu contianing the list of Shape actions.
     * </p>
     * 
     * @return The shape menu UI element.
     */
    public JMenu createMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
        JMenu shapeMenu = new JMenu(bundle.getString("draw_tt"));

        for (Action action : actions) {
            shapeMenu.add(new JMenuItem(action));
        }

        return shapeMenu;
    }

    // --- action classes ---

    public class DrawShapeAction extends ImageAction {

        private SelectShape shapeSelection; // current shape select type
        private JToolBar toolbar;

        /** variables for when adding the shape */
        private Color shapeColour;
        private Boolean filled;
        private int outlineThickness;


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
        DrawShapeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // set default shape variables
            shapeColour = Color.BLACK;
            filled = false;
            outlineThickness = 2;
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
            //if (toolbar != null) {
            //   return;
            //}


            makeToolBar(bundle);
            
            // add the toolbar which adds all options to make shapes
            target.addToolbar(toolbar);

        }

        // cleans up when closing the add shape toolbar, stops shape selection and removes toolbar ui
        private void close() {
            // if (shapeSelection != null) {
            //     // remove the selector
            //     target.removeMouseListener(shapeSelection);
            // }

            target.removeToolbar();

            toolbar = null;
        }

        private void addSelectAction(SelectShape sa) {
            if (sa == null) {
                return;
            }
            // remove the previous one
            target.removeMouseListener(shapeSelection);
            target.addMouseListener(sa);

            target.removeMouseMotionListener(shapeSelection);
            target.addMouseMotionListener(sa);

            target.requestFocus();

            // set new select action
            shapeSelection = sa;
        }

        private void makeToolBar(ResourceBundle bundle) {

            toolbar = new JToolBar();
            toolbar.setLayout(new FlowLayout());

            // Color Selector Button ------------------
            JButton colourSelectButton = new JButton("🖊");
            
            colourSelectButton.setBackground(shapeColour);

            colourSelectButton.setBorder(BorderFactory.createLineBorder(shapeColour, 5));

            colourSelectButton.addActionListener(new ChangeColourAction());

            toolbar.add(colourSelectButton);

            // Shape Radio Buttons ---------------------
            JToggleButton squareButton = new JToggleButton("▢");
            JToggleButton triangleButton = new JToggleButton("△");
            JToggleButton circleButton = new JToggleButton("◯");

            // add action listeners to set select area listeners
            squareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (shapeSelection == null || shapeSelection.getClass() != SelectRectangle.class){
                        addSelectAction(new SelectRectangle(target, shapeColour, filled, outlineThickness));
                    }
                        
                }
            });
            triangleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (shapeSelection == null || shapeSelection.getClass() != SelectTriangle.class){
                        addSelectAction(new SelectTriangle(target, shapeColour, filled, outlineThickness));
                    }
                        
                }
            });
            circleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (shapeSelection == null || shapeSelection.getClass() != SelectOval.class){
                        addSelectAction(new SelectOval(target, shapeColour, filled, outlineThickness));
                    }
                        
                }
            });

            ButtonGroup shapeButtonGroup = new ButtonGroup();
            shapeButtonGroup.add(squareButton);
            shapeButtonGroup.add(triangleButton);
            shapeButtonGroup.add(circleButton);

            JPanel shapePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            //shapePanel.add(new JLabel("Select Shape: "));
            shapePanel.add(squareButton);
            shapePanel.add(triangleButton);
            shapePanel.add(circleButton);

            toolbar.addSeparator();
            toolbar.add(shapePanel);

            // Thickness Dropdown Selector ------------
            // Integer[] thicknessOptions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            // JComboBox<Integer> thicknessComboBox = new JComboBox<Integer>(thicknessOptions);
            // thicknessComboBox.addActionListener(new ChangeLineThicknessAction());
            SpinnerNumberModel lineThicknessModel = new SpinnerNumberModel(outlineThickness, 1, 20, 1);
            JSpinner thicknessSpinner = new JSpinner(lineThicknessModel);
            lineThicknessModel.addChangeListener(new ChangeLineThicknessAction());

            JPanel thicknessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            thicknessPanel.add(new JLabel("│┃"));
            thicknessPanel.add(thicknessSpinner);

            toolbar.addSeparator();
            toolbar.add(thicknessPanel);

            // Fill shape -----------------------------
            JButton fillToggleButton = new JButton(filled ? "■" : "□"); // checking what the default is
            
            fillToggleButton.setFont(new Font("Arial", Font.PLAIN, 20));
            fillToggleButton.addActionListener(new ChangeIsFilledAction());


            JPanel fillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fillPanel.add(fillToggleButton);

            toolbar.addSeparator();
            toolbar.add(fillPanel);

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

        private class ChangeColourAction extends AbstractAction {
            ChangeColourAction() {
                super();
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
                JButton colourSelectButton= (JButton) e.getSource();
                shapeColour = JColorChooser.showDialog(
                    colourSelectButton,
                    bundle.getString("draw_colourpicker_title"),
                    shapeColour);
                colourSelectButton.setBackground(shapeColour);
                colourSelectButton.setBorder(BorderFactory.createLineBorder(shapeColour, 5));
                
                colourSelectButton.repaint();
                colourSelectButton.getParent().revalidate();

                // update shape to be drawn
                if (shapeSelection != null) {
                    // update the shape we're adding
                    shapeSelection.setColour(shapeColour);
                }

            }
        }
    
    
        private class ChangeLineThicknessAction implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel)e.getSource();
                
                outlineThickness = source.getNumber().intValue();

                if (shapeSelection != null) {
                    // update the shape we're adding
                    shapeSelection.setOutlineThickness(outlineThickness);
                }
            }
        }
    
        private class ChangeIsFilledAction extends AbstractAction {
            ChangeIsFilledAction() {
                super();
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton filledButton= (JButton) e.getSource();
    
                // if currently filled, then make it not filled
                if (filled) {
                    filledButton.setText("□");
                    filled = false;
                }
                else {
                    filledButton.setText("■");
                    filled = true;
                }

                if (shapeSelection != null) {
                    // update the shape we're adding
                    shapeSelection.setFilled(filled);
                }
            }
        }

    }

    public class DrawFreeAction extends ImageAction {

        private SelectLine lineSelection; // current shape select type
        private JToolBar toolbar;

        /** variables for when adding the shape */
        private Color lineColour;
        private int outlineThickness;


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
        DrawFreeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

            // set default shape variables
            lineColour = Color.BLACK;
            outlineThickness = 2;
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

            makeToolBar(bundle);
            
            // add the toolbar which adds all options to draw
            target.addToolbar(toolbar);

            lineSelection = new SelectLine(target, lineColour, outlineThickness);


            addSelectAction(lineSelection);

        }

        // cleans up when closing the draw line toolbar, stops line selection and removes toolbar ui
        private void close() {

            target.removeToolbar();

            toolbar = null;
        }

        private void addSelectAction(SelectLine sa) {
            if (sa == null) {
                return;
            }
            // remove the previous listeners add new ones
            target.removeMouseListener(lineSelection);
            target.addMouseListener(sa);

            target.removeMouseMotionListener(lineSelection);
            target.addMouseMotionListener(sa);

            // set new select action
            lineSelection = sa;
        }

        private void makeToolBar(ResourceBundle bundle) {

            toolbar = new JToolBar();
            toolbar.setLayout(new FlowLayout());

            // Color Selector Button ------------------
            JButton colourSelectButton = new JButton("🖊");
            
            colourSelectButton.setBackground(lineColour);

            colourSelectButton.setBorder(BorderFactory.createLineBorder(lineColour, 5));

            colourSelectButton.addActionListener(new ChangeColourAction());

            toolbar.add(colourSelectButton);

            // Thickness Dropdown Selector ------------
            SpinnerNumberModel lineThicknessModel = new SpinnerNumberModel(outlineThickness, 1, 20, 1);
            JSpinner thicknessSpinner = new JSpinner(lineThicknessModel);
            lineThicknessModel.addChangeListener(new ChangeLineThicknessAction());

            JPanel thicknessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            thicknessPanel.add(new JLabel("│┃"));
            thicknessPanel.add(thicknessSpinner);

            toolbar.addSeparator();
            toolbar.add(thicknessPanel);


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

        private class ChangeColourAction extends AbstractAction {
            ChangeColourAction() {
                super();
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                ResourceBundle bundle = ResourceBundle.getBundle("languages/MessageBundle");
                JButton colourSelectButton= (JButton) e.getSource();
                lineColour = JColorChooser.showDialog(
                    colourSelectButton,
                    bundle.getString("draw_colourpicker_title"),
                    lineColour);
                colourSelectButton.setBackground(lineColour);
                colourSelectButton.setBorder(BorderFactory.createLineBorder(lineColour, 5));
                
                colourSelectButton.repaint();
                colourSelectButton.getParent().revalidate();

                // update shape to be drawn
                if (lineSelection != null) {
                    // update the shape we're adding
                    lineSelection.setColour(lineColour);
                }

            }
        }
    
    
        private class ChangeLineThicknessAction implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                SpinnerNumberModel source = (SpinnerNumberModel)e.getSource();
                
                outlineThickness = source.getNumber().intValue();

                if (lineSelection != null) {
                    // update the shape we're adding
                    lineSelection.setOutlineThickness(outlineThickness);
                }
            }
        }

    }


}
