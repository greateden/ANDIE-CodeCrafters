

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEditor extends JPanel {
    private Image backgroundImage;
    private Rectangle selectionRect;
    private Point startPoint;
    private Point endPoint;
    private boolean isSelecting;
    private int rotationAngle = 0;

    public ImageEditor() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = startPoint;
                isSelecting = true;
                rotationAngle = 0; // Reset rotation angle
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isSelecting = false;
                selectionRect = new Rectangle(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                repaint();
            }
        });

        // Rotate the border pattern every 100 milliseconds
        Timer timer = new Timer(100, e -> {
            if (!isSelecting) {
                rotationAngle -= 1; // Decrease rotation angle (clockwise rotation)
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }

        if (isSelecting || selectionRect != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);

            int x1 = Math.min(startPoint.x, endPoint.x);
            int y1 = Math.min(startPoint.y, endPoint.y);
            int x2 = Math.max(startPoint.x, endPoint.x);
            int y2 = Math.max(startPoint.y, endPoint.y);
            int width = Math.abs(x2 - x1);
            int height = Math.abs(y2 - y1);

            // Draw the outer border
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x1, y1, width, height);

            // Draw alternating black and white segments on the border
            for (int i = 0; i < width; i++) {
                if (((i + rotationAngle) / 8) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.drawLine(x1 + i, y1, x1 + i, y1 + 1);
                g2d.drawLine(x1 + i, y1 + height - 1, x1 + i, y1 + height);
            }
            for (int i = 0; i < height; i++) {
                if (((i + rotationAngle) / 8) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.drawLine(x1, y1 + i, x1 + 1, y1 + i);
                g2d.drawLine(x1 + width - 1, y1 + i, x1 + width, y1 + i);
            }
        }
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Editor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            ImageEditor imageEditor = new ImageEditor();
            frame.add(imageEditor, BorderLayout.CENTER);

            // Create a menu bar with an "Open" option
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem openMenuItem = new JMenuItem("Open");
            openMenuItem.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Image image = new ImageIcon(fileChooser.getSelectedFile().getPath()).getImage();
                    imageEditor.setBackgroundImage(image);
                }
            });
            fileMenu.add(openMenuItem);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);

            frame.setVisible(true);
        });
    }
}
