package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.logic.commands.EditCommand;
import seedu.tasklist.model.task.Description;
import seedu.tasklist.model.task.Title;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;

public class EditCommandTest extends TaskListGuiTest {
    @Test
    public void edit() {
        //edit one task title
        TestTask[] currentList = td.getTypicalTasks();
        assertEditTitleSuccess(1, currentList[0], currentList);

        //edit another task title
        assertEditTitleSuccess(2, currentList[1], currentList);

        //edit one task description
        assertEditDescriptionSuccess(3, currentList[2], currentList);

        //edit another task description
        assertEditDescriptionSuccess(4, currentList[3], currentList);

        //edit to empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("edit 1 CS2103");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //invalid command
        commandBox.runCommand("edits 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertEditTitleSuccess(int index, TestTask taskToEdit, TestTask... currentList) {
        commandBox.runCommand("edit " + index + " CS210" + index);
        try {
            taskToEdit.setTitle(new Title("CS210" + index));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getTitle().fullTitle);
        assertMatching(taskToEdit, editedCard);
    }    
    
    private void assertEditDescriptionSuccess(int index, TestTask taskToEdit, TestTask... currentList) {
        commandBox.runCommand("edit " + index + " d/submit early " + index);
        try {
            taskToEdit.setDescription(new Description("submit early " + index));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToEdit.getTitle().fullTitle);
        assertMatching(taskToEdit, editedCard);
    }
}
