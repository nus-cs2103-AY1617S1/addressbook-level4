package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.logic.commands.AddCommand;
import seedu.unburden.testutil.TestTask;
import seedu.unburden.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class DoneCommandTest extends ListOfTaskGuiTest {
	
        //@Test
        public void done() throws IllegalValueException {
            
        	// marks the first task as done 
        	TestTask[] currentList = td.getTypicalPersons();
        	int index = 1;
        	assertDoneSuccess(index,currentList );
        	
        	// marks the last task as done 
        	currentList = TestUtil.doneTaskFromList(currentList,index);
        	index = currentList.length;
        	assertDoneSuccess(index,currentList );
        	
        	//marks the task in the middle of the list as done 
        	currentList = TestUtil.doneTaskFromList(currentList,index);
        	index = currentList.length/2;
        	assertDoneSuccess(index,currentList );
        	
        	//If a task is invalid 
        	commandBox.runCommand("done t" + currentList.length + 1);
        	assertResultMessage("The task index is invalid!");
        	
        			
        }
        
        private void assertDoneSuccess(int index, final TestTask[] currentList){
        	TestTask doneTasks = currentList[index - 1];
        	commandBox.runCommand("done t" + index);
        	
        	// checks if the list contains the task which is being marked as done 
        	assertTrue(doneTasks.getDone());
        	

        }
        

}
