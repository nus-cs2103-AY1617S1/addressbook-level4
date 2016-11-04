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
    public static final String INDEX_RANGE_SYMBOL = "-";
    public static final String WHITE_SPACE_REGEX_STRING = "\\s+";
    public static final String EMPTY_STRING = "";
    public static final int NOT_FOUND = -1;
    public static final int STRING_START = 0;
    public static final int FILE_EXTENSION_LENGTH = 4;
    
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
         
    //Used for checking for number date formats in arguments
    private static final Pattern LOCAL_DATE_FORMAT =  Pattern.compile("\\d{1,2}[/-]\\d{1,2}[/-]?(\\d{2}|\\d{4})?");
    
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
        	return prepareView(arguments);
        
        case PathCommand.COMMAND_WORD:
            return preparePath(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    //@@author A0135793W
    /**
     * Parses arguments in the context of path command
     * @param argument full command args string
     * @return the prepared command
     */
    private Command preparePath(String argument) {
        String args = argument.trim();
        
        if (args.equals(EMPTY_STRING)) {
            return new IncorrectCommand(String.format(PathCommand.MESSAGE_INVALID_MISSING_FILEPATH, 
                    PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        } else if (args.length() < FILE_EXTENSION_LENGTH) {
            return new IncorrectCommand(String.format(PathCommand.MESSAGE_INVALID_FILEPATH, 
                    PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        } else if (!isValidFileXmlExtension(args)) {
            return new IncorrectCommand(String.format(PathCommand.MESSAGE_INVALID_FILEPATH, 
                    PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        }
        return new PathCommand(args);
    }
    
    /**
     * Checks if input argument has a valid xml file extension
     * @param argument full command args string
     * @return true if argument ends with .xml and false otherwise
     */
    private boolean isValidFileXmlExtension(String argument) {
        //Checking if filename ends with .xml
        Optional<String> fileExtension = getFileExtension(argument.trim());
        if (!fileExtension.isPresent()) {
            return false;
        } else if (!fileExtension.get().equals(".xml")) {
            return false;
        }
        return true;
    }
    
    /**
     * Gets file extension of an argument
     * Assume file extension has .___ format
     * @param argument full command args string
     * @return an optional depending on whether it is a valid file extension
     */
    private Optional<String> getFileExtension(String argument) {
        int length = argument.length();
        String extension = argument.substring(length-FILE_EXTENSION_LENGTH);
        if (extension.charAt(STRING_START) != '.') {
            return Optional.empty();
        }
        return Optional.of(extension);
    }
    //@@author
    
    //@@author A0130853L
    /**
     * Parses arguments in the context of the view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String arguments) {
    	if (arguments.trim().isEmpty()) {
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
                    args);
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
        int detailLastIndex = arguments.indexOf(Tag.TAG_PREFIX);
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
        int tagStartIndex = arguments.indexOf(Tag.TAG_PREFIX);
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
        String nattyDataArguments = convertToNattyDateFormat(dataArguments);
        ArrayList<String> details = new ArrayList<String>();
        int nameEndIndex = nattyDataArguments.length();
        
        String dataArgumentsNameExtracted = extractNameInQuotes(nattyDataArguments, details);
        
        //if list is not empty at this point, it means that name was successfully extracted
        boolean isNameExtracted = !details.isEmpty();
        
        nameEndIndex = extractDateTimeUsingNatty(dataArgumentsNameExtracted, details);
        
        if (!isNameExtracted) {
            details.add(Task.TASK_COMPONENT_INDEX_NAME, 
                    dataArgumentsNameExtracted.substring(STRING_START, nameEndIndex).trim());
        }
        
        String[] returnDetails = new String[details.size()];
        details.toArray(returnDetails);
        return returnDetails;
    }
    
    /**
     * Extracts the name from dataArguments if the name is surrounded by quotes.
     * Puts the extracted name into details
     * 
     * Returns a String with the name removed from dataArguments
     */
    private String extractNameInQuotes(String dataArguments, ArrayList<String> details) {
        String dataArgumentsAfterExtract;
        int quoteEndIndex = dataArguments.lastIndexOf(COMMAND_QUOTE_SYMBOL);
        if (quoteEndIndex != NOT_FOUND) {
            int nameStartIndex = dataArguments.indexOf(COMMAND_QUOTE_SYMBOL);
            if (nameStartIndex == NOT_FOUND) {
                nameStartIndex = STRING_START;
            }

            String nameDetail = dataArguments.substring(nameStartIndex, quoteEndIndex);
            
            //remove name from dataArguments
            dataArgumentsAfterExtract = dataArguments.replace(nameDetail, EMPTY_STRING);
            
            //remove quotes from nameDetail
            nameDetail = nameDetail.replaceAll(COMMAND_QUOTE_SYMBOL, EMPTY_STRING);
            
            details.add(Task.TASK_COMPONENT_INDEX_NAME, nameDetail);
        } else {
            dataArgumentsAfterExtract = dataArguments; //nothing is extracted
        }
        return dataArgumentsAfterExtract;
    }
    
    /**
     * Extracts the date and time using Natty
     * 
     * Returns the index where the task name should end.
     */
    private int extractDateTimeUsingNatty(String dataArguments, ArrayList<String> details) {
        Parser dateTimeParser = new Parser(); 
        List<DateGroup> dateGroups = dateTimeParser.parse(dataArguments);
        int nameEndIndex = dataArguments.length();
        
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
        return nameEndIndex;
    }
    
    //@@author A0139052L
    /**
     * Converts any number formats of date from the local format to one which can be parsed by natty
     * @param arguments
     * @return arguments with converted dates if any
     */
    private String convertToNattyDateFormat(String arguments) {
        String convertedToNattyDateString = arguments;
        String[] splitArgs = arguments.split(WHITE_SPACE_REGEX_STRING);
        
        for (String arg: splitArgs) {
            Matcher matchDate = LOCAL_DATE_FORMAT.matcher(arg);
            
            if (matchDate.matches()) {
                String dateSeparator = getDateSeparator(arg);
                String convertedDate = swapDayAndMonth(arg, dateSeparator);
                convertedToNattyDateString = convertedToNattyDateString.replace(arg, convertedDate);
            }
        }
        
        return convertedToNattyDateString;    
    }
    
    /**
     * Get the separator between day month and year in a date
     * @param localDateString the string representing the date
     * @return the separator character used in localDateString
     */
    private String getDateSeparator(String localDateString) {
        // if 2nd char in string is an integer, then the 3rd char must be the separator
        if (StringUtil.isInteger(localDateString.substring(1,2))) {
            return localDateString.substring(2, 3);
        } else { // else 2nd char is the separator
            return localDateString.substring(1, 2);
        }
    }
    
    /**
     * Swaps the day and month component of the date
     * @param localDate the local date String to convert
     * @param dateSeparator the Separator used in the date string
     * @return the date string with its day and month component swapped
     */
    private String swapDayAndMonth(String localDate, String dateSeparator) {
        String[] splitDate = localDate.split(dateSeparator);
        if (splitDate.length == 3) {
            return splitDate[1] + dateSeparator + splitDate[0] + dateSeparator + splitDate[2];
        } else {
            return splitDate[1] + dateSeparator + splitDate[0];
        }
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

    //@@author A0139930B
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
        final Collection<String> tagStrings = Arrays.asList(tagArguments
                .replaceFirst(Tag.TAG_PREFIX, EMPTY_STRING)
                .split(Tag.TAG_PREFIX));
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
        String[] indexes = dataArgs.split(WHITE_SPACE_REGEX_STRING);                
        ArrayList<Pair<Integer, Integer>> listOfIndexes = getIndexes(indexes);
        
        if (listOfIndexes == null) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Command.MESSAGE_FORMAT + DeleteCommand.MESSAGE_PARAMETER));
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
        String[] indexes = dataArgs.split(WHITE_SPACE_REGEX_STRING);        
        ArrayList<Pair<Integer, Integer>> listOfIndexes = getIndexes(indexes);
        
        if (listOfIndexes == null) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Command.MESSAGE_FORMAT + DoneCommand.MESSAGE_PARAMETER));
        }
        
        return new DoneCommand(listOfIndexes, args);
    }
    //@@author
    
    //@@author A0139052L
    /**
     * 
     * Parses each index string in the array and adds them to a list if valid
     * @param indexes the string array of indexes separated
     * @return a list of all valid indexes parsed or null if an invalid index was given
     */
    private ArrayList<Pair<Integer, Integer>> getIndexes(String[] indexes) {        
        Pair<Integer, Integer> categoryAndIndex;
        ArrayList<Pair<Integer, Integer>> listOfIndexes = new ArrayList<Pair<Integer, Integer>>();       
        for (String index: indexes) {
            if (index.contains(INDEX_RANGE_SYMBOL)) {               
                String[] splitIndex = index.split(INDEX_RANGE_SYMBOL);
                categoryAndIndex = getCategoryAndIndex(splitIndex[0]);
                Optional<Integer> secondIndex = parseIndex(splitIndex[1]);                               
                
                if (!secondIndex.isPresent() || categoryAndIndex == null) {
                    return null;
                }                
                int firstIndex = categoryAndIndex.getValue();               
                int categoryIndex = categoryAndIndex.getKey();
                
                if (firstIndex >= secondIndex.get()) {
                    return null;
                }                
                for (; firstIndex <= secondIndex.get(); firstIndex++) {
                    categoryAndIndex = new Pair<Integer, Integer>(categoryIndex, firstIndex);
                    listOfIndexes.add(categoryAndIndex);
                }                
            } else {
                categoryAndIndex = getCategoryAndIndex(index);               
                if (categoryAndIndex == null) {
                    return null;
                }               
                listOfIndexes.add(categoryAndIndex);
            }
        }
        return listOfIndexes;
    }
    
    //@@author A0135793W
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        String[] splitArgs = args.trim().split(WHITE_SPACE_REGEX_STRING);
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
            String arguments = EMPTY_STRING;
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
    //@@author
    
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
        final String[] keywords = matcher.group("keywords").split(WHITE_SPACE_REGEX_STRING);
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}