package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParser extends CommandParser<ClearCommand> {
    private static final String HEADER = "clear";
    private static final String READABLE_FORMAT = "clear";
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected ClearCommand parse(String commandText) throws ParseException {
        if (commandText.trim().equalsIgnoreCase(HEADER)) {
            return new ClearCommand();
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
