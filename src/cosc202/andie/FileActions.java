package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {

    ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    protected boolean isOpened = false;
    // protected boolean isSaved = false;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */

    public FileActions() {

        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(bundle.getString("OpenAction"), null, bundle.getString("OpenDesc"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(bundle.getString("SaveAction"), null,
                bundle.getString("SaveDesc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(bundle.getString("SaveAsAction"), null, bundle.getString("SaveAsDesc"),
                Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(bundle.getString("ExportAction"), null, bundle.getString("ExportDesc"),
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(bundle.getString("ExitAction"), null, bundle.getString("ExitDesc"), Integer.valueOf(KeyEvent.VK_Q)));
        actions.add(new FileChangeLanguageAction(bundle.getString("ChangeLanguage"),null , bundle.getString("ChangeLanguage"), Integer.valueOf(KeyEvent.VK_L)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("File"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // dead bug showing here
            // do not create a new instance cuz otherwise the stack will defo be empty!!!
            // EditableImage ei = new EditableImage();

            if (isOpened == true && EditableImage.isOpsNotEmptyStatus == true) {

                Object[] options = { bundle.getString("Yes"), bundle.getString("No"), bundle.getString("Cancel") };

                int n = JOptionPane.showOptionDialog(null,
                        bundle.getString("DoYouWantToSave"),
                        bundle.getString("Warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                //TODO issues here
                if (n == 0) { // yes
                    if (EditableImage.hasOpsFile == false) actions.get(1).actionPerformed(e);
                    if (EditableImage.hasOpsFile == true) actions.get(0).actionPerformed(e);

                } else if (n == 1) { // no
                    openFile();
                } else {
                    // Should change this statement to case control cuz the "else" here is
                    // useless...
                }
            } else {
                openFile();
            }
        }

        public void openFile() {
            JFileChooser fileChooser = new JFileChooser();

            // Cannot resolve .dYSM files
            FileNameExtensionFilter filterJPG = new FileNameExtensionFilter(
                    "JPG, JPEG", "jpg", "jpeg");
            fileChooser.setFileFilter(filterJPG);

            FileNameExtensionFilter filterGIF = new FileNameExtensionFilter(
                    "GIF", "gif");
            fileChooser.setFileFilter(filterGIF);

            FileNameExtensionFilter filterTIF = new FileNameExtensionFilter(
                    "TIF, TIFF", "tif", "tiff");
            fileChooser.setFileFilter(filterTIF);

            FileNameExtensionFilter filterPNG = new FileNameExtensionFilter(
                    "PNG", "png");
            fileChooser.setFileFilter(filterPNG);

            FileNameExtensionFilter filterBMP = new FileNameExtensionFilter(
                    "BMP", "bmp");
            fileChooser.setFileFilter(filterBMP);

            FileNameExtensionFilter filterWBEP = new FileNameExtensionFilter(
                    "WBEP", "wbep");
            fileChooser.setFileFilter(filterWBEP);

            FileNameExtensionFilter filterAllTypes = new FileNameExtensionFilter(
                    bundle.getString("AllSupportedFileTypes"), "jpg", "jpeg", "gif", "tif", "tiff", "png", "bmp", "wbep");
            fileChooser.setFileFilter(filterAllTypes);

            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    isOpened = true;
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    System.exit(1);
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image without saving the .ops file.
     * i.e., to actually make changes to the image.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileExportAction extends ImageAction {
        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a format they wants to export and export
         * to an image without .ops file.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (isOpened == true) { // The user doesn't have to save first when exporting images
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

                // Add file filters for different image formats
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", "Joint Photographic Experts Group"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", "Tagged Image File Format"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", "Portable Network Graphics"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", "Bitmap Image File"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", "WebP Image File"));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        FileFilter selectedFilter = fileChooser.getFileFilter();
                        String format = ((ImageFileFilter) selectedFilter).getExtension();
                        ImageIO.write(target.getImage().getCurrentImage(), format, new File(imageFilepath));
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("YouDidNotOpen"),
                        bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

        private class ImageFileFilter extends FileFilter {
            private String extension;
            private String description;

            public ImageFileFilter(String extension, String description) {
                this.extension = extension.toLowerCase();
                this.description = description;
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith("." + extension);
            }

            public String getDescription() {
                return description + String.format(" (*.%s)", extension);
            }

            public String getExtension() {
                return extension;
            }
        }
    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // if no .ops file detected, go to save as
                // to keep the original image.
                if (EditableImage.hasOpsFile == false) {
                    FileSaveAsAction saveAsAction = new FileSaveAsAction("Save As", null, "Save a copy",
                            Integer.valueOf(KeyEvent.VK_A));
                    saveAsAction.actionPerformed(e);
                    // No need for the command below because otherwise if the user clicked
                    // the button and closed the new window without saving anything,
                    // the command below will set the value to true and cause bugs!
                    // isSaved = true;
                } else {
                    target.getImage().save();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bundle.getString("YouDidNotOpen"),
                        bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();

            if (isOpened == true) {

                fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

                // Add file filters for different image formats
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", "Joint Photographic Experts Group"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", "Tagged Image File Format"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", "Portable Network Graphics"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", "Bitmap Image File"));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", "WebP Image File"));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().saveAs(imageFilepath);
                        // isSaved = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, bundle.getString("MyDearUser"),
                            bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("YouDidNotOpen"),
                        bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

        private class ImageFileFilter extends FileFilter {
            private String extension;
            private String description;

            public ImageFileFilter(String extension, String description) {
                this.extension = extension.toLowerCase();
                this.description = description;
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith("." + extension);
            }

            public String getDescription() {
                return description + String.format(" (*.%s)", extension);
            }

            public String getExtension() {
                return extension;
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

      /**
     * <p>
     * Action to change the language.
     * by Kevin Sathyanath, adapted from Yuxing's Resize Image work.
     * </p>
     * 
     */
    public class FileChangeLanguageAction extends ImageAction implements ActionListener {
        int height;
        int width;
        JLabel widthJLabel, heightLabel, titleLabel,blankLabel;
        JTextField widthField, heightField;
        JButton bahasaButton;
        JButton englishButton;
   
        /**
         * <p>
         * Create a Language Change Action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileChangeLanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Change Language action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Change Language Action is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */

        @Override
        public void actionPerformed(ActionEvent e) {


        String[] languages =  { "English" , "Bahasa Indonesia"};

        
            
            JFrame l = new JFrame();

        //Set behaviour of frame
        l.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        l.setBounds(200,200,300,200);
        Container c = l.getContentPane();
        c.setLayout(new FlowLayout());

        JButton english = new JButton("English - NZ");
        JButton bahasa = new JButton("Bahasa Indonesia - ID");

        english.setSize(500,30);
        english.setLocation(100,100);
        c.add(english);
        bahasa.setSize(400,30);
        bahasa.setLocation(100,250);
        c.add(bahasa);

        //english.setEnabled(false);

        english.addActionListener(new ActionListener(){     //Anonymous inner class to show behaviour of english button


            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e){

                Preferences p = Preferences.userNodeForPackage(Andie.class);
                Locale.setDefault(new Locale( "en", "NZ"));
                p.put("language", "en");
                p.put("country", "NZ");
                Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                
                System.out.println(p.get("language", "id"));
                Andie.setLanguage();
                System.out.println(Andie.bundle.getString("EnterFilterRadius"));


            }

        });  //End of anonymous inner class





        bahasa.addActionListener(new ActionListener(){

            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e){

                Preferences p = Preferences.userNodeForPackage(Andie.class);
                Locale.setDefault(new Locale( "id", "ID"));
                p.put("language","id");
                p.put("country", "ID");
                Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                System.out.println(p.get("language", "en"));
                Andie.setLanguage();
                System.out.println(Andie.bundle.getString("EnterFilterRadius"));


            }

        });

        l.setVisible(true);
            /* 
            if(e.getSource() == englishButton){
                Preferences p = Preferences.userNodeForPackage(Andie.class);
                Locale.setDefault(new Locale(p.get("language", "en"), p.get("country", "NZ")));
            }
            if(e.getSource() == bahasaButton){
                Preferences p = Preferences.userNodeForPackage(Andie.class);
                Locale.setDefault(new Locale(p.get("language", "id"), p.get("country", "ID")));
            }
             //Create a panel
             //createPanel();

            */
        }

    

    protected void createPanel(){
        
        JFrame l = new JFrame();

        //Set behaviour of frame
        l.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        l.setBounds(200,200,500,500);
        Container c = l.getContentPane();
        c.setLayout(new FlowLayout());

        JButton english = new JButton("English - NZ");
        JButton bahasa = new JButton("Bahasa Indonesia - ID");

        english.setSize(100,30);
        english.setLocation(100,100);
        c.add(english);
        bahasa.setSize(100,30);
        bahasa.setLocation(100,200);
        c.add(bahasa);

        //english.setEnabled(false);

        english.addActionListener(this);
        bahasa.addActionListener(this);

        l.setVisible(true);
        
    } 
    }
}