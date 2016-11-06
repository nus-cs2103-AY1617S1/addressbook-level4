// @@author A0140156R
// Refactored individual command tests from LogicManagerTest into individual classes
// @@author A0121657H

package seedu.oneline.logic;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.DoneCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;

public class DoneCommandTest extends LogicTestManager {
    
    //---------------- Tests for doneCommand —------------------------------------
    /*
     * Invalid equivalence partitions for index: null, signed integer, non-numeric characters
     * Invalid equivalence partitions for index: index larger than no. of tasks in taskBook
     * The two test cases below test invalid input above one by one.
     */
    
    @Test
    public void done_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void done_indexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    
    /**
     * Checks that the task book is able to sort through the task list
     * and mark the appropriate task as done. The resulting task list 
     * shown should only contain tasks that are not done.
     * @throws Exception
     */
    @Test
    public void done_validIndex_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple");
        Task p2 = helper.generateTaskWithName("boil water");
        Task p3 = helper.generateTaskWithName("poke pineapple");
        Task p4 = helper.generateTaskWithName("buy pen");

        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks); 
        List<Task> newTasks = helper.generateTaskList(p3, p1, p4.markDone(), p2);
        Collections.sort(newTasks); 
        TaskBook expectedAB = helper.generateTaskBook(newTasks);
        List<Task> expectedList = helper.generateTaskList(p2, p1, p3);
        helper.addToModel(model, originalTasks);
        model.updateFilteredListToShowAllNotDone();

        assertCommandBehavior("done 2",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, p4),
                expectedAB,
                expectedList);
    }
    
}
