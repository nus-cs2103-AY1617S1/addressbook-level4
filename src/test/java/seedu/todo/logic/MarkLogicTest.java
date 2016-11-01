//@@author A0093896H
package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.MarkCommand;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.Task;

/**
 * Test class for the mark command's logic
 */
public class MarkLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_mark_successful() throws IllegalValueException {
        Task toBeMarked = helper.generateFullTask(0);
        expectedTDL.addTask(toBeMarked);
        
        toBeMarked.setCompletion(new Completion(true));
        model.addTask(helper.generateFullTask(0));
        
        assertCommandBehavior("mark 1",
                String.format(MarkCommand.MESSAGE_SUCCESS, toBeMarked.getName()),
                expectedTDL,
                expectedTDL.getTaskList());

    }
    
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
