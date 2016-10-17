package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.storage.StorageManager;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern INDEX_COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^/]*)"
                    + " e/(?<endDate>.*)"
                    + "(?>\\s+\\bat\\b)"
                    + "(?<address>.*)"
                    ); 
 
    private static final Pattern DEADLINE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)"
                    + "(?>\\s+\\bby\\b)"
                    + "(?<endDate>.*)"
                    );
    
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[\\p{Alnum} ]+)");
    
    private static final Pattern SETPATH_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[\\p{Alnum}|/]+)");   //    data/  <---
    
    
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();   

        case SetPathCommand.COMMAND_WORD:
            return setSavePath(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /**
     * Parses arguments in the context of the set save path command.
     * 
     * @param args full command args string
     * @return the custom saved path
     */

    private Command setSavePath(String args) {
        args = args.trim();
        Matcher matcher = SETPATH_DATA_ARGS_FORMAT.matcher(args);
        // Validate arg string format
        if (matcher.matches()) {
            String path = "data/" + matcher.group("name").trim().replaceAll("/$","") +".xml";
            return new SetPathCommand(path); 
        }
        else {   
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPathCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        try {
            args = args.trim();
            Matcher matcher = EVENT_DATA_ARGS_FORMAT.matcher(args);
            // Validate arg string format
            if (matcher.matches()) {
                return new AddEventCommand(matcher.group("name"), matcher.group("startDate"),
                    matcher.group("endDate"), matcher.group("address"));
            }
    
            matcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args);
    
            if (matcher.matches()) {
                return new AddDeadlineCommand(matcher.group("name"),
                    matcher.group("endDate"));
            }
            matcher = TASK_DATA_ARGS_FORMAT.matcher(args);
            
            
            if (matcher.matches()) {
                return new AddFloatingCommand(args);
            } else {   
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        try {
            args = args.trim();
            final Matcher matcher = INDEX_COMMAND_FORMAT.matcher(args);
    
            // Validate arg string format
            if (!matcher.matches()) {
                return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            int index = Integer.parseInt(matcher.group("index"));
            assert index >= 0;
            args = matcher.group("arguments").trim();
            Matcher editMatcher = EVENT_DATA_ARGS_FORMAT.matcher(args);
            
            if (editMatcher.matches()) {
                return new EditEventCommand(index, editMatcher.group("name"), editMatcher.group("startDate"),
                        editMatcher.group("endDate"), editMatcher.group("address"));
            }
            editMatcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args);
            
            if (editMatcher.matches()) {
                return new EditDeadlineCommand(index, editMatcher.group("name"),
                        editMatcher.group("endDate")); 
            }
            
            editMatcher = TASK_DATA_ARGS_FORMAT.matcher(args);
            if (editMatcher.matches()) {
                return new EditFloatingCommand(index, args);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));      
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }
    
    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}