package seedu.oneline.logic.parser;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.oneline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.commons.util.StringUtil;
import seedu.oneline.logic.LogicManager;
import seedu.oneline.logic.commands.*;
import seedu.oneline.model.task.TaskField;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^#]+)"
                    + " \\.a (?<startTime>[^#]+)"
                    + " \\.b (?<endTime>[^#]+)"
                    + " \\.c (?<deadline>[^#]+)"
                    + " \\.d (?<recurrence>[^#]+)"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    public Parser() {}

    private static final Map<String, Class> COMMAND_CLASSES = initCommandClasses();
    
    private static Map<String, Class> initCommandClasses() {
        Map<String, Class> commands = new HashMap<String, Class>();
        commands.put(AddCommand.COMMAND_WORD.toLowerCase(), AddCommand.class);
        commands.put(SelectCommand.COMMAND_WORD.toLowerCase(), SelectCommand.class);
        commands.put(DeleteCommand.COMMAND_WORD.toLowerCase(), DeleteCommand.class);
        commands.put(ClearCommand.COMMAND_WORD.toLowerCase(), ClearCommand.class);
        commands.put(FindCommand.COMMAND_WORD.toLowerCase(), FindCommand.class);
        commands.put(ListCommand.COMMAND_WORD.toLowerCase(), ListCommand.class);
        commands.put(ExitCommand.COMMAND_WORD.toLowerCase(), ExitCommand.class);
        commands.put(HelpCommand.COMMAND_WORD.toLowerCase(), HelpCommand.class);
        return commands;
    }
    
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
//        switch (commandWord) {
//
//        case AddCommand.COMMAND_WORD:
//            return prepareAdd(arguments);
//
//        case SelectCommand.COMMAND_WORD:
//            return prepareSelect(arguments);
//
//        case DeleteCommand.COMMAND_WORD:
//            return prepareDelete(arguments);
//
//        case ClearCommand.COMMAND_WORD:
//            return new ClearCommand();
//
//        case FindCommand.COMMAND_WORD:
//            return prepareFind(arguments);
//
//        case ListCommand.COMMAND_WORD:
//            return new ListCommand();
//
//        case ExitCommand.COMMAND_WORD:
//            return new ExitCommand();
//
//        case HelpCommand.COMMAND_WORD:
//            return new HelpCommand();
//
//        default:
//            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
//        }
        
        if (!COMMAND_CLASSES.containsKey(commandWord)) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        Class cmdClass = COMMAND_CLASSES.get(commandWord);
        Object obj = null;
        try {
            obj = cmdClass.getConstructor(String.class).newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            assert false : "Every command constructor should have a Class(String) constructor";
            return null;
        } catch (InvocationTargetException e) {
            return new IncorrectCommand(e.getCause().getMessage());
        }
        assert obj instanceof Command;
        Command cmd = (Command) obj;
        return cmd;
    }

    /**
     * Parses arguments in the context of CRUD commands for tasks
     *
     * @param args full command args string
     * @return the fields specified in the args
     */
    public static Map<TaskField, String> getTaskFieldsFromArgs(String args) throws IllegalCmdArgsException {
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        Map<TaskField, String> fields = new HashMap<TaskField, String>();
        // Validate arg string format
        if (!matcher.matches()) {
            throw new IllegalCmdArgsException(AddCommand.MESSAGE_USAGE); // TODO: THROW ERROR
        }
        fields.put(TaskField.NAME, matcher.group("name"));
        fields.put(TaskField.START_TIME, matcher.group("startTime"));
        fields.put(TaskField.END_TIME, matcher.group("endTime"));
        fields.put(TaskField.DEADLINE, matcher.group("deadline"));
        fields.put(TaskField.RECURRENCE, matcher.group("recurrence"));
        fields.put(TaskField.TAG_ARGUMENTS, matcher.group("tagArguments"));
        return fields;
    }
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    matcher.group("startTime"),
                    matcher.group("endTime"),
                    matcher.group("deadline"),
                    matcher.group("recurrence"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    public static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" #", "").split(" #"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments to get an integer index
     *
     * @param args full command args string
     * @return the value of the index, null if invalid
     */
    public static Integer getIndexFromArgs(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return null; // TODO: THROW ERROR
        }
        return index.get();
    }
    
    /**
     * Parses arguments to get search keywords
     *
     * @param args full command args string
     * @return set of keywords
     */
    public static Set<String> getKeywordsFromArgs(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return null; // TODO: THROW ERROR
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return keywordSet;
    }
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private static Command prepareDelete(String args) {

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
    private static Optional<Integer> parseIndex(String command) {
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
    private static Command prepareFind(String args) {
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