package cosc202.andie;

import java.util.*;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

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
 * @modified_by Eden the Greatest
 * @modified_date 15 MAR 2024
 */
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    protected boolean isOpened = false;
    protected boolean isSaved = false;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction("Open (O)", null, "Open a file", Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction("Save (S)", null, "Save the file", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileExportAction("Export (E)", null, "Export an image without the .ops file",
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction("Exit (Q)", null, "Exit the program", Integer.valueOf(KeyEvent.VK_Q)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");

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

            EditableImage ei = new EditableImage();

            if (isOpened == true && !ei.isOpsEmpty()) {
                // do u wanna save it
                // JPanel panel = new JPanel();
                // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // JLabel label = new JLabel("Do you want to save the file before open another
                // file?");
                // label.setAlignmentX(Component.CENTER_ALIGNMENT);

                // JPanel buttonPanel = new JPanel();
                // buttonPanel.add(new JButton("Cancel (C)"));
                // buttonPanel.add(new JButton("No (N)"));
                // buttonPanel.add(new JButton("Yes (Y)"));

                // panel.add(label);
                // panel.add(buttonPanel);

                // int option = JOptionPane.showOptionDialog(null, panel, "Color Selection",
                // JOptionPane.OK_CANCEL_OPTION,
                // JOptionPane.PLAIN_MESSAGE, null, null, null);

                Object[] options = { "Yes (Y)", "No (N)", "Cancel (C)" };

                int n = JOptionPane.showOptionDialog(null,
                        "Do you want to save the file before open another file?",
                        "Warning",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (n == 0) {
                    FileSaveAction saveAction = new FileSaveAction("Save", null, "Save",
                            Integer.valueOf(KeyEvent.VK_A));
                    saveAction.actionPerformed(e);
                } else if (n == 1) {
                    openFile();
                } else {
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
                    "All Supported File Types", "jpg", "jpeg", "gif", "tif", "tiff", "png", "bmp", "wbep");
            fileChooser.setFileFilter(filterAllTypes);

            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                    isOpened = true;
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
        // TODO Add the real export function here!

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
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

            // Add file filters for different image formats
            fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", "Joint Photographic Experts Group"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", "Tagged Image File Format"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", "Portable Network Graphics"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", "Bitmap Image File"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", "WebP Image File"));
            fileChooser
                    .addChoosableFileFilter(new ImageFileFilter("GIF", "The file type that you mainly used for memes"));

            if (isOpened == true) {
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
                } else {
                    JOptionPane.showMessageDialog(null, "With all due respect, you didn't open anything.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
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
                // if hasn't been saved, create a new instance of Save As and call the
                // actionPerformed() method
                if (isSaved == false) {
                    FileSaveAsAction saveAsAction = new FileSaveAsAction("Save As", null, "Save a copy",
                            Integer.valueOf(KeyEvent.VK_A));
                    saveAsAction.actionPerformed(e);
                    isSaved = true;
                } else {
                    target.getImage().save();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "With all due respect, you didn't open anything.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
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

            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

            // Add file filters for different image formats
            fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", "Joint Photographic Experts Group"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", "Tagged Image File Format"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", "Portable Network Graphics"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", "Bitmap Image File"));
            fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", "WebP Image File"));
            fileChooser
                    .addChoosableFileFilter(new ImageFileFilter("GIF", "The file type that you mainly used for memes"));

            if (isOpened == true) {
                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        // Get the selected file filter
                        FileFilter selectedFilter = fileChooser.getFileFilter();
                        String format = ((ImageFileFilter) selectedFilter).getExtension();
                        target.getImage().saveAs(imageFilepath);
                        isSaved = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "What did you just do my dear user...",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "With all due respect, you didn't open anything.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
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
}