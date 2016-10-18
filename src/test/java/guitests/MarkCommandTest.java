package guitests;

import org.junit.Test;
import seedu.agendum.logic.commands.MarkCommand;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.testutil.TaskBuilder;
import seedu.agendum.testutil.TestTask;
import seedu.agendum.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class MarkCommandTest extends ToDoListGuiTest {

    @Test
    public void mark() {

        //mark the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertMarkSuccess(targetIndex, currentList);

        //Task in the original currentList are not marked (only the copy is tested)

        //mark the last in the list
        targetIndex = currentList.length;
        assertMarkSuccess(targetIndex, currentList);

        //mark from the middle of the list
        targetIndex = currentList.length/2;
        assertMarkSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("mark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    /**
     * Runs the mark command to mark the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking).
     */
    private void assertMarkSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask markedTask = new TaskBuilder(taskToMark).withCompletedStatus().build();
        TestTask[] expectedList = TestUtil.replaceTaskFromList(currentList, markedTask, targetIndexOneIndexed -1);

        commandBox.runCommand("mark " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks and the specified task has been marked
        assertTrue(taskListPanel.isListMatching(expectedList));

        //confirm the result message is correct
        ArrayList<Integer> markedTaskIndices = new ArrayList<Integer>();
        markedTaskIndices.add(targetIndexOneIndexed);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTaskIndices));
    }

}
