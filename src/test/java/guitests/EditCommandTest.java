//@@author A0147335E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Name;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

public class EditCommandTest extends TaskManagerGuiTest {
    @Test
    public void edit() {
        
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit 1 name, Accompany dad to the doctor");
        commandBox.runCommand("e 1 tag, gwsDad");
        
        commandBox.runCommand("edit 1 start, 3pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 end, 6pm");
        commandBox.runCommand("undo");

        commandBox.runCommand("edit 1 due, 10pm");
        commandBox.runCommand("undo");
        
        assertEditSuccess(1, TypicalTestTasks.taskJ, currentList);
        commandBox.runCommand("undo 2");
        
        //@@author A0152958R
        commandBox.runCommand("clear");
        commandBox.runCommand("add task, at today");
        
        commandBox.runCommand("edit 2 name, new task");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        commandBox.runCommand("edit 1 start, i don't know what it is");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_FORMAT);
        
        commandBox.runCommand("edit 1 end, yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_TIME_INTERVAL);
        
        commandBox.runCommand("edit 1 name, //");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
        
        commandBox.runCommand("edit 1 tag, //");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
        
        
    }

    private void assertEditSuccess(int index, TestTask taskToAdd, TestTask... currentList) {
        
        TestTask[] expectedList = currentList;
        expectedList[index - 1] = taskToAdd;
        
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
