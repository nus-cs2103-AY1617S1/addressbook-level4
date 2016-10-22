package tars.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tars.commons.exceptions.IllegalValueException;
import tars.logic.parser.Prefix;

/**
 * Container for methods which extract data from string
 */
public class ExtractorUtil {
    private static final String EMPTY_STRING = "";
    private static final String ONE_EMPTY_SPACE = " ";

    /**
     * Extracts the new task's dateTimes from the rsv command's dateTime arguments string. Merges
     * duplicate dateTime strings.
     */
    public static Set<String> getDateTimeStringSetFromArgs(String dateTimeArguments, Prefix prefix)
            throws IllegalValueException {
        // no dateTime
        if (EMPTY_STRING.equals(dateTimeArguments)) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> dateTimeStrings = Arrays.asList(
                dateTimeArguments.replaceFirst(prefix.prefix + ONE_EMPTY_SPACE, EMPTY_STRING)
                        .split(ONE_EMPTY_SPACE + prefix.prefix + ONE_EMPTY_SPACE));
        return new HashSet<>(dateTimeStrings);
    }

    /**
     * Extracts the new task's recurring args from add command.
     * 
     * @@author A0140022H
     */
    public static String[] getRecurringFromArgs(String recurringArguments, Prefix prefix)
            throws IllegalValueException {
        recurringArguments = recurringArguments.replaceFirst(prefix.prefix, EMPTY_STRING).trim();
        String[] recurringString = recurringArguments.split(ONE_EMPTY_SPACE);

        return recurringString;
    }
}
