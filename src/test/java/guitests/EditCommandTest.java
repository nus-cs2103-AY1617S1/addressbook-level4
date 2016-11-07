package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.logic.commands.EditCommand;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.Name;
import seedu.taskmaster.model.task.RecurringType;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.testutil.TestTask;
import seedu.taskmaster.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskmaster.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

//@@author A0147967J
public class EditCommandTest extends TaskMasterGuiTest {

    @Test
    public void edit() throws IllegalValueException {
        commandBox.runCommand("list"); //switch to all tasks first

        // Fix Index first to see edit effect
        // edit deadline
        int index = 4;
        TestTask[] currentList = td.getTypicalTasks();
        TestTask toBeEdited = currentList[index - 1];
        toBeEdited.setEndDate("2 oct 10am");
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 by 2 oct 10am", currentList);

        // edit it to time slot
        toBeEdited = currentList[index - 1];
        toBeEdited.setStartDate("2 oct 9am");
        toBeEdited.setEndDate("2 oct 11am");
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 from 2 oct 9am to 2 oct 11am", currentList);

        // add a tag
        toBeEdited = currentList[index - 1];
        toBeEdited.setTag(new UniqueTagList(new Tag("testTag")));
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 t/testTag", currentList);

        // add tags
        toBeEdited = currentList[index - 1];
        toBeEdited.setTag(new UniqueTagList(new Tag("testTag1"), new Tag("testTag2")));
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 t/testTag1 t/testTag2", currentList);

        // change name
        toBeEdited = currentList[index - 1];
        toBeEdited.setName(new Name("Test name"));
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 Test name", currentList);

        // invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " invalid index");
        assertResultMessage("The task index provided is invalid");

        // invalid command
        commandBox.runCommand("edits read weblecture");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // Edit a normal task to a recurring task
        toBeEdited = currentList[index - 1];
        toBeEdited.setRecurringType(RecurringType.MONTHLY);
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 monthly", currentList);

        // Edit it back also enabled
        toBeEdited = currentList[index - 1];
        toBeEdited.setRecurringType(RecurringType.NONE);
        currentList[index - 1] = toBeEdited;
        assertEditSuccess(toBeEdited, "edit 4 none", currentList);

    }

    private void assertEditSuccess(TestTask editedCopy, String command, TestTask... modifiedList) {

        commandBox.runCommand(command);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedCopy.getName().fullName);
        assertMatching(editedCopy.getTaskDateComponent().get(0), editedCard);

        // confirm the list now contains all the unmodified tasks and the edited
        // task
        TaskOccurrence[] taskComponents = TestUtil.convertTasksToDateComponents(modifiedList);

        assertTrue(taskListPanel.isListMatching(taskComponents));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedCopy.getLastAppendedComponent()));
    }

}
