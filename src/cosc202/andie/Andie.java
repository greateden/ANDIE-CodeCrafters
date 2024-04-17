package cosc202.andie;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.*;

import cosc202.MouseSelection;

import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {
    private static JFrame frame;  
    private static ImagePanel imagePanel;
    public static ResourceBundle bundle;
    public static JMenuBar menuBar;

    private static FileActions fileActions;
    private static EditActions editActions;
    private static ViewActions viewActions;
    private static FilterActions filterActions;
    private static ImageMenuBar imageMenuBar;
    private static ColourActions colourActions;
    private static HelpActions helpActions;

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        frame = new JFrame("ANDIE: CodeCrafters");
        // JFrame.setDefaultLookAndFeelDecorated(true);
        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        imagePanel.addMouseListener(new MouseSelection(imagePanel));
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        fileActions = new FileActions();
        editActions = new EditActions();
        viewActions = new ViewActions();
        filterActions = new FilterActions();
        imageMenuBar = new ImageMenuBar();
        colourActions = new ColourActions();
        helpActions = new HelpActions();

        createMenuBar();

    }

    /**
     * <p>
     * Exits full screen mode.
     * </p>
     */
    public static void exitFullScreen() {
        frame.setExtendedState(JFrame.NORMAL);
    }

    /**
     * <p>
     * Gets the status of the frame.
     * </p>
     */
    public static void getStatus(){
        System.out.println(frame.getExtendedState());
    }

    /**
     * <p>
     * Creates the menu bar for the GUI.
     * </p>
     */
    private static void createMenuBar() {
        // Add in menus for various types of action the user may perform.
        JMenuBar newMenuBar = new JMenuBar();
    
        fileActions = new FileActions();
        editActions = new EditActions();
        viewActions = new ViewActions();
        filterActions = new FilterActions();
        imageMenuBar = new ImageMenuBar();
        colourActions = new ColourActions();
        helpActions = new HelpActions();
        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        newMenuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        newMenuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content
        newMenuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        newMenuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        newMenuBar.add(colourActions.createMenu());

        // Actions that alter the image such as image flip/rotate
        newMenuBar.add(imageMenuBar.createMenu());

        // Provides an about page and link to online docs
        newMenuBar.add(helpActions.createMenu());

        frame.setJMenuBar(newMenuBar);
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <p>
     * Gets the frame.
     * </p>
     * 
     * @return The frame.
     */
    public static JFrame getFrame() {
        return frame;
    }

    /**
     * Yes it's calling createMenuBar(), we'rejust trying to make it with more sense.
     * Makes more sense than using a carrier pigeon for teammate communication.
     */
    public static void setLanguage() {
        boolean prevIsOpenedStatus = fileActions.isOpenedGetter();
        createMenuBar();
        fileActions.isOpenedSetter(prevIsOpenedStatus);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @SuppressWarnings("deprecation")
            public void run() {

                // Making preferences and locale
                Preferences p = Preferences.userNodeForPackage(Andie.class);
                Locale.setDefault(new Locale("en","NZ"));

                // Now making the ResourceBundle
                bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

                // Line below is for testing the bundle
                // System.out.println(bundle.getString("convertToGreyAction"));

                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}