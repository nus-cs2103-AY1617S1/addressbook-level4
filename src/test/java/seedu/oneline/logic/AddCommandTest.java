// @@author A0140156R
// Refactored individual command tests from LogicManagerTest into individual classes
// @@author

package seedu.oneline.logic;

import org.junit.Test;

import seedu.oneline.logic.LogicTestManager.TestDataHelper;
import seedu.oneline.logic.commands.AddCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;

public class AddCommandTest extends LogicTestManager {

    @Test
    public void addCommand_invalidTaskData_constrantsMessage() throws Exception {
        assertCommandBehavior(
                "add []\\[;] .from Monday .to Tuesday .due Wednesday .every week", TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
        // TODO: ADD PROPER CONSTRAINTS
    }

    @Test
    public void addCommand_validTask_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // setup expectations
        Task toBeAdded = helper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void addCommand_duplicateTask_duplicateMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // setup expectations
        Task toBeAdded = helper.myTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());
    }
    
}
