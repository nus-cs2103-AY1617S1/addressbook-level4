package seedu.oneline.logic;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.DoneCommand;

public class DoneCommandTest extends LogicTestManager{
    
    //---------------- Tests for DoneCommand --------------------------------------
    /*
     * Invalid equivalence partitions for index: null, signed integer, non-numeric characters
     * Invalid equivalence partitions for index: index larger than no. of tasks in taskBook
     * The two test cases below test invalid input above one by one.
     */
    
    // TODO CHECK IF CORRECT EXECUTION
    
    @Test
    public void execute_doneInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }
    
    @Test
    public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }
    
}
