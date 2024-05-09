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
 * Actions provided by the Edit menu.
 * </p>
 *
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class EditActions {

    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    public ResourceBundle bundle = Andie.bundle;

    private Action undo, redo;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();

        undo = new UndoAction(Andie.bundle.getString("Undo"), null, Andie.bundle.getString("Undo"),
                Integer.valueOf(KeyEvent.VK_U));
        actions.add(undo);
        CreateHotKey.createHotkey(undo, KeyEvent.VK_Z, InputEvent.META_DOWN_MASK, "Undo");

        redo = new RedoAction(Andie.bundle.getString("Redo"), null, Andie.bundle.getString("Redo"),
                Integer.valueOf(KeyEvent.VK_R));
        actions.add(redo);
        CreateHotKey.createHotkey(redo, KeyEvent.VK_Z, InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, "Redo");
        CreateHotKey.createHotkey(redo, KeyEvent.VK_Y, InputEvent.META_DOWN_MASK, "Redo");

        Action changeTheme = new ChangeThemeAction("Change Theme", null, Andie.bundle.getString("Redo"),
                Integer.valueOf(KeyEvent.VK_C));
        actions.add(changeTheme);
    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(Andie.bundle.getString("Edit"));

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     */
    public void changeCertainMenuStatus(boolean status) {
        undo.setEnabled(status);
        redo.setEnabled(status);
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     *
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().undo();
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     *
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().redo();
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrLastStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * Represents an action to change the theme of the application.
     *
     * @author GreatestEden
     * @version 1.0
     */
    public class ChangeThemeAction extends ImageAction {

        private JCheckBox followOSTheme;
        private JComboBox<String> themeSelector;
        private JButton okButton, cancelButton;

        /**
         * Create a new change theme action.
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChangeThemeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Performs the action of changing the theme.
         *
         * @param event The action event that triggered this action.
         */
        public void actionPerformed(ActionEvent event) {
            try {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                followOSTheme = new JCheckBox("Follow OS theme");
                JLabel label = new JLabel("Theme:");
                String[] themes = { "FlatLightLaf", "FlatMacDarkLaf", "FlatMacLightLaf", "FlatDarkLaf",
                        "FlatIntelliJLaf",
                        "FlatDarculaLaf" };
                themeSelector = new JComboBox<>(themes);
                cancelButton = new JButton("Cancel");
                okButton = new JButton("OK");

                // Listener for "Follow OS theme" checkbox
                followOSTheme.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        themeSelector.setEnabled(false);
                        // Do something when "Follow OS theme" is selected
                    } else {
                        themeSelector.setEnabled(true);
                    }
                });

                // Listener for OK button
                okButton.addActionListener(e -> {
                    ThemeConfig.SetTheme(followOSTheme.isSelected(), (String) themeSelector.getSelectedItem());
                    Andie.createTheme();
                    JOptionPane.showMessageDialog(Andie.getFrame(),
                            "New theme will be applied next time when the program runs.",
                            Andie.bundle.getString("Warning"), 1); // Handle OK button click
                });

                // Listener for Cancel button
                cancelButton.addActionListener(e -> {
                    frame.dispose();
                    // Handle Cancel button click
                });

                panel.setLayout(new GridLayout(3, 2));
                panel.add(followOSTheme);
                panel.add(new JLabel());
                panel.add(label);
                panel.add(themeSelector);
                panel.add(cancelButton);
                panel.add(okButton);

                frame.add(panel);
                frame.setSize(300, 200);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception exc) {
                System.out.println(exc.toString());
            }
        }

    }

}
