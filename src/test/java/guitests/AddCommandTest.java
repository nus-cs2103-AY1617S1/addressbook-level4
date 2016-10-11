package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestFloatingTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestFloatingTask[] currentList = td.getTypicalPersons();
        TestFloatingTask personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addFloatingTasksToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addFloatingTasksToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_FLOATING_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestFloatingTask personToAdd, TestFloatingTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = personListPanel.navigateToFloatingTask(personToAdd.getName().name);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestFloatingTask[] expectedList = TestUtil.addFloatingTasksToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
