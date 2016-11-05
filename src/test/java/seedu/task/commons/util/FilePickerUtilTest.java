package seedu.task.commons.util;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import javafx.stage.FileChooser;

public class FilePickerUtilTest {

    @Test
    public void prepare_chooser_successfullyPrepares() {
        FileChooser chooser = FilePickerUtil.prepareFileChooser();
        chooser.setTitle("Open Data File"); // Set FileChooser window title
        assertEquals(new File("."), chooser.getInitialDirectory());
        assertEquals("backup.xml", chooser.getInitialFileName());
        assertEquals("Open Data File", chooser.getTitle());
    }
}
