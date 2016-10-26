//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.RedoCommand;

public class RedoCommandParser implements CommandParser<RedoCommand> {
    private static final String HEADER = "redo";
    private static final String READABLE_FORMAT = HEADER;
    
    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    public RedoCommand parse(String commandText) throws ParseException {
        if (commandText.trim().equalsIgnoreCase(HEADER)) {
            return new RedoCommand();
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
