package seedu.address.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.common.io.CharStreams;

import javafx.scene.image.Image;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }
    
    public static String getHtmlString(String htmlPath) throws UnsupportedEncodingException, IOException {
        assert htmlPath != null;
        InputStream input = MainApp.class.getResourceAsStream(htmlPath);
        assert input != null;
        String stringFromStream = CharStreams.toString(new InputStreamReader(input, "UTF-8"));
        System.out.println(stringFromStream);
        return stringFromStream;
    }

}
