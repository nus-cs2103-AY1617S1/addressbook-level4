package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.task.ListType;

public class ListCommandParser extends CommandParser<ListCommand> {
    private static final String HEADER = "list";
    private static final String READABLE_FORMAT = "list [t/LIST_TYPE]";
    
    private static final String REGEX_REF_LIST_TYPE = "ListType";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            "list\\s*((?<=\\s)t/(?<"+REGEX_REF_LIST_TYPE+">[^/]+))?",
            Pattern.CASE_INSENSITIVE);
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected ListCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            parseListType(matcher.group(REGEX_REF_LIST_TYPE));
            // TODO: return ListCommand
        }
        
        throw new ParseException(commandText, String.format(
            Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

    private ListType parseListType(String listTypeText) throws ParseException {
        if (listTypeText == null)
            return null;
        
        try {
            listTypeText = listTypeText.trim();
            return ListType.valueOfIgnoreCase(listTypeText.replaceAll("\\s", ""));
        } catch (IllegalArgumentException ex) {
            throw new ParseException(listTypeText, "LIST_TYPE: Unknown type '" + listTypeText + "'");
        }
    }
}
