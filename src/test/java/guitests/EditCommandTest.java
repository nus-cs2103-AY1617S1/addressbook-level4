package guitests;


import org.junit.Test;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.*;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.logic.commands.EditCommand;

import static seedu.ggist.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class EditCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void edit() throws IllegalArgumentException, IllegalValueException {
		//edit the first in the list, edit task name
		TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String newContent = " edited name";
        String type = " task";
        TestTask taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setTaskName(new TaskName(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);

        //edit the last in the list, edit time 
        targetIndex = currentList.length;
        newContent = " 10:00 AM";
        type = " start time";
        taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setStartTime(new TaskTime(newContent));
      //  assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        
        //edit priority
        targetIndex = 1;
        newContent = " med";
        type = " priority";
        taskToBeReplaced = currentList[targetIndex - 1];
        taskToBeReplaced.setPriority(new Priority(newContent));
     //   assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        
        //invalid command format
        commandBox.runCommand("edit task 1 wrong");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        //invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " task invalid");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //invalid type
        commandBox.runCommand("edit 1 name bug");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
	}
	
	private void assertEditSuccess(int targetIndexOneIndexed, String type, String content, TestTask taskToEdit) throws IllegalArgumentException, IllegalValueException {
    
        commandBox.runCommand("edit " + targetIndexOneIndexed + " " + type + " " + content);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}