package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.menion.commons.core.Messages;
import seedu.menion.logic.commands.AddCommand;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask personToAdd = td.assignment8;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.assignment9;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(td.assignment8.getAddCommand());
        //assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        //assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.assignment1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getActivityName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
