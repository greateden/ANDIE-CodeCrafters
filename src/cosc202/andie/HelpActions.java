package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class HelpActions {
    
    /** A list of actions for the Help menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Help menu actions.
     * </p>
     */
    public HelpActions() {
        actions = new ArrayList<Action>();
        actions.add(new HelpActionsAboutUs("About us", null, "A little bit about the team", Integer.valueOf(KeyEvent.VK_G)));
    }

    /**
     * <p>
     * Create a menu containing the list of Help actions.
     * </p>
     * 
     * @return The Help menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Help");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class HelpActionsAboutUs extends AbstractAction {

        /**
         * <p>
         * Create a new about-us action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        HelpActionsAboutUs(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

         /**
         * <p>
         * Callback for when the about-us action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the about-us-action is triggered.
         * It prints a message in a dialog box.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "A project for COSC202 made by:\n\n   Angus Lyall\n   Emma Boult\n   Yuxing Zhang\n   Eden Li\n   Kevin Sathyanath\n", "About us", 1);
        }
    }

}


