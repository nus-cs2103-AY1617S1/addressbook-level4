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
        //add one item
        TestItem[] currentList = td.getTypicalItems();
        TestItem itemToAdd = td.help;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add another item
        itemToAdd = td.indeed;
        assertAddSuccess(itemToAdd, currentList);
        currentList = TestUtil.addItemsToList(currentList, itemToAdd);

        //add duplicate item - allowed
        commandBox.runCommand(td.help.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_SUCCESS);
        assertTrue(itemListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.always);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestItem itemToAdd, TestItem... currentList) {
        commandBox.runCommand(itemToAdd.getAddCommand());

        //confirm the new card contains the right data
        ItemCardHandle addedCard = itemListPanel.navigateToItem(itemToAdd.getDescription().getFullDescription());
        assertMatching(itemToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestItem[] expectedList = TestUtil.addItemsToList(currentList, itemToAdd);
        assertTrue(itemListPanel.isListMatching(expectedList));
    }

}
