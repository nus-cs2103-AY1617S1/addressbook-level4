//@@author A0146130W

package guitests;

import org.junit.Test;

import seedu.gtd.testutil.TestTask;
import seedu.gtd.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.gtd.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit() {

        //edit the priority of the first task
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String change = "p/4";
        assertEditSuccess(targetIndex, change, currentList);

        //edit the dueDate of the last in the list
        currentList = TestUtil.editTaskInList(currentList, targetIndex, change, currentList[targetIndex-1]);
        targetIndex = currentList.length;
        change = "d/2";
        assertEditSuccess(targetIndex, change, currentList);

        //edit the name task from the middle of the list
        currentList = TestUtil.editTaskInList(currentList, targetIndex, change, currentList[targetIndex-1]);
        targetIndex = currentList.length/2;
        change = "Cook lunch for friends";
        assertEditSuccess(targetIndex, change, currentList);
        
        //edit the address task from the middle of the list
        currentList = TestUtil.editTaskInList(currentList, targetIndex, change, currentList[targetIndex-1]);
        change = "a/Little India";
        assertEditSuccess(targetIndex, change, currentList);
        
        /*
        //edit everything at once
        currentList = TestUtil.editTaskInList(currentList, targetIndex, change, currentList[targetIndex-1]);
        change = "Cook friends for lunch d/midnight a/SMU p/5";
        String change1 = "Cook friends for lunch";
        String change2 = "d/midnight";
        String change3 = "a/SMU";
        String change4 = "p/5";
        String[] changes = {change1, change2, change3, change4};
        assertMultipleEditSuccess(targetIndex, change, currentList, changes);
        */

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + " Invalid");
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before editing)
     * @param change: contains detail with appropriate prefix that the user wants to edit into a task with index targetIndexOneIndexed.
     * 
     */
    private void assertEditSuccess(int targetIndexOneIndexed, String change, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.editTaskInList(currentList, targetIndexOneIndexed, change, taskToEdit);
        commandBox.runCommand("edit " + targetIndexOneIndexed + " " + change);

        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedRemainder[targetIndexOneIndexed-1]));
    }
    
    /*
    private void assertMultipleEditSuccess(int targetIndexOneIndexed, String change, TestTask[] currentList, String[] changeArr) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
      
        for(String c: changeArr) {
          currentList = TestUtil.editTaskInList(currentList, targetIndexOneIndexed, c, taskToEdit);
        }
        TestTask[] expectedRemainder = currentList;
        
        commandBox.runCommand("edit " + targetIndexOneIndexed + " " + change);

        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedRemainder[targetIndexOneIndexed-1]));
    }
    */
}
