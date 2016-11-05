package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.ExitCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.parser.ExitCommandParser;

public class ExitCommandParserTest {
    ExitCommandParser parser = new ExitCommandParser();
    
    @Test
    public void prepareCommand_noArguments(){
        /*
         * Test the exit command with no arguments - should be accepted
         */
        TaskCommand command = parser.prepareCommand("");
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }
    
    @Test
    public void prepareCommand_withArguments(){
        /*
         * Test the exit command with some arguments - should be rejected
         */
        TaskCommand command = parser.prepareCommand("extra arguments");
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
    }
}
