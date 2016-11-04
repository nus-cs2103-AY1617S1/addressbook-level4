package guitests;

import org.junit.Test;

import seedu.simply.testutil.TestTodo;
import seedu.simply.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.simply.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteTodoCommandTest extends SimplyGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTodo[] currentList = td.getTypicalTodo();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTodosFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTodosFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete T" + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the todo at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first todo in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of todos (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTodo[] expectedRemainder = TestUtil.removeTodosFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + ("T" + targetIndexOneIndexed));

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(todoListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, "[T"+targetIndexOneIndexed +"]"));
    }

}
