//@@author A0147335E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

public class EditCommandTest extends TaskManagerGuiTest {
    @Test
    public void edit() {
        
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit 1 name, Accompany dad to the doctor");
        commandBox.runCommand("edit 1 tag, gwsDad");
        
        commandBox.runCommand("edit 1 start, 3pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 end, 6pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 due, 10pm");
        commandBox.runCommand("undo");
        
        assertEditSuccess(1, TypicalTestTasks.taskJ, currentList);
        
        //@@author A0152958R
        commandBox.runCommand("clear");
        commandBox.runCommand("add task");
        
        commandBox.runCommand("edit 2 name, new task");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        commandBox.runCommand("edit 1 start, i don't know what it is");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_FORMAT);
        
        
    }

    private void assertEditSuccess(int index, TestTask taskToAdd, TestTask... currentList) {
        
        TestTask[] expectedList = currentList;
        expectedList[index - 1] = taskToAdd;
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
