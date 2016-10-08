package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Parser;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maps and builds commands from input strings, using {@link Parser}
 * In charge of splitting up input strings to required parts for commands
 */
public class CommandFactory {
    public static final String KEYWORD_DATERANGE_START = "from";
    public static final String KEYWORD_DATERANGE_END = "to";
    public static final String KEYWORD_DUEDATE = "by";
    public static final String TAG_PREFIX = "#";

    private Parser parser;
    {
        parser = new Parser();
    }

    /**
     * Interprets an input string as a command, initializes it, and returns it
     * @return instance of a command based on {@param parsable}
     */
    public Command build(String inputString){
        parser.setInput(inputString);

        // Check if command word exists
        Optional<String> commandWord = parser.extractFirstWord();

        if (!commandWord.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_COMMAND_WORD);
        }

        switch (commandWord.get()) {
            case AddCommand.COMMAND_WORD:
                return buildAddCommand();
            case DeleteCommand.COMMAND_WORD:
                return buildDeleteCommand();
            case FindCommand.COMMAND_WORD:
                return buildFindCommand();
            case ExitCommand.COMMAND_WORD:
                return buildExitCommand();
            case ClearCommand.COMMAND_WORD:
                return buildClearCommand();
            case HelpCommand.COMMAND_WORD:
                return buildHelpCommand();
            case EditCommand.COMMAND_WORD:
                return buildEditCommand();
            default:
                return new InvalidCommand(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command buildExitCommand() {
        return new ExitCommand();
    }

    private Command buildAddCommand() {
        // Try to find title
        Optional<String> title = parser.extractText();
        if (!title.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_TITLE);
        }

        try {
            return new AddCommand(title.get());
        } catch (IllegalValueException exception) {
            return new InvalidCommand(Messages.MESSAGE_TODO_TITLE_CONSTRAINTS);
        }
    }

    private Command buildDeleteCommand() {
        // Try to find index
        Optional<Integer> index = parser.extractFirstInteger();
        if (!index.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_ITEM_INDEX);
        }

        return new DeleteCommand(index.get());
    }

    private Command buildFindCommand() {
        // Try to find keywords
        List<String> words = parser.extractWords();

        // Convert to set
        return new FindCommand(words.stream().collect(Collectors.toSet()));
    }

    private Command buildClearCommand() {
        return new ClearCommand();
    }

    private Command buildHelpCommand() {
        // Try to find command word
        Optional<String> word = parser.extractText();

        if (word.isPresent()) {
            return new HelpCommand(word.get());
        } else {
            return new HelpCommand();
        }
    }
    private Command buildEditCommand() {
        // Try to find index
        Optional<Integer> index = parser.extractFirstInteger();
        if (!index.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_ITEM_INDEX);
        }

        EditCommand command = new EditCommand(index.get());

        // Extract tags
        List<String> tags = parser.extractPrefixedWords(TAG_PREFIX);

        if (!tags.isEmpty()) {
            try {
                command.setTags(tags.stream().collect(Collectors.toSet()));
            } catch (IllegalValueException exception) {
                return new InvalidCommand(Messages.MESSAGE_TODO_TAG_CONSTRAINTS);
            }
        }

        // Extract title
        Optional<String> title = parser.extractText(
            KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END,
            KEYWORD_DUEDATE
        );

        if (title.isPresent()) {
            try {
                command.setTitle(title.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(Messages.MESSAGE_TODO_TITLE_CONSTRAINTS);
            }
        }

        return command;
    }
}
