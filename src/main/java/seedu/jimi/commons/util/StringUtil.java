package seedu.jimi.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
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
    private static final double SUBSTRING_ALLOWANCE = 0.25; 
    
    /** Returns true if any of the strings in {@code keywords} nearly matches {@code query}. */
    public static boolean isAnyNearMatch(String query, List<String> keywords) {
        return keywords.stream()
                .filter(kw -> isNearMatch(kw, query))
                .findAny()
                .isPresent();
    }

    /** 
     * Returns true if {@code query} is a near match of {@code source}. <br>
     * This is a recursive driver method.
     */
    public static boolean isNearMatch(String source, String query) {
        return isNearMatch(source, query, DEFAULT_EDIT_DISTANCE);
    }
    
    /**
     * Returns true if {@code query} is a near match of {@code source} <br>
     * <br>
     * For an edit distance of 1:
     * <ul>
     * <li> {@code query} and {@code source} are valid substrings of each other.
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
        
        if (isValidSubstrings(sourceNoSpaces, queryNoSpaces)) {
            return true; // Strings are valid substrings of each other.
        }
        Set<String> transposedDict = Dictionary.generateTransposedChar(sourceNoSpaces);
        if (transposedDict.contains(queryNoSpaces)) {
            return true; // Similar by two transposed characters.
        }
        Set<String> missingCharDict = Dictionary.generateMissingChar(sourceNoSpaces);
        if (missingCharDict.contains(queryNoSpaces)) {
            return true; // Similar by a missing character.
        }
        Set<String> oneCharDiffDict = Dictionary.generateReplacedChar(sourceNoSpaces);
        if (oneCharDiffDict.contains(queryNoSpaces)) {
            return true; // Similar by a differing character.
        }
        Set<String> oneExtraCharDict = Dictionary.generateExtraChar(sourceNoSpaces);
        if (oneExtraCharDict.contains(queryNoSpaces)) {
            return true; // Similar by an extra character.
        }
        if (editDistance <= 1) {
            return false; // Recursion base case.
        }
        
        // Combining all dictionaries.
        Set<String> combined = Dictionary.join(transposedDict, missingCharDict, oneCharDiffDict, oneExtraCharDict);
        
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
     * Adapted from StackOverflow because I'm too lazy to create my own 
     * method that extracts the first word of a string.
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

class Dictionary {
    
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /** Combines multiple dictionaries to one */
    @SafeVarargs
    public static Set<String> join(Set<String>... sets) {
        assert sets != null;
        CollectionUtil.assertNoNullElements(Arrays.asList(sets));
        Set<String> joined = new HashSet<String>();
        Arrays.stream(sets).forEach(s -> joined.addAll(s));
        return joined;
    }
    
    /** Generates a one edit distance dictionary of {@code src}. */
    public static Set<String> generateOneEditDistanceDict(String src) {
        assert src != null;
        return Dictionary.join(generateExtraChar(src), generateMissingChar(src), generateReplacedChar(src),
                generateTransposedChar(src));
    }
    
    /** Generates a dictionary of strings that have two transposed characters from {@code src}. */
    public static Set<String> generateTransposedChar(String src) {
        assert src != null;
        Set<String> dictionary = new HashSet<String>();
        StringBuilder sb = new StringBuilder(src);
        for (int i = 0; i < src.length() - 1; i++) {
            char c = sb.charAt(i); // Transposing two characters.
            sb.setCharAt(i, sb.charAt(i + 1));
            sb.setCharAt(i + 1, c);
            dictionary.add(sb.toString());
            c = sb.charAt(i); // Reverting the transposition.
            sb.setCharAt(i, sb.charAt(i + 1));
            sb.setCharAt(i + 1, c);
        }
        return dictionary;
    }
    
    /** Generates a dictionary of strings that differ by one character from {@code src}. */
    public static Set<String> generateReplacedChar(String src) {
        assert src != null;
        Set<String> dictionary = new HashSet<String>();
        StringBuilder sb = new StringBuilder(src);
        for (int i = 0; i < src.length(); i++) {
            for (Character c : ALPHABET) {
                char old = sb.charAt(i);
                sb.setCharAt(i, c); // Replacing the char at idx i. with character c.
                dictionary.add(sb.toString());
                sb.setCharAt(i, old); // Placing back the char after adding to dictionary.
            }
        }
        return dictionary;
    }

    /** Generates a dictionary of strings that are missing a letter from {@code src}. */
    public static Set<String> generateMissingChar(String src) {
        assert src != null;
        return IntStream.range(0, src.length()) 
                .mapToObj(i -> src.substring(0, i) + src.substring(i + 1)) // Removing character at idx i.
                .collect(Collectors.toSet());
    }
    
    /** Generates a dictionary of strings that have one extra character from {@code src}. */
    public static Set<String> generateExtraChar(String src) {
        assert src != null;
        Set<String> dictionary = new HashSet<String>();
        StringBuilder sb = new StringBuilder(src);
        for (int i = 0; i <= src.length(); i++) {
            for (Character c : ALPHABET) {
                dictionary.add(sb.insert(i, c.toString()).toString()); // Inserting character c at idx i.
                sb.deleteCharAt(i); // Deleting inserted character.
            }
        }
        return dictionary;
    }
}
