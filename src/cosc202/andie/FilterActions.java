package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

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

   public ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction(Andie.bundle.getString("MeanFilterAction"), null, Andie.bundle.getString("MeanFilterDesc"),
                Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SoftBlurFilterAction(Andie.bundle.getString("SoftBlur"), null, Andie.bundle.getString("SoftBlurDesc"),
                Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new SharpenFilterAction(Andie.bundle.getString("SharpenFilter"), null, Andie.bundle.getString("SharpenDesc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new GaussianFilterAction(Andie.bundle.getString("GaussianFilter"), null, Andie.bundle.getString("GaussianDesc"),
                Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MedianFilterAction(Andie.bundle.getString("MedianFilter"), null, Andie.bundle.getString("MedianDesc"),
                Integer.valueOf(KeyEvent.VK_PAGE_DOWN)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("Filter"));

        for (Action action : actions) {

            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {

            try {
                // Determine the radius - ask the user.
                int radius = 0;

                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                JLabel info = new JLabel();
                info.setText("Please enter a positive integer.");
                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                panel.add(info);
                panel.add(radiusSpinner);

                int option = JOptionPane.showOptionDialog(null, panel, Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);     

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }
                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

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
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Determine the radius - ask the user.
                int radius = 0;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }
                // Pop-up dialog box to ask for the radius value.

                // Create and apply the filter
                target.getImage().apply(new MedianFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }

    /**
     * Action class code layout created by Steven Mills
     */
    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian-filter action.
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

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {

                // Determine the radius - ask the user.
                int radius = 0;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                //System.out.println("the language is" + Andie.bundle.getString("EnterFilterRadius"));

                int option = JOptionPane.showOptionDialog(null, radiusSpinner, Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }
                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new GaussianFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }

    }

    /**
     * <p>
     * Action to apply a blur to an image with Soft blur filter.
     * </p>
     * 
     * @see SoftBlurFilter
     */
    public class SoftBlurFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Soft Blur filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SoftBlurFilterAction is triggered.
         * It applys the soft blur filter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Create and apply soft blur filter
                target.getImage().apply(new SoftBlurFilter());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * <p>
     * Action to sharpen an image with a Sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It applys the Sharpen filter.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // make and apply filter
                target.getImage().apply(new SharpenFilter());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }
}
