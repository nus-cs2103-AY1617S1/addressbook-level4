package seedu.oneline.logic.parser;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.oneline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.commons.util.StringUtil;
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
                    + " \\.from (?<startTime>[^#]+)"
                    + " \\.to (?<endTime>[^#]+)"
                    + " \\.due (?<deadline>[^#]+)"
                    + " \\.every (?<recurrence>[^#]+)"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    private static final Pattern EDIT_COMMAND_ARGS_FORMAT =
            Pattern.compile("(?<index>-?[\\d]+)" // index
                    + " (?<args>.+)"); // the other arguments
    
    public Parser() {}

    private static final Map<String, Class<?>> COMMAND_CLASSES = initCommandClasses();
    
    private static Map<String, Class<?>> initCommandClasses() {
        Map<String, Class<?>> commands = new HashMap<String, Class<?>>();
        commands.put(AddCommand.COMMAND_WORD.toLowerCase(), AddCommand.class);
        commands.put(SelectCommand.COMMAND_WORD.toLowerCase(), SelectCommand.class);
        commands.put(EditCommand.COMMAND_WORD.toLowerCase(), EditCommand.class);
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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        
        if (!COMMAND_CLASSES.containsKey(commandWord)) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        Class<?> cmdClass = COMMAND_CLASSES.get(commandWord);
        Object obj = null;
        try {
            obj = cmdClass.getConstructor(String.class).newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            assert false : "Every command constructor should have a Class(String args) constructor";
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
            throw new IllegalCmdArgsException("Invalid format for command arguments"); // TODO: THROW ERROR
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
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    public static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.trim().replaceFirst(CommandConstants.TAG_PREFIX, "").split(" " + CommandConstants.TAG_PREFIX));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments to get an integer index
     *
     * @param args full command args string
     * @return the value of the index, null if invalid
     * @throws IllegalValueException 
     */
    public static Integer getIndexFromArgs(String args) throws IllegalValueException {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            throw new IllegalValueException("Index does not parse to integer.");
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
    
    public static Entry<Integer, Map<TaskField, String>> getIndexAndTaskFieldsFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        final Matcher matcher = EDIT_COMMAND_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new IllegalCmdArgsException("Args not in format <index> <arguments>");
        }
        Integer index = Parser.getIndexFromArgs(matcher.group("index"));
        Map<TaskField, String> fields = Parser.getTaskFieldsFromArgs(matcher.group("args"));
        return new SimpleEntry<Integer, Map<TaskField, String>>(index, fields);
    }

}