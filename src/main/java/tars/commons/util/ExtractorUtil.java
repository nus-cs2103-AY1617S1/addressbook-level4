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
 *
 */
public class ExtractorUtil {

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
     * Gets all flag position from arguments string
     */
    public static TreeMap<Integer, Flag> getFlagPositon(String args, Flag[] prefixes) {
        args = args.trim();
        TreeMap<Integer, Flag> flagsPosMap = new TreeMap<Integer, Flag>();

        if (args != null && args.length() > 0 && prefixes.length > 0) {
            for (int i = 0; i < prefixes.length; i++) {
                int indexOf = -1;
                do {
                    indexOf = args.indexOf(prefixes[i].prefix, indexOf + 1);
                    if (indexOf >= 0) {
                        flagsPosMap.put(indexOf, prefixes[i]);
                    }
                    if (!prefixes[i].hasMultiple) {
                        break;
                    }
                } while (indexOf >= 0);
            }
        }

        return flagsPosMap;
    }

    /**
     * Extracts the option's flag and arg from arguments string.
     */
    public static HashMap<Flag, String> getArguments(String args, Flag[] prefixes, TreeMap<Integer, Flag> flagsPosMap) {
        args = args.trim();
        HashMap<Flag, String> flagsValueMap = new HashMap<Flag, String>();

        if (args != null && args.length() > 0 && prefixes.length > 0) {
            // initialize the flagsValueMap
            for (int i = 0; i < prefixes.length; i++) {
                flagsValueMap.put(prefixes[i], "");
            }

            int endPos = args.length();
            for (Map.Entry<Integer, Flag> entry : flagsPosMap.descendingMap().entrySet()) {
                Flag prefix = entry.getValue();
                Integer pos = entry.getKey();

                if(pos == -1) {
                    continue;
                }

                String arg = args.substring(pos, endPos);
                endPos = pos;

                if (flagsValueMap.containsKey(prefix)) {
                    flagsValueMap.put(prefix, flagsValueMap.get(prefix).concat(" ").concat(arg));
                } else {
                    flagsValueMap.put(prefix, arg);
                }
            }
        }

        return flagsValueMap;
    }
}
