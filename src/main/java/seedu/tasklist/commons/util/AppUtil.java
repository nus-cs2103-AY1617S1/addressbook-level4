package seedu.tasklist.commons.util;

import javafx.scene.image.Image;
import seedu.tasklist.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
