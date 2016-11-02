//@@author A0093896H
package seedu.todo.logic;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.UntagCommand;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.Task;

/**
 * Test class for the untag command's logic
 */
public class UntagLogicTest extends CommandLogicTest {
    
    @Test
    public void execute_untag_successful() throws IllegalValueException {
        Task toBeUntagged = helper.generateFullTask(0);
        expectedTDL.addTask(toBeUntagged);
        
        Task toBeUntaggedInModel = helper.generateFullTask(0);
        toBeUntaggedInModel.addTag(new Tag("yay"));
        model.addTask(toBeUntaggedInModel);        
        
        // execute command and verify result
        assertCommandBehavior("untag 1 yay",
                String.format(UntagCommand.MESSAGE_SUCCESS, toBeUntagged.getName()),
                expectedTDL,
                expectedTDL.getTaskList());

    }
    
    @Test
    public void execute_untagInvalidArgsFormat_errorMessageShown() throws IllegalValueException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("untag", expectedMessage);
    }

    @Test
    public void execute_untagIndexNotFound_errorMessageShown() throws IllegalValueException {
        assertIndexNotFoundBehaviorForCommand("untag");
    }
}
