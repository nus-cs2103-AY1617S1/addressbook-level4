package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.SequentialParser;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps and builds commands from input strings, using {@link SequentialParser}
 * In charge of splitting up input strings to required parts for commands
 */
public class CommandFactory {
    public static final String KEYWORD_DATERANGE_START = "from";
    public static final String KEYWORD_DATERANGE_END = "to";
    public static final String KEYWORD_DUEDATE = "by";
    public static final String TAG_PREFIX = "#";

    private SequentialParser sequentialParser;
    private DateTimeParser dateTimeParser;
    {
        sequentialParser = new SequentialParser();
        dateTimeParser = new DateTimeParser();
    }

    /**
     * Interprets an input string as a command, initializes it, and returns it
     * @return instance of a command based on {@param parsable}
     */
    public Command build(String inputString){
        sequentialParser.setInput(inputString);

        // Check if command word exists
        Optional<String> commandWord = sequentialParser.extractFirstWord();

        if (!commandWord.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_COMMAND_WORD);
        }

        switch (commandWord.get()) {
            case AddCommand.COMMAND_WORD:
                return buildAddCommand();
            case DeleteCommand.COMMAND_WORD:
                return buildDeleteCommand();
            case FinishCommand.COMMAND_WORD:
                return buildFinishCommand();
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
        // Extract tags
        List<String> tags = sequentialParser.extractPrefixedWords(TAG_PREFIX);

        // Try to find title
        Optional<String> title = sequentialParser.extractText(
            KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END,
            KEYWORD_DUEDATE
        );

        if (!title.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_TITLE);
        }

        AddCommand command;
        try {
            command = new AddCommand(title.get());
        } catch (IllegalValueException exception) {
            return new InvalidCommand(Messages.MESSAGE_TODO_TITLE_CONSTRAINTS);
        }

        // Put in tags
        if (!tags.isEmpty()) {
            try {
                command.setTags(tags.stream().collect(Collectors.toSet()));
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        // Extract due date, if exists
        Optional<String> dueDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DUEDATE,
            KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END
        );

        Optional<LocalDateTime> dueDate = dateTimeParser.parseDateTime(dueDateString.orElse(""));

        if (dueDate.isPresent()) {
            try {
                command.setDueDate(dueDate.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        // Extract date range, if exists
        Optional<String> startDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END,
            KEYWORD_DUEDATE
        );
        Optional<String> endDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DATERANGE_END,
            KEYWORD_DATERANGE_START,
            KEYWORD_DUEDATE
        );

        if (startDateString.isPresent() || startDateString.isPresent()) {
            if (endDateString.isPresent() && !startDateString.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_DATERANGE_START);
            } else if (startDateString.isPresent() && !endDateString.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_DATERANGE_END);
            }

            Optional<LocalDateTime> startDate = dateTimeParser.parseDateTime(startDateString.orElse(""));
            Optional<LocalDateTime> endDate = dateTimeParser.parseDateTime(endDateString.orElse(""));

            if (!startDate.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_TODO_DATERANGE_START_INVALID_FORMAT);
            } else if (!endDate.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_TODO_DATERANGE_END_INVALID_FORMAT);
            }

            // Here, startDate and endDate exist and are valid
            try {
                command.setDateRange(startDate.get(), endDate.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        return command;
    }

    private Command buildDeleteCommand() {
        // Try to find index
        Optional<Integer> index = sequentialParser.extractFirstInteger();
        if (!index.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_ITEM_INDEX);
        }

        return new DeleteCommand(index.get());
    }
    
    private Command buildFinishCommand(){
        Optional<Integer> index = sequentialParser.extractFirstInteger();
        if (!index.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_ITEM_INDEX);
        }

        return new FinishCommand(index.get());
    }

    private Command buildFindCommand() {
        // Try to find keywords
        List<String> words = sequentialParser.extractWords();

        // Convert to set
        return new FindCommand(words.stream().collect(Collectors.toSet()));
    }

    private Command buildClearCommand() {
        return new ClearCommand();
    }

    private Command buildHelpCommand() {
        // Try to find command word
        Optional<String> word = sequentialParser.extractText();

        if (word.isPresent()) {
            return new HelpCommand(word.get());
        } else {
            return new HelpCommand();
        }
    }
    private Command buildEditCommand() {
        // Try to find index
        Optional<Integer> index = sequentialParser.extractFirstInteger();
        if (!index.isPresent()) {
            return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_ITEM_INDEX);
        }

        EditCommand command = new EditCommand(index.get());

        // Extract tags
        List<String> tags = sequentialParser.extractPrefixedWords(TAG_PREFIX);

        if (!tags.isEmpty()) {
            try {
                command.setTags(tags.stream().collect(Collectors.toSet()));
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        // Extract title
        Optional<String> title = sequentialParser.extractText(
            KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END,
            KEYWORD_DUEDATE
        );

        if (title.isPresent()) {
            try {
                command.setTitle(title.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        // Extract due date, if exists
        Optional<String> dueDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DUEDATE,
            KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END
        );

        Optional<LocalDateTime> dueDate = dateTimeParser.parseDateTime(dueDateString.orElse(""));

        if (dueDate.isPresent()) {
            try {
                command.setDueDate(dueDate.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        // Extract date range, if exists
        Optional<String> startDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DATERANGE_START,
            KEYWORD_DATERANGE_END,
            KEYWORD_DUEDATE
        );
        Optional<String> endDateString = sequentialParser.extractTextAfterKeyword(KEYWORD_DATERANGE_END,
            KEYWORD_DATERANGE_START,
            KEYWORD_DUEDATE
        );

        if (startDateString.isPresent() || startDateString.isPresent()) {
            if (endDateString.isPresent() && !startDateString.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_DATERANGE_START);
            } else if (startDateString.isPresent() && !endDateString.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_MISSING_TODO_DATERANGE_END);
            }

            Optional<LocalDateTime> startDate = dateTimeParser.parseDateTime(startDateString.orElse(""));
            Optional<LocalDateTime> endDate = dateTimeParser.parseDateTime(endDateString.orElse(""));

            if (!startDate.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_TODO_DATERANGE_START_INVALID_FORMAT);
            } else if (!endDate.isPresent()) {
                return new InvalidCommand(Messages.MESSAGE_TODO_DATERANGE_END_INVALID_FORMAT);
            }

            // Here, startDate and endDate exist and are valid
            try {
                command.setDateRange(startDate.get(), endDate.get());
            } catch (IllegalValueException exception) {
                return new InvalidCommand(exception.getMessage());
            }
        }

        return command;
    }
}
