package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.testutil.TestPerson;
import seedu.jimi.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestPerson[] currentList = td.getTypicalPersons();
        TestPerson personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

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

    private void assertAddSuccess(TestPerson personToAdd, TestPerson... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestPerson[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
