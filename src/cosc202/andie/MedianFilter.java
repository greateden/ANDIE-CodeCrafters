package cosc202.andie;

import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * The median filter takes all of the pixel values in a local neighbourhood and
 * sorts them. The new pixel value is the middle value
 * from the sorted list.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    private final int NUM_THREADS = 8;

    /**
     * <p>
     * Construct a median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter.
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {

        if (radius == 0) {
            return input;
        }

        int side = 2 * radius + 1; // The side of the kernel using the radius.
        int kernelWidth = side;
        int kernelHeight = side;

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);

        JFrame f = new JFrame("Progress bar");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(200, 150);
        JPanel c = new JPanel();
        c.setLayout(new BorderLayout());

        f.setContentPane(c);

        JDialog progressDialog = new JDialog(f, "progress", true);
        // JProgressBar progressBar = new JProgressBar(0,100);
        JProgressBar progressBar = new JProgressBar(0, input.getHeight());

        progressBar.setValue(progressBar.getMinimum());
        progressBar.setStringPainted(true);
        // Ask dems about variables accessed in inner class needing to be declared final

        SwingWorker<BufferedImage, Integer> worker = new SwingWorker<BufferedImage, Integer>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {

                Thread[] threads = new Thread[NUM_THREADS];

                for (int i = 0; i < NUM_THREADS; i++) {
                    final int threadIndex = i;

                    threads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Calculate number of rows each thread will process
                            int rowsPerThread = input.getHeight() / NUM_THREADS;
                            int remainingRows = input.getHeight() % NUM_THREADS;

                            // Arrays to hold the argb elements in a kernel; size determined by given
                            // radius.
                            int[] rMedian = new int[kernelWidth * kernelHeight];
                            int[] gMedian = new int[kernelWidth * kernelHeight];
                            int[] bMedian = new int[kernelWidth * kernelHeight];
                            int[] aMedian = new int[kernelWidth * kernelHeight];

                            int argb; // argb values for transformation.

                            // got from stack overflow
                            // Calculate start and end rows for this thread
                            int startRow = threadIndex * rowsPerThread;
                            int endRow = (threadIndex + 1) * rowsPerThread;
                            if (threadIndex == NUM_THREADS - 1) {
                                // Last thread processes remaining rows
                                endRow += remainingRows;
                            }
                            // stackoverflow codes end

                            for (int i = startRow; i < endRow; i++) {

                                for (int j = 0; j < input.getWidth(); j++) {
                                    int a1 = 0; // Counter to help fit a square kernel into a 1-D array.
                                    for (int k = i - side / 2; k < i + side / 2; k++) {
                                        for (int l = j - side / 2; l < j + side / 2; l++) {
                                            if (k >= 0 && k < input.getHeight() && l >= 0 && l < input.getWidth()) {
                                                // Taken from MeanFilter.
                                                argb = input.getRGB(l, k);
                                                int a = (argb & 0xFF000000) >> 24;
                                                int r = (argb & 0x00FF0000) >> 16;
                                                int g = (argb & 0x0000FF00) >> 8;
                                                int b = (argb & 0x000000FF);

                                                // Filling the arrays.
                                                rMedian[a1] = r;
                                                gMedian[a1] = g;
                                                bMedian[a1] = b;
                                                aMedian[a1] = a;

                                                // Incrementing the counter.
                                                a1++;
                                            }
                                        }
                                    }

                                    // Ok. Time to sort these arrays and see what happens.
                                    Arrays.sort(rMedian);
                                    Arrays.sort(gMedian);
                                    Arrays.sort(bMedian);
                                    Arrays.sort(aMedian);

                                    // Find the middle? Must be the radius probably.
                                    int nr = rMedian[radius];
                                    int ng = gMedian[radius];
                                    int nb = bMedian[radius];
                                    int na = aMedian[radius];

                                    // Apply the filter now.
                                    argb = (na << 24) | (nr << 16) | (ng << 8) | nb;
                                    output.setRGB(j, i, argb);

                                    // Update the progress bar
                                    // progressBar.setValue(i);
                                    // publish(i);
                                }
                               //publish(i);
                            }
                            
                        }

                    });// end of threads

                    threads[i].start();
                }
                // got from stack overflow
                for (int i = 0; i < NUM_THREADS; i++) {
                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return output;
            }// End of doInBackground()

            // stackoverflow codes end

            @Override
            protected void process(java.util.List<Integer> chunks) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                progressDialog.dispose();
            }
        };
        worker.execute();
        progressDialog.add(progressBar);
        progressDialog.pack();
        progressDialog.setLocationRelativeTo(Andie.getFrame());
        // progressDialog.setLocationByPlatform(true);
        progressDialog.setVisible(true);
        return output;

    }
}
