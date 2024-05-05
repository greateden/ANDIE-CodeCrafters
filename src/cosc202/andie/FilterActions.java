package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

        Action mean = new MeanFilterAction(Andie.bundle.getString("MeanFilterAction"), null,
                Andie.bundle.getString("MeanFilterDesc"),
                Integer.valueOf(KeyEvent.VK_M));
        actions.add(mean);

        Action soft = new SoftBlurFilterAction(Andie.bundle.getString("SoftBlur"), null,
                Andie.bundle.getString("SoftBlurDesc"),
                Integer.valueOf(KeyEvent.VK_S));
        actions.add(soft);

        Action sharpen = new SharpenFilterAction(Andie.bundle.getString("SharpenFilter"), null,
                Andie.bundle.getString("SharpenDesc"),
                Integer.valueOf(KeyEvent.VK_H));
        actions.add(sharpen);

        Action gaussian = new GaussianFilterAction(Andie.bundle.getString("GaussianFilter"), null,
                Andie.bundle.getString("GaussianDesc"),
                Integer.valueOf(KeyEvent.VK_G));
        actions.add(gaussian);

        Action median = new MedianFilterAction(Andie.bundle.getString("MedianFilter"), null,
                Andie.bundle.getString("MedianDesc"),
                Integer.valueOf(KeyEvent.VK_D));
        actions.add(median);

        Action emboss = new EmbossFilterAction(Andie.bundle.getString("EmbossFilter"), null,
                Andie.bundle.getString("EmbossDesc"),
                Integer.valueOf(KeyEvent.VK_E));
        actions.add(emboss);

        Action sobel = new SobelFilterAction("Sobel Filter", null, "Applys a sobel filter vertically or horizionally", null);
        actions.add(sobel);
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

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a radius, then applies a mean filter of that
         * radius to the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {
                // Determine the radius - ask the user.
                int radius = 0;

                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                JLabel info = new JLabel();
                info.setText(Andie.bundle.getString("PleaseEnter"));
                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                panel.add(info);
                panel.add(radiusSpinner);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), panel,
                        Andie.bundle.getString("EnterFilterRadius"),
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
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    /**
     * <p>
     * Action to change an image with a median filter.
     * </p>
     *
     * @see MedianFilter
     */
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
        JSlider medianSlider;
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

                final EditableImage preview = target.getImage().makeCopy();
                final ImagePanel show = new ImagePanel(preview);
                PreviewPanel show2 = new PreviewPanel(preview);

                medianSlider = new JSlider(JSlider.VERTICAL, 0,10,0);
                medianSlider.setMajorTickSpacing(1);
                medianSlider.setPaintTicks(true);
                medianSlider.setPaintLabels(true);
                medianSlider.setSnapToTicks(true);
                medianSlider.setValue(0);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
    
                        int rad = medianSlider.getValue();

                        //Setting a new target for the ImageActon
                        setTarget(show);
                        target.getImage().reset();
                        target.getImage().apply(new MedianFilter(rad)); 
                        target.repaint(); 
                        target.getParent().revalidate(); 
                        
                    }
                };

                medianSlider.addChangeListener(sliderChangeListener);

                // Pop-up dialog box to ask for the radius value.
                //SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                //JSpinner radiusSpinner = new JSpinner(radiusModel);

                JPanel menu = new JPanel(new FlowLayout());
                //GridBagConstraints a = new GridBagConstraints();
                menu.add(show);
                menu.add(medianSlider);


                int option = JOptionPane.showOptionDialog(Andie.frame, menu, Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = medianSlider.getValue();
                    setTarget(Andie.getPanel());
                    target.getImage().apply(new MedianFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }
                
                
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
                else{
                    System.out.println(err);
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
                // System.out.println("the language is" +
                // Andie.bundle.getString("EnterFilterRadius"));

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), radiusSpinner,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

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
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }

    }
    public class SobelFilterAction extends ImageAction{
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }


        public void actionPerformed(ActionEvent e){
            try{
                JPanel panel = new JPanel(new GridLayout(3, 1));
                JLabel l1 = new JLabel("Please Select Sobel Direction");
                panel.add(l1);
                JRadioButton r1 = new JRadioButton("Horizontal");
                JRadioButton r2 = new JRadioButton("Vertical");
                ButtonGroup g1 = new ButtonGroup();
                g1.add(r1);
                g1.add(r2);

                panel.add(r1);
                panel.add(r2);

                int option = JOptionPane.showOptionDialog(null, panel, "Select Sobel Direction", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,null,null);
                if (option == JOptionPane.OK_OPTION){
                    boolean Direction = false;
                    if (r1.isSelected()){
                        Direction = true;
                    }
                    target.getImage().apply(new SobelFilter(Direction));
                    target.repaint();
                    target.getParent().revalidate();


                }

            }
            catch (Exception err){
                if (err instanceof NullPointerException){
                    JOptionPane.showMessageDialog(Andie.getFrame(),Andie.bundle.getString("YouDidNotOpen"), Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }


    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new EmbossFilter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         *
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link EmbossFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {

                // Determine the radius - ask the user.
                int angle = 0;
                int radius =0;
                SpinnerNumberModel angleModel = new SpinnerNumberModel(0, 0, 360, 1);
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JPanel panel = new JPanel(new GridLayout(4, 1));
                JLabel l1 = new JLabel(Andie.bundle.getString("EnterEmbossDirection"));
                JSpinner s1 = new JSpinner(angleModel);
                JSpinner s2 = new JSpinner(radiusModel);

                JLabel l2 = new JLabel(Andie.bundle.getString("EnterFilterRadius"));
                panel.add(l1);
                panel.add(s1);
                panel.add(l2);
                panel.add(s2);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), panel,
                        Andie.bundle.getString("EnterEmbossDirection"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    angle = angleModel.getNumber().intValue();
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new EmbossFilter(angle, radius));
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
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
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
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }
}
