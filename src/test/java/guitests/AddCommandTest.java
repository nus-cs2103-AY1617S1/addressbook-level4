package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestFloatingTask[] currentList = td.getTypicalTasks();
        TestFloatingTask taskToAdd = td.dream;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another person
        taskToAdd = td.dream;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(td.dream.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.water);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestFloatingTask taskToAdd, TestFloatingTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestFloatingTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
