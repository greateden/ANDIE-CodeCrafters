package cosc202.andie;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;

//import cosc202.andie.ResizePannel;

import java.awt.*;

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

        int height;
        int width;
        JLabel widthJLabel, heightLabel, titleLabel, blankLabel;

        JTextField widthField, heightField;
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

            dialog = new JDialog(Andie.getFrame(), Andie.bundle.getString("Resize"), true);
            dialog.setPreferredSize(new Dimension(500, 400));
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JPanel p = new JPanel();
            p.setLayout(new GridLayout(4, 2));

            p.setPreferredSize(new Dimension(250, 350));
            titleLabel = new JLabel(Andie.bundle.getString("ReScalingInstruction"));
            titleLabel.setPreferredSize(new Dimension(200, 50));
            blankLabel = new JLabel("                       ");

            heightLabel = new JLabel(Andie.bundle.getString("Height"));
            heightLabel.setPreferredSize(new Dimension(100, 50));
            widthJLabel = new JLabel(Andie.bundle.getString("Width"));
            widthJLabel.setPreferredSize(new Dimension(100, 50));

            widthField = new JTextField(5);
            heightField = new JTextField(5);
            heightField.setPreferredSize(new Dimension(100, 50));
            widthField.setPreferredSize(new Dimension(100, 50));

            goButton = new JButton(Andie.bundle.getString("Go"));
            goButton.setOpaque(true);
            goButton.setBackground(Color.black);
            goButton.setPreferredSize(new Dimension(200, 50));

            // panel.add(frame);

            // add all the lables and buttons to the panel
            p.add(titleLabel);
            p.add(blankLabel);
            p.add(heightLabel);
            p.add(heightField);
            p.add(widthJLabel);
            p.add(widthField);
            p.add(goButton);

            JPanel buttonPanel = new JPanel();
            ButtonListener bl = new ButtonListener();
            buttonPanel.add(goButton);
            goButton.addActionListener(bl);
            p.add(buttonPanel);

            // dialog.getContentPane().add(p);
            dialog.getContentPane().add(p);
            dialog.pack();
            dialog.setVisible(true);
            // frame.getContentPane().add(dialog);

        }

        /**
         * ButtonListener class implements the ActionListener interface and handles
         * button click events.
         * This class defines the actionPerformed method to respond to button clicks,
         * specifically for the "Go" button
         * in the image resizing panel.
         */
        public class ButtonListener implements ActionListener {
            /**
             * Responds to button click events.
             *
             * This method is called when a button is clicked. It retrieves the source of
             * the action, parses the input fields for height and width, initiates the
             * resizing process,
             * and updates the target component accordingly. It also handles various
             * exceptions that may occur during the resizing process.
             *
             * @param ae The action event triggered by the button click.
             */
            public void actionPerformed(ActionEvent ae) {
                JButton source = (JButton) ae.getSource();
                try {
                    if (source == goButton) {
                        height = Integer.parseInt(heightField.getText());
                        width = Integer.parseInt(widthField.getText());
                    }
                    target.getImage().apply(new ImageResize(height, width));
                    target.repaint();
                    target.getParent().revalidate();
                    dialog.dispose();
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
        public void actionPerformed(ActionEvent e) {
            try {
                // Determine the radius - ask the user.
                int radius = 0;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, null, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(Andie.getFrame(), radiusSpinner,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                    System.out.println(radius);
                }

                // Create and apply the filter
                target.getImage().apply(new RandomScattering2(radius));
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
