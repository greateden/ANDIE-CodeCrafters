package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.FilterActions.GaussianFilterAction;

/**
 * <p>
 * Actions provided by the Image menu.
 * </p>
 * 
 * <p>
 * The Image menu contains actions that alter the image in some fashion.
 * This includes image rotation and flipping.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author CodeCrafters
 * @version 1.0
 */
public class ImageMenuBar {
    
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public ImageMenuBar() {
        actions = new ArrayList<Action>();
        actions.add(new ImageMenuBarFlipHorizontal("Flip Horizontal", null, "Flips the image along the y-axis", Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new ImageMenuBarFlipVertical("Flip Vertical", null, "Flips the image along the x-axis", Integer.valueOf(KeyEvent.VK_V)));
        actions.add(new RotateImageAction("Rotate Image", null, "Apply an Image rotation at set theta", Integer.valueOf(KeyEvent.VK_M)));
    }

    /**
     * <p>
     * Create a menu containing the list of Image actions.
     * </p>
     * 
     * @return The Image menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Image");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class ImageMenuBarFlipHorizontal extends ImageAction {

        /**
         * <p>
         * Create a new Flip Horizontal action
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageMenuBarFlipHorizontal(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
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
            target.getImage().apply(new ImageFlipHorizontal());
            target.repaint();
            target.getParent().revalidate();
        }
    }
    


    public class ImageMenuBarFlipVertical extends ImageAction {

        /**
         * <p>
         * Create a new Flip Vertical action
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageMenuBarFlipVertical(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

         /**
         * <p>
         * Callback for when Flip vertical is pressed..
         * </p>
         * 
         * <p>
         * This method is called whenever the Flip Vertical method is called.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ImageFlipVertical());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     *  Action class code layout created by Steven Mills
     */
    public class RotateImageAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link GaussianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int deg = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel degModel = new SpinnerNumberModel(1, 1, 360, 1);
            JSpinner degSpinner = new JSpinner(degModel);
            int option = JOptionPane.showOptionDialog(null, degSpinner, "Enter rotation theta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                deg = degModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new ImageRotate(deg));
            target.repaint();
            target.getParent().revalidate();
        }

    }
}
    


