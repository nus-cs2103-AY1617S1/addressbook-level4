//@@author A0139916U
package seedu.savvytasker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.ListCommand;
import seedu.savvytasker.model.task.ListType;

public class ListCommandParser implements CommandParser<ListCommand> {
    private static final String HEADER = "list";
    private static final String READABLE_FORMAT = HEADER+" [t/LIST_TYPE]";
    
    private static final String REGEX_REF_LIST_TYPE = "ListType";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s*((?<=\\s)t/(?<"+REGEX_REF_LIST_TYPE+">[^/]+))?",
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
    public ListCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            ListType listType = parseListType(matcher.group(REGEX_REF_LIST_TYPE));
            
            return new ListCommand(listType);
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
