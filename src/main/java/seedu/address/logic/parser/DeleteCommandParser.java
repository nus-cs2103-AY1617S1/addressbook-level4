package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.DeleteCommand;

public class DeleteCommandParser extends CommandParser<DeleteCommand> {
    private static final String HEADER = "delete";
    private static final String READABLE_FORMAT = "delete INDEX [MORE_INDEX]";
    
    private static final String REGEX_REF_INDICES = "Indices";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            "delete\\s+(?<"+REGEX_REF_INDICES+">(\\d+(\\s+|$))+)", Pattern.CASE_INSENSITIVE);
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected DeleteCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String captured = matcher.group(REGEX_REF_INDICES).trim();
            String[] strIndices = captured.split("\\s+");
            int[] indices = Arrays
                    .stream(strIndices)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            // TODO: Create DeleteCommand
        }
        
        throw new ParseException(commandText);
    }

}
