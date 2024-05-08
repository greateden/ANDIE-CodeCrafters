package cosc202.andie;

import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


/**
 * <p>
 * Actions provided by the Action menu.
 * </p>
 *
 * <p>
 * The Macro menu contains actions that could save all
 * the operations performed between the start and stop actions.
 * Could also apply an ops file on another image to preform
 * the operations form the opened ops file
 *
 *</p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 *
 */
public class MacroActions {

    protected ArrayList<Action> actions;
    /** The file where the operation sequence is stored. */

    public ResourceBundle bundle = Andie.bundle;


    /**
     * <p>
     * Create a set of Macro menu actions.
     * </p>
     */
    public MacroActions() {

        actions = new ArrayList<Action>();
        actions.add(new StartAction("Start", null, "Start the Macro Action", null));
        actions.add(new StopAction("Stop", null, Andie.bundle.getString("Redo"),
                null));
        actions.add(new ApplyPrevMacroAction("Apply ops", null, Andie.bundle.getString("Redo"),
                null));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu macroMenu = new JMenu("Macro");

        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
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
     * Create a macro start recording action.
     * </p>
     *
     */
    public class StartAction extends ImageAction {

        StartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    EditableImage.recordingStart = true;
                    System.out.println("Start Recording");
                    // target.getImage().recordOperations(op);
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                    }
                }
            } else {
                //System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }
    }


     /**
     * <p>
     * Create a macro stop recording action.
     * </p>
     *
     * This class peforms a stop action of the macro action
     * and save the operations to a new file
     *
     */
    public class StopAction extends ImageAction {


        StopAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
          if(EditableImage.recordingStart == true||EditableImage.macroStack.empty()==true){
            try {
                // make a JOption to let user choose if they needs to
                // yes: save the oerations or
                // cancel: keep adding some other operations
                // no: delete all operations and starts again
                int opsSaveOrNot = JOptionPane.showConfirmDialog(null,
                        "Would you like to save the operations as an ops file?", "Save the Macro Operations",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if (opsSaveOrNot == JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), "OPS File Save Cancled, You Could Continue Adding Operations",
                    "" , JOptionPane.WARNING_MESSAGE);

                }

                else if (opsSaveOrNot == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), "OPS File Save Cancled, All the Operations Discarded,You Colud STart Agian and Add New Operaions",
                    "" , JOptionPane.WARNING_MESSAGE);
                     EditableImage.macroStack.clear();
                     EditableImage.recordingStart = false;

                }

                // if user click ok, then let the user choose a file to save the ops file
                else if (opsSaveOrNot == JOptionPane.OK_OPTION) {


                    JFileChooser fileChooser = new JFileChooser();

                    // Add file filters for OPS format only
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("OPS Files (*.ops)", "ops");

                    fileChooser.addChoosableFileFilter(filter);
                    fileChooser.setFileFilter(filter);

                    //show the save window
                    int chosed = fileChooser.showSaveDialog(target);
                    if (chosed == JFileChooser.APPROVE_OPTION) {
                        try {
                             File fileToSave = fileChooser.getSelectedFile();

                            // Append ".ops" extension if not already present
                            String filePath = fileToSave.getAbsolutePath();
                            if (!filePath.toLowerCase().endsWith(".ops")) {
                                System.out.println("no ops extension added");

                                filePath += ".ops";
                            }

                            // Rename the file with the enforced extension
                            File renamedFile = new File(filePath);
                            fileToSave.renameTo(renamedFile);
                            System.out.println("File saved as: " + renamedFile.getAbsolutePath());


                            EditableImage.isOpsNotEmptyStatus = false;
                            FileOutputStream fileOut = new FileOutputStream(filePath);
                            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                            objOut.writeObject(EditableImage.macroStack);
                            objOut.close();
                            fileOut.close();

                            // create a message box to tell user it's saved successfully
                            JOptionPane.showMessageDialog(Andie.getFrame(), "OPS File Saved",
                                    Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("MyDearUser"),
                                    Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                        }

                        EditableImage.recordingStart = false;
                        EditableImage.macroStack.clear();// empty the stack after save the ops file
                        // }else if(chosed==JFileChooser.CANCEL_OPTION){

                        // }else if(chosed==JFileChooser.ERROR_OPTION ){

                        // }
                        // target.getImage().;
                        target.repaint();
                        target.getParent().revalidate();
                    }
                }
            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                }
            }

        }else{
            JOptionPane.showMessageDialog(null,"Not Able to Stop without Start Macro First, and Do Some Operations",
            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

        }

    }
    }

    public class ApplyPrevMacroAction extends ImageAction {

        ApplyPrevMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if(FileActions.isOpened == true){

        try{

                // System.out.println("Select ops file");
                JFileChooser fileChooser = new JFileChooser();

                // Show open dialog
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();

                    // try{
                    FileInputStream fileinput = new FileInputStream(opsFilepath);
                    try (ObjectInputStream obInput = new ObjectInputStream(fileinput)) {
                        @SuppressWarnings("unchecked")
                        Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) obInput.readObject();


                        for (int i = 0; i < opsFromFile.size(); i++) {
                            target.getImage().apply(opsFromFile.get(i));
                            target.repaint();
                            target.getParent().revalidate();
                        }
                        // }catch(){
                    }



                    // }

                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Operation canceled.");
                }

            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                }
            }

    }else{
         //System.out.println("Exception");
         JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
         Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

    }

    }
    }
}
