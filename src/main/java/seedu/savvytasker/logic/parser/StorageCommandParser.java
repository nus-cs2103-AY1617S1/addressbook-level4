//@@author A0139916U
package seedu.savvytasker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.StorageCommand;

public class StorageCommandParser implements CommandParser<StorageCommand> {
    private static final String HEADER = "storage";
    private static final String READABLE_FORMAT = HEADER+" NEW_PATH";
    
    private static final String REGEX_REF_PATH = "Path";

    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s+(?<"+REGEX_REF_PATH+">.*)",
            Pattern.CASE_INSENSITIVE);
    
    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    public StorageCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String path = matcher.group(REGEX_REF_PATH).trim();
            
            if (!path.isEmpty()) {
                return new StorageCommand(path);
            }
        }
        
        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
