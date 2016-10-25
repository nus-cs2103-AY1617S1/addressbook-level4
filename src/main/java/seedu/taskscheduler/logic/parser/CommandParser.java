package seedu.taskscheduler.logic.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskscheduler.commons.util.StringUtil;
import seedu.taskscheduler.logic.commands.Command;

//@@author A0148145E

/**
 * Represents a command parser with hidden internal logic and the ability to be executed.
 */
public abstract class CommandParser {

    protected static final String START_DATE_DELIMITER = "s/";
    protected static final String END_DATE_DELIMITER = "e/";
    
    protected static final Pattern INDEX_COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)(?<arguments>.*)");

    protected static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    protected static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    protected static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " "
                    + START_DATE_DELIMITER
                    + "(?<startDate>[^/]*)"
                    + " "
                    + END_DATE_DELIMITER
                    + "(?<endDate>.*)"
                    + "(?>\\s+\\bat\\b)"
                    + "(?<address>.*)"
                    ); 
 
    protected static final Pattern DEADLINE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+?)"
                    + "(?>(\\s+\\b(by|on)\\b))"
                    + "(?<endDate>.*)"
                    );
    
    protected static final Pattern FLOATING_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[\\p{Alnum} ]+)");

    protected static final Pattern SETPATH_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[\\p{Alnum}|/|:|\\s+]+)"); 
    
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    protected Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    public abstract Command prepareCommand(String args);
}
