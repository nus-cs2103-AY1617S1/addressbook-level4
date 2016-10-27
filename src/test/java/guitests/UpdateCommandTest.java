package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.UpdateCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class UpdateCommandTest extends TaskManagerGuiTest {
	@Test
    public void update() throws IllegalValueException {
	    TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // update first task
        assertUpdateSuccess(targetIndex, td.hoon, currentList);

        currentList = TestUtil.replaceTaskFromList(currentList, td.hoon, targetIndex - 1);
        targetIndex = currentList.length;
        
        // update last task
        assertUpdateSuccess(targetIndex, td.ida, currentList);
        
        currentList = TestUtil.replaceTaskFromList(currentList, td.ida, targetIndex - 1);
        targetIndex = 1;

        // add new tags
        Tag tagToAdd = new Tag("urgent");
        commandBox.runCommand("update " + targetIndex + " t/urgent");
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getTags().contains(tagToAdd));
        
        // remove existing tags
        commandBox.runCommand("update " + targetIndex + " rt/urgent");
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(!newTask.getTags().contains(tagToAdd));
        
        // modify open time
        commandBox.runCommand("update " + targetIndex + " s/2 hour later");
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(targetIndex-1);
        TestTask expectedTask = currentList[targetIndex - 1];
        expectedTask.setOpenTime(new DateTime("2 hour later"));
        assertMatching(expectedTask, updatedCard);
        
        // modify close time
        commandBox.runCommand("update " + targetIndex + " c/the day after tomorrow");
        updatedCard = taskListPanel.navigateToTask(targetIndex-1);
        expectedTask = currentList[targetIndex - 1];
        expectedTask.setCloseTime(new DateTime("the day after tomorrow"));
        assertMatching(expectedTask, updatedCard);
        
        // update with no changes
        targetIndex = 1;
        commandBox.runCommand("update " + targetIndex);
        updatedCard = taskListPanel.navigateToTask(targetIndex - 1);
        assertMatching(td.hoon, updatedCard);
        
        // update own task without changing name
        targetIndex = 3;
        commandBox.runCommand("update " + targetIndex + td.carl.getArgs());
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // invalid index
        commandBox.runCommand("update " + (currentList.length+1) + td.ida.getArgs());
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // no index provided
        commandBox.runCommand("update");
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("updates " + currentList.length);
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
