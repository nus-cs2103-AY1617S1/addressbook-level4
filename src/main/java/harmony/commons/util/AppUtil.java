package harmony.commons.util;

import harmony.MainApp;
import javafx.scene.image.Image;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
