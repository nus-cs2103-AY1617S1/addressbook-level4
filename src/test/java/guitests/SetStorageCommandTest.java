package guitests;

import org.junit.Test;

import seedu.address.logic.commands.SetStorageCommand;

import static org.junit.Assert.assertTrue;

//@@author A0143756Y
public class SetStorageCommandTest extends TaskManagerGuiTest {

    @Test
    public void setStorage() {
    	
    	//Valid folder file path and valid file name
    
    	assertSetStorageCommandSuccess();
    	
    	//Storage location has previously been set
    	
    	assertResultMessage(SetStorageCommand.MESSAGE_STORAGE_PREVIOUSLY_SET);
    	
    	//Invalid folder file path
    	
    	assertResultMessage(SetStorageCommand.MESSAGE_INVALID_PATH_EXCEPTION);
    	
    	//Folder specified by user does not exist
    	
    	assertResultMessage(SetStorageCommand.MESSAGE_FOLDER_DOES_NOT_EXIST);
    	
    	//Folder file path given does not navigate to a folder/ directory
    	
    	assertResultMessage(SetStorageCommand.MESSAGE_FOLDER_NOT_DIRECTORY);
    	
    	//Invalid file name
    	
    	assertResultMessage(SetStorageCommand.)
    	
    	//File with identical name exists in folder
    
 
    	
    	
    	
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
