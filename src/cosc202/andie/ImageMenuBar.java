package cosc202.andie;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.Rectangle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
 * Should be called as ImageActions, however there is one already...
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author CodeCrafters
 * @version 1.0
 */
public class ImageMenuBar {

    public ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    private JMenu fileMenu;
    private ImageScalingAction scalAct;
    private RotateImageStrictAction rotAct;
    protected JDialog dialog;

    private JMenuItem scale25, scale50, scale75, scale125, scale150;
    private JMenuItem rotMenu90, rotMenu180, rotMenu270;

    public BufferedImage previewImage;
    public ImageIcon previewIcon;
    public JPanel previewPanel;
    private Action crop;

    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public ImageMenuBar() {
        actions = new ArrayList<Action>();

        Action horizontal = new ImageMenuBarFlipHorizontal(Andie.bundle.getString("FlipHorizontal"), null,
                Andie.bundle.getString("FHDesc"), Integer.valueOf(KeyEvent.VK_H));
        actions.add(horizontal);

        Action vertical = new ImageMenuBarFlipVertical(Andie.bundle.getString("FlipVertical"), null,
                Andie.bundle.getString("FVDesc"), Integer.valueOf(KeyEvent.VK_V));
        actions.add(vertical);

        // Action crop = new ImageCropAction(Andie.bundle.getString("ImageCrop", null,
        // Andie.bundle.getString("ImageCropAction"), Integer.valueOf(KeyEvent.VK_J));

        crop = new ImageCropAction("Image crop", null,
                "Crops the image duh", Integer.valueOf(KeyEvent.VK_J));
        actions.add(crop);

        Action rotate = new RotateImageAction(Andie.bundle.getString("RotateImageAction"), null,
                Andie.bundle.getString("RIADesc"), Integer.valueOf(KeyEvent.VK_R));
        actions.add(rotate);

        Action resize = new ImageResizeAction(Andie.bundle.getString("ImageResizeAction"), null,
                Andie.bundle.getString("ImageResizeAction"), Integer.valueOf(KeyEvent.VK_E));
        actions.add(resize);
        CreateHotKey.createHotkey(resize, KeyEvent.VK_I, InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK,
                "resize");

        Action scattering = new RandomScatteringAction(Andie.bundle.getString("RandomScattering"), null,
                Andie.bundle.getString("RandomScattering"), Integer.valueOf(KeyEvent.VK_A));
        actions.add(scattering);

        scalAct = new ImageScalingAction(Andie.bundle.getString("Scaling"), null, Andie.bundle.getString("ReScaling"),
                Integer.valueOf(KeyEvent.VK_S));
        actions.add(scalAct);

        rotAct = new RotateImageStrictAction("Rotate by", null, "Rotating by multiples of right angles",
                Integer.valueOf(KeyEvent.VK_O));
        actions.add(rotAct);



    }

    /**
     * <p>
     * Create a menu containing the list of Image actions.
     * </p>
     *
     * @return The Image menu UI element.
     */
    public JMenu createMenu() {
        fileMenu = new JMenu(Andie.bundle.getString("Image"));

        for (Action action : actions) {
            if (action != scalAct && action != rotAct) {
                fileMenu.add(new JMenuItem(action));
            }

        }

        // JMenuItem scaleMenu = fileMenu.getItem(4);
        scalAct.createSubMenu();
        rotAct.createSubMenu();

        return fileMenu;
    }

    /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
        if (FileActions.isOpened) {
            crop.setEnabled(!status);
        }
        ImageAction.target.setCrop(crop);
        scale25.setEnabled(status);
        scale50.setEnabled(status);
        scale75.setEnabled(status);
        scale125.setEnabled(status);
        scale150.setEnabled(status);

        rotMenu90.setEnabled(status);
        rotMenu180.setEnabled(status);
        rotMenu270.setEnabled(status);
    }

    /**
     * ImageMenuBarFlipHorizontal extends ImageAction and represents an action for
     * flipping the image horizontally.
     * This action is triggered by user interaction in the graphical user interface.
     */
    public class ImageMenuBarFlipHorizontal extends ImageAction {

        /**
         * <p>
         * Create a new Flip Horizontal action
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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
            try {
                target.getImage().apply(new ImageFlipHorizontal());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * ImageMenuBarFlipVertical extends ImageAction and represents an action for
     * flipping the image vertically.
     * This action is triggered by user interaction in the graphical user interface.
     */
    public class ImageMenuBarFlipVertical extends ImageAction {

        /**
         * <p>
         * Create a new Flip Vertical action
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageMenuBarFlipVertical(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when Flip vertical is pressed.
         * </p>
         *
         * <p>
         * This method is called whenever the Flip Vertical method is called.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new ImageFlipVertical());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * ImageMenuBarFlipVertical extends ImageAction and represents an action for
     * flipping the image vertically.
     * This action is triggered by user interaction in the graphical user interface.
     */
    public class ImageCropAction extends ImageAction {

        /**
         * <p>
         * Create a new Flip Vertical action
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageCropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when crop button is pressed.
         * </p>
         *
         * <p>
         * This method is called whenever the Flip Vertical method is called.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Rectangle selection = target.getSelectionRect();
                int x = (int) (selection.getX() / (target.getZoom() / 100));
                int y = (int) (selection.getY() / (target.getZoom() / 100));
                int width = (int) (selection.getWidth() / (target.getZoom() / 100));
                int height = (int) (selection.getHeight() / (target.getZoom() / 100));

                target.getImage().apply(new ImageCrop(x, y, width, height));
                // ImageCrop imCrop = new ImageCrop(x, y, width, height);
                // imCrop.apply(target.getImage().getCurrentImage());
                target.repaint();
                target.getParent().revalidate();
                MouseSelection.imagePanel.setIsSelecting(false);
                MouseSelection.imagePanel.setSelectionRect(null);
                MouseSelection.imagePanel.repaint();
                crop.setEnabled(false);
                ImageAction.target.setCrop(false);
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * Action class code layout created by Steven Mills
     * /**
     * Action class code layout created by Steven Mills
     */
    public class RotateImageAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate Image action.
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
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Rotate Image Action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Rotate Image is triggered.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            try {
                int deg = 0;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel degModel = new SpinnerNumberModel(0, null, null, 0.1);
                JSpinner degSpinner = new JSpinner(degModel);
                int option = JOptionPane.showOptionDialog(Andie.getFrame(), degSpinner,
                        Andie.bundle.getString("EnterRotationTheta"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    deg = degModel.getNumber().intValue();
                }
                // rotateAttempt++;
                // Create and apply the filter
                target.getImage().apply(new ImageRotate(deg));
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    /**
     *
     * /**
     * <p>
     * Action to resize an image.
     *
     *
     * made by Yuxing Zhang
     *
     *
     */
    public class ImageResizeAction extends ImageAction {
        /** The height of the image. */
        int height;
        /** The width of the image. */
        int width;
        /** The label for the width, height and title . */
        JLabel widthJLabel, heightLabel, titleLabel, blankLabel;
        /** The text field for the height and width. */
        JTextField widthField, heightField;
        /** The button to apply changes. */
        JButton goButton;

        /**
         * <p>
         * Create a Image Resize Action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Image Resize action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Image Resize Action is triggered.
         * It resizes the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */

        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a panel
            try {
                createPanel();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }

            }
        }

        /**
         * Creates and displays a panel for resizing images.
         * This method constructs a dialog window containing input fields for width and
         * height, as well as a button to initiate the resizing process.
         */
        protected void createPanel() {

            // Write code to create the panel
            // JPanel panel=new JPanel();

            int height = 0;
            int width = 0;
            SpinnerNumberModel heightModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
            SpinnerNumberModel widthModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
            JPanel panel = new JPanel(new GridLayout(4, 1));
            JLabel l1 = new JLabel(Andie.bundle.getString("Height"));
            JSpinner s1 = new JSpinner(heightModel);
            JSpinner s2 = new JSpinner(widthModel);

            JLabel l2 = new JLabel(Andie.bundle.getString("Width"));
            panel.add(l1);
            panel.add(s1);
            panel.add(l2);
            panel.add(s2);
            int option = JOptionPane.showOptionDialog(Andie.getFrame(), panel,
                    Andie.bundle.getString("ReScalingInstruction"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                height = heightModel.getNumber().intValue();
                width = widthModel.getNumber().intValue();
                try {
                    target.getImage().apply(new ImageResize(height, width));
                    target.repaint();
                    target.getParent().revalidate();

                } catch (Exception e) {
                    System.out.println(e);
                    if (e instanceof NumberFormatException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(),
                                Andie.bundle.getString("PosInt"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof java.lang.NegativeArraySizeException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(),
                                Andie.bundle.getString("SmallNum"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof java.lang.IllegalArgumentException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(),
                                Andie.bundle.getString("PosOrSmallInt"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof NullPointerException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    } else {
                        // show message dialog and print the e into the box, saying that's an unexpected
                        // error.
                        JOptionPane.showMessageDialog(Andie.getFrame(),
                                Andie.bundle.getString("BooBoo"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        }

        /**
         * ButtonListener class implements the ActionListener interface and handles
         * button click events.
         * This class defines the actionPerformed method to respond to button clicks,
         * specifically for the "Go" button
         * in the image resizing panel.
         */

    }

    /**
     * ImageScalingAction extends ImageAction and represents an action for scaling
     * images.
     * This action is responsible for creating a submenu with options for scaling
     * images by different percentages.
     */
    public class ImageScalingAction extends ImageAction {
        // int height;
        // int width;
        /**
         * <p>
         * Create a Image Scale Action.
         *
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageScalingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        // We must have this class but it won't do anything.
        @Override
        public void actionPerformed(ActionEvent e) {

            // createSubMenu();

            throw new UnsupportedOperationException(Andie.bundle.getString("Unimplemented"));
        }

        /**
         * Creates a submenu with options for scaling images by different percentages.
         */
        public void createSubMenu() {

            JMenu scaleMenu = new JMenu(Andie.bundle.getString("Scaling"));
            scale25 = new JMenuItem("25%");
            CreateHotKey.createHotkey(scale25, KeyEvent.VK_1, 0, "scale25");

            scale50 = new JMenuItem("50%");
            CreateHotKey.createHotkey(scale50, KeyEvent.VK_2, 0, "scale50");

            scale75 = new JMenuItem("75%");
            CreateHotKey.createHotkey(scale75, KeyEvent.VK_3, 0, "scale75");

            scale125 = new JMenuItem("125%");
            CreateHotKey.createHotkey(scale125, KeyEvent.VK_4, 0, "scale125");

            scale150 = new JMenuItem("150%");
            CreateHotKey.createHotkey(scale150, KeyEvent.VK_5, 0, "scale150");

            scale25.addActionListener(new ScaleActionListener(0.25));
            scale50.addActionListener(new ScaleActionListener(0.5));
            scale75.addActionListener(new ScaleActionListener(0.75));
            scale125.addActionListener(new ScaleActionListener(1.25));
            scale150.addActionListener(new ScaleActionListener(1.5));

            scaleMenu.add(scale25);
            scaleMenu.add(scale50);
            scaleMenu.add(scale75);
            scaleMenu.add(scale125);
            scaleMenu.add(scale150);

            fileMenu.add(scaleMenu);

        }

        /**
         * ActionListener for scaling images by a specified percentage.
         */
        public class ScaleActionListener implements ActionListener {
            /** The percentage by which to scale the image. */
            private double scalePercentage;

            /**
             * Constructs a ScaleActionListener with the specified scaling percentage.
             *
             * @param scalePercentage The percentage by which to scale the image.
             */
            public ScaleActionListener(double scalePercentage) {
                this.scalePercentage = scalePercentage;
            }

            /**
             * Responds to action events triggered by scaling options.
             *
             * This method is called when a scaling option is selected from the submenu.
             * It scales the image by the specified percentage, repaints the target
             * component,
             * and revalidates its parent container.
             *
             * @param e The action event triggered by the scaling option.
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    target.getImage().apply(new ImageScaling(scalePercentage));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof NullPointerException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        }

    } // End of Image Scaling Action

    /**
     * Action class code layout created by Steven Mills
     * /**
     * Action class code layout created by Steven Mills
     */
    public class RotateImageStrictAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate Image Strict Action action.
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
        RotateImageStrictAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Rotate Image Strict Action is triggered.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        // We must have this class but it won't do anything.
        @Override
        public void actionPerformed(ActionEvent e) {

            // createSubMenu();

            throw new UnsupportedOperationException(Andie.bundle.getString("Unimplemented"));
        }

        /**
         * Creates a submenu with options for rotating images by specified angles.
         */
        public void createSubMenu() {

            JMenu rotMenu = new JMenu(Andie.bundle.getString("RotateBy"));

            rotMenu90 = new JMenuItem("90° right");
            CreateHotKey.createHotkey(rotMenu90, KeyEvent.VK_1, 0, "rotMenu90");

            rotMenu180 = new JMenuItem("180°");
            CreateHotKey.createHotkey(rotMenu180, KeyEvent.VK_2, 0, "rotMenu180");

            rotMenu270 = new JMenuItem("90° left");
            CreateHotKey.createHotkey(rotMenu270, KeyEvent.VK_3, 0, "rotMenu270");

            rotMenu90.addActionListener(new ScaleActionListener(90));
            rotMenu180.addActionListener(new ScaleActionListener(180));
            rotMenu270.addActionListener(new ScaleActionListener(270));

            rotMenu.add(rotMenu90);
            rotMenu.add(rotMenu180);
            rotMenu.add(rotMenu270);

            fileMenu.add(rotMenu);

        }

        /**
         * ActionListener for rotating images by specified angles.
         */
        public class ScaleActionListener implements ActionListener {
            private double scalePercentage;

            /**
             * Constructs a ScaleActionListener with the specified rotation angle.
             *
             *
             */
            public ScaleActionListener(double scalePercentage) {
                this.scalePercentage = scalePercentage;
            }

            /**
             * Responds to action events triggered by scaling options.
             *
             * This method is called when a rotation option is selected from the submenu.
             * It rotates the image by the specified angle, repaints the target component,
             * and revalidates its parent container.
             *
             * @param e The action event triggered by the rotation option.
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    target.getImage().apply(new ImageRotate(scalePercentage));
                    target.repaint();
                    target.getParent().revalidate();

                } catch (Exception err) {
                    if (err instanceof NullPointerException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        }
    }

    /**
     * RandomScatteringAction extends ImageAction and represents an action for
     * applying random scattering effect to an image.
     * This action prompts the user for a filter radius and applies a
     * RandomScattering filter accordingly.
     */
    public class RandomScatteringAction extends ImageAction {
        int radius = 0;

        /**
         * <p>
         * Create a new Random Scattering action.
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
        RandomScatteringAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Random Scattering action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the RandomScatteringAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link RandomScattering}.
         * </p>
         *
         * @param e The event triggering this callback.
         */

        JSlider radiusSlider;

        public void actionPerformed(ActionEvent e) {
            try {
                // Determine the radius - ask the user.
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());

                // final EditableImage preview = target.getImage().makeCopy();
                // final ImagePanel show = new ImagePanel(preview);

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(new Dimension(500, 300));
                updatePreviewImage(prev);

                // Pop-up dialog box to ask for the radius value.
                JPanel sliderPane = new JPanel(new FlowLayout());
                sliderPane.setPreferredSize(new Dimension(450, 50));
                JPanel labelPane = new JPanel(new GridLayout(1, 1, 167, 0));
                radiusSlider = new JSlider(0, 10, 0);
                radiusSlider.setPreferredSize(new Dimension(400, 50));

                JLabel tempLabel = new JLabel("Radius", JLabel.CENTER);
                radiusSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

                radiusSlider.setMajorTickSpacing(1);
                radiusSlider.setPaintTicks(true);
                radiusSlider.setPaintLabels(true);
                radiusSlider.setSnapToTicks(true);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        int rad = radiusSlider.getValue();
                        BufferedImage curr = RandomScattering2
                                .applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), rad);
                        updatePreviewImage(curr);
                        radius = rad;

                    }
                };

                radiusSlider.addChangeListener(sliderChangeListener);

                labelPane.add(tempLabel);

                sliderPane.add(radiusSlider);

                JPanel menu = new JPanel(new GridBagLayout());
                GridBagConstraints a = new GridBagConstraints();
                Insets i = new Insets(20, 0, 0, 0);

                // a.fill = GridBagConstraints.BOTH;
                a.gridx = 0;
                a.gridy = 0;
                a.gridwidth = 2;
                a.anchor = GridBagConstraints.PAGE_START;
                menu.add(previewPanel, a);

                a.fill = GridBagConstraints.VERTICAL;
                a.gridx = 0;
                a.gridy = 1;
                a.weighty = 1.0;
                a.insets = i;
                menu.add(sliderPane, a);

                a.gridx = 0;
                a.gridy = 2;
                a.weighty = 0.7;
                a.ipady = 1;
                i.set(10, 0, 0, 0);
                menu.add(labelPane, a);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), menu,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    target.getImage().apply(new RandomScattering2(radius));
                    target.getParent().revalidate();
                    target.repaint();
                }

                // Create and apply the filter

            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }// End of randomScattering


    /**
     * A method to update the preview pane common across all menu classes that use
     * the preview panel.
     *
     * @author Kevin Steve Sathyanath
     * @param i The Buffered image input on the Preview Panel
     */
    public void updatePreviewImage(BufferedImage i) {
        BufferedImage j = ImageResize.applyToPreview(i);
        JLabel pic = new JLabel(new ImageIcon(j));
        // previewIcon = new ImageIcon(j);
        previewPanel.removeAll();
        previewPanel.add(pic);
        previewPanel.repaint();
        previewPanel.revalidate();
    }

}
