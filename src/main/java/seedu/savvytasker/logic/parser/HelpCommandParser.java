//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.HelpCommand;

public class HelpCommandParser implements CommandParser<HelpCommand> {
    private static final String HEADER = "help";
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
    public HelpCommand parse(String commandText) throws ParseException {
        if (commandText.trim().equalsIgnoreCase(HEADER)) {
            return new HelpCommand();
        }

        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
