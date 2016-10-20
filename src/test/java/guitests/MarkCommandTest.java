package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.MarkCommand;
import seedu.tasklist.testutil.TestTask;

public class MarkCommandTest extends TaskListGuiTest {

    @Test
    public void mark() {
        //mark first task
        TestTask[] currentList = td.getTypicalTasks();
        assertMarkSuccess(1, currentList[0], currentList);
        
        //mark last task
        assertMarkSuccess(currentList.length, currentList[currentList.length-1], currentList);
        
        //mark task that don't exist
        commandBox.runCommand("mark " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //mark duplicate task
        commandBox.runCommand("mark 1");
        assertResultMessage(MarkCommand.MESSAGE_MARKED_TASK);
        
        //invalid command
        commandBox.runCommand("marks 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertMarkSuccess(int index, TestTask taskToMark, TestTask... currentList) {
        commandBox.runCommand("mark " + index);
        taskToMark.setCompleted(true);
        
        //confirm the new card contains the right data
        TaskCardHandle markedCard = taskListPanel.navigateToTask(taskToMark.getTitle().fullTitle);
        assertMarked(taskToMark, markedCard);
        assertResultMessage("Task marked: " + taskToMark.getAsText());
    }
}
