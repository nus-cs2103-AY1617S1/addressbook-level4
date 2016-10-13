package seedu.jimi.logic.parser;

import static seedu.jimi.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jimi.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jimi.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.jimi.commons.exceptions.DateNotParsableException;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.logic.commands.*;

/**
 * Parses user input.
 */
public class JimiParser {
    
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<detailsArguments>[^/]+)(?<tagArguments>(?: t/[^/]+)?)"); // zero or one tag only

    private static final Pattern EDIT_DATA_ARGS_FORMAT = // accepts index at beginning, follows task/event patterns after
            Pattern.compile("(?<targetIndex>\\d+\\s)(?<name>[^/]+)(?<tagArguments>(?: t/[^/]+)?)");
    
    private static final Pattern DETAILS_ARGS_FORMAT = 
            Pattern.compile("(\"(?<taskDetails>.+)\")( by (?<dateTime>.+))?");
    
    private static final List<Command> COMMAND_STUB_LIST = Arrays.asList(
            new AddCommand(),
            new EditCommand(),
            new SelectCommand(),
            new DeleteCommand(),
            new ClearCommand(),
            new FindCommand(),
            new ListCommand(),
            new ExitCommand(),
            new HelpCommand()
    );
    
    public JimiParser() {}

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
        
        return prepareCommand(commandWord, arguments);
    }

    /**
     * Identifies which command to prepare according to raw command word.
     * 
     * @param commandWord command word from raw input
     * @param arguments arguments from raw input
     * @return correct Command corresponding to the command word if valid, else returns incorrect command.
     */
    private Command prepareCommand(String commandWord, String arguments) {
        for (Command command : COMMAND_STUB_LIST) {
            // if validation checks implemented by the respective commands are passed
            if (command.isValidCommandWord(commandWord)) {
                // identifying which command this is
                switch (command.getCommandWord()) {
                case AddCommand.COMMAND_WORD :
                    return prepareAdd(arguments);
                case EditCommand.COMMAND_WORD :
                    return prepareEdit(arguments);
                case SelectCommand.COMMAND_WORD :
                    return prepareSelect(arguments);
                case DeleteCommand.COMMAND_WORD :
                    return prepareDelete(arguments);
                case FindCommand.COMMAND_WORD :
                    return prepareFind(arguments);
                default : // commands which do not require arguments for instantiation
                    return command;
                }
            }
        }
        
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher detailsAndTagsMatcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate entire args string format
        if (!detailsAndTagsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        final Matcher detailsMatcher =
                DETAILS_ARGS_FORMAT.matcher(detailsAndTagsMatcher.group("detailsArguments").trim());
        // Validate details args format
        if (!detailsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        List<Date> dates;
        try {
            dates = parseStringToDate(detailsMatcher.group("dateTime"));
        } catch (DateNotParsableException e) {
            return new IncorrectCommand(e.getMessage());
        }
        
        try {
            return new AddCommand(
                    detailsMatcher.group("taskDetails"),
                    dates,
                    getTagsFromArgs(detailsAndTagsMatcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private static List<Date> parseStringToDate(final String str) throws DateNotParsableException {
        if(str == null)
            return new ArrayList<Date>();
        final Parser dateParser = new Parser();
        final List<DateGroup> groups = dateParser.parse(str);
        if(!groups.isEmpty())
            return groups.get(0).getDates();
        else
            throw new DateNotParsableException(MESSAGE_INVALID_DATE);
    }

    /**
     * Parses arguments in context of the edit task command.
     * 
     * @param args Full user command input args
     * @return  the prepared edit command
     */
    private Command prepareEdit(String args){
        final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        try {
            return new EditCommand(
                    matcher.group("name"),
                    getTagsFromArgs(matcher.group("tagArguments")),
                    Integer.parseInt(matcher.group("targetIndex").trim())
                    );
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