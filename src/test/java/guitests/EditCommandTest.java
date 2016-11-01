package guitests;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import javafx.collections.FXCollections;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.*;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.logic.commands.EditCommand;

import static seedu.ggist.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

//@@author A0147994J
public class EditCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void edit() throws IllegalArgumentException, IllegalValueException {
		TestTask[] currentArray = td.getTypicalTasks();
		
		
		//edit the first in the list, edit task name
		ArrayList<TestTask> NameTesting= new ArrayList<TestTask> (Arrays.asList(currentArray));
        int targetIndex = 1;
        String newContent = "edited name";
        String type = "task";
        TestTask taskToBeReplaced = NameTesting.get(targetIndex - 1);
        TaskName original = taskToBeReplaced.getTaskName();
        taskToBeReplaced.setTaskName(new TaskName(newContent));
        //currentList.sort(taskToBeReplaced.getTaskComparator());
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        commandBox.runCommand("undo");
        taskToBeReplaced.setTaskName(original);
        

        //edit the last in the list, edit time 
        ArrayList<TestTask> TimeTesting= new ArrayList<TestTask> (Arrays.asList(currentArray));
        targetIndex = currentArray.length;
        newContent = "10:00 PM";
        type = "start time";
        taskToBeReplaced = TimeTesting.get(targetIndex - 1);
        TaskTime originalTime = taskToBeReplaced.getStartTime();
        taskToBeReplaced.setStartTime(new TaskTime(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        commandBox.runCommand("undo");
        taskToBeReplaced.setStartTime(originalTime);
        
        //edit priority
        ArrayList<TestTask> currentList= new ArrayList<TestTask> (Arrays.asList(currentArray));
        targetIndex = 1;
        newContent = "med";
        type = "priority";
        taskToBeReplaced = currentList.get(targetIndex - 1);
        Priority originalPriority = taskToBeReplaced.getPriority();
        taskToBeReplaced.setPriority(new Priority(newContent));
        assertEditSuccess(targetIndex,type,newContent,taskToBeReplaced);
        commandBox.runCommand("undo");
        taskToBeReplaced.setPriority(originalPriority);

        
        //invalid command format
        commandBox.runCommand("edit task 1 wrong");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        //invalid index
        commandBox.runCommand("edit " + (currentList.size() + 1) + " task invalid");
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