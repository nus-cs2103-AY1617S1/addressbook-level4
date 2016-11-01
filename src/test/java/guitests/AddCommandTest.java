package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.four;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.five;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(currentList));

        //TODO add duplicate, add undated task
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.one);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = datedListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous task plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(datedListPanel.isListMatching(expectedList));
    }

}
