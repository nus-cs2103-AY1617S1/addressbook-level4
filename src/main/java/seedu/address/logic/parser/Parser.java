package seedu.address.logic.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a given input string
 * Methods modify the input string by extracting appropriate parts of it
 */
public class Parser {

    private static final Pattern FIRST_WORD_PATTERN = Pattern.compile("^(?<word>\\S+)(?<tail>.*)$");
    private static final Pattern FIRST_INTEGER_PATTERN = Pattern.compile("^(?<index>\\d+)(?<tail>.*)$");
    private static final Pattern WORD_PATTERN = Pattern.compile("(?<word>\\S+)");
    private String input;

    public Parser() {
    }

    /**
     * Set the current input the parser is working on
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Gets current input the parser is working on
     */
    public String getInput() {
        return input;
    }

    /**
     * From start, extracts the text after the first occurrence of {@param keyword} from input
     * until any keyword in the set of {@param otherKeywords}
     * {@param keyword} and the subsequent text extracted is removed from input
     * Similar to {@link this#extractTextFromIndex(int, String...)}
     * Asserts {@param keyword} is non-null
     *
     * @return optional of text extracted from input, empty if not found
     */
    public Optional<String> extractTextAfterKeyword(String keyword, String... otherKeywords) {
        assert keyword != null;

        String lowerCaseInput = input.toLowerCase();
        Matcher matcher = WORD_PATTERN.matcher(lowerCaseInput);

        while (matcher.find()) {
            // If keyword matched current word
            if (matcher.group("word").equals(keyword.trim().toLowerCase())) {
                // Remove keyword
                input = input.substring(0, matcher.start()) + input.substring(matcher.end());

                // Extract text from start (was end) of the keyword
               return extractTextFromIndex(
                   matcher.start(),
                   otherKeywords
               );
            }
        }

        // Keyword not found
        return Optional.empty();
    }

    /**
     * From {@param startIndex}, extracts text from input until any keyword in the set of keywords {@param keywords}
     * Keywords are matched as whole words, not substrings, and as case-insensitive ("word" does not match in "keyword")
     * It can match up to the end of input, if an empty set of keywords is provided or none in
     * set is encountered
     *
     * @return optional of text extracted from input, empty if not found
     */
    public Optional<String> extractTextFromIndex(int startIndex, String... keywords) {
        // Search for earliest occurrence of any keyword, case insensitive
        int endIndex = input.length();
        String lowerCaseInput = input.substring(startIndex).toLowerCase();

        Matcher matcher = WORD_PATTERN.matcher(lowerCaseInput);
        loop:
        while (matcher.find()) {
            for (String keyword : keywords) {
                // If any keyword matched current word
                if (matcher.group("word").equals(keyword.trim().toLowerCase())) {
                    endIndex = matcher.start() + startIndex;
                    break loop; // stop, we found the first
                }
            }
        }

        String text = input.substring(startIndex, endIndex).trim();

        // Remove extracted text
        if (startIndex == 0) {
            input = input.substring(endIndex);
        } else {
            // Add space to account for startIndex splitting a word
            input = input.substring(0, startIndex) + " " + input.substring(endIndex);
        }

        if (text.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(text);
    }

    /**
     * Similar to {@link this#extractTextFromIndex(int, String...)}, but from start of input
     */
    public Optional<String> extractText(String... keywords) {
        return extractTextFromIndex(0, keywords);
    }

    /**
     * Extracts all words prefixed with {@param prefix} as a list,
     * in the order that appears in the input
     * Eg, method("#") on input = "some #tag1 #tag2 thing" returns ["tag1", "tag2"]
     * and resulting input = "some  thing"
     * Asserts {@param prefix} is non-null
     */
    public List<String> extractPrefixedWords(String prefix) {
        assert prefix != null;

        List<String> words = new LinkedList<>();

        // Keep trying to find the next word, until cannot be found
        for (Matcher matcher = WORD_PATTERN.matcher(input);
             matcher.find(); ) {

            String word = matcher.group("word");

            // Check prefix of word
            if (word.indexOf(prefix) == 0) {
                words.add(word.substring(prefix.length())); // Add without prefix
                input = input.substring(0, matcher.start())
                    + input.substring(matcher.end()); // Remove matched word from input

                matcher = WORD_PATTERN.matcher(input); // Reset matcher to new input string
            }
        }

        return words;
    }

    /**
     * From start, extracts a list of words in input until its end
     * @return list of words found, in the order that appears in input
     */
    public List<String> extractWords() {
        final List<String> words = new LinkedList<>();

        final Matcher matcher = WORD_PATTERN.matcher(input.trim());

        while (matcher.find()) {
            words.add(matcher.group("word"));
        }

        input = ""; // empty input

        return words;
    }

    /**
     * From start, extracts the first word in input, if found
     *
     * @return optional of word extracted from input, empty if not found
     */
    public Optional<String> extractFirstWord() {
        final Matcher matcher = FIRST_WORD_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            input = matcher.group("tail"); // Remove extracted command word
            return Optional.of(matcher.group("word"));
        }

        return Optional.empty();
    }

    /**
     * From start, extracts first integer in input, if found
     *
     * @return optional of found integer extracted from input, empty if not found
     */
    public Optional<Integer> extractFirstInteger() {
        final Matcher matcher = FIRST_INTEGER_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            input = matcher.group("tail"); // Remove extracted item index
            String indexString = matcher.group("index");

            try {
                return Optional.of(Integer.parseInt(indexString));
            } catch (NumberFormatException exception) {
                assert false : "Shouldn't be able to fail parsing of item index based on pattern";
            }
        }

        return Optional.empty();
    }
}