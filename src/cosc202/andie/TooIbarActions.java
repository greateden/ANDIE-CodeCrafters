package cosc202.andie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Yes it's Too[I]barActions.
 */
public class TooIbarActions {

    private JToolBar toolbar;

    public TooIbarActions() {
        toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);

        // Create buttons
        JButton openButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/open-file stockes_02.png", 20, 20));
        JButton saveButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/diskette Yogi Aprelliyanto.png", 20, 20));
        JButton exportButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/export Freepik.png", 20, 20));
        JButton printButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/printer Freepik.png", 20, 20));

        JButton undoButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/undo-arrow Dave Gandy.png", 20, 20));
        JButton redoButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/redo-arrow Dave Gandy.png", 20, 20));

        JButton selectButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/selection-box Saepul Nahwan.png", 20, 20));
        JButton cropButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/crop google.png", 20, 20));
        // JButton drawButton = new JButton(new ImageIcon("/Users/liyitian/icon/"));

        JButton zoomInButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/zoom zafdesign.png", 20, 20));
        JButton zoomOutButton = new JButton(resizeAndAdjustIcon("/Users/liyitian/icon/zoom-out zafdesign.png", 20, 20));

        openButton.setToolTipText("Open");
        saveButton.setToolTipText("Save");
        exportButton.setToolTipText("Export");
        printButton.setToolTipText("Print");

        undoButton.setToolTipText("Undo");
        redoButton.setToolTipText("Redo");

        selectButton.setToolTipText("Select");
        cropButton.setToolTipText("Crop");

        zoomInButton.setToolTipText("Zoom In");
        zoomOutButton.setToolTipText("Zoom Out");

        // Add action listeners to buttons
        cropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement crop functionality here
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement select functionality here
            }
        });

        // Add buttons to toolbar
        toolbar.add(openButton);
        toolbar.add(saveButton);
        toolbar.add(exportButton);
        toolbar.add(printButton);
        toolbar.addSeparator();

        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.addSeparator();

        toolbar.add(selectButton);
        toolbar.add(cropButton);
        toolbar.addSeparator();

        toolbar.add(zoomInButton);
        toolbar.add(zoomOutButton);

        // Add action listeners to buttons
        cropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement crop functionality here
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement select functionality here
            }
        });
    }

    private static ImageIcon resizeAndAdjustIcon(String iconPath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(iconPath));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JToolBar createToolBar() {
        return toolbar;
    }

}// End of Class
