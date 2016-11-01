package guitests;

import org.junit.Test;

import seedu.address.logic.commands.SetStorageCommand;

import static org.junit.Assert.assertTrue;

//@@author A0143756Y
public class SetStorageCommandTest extends TaskManagerGuiTest {

    @Test
    public void setStorage() {
    	
    	//Valid folder file path: Canonical file path, valid file name
    	
    	//Valid folder file path: Absolute file path
    	
    	//
    	
    	//and valid file name
    	
    	assertSetStorageCommandSuccess();
    	
    	
    	//Storage location has previously been set
    	
    	assertResultMessage(SetStorageCommand.MESSAGE_STORAGE_PREVIOUSLY_SET);
    	
    	//Invalid folder file path: Invalid file path
    	
    	
    	assertResultMessage(SetStorageCommand.MESSAGE)
    	
    	//Invalid folder file path: Folder does not exist
    	
    	//Invalid folder file path: File path does not navigate to a folder/ directory
    	
    	//Invalid file name: 
    	
    	//Invalid file name: File with identical name exists in user-specified folder
    
    	
    	
    	
    	
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertSetStorageCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.hoon));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertSetStorageCommandSuccess();
    }

    private void assertSetStorageCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
}
