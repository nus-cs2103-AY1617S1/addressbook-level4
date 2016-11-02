package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.testutil.TestDeadlineTask;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;
import seedu.jimi.testutil.TypicalTestDeadlineTasks;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    //@Test
    public void add() {
        ReadOnlyTask[] currentList = td.getTypicalTasks();
        
        //add one task
        TestFloatingTask taskToAdd = TypicalTestFloatingTasks.dream;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestFloatingTasks.night;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(TypicalTestFloatingTasks.dream.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //add deadline task
        TestDeadlineTask deadlineTaskToAdd = TypicalTestDeadlineTasks.homework;
        assertAddSuccess(deadlineTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, deadlineTaskToAdd);
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestFloatingTasks.beach);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestFloatingTask taskToAdd, ReadOnlyTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        ReadOnlyTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    //@@author A0143471L
    private void assertAddSuccess(TestDeadlineTask taskToAdd, ReadOnlyTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
    }
    //@@author
}
