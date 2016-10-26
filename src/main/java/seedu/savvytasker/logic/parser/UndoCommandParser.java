//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.UndoCommand;

public class UndoCommandParser implements CommandParser<UndoCommand> {
    private static final String HEADER = "undo";
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
    public UndoCommand parse(String commandText) throws ParseException {
        if (commandText.trim().equalsIgnoreCase(HEADER)) {
            return new UndoCommand();
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
