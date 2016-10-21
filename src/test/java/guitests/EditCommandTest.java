package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.logic.commands.EditCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EditCommandTest extends FlexiTrackGuiTest {

    @Test
    public void editPass() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask editedTask;
        int index;
        String command;

        // edit a task name
        editedTask = td.homework1EditName;
        index = 1;
        command = " n/ Name Edited";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit a task duedate
        editedTask = td.homework1EditDueDate;
        index = 1;
        command = " by/ Jan 14 2016 10am";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit an event name
        editedTask = td.soccerEditName;
        index = 4;
        command = " n/ Name Edited";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit an event start time
        editedTask = td.soccerEditStartTime;
        index = 4;
        command = " from/ June 10 2016 9pm";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit an event end time
        editedTask = td.soccerEditEndTime;
        index = 4;
        command = " to/ June 30 2020 6am";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit a floating task name
        editedTask = td.homework3EditName;
        index = 3;
        command = " n/ Name Edited";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit a floating task into a task
        editedTask = td.homework3EditToTask;
        index = 3;
        command = " by/ Jun 10 2016 9pm";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

        // edit a floating task into an event
        editedTask = td.eventEditToEvent;
        index = 8;
        command = " from/ Jun 10 2016 21:00 to/ Jun 30 2016 23:00";
        assertEditSuccess(editedTask, currentList, index, command);
        currentList = TestUtil.editTasksToList(currentList, index - 1, editedTask);

    }

    @Test
    public void editFail() {
        TestTask[] currentList = td.getTypicalTasks();

        // index not found
        commandBox.runCommand("edit " + (currentList.length + 1) + " n/ hello");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // edit task starttime
        commandBox.runCommand("edit " + 1 + " from/ today");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit task endtime
        commandBox.runCommand("edit " + 1 + " to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit event duedate
        commandBox.runCommand("edit " + 4 + " by/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with only starttime
        commandBox.runCommand("edit " + 3 + " from/ today");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with only endtime
        commandBox.runCommand("edit " + 3 + " to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with both duedate and starttime
        commandBox.runCommand("edit " + 3 + " by/ tomorrow from/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with both duedate and endtime
        commandBox.runCommand("edit " + 3 + " by/ tomorrow to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // invalid command format
        commandBox.runCommand("edit wtf is this");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

    }

    private void assertEditSuccess(TestTask editedTask, final TestTask[] currentList, int indexOneIndexed,
            String command) {
        commandBox.runCommand("edit " + indexOneIndexed + command);

        int index = indexOneIndexed - 1;
        // confirm the edited card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskListPanel.getTask(index));
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.editTasksToList(currentList, index, editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}