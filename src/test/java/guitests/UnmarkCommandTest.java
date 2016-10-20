package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.UnmarkCommand;
import seedu.tasklist.testutil.TestTask;

public class UnmarkCommandTest extends TaskListGuiTest {

    @Test
    public void unmark() {
        //unmark first task
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("mark " + 1);
        commandBox.runCommand("mark " + currentList.length);
        assertUnmarkSuccess(1, currentList[0], currentList);
        
        //unmark last task
        assertUnmarkSuccess(currentList.length, currentList[currentList.length-1], currentList);
        
        //unmark task that don't exist
        commandBox.runCommand("unmark " + (currentList.length+1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //unmark duplicate task
        commandBox.runCommand("unmark 1");
        assertResultMessage(UnmarkCommand.MESSAGE_UNMARKED_TASK);
        
        //invalid command
        commandBox.runCommand("unmarks 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertUnmarkSuccess(int index, TestTask taskToUnmark, TestTask... currentList) {
        commandBox.runCommand("unmark " + index);
        taskToUnmark.setCompleted(false);
        
        //confirm the new card contains the right data
        TaskCardHandle markedCard = taskListPanel.navigateToTask(taskToUnmark.getTitle().fullTitle);
        assertUnmarked(taskToUnmark, markedCard);
        assertResultMessage("Task unmarked: " + taskToUnmark.getAsText());
    }
}
