package seedu.todoList.logic.parser;

import seedu.todoList.logic.commands.*;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.commons.util.StringUtil;

import static seedu.todoList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoList.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

    private static final Pattern task_DELETE_ARGS_FORMAT = Pattern.compile("(?<dataType>\\S+)(?<targetIndex>.*)");

    //one or more keywords separated by whitespace
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 

    /*
     * Add Command, task,event,deadline pattern
     */
    private static final Pattern task_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" 
                    + " from/(?<date>[^/]+)" 
                    + "( to/(?<endDate>[^/]+))?"
                    + " p/(?<priority>[^/]+)");

    private static final Pattern event_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" 
                    + " from/(?<date>[^/]+)" 
                    + "( to/(?<endDate>[^/]+))?"
                    + " at/(?<startTime>[^/]+)"
                    + " to/(?<endTime>[^/]+)");

    private static final Pattern deadline_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" 
                    + " on/(?<date>[^/]+)" 
                    + " at/(?<endTime>[^/]+)");

    /*
     * Edit Command, task,event,deadline pattern
     */
    private static final Pattern task_EDIT_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<dataType>.+)" 
                    + " (?<targetIndex>.+)" 
                    + " name/(?<name>.+)" 
                    + " from/(?<date>[^/]+)"
                    + "( to/(?<endDate>[^/]+))?"
                    + " p/(?<priority>[^/]+)");

    private static final Pattern event_EDIT_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<dataType>.+)" 
                    + " (?<targetIndex>.+)" 
                    + " name/(?<name>.+)" 
                    + " from/(?<date>[^/]+)"
                    + "( to/(?<endDate>[^/]+))?"
                    + " at/(?<startTime>[^/]+)" 
                    + " to/(?<endTime>[^/]+)");

    private static final Pattern deadline_EDIT_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<dataType>.+)" 
                    + " (?<targetIndex>.+)" 
                    + " name/(?<name>.+)" 
                    + " on/(?<date>[^/]+)"
                    + " at/(?<endTime>[^/]+)");
    
    private static final Pattern name_ARGS_FORMAT = Pattern.compile("n/(?<name>.+)");
    private static final Pattern priority_ARGS_FORMAT = Pattern.compile("p/(?<priority>.+)");
    private static final Pattern date_ARGS_FORMAT = Pattern.compile("d/(?<date>.+)");
    private static final Pattern endDate_ARGS_FORMAT = Pattern.compile("nd/(?<endDate>.+)");
    private static final Pattern startTime_ARGS_FORMAT = Pattern.compile("s/(?<startTime>.+)");
    private static final Pattern endTime_ARGS_FORMAT = Pattern.compile("e/(?<endTime>.+)");

    public Parser() {
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case DoneCommand.COMMAND_WORD:
        	return prepareDone(arguments);
        	
        case UndoneCommand.COMMAND_WORD:
            return prepareUndone(arguments);
            
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand(arguments);

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StorageCommand.COMMAND_WORD:
            return new StorageCommand(arguments);
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcher_task = task_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_event = event_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_deadline = deadline_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        /*
         * Check if input matches task, event or deadline
         */
        
        if (matcher_task.matches()) {
            try {
                return new AddCommand(
                        matcher_task.group("name").trim(), 
                        matcher_task.group("date").trim(),
                        isInputPresent(matcher_task.group("endDate")),
                        matcher_task.group("priority").trim()
                        );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (matcher_event.matches()) {
            try {
                return new AddCommand(
                        matcher_event.group("name").trim(), 
                        matcher_event.group("date").trim(),
                        isInputPresent(matcher_event.group("endDate")).trim(),
                        matcher_event.group("startTime").trim(), 
                        matcher_event.group("endTime").trim()
                        );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else if (matcher_deadline.matches()) {
            try {
                return new AddCommand(
                        matcher_deadline.group("name").trim(), 
                        matcher_deadline.group("date").trim(),
                        matcher_deadline.group("endTime").trim()
                        );
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        Optional<String> dataType = parseDataType(args);
        Optional<Integer> index = parseIndex(args);
        if (!dataType.isPresent() || !((dataType.get().equals("todo")) || (dataType.get().equals("event"))
                || (dataType.get().equals("deadline"))) || !index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(dataType.get(), index.get());
    }
    
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
        Optional<String> dataType = parseDataType(args);
        Optional<Integer> index = parseIndex(args);
        if (!dataType.isPresent() || !((dataType.get().equals("todo")) || (dataType.get().equals("event"))
                || (dataType.get().equals("deadline"))) || !index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(dataType.get(), index.get());
    }
    
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareUndone(String args) {
        Optional<String> dataType = parseDataType(args);
        Optional<Integer> index = parseIndex(args);
        if (!dataType.isPresent() || !((dataType.get().equals("todo")) || (dataType.get().equals("event"))
                || (dataType.get().equals("deadline"))) || !index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }

        return new UndoneCommand(dataType.get(), index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = task_DELETE_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex").trim();
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns the specified dataType in the {@code command} Returns an
     * {@code Optional.empty()} otherwise.
     */
    private Optional<String> parseDataType(String command) {
        final Matcher matcher = task_DELETE_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String dataType = matcher.group("dataType");
        if (!StringUtil.isUnsignedString(dataType)) {
            return Optional.empty();
        }
        return Optional.of(dataType);
    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher_keywords = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        Optional<String> dataType = parseDataType(args);
        if (!dataType.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher_keywords.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        return new FindCommand(keywordSet, dataType.get());
    }

    private Command prepareEdit(String args) {
        final Matcher matcher_task = task_EDIT_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_event = event_EDIT_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_deadline = deadline_EDIT_ARGS_FORMAT.matcher(args.trim());

        final Matcher matcher_name = name_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_priority = priority_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_date = date_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_st = startTime_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher_et = endTime_ARGS_FORMAT.matcher(args.trim());
        
        Optional<String> dataType = parseDataType(args);
        
        if (!dataType.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // check user input to edit todolist
        if (dataType.get().equals("todo")) {
            if (matcher_task.matches()) {
                try {
                    return new EditCommand(
                            matcher_task.group("name").trim(), 
                            matcher_task.group("date").trim(),
                            isInputPresent(matcher_task.group("endDate")).trim(),
                            matcher_task.group("priority").trim(),
                            Integer.parseInt(matcher_task.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_event.matches()) {
                try {
                    return new EditCommand(
                            matcher_event.group("name").trim(), 
                            matcher_event.group("date").trim(),
                            isInputPresent(matcher_event.group("endDate")).trim(),
                            matcher_event.group("startTime").trim(), 
                            matcher_event.group("endTime").trim(),
                            Integer.parseInt(matcher_event.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_deadline.matches()) {
                try {
                    return new EditCommand(
                            matcher_deadline.group("name").trim(), 
                            matcher_deadline.group("date").trim(),
                            matcher_deadline.group("endTime").trim(), 
                            Integer.parseInt(matcher_deadline.group("targetIndex")),
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        
        // check user input to edit eventlist
        } else if (dataType.get().equals("event")) {
            if (matcher_task.matches()) {
                try {
                    return new EditCommand(
                            matcher_task.group("name").trim(), 
                            matcher_task.group("date").trim(),
                            isInputPresent(matcher_task.group("endDate")).trim(),
                            matcher_task.group("priority").trim(),
                            Integer.parseInt(matcher_task.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_event.matches()) {
                try {
                    return new EditCommand(
                            matcher_event.group("name").trim(), 
                            matcher_event.group("date").trim(),
                            isInputPresent(matcher_event.group("endDate")).trim(),
                            matcher_event.group("startTime").trim(), 
                            matcher_event.group("endTime").trim(),
                            Integer.parseInt(matcher_event.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_deadline.matches()) {
                try {
                    return new EditCommand(
                            matcher_deadline.group("name").trim(), 
                            matcher_deadline.group("date").trim(),
                            matcher_deadline.group("endTime").trim(), 
                            Integer.parseInt(matcher_deadline.group("targetIndex")),
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        
        // check user input to edit deadlinelist
        } else if (dataType.get().equals("deadline")) {
            if (matcher_task.matches()) {
                try {
                    return new EditCommand(
                            matcher_task.group("name").trim(), 
                            matcher_task.group("date").trim(),
                            isInputPresent(matcher_task.group("endDate")).trim(),
                            matcher_task.group("priority").trim(),
                            Integer.parseInt(matcher_task.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_event.matches()) {
                try {
                    return new EditCommand(
                            matcher_event.group("name").trim(), 
                            matcher_event.group("date").trim(),
                            isInputPresent(matcher_event.group("endDate")).trim(),
                            matcher_event.group("startTime").trim(), 
                            matcher_event.group("endTime").trim(),
                            Integer.parseInt(matcher_event.group("targetIndex")), 
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else if (matcher_deadline.matches()) {
                try {
                    return new EditCommand(
                            matcher_deadline.group("name").trim(), 
                            matcher_deadline.group("date").trim(),
                            matcher_deadline.group("endTime").trim(), 
                            Integer.parseInt(matcher_deadline.group("targetIndex")),
                            dataType.get().trim(),
                            "false");
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }
    
    /*
     *  Check whether the attribute is set
     */
    private String isInputPresent(String input){
        return input == null ? "No End Date" : input;
    }
}