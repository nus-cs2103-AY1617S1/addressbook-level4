package guitests;

import org.junit.Test;

import seedu.todo.testutil.TestTask;
import seedu.todo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.todo.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {
        
        
        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        for (TestTask t : currentList) {
            commandBox.runCommand(t.getAddCommand());
        }
        int targetIndex = 1;
        TestTask[] currentRevList = td.getTypicalTasksReverse();
        assertDeleteSuccess(targetIndex, currentRevList);

        //delete the last in the list
        currentRevList = TestUtil.removePersonFromList(currentRevList, targetIndex);
        targetIndex = currentRevList.length;
        assertDeleteSuccess(targetIndex, currentRevList);

        //delete from the middle of the list
        currentRevList = TestUtil.removePersonFromList(currentRevList, targetIndex);
        targetIndex = currentRevList.length / 2;
        assertDeleteSuccess(targetIndex, currentRevList);

        //invalid index
        commandBox.runCommand("delete " + currentRevList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed - 1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);
        
        for (TestTask t : expectedRemainder) {
            System.out.println(t.getName().fullName);
        }
        
        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete.getName()));
    
    }
}
