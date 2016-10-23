package seedu.malitio.logic.parser;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.commons.util.StringUtil;
import seedu.malitio.logic.commands.*;
import seedu.malitio.model.task.Name;

import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.malitio.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>)[e|d|f|E|D|F]\\d+");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern EDIT_DATA_ARGS_FORMAT =
            Pattern.compile("(?<targetIndex>[e|d|f|E|D|F]\\d+)"
                    + "(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Set<String> TYPES_OF_TASKS = new HashSet<String>(Arrays.asList("f", "d", "e" ));

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

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @author Nathan A0153006W
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        boolean hasStart = false;
        boolean hasEnd = false;
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(Name.MESSAGE_NAME_CONSTRAINTS, AddCommand.MESSAGE_USAGE));
        }
        try {
            String name = matcher.group("name");
            
            String deadline = getDeadlineFromArgs(name);
            if (!deadline.isEmpty()) {
                name = name.replaceAll("by " + deadline, "");
            }
            
            String start = getStartFromArgs(name);
            if (!start.isEmpty()) {
                name = name.replaceAll("start " + start, "");
                hasStart = true;
            }
            
            String end = getEndFromArgs(name);
            if (!end.isEmpty()) {
                name = name.replaceAll("end " + end, "");
                hasEnd = true;
            }
            
            if (!deadline.isEmpty()) {
                return new AddCommand(
                        name,
                        deadline,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            } else if (hasStart && hasEnd) {
                return new AddCommand(
                        name,
                        start,
                        end,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            } else if (hasStart ^ hasEnd) {
                return new IncorrectCommand("Expecting start and end times\nExample: start 10032016 1200 end 10032016 1300");
            }
            return new AddCommand(
                    name,
                    getTagsFromArgs(matcher.group("tagArguments"))
                    );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * 
     * @param arguments
     * @return the prepared command
     * @author Bel
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        try {
            String index = parseIndex(matcher.group("targetIndex"));
            if (index.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            char taskType = index.charAt(0);
            int taskNum = Integer.parseInt(index.substring(1));
            
            String name = matcher.group("name");
            
            String deadline = getDeadlineFromArgs(name);
            if (!deadline.isEmpty()) {
                name = name.replaceAll(" by " + deadline, "");
            }
            
            String start = getStartFromArgs(name);
            if (!start.isEmpty()) {
                name = name.replaceAll(" start " + start, "");
            }
            
            String end = getEndFromArgs(name);
            if (!end.isEmpty()) {
                name = name.replaceAll(" end " + end, "");
            }
            
            if (!deadline.isEmpty()) {
                return new EditCommand(
                        taskType,
                        taskNum,
                        name,
                        deadline,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            } else if (!start.isEmpty() || !end.isEmpty()) {
                return new EditCommand(
                        taskType,
                        taskNum,
                        name,
                        start,
                        end,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            }
            return new EditCommand(
                    taskType,
                    taskNum,
                    name,
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        String index = parseIndex(args);
        char taskType = index.charAt(0);
        int taskNum = Integer.parseInt(index.substring(1));
        if(index.isEmpty()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(taskType, taskNum);
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        String index = parseIndex(args);
        char taskType = index.charAt(0);
        int taskNum = Integer.parseInt(index.substring(1));
        if(index.isEmpty()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
//TODO: fix the Select Command to support e|f|d
        return new SelectCommand(taskNum);
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
        String[] keywords = matcher.group("keywords").split("\\s+");
        String typeOfTask = "";
        
        if(TYPES_OF_TASKS.contains(keywords[0])) {
            typeOfTask = keywords[0];
        }
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(typeOfTask, keywordSet);
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @author Nathan A0153006W
     */
    private Command prepareList(String args) {
        if (args.isEmpty()) {
            return new ListCommand();
        }
        try {
        args = args.trim().toLowerCase();
        return new ListCommand(args);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
    
    /**
     * Returns the specified index as a String in the {@code command}
     */
    private String parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return "";
        }
        String index = command.trim().toLowerCase();
        return index;
    }
    
    /**
     * Extracts the task's deadline from the command's arguments string.
     * @author Nathan A0153006W
     */
    private static String getDeadlineFromArgs(String args) throws IllegalValueException {
        int byIndex = args.lastIndexOf("by");
        String deadline = "";
        if(byIndex > 0 && byIndex < args.length() - 2) {
            try {
                deadline = args.substring(byIndex + 3);
                if (deadline.matches("[^\\d]+")) {
                    return "";
                } else if (!deadline.matches("\\d{8} \\d{4}")) {
                    throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers\nExample: by 03122016 1320");
                }
            } catch (IndexOutOfBoundsException iob){
                throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers\nExample: by 03122016 1320");
            }
        }
        return deadline;
    }

    /**
     * Extracts the task's event start from the command's arguments string.
     */
    private static String getStartFromArgs(String args) throws IllegalValueException {
        int startIndex = args.lastIndexOf("start");
        String start = "";
        if(startIndex > 0 && startIndex < args.length() - 2) {
            try {
                start = args.substring(startIndex + 6, startIndex + 19);
                if (start.matches("[^\\d]+")) {
                    return "";
                }
                else if (!start.matches("\\d{8} \\d{4}")) {
                    throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers");
                }
            } catch (IndexOutOfBoundsException iob){
                throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers");
            }
        }
        return start;
    }

    /**
     * Extracts the task's event end from the command's arguments string.
     */
    private static String getEndFromArgs(String args) throws IllegalValueException {
        int endIndex = args.lastIndexOf("end");
        String end = "";
        if(endIndex > 0 && endIndex < args.length() - 2) {
            try {
                end = args.substring(endIndex + 4, endIndex + 17);
                if (end.matches("[^\\d]+")) {
                    return "";
                } else if (!end.matches("\\d{8} \\d{4}")) {
                    throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers");
                }
            } catch (IndexOutOfBoundsException iob){
                throw new IllegalValueException("Expecting 8 numbers followed by 4 numbers");
            }
        }
        return end;
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
}