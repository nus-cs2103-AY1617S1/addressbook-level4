package guitests;

import org.junit.Test;

import seedu.gtd.commons.exceptions.IllegalValueException;
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
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        change = "d/tomorrow";
        assertEditSuccess(targetIndex, change, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        change = "Tutorial 4";
        assertEditSuccess(targetIndex, change, currentList);

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1 + "Invalid");
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, String change, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder;
		try {
			expectedRemainder = TestUtil.editTaskInList(currentList, targetIndexOneIndexed, change, taskToEdit);
		} catch (IllegalValueException e) {
			expectedRemainder = currentList;
			e.printStackTrace();
		}

        commandBox.runCommand("edit " + targetIndexOneIndexed + " " + change);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}
