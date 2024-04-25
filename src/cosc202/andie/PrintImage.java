package cosc202.andie;

import java.awt.*;
import java.awt.Graphics;
import java.awt.print.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JOptionPane;

/**
 * <p>
 * Prints the current image.
 * </p>
 * 
 * @author The Greatest Eden
 */
public class PrintImage implements Printable {
    private BufferedImage imageToPrint;
    private boolean fitWholePage = false;

    /**
     * Constructor
     * @param fitWholePage Whether to fit the whole page or not
     */
    public PrintImage(boolean fitWholePage) {
        this.imageToPrint = EditableImage.getCurrentImage();
        this.fitWholePage = fitWholePage;
    }

    /**
     * <p>
     * Callback for when the file print action is triggered.
     * </p>
     * 
     * Some of the code below is from Perplexity
     * (https://www.perplexity.ai/search/if-I-have-C1ciqa8pQ0aJ4vP5C1Mz6g)
     * 
     * @param g graphics
     * @param pf page format
     * @param pageIndex page index
     * @return different status of the printing - we don't need this here
     * @throws PrinterException what if sonething really happens
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        if (fitWholePage) {
            // Scale the image to fit the page
            double scaleWidth = pf.getImageableWidth() / imageToPrint.getWidth();
            double scaleHeight = pf.getImageableHeight() / imageToPrint.getHeight();
            double scale = Math.min(scaleWidth, scaleHeight);

            g2d.drawImage(imageToPrint, 0, 0, (int) (imageToPrint.getWidth() * scale),
                    (int) (imageToPrint.getHeight() * scale), null);
        } else {
            g2d.drawImage(imageToPrint, 0, 0, imageToPrint.getWidth(), imageToPrint.getHeight(), null);
        }
        return PAGE_EXISTS;
    }

    /**
     * Prints the image.
     */
    public void printImage() {

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        job.printDialog();//why????? I have totally no idea about why we need this
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                                Andie.bundle.getString("Error"),
                                JOptionPane.WARNING_MESSAGE);
            }
            // Handle printing exception
        }
    }
}
