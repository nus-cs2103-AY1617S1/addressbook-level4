package seedu.oneline.logic.parser;

import static seedu.oneline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.oneline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.lang.reflect.Constructor;
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

    private static final Pattern EDIT_COMMAND_ARGS_FORMAT =
            Pattern.compile("(?<index>-?[\\d]+)" // index
                    + " (?<args>.+)"); // the other arguments
    
    public Parser() {}

    private static final Map<String, Class<? extends Command>> COMMAND_CLASSES = initCommandClasses();
    
    private static Map<String, Class<? extends Command>> initCommandClasses() {
        Map<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();
        commands.put(AddCommand.COMMAND_WORD.toLowerCase(), AddCommand.class);
        commands.put(SelectCommand.COMMAND_WORD.toLowerCase(), SelectCommand.class);
        commands.put(EditCommand.COMMAND_WORD.toLowerCase(), EditCommand.class);
        commands.put(DeleteCommand.COMMAND_WORD.toLowerCase(), DeleteCommand.class);
        commands.put(ClearCommand.COMMAND_WORD.toLowerCase(), ClearCommand.class);
        commands.put(FindCommand.COMMAND_WORD.toLowerCase(), FindCommand.class);
        commands.put(ListCommand.COMMAND_WORD.toLowerCase(), ListCommand.class);
        commands.put(ExitCommand.COMMAND_WORD.toLowerCase(), ExitCommand.class);
        commands.put(HelpCommand.COMMAND_WORD.toLowerCase(), HelpCommand.class);
        commands.put(UndoCommand.COMMAND_WORD.toLowerCase(), UndoCommand.class);
        commands.put(RedoCommand.COMMAND_WORD.toLowerCase(), RedoCommand.class);
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
        Class<? extends Command> cmdClass = COMMAND_CLASSES.get(commandWord);
        try {
            Constructor<? extends Command> constructor = cmdClass.getConstructor(String.class);
            Command cmd = constructor.newInstance(arguments);
            return cmd;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            assert false : "Every command constructor should have a Class(String args) constructor";
        } catch (InvocationTargetException e) {
            return new IncorrectCommand(e.getCause().getMessage());
        }
        return null;
    }

    /**
     * Parses arguments in the context of CRUD commands for tasks
     *
     * @param args full command args string
     * @return the fields specified in the args
     */
    public static Map<TaskField, String> getTaskFieldsFromArgs(String args) throws IllegalCmdArgsException {
        // Clear extra whitespace characters
        args = args.trim();
        while (args.contains("  ")) {
            args = args.replaceAll("  ", " "); // get rid of double-spaces
        }
        // Get the indexes of all task fields
        String[] splitted = args.split(" ");
        List<Entry<TaskField, Integer>> fieldIndexes = new ArrayList<>();
        TaskField[] fields = new TaskField[] { TaskField.START_TIME, TaskField.END_TIME,
                                               TaskField.DEADLINE, TaskField.RECURRENCE };
        for (TaskField tf : fields) {
            Integer index = getIndexesOfKeyword(splitted, tf.getKeyword());
            if (index != null) {
                fieldIndexes.add(new SimpleEntry<TaskField, Integer>(tf, index));
            }
        }
        for (int i = 0; i < splitted.length; i++) {
            if (splitted[i].toLowerCase().startsWith(CommandConstants.TAG_PREFIX)) {
                for (int j = i; j < splitted.length; j++) {
                    if (!splitted[j].startsWith(CommandConstants.TAG_PREFIX)) {
                        throw new IllegalCmdArgsException("Hashtags should be the last fields in command.");
                    }
                }
                fieldIndexes.add(new SimpleEntry<TaskField, Integer>(TaskField.TAG_ARGUMENTS, i));
                break;
            }
        }
        // Arrange the indexes of task fields in sorted order
        Collections.sort(fieldIndexes, new Comparator<Entry<TaskField, Integer>>() {
            @Override
            public int compare(Entry<TaskField, Integer> a, Entry<TaskField, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            } });
        // Extract the respective task fields into results map
        Map<TaskField, String> result = new HashMap<TaskField, String>();
        if (fieldIndexes.size() == 0) {
            if (splitted[0].equals("")) { 
                throw new IllegalCmdArgsException("Task Name is a compulsory field.");
            }
            result.put(TaskField.NAME, String.join(" ", splitted));
            return result;
        }
        Integer firstIndex = fieldIndexes.get(0).getValue();
        if (firstIndex > 0) {
            String[] subArr = Arrays.copyOfRange(splitted, 0, firstIndex);
            result.put(TaskField.NAME, String.join(" ", subArr));
        }
        for (int i = 0; i < fieldIndexes.size(); i++) {
            if (fieldIndexes.get(i).getKey() == TaskField.TAG_ARGUMENTS && i != fieldIndexes.size() - 1) {
                throw new IllegalCmdArgsException("Hashtags should be the last fields in command.");
            }
            String[] subArr = Arrays.copyOfRange(splitted,
                                (fieldIndexes.get(i).getKey() == TaskField.TAG_ARGUMENTS) ?
                                    fieldIndexes.get(i).getValue() :
                                    fieldIndexes.get(i).getValue() + 1,
                                (i == fieldIndexes.size() - 1) ?
                                    splitted.length : fieldIndexes.get(i + 1).getValue());
            result.put(fieldIndexes.get(i).getKey(), String.join(" ", subArr));
        }
        return result;
    }
    
    /**
     * Finds the location of the specified keyword in the array of args
     *
     * @param args fields to be checked
     * @return index of where the keyword is found
     * @throws IllegalCmdArgsException if command is not found
     */
    private static Integer getIndexesOfKeyword(String[] args, String keyword) throws IllegalCmdArgsException {
        keyword = keyword.toLowerCase();
        String curKeyword = CommandConstants.KEYWORD_PREFIX + keyword.toLowerCase();
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].toLowerCase().equals(curKeyword)) {
                indexes.add(i);
            }
        }
        if (indexes.size() > 1) {
            throw new IllegalCmdArgsException("There are more than 1 instances of " +
                                CommandConstants.KEYWORD_PREFIX + keyword + " in the command.");
        } else if (indexes.size() < 1) {
            return null;
        }
        // We allow multiple interpretations of the command if no clear keywords are used
        return indexes.get(0);
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