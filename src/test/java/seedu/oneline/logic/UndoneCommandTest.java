// @@author A0121657H

package seedu.oneline.logic;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.UndoneCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;

public class UndoneCommandTest extends LogicTestManager {
    
    //---------------- Tests for undoneCommand ------------------------------------
    /*
     * Invalid equivalence partitions for index: null, signed integer, non-numeric characters
     * Invalid equivalence partitions for index: index larger than no. of tasks in taskBook
     * The two test cases below test invalid input above one by one.
     */
    
    @Test
    public void undone_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(UndoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("undone", expectedMessage);
    }
    
    @Test
    public void undone_indexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("undone");
    }
    
    /**
     * Checks that the task book is able to sort through the task list
     * and mark the appropriate task as not done. The resulting task list 
     * shown should only contain tasks that are not done.
     * @throws Exception
     */
    @Test
    public void undone_validIndex_successMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("eat apple").markDone();
        Task p2 = helper.generateTaskWithName("boil water").markDone();
        Task p3 = helper.generateTaskWithName("poke pineapple").markDone();
        Task p4 = helper.generateTaskWithName("buy pen").markDone();

        List<Task> originalTasks = helper.generateTaskList(p3, p1, p4, p2);
        Collections.sort(originalTasks); 
        TaskBook expectedAB = helper.generateTaskBook(originalTasks);
        List<Task> expectedList = helper.generateTaskList(p2, p1, p3);
        helper.addToModel(model, originalTasks);
        model.updateFilteredListToShowAllDone();

        assertCommandBehavior("undone 2",
                String.format(UndoneCommand.MESSAGE_DONE_TASK_SUCCESS, p4),
                expectedAB,
                expectedList);
    }
    
}
