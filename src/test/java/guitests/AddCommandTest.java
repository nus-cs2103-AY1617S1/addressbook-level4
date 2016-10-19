package guitests;

import guitests.guihandles.ItemCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestItem;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestItem[] currentList = td.getTypicalItems();
        TestItem personToAdd = td.help;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.indeed;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(td.help.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ITEM);
        assertTrue(itemListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.always);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestItem personToAdd, TestItem... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        ItemCardHandle addedCard = itemListPanel.navigateToItem(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, personToAdd);
        assertTrue(itemListPanel.isListMatching(expectedList));
    }

}
