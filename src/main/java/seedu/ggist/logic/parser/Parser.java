package seedu.ggist.logic.parser;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ggist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.StringUtil;
import seedu.ggist.logic.commands.*;

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

    //regex for tasks without deadline
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.*)"
                    + "(?<tagArguments>(?: [^,]+)*)"); // variable number of tags;
    
    //regex for tasks with deadline
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.*)\\s*,\\s*(?<taskDate>.*)\\s*,\\s*(?<time>\\d{4})\\s*,*\\s*(?<tagArguments>(?:[^,]+)*)");
        
    //regex for tasks with start and end time
    private static final Pattern EVENT_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.*)\\s*,\\s*(?<taskDate>.*)\\s*,\\s*(?<startTime>\\d{4})\\s*-\\s*(?<endTime>\\d{4})\\s*,*\\s*(?<tagArguments>(?:[^,]+)*)");
   
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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

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
        if (taskType.equals("taskTypeNotFound")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }    
        try {
            if(taskType.equals("eventTask")) {
                matcher = EVENT_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    return new AddCommand(
                        matcher.group("taskName"),
                        matcher.group("taskDate"),
                        matcher.group("startTime"),
                        matcher.group("endTime"),
                        getTagsFromArgs(matcher.group("tagArguments"))
                     );
                }
            } else if (taskType.equals("deadlineTask")) {
                matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    return new AddCommand(
                        matcher.group("taskName"),
                        matcher.group("taskDate"),
                        matcher.group("time"),
                        getTagsFromArgs(matcher.group("tagArguments"))
                     );
                }
            } else if (taskType.equals("floatingTask")) {
                matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    return new AddCommand(
                        matcher.group("taskName"),
                        getTagsFromArgs(matcher.group("tagArguments"))
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
        if ((matcher = EVENT_TASK_DATA_ARGS_FORMAT.matcher(args)).matches()) {
            return new String("eventTask");
        } else if ((matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args)).matches()) {
            return new String("deadlineTask");
        } else if ((matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args)).matches()) {
            return new String("floatingTask");
        }       
        return new String("taskTypeNotFound");
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
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
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

}