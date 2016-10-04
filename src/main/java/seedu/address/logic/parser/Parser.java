package seedu.address.logic.parser;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses
 */
public class Parser {

    private static final Pattern COMMAND_WORD_PATTERN = Pattern.compile("^(?<commandWord>\\S+)(?<tail>.*)$");
    private static final Pattern ITEM_INDEX_PATTERN = Pattern.compile("^(?<index>\\d+)(?<tail>.*)$");
    private static final Pattern TEXT_PATTERN = Pattern.compile("^(?<text>.+)$");
    private static final Pattern WORD_PATTERN = Pattern.compile("(?<word>\\S+)");

    private String input;

    public Parser() {}

    /**
     * Set the current input the parser is working on
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Extracts text and removes it from the current input, if found
     * @return optional of description extracted from input, empty if not found
     */
    public Optional<String> extractText() {
        final Matcher matcher = TEXT_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            return Optional.of(matcher.group("text"));
        }

        return Optional.empty();
    }

    /**
     * Extracts a set of words in input
     * @return set of words found
     */
    public Set<String> extractWords() {
        final HashSet<String> words = new HashSet<>();

        final Matcher matcher = WORD_PATTERN.matcher(input.trim());

        while (matcher.find()) {
           words.add(matcher.group("word"));
        }

        return words;
    }

    /**
     * Extracts command word in input and removes it from the current input, if found
     * @return optional of command word extracted from input, empty if not found
     */
    public Optional<String> extractCommandWord() {
        final Matcher matcher = COMMAND_WORD_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            input = matcher.group("tail"); // What's left after extracting
            return Optional.of(matcher.group("commandWord"));
        }

        return Optional.empty();
    }

    /**
     * Extracts item index in input and removes it from the current input, if found
     * @return optional of item index extracted from input, empty if not found
     */
    public Optional<Integer> extractItemIndex() {
        final Matcher matcher = ITEM_INDEX_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            input = matcher.group("tail"); // What's left after extracting
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