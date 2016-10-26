package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.logic.commands.DeleteCommand;
import seedu.taskmanager.logic.commands.SaveCommand;
import seedu.taskmanager.testutil.TestItem;
import seedu.taskmanager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;

public class DeleteCommandTest extends TaskManagerGuiTest {

    private static final Logger logger = LogsCenter.getLogger(DeleteCommandTest.class);

    @Test
    public void delete() {

        /* Delete single items */
        //delete the first in the list
        TestItem[] currentList = td.getTypicalItems();
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
        
        //@@author A0143641M
        /* Delete multiple items */
        // delete first and last in the list
        currentList = TestUtil.removeItemFromList(currentList, targetIndex);
        int[] targetTwoIndexes = {2, currentList.length};
        assertDeleteSuccess(targetTwoIndexes, currentList);
        
        // delete all items except the first item
        currentList = TestUtil.removeItemsFromList(currentList, targetTwoIndexes);
        int[] targetMultipleIndexes = new int[currentList.length-1];
        int arrayIndex = 0;
        for(int i = 1; i < currentList.length; i++) {
            targetMultipleIndexes[arrayIndex] = i + 1;
            arrayIndex += 1;
        }
        logger.info(Arrays.toString(targetMultipleIndexes));
        assertDeleteSuccess(targetMultipleIndexes, currentList);
        
        // delete invalid indexes
        currentList = TestUtil.removeItemsFromList(currentList, targetMultipleIndexes);
        targetTwoIndexes[0] = currentList.length + 1;
        targetTwoIndexes[1] = targetTwoIndexes[0] + 1;
        commandBox.runCommand("delete " + targetTwoIndexes[0] + " " + targetTwoIndexes[1]);
        assertResultMessage(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        //invalid index
        commandBox.runCommand("delete " + (currentList.length + 1));
        assertResultMessage(MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        
        // non-positive integer index
        commandBox.runCommand("delete 0 " + (currentList.length + 1));
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        
    }

    /**
     * Runs the delete command to delete the persons at specified indexes and confirms the result is correct.
     * @param targetIndexes e.g. to delete the first and last person in the list, 1 and currentlist.length should be given as the target index.
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
        assertTrue(shortItemListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemsToDelete));
    }

    //@@author
    
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
        assertTrue(shortItemListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ITEM_SUCCESS, personToDelete));
    }

}
