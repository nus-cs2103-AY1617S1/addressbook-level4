package seedu.address.logic;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.CommandParser;

public class ParserAddCommandTest {

    CommandParser parser = new CommandParser();
    
    @Before
    public void prepare() {

        p.setSalaryManager(new SalaryManagerStub()); // dependency injection
        
    }

    @Test
    public void prepareAdd_noArguments() {
        String userInput = "add       ";
        Command command = parser.parseCommand(userInput);
        if (command instanceof IncorrectCommand) {
            IncorrectCommand incorrectCommand = (IncorrectCommand) command;
            assertEquals(incorrectCommand.feedbackToUser,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            fail("Command is not an instance of IncorrectCommand");
        }
    }

    @Test
    public void prepareAdd_validCommandOne() {
        String userInput = "eat bingsu with elsa, linhan";
        Command command = new CommandParser().parseCommand(userInput.trim());

        if (command instanceof AddCommand) {
            AddCommand incorrectCommand = (AddCommand) command;
            assertEquals(incorrectCommand.feedbackToUser,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            fail("Command is not an instance of AddCommand");
        }
    }

    @Test
    public void prepareAdd_validCommandTwo() {
        String userInput = "eat bingsu with elsa, linhan, hanxiang -high";
        final Matcher matcher = CommandParser.getRegexAddTask().matcher(userInput.trim());

        if (!matcher.matches()) {
            fail("No match found");
        }

        assertEquals(matcher.group("taskName").toString(), "eat bingsu with elsa, linhan, hanxiang");
        assertEquals(matcher.group("endDateFormatOne"), null);
        assertEquals(matcher.group("startDateFormatOne"), null);
        assertEquals(matcher.group("startDateFormatTwo"), null);
        assertEquals(matcher.group("startDateFormatThree"), null);
        assertEquals(matcher.group("endDateFormatTwo"), null);
        assertEquals(matcher.group("endDateFormatThree"), null);
        assertEquals(matcher.group("recurrenceRate"), null);
        assertEquals(matcher.group("priority").toString(), "high");
    }

    @Test
    public void prepareAdd_validCommandThree() {
        String userInput = "go zoo with elsa, linhan, from 13 Sep 11:30pm to 12pm -low";
        final Matcher matcher = CommandParser.getRegexAddTask().matcher(userInput.trim());

        if (!matcher.matches()) {
            fail("No match found");
        }

        assertEquals(matcher.group("taskName").toString(), "go zoo with elsa, linhan");
        assertEquals(matcher.group("endDateFormatOne"), null);
        assertEquals(matcher.group("startDateFormatOne").toString(), "13 Sep 11:30pm");
        assertEquals(matcher.group("startDateFormatTwo"), null);
        assertEquals(matcher.group("startDateFormatThree"), null);
        assertEquals(matcher.group("endDateFormatTwo").toString(), "12pm");
        assertEquals(matcher.group("endDateFormatThree"), null);
        assertEquals(matcher.group("recurrenceRate"), null);
        assertEquals(matcher.group("priority").toString(), "low");
    }
}
