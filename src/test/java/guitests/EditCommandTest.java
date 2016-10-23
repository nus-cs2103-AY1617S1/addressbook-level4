package guitests;


import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.EditCommand;
import seedu.ggist.model.task.*;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.ggist.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class EditCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void edit() throws IllegalArgumentException, IllegalValueException {
		//edit the first in the list, edit task name
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String newContent = "edited name";
        String type = "task";
        TestTask taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setTaskName(new TaskName(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);

        //edit the last in the list, edit time 
        targetIndex = currentList.length;
        newContent = "10:00";
        type = "start time";
        taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setStartTime(new TaskTime(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);

        //edit from the middle of the list, edit date
        targetIndex = currentList.length/2 == 0 ? 1: currentList.length/2;
        newContent = "tomorrow";
        type = "end date";
        taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setEndDate(new TaskDate(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);

        
        //edit priority
        targetIndex = 1;
        newContent = "med";
        type = "priority";
        taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setPriority(new Priority(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        
        //invalid command format
        commandBox.runCommand("edit task 1 wrong");
        assertResultMessage("Invalid task format!");

        //invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + "task invalid");
        assertResultMessage("The task index provided is invalid");
        
        //invalid type
        commandBox.runCommand("edit 1 name bug");
        assertResultMessage("Invalid task format!");
        
	}
	
	private void assertEditSuccess(int targetIndexOneIndexed, String type, String content, TestTask taskToEdit) throws IllegalArgumentException, IllegalValueException {
    
        commandBox.runCommand("edit " + targetIndexOneIndexed + type + content);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}