package guitests;

import org.junit.Test;

import seedu.taskmanager.TestApp;
import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.logic.commands.SaveCommand;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0143641M
public class SaveCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() throws DataConversionException {

        String defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;
        Config currentConfig = ConfigUtil.readConfig(defaultConfigFilePath).orElse(new Config());
        String currentFilePath = currentConfig.getTaskManagerFilePath();
        
        //verify can save to another location when list is not empty
        assertTrue(shortItemListPanel.isListMatching(td.getTypicalItems()));
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + TestUtil.getFilePathInSandboxFolder("new.xml"));
        assertSaveCommandSuccess(TestUtil.getFilePathInSandboxFolder("new.xml"));
        
        //verify can save to previously specified location (the default save location for testing)
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + TestApp.SAVE_LOCATION_FOR_TESTING);
        assertSaveCommandSuccess(TestApp.SAVE_LOCATION_FOR_TESTING);
        
        //verify cannot save to a non .xml file
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + TestUtil.getFilePathInSandboxFolder("hello.txt"));
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        
        //verify can save to another location when list is empty
        commandBox.runCommand("clear");
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + TestUtil.getFilePathInSandboxFolder("new2.xml"));
        assertSaveCommandSuccess(TestUtil.getFilePathInSandboxFolder("new2.xml"));
        
        // reset file path to original
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + currentFilePath);
    }

    private void assertSaveCommandSuccess(String saveToFilePath) {
        assertDataFilePathChanged(saveToFilePath);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, saveToFilePath));
    }
}
