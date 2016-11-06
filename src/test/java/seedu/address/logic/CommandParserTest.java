package seedu.address.logic;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.CommandParserHelper;

public class CommandParserTest {

    CommandParser parser;
    
    @Test
    public void prepareAdd_emptyArgument_returnIncorrectCommand() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add     ");
        IncorrectCommand expectedFeedback = new IncorrectCommand(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.getMessageUsage()));
        if (command instanceof IncorrectCommand) {
            assertEquals(((IncorrectCommand) command).feedbackToUser, expectedFeedback.feedbackToUser);
        } else {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_validArgument_returnAddCommand() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add eat bingsu");
        if (command instanceof AddCommand) {
            assert true;
        } else {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_invalidArgument_returnIncorrectCommand() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add eat bingsu from 10:30am from 10:40am");
        IncorrectCommand expectedFeedback = new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CommandParserHelper.getMessageRepeatedStartTime() + CommandParser.getNewLineString() 
                + AddCommand.getMessageUsage()));
        if (command instanceof IncorrectCommand) {
            assertEquals(((IncorrectCommand) command).feedbackToUser, expectedFeedback.feedbackToUser);
        } else {
            assert false;
        }
    }
}
