package tars.commons.util;

import tars.commons.exceptions.IllegalValueException;
import tars.logic.parser.Prefix;

/**
 * Container for methods which extract data from string
 */
public class ExtractorUtil {
    /**
     * Extracts the new task's recurring args from add command.
     * 
     * @@author A0140022H
     */
    public static String[] getRecurringFromArgs(String recurringArguments,
            Prefix prefix) throws IllegalValueException {
        recurringArguments = recurringArguments
                .replaceFirst(prefix.value, StringUtil.EMPTY_STRING).trim();
        String[] recurringString =
                recurringArguments.split(StringUtil.STRING_WHITESPACE);

        return recurringString;
    }
}
