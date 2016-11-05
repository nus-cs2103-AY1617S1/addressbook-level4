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
        //assertCommandBehavior(
        //        "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        //assertCommandBehavior(
        //        "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        //assertCommandBehavior(
        //        "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

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
