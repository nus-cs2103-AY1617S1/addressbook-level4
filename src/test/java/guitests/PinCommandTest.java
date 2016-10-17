package guitests;

import guitests.guihandles.TaskCardHandle;
import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.PinCommand.MESSAGE_PIN_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.UpdateCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class PinCommandTest extends TaskManagerGuiTest{
	@Test
    public void pin() throws IllegalValueException {
	TestTask[] currentList=td.getTypicalTasks();
	int targetIndex =1;
	
	//invalid index
    commandBox.runCommand("pin " + currentList.length + 1);
    assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	
    //pin the first task
    commandBox.runCommand("pin "+targetIndex);
    ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
    assertTrue(newTask.getImportance());
    
    //pin another task
    targetIndex=3;
    commandBox.runCommand("pin "+targetIndex);
    ReadOnlyTask otherTask = taskListPanel.getTask(targetIndex - 1);
    assertTrue(otherTask.getImportance());
	}	

}
