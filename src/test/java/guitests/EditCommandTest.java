//@@author A0127855W
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.logic.commands.EditCommand;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;
import seedu.flexitrack.testutil.TypicalTestTasks;

public class EditCommandTest extends FlexiTrackGuiTest {
    
    TestTask[] currentList = td.getTypicalSortedTasks();
    TestTask editedTask;
    int index;
    String command;
    
    @Test
    public void edit_taskName_pass() {
        editedTask = TypicalTestTasks.homework1EditName;
        index = 6;
        command = " n/ Name Edited";
        assertEditSuccess(editedTask, currentList, index, command);
    }
    
    @Test
    public void edit_taskDueDate_pass() {
        editedTask = TypicalTestTasks.homework1EditDueDate;
        index = 6;
        command = " by/ Jan 14 2016 10am";
        assertEditSuccess(editedTask, currentList, index, command);
    }
    
    @Test
    public void edit_eventName_pass() {
        editedTask = TypicalTestTasks.soccerEditName;
        index = 4;
        command = " n/ Name Edited 2";
        assertEditSuccess(editedTask, currentList, index, command);
    }

    @Test
    public void edit_eventStartTime_pass() {
        editedTask = TypicalTestTasks.soccerEditStartTime;
        index = 4;
        command = " from/ June 10 2016 9pm";
        assertEditSuccess(editedTask, currentList, index, command);
    }
    
    @Test
    public void edit_eventEndTime_pass() {
        editedTask = TypicalTestTasks.soccerEditEndTime;
        index = 4;
        command = " to/ June 30 2020 6am";
        assertEditSuccess(editedTask, currentList, index, command);
    }
    
    @Test
    public void edit_floatingTaskName_pass() {
        editedTask = TypicalTestTasks.homework3EditName;
        index = 2;
        command = " n/ Name Edited 3";
        assertEditSuccess(editedTask, currentList, index, command);
    }
    
    @Test
    public void edit_floatingTaskIntoTask_pass() {
        editedTask = TypicalTestTasks.homework3EditToTask;
        index = 2;
        command = " by/ Jun 10 2016 9pm";
        assertEditSuccess(editedTask, currentList, index, command);
    }

    @Test
    public void edit_floatingTaskIntoEvent_pass() {
        editedTask = TypicalTestTasks.eventEditToEvent;
        index = 1;
        command = " from/ Jun 10 2016 21:00 to/ Jun 30 2016 23:00";
        assertEditSuccess(editedTask, currentList, index, command);
    }

    @Test
    public void edit_typicalTaskSet_fail() {
        TestTask[] currentList = td.getTypicalSortedTasks();

        // index not found
        commandBox.runCommand("edit " + (currentList.length + 1) + " n/ hello");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // edit task starttime
        commandBox.runCommand("edit " + 5 + " from/ today");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit task endtime
        commandBox.runCommand("edit " + 5 + " to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit event duedate
        commandBox.runCommand("edit " + 3 + " by/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with only starttime
        commandBox.runCommand("edit " + 1 + " from/ today");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with only endtime
        commandBox.runCommand("edit " + 1 + " to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with both duedate and starttime
        commandBox.runCommand("edit " + 1 + " by/ tomorrow from/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // edit floating task with both duedate and endtime
        commandBox.runCommand("edit " + 1 + " by/ tomorrow to/ tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // invalid command format
        commandBox.runCommand("edit what is this");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

    }

    private void assertEditSuccess(TestTask editedTask, final TestTask[] currentList, int indexOneIndexed,
            String command) {
        int index = indexOneIndexed - 1;
       
        commandBox.runCommand("edit " + indexOneIndexed + command);
         TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().toString());
        
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.editTasksToList(currentList, index, editedTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}