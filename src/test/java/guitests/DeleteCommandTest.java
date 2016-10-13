package guitests;

import org.junit.Test;
import seedu.address.testutil.TestItem;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;

import java.awt.List;
import java.util.ArrayList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;

public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void delete() {

        /* Delete single items */
        //delete the first in the list
        TestItem[] currentList = td.getTypicalPersons();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeItemFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeItemFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        /* Delete multiple items */
        // delete first and last in the list
        currentList = TestUtil.removeItemFromList(currentList, targetIndex);
        int[] targetIndexes = {2, currentList.length};
//        currentList = TestUtil.removeItemsFromList(currentList, targetIndexes);
        assertDeleteSuccess(targetIndexes, currentList);
        
        //invalid index
        commandBox.runCommand("delete " + (currentList.length + 1));
        assertResultMessage(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        
    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestItem[] currentList) {
        TestItem personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestItem[] expectedRemainder = TestUtil.removeItemFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous items except the deleted item
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndexes, final TestItem[] currentList) {
        ArrayList<TestItem> itemsToDelete = new ArrayList<TestItem>();
        for(int index : targetIndexes) {
            itemsToDelete.add(currentList[index-1]); //-1 because array uses zero indexing
        }
        TestItem[] expectedRemainder = TestUtil.removeItemsFromList(currentList, targetIndexes);

        // format for deleting multiple indexes
        StringBuilder targetIndexesStringFormat = new StringBuilder();
        for(int i = 0; i < targetIndexes.length; i++) {
            targetIndexesStringFormat.append(String.valueOf(targetIndexes[i]));
            if(i != (targetIndexes.length - 1)) {
                targetIndexesStringFormat.append(" ");
            }
        }
        commandBox.runCommand("delete " + targetIndexesStringFormat);

        //confirm the list now contains all previous items except the deleted items
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemsToDelete));
    }

}
