package seedu.savvytasker.commons.util;

import javafx.scene.image.Image;
import seedu.savvytasker.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
