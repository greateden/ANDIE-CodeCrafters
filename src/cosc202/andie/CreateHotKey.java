package cosc202.andie;

import javax.swing.*;
import java.awt.event.*;

/**
 * A class that creates hotkeys for the menus in the menuBar.
 * 
 * Due to lazyness in reconstructing ImageAction, the Greatest
 * Eden decided to use this class in two ways:
 * 
 * 0. The programmers always love the way number zero;
 * 
 * 1. To create hotkeys for the menus in the menuBar - hot keys that
 * when user press a single key (e.g., z, x, e);
 * 
 * 2. To create hotkeys for the options/functions in each menus - hot
 * keys that when user press a combination of keys (e.g., ctrl/cmd + z,
 * ctrl + x, ctrl + e);
 * 
 * By the way! the hot key constructor in ImageAction is still in use!
 * Its function is more or less like way number one.
 * 
 * If someone wollen zu recontruct these whole scheiße, ben, bonne chance
 * with dealing with those spaghetti codes!
 * 
 * Some codes are generated by Copilot in this method, not due to lazyness.
 * 
 * @author Eden(Yitian) Li
 * @version 1.0
 */
public class CreateHotKey {
    /**
     * A method that does way number 1 above.
     * 
     * @param menu      the specific menu that the hotkey is for
     * @param key       the key that the hotkey is for
     * @param modifiers the modifiers that the hotkey is for
     * @param name      the name of the hotkey
     */
    public static void createHotkey(JMenu menu, int key, int modifiers, String name) {
        Andie.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(key, modifiers), name);
        Andie.frame.getRootPane().getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.doClick(); // Simulate a click on the menu
            }
        });
    }

    /**
     * A method that does way number 2 above.
     * 
     * @param action      the specific action that the hotkey is for
     * @param key       the key that the hotkey is for
     * @param modifiers the modifiers that the hotkey is for
     * @param name      the name of the hotkey
     */
    public static void createHotkey(Action action, int key, int modifiers, String name) {
        Andie.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(key, modifiers), name);
        Andie.frame.getRootPane().getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e); // Simulate a click on the menu
            }
        });
    }

    /**
     * A method that does creating hotkeys for JMenuItem.
     * 
     * @param item      the specific item that the hotkey is for
     * @param key       the key that the hotkey is for
     * @param modifiers the modifiers that the hotkey is for
     * @param name      the name of the hotkey
     */
    public static void createHotkey(JMenuItem item, int key, int modifiers, String name) {
        Andie.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(key, modifiers), name);
        Andie.frame.getRootPane().getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.doClick(); // Simulate a click on the menu
            }
        });
    }

}

