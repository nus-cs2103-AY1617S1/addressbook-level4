package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses
 */
public class Parser {

    private static final Pattern COMMAND_WORD_PATTERN = Pattern.compile("^(?<commandWord>\\S+)(?<tail>.*)$");
    private static final Pattern ITEM_INDEX_PATTERN = Pattern.compile("^(?<index>\\d+)(?<tail>.*)$");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^(?<description>.+)$");

    private String text;

    public Parser() {}

    /**
     * Set the current text the parser is working on
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Extracts description in text and removes it from the current text, if found
     * @return optional of description extracted from text, empty if not found
     */
    public Optional<String> extractDescription() {
        final Matcher matcher = DESCRIPTION_PATTERN.matcher(text);

        if (matcher.matches()) {
            return Optional.of(matcher.group("description"));
        }

        return Optional.empty();
    }

    /**
     * Extracts command word in text and removes it from the current text, if found
     * @return optional of command word extracted from text, empty if not found
     */
    public Optional<String> extractCommandWord() {
        final Matcher matcher = COMMAND_WORD_PATTERN.matcher(text);

        if (matcher.matches()) {
            text = matcher.group("tail"); // What's left after extracting
            return Optional.of(matcher.group("commandWord"));
        }

        return Optional.empty();
    }

    /**
     * Extracts item index in text and removes it from the current text, if found
     * @return optional of item index extracted from text, empty if not found
     */
    public Optional<Integer> extractItemIndex() {
        final Matcher matcher = ITEM_INDEX_PATTERN.matcher(text);

        if (matcher.matches()) {
            text = matcher.group("tail");
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