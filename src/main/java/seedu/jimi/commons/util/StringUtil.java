package seedu.jimi.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@@author A0140133B
/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    
    private static final int DEFAULT_EDIT_DISTANCE = 2; // Edit distance to quantify string similarity.
    
    // Percentage of containing string for the contained string to be a valid substring.
    private static final double SUBSTRING_ALLOWANCE = 1.0 / 3.0; // = a third 
    
    /** Returns true if any of the strings in {@code keywords} nearly matches {@code query}. */
    public static boolean isAnyNearMatch(String query, List<String> keywords) {
        if (query == null || keywords == null) {
            return false;
        }
        CollectionUtil.assertNoNullElements(keywords);
        return keywords.stream()
                .filter(kw -> isNearMatch(kw, query))
                .findAny()
                .isPresent();
    }

    /** 
     * Returns true if {@code query} is a near match of {@code source} or are valid substrings of each other. <br>
     * This is also a recursive driver method.
     */
    public static boolean isNearMatch(String source, String query) {
        if (source == null || query == null) {
            return false;
        }
        return isValidSubstrings(source, query) || isNearMatch(source, query, DEFAULT_EDIT_DISTANCE);
    }
    
    /**
     * Returns true if {@code query} is a near match of {@code source} <br>
     * <br>
     * Edit distance is a way of quantifying string similarity. It is simply the minimum number of operations <br>
     * to transform a string to another. These operations include: deleting a character, inserting a character, <br>
     * replacing a character and transposing two characters. This method approximates string similarity to an <br>
     * edit distance of {@code editDistance}. <br>
     * <br>
     * For an edit distance of 1:
     * <ul>
     * <li> {@code query} is the same as {@code source} but missing a character.
     * <li> {@code query} is the same as {@code source} but differing by a character.
     * <li> {@code query} is the same as {@code source} but have two transposed characters.
     * <li> {@code query} is the same as {@code source} but has an extra character.
     * </ul>
     * 
     * All matches are case-insensitive and have all spaces removed in both strings prior to comparison. <br>
     * Conditions are also short-circuited for performance and memory. <br>
     * This is a recursive method that executes {@code editDistance} times.
     * 
     * @param source String to match against
     * @param query String to match with
     * @return true if {@code query} nearly matches {@code source}.
     */
    private static boolean isNearMatch(String source, String query, int editDistance) {
        assert source != null && query != null;
        // Removing all spaces and converting both strings to lower case.
        String sourceNoSpaces = source.toLowerCase().replaceAll("\\s+", "");
        String queryNoSpaces = query.toLowerCase().replaceAll("\\s+", "");
        
        Set<String> transposedDict = DictionaryUtil.generateTransposedChar(sourceNoSpaces);
        if (transposedDict.contains(queryNoSpaces)) {
            return true; // Similar by two transposed characters.
        }
        Set<String> missingCharDict = DictionaryUtil.generateMissingChar(sourceNoSpaces);
        if (missingCharDict.contains(queryNoSpaces)) {
            return true; // Similar by a missing character.
        }
        Set<String> oneCharDiffDict = DictionaryUtil.generateReplacedChar(sourceNoSpaces);
        if (oneCharDiffDict.contains(queryNoSpaces)) {
            return true; // Similar by a differing character.
        }
        Set<String> oneExtraCharDict = DictionaryUtil.generateExtraChar(sourceNoSpaces);
        if (oneExtraCharDict.contains(queryNoSpaces)) {
            return true; // Similar by an extra character.
        }
        if (editDistance <= 1) {
            return false; // Recursion base case.
        }
        
        // Combining all dictionaries.
        Set<String> combined = DictionaryUtil.join(transposedDict, missingCharDict, oneCharDiffDict, oneExtraCharDict);
        
        // Perform string approximation methods again.
        return combined.stream()
                .filter(kw -> isNearMatch(kw, queryNoSpaces, editDistance - 1))
                .findAny()
                .isPresent();
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }
    
    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
    
    /** 
     * Returns the first word separated by spaces in {@code text}. 
     * Adapted from stackoverflow.com/questions/5067942/what-is-the-best-way-to-extract-the-first-word-from-a-string-in-java <br> 
     * because I'm too lazy to create my own method.
     */
    public static String getFirstWord(String text) {
        assert text != null;
        String trimmed = new String(text.trim());
        if (trimmed.indexOf(' ') > -1) { // Check if there is more than one word.
            return trimmed.substring(0, trimmed.indexOf(' ')); // Extract first word.
        } else {
            return trimmed; // Text is the first word itself.
        }
    }
    
    /**
     * Returns a string that has all items in {@code items} on its own indexed new line.
     * @param items items to be indexed.
     * @return a string that has all items on an indexed new line.
     */
    public static <T> String toIndexedListString(List<T> items) {
        assert items != null;
        CollectionUtil.assertNoNullElements(items);
        return IntStream.range(0, items.size())
                .mapToObj(i -> (i + 1) + ". " + items.get(i).toString())
                .collect(Collectors.joining("\n"));
    }
    
    /** 
     * Returns true if {@code src} and {@code query} are valid substrings of each other. 
     * Validity is checked by {@code SUBSTRING_ALLOWANCE} where the substring length 
     * is a substantial percentage of the source string. 
     */
    public static boolean isValidSubstrings(String src, String query) {
        if (src == null || query == null) {
            return false;
        }
        if (!(src.contains(query) || query.contains(src))) {
            return false;
        }
        // Validating substring length percentage, returns true if above given allowance.
        return (double) Math.min(src.length(), query.length())
                / (double) Math.max(src.length(), query.length()) >= SUBSTRING_ALLOWANCE;
    }
}
