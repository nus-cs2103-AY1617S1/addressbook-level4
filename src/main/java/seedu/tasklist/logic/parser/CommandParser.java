package seedu.tasklist.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.logic.commands.Command;

/**
 * Parser interface for interaction with the Command classes.
 */
public interface CommandParser {
    
    /**
     * Parses arguments in the context of the requested task command.
     * @param args full command args string
     * @return the prepared command
     */
    public Command prepare(String args);
    
    /**
     * Used for initial separation of command word and args.
     */
    final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<title>[^/]+)"
                    + "(?<isDescriptionPrivate>p?)(?<description>(?: d/[^/]+)*)"
                    + "(?<isStartDateTimePrivate>p?)(?<startDateTime>(?: s/[^/]+)*)"
                    + "(?<isEndDateTimePrivate>p?)(?<endDateTime>(?: e/[^/]+)*)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    final Pattern EDIT_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>[^/\\s]+)"
                    + "(?<title>(?: [^/]+)*)"
                    + "(?<isDescriptionPrivate>p?)(?<description>(?: d/[^/]+)*)"
                    + "(?<isStartDateTimePrivate>p?)(?<startDateTime>(?: s/[^/]+)*)"
                    + "(?<isEndDateTimePrivate>p?)(?<endDateTime>(?: e/[^/]+)*)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
        
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    default Optional<Integer> parseIndex(String command) {
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
    
    /**
     * Extracts the the task's details from the arguments string
     */
    default String getDetailsFromArgs(String detailsArguments) {
        if (detailsArguments.isEmpty()) {
            return "";
        }
        return detailsArguments.trim().substring(detailsArguments.indexOf("/"), detailsArguments.length()-1);
    }
    
    /**
     * Extracts the task's tags from the tag arguments string.
     * Merges duplicate tag strings.
     */
    default Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }
    
}
