package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestItem;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one person
        TestItem[] currentList = td.getTypicalPersons();
        TestItem itemToAdd = td.deadline3;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another person
        itemToAdd = td.task3;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add duplicate person
        commandBox.runCommand(td.deadline3.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ITEM);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.event1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestItem itemToAdd, TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(itemToAdd.getName().value);
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
