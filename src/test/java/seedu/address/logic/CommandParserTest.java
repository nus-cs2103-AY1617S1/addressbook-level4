package seedu.address.logic;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.CommandParser;

public class CommandParserTest {

    CommandParser parser;
    
    @Test
    public void prepareAdd_emptyArgument() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add     ");
        IncorrectCommand expectedFeedback = new IncorrectCommand(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        if (command instanceof IncorrectCommand) {
            assertEquals(((IncorrectCommand) command).feedbackToUser, expectedFeedback.feedbackToUser);
        } else {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_validArgument() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add eat bingsu");
        if (command instanceof AddCommand) {
            assert true;
        } else {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_invalidArgument() {
        parser = new CommandParser();
        Command command = parser.parseCommand("add eat bingsu from 10:30am from 10:40am");
        IncorrectCommand expectedFeedback = new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                AddCommand.MESSAGE_USAGE + "\n" + "Repeated start times are not allowed."));
        if (command instanceof IncorrectCommand) {
            assertEquals(((IncorrectCommand) command).feedbackToUser, expectedFeedback.feedbackToUser);
        } else {
            assert false;
        }
    }
}
