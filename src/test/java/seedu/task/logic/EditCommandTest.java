package seedu.task.logic;

import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Task;

public class EditCommandTest extends CommandTest {

    /* Need help with this test to test for duplicates not allowed. By running the program, the exception can be caught
     * */

    @Ignore
    @Test
    public void execute_editFloatTask_duplicate() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingFloatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        toBeAdded = helper.computingEditedNameFloatTask();
        expectedAB.addTask(toBeAdded);
        
        // setup model
        model.addTask(helper.computingFloatTask());
        model.addTask(helper.computingEditedNameFloatTask());
        Task toBeEdited = helper.computingFloatTask();
        model.editTask(toBeEdited, 0);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited),
                String.format(EditTaskCommand.MESSAGE_DUPLICATE_TASK, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_editFloatTask_name_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();
        expectedAB.editTask(toBeEdited, 0);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_editFloatTask_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedNameFloatTask();
        expectedAB.editTask(toBeEdited, 0);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_editTask_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDescFloatTask();
        expectedAB.editTask(toBeEdited, 0);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
}
