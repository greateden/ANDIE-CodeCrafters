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
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author CodeCrafters
 * @version 1.0
 */
public class ImageMenuBar {

    private int rotateAttempt = 0;
    public ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;
    private JMenu fileMenu;
    private ImageScalingAction scalAct;

    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public ImageMenuBar() {
        actions = new ArrayList<Action>();
        actions.add(new ImageMenuBarFlipHorizontal(Andie.bundle.getString("FlipHorizontal"), null,
                Andie.bundle.getString("FHDesc"), Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new ImageMenuBarFlipVertical(Andie.bundle.getString("FlipVertical"), null,
                Andie.bundle.getString("FVDesc"), Integer.valueOf(KeyEvent.VK_V)));
        actions.add(new RotateImageAction(Andie.bundle.getString("RotateImageAction"), null,
                Andie.bundle.getString("RIADesc"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new ImageResizeAction(Andie.bundle.getString("ImageResizeAction"), null,
                Andie.bundle.getString("ImageResizeAction"), Integer.valueOf(KeyEvent.VK_J)));
        scalAct = new ImageScalingAction(Andie.bundle.getString("Scaling"), null, Andie.bundle.getString("ReScaling"),
                null);
        actions.add(scalAct);
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
            if (action != scalAct) {
                fileMenu.add(new JMenuItem(action));
            }

        }

        // JMenuItem scaleMenu = fileMenu.getItem(4);
        scalAct.createSubMenu();

        return fileMenu;
    }

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
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

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
            try {
                target.getImage().apply(new ImageFlipVertical());
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
     * /**
     * Action class code layout created by Steven Mills
     */
    public class RotateImageAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian-filter action.
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
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianFilter}.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianFilter}.
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
                int option = JOptionPane.showOptionDialog(null, degSpinner,
                        Andie.bundle.getString("EnterRotationTheta"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    deg = degModel.getNumber().intValue();
                }
                //rotateAttempt++;
                // Create and apply the filter
                target.getImage().apply(new ImageRotate(deg, rotateAttempt));
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
     * 
     * /**
     * <p>
     * Action to resieze an image.
     * 
     * 
     * @made by Yuxing Zhang
     *       </p>
     *       </p>
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
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }

            }
        }

        protected void createPanel() {

            // Write code to create the panel
            // JPanel panel=new JPanel();

            JDialog dialog = new JDialog(Andie.getFrame(), Andie.bundle.getString("Resize"), true);
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

        public class ButtonListener implements ActionListener {
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
                } catch (Exception e) {
                    System.out.println(e);
                    if (e instanceof NumberFormatException) {
                        JOptionPane.showMessageDialog(null,
                                Andie.bundle.getString("PosInt"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof java.lang.NegativeArraySizeException) {
                        JOptionPane.showMessageDialog(null,
                                Andie.bundle.getString("SmallNum"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof java.lang.IllegalArgumentException) {
                        JOptionPane.showMessageDialog(null,
                                Andie.bundle.getString("PosOrSmallInt"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    } else if (e instanceof NullPointerException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    } else {
                        // show message dialog and print the e into the box, saying that's an unexpected
                        // error.
                        JOptionPane.showMessageDialog(null,
                                Andie.bundle.getString("BooBoo"),
                                Andie.bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

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

            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

        public void createSubMenu() {

            JMenu scaleMenu = new JMenu(Andie.bundle.getString("Scaling"));
            JMenuItem scale25 = new JMenuItem("25%");
            JMenuItem scale50 = new JMenuItem("50%");
            JMenuItem scale75 = new JMenuItem("75%");
            JMenuItem scale125 = new JMenuItem("125%");
            JMenuItem scale150 = new JMenuItem("150%");

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

        public class ScaleActionListener implements ActionListener {
            private double scalePercentage;

            public ScaleActionListener(double scalePercentage) {
                this.scalePercentage = scalePercentage;
            }

            public void actionPerformed(ActionEvent e) {
                try {
                    target.getImage().apply(new ImageScaling(scalePercentage));
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
}
