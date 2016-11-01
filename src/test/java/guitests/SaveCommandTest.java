package guitests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.SaveCommand;

public class SaveCommandTest extends TaskManagerGuiTest {

    private static final String DEFAULT_TEST_FILENAME = "/testData.xml";
    private static final String DEFAULT_TEST_FILE_FOLDER = "/test/data/sandbox";
/*
    @Test
    public void save_validDirectory() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("save data/sandbox");
        File fileIsPresent = new File(DEFAULT_TEST_FILE_FOLDER + DEFAULT_TEST_FILENAME);
        if (fileIsPresent.exists()) {
            assertSaveSuccess();
        }
    }
   
    @Test
    public void save_invalidDirectory_fail() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("save abc");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }
/*    
    @Test
    public void save_validFileExtension_fail() throws IllegalArgumentException, IllegalValueException {
               
    }
    
    @Test
    public void save_invalidFileExtension_fail() throws IllegalArgumentException, IllegalValueException {
               
    }
*/
    private void assertSaveSuccess() {
        assertResultMessage(SaveCommand.MESSAGE_SUCCESS);
    }
}
