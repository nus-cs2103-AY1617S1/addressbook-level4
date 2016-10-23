package seedu.ggist.logic.parser;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ggist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.StringUtil;
import seedu.ggist.logic.commands.*;
import seedu.ggist.model.task.Priority;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)", Pattern.CASE_INSENSITIVE);

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)", Pattern.CASE_INSENSITIVE);

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)", Pattern.CASE_INSENSITIVE); // one or more keywords separated by whitespace
    
    private static final Pattern LIST_ARGS_FORMAT =
            Pattern.compile("(?<listing>.*)", Pattern.CASE_INSENSITIVE);

    //regex for floating
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)" , Pattern.CASE_INSENSITIVE); 
    
  //regex for floating with priority
    private static final Pattern FLOATING_WITH_PRIORITY = 
            Pattern.compile("(?<taskName>.+)"
                    + "\\s*(?:-)(?<priority>.+)" , Pattern.CASE_INSENSITIVE); 
    
    //regex for tasks with deadline
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)"
                    + "((\\s*,\\s*)|(\\s+?(by|on)\\s+?))(?<dateTime>.+)" , Pattern.CASE_INSENSITIVE);
        
    //regex for tasks with start and end time spanning diff days
    private static final Pattern EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)"
                    + "((\\s*,\\s*)|(\\s+?(from)\\s+?))(?<startDateTime>.+)"
                    + "((\\s*(,)\\s*)|(\\s+?to\\s+?))(?<endDateTime>.+)" , Pattern.CASE_INSENSITIVE);
    
  //regex for tasks with start and end time on same days
    private static final Pattern EVENT_TASK_SAME_DAY_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)"
                    + "((\\s*,\\s*)|(\\s+?(by|on)\\s+))(?<day>.+"
                    + ")((\\s*,\\s*)|(\\s+?(from)\\s+))(?<startTime>.+)"
                    + "((\\s*(,)\\s*)|(\\s+?to\\s+?))(?<endTime>.+)" , Pattern.CASE_INSENSITIVE);
    
    //regex for edit
    private static final Pattern EDIT_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<index>\\d+?)"
                    + "\\s+?(?<field>(task|start date|start time|end date|end time|priority))"
                    + "\\s+?(?<value>.+)" , Pattern.CASE_INSENSITIVE);
   
    public static final Pattern PRIORITY_MATCHER_REGEX = Pattern.compile("(?:.*-\\s*(?<priority>.+))");
    
    public Parser() {}
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws IllegalValueException 
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
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);
        
        case EditCommand.COMMAND_WORD: 
            return prepareEdit(arguments);
                
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
            return prepareSearch(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);
                
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final String taskType = matchTaskType(args.trim());
        Matcher matcher;
        Matcher matcherTwo;
        String priority;
        if (taskType.equals("taskTypeNotFound")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }    
        try {
            if(taskType.equals("eventTask")) {
                matcher = EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT.matcher(args.trim());
                matcherTwo = EVENT_TASK_SAME_DAY_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcherTwo.matches()){
                    priority = parsePriority(matcherTwo.group("endTime"));
                    return new AddCommand(
                            matcherTwo.group("taskName"),
                            new DateTimeParser(matcherTwo.group("day")).getDate(),
                            new DateTimeParser(matcherTwo.group("startTime")).getTime(),
                            new DateTimeParser(matcherTwo.group("day")).getDate(),
                            new DateTimeParser(matcherTwo.group("endTime")).getTime(),
                            priority
                            );
                
                    } else if (matcher.matches()) {
                        priority = parsePriority(matcher.group("endDateTime"));
                        return new AddCommand(
                                matcher.group("taskName"),
                                new DateTimeParser(matcher.group("startDateTime")).getDate(),
                                new DateTimeParser(matcher.group("startDateTime")).getTime(),
                                new DateTimeParser(matcher.group("endDateTime")).getDate(),
                                new DateTimeParser(matcher.group("endDateTime")).getTime(),
                                priority
                                );
                    }
            } else if (taskType.equals("deadlineTask")) {
                matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    priority = parsePriority(matcher.group("dateTime"));
                    return new AddCommand(
                        matcher.group("taskName"),
                        new DateTimeParser(matcher.group("dateTime")).getDate(),
                        new DateTimeParser(matcher.group("dateTime")).getTime(),
                        priority
                     );
                }
            } else if (taskType.equals("floatingTask")) {
                matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                matcherTwo = FLOATING_WITH_PRIORITY.matcher(args.trim());
                if (matcherTwo.matches()) {
                    return new AddCommand(
                        matcherTwo.group("taskName"),
                        matcherTwo.group("priority")
                    );
                } else if (matcher.matches()) {
                    return new AddCommand(
                        matcher.group("taskName"),
                        null
                    );
                }
            }                         
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return null;   
    }
    
    
    /**
     *  Matches arg string format and validates
     * @param args full command string
     * @return the task type in String
     */
    private String matchTaskType(String args) {
        Matcher matcher;
        if ((matcher = EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT.matcher(args)).matches() || 
            ((matcher = EVENT_TASK_SAME_DAY_DATA_ARGS_FORMAT.matcher(args)).matches())) {
            return new String("eventTask");
        } else if ((matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args)).matches()) {
            return new String("deadlineTask");
        } else if ((matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args)).matches() || 
                ((matcher = FLOATING_WITH_PRIORITY.matcher(args)).matches())) {
            return new String("floatingTask");
        }       
        return new String("taskTypeNotFound");
    }
    
    private String parsePriority(String args) {
        Matcher matcherPriority = PRIORITY_MATCHER_REGEX.matcher(args);
        if (!matcherPriority.matches()) {
            return null;
        }
        return matcherPriority.group("priority");
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        String[] parts = args.split(",");
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < parts.length; i++){
            indexes.add(Integer.parseInt(parts[i].trim()));
        }
        
        for(int i = 0; i < parts.length; i++){
        Optional<Integer> index = parseIndex(parts[i]);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        
        return new DeleteCommand(indexes);
    }
    
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
        String[] parts = args.split(",");
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < parts.length; i++){
            indexes.add(Integer.parseInt(parts[i].trim()));
        }
       
        for (int i =0; i < parts.length;i++){
        Optional<Integer> index = parseIndex(parts[i]);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
        }
        return new DoneCommand(indexes);
    }
    
    private Command prepareEdit(String args) {
        Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        int index = Integer.parseInt(matcher.group("index"));
        String field  = matcher.group("field");
        String value = matcher.group("value");

        try {
            if (field.equals("start date") || field.equals("end date")) {
                value = new DateTimeParser(value).getDate();
            }
        
            if (field.equals("start time") || field.equals("end time")) {
                value = new DateTimeParser(value).getTime();
            }
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
         return new EditCommand(index, field.trim(), value.trim());
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
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSearch(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new SearchCommand(keywordSet);
    }
    
    /**
     * Parses arguments in the context of the list command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args) {
        final Matcher matcher = LIST_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }
        final String listing = matcher.group("listing");
        
        try {
            if (args.equals("") || ListCommand.isValidListArgs(listing)) {
                return new ListCommand(listing);
            } else {
                String dateListing = new DateTimeParser(listing).getDate();
                return new ListCommand(dateListing);
            }
        } catch (IllegalValueException e) {
            return new IncorrectCommand(ListCommand.MESSAGE_USAGE);
        }
    }
    
    /**
     * Parses arguments in the context of the save command.
     *
     * @param args full command arguments string
     * @return the prepared command
     */
    private Command prepareSave(String args) {
        if (args.equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveCommand.MESSAGE_USAGE));
        }
        try {
            return new SaveCommand(args.trim());
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}

