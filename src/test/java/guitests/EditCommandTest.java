package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.taskcommons.core.Messages;

public class EditCommandTest extends TaskBookGuiTest{

    @Test
    public void edit() {
        //edit one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToEdit = td.arts;
        currentList = TestUtil.editTasksToList(currentList, 0 , taskToEdit);
        assertEditSuccess(taskToEdit, 1, currentList);
        
        //edit another task
        taskToEdit = td.socSciences;
        currentList = TestUtil.editTasksToList(currentList, 3, taskToEdit);
        assertEditSuccess(taskToEdit, 4 ,currentList);

        //edit to a duplicate task
        commandBox.runCommand(td.arts.getEditFloatTaskCommand(3));
        assertResultMessage(EditTaskCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("edits 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command format
        commandBox.runCommand("edit 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    private void assertEditSuccess(TestTask taskToEdit, int index, TestTask... currentList) {
        commandBox.runCommand(taskToEdit.getEditFloatTaskCommand(index));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getTask().fullName);
        assertMatching(taskToEdit, editedCard);

        //confirm the list now contains all previous tasks plus the new edited task
        TestTask[] expectedList = TestUtil.addTasksToListAtIndex(currentList, index -1);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
