package tars.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;

/**
 * Container for methods which extract data from string
 */
public class ExtractorUtil {
    private static final int INVALID_POS = -1;

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    public static Set<String> getTagsFromArgs(String tagArguments, Flag prefix) throws IllegalValueException {
        // no tags
        if (tagArguments.equals("")) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments
                .replaceFirst(prefix.prefix + " ", "")
                .split(" " + prefix.prefix + " "));
        return new HashSet<>(tagStrings);
    }
    
    /**
     * Extracts the new task's dateTimes from the rsv command's dateTime arguments string.
     * Merges duplicate dateTime strings.
     */
    public static Set<String> getDateTimeStringSetFromArgs(String dateTimeArguments, Flag prefix) throws IllegalValueException {
        // no dateTime
        if (dateTimeArguments.equals("")) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> dateTimeStrings = Arrays.asList(dateTimeArguments
                .replaceFirst(prefix.prefix + " ", "")
                .split(" " + prefix.prefix + " "));
        return new HashSet<>(dateTimeStrings);
    }

    /**
     * Gets all flag positions from arguments string
     * 
     * @@author A0139924W
     */
    public static TreeMap<Integer, Flag> getFlagPositon(String args, Flag[] prefixes) {
        TreeMap<Integer, Flag> flagsPosMap = new TreeMap<Integer, Flag>();

        if (args != null && args.length() > 0 && prefixes != null && prefixes.length > 0) {
            args = args.trim();
            for (int i = 0; i < prefixes.length; i++) {
                int curIndexPos = INVALID_POS;
                do {
                    curIndexPos = args.indexOf(prefixes[i].prefix, curIndexPos + 1);
                    if (curIndexPos >= 0) {
                        flagsPosMap.put(curIndexPos, prefixes[i]);
                    }
                    if (!prefixes[i].hasMultiple) {
                        break;
                    }
                } while (curIndexPos >= 0);
            }
        }

        return flagsPosMap;
    }

    /**
     * Extracts the option's flag and arg from arguments string.
     * 
     * @@author A0139924W
     */
    public static HashMap<Flag, String> getArguments(String args, Flag[] flags, TreeMap<Integer, Flag> flagsPosMap) {
        HashMap<Flag, String> flagsValueMap = new HashMap<Flag, String>();

        if (args != null && args.length() > 0 && flags != null && flags.length > 0) {
            args = args.trim();

            // initialize the flagsValueMap
            for (int i = 0; i < flags.length; i++) {
                flagsValueMap.put(flags[i], "");
            }

            int endPos = args.length();
            for (Map.Entry<Integer, Flag> entry : flagsPosMap.descendingMap().entrySet()) {
                Flag prefix = entry.getValue();
                Integer pos = entry.getKey();

                if (pos == INVALID_POS) {
                    continue;
                }

                String arg = args.substring(pos, endPos);
                endPos = pos;

                flagsValueMap.put(prefix, flagsValueMap.get(prefix).concat(" ").concat(arg));
            }
        }

        return flagsValueMap;
    }
}
