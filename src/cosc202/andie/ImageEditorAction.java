package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageEditorAction extends ImageAction {
    private Image backgroundImage;
    private Rectangle selectionRect;
    private Point startPoint;
    private Point endPoint;
    private boolean isSelecting;
    private int rotationAngle = 0;

    public ImageEditorAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your image editor code here.
        // You can refer to the ImageEditor class provided earlier.
        // Remember to associate the functionality and interface elements of the
        // ImageEditor with the ImagePanel.
        // For example, you could have a button or menu item on the ImagePanel that
        // triggers the image editor to start when the user clicks on it.
    }
}
