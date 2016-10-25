package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;

public class EditCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() {
        //edit one task
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        TestTask taskToEdit = td.todo;
        int targetIndex = currentList.size('d');
        assertEditSuccess(taskToEdit, targetIndex, 'd', currentList);

        //edit another task
        taskToEdit = td.deadline;
        targetIndex = currentList.size('e');
        assertEditSuccess(taskToEdit, targetIndex, 'e', currentList);
        
        //edit another task
        taskToEdit = td.event;
        targetIndex = currentList.size('t');
        assertEditSuccess(taskToEdit, targetIndex, 't', currentList);
        
        //edit into duplicate task
        commandBox.runCommand(td.deadline.getEditCommand(targetIndex - 1, 'e'));
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(currentList.isListMatching(taskListPanel));

        //invalid command
        commandBox.runCommand("edits party");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertEditSuccess(TestTask taskToEdit, int index, char category, TestTaskList currentList) {
        commandBox.runCommand(taskToEdit.getEditCommand(index, category));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getName().fullName, taskToEdit.getPeriod().getNumArgs());
        assertMatching(taskToEdit, editedCard);

        //confirm the list now contains all previous persons plus the new person
        currentList.editTaskFromList(index - 1, category, taskToEdit);
        assertTrue(currentList.isListMatching(taskListPanel));
    }

}
