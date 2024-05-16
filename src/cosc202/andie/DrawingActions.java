package cosc202.andie;

/**
 * 
 * 
 * <p>
 * ImageOperation to apply drawing functions.
 * </p>
 * 
 * <p>
 * The DrawingOperation menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes 3 drawing shapes actions.
 * </p>
 * 
 * 
 * </p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 */
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class DrawingActions {
    protected ArrayList<Action> actions;
    /** The file where the operation sequence is stored. */

    public ResourceBundle bundle = Andie.bundle;

    /**
     * <p>
     * Create a set of Drawing menu actions.
     * </p>
     */
    public DrawingActions() {
        actions = new ArrayList<Action>();

        actions.add(new DrawOvalAction("Oval", null, "Draw an oval on the image", null));
        actions.add(new DrawRectAction("Rectangle", null, "Draw rectangle on the image",
                null));
        actions.add(new DrawLineAction("Line", null, "Draw line on the image",
                null));
    }

    /**
     * <p>
     * Create a menu containing the list of drawing actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu drawMenu = new JMenu("Drawing");
        for (Action action : actions) {
            drawMenu.add(new JMenuItem(action));
        }

        return drawMenu;
    }

    /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawOvalAction extends ImageAction {

        DrawOvalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    DrawingOperations.isDrawingOval=true;

                    // target.getImage().apply(new DrawingOperations('o'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawRectAction extends ImageAction {

        DrawRectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {

                   DrawingOperations.isDrawingRect=true;
                    //target.getImage().apply(new DrawingOperations('r'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawLineAction extends ImageAction {

        DrawLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    DrawingOperations.isDrawingLine=true;

                    // target.getImage().apply(new DrawingOperations('l'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }
}
