package seedu.cmdo.commons.util;

import javafx.scene.image.Image;
import seedu.cmdo.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
