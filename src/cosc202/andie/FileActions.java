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

    public ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    protected boolean isOpened = false;
    // protected boolean isSaved = false;

    public boolean isOpenedGetter(){
        return isOpened;
    }

    public boolean isOpenedSetter(boolean isOpened){
        this.isOpened = isOpened;
        return isOpened;
    }

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */

    public FileActions() {

        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(Andie.bundle.getString("OpenAction"), null, Andie.bundle.getString("OpenDesc"),
                Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(Andie.bundle.getString("SaveAction"), null,
                Andie.bundle.getString("SaveDesc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(
                new FileSaveAsAction(Andie.bundle.getString("SaveAsAction"), null, Andie.bundle.getString("SaveAsDesc"),
                        Integer.valueOf(KeyEvent.VK_A)));
        actions.add(
                new FileExportAction(Andie.bundle.getString("ExportAction"), null, Andie.bundle.getString("ExportDesc"),
                        Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(Andie.bundle.getString("ExitAction"), null, Andie.bundle.getString("ExitDesc"),
                Integer.valueOf(KeyEvent.VK_Q)));
        actions.add(new FileChangeLanguageAction(Andie.bundle.getString("ChangeLanguage"), null,
                Andie.bundle.getString("ChangeLanguage"), Integer.valueOf(KeyEvent.VK_L)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("File"));

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

            if (isOpened == true && EditableImage.isOpsNotEmptyStatus == true) {

                Object[] options = { Andie.bundle.getString("Yes"), Andie.bundle.getString("No"),
                        Andie.bundle.getString("Cancel") };

                int n = JOptionPane.showOptionDialog(
                        Andie.getFrame(),
                        Andie.bundle.getString("DoYouWantToSave"),
                        Andie.bundle.getString("Warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (n == 0) { // yes
                    // if user chose yes and there's no ops file saved
                    // then call "save"
                    actions.get(1).actionPerformed(e);

                    // then we treat it as saved so that next time we run the code, it will
                    // trigger the "else" statement outside of this loop
                    EditableImage.isOpsNotEmptyStatus = false;

                    // then open the openfile window
                    openFile();

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
                    Andie.bundle.getString("AllSupportedFiletypes"), "jpg", "jpeg", "gif", "tif", "tiff", "png", "bmp",
                    "wbep");
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
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", Andie.bundle.getString("JPG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", Andie.bundle.getString("TIFF")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", Andie.bundle.getString("PNG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", Andie.bundle.getString("BMP")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", Andie.bundle.getString("WBEP")));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", Andie.bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Codes below are generated by AI - ChatGPT 3.5

                        // gather the filter user chose
                        FileFilter selectedFilter = fileChooser.getFileFilter();

                        // get the extension user chose
                        String selectedExtension = ((ImageFileFilter) selectedFilter).getExtension();

                        File selectedFile = fileChooser.getSelectedFile();
                        // check whether there is the extension in user's operation, if yes, then not
                        // add one more time
                        String selectedFilePath = selectedFile.getCanonicalPath();
                        if (!selectedFilePath.toLowerCase().endsWith(selectedExtension.toLowerCase())) {
                            selectedFilePath += "." + selectedExtension.toLowerCase();
                        }
                        // end of AI codes

                        String format = ((ImageFileFilter) selectedFilter).getExtension();

                        ImageIO.write(target.getImage().getCurrentImage(), format, new File(selectedFilePath));

                        // create a message box to tell user it's saved successfully
                        //JOptionPane.showMessageDialog(null, Andie.bundle.getString("ImageExportSaveSuccess"),
                        //        Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
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
                // disestablished the codes below as it's a weird design
                // if (EditableImage.hasOpsFile == false) {
                // // these codes below don't need multi lingual support
                // FileSaveAsAction saveAsAction = new FileSaveAsAction("Save As", null, "Save a
                // copy",
                // Integer.valueOf(KeyEvent.VK_A));
                // saveAsAction.actionPerformed(e);
                // // No need for the command below because otherwise if the user clicked
                // // the button and closed the new window without saving anything,
                // // the command below will set the value to true and cause bugs!
                // // isSaved = true;
                // // then we must change the status to true
                // // otherwise it will ask the user to save a copy again
                // EditableImage.hasOpsFile = true;
                // } else {
                target.getImage().save();

                // then we treat it as saved so that next time we run the code, it will
                // trigger the "else" statement outside of this loop
                EditableImage.isOpsNotEmptyStatus = false;

                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                        Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);
                // }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image and the ops file to a new file location.
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
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", Andie.bundle.getString("JPG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", Andie.bundle.getString("TIFF")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", Andie.bundle.getString("PNG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", Andie.bundle.getString("BMP")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", Andie.bundle.getString("WBEP")));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", Andie.bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Codes below are generated by AI - ChatGPT 3.5

                        // gather the filter user chose
                        FileFilter selectedFilter = fileChooser.getFileFilter();

                        // get the extension user chose
                        String selectedExtension = ((ImageFileFilter) selectedFilter).getExtension();

                        File selectedFile = fileChooser.getSelectedFile();
                        // check whether there is the extension in user's operation, if yes, then not
                        // add one more time
                        String selectedFilePath = selectedFile.getCanonicalPath();
                        if (!selectedFilePath.toLowerCase().endsWith(selectedExtension.toLowerCase())) {
                            selectedFilePath += "." + selectedExtension.toLowerCase();
                        }
                        // end of AI codes

                        target.getImage().saveAs(selectedFilePath);

                        // then we treat it as saved so that next time we run the code, it will
                        // trigger the "else" statement outside of this loop
                        EditableImage.isOpsNotEmptyStatus = false;

                        // create a message box to tell user it's saved successfully
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                                Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                        // isSaved = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("MyDearUser"),
                                Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
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
            if (isOpened == true && EditableImage.isOpsNotEmptyStatus == true) {

                Object[] options = { Andie.bundle.getString("Yes"), Andie.bundle.getString("No"),
                        Andie.bundle.getString("Cancel") };

                int n = JOptionPane.showOptionDialog(
                        Andie.getFrame(),
                        Andie.bundle.getString("DoYouWantToSaveBeforeExit"),
                        Andie.bundle.getString("Warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (n == 0) { // yes
                    // if user chose yes and there's no ops file saved
                    // then call "save"
                    actions.get(1).actionPerformed(e);

                    // create a message box to tell user it's saved successfully
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                            Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                    // then we treat it as saved so that next time we run the code, it will
                    // trigger the "else" statement outside of this loop
                    EditableImage.isOpsNotEmptyStatus = false;

                    // then close window
                    System.exit(0);

                } else if (n == 1) { // no
                    System.exit(0);
                } else {
                    // Should change this statement to case control cuz the "else" here is
                    // useless...
                }
            } else {
                System.exit(0);
            }
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
        JLabel widthJLabel, heightLabel, titleLabel, blankLabel;
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

            String[] languages = { "English", "Bahasa Indonesia", "繁體中文" };

            JFrame l = new JFrame();

            // Set behaviour of frame
            l.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            l.setBounds(200, 200, 300, 200);
            Container c = l.getContentPane();
            c.setLayout(new FlowLayout());

            JButton english = new JButton("English - NZ");
            JButton bahasa = new JButton("Bahasa Indonesia - ID");
            JButton traChinese = new JButton("繁體中文 - TW");

            english.setSize(500, 30);
            english.setLocation(100, 100);
            c.add(english);
            bahasa.setSize(400, 30);
            bahasa.setLocation(100, 250);
            c.add(bahasa);
            traChinese.setSize(400, 30);
            traChinese.setLocation(100, 400);
            c.add(traChinese);

            english.addActionListener(new ActionListener() {
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e) {
                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("en", "NZ"));
                    p.put("language", "en");
                    p.put("country", "NZ");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                    System.out.println(p.get("language", "id"));
                    Andie.setLanguage();
                    l.dispose();
                }
            });

            bahasa.addActionListener(new ActionListener() {
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e) {
                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("id", "ID"));
                    p.put("language", "id");
                    p.put("country", "ID");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                    System.out.println(p.get("language", "en"));
                    Andie.setLanguage();
                    l.dispose();
                }
            });

            traChinese.addActionListener(new ActionListener() {
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e) {

                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("zh", "TW"));
                    p.put("language", "zh");
                    p.put("country", "TW");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

                    System.out.println(p.get("language", "zh"));
                    Andie.setLanguage();
                    // System.out.println(Andie.bundle.getString("EnterFilterRadius"));
                    l.dispose();

                }

            });
            l.setVisible(true);
        }
    }
}