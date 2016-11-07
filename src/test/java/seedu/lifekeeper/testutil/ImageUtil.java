package seedu.lifekeeper.testutil;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUtil {
    /**
     * Compares two images pixel by pixel.
     *
     * @param first the first image.
     * @param second the second image.
     * @return true if both images are the same.
     */
    public static boolean compareImages(Image first, Image second) {
        if (first == null || second == null) {
            return !((first == null) ^ (second == null));
        } else {
            BufferedImage imgA = SwingFXUtils.fromFXImage(first, null);
            BufferedImage imgB = SwingFXUtils.fromFXImage(second, null);

            // Returns false if the images are of different size.
            if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
                int width = imgA.getWidth();
                int height = imgA.getHeight();

                // Loop over every pixel of both images.
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        // Compares every pixels for equality.
                        if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }

            return true;
        }
    }
}
