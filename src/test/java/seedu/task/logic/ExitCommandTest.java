package seedu.task.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.task.logic.commands.CommandResult;

/**
 * Responsible for testing the execution of ExitCommand
 * @@author A0121608N
 */

public class ExitCommandTest extends CommandTest{

    @Test
    public void execute_Exit(){
        CommandResult result = logic.execute("exit");
        String expectedMessage = "Exiting Task Book as requested ...";
        assertEquals(expectedMessage, result.feedbackToUser);
    }
    
}
