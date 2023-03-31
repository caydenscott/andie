package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class LanguageActions {
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Language menu actions.
     * </p>
     */
    public LanguageActions() {
        actions = new ArrayList<Action>();
        actions.add(new EnglishAction("Undo", null, "Undo", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new MaoriAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_Y)));
        actions.add(new ItalinoAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_Y)));
    }

    /**
     * <p>
     * Create a menu contianing the list of language actions.
     * </p>
     * 
     * @return The language menu UI element.
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu("Language");

        for (Action action: actions) {
            languageMenu.add(new JMenuItem(action));
        }

        return languageMenu;
    }
}
