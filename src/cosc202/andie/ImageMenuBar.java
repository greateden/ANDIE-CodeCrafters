package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import cosc202.andie.FilterActions.GaussianFilterAction;
import javax.swing.plaf.basic.BasicButtonListener;

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
        actions.add(new ImageResizeAction("Image Resize", null, "Resize the Image", Integer.valueOf(KeyEvent.VK_J)));
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

        for (Action action : actions) {
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
            target.getImage().apply(new ImageFlipVertical());
            target.repaint();
            target.getParent().revalidate();
        }
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
  /**
     * <p>
     * Action to resieze an image.
     * @made by Yuxing Zhang
     * </p>
     * 
     */
    public class ImageResizeAction extends ImageAction {
        int height;
        int width;
        JLabel widthJLabel, heightLabel, titleLabel,blankLabel;
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

            // ResizePannel rp = new ResizePannel();
             //Create a panel
             createPanel();

            // int height = rp.getHeight();
            // int width = rp.getWidth();
            // System.out.println(height);
            // System.out.println(width);

            // target.getImage().apply(new ImageResize());
            // target.repaint();
            // target.getParent().revalidate();
        }

    

    protected void createPanel(){
        
        //Write code to create the panel
       // JPanel panel=new JPanel();
       
       JDialog  dialog = new JDialog (Andie.getFrame(),"Resize",true);
       dialog.setPreferredSize(new Dimension(500, 400));
       dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 2));
 ;

        p.setPreferredSize(new Dimension(250, 350));
        titleLabel = new JLabel("Set the size of the new image");
        titleLabel.setPreferredSize(new Dimension(200, 50));
        blankLabel = new JLabel("                       ");


        heightLabel = new JLabel("Height:");
        heightLabel.setPreferredSize(new Dimension(100, 50));
        widthJLabel = new JLabel("Width:");
        widthJLabel.setPreferredSize(new Dimension(100, 50));

        widthField = new JTextField(5);
        heightField = new JTextField(5);
        heightField.setPreferredSize(new Dimension(100, 50));
        widthField.setPreferredSize(new Dimension(100, 50));

        goButton = new JButton("Go!");
        goButton.setOpaque(true);
        goButton.setBackground(Color.black);
        goButton.setPreferredSize(new Dimension(200, 50));

        //panel.add(frame);
        

//add all the lables and buttons to the panel
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

            JButton source = (JButton) ae.getSource();;
            if (source == goButton) {
                height = Integer.parseInt(heightField.getText());
                width = Integer.parseInt(widthField.getText());
            }
            //  new ImageResize(height, width);
            target.getImage().apply(new ImageResize(height, width));
        target.repaint();
        target.getParent().revalidate();
        }
    }
    }
    
}

