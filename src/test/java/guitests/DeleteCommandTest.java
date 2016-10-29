package guitests;

import org.junit.Test;

import seedu.todoList.testutil.TestUtil;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.testutil.TaskBuilder;
import seedu.todoList.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.todoList.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends ListGuiTest {

    @Test
    //@@author A0132157M reused
    public void delete() throws IllegalValueException {

        //delete the first in the list
        TestTask[] currentList = new TestTask[] {new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build(),
                new TaskBuilder().withName("TODO 456").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build(),
                new TaskBuilder().withName("TODO 789").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("1").withDone("false").build()};//td.getTypicaltasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removetaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete todo" + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removetaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete todo " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
