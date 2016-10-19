package guitests;

import org.junit.Test;

import seedu.taskmanager.TestApp;
import seedu.taskmanager.logic.commands.SaveCommand;

import static org.junit.Assert.assertTrue;

public class SaveCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        //verify can save to another location when list is not empty
        assertTrue(itemListPanel.isListMatching(td.getTypicalItems()));
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " data/new.xml");
        assertSaveCommandSuccess("data/new.xml");
        
        //verify can save to previously specified location (the default save location for testing)
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + TestApp.SAVE_LOCATION_FOR_TESTING);
        assertSaveCommandSuccess(TestApp.SAVE_LOCATION_FOR_TESTING);
        
        //verify can save to another location when list is empty
        commandBox.runCommand("clear");
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " data/new2.xml");
        assertSaveCommandSuccess("data/new2.xml");
        


    }

    private void assertSaveCommandSuccess(String saveToFilePath) {
        assertDataFilePathChanged(saveToFilePath);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, saveToFilePath));
    }
}
