package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import seedu.address.logic.commands.UpdateCommand;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import seedu.address.model.task.*;

public class UpdateCommandTest extends AddressBookGuiTest {
	@Test
    public void update() {
	    TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // update first task
        assertUpdateSuccess(targetIndex, td.hoon, currentList);

        currentList = TestUtil.replaceTaskFromList(currentList, td.hoon, targetIndex);
        targetIndex = currentList.length;
        
        // update last task
        assertUpdateSuccess(targetIndex, td.ida, currentList);
        
        currentList = TestUtil.replaceTaskFromList(currentList, td.ida, targetIndex);
        targetIndex = 1;
        
        // update only tags
        commandBox.runCommand("update " + targetIndex + " t/urgent");
        
        // update with no changes
        commandBox.runCommand("update " + targetIndex);
        
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(targetIndex-1);
        assertMatching(td.hoon, updatedCard);
        
        // update own task without changing name
        targetIndex = 3;
        commandBox.runCommand("update " + targetIndex + td.carl.getArgs());
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // update task to a name that is duplicated
        targetIndex = 2;
        commandBox.runCommand("update " + targetIndex + td.carl.getArgs());
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // invalid index
        commandBox.runCommand("update " + (currentList.length+1) + td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // no index provided
        commandBox.runCommand("update");
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("updates "+ currentList.length);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
	
	private void assertUpdateSuccess(int targetIndex, TestTask taskToUpdate, TestTask... currentList) {
		commandBox.runCommand("update " + targetIndex + taskToUpdate.getArgs() );
		
		//confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(targetIndex-1);
        assertMatching(taskToUpdate, updatedCard);

        TestTask[] expectedList = TestUtil.addTasksToList(currentList);
        
        // merge tags
        taskToUpdate.getTags().mergeFrom(expectedList[targetIndex - 1].getTags());
        
        expectedList[targetIndex - 1] = taskToUpdate;
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }
	
	
}
