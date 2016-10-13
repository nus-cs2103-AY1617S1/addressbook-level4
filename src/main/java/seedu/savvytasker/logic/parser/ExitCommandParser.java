package seedu.savvytasker.logic.parser;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.ExitCommand;

public class ExitCommandParser extends CommandParser<ExitCommand> {
    private static final String HEADER = "exit";
    private static final String READABLE_FORMAT = "exit";
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected ExitCommand parse(String commandText) throws ParseException {
        if (commandText.trim().equalsIgnoreCase(HEADER)) {
            return new ExitCommand();
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
