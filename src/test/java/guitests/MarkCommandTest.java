package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.MarkCommand;
import seedu.tasklist.testutil.TestTask;

public class MarkCommandTest extends TaskListGuiTest {

    @Test
    public void mark() {
        TestTask[] currentList = td.getTypicalTasks();
        
        //mark first task
        assertMarkSuccess(1, currentList[0], currentList);
        
        //mark last task
        assertMarkSuccess(currentList.length, currentList[currentList.length-1], currentList);
        
        //mark task that don't exist
        commandBox.runCommand("mark -10");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        commandBox.runCommand("mark -1");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        commandBox.runCommand("mark " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        commandBox.runCommand("mark " + (currentList.length + 10));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //mark duplicate task
        commandBox.runCommand("mark 1");
        assertResultMessage(MarkCommand.MESSAGE_MARKED_TASK);
        commandBox.runCommand("mark " + currentList.length);
        assertResultMessage(MarkCommand.MESSAGE_MARKED_TASK);
        
        //invalid command
        commandBox.runCommand("marks 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("mark index");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
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
