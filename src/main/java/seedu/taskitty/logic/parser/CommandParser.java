package seedu.taskitty.logic.parser;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.commons.util.TaskUtil;
import seedu.taskitty.logic.commands.*;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskitty.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import javafx.util.Pair;

/**
 * Parses user input.
 */
public class CommandParser {

    public static final String COMMAND_QUOTE_SYMBOL = "\"";
    public static final String EMPTY_STRING = "";
    public static final int NOT_FOUND = -1;
    public static final int STRING_START = 0;
    
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
         
    //Used for checking for number date formats in arguments
    private static final Pattern LOCAL_DATE_FORMAT =  Pattern.compile(".* (?<arguments>\\d(\\d)?[/-]\\d(\\d)?).*");
    
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = //Tags must be at the end
            Pattern.compile("(?<arguments>[\\p{Graph} ]+)"); // \p{Graph} is \p{Alnum} or \p{Punct}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    HelpCommand.MESSAGE_ERROR));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
        
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case DoneCommand.COMMAND_WORD:
        	return prepareDone(arguments);
        	
        case ViewCommand.COMMAND_WORD:
        	if (userInput.trim().equals("view")) {
        		return prepareView(null);
        	}
        	return prepareView(arguments);
        
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    //@@author A0135793W
    private Command prepareSave(String argument) {
        try {
            return new SaveCommand(argument.trim());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author A0130853L

    /**
     * Parses arguments in the context of the view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String arguments) {
    	if (arguments == null) {
			return new ViewCommand(); // view all upcoming uncompleted tasks, events and deadlines
		}
    	if (arguments.trim().equals("done")) {
    		return new ViewCommand("done"); // view done command
    	}
    	if (arguments.trim().equals("all")) {
    		return new ViewCommand("all"); // view all command
    	}
		String[] details = extractTaskDetailsNatty(arguments);
		if (details.length!= 3) { // no date was successfully extracted
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
			        Command.MESSAGE_FORMAT + ViewCommand.MESSAGE_PARAMETER));
	    } else {
	        assert details[1] != null; // contains date
	        return new ViewCommand(details[1]);
	    }
	}

    //@@author A0139930B
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
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Command.MESSAGE_FORMAT + AddCommand.MESSAGE_PARAMETER));
        }
        try {
            String arguments = matcher.group("arguments");
            String taskDetailArguments = getTaskDetailArguments(arguments);
            String tagArguments = getTagArguments(arguments);
            
            return new AddCommand(
                    extractTaskDetailsNatty(taskDetailArguments),
                    getTagsFromArgs(tagArguments),
                    args
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses the argument to get a string of all the relevant details of the task
     * 
     * @param arguments command args string without command word
     */
    private String getTaskDetailArguments(String arguments) {
        int detailLastIndex = arguments.indexOf(Tag.TAG_VALIDATION_REGEX_PREFIX);
        if (detailLastIndex == NOT_FOUND) {
            detailLastIndex = arguments.length();
        }
        
        return arguments.substring(STRING_START, detailLastIndex).trim();
    }
    
    /**
     * Parses the argument to get a string of all tags, including the Tag prefix
     * 
     * @param arguments command args string without command word
     */
    private String getTagArguments(String arguments) {
        //This line is exactly the same as the 1st line of getTaskDetailArguments.. how?
        int tagStartIndex = arguments.indexOf(Tag.TAG_VALIDATION_REGEX_PREFIX);
        if (tagStartIndex == NOT_FOUND) {
            tagStartIndex = arguments.length();
        }
        
        return arguments.substring(tagStartIndex);
    }
    
    /**
     * Extracts the task details into a String array representing the name, date, time.
     * Details are arranged according to index shown in Task
     * 
     * @param dataArguments command args string with only name, date, time arguments
     */
    private String[] extractTaskDetailsNatty(String dataArguments) {
        String dataArgumentsNattyFormat = convertToNattyDateFormat(dataArguments);
        
        int nameEndIndex = dataArgumentsNattyFormat.length();
        ArrayList<String> details = new ArrayList<String>();
        
        //Attempt to extract name out if it is surrounded by quotes
        nameEndIndex = dataArgumentsNattyFormat.lastIndexOf(COMMAND_QUOTE_SYMBOL);
        boolean isNameExtracted = false;
        if (nameEndIndex != NOT_FOUND) {
            int nameStartIndex = dataArgumentsNattyFormat.indexOf(COMMAND_QUOTE_SYMBOL);
            if (nameStartIndex == NOT_FOUND) {
                nameStartIndex = STRING_START;
            }
            //+1 because we want the quote included in the string
            String nameDetail = dataArgumentsNattyFormat.substring(nameStartIndex, nameEndIndex + 1);
            
            //remove name from dataArguments
            dataArgumentsNattyFormat = dataArgumentsNattyFormat.replace(nameDetail, EMPTY_STRING);
            
            //remove quotes from nameDetail
            nameDetail = nameDetail.replaceAll(COMMAND_QUOTE_SYMBOL, EMPTY_STRING);
            
            details.add(Task.TASK_COMPONENT_INDEX_NAME, nameDetail);
            isNameExtracted = true;
        }
        
        Parser dateTimeParser = new Parser(); 
        List<DateGroup> dateGroups = dateTimeParser.parse(dataArgumentsNattyFormat);
        nameEndIndex = dataArgumentsNattyFormat.length();
        
        for (DateGroup group : dateGroups) {
            List<Date> dates = group.getDates();
            //Natty's getPosition returns 1 based position
            //-1 because we want the 0 based position
            nameEndIndex = Math.min(nameEndIndex, group.getPosition() - 1);
            for (Date date : dates) {
                details.add(extractLocalDate(date));
                details.add(extractLocalTime(date));
            }
        }
        
        if (!isNameExtracted) {
            details.add(Task.TASK_COMPONENT_INDEX_NAME,
                    dataArgumentsNattyFormat.substring(STRING_START, nameEndIndex).trim());
        }
        
        String[] returnDetails = new String[details.size()];
        details.toArray(returnDetails);
        return returnDetails;
    }
    
    //@@author A0139052L
    /**
     * Converts any number formats of date from the local format to one which can be parsed by natty
     * @param arguments
     * @return arguments with converted dates if any
     */
    private String convertToNattyDateFormat(String arguments) {
        Matcher matchDate = LOCAL_DATE_FORMAT.matcher(arguments);
        if (matchDate.matches()) {
            String localDateString = matchDate.group("arguments");
            String dateSeparator = getDateSeparator(localDateString);
            return convertToNattyFormat(arguments, localDateString, dateSeparator);
        } else {
            return arguments;
        }       
    }
    
    /**
     * Get the separator between day month and year in a date
     * @param localDateString the string representing the date
     * @return the separator character used in localDateString
     */
    private String getDateSeparator(String localDateString) {
        // if 2nd char in string is an integer, then the 3rd char must be the separator
        // else 2nd char is the separator
        if (StringUtil.isInteger(localDateString.substring(1,2))) {
            return localDateString.substring(2, 3);
        } else {
            return localDateString.substring(1, 2);
        }
    }
    
    /**
     * Convert the local date format inside arguments into a format
     * which can be parsed by natty
     * @param arguments the full argument string
     * @param localDateString the localDate extracted out from arguments
     * @param dateSeparator the separator for the date extracted out
     * @return converted string where the date format has been converted from local to natty format
     */
    private String convertToNattyFormat(String arguments, String localDateString, String dateSeparator) {
        String[] dateComponents = localDateString.split(dateSeparator);
        int indexOfDate = arguments.indexOf(localDateString);
        String nattyDateString = swapDayAndMonth(dateComponents, dateSeparator);
        arguments = arguments.replace(localDateString, nattyDateString);
        String stringFromConvertedDate = arguments.substring(indexOfDate);
        String stringUpToConvertedDate = arguments.substring(0, indexOfDate);
        return convertToNattyDateFormat(stringUpToConvertedDate) + stringFromConvertedDate;
    }
    
    /**
     * Swaps the day and month component of the date
     * @param dateComponents the String array obtained after separting the date string
     * @param dateSeparator the Separator used in the date string
     * @return the date string with its day and month component swapped
     */
    private String swapDayAndMonth(String[] dateComponents, String dateSeparator) {
        StringBuilder nattyDateStringBuilder = new StringBuilder();
        nattyDateStringBuilder.append(dateComponents[1]);
        nattyDateStringBuilder.append(dateSeparator);
        nattyDateStringBuilder.append(dateComponents[0]);
        return nattyDateStringBuilder.toString();
    }
    
    //@@author A0139930B
    /**
     * Takes in a date from Natty and converts it into a string representing date
     * Format of date returned is according to TaskDate
     * 
     * @param date retrieved using Natty
     */
    private String extractLocalDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TaskDate.DATE_FORMAT_STRING);
        return dateFormat.format(date);
    }
    
    /**
     * Takes in a date from Natty and converts it into a string representing time
     * Format of time returned is according to TaskTime
     * 
     * @param date retrieved using Natty
     */
    private String extractLocalTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TaskTime.TIME_FORMAT_STRING);
        String currentTime = timeFormat.format(new Date());
        String inputTime = timeFormat.format(date);
        
        if (currentTime.equals(inputTime)) {
            //Natty parses the current time if string does not include time.
            //We want to ignore input when current time equal input time
            return null;
        }
        return inputTime;
        
    }

    //@@author
    /**
     * Extracts the new person's tags from the add command's tag arguments string.
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
    
    //@@author A0139052L
    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        
        String dataArgs = args.trim();
        String[] indexes = dataArgs.split("\\s");
        Pair<Integer, Integer> categoryAndIndex = null;
        ArrayList<Pair<Integer, Integer>> listOfIndexes = new ArrayList<Pair<Integer, Integer>>();
        
        for (String index: indexes) {
            categoryAndIndex= getCategoryAndIndex(index);
            if (categoryAndIndex == null) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                Command.MESSAGE_FORMAT + DeleteCommand.MESSAGE_PARAMETER));
            }
            listOfIndexes.add(categoryAndIndex);
        }
        
        return new DeleteCommand(listOfIndexes, args);
    }
    
    //@@author A0135793W
    /**
     * Parses arguments in the context of the mark as done command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
        
        String dataArgs = args.trim();                
        String[] indexes = dataArgs.split("\\s");
        Pair<Integer, Integer> categoryAndIndex = null;
        ArrayList<Pair<Integer, Integer>> listOfIndexes = new ArrayList<Pair<Integer, Integer>>();
        
        for (String index: indexes) {
            categoryAndIndex= getCategoryAndIndex(index);
            if (categoryAndIndex == null) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                Command.MESSAGE_FORMAT + DoneCommand.MESSAGE_PARAMETER));
            }
            listOfIndexes.add(categoryAndIndex);
        }
        
        return new DoneCommand(listOfIndexes, args);
    }
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        String[] splitArgs = args.trim().split(" ");
        if (splitArgs.length < 2) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        }
        
        Pair<Integer, Integer> categoryAndIndexPair = getCategoryAndIndex(splitArgs[0]);
        
        if (categoryAndIndexPair == null) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Command.MESSAGE_FORMAT + EditCommand.MESSAGE_PARAMETER));
        }
        
        try {
            String arguments = "";
            for (int i = 1; i<splitArgs.length; i++){
                arguments = arguments + splitArgs[i] + " ";
            }
            arguments.substring(0, arguments.length() - 1);
            String taskDetailArguments = getTaskDetailArguments(arguments);
            String tagArguments = getTagArguments(arguments);

            return new EditCommand(
                    extractTaskDetailsNatty(taskDetailArguments),
                    getTagsFromArgs(tagArguments),
                    categoryAndIndexPair.getValue(),
                    categoryAndIndexPair.getKey(),
                    args);            
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author A0139052L
    /**
     * Parses the string and returns the categoryIndex and the index if a valid one was given
     * @param args 
     * @return an int array with categoryIndex and index in 0 and 1 index respectively
     */
    private Pair<Integer, Integer> getCategoryAndIndex(String args) {
        
        if (args.trim().equals(EMPTY_STRING)) {
            return null;
        }
        
        // category index should be the first char in the string
        Optional<Integer> checkForCategory = parseIndex(args.substring(0, 1));
        Optional<Integer> index;
        int categoryIndex;
        
        if (checkForCategory.isPresent()){
            index = parseIndex(args);
            // give the default category index if none was provided
            categoryIndex = TaskUtil.getDefaultCategoryIndex();
        } else {
            // index should be the rest of the string if category char is present
            index = parseIndex(args.substring(1));
            categoryIndex = TaskUtil.getCategoryIndex(args.charAt(0));
        }
        
        if (!index.isPresent()){
            return null;
        }
        
        return new Pair<Integer, Integer>(categoryIndex, index.get());
    }
    
    //@@author
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
                    FindCommand.MESSAGE_USAGE));
        }
        
        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}