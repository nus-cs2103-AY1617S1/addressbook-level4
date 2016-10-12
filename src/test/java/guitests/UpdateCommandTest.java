package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import seedu.address.logic.commands.UpdateCommand;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import seedu.address.model.task.*;

public class UpdateCommandTest extends AddressBookGuiTest {

	
	@Test
    public void update() {
      
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        /*TestTask taskToUpdate=td.hoon;
        assertUpdateSuccess(targetIndex,taskToUpdate,currentList);
        currentList[targetIndex-1]=taskToUpdate;
        currentList=TestUtil.addTasksToList(currentList);*/
       
        //edit with duplicate task
        targetIndex=3;
        commandBox.runCommand("update "+targetIndex+td.alice.getArgs());
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));   
        
       //invalid command
        commandBox.runCommand("updatee "+td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
       //invalid index
        commandBox.runCommand("update " + (currentList.length+1) + td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //edit in an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("update "+targetIndex+td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //invalid command
        commandBox.runCommand("updatee "+td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
  
    }
	
	private void assertUpdateSuccess(int targetIndex,TestTask taskToUpdate, TestTask... currentList) {
		commandBox.runCommand("update " + targetIndex + taskToUpdate.getArgs() );
		
		//confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(targetIndex-1);
        assertMatching(taskToUpdate, updatedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        expectedList[targetIndex-1]=taskToUpdate;
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }
	
	
}
