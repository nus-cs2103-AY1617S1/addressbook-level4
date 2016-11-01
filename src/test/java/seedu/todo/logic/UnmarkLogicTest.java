//@@author A0093896H
package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.UnmarkCommand;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.Task;

/**
 * Test class for the unmark command's logic
 */
public class UnmarkLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_unmark_successful() throws IllegalValueException {
        Task toBeMarked = helper.generateFullTask(0);
        expectedTDL.addTask(toBeMarked);
        
        Task toBeMarkedInModel = helper.generateFullTask(0);
        toBeMarkedInModel.setCompletion(new Completion(true));
        model.addTask(toBeMarkedInModel);        
       
        logic.execute("search done"); //have to search for completed tasks first
        
        assertCommandBehavior("unmark 1",
                String.format(UnmarkCommand.MESSAGE_SUCCESS, toBeMarked.getName()),
                expectedTDL,
                expectedTDL.getTaskList());

    }
    
    @Test
    public void execute_unmarkInvalidArgsFormat_errorMessageShown() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("unmark", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("unmark");
    }
}
