package guitests;

import static org.junit.Assert.*;
import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

public class EditCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() {
        //edit one task
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        TestTask taskToEdit = td.todo;
        int targetIndex = currentList.size("t");
        assertEditSuccess(taskToEdit, targetIndex, "t", currentList);

        //edit another task
        taskToEdit = td.deadline;
        targetIndex = currentList.size("d");
        assertEditSuccess(taskToEdit, targetIndex, "d", currentList);
        
        //edit another task
        taskToEdit = td.event;
        targetIndex = currentList.size("e");
        assertEditSuccess(taskToEdit, targetIndex, "e", currentList);
        
        //edit into duplicate task
        commandBox.runCommand(td.deadline.getEditCommand(targetIndex - 1, "e"));
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(currentList.isListMatching(taskListPanel));

        //invalid command
        commandBox.runCommand("edits party");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        commandBox.runCommand("edit 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit 1 deadline 09/09/2016 5pm");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("edit d deadline 09/09/2016 5pm");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        //invalid index
        targetIndex = currentList.size("t") + 1;
        commandBox.runCommand(td.todo.getEditCommand(targetIndex, "t"));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertTrue(currentList.isListMatching(taskListPanel));
    }

    private void assertEditSuccess(TestTask taskToEdit, int index, String category, TestTaskList currentList) {
        commandBox.runCommand(taskToEdit.getEditCommand(index, category));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getName().fullName, taskToEdit.getPeriod().getNumArgs());
        assertMatching(taskToEdit, editedCard);

        //confirm the list now contains all previous persons plus the new person
        currentList.editTaskFromList(index - 1, category, taskToEdit);
        assertTrue(currentList.isListMatching(taskListPanel));
    }

}
