package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import taskle.commons.core.Messages;
import taskle.logic.commands.AddCommand;
import taskle.logic.commands.EditCommand;
import taskle.testutil.TestTask;
import taskle.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest{


    /**
     * Edits a current task inside the TypicalTestTask to test the edit function.
     */
    @Test
    public void edit_existing_task() {
        //edits one task
        assertEditResultSuccess("1", "Buy Groceries");

    }
    
    /**
     * Edits an inexistent task
     */
    @Test
    public void edit_inexistent_task() {
        assertEditResultFailure("10", "Buy dinner home");
    }
    
    /**
     * Edit task without giving new task name
     */
    private void addTask() {
        
    }
    
    private void assertEditResultSuccess(String taskNumber, String newName) {
        String command = EditCommand.COMMAND_WORD + " " + taskNumber + " " + newName;
        commandBox.runCommand(command);
        assertResultMessage("Edited Task: " + newName);
    }
    
    private void assertEditResultFailure(String taskNumber, String newName) {
        String command = EditCommand.COMMAND_WORD + " " + taskNumber + " " + newName;
        commandBox.runCommand(command);
        assertResultMessage("The task index provided is invalid");
    }
    

}
