package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
<<<<<<< HEAD
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
=======
        TestTask[] currentList = td.getTypicalPersons();
        TestTask personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439

        //add another person
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().taskName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
<<<<<<< HEAD
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, personToAdd);
=======
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
