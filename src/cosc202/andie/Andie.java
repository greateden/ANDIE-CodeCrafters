package cosc202.andie;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

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
    public static JFrame frame;
    private static ImagePanel imagePanel;
    public static ResourceBundle bundle;
    public static JMenuBar menuBar;
    public static JTabbedPane tabs;

    private static FileActions fileActions;
    private static EditActions editActions;
    private static ViewActions viewActions;
    private static FilterActions filterActions;
    private static ImageMenuBar imageMenuBar;
    private static ColourActions colourActions;
    private static HelpActions helpActions;
    private static MacroActions macroActions;

    public static boolean allCertainMenuStatus;

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
     * @see MacroActions
     *
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        frame = new JFrame("ANDIE: CodeCrafters");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 600));

        // The main content area is an ImagePanel
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        // tabs = new JTabbedPane();
        // tabs.addTab("Welcome", null, null, "Does nothing yet");
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        

        scrollPane.getViewport().getView().addMouseWheelListener(new MouseWheelListener(){

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int verticalScrollAmount = e.getUnitsToScroll();
                //int horizontalScrollAmount = e.getUnitsToScroll();

                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                //JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

                verticalScrollBar.setValue(verticalScrollBar.getValue() - verticalScrollAmount);
                //horizontalScrollBar.setValue(horizontalScrollBar.getValue() - horizontalScrollAmount);
            }
            

        });

        frame.add(scrollPane, BorderLayout.CENTER);
        // frame.add(tabs);
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
    public static void getStatus() {
        System.out.println(frame.getExtendedState());
    }

    /**
     * Method to change all the related "grey buttons'" status
     * before/after the image get imported.
     * @param status
     */
    public static void changeAllCertainMenuStatus(boolean status) {
        fileActions.changeCertainMenuStatus(status);
        editActions.changeCertainMenuStatus(status);
        viewActions.changeCertainMenuStatus(status);
        filterActions.changeCertainMenuStatus(status);
        imageMenuBar.changeCertainMenuStatus(status);
        colourActions.changeCertainMenuStatus(status);
        macroActions.changeCertainMenuStatus(status);

    }

    /**
     * <p>
     * Creates the menu bar for the GUI with a whole hellofa bunch of menus inside.
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
        macroActions = new MacroActions();
        helpActions = new HelpActions();
        // File menus are pretty standard, so things that usually go in File menus go
        // here.

        JMenu fileMenu = fileActions.createMenu();
        newMenuBar.add(fileMenu);

        // Likewise Edit menus are very common, so should be clear what might go here.
        JMenu editMenu = editActions.createMenu();
        newMenuBar.add(editMenu);

        // View actions control how the image is displayed, but do not alter its actual
        // content
        JMenu viewMenu = viewActions.createMenu();
        newMenuBar.add(viewMenu);

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        JMenu filterMenu = filterActions.createMenu();
        newMenuBar.add(filterMenu);

        // Actions that affect the representation of colour in the image
        JMenu colourMenu = colourActions.createMenu();
        newMenuBar.add(colourMenu);

        // Actions that alter the image such as image flip/rotate
        JMenu imageMenu = imageMenuBar.createMenu();
        newMenuBar.add(imageMenu);

        // actions that apply a macro funtion of the operations
        newMenuBar.add(macroActions.createMenu());

        // Provides an about page and link to online docs
        JMenu helpMenu = helpActions.createMenu();
        newMenuBar.add(helpMenu);

        

        frame.setJMenuBar(newMenuBar);

        changeAllCertainMenuStatus(allCertainMenuStatus);// before open an image, set all to unable to click

        CreateHotKey.createHotkey(fileMenu, KeyEvent.VK_F, 0, "filemenu");
        CreateHotKey.createHotkey(editMenu, KeyEvent.VK_E, 0, "editmenu");
        CreateHotKey.createHotkey(viewMenu, KeyEvent.VK_V, 0, "viewmenu");
        CreateHotKey.createHotkey(filterMenu, KeyEvent.VK_L, 0, "filtermenu");
        CreateHotKey.createHotkey(colourMenu, KeyEvent.VK_C, 0, "colourmenu");
        CreateHotKey.createHotkey(imageMenu, KeyEvent.VK_I, 0, "imagemenu");
        CreateHotKey.createHotkey(helpMenu, KeyEvent.VK_H, 0, "helpmenu");

        frame.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);
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
     * Yes it's calling createMenuBar(), we'rejust trying to make it with more
     * sense.
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

            public void run() {

                Locale.setDefault(new Locale("en", "NZ"));

                // Now making the ResourceBundle
                bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

                // Line below is for testing the bundle
                // System.out.println(bundle.getString("convertToGreyAction"));

                try {
                    FlatMacDarkLaf.setup();
                    try {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                    // Keeping in case we need to revert. Also change dependency in Gradle file.
                    // try{
                    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    // }
                    // catch(Exception ignored){}
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

    /**
     * An accessor for the imagePanel that is used in the preview panels for
     * Brightness and Contrast, among others.
     *
     * @author Kevin Steve Sathyanath
     * @return the ImagePanel in question
     * @date 27/04/2024
     */
    public static ImagePanel getPanel() {
        return imagePanel;
    }

    /**
     * A method to add tabs to the tabPane
     *
     * @author Kevin Steve Sathyanath
     *         date 5/5/2024
     */
    public static void addTab(EditableImage i) {

    }
}