package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.MarkCommand;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.Task;
//@@author A0138967J
/**
 * Test class for the mark command's logic
 */
public class MarkLogicTest extends CommandLogicTest {

    @Test
    public void execute_mark_successful_tmr() throws IllegalValueException {
        Task toBeMarked = helper.generateFullTaskTmr(0);
        expectedTDL.addTask(toBeMarked);
        
        toBeMarked.setCompletion(new Completion(true));
        model.addTask(helper.generateFullTaskTmr(0));
        
        assertCommandBehavior("mark 1",
                String.format(MarkCommand.MESSAGE_SUCCESS, 1, toBeMarked),
                expectedTDL,
                (new DoDoBird()).getTaskList());

    }

    @Test
    public void execute_mark_successful_today() throws IllegalValueException {
        Task toBeMarkedToday = helper.generateFullTaskToday(0);
        expectedTDL.addTask(toBeMarkedToday);
        
        toBeMarkedToday.setCompletion(new Completion(true));
        model.addTask(helper.generateFullTaskToday(0));
        
        assertCommandBehavior("mark 1",
                String.format(MarkCommand.MESSAGE_SUCCESS, 1, toBeMarkedToday),
                expectedTDL,
                (new DoDoBird()).getTaskList());

    }
    //@@author
    @Test
    public void execute_markInvalidArgsFormat_errorMessageShown() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("mark", expectedMessage);
    }

    @Test
    public void execute_markIndexNotFound_errorMessageShown() throws IllegalValueException {
        assertIndexNotFoundBehaviorForCommand("mark");
    }
}
