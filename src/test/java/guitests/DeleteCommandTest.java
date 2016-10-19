package guitests;

import org.junit.Test;

import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskitty.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.taskitty.logic.commands.DeleteCommand.CATEGORIES;

public class DeleteCommandTest extends TaskManagerGuiTest {
/*
    @Test
    public void deleteWithoutCategory() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);
        
        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
    }
    
    @Test
    public void deleteWithCategory() {

        //delete first todo in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String category = "t";
        assertDeleteSuccess(targetIndex, category, currentList);

        //delete the last deadline in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        category = "d";
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, category, currentList);

        //delete from the middle of the list event
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        category = "e";
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, category, currentList);
        
        //invalid index
        commandBox.runCommand("delete " + category + " " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
    }*/
    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    /*private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, CATEGORIES[0], personToDelete));
    }*/
    
    /**
     * 
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param category the category in which to delete from
     * @param currentList A copy of the current list of persons (before deletion).     
     */
    /*private void assertDeleteSuccess(int targetIndexOneIndexed, String category, final TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);
        commandBox.runCommand("delete " + category + " " + targetIndexOneIndexed);
        int categoryIndex = StringUtil.getCategoryIndex(category);
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, CATEGORIES[categoryIndex], personToDelete));
    }*/
}
