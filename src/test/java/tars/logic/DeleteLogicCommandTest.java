package tars.logic;

import static tars.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_TASK_CANNOT_BE_FOUND;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tars.logic.commands.DeleteCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;
import tars.ui.formatter.Formatter;

/**
 * Logic command test for delete
 */
public class DeleteLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_delete_InvalidArgsFormatErrorMessageShown()
            throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("del ", expectedMessage);
    }

    @Test
    public void execute_delete_IndexNotFoundErrorMessageShown()
            throws Exception {
        assertIndexNotFoundBehaviorForCommand("del");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Tars expectedTars = helper.generateTars(threeTasks);
        expectedTars.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("del 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                        "1.\t" + threeTasks.get(1) + "\n"),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_delete_range() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Tars expectedTars = helper.generateTars(threeTasks);
        helper.addToModel(model, threeTasks);

        // delete tasks within range
        expectedTars.removeTask(threeTasks.get(0));
        expectedTars.removeTask(threeTasks.get(1));
        expectedTars.removeTask(threeTasks.get(2));

        ArrayList<ReadOnlyTask> deletedTasks = new ArrayList<ReadOnlyTask>();
        deletedTasks.add(threeTasks.get(0));
        deletedTasks.add(threeTasks.get(1));
        deletedTasks.add(threeTasks.get(2));

        String formattedResult = new Formatter().formatTaskList(deletedTasks);
        assertCommandBehavior("del 1..3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                        formattedResult),
                expectedTars, expectedTars.getTaskList());
    }

    // @@author A0139924W
    @Test
    public void execute_undoAndRedo_delSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeRemoved = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeRemoved);

        model.addTask(toBeRemoved);
        expectedTars.removeTask(toBeRemoved);

        // execute command and verify result
        assertCommandBehavior("del 1",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                        "1.\t" + toBeRemoved + "\n"),
                expectedTars, expectedTars.getTaskList());

        expectedTars.addTask(toBeRemoved);
        
        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS,
                        String.format(DeleteCommand.MESSAGE_UNDO,
                                "1.\t" + toBeRemoved + "\n")),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeTask(toBeRemoved);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS,
                        String.format(DeleteCommand.MESSAGE_REDO,
                                "1.\t" + toBeRemoved + "\n")),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_undoAndRedo_delUnsuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeRemoved = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeRemoved);

        model.addTask(toBeRemoved);
        expectedTars.removeTask(toBeRemoved);

        // execute command and verify result
        assertCommandBehavior("del 1",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                        "1.\t" + toBeRemoved + "\n"),
                expectedTars, expectedTars.getTaskList());

        expectedTars.addTask(toBeRemoved);
        model.addTask(toBeRemoved);

        // execute undo and verify result
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_DUPLICATE_TASK),
                expectedTars, expectedTars.getTaskList());

        expectedTars.removeTask(toBeRemoved);
        model.deleteTask(toBeRemoved);

        // execute redo and verify result
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_UNSUCCESS,
                        MESSAGE_TASK_CANNOT_BE_FOUND),
                expectedTars, expectedTars.getTaskList());
    }

}
