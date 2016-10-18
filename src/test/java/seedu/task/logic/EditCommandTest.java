package seedu.task.logic;

import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Task;
import seedu.taskcommons.core.Messages;

public class EditCommandTest extends CommandTest {

    @Test
    public void execute_editFloatTask_duplicate() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingFloatTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeAdded2 = helper.computingEditedNameFloatTask();
        expectedAB.addTask(toBeAdded2);
        Task toBeEdited = helper.computingFloatTask();

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddFloatTaskCommand(toBeAdded), helper.generateAddFloatTaskCommand(toBeAdded2),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,2),
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
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
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
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
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
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_editTask_invalidIndex_unsuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,2),
                String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getTaskList());

    }
}
