//@@author A0135769N
package guitests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static seedu.tasklist.logic.commands.SetStorageCommand.MESSAGE_SUCCESS;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;

public class SetStorageCommandTest extends TaskListGuiTest {
	
	
	@Test
	public void setStorage(){
		
		TestTask[] currentList = td.getTypicalTasks();
		
		String testCase1 = "docs/tasklist.xml";
		String testCase2 = "data/tasklist.xml";
		String testCase3 = "default";
		String testCase4 = null;
		assertStorageSuccess(testCase3);
		assertStorageSuccess(testCase1);
		assertStorageSuccess(testCase2);
		assertStorageSuccess(testCase4);
	}
	
	/**
     * Runs the setstorage command to move the file to the directory specified by the user (as a file path).
     * @param filePath e.g. to validate and move the file from the current position to the final position.
     * @param currentList A copy of the current list of tasks, to check for no loss of data while transferring.
     */
	
	void assertStorageSuccess(String file){
		String filePath;
		if(file==null){		
			assertNull("Null Value Entered", file);
		}
		else{
			if(file.equals("default")){
				filePath = "data/tasklist.xml";
			}
			else{
				filePath = file;
			}
			commandBox.runCommand("setstorage " + filePath);
			assertResultMessage(String.format(MESSAGE_SUCCESS + filePath));
			
      }
	}
	
}
