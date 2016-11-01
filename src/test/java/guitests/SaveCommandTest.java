package guitests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.SaveCommand;
//@@author A0138411N
public class SaveCommandTest extends TaskManagerGuiTest {

    private static final String DEFAULT_TEST_FILENAME = "testData.xml";
    private static final String DEFAULT_TEST_FILE_FOLDER = "./src/test/data/sandbox";

    @Test
    public void save_validDirectory() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("save " + DEFAULT_TEST_FILE_FOLDER);
        assertSaveSuccess(DEFAULT_TEST_FILE_FOLDER + SaveCommand.DEFAULT_FILENAME);
    }
   
    @Test
    public void save_invalidDirectory_fail() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("save abc");
        assertResultMessage(SaveCommand.MESSAGE_FAIL + "\n" + SaveCommand.MESSAGE_USAGE);
    }
    
    @Test
    public void save_validFileExtension_fail() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("save " + DEFAULT_TEST_FILENAME);
        assertSaveSuccess(SaveCommand.DEFAULT_FILE_FOLDER + DEFAULT_TEST_FILENAME);
    }
    
    @Test
    public void save_invalidFileExtension_fail() throws IllegalArgumentException, IllegalValueException {
               
    }

    private void assertSaveSuccess(String filePath) {
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, filePath));
    }
}
