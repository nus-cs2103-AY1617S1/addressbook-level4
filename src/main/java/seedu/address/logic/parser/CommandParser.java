package seedu.address.logic.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;

public abstract class CommandParser {

    protected static final Pattern INDEX_COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)(?<arguments>.*)");

    protected static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    protected static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    protected static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^/]*)"
                    + " e/(?<endDate>.*)"
                    + "(?>\\s+\\bat\\b)"
                    + "(?<address>.*)"
                    ); 
 
    protected static final Pattern DEADLINE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+?)"
                    + "(?>(\\s+\\b(by|in|at)\\b))"
                    + "(?<endDate>.*)"
                    );
    
    protected static final Pattern FLOATING_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[\\p{Alnum} ]+)");
    
    protected static final Pattern SETPATH_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[\\p{Alnum}|/]+)");   //    data/  <---
    
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
