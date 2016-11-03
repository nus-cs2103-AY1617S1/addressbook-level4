package seedu.jimi.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@@author A0140133B
/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    
    private final static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    /** Returns true if any of the strings in {@code keywords} nearly matches {@code query}. */
    public static boolean isNearMatch(String query, String... keywords) {
        return Arrays.stream(keywords)
                .filter(kw -> isNearMatch(kw, query))
                .findAny()
                .isPresent();
    }
    
    /**
     * Returns true if {@code query} is a near match of {@code source}, where: <br>
     * <ul>
     * <li> {@code query} is a substring of {@code source}.
     * <li> {@code query} is the same as {@code source} but missing a character.
     * <li> {@code query} is the same as {@code source} but differing by a character.
     * <li> {@code query} has identical character frequencies as {@code source}.
     * <li> {@code query} is the same as {@code source} but has an extra character.
     * </ul>
     * 
     * All matches are case-insensitive and have all spaces removed in both strings prior to comparison. <br>
     * Conditions are also short-circuited for performance and memory.
     * 
     * @param source String to match against
     * @param query String to match with
     * @return true if {@code query} nearly matches {@code source}.
     */
    public static boolean isNearMatch(String source, String query) {
        // Removing all spaces and converting both strings to lower case.
        String sourceNoSpaces = source.toLowerCase().replaceAll("\\s+", "");
        String queryNoSpaces = query.toLowerCase().replaceAll("\\s+", "");
        
        if (sourceNoSpaces.contains(queryNoSpaces)) {
            return true; // Source string contains query.
        }
        
        HashMap<Character, Integer> srcFrequencyMap = generateCharFrequencyMap(sourceNoSpaces);
        if (isSameCharFrequency(srcFrequencyMap, queryNoSpaces)) {
            return true; // Identical character frequencies.
        }
        
        Set<String> missingCharDict = generateMissingCharDictionary(sourceNoSpaces);
        if (missingCharDict.contains(queryNoSpaces)) {
            return true; // Similar by a missing character.
        }
        
        Set<String> oneCharDiffDict = generateOneCharDiffDictionary(sourceNoSpaces);
        if (oneCharDiffDict.contains(queryNoSpaces)) {
            return true; // Similar by a differing character.
        }
        
        Set<String> oneExtraCharDict = generateOneExtraCharDictionary(sourceNoSpaces);
        if (oneExtraCharDict.contains(queryNoSpaces)) {
            return true; // Similar by an extra character.
        }
        
        return false;
    }
    // @@author

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
     * method to extract the first word of a string.
     */
    public static String getFirstWord(String text) {
        String trimmed = new String(text.trim());
        if (trimmed.indexOf(' ') > -1) { // Check if there is more than one word.
            return trimmed.substring(0, trimmed.indexOf(' ')); // Extract first word.
        } else {
            return trimmed; // Text is the first word itself.
        }
    }
    
    // @@author A0140133B
    /*
     * ============================================================ 
     *                      Helper Methods
     * ============================================================ 
     */
    
    /** Checks if string {@code s} has the same number of distinct characters as mapped in {@code map}. */
    private static boolean isSameCharFrequency(HashMap<Character, Integer> map, String s) {
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (!map.containsKey(c) || (map.containsKey(c) && map.get(c) <= 0)) {
                return false; // Character or frequency mismatch.
            } else if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1); // Subtracting acts as a way to track frequency.
            }
        }
        // Returns false if frequencies are not exactly the same, 
        // same meaning all frequencies are correctly subtracted to 0.
        return !map.keySet().stream()
                .filter(k -> map.get(k) != 0)
                .findAny()
                .isPresent();
    }
    
    /** Generates a frequency map of character frequencies in string {@code s}.*/
    private static HashMap<Character, Integer> generateCharFrequencyMap(String s) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            Integer currentFrequency = map.containsKey(c) ? map.get(c) : 0;
            map.put(c, currentFrequency + 1); // Increasing frequency count.
        }
        return map;
    }
    
    /** Generates a dictionary of strings that differ by one character from {@code src}. */
    private static Set<String> generateOneCharDiffDictionary(String src) {
        Set<String> dictionary = new HashSet<String>();
        for (int i = 0; i < src.length(); i++) {
            StringBuilder sb = new StringBuilder(src);
            for (Character c : ALPHABET) {
                sb.setCharAt(i, c); // Replacing the char at idx i. with character c.
                dictionary.add(sb.toString());
            }
        }
        return dictionary;
    }

    /** Generates a dictionary of strings that are missing a letter from {@code src}. */
    private static Set<String> generateMissingCharDictionary(String src) {
        return IntStream.range(0, src.length()) 
                .mapToObj(i -> src.substring(0, i) + src.substring(i + 1)) // Removing character at idx i.
                .collect(Collectors.toSet());
    }
    
    /** Generates a dictionary of strings that have one extra character from {@code src}. */
    private static Set<String> generateOneExtraCharDictionary(String src) {
        Set<String> dictionary = new HashSet<String>();
        StringBuilder sb = new StringBuilder(src);
        for (int i = 0; i <= src.length(); i++) {
            for (Character c : ALPHABET) {
                dictionary.add(sb.insert(i, c.toString()).toString()); // Inserting character c at idx i.
                sb.deleteCharAt(i); // Deleting after adding to dictionary.
            }
        }
        return dictionary;
    }
    // @@author
}
