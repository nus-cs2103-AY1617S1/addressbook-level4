package guitests;

import guitests.guihandles.TaskCardHandle;

import static org.junit.Assert.*;
import static seedu.task.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestTasks;

public class UpdateCommandTest extends TaskManagerGuiTest {
	@Test
    public void update() throws IllegalValueException {
	    TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // update first task with details that will put it to the back
        assertUpdateSuccess(targetIndex, currentList.length, TypicalTestTasks.ida, currentList);

        currentList = TestUtil.moveTaskInList(currentList, targetIndex - 1, currentList.length - 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.ida, currentList.length - 1);
        targetIndex = currentList.length;
        
        // update last task with details that will put it to the front
        assertUpdateSuccess(targetIndex, 1, TypicalTestTasks.first, currentList);
        
        currentList = TestUtil.moveTaskInList(currentList, targetIndex - 1, 0);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.first, 0);
        targetIndex = 1;

        // add new tags
        Tag tagToAdd = new Tag("urgent");
        commandBox.runCommand("update " + targetIndex + " tag urgent");
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getTags().contains(tagToAdd));
        
        // remove existing tags
        commandBox.runCommand("update " + targetIndex + " remove-tag urgent");
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask.getTags().contains(tagToAdd));
        
        // update with no changes
        targetIndex = 4;
        commandBox.runCommand("update " + targetIndex);
        updatedCard = taskListPanel.navigateToTask(targetIndex - 1);
        assertMatching(TypicalTestTasks.fiona, updatedCard);
        
        // update own task without changing name
        targetIndex = 3;
        commandBox.runCommand("update " + targetIndex + " name"+ TypicalTestTasks.carl.getArgs());
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // invalid index
        commandBox.runCommand("update " + (currentList.length+1) + " name"+ TypicalTestTasks.ida.getArgs());
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
	
	private void assertUpdateSuccess(int targetIndex, int newIndex, TestTask taskToUpdate, TestTask... currentList) {
		commandBox.runCommand("update " + targetIndex + " name"+ taskToUpdate.getArgs() );
		
		//confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(newIndex - 1);
        assertMatching(taskToUpdate, updatedCard);

        TestTask[] expectedList = TestUtil.moveTaskInList(currentList, targetIndex - 1, newIndex - 1);
        
        // merge tags
        taskToUpdate.getTags().mergeFrom(expectedList[newIndex - 1].getTags());
        
        expectedList[newIndex - 1] = taskToUpdate;
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }
	
	
}
