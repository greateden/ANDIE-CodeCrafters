package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 *
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
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
 *          modified_by The Greatest Eden
 *          modified_date 10 Mar 2024
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    public ResourceBundle bundle = Andie.bundle;
    public BufferedImage previewImage;
    public ImageIcon previewIcon;
    public JPanel previewPanel;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();

        Action grey = new ConvertToGreyAction(Andie.bundle.getString("convertToGreyAction"), null,
                Andie.bundle.getString("GreyscaleDesc"), Integer.valueOf(KeyEvent.VK_G));
        actions.add(grey);

        Action invert = new ImageInvertAction(Andie.bundle.getString("invertColour"), null,
                Andie.bundle.getString("ImageInvertDesc"),
                Integer.valueOf(KeyEvent.VK_I));
        actions.add(invert);
        CreateHotKey.createHotkey(invert, KeyEvent.VK_I, InputEvent.META_DOWN_MASK, "invert");

        Action rgbSwap = new RGBSwappingAction(Andie.bundle.getString("RGBSwappingAction"), null,
                Andie.bundle.getString("RGBSwapDesc"),
                Integer.valueOf(KeyEvent.VK_C));
        actions.add(rgbSwap);
        actions.add(new brightnessAndContrastAction("Brightness and Contrast", null, "Change the Brightness and Contrast of the loaded image.", Integer.valueOf(KeyEvent.VK_B)));

    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     *
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("Colour"));

        for (Action action : actions) {

            fileMenu.add(new JMenuItem(action));
        }

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
            try {
                target.getImage().apply(new ConvertToGrey());
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
     * Action to invert an image.
     *
     * Same as the above class, but edited to work for imageInvert().
     * </p>
     */
    public class ImageInvertAction extends ImageAction {

        /**
         * <p>
         * Create a new imageInvert action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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
            try {
                target.getImage().apply(new ImageInvert());
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
            try {
                // Default value of the orders of R, G and B.
                int R = 0;
                int G = 0;
                int B = 0;

                // Create the dialog panel
                JPanel panel = new JPanel(new GridLayout(5, 3));

                JLabel l1 = new JLabel(Andie.bundle.getString("RGBSwappingInstruction"));
                JLabel l2 = new JLabel();
                JLabel l3 = new JLabel();

                panel.add(l1);
                panel.add(l2);
                panel.add(l3);

                JLabel l4 = new JLabel(Andie.bundle.getString("FirstChannel"));
                JLabel l5 = new JLabel(Andie.bundle.getString("SecondChannel"));
                JLabel l6 = new JLabel(Andie.bundle.getString("ThirdChannel"));

                panel.add(l4);
                panel.add(l5);
                panel.add(l6);


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
                int option = JOptionPane.showOptionDialog(null, panel, Andie.bundle.getString("ColourSelection"),
                        JOptionPane.OK_CANCEL_OPTION,
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

                    // Fun fact: put the code below outside of the if statement to trick the
                    // users!!!
                    // Especially useful on 1st Apr

                    // If user gave half an input, will say we don't understand what you're tryna do
                    if (!(R == 0 && G == 0 && B == 0) && (R == B || B == G || R == B || R == 0 || G == 0 || B == 0)) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("CannotProcess"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                        // If user gave full input but with the same RGB order, will give them a
                        // friendly notice.
                    } else if (!(R == 0 && G == 0 && B == 0) && (R == 1 && G == 2 && B == 3)) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("WithRespect"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        // Will educate the user if they didn't give any inputs and still wanna hit the
                        // OK button
                    } else if (R == 0 && G == 0 && B == 0) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("PleaseChoose"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                        // If a legal input (i.e., passed all the ifs above), will apply the filter.
                    } else {
                        // Apply the filter
                        target.getImage().apply(new RGBSwapping(R, G, B));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                }
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    System.out.println(err);
                }
            }
        }
    }// End of RGBSwapping()

    /**A class to implement the GUI for B&C manipulation.
     * @author Kevin Steve Sathyanath
     * @date 19/04/2024
     */
    public class brightnessAndContrastAction extends ImageAction{

        int brightnessFactor = 0;
        int contrastFactor = 0;

        /**
         * <p>
         * Create a new brightnessAndContrast action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        brightnessAndContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the brightnessAndContrast action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever brightnessAndContrast is triggered.
         * It changes the image's brightness and contrast depending on user input.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        JSlider brightnessSlider;
        JSlider contrastSlider;



        public void actionPerformed(ActionEvent e){
            try{
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());

                final EditableImage preview = target.getImage().makeCopy();
                final ImagePanel show = new ImagePanel(preview);

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(new Dimension(500,300));
                updatePreviewImage(prev);




                JPanel sliderPane = new JPanel(new GridLayout(1,2, 17, 0));
                JPanel labelPane = new JPanel(new GridLayout(1,2,167,0));
                brightnessSlider = new JSlider(-100,100);
                contrastSlider = new JSlider(-100,100);
                JLabel brightnessSliderLabel = new JLabel("Brightness", JLabel.CENTER);
                brightnessSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel contrastSliderLabel = new JLabel("Contrast", JLabel.CENTER);
                contrastSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                contrastSlider.setMajorTickSpacing(50);
                contrastSlider.setPaintTicks(true);
                contrastSlider.setPaintLabels(true);
                contrastSlider.setValue(0);

                brightnessSlider.setMajorTickSpacing(50);
                brightnessSlider.setPaintTicks(true);
                brightnessSlider.setPaintLabels(true);
                brightnessSlider.setValue(0);



                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        brightnessFactor = brightnessSlider.getValue();
                        contrastFactor = contrastSlider.getValue();

                        //Setting a new target for the ImageActon
                        // setTarget(show);
                        // target.getImage().reset();
                        // target.getImage().apply(new BrightnessAndContrast(brightnessFactor,contrastFactor));
                        //target.getImage().repaint();
                        // target.getParent().revalidate();
                        //curr = BrightnessAndContrast.applyToPreview(prev, brightnessFactor, contrastFactor);
                        BufferedImage curr = BrightnessAndContrast.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), brightnessFactor, contrastFactor);
                        updatePreviewImage(curr);


                    }
                };

                brightnessSlider.addChangeListener(sliderChangeListener);
                contrastSlider.addChangeListener(sliderChangeListener);


                labelPane.add(brightnessSliderLabel);
                labelPane.add(contrastSliderLabel);

                sliderPane.add(brightnessSlider);
                sliderPane.add(contrastSlider);
                //p.add(show);

                JPanel menu = new JPanel(new GridBagLayout());
                GridBagConstraints a = new GridBagConstraints();
                Insets i = new Insets(20,0,0,0);

                //a.fill = GridBagConstraints.BOTH;
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
                i.set(10,0,0,0);
                menu.add(labelPane, a);

                int option = JOptionPane.showOptionDialog(null, menu, "Set Brightness And Contrast",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);  //Added ImageIcon here



                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {

                }
                else if (option == JOptionPane.OK_OPTION) {
                    //System.out.println(brightnessFactor + " " + contrastFactor);
                    //setTarget(Andie.getPanel());
                    target.getImage().apply(new BrightnessAndContrast(brightnessFactor, contrastFactor));
                    target.getParent().revalidate();
                    target.repaint();
                }
            } //End of try


            catch(Exception err){
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    System.out.println(err);
                }
            } //End of catch


            } //End of actionPerformed()
        }//End of B&C()


        public void updatePreviewImage(BufferedImage i){
            JLabel pic = new JLabel(new ImageIcon(i));
            previewIcon = new ImageIcon(i);
            previewPanel.removeAll();
            previewPanel.add(pic);
            previewPanel.repaint();
            previewPanel.revalidate();
        }

    }//End of class
