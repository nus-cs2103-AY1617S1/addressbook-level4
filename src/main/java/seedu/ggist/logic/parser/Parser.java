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
    
    private static final Pattern LIST_ARGS_FORMAT =
            Pattern.compile("(?<listing>.*)");

    //regex for tasks without deadline
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)\\s*,*\\s*(?<tagArguments>(?: t/[^,]+)*)"); // variable number of tags;
    
    //regex for tasks with deadline
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)\\s*(,|by|on|at)\\s*(?<dateTime>.+)\\s*,*\\s*(?<tagArguments>(?: t/[^,]+)*)");
        
    //regex for tasks with start and end time spanning different days
    private static final Pattern EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)\\s*(,|from)\\s*(?<startDateTime>.+)\\s*(,|-)\\s*(?<endDateTime>.+)\\s*,*\\s*(?<tagArguments>(?:t/[^,]+)*)");
   
  //regex for tasks with start and end time within same day
    private static final Pattern EVENT_TASK_SAME_DAYS_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<taskName>.+)\\s*(,|on)\\s*(?<day>.+)\\s*(,|from)\\s*(?<startTime>.+)\\s*(,|-)\\s*(?<endTime>.+)\\s*,*\\s*(?<tagArguments>(?:t/[^,]+)*)");
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
        if (taskType.equals("taskTypeNotFound")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }    
        try {
            if(taskType.equals("eventTask")) {
                matcher = EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT.matcher(args.trim());
                matcherTwo = EVENT_TASK_SAME_DAYS_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcherTwo.matches()) {
                    System.out.println("events same");
                    return new AddCommand(
                        matcherTwo.group("taskName"),
                        new DateTimeParser(matcherTwo.group("day")).getDate(),
                        new DateTimeParser(matcherTwo.group("startTime")).getTime(),
                        new DateTimeParser(matcherTwo.group("day")).getDate(),
                        new DateTimeParser(matcherTwo.group("endTime")).getTime(),
                        getTagsFromArgs(matcherTwo.group("tagArguments"))
                 );
                } else if (matcher.matches()) {
                    System.out.println("events diff");
                    return new AddCommand(
                        matcher.group("taskName"),
                        new DateTimeParser(matcher.group("startDateTime")).getDate(),
                        new DateTimeParser(matcher.group("startDateTime")).getTime(),
                        new DateTimeParser(matcher.group("endDateTime")).getDate(),
                        new DateTimeParser(matcher.group("endDateTime")).getTime(),
                        getTagsFromArgs(matcher.group("tagArguments"))
                     );
                }
                
            } else if (taskType.equals("deadlineTask")) {
                matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    System.out.println("deadline");
                    return new AddCommand(
                        matcher.group("taskName"),
                        new DateTimeParser(matcher.group("dateTime")).getDate(),
                        new DateTimeParser(matcher.group("dateTime")).getTime(),
                        getTagsFromArgs(matcher.group("tagArguments"))
                     );
                }
            } else if (taskType.equals("floatingTask")) {
                matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
                if (matcher.matches()) {
                    System.out.println("floating");
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
        if ((matcher = EVENT_TASK_DIFF_DAYS_DATA_ARGS_FORMAT.matcher(args)).matches() || 
           (matcher = EVENT_TASK_SAME_DAYS_DATA_ARGS_FORMAT.matcher(args)).matches() ) {
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
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        return new DoneCommand(index.get());
    }
    
    private Command prepareEdit(String args) {
    	
    	String type;
    	String toEdit;
    	int index = -1;
  
    	String[] splitedArgs = args.trim().split("\\s+");
    	if (splitedArgs.length >= 3) {
    		index = Integer.parseInt(splitedArgs[0]);
    		type = splitedArgs[1];
    		StringBuffer toBeEdited = new StringBuffer ();
    		for (int i=2; i<splitedArgs.length; i++) {
    			toBeEdited.append(splitedArgs[i]);
    			toBeEdited.append(" ");
    		}	
    		toEdit = toBeEdited.toString();
    
    	} else {
    		return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    	}
         return new EditCommand (index,type,toEdit.trim());
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
        return new SaveCommand(args.trim());
    }

}

