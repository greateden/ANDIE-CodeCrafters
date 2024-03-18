package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly
 * without reference to the rest of the image.
 * Eden has added the RGBSwapping function, now we have two functions 
 * include greyscale as well, please MERGE WITH CARE.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 * @modified_by The Greatest Eden
 * @modified_date 10 Mar 2024
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    ResourceBundle bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(bundle.getString("convertToGreyAction"), null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ImageInvertAction("Invert Colour", null, "Invert colours of image", Integer.valueOf(KeyEvent.VK_G)));
        // I am using key C as the hotkey
        // addeed RGBSwapping function's button
        actions.add(new RGBSwappingAction("Colour Channel Cycling", null, "Swap the colour channels around",
                Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
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
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    //Same as above, but edited to work for imageInvert().
    public class ImageInvertAction extends ImageAction {

        /**
         * <p>
         * Create a new imageInvert action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ImageInvertAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the invert image command is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Image Invert is triggered.
         * It changes the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ImageInvert());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to change an image's colour channel's order based on the user's taste.
     * </p>
     * 
     * @see ConvertToGrey
     * @author The Greatest Eden
     */
    public class RGBSwappingAction extends ImageAction {

        /**
         * <p>
         * Create a new RGBSwapping action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RGBSwappingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the RGBSwapping action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RGBSwapping is triggered.
         * It changes the image's colour channel's order based on the user's taste.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Default value of the orders of R, G and B.
            int R = 0;
            int G = 0;
            int B = 0;

            // Create the dialog panel
            JPanel panel = new JPanel(new GridLayout(3, 3));

            JRadioButton r1 = new JRadioButton("R");
            JRadioButton r2 = new JRadioButton("G");
            JRadioButton r3 = new JRadioButton("B");

            ButtonGroup group1 = new ButtonGroup();
            group1.add(r1);
            group1.add(r2);
            group1.add(r3);

            JRadioButton g1 = new JRadioButton("R");
            JRadioButton g2 = new JRadioButton("G");
            JRadioButton g3 = new JRadioButton("B");

            ButtonGroup group2 = new ButtonGroup();
            group2.add(g1);
            group2.add(g2);
            group2.add(g3);

            JRadioButton b1 = new JRadioButton("R");
            JRadioButton b2 = new JRadioButton("G");
            JRadioButton b3 = new JRadioButton("B");

            ButtonGroup group3 = new ButtonGroup();
            group3.add(b1);
            group3.add(b2);
            group3.add(b3);

            panel.add(r1);
            panel.add(g1);
            panel.add(b1);
            panel.add(r2);
            panel.add(g2);
            panel.add(b2);
            panel.add(r3);
            panel.add(g3);
            panel.add(b3);

            // Show the option dialog
            int option = JOptionPane.showOptionDialog(null, panel, "Color Selection", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            // If cancel or close the tab, do nothing
            // If click ok, run the function but with input testify.
            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                // Do nothing, but we keep this statement for possible future function expands.
            } else if (option == JOptionPane.OK_OPTION) {
                // Get selected values of R, G, and B
                if (r1.isSelected()) {
                    R = 1;
                } else if (r2.isSelected()) {
                    R = 2;
                } else if (r3.isSelected()) {
                    R = 3;
                }

                if (g1.isSelected()) {
                    G = 1;
                } else if (g2.isSelected()) {
                    G = 2;
                } else if (g3.isSelected()) {
                    G = 3;
                }

                if (b1.isSelected()) {
                    B = 1;
                } else if (b2.isSelected()) {
                    B = 2;
                } else if (b3.isSelected()) {
                    B = 3;
                }

                //Fun fact: put the code below outside of the if statement to trick the users!!!
                //Especially useful on 1st Apr

                //If user gave half an input, will say we don't understand what you're tryna do
                if (!(R == 0 && G == 0 && B == 0) && (R == B || B == G || R == B || R == 0 || G == 0 || B == 0)) {
                    JOptionPane.showMessageDialog(null, "Cannot proceed your weird request, please choose again.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    actionPerformed(e);
                //If user gave full input but with the same RGB order, will give them a friendly notice.
                } else if (!(R == 0 && G == 0 && B == 0) && (R == 1 && G == 2 && B == 3)){
                    JOptionPane.showMessageDialog(null, "With all due respect, you didn't change anything.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                //Will educate the user if they didn't give any inputs and still wanna hit the OK button/
                } else if (R == 0 && G == 0 && B == 0) {
                    JOptionPane.showMessageDialog(null, "Please at least choose something, that's the basic respect to your human brain.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    actionPerformed(e);
                //If a legal input (i.e., passed all the ifs above), will apply the filter.
                } else {
                    // Apply the filter
                    target.getImage().apply(new RGBSwapping(R, G, B));
                    target.repaint();
                    target.getParent().revalidate();
                }
            }            
        }
    }
}
