package seedu.jimi.commons.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @@author A0140133B
/** Container class for dictionary related methods */
public class DictionaryUtil {
    
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
        return DictionaryUtil.join(generateExtraChar(src), generateMissingChar(src), generateReplacedChar(src),
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