package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class MarkCommandTest extends FlexiTrackGuiTest {

    @Test
    public void mark() {
        //mark a task
        TestTask[] currentList = td.getTypicalTasks();
        assertMarkSuccess(4, currentList);
        currentList = TestUtil.markTasksToList(currentList, 4);

        //mark a task
        assertMarkSuccess(1, currentList);
        currentList = TestUtil.markTasksToList(currentList, 1);

        //mark a task with invalid number
        commandBox.runCommand(TestTask.getMarkCommand(10));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //mark an already marked task
        assertMarkSuccess(4, currentList);
        currentList = TestUtil.markTasksToList(currentList, 4);

        //un-mark a marked test
        assertUnMarkSuccess(1, currentList);
        currentList = TestUtil.markTasksToList(currentList, 1);
        
        //un-mark an unmarked test
        assertUnMarkSuccess(3, currentList);
        currentList = TestUtil.markTasksToList(currentList, 1);
        
        //unmark a task with invalid number
        commandBox.runCommand(TestTask.getUnMarkCommand(10));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUnMarkSuccess(int taskToUnMark, TestTask ... currentList) {
        commandBox.runCommand(TestTask.getUnMarkCommand(taskToUnMark));

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.unMarkTasksToList(currentList, taskToUnMark);
        assertTrue(taskListPanel.isListMatching(expectedList));        
    }

    private void assertMarkSuccess(int taskToMark, TestTask... currentList) {
        commandBox.runCommand(TestTask.getMarkCommand(taskToMark));

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.markTasksToList(currentList, taskToMark);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
