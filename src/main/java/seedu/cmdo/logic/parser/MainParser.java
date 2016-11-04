package seedu.cmdo.logic.parser;

import static seedu.cmdo.commons.core.Messages.MESSAGE_BLANK_DETAIL_WARNING;
import static seedu.cmdo.commons.core.Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING;
import static seedu.cmdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.cmdo.commons.core.Messages.MESSAGE_INVALID_PRIORITY;
import static seedu.cmdo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;

import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.commons.util.StringUtil;
import seedu.cmdo.logic.commands.AddCommand;
import seedu.cmdo.logic.commands.BlockCommand;
import seedu.cmdo.logic.commands.ClearCommand;
import seedu.cmdo.logic.commands.Command;
import seedu.cmdo.logic.commands.DeleteCommand;
import seedu.cmdo.logic.commands.DoneCommand;
import seedu.cmdo.logic.commands.EditCommand;
import seedu.cmdo.logic.commands.ExitCommand;
import seedu.cmdo.logic.commands.FindCommand;
import seedu.cmdo.logic.commands.HelpCommand;
import seedu.cmdo.logic.commands.IncorrectCommand;
import seedu.cmdo.logic.commands.ListCommand;
import seedu.cmdo.logic.commands.RedoCommand;
import seedu.cmdo.logic.commands.SelectCommand;
import seedu.cmdo.logic.commands.StorageCommand;
import seedu.cmdo.logic.commands.UndoCommand;
import seedu.cmdo.logic.commands.UpDownCommand;
import seedu.cmdo.model.task.Priority;

/**
 * Parses user input.
 */
public class MainParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    
    private static final Pattern LIST_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+(?:\\s+\\S+)*)(?<arguments>.*)"); 

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    private static final Pattern TASK_LOOSE_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[^ ]+) (.*)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
	
	public static final LocalDate NO_DATE_DEFAULT = LocalDate.MIN;	// All floating tasks are giving this date.
	public static final LocalTime NO_TIME_DEFAULT = LocalTime.MAX;	// All timeless tasks are given this time.
	public static final LocalDateTime NO_DATETIME_DEFAULT = LocalDateTime.of(LocalDate.MIN, LocalTime.MAX);
	public static final String EMPTY_STRING = "";
	
	private static LocalDateTime dt = NO_DATETIME_DEFAULT;
	private static LocalDateTime dtStart = NO_DATETIME_DEFAULT;
	private static LocalDateTime dtEnd = NO_DATETIME_DEFAULT;
	private static ArrayList<LocalDateTime> datesAndTimes;
	private static String args;
	private static String detailToAdd;
	private static Integer dateTimeType;
	private static String[] splittedArgs;
	private static Boolean floatingRequestInEdit = false;
	private static Boolean priorityRequestInEdit = false;
	private static Boolean onlyOneDateOrTimeRequestInEdit = false;
	private static Integer targetIndex;
	
	// Singleton
	private static MainParser mainParser;
	private static final Parser parser = new Parser();
	
    /**
     * Private constructor
     */
	private MainParser() {
    	init();
    }
    
    public static MainParser getInstance() {
    	if (mainParser == null) {
    		mainParser = new MainParser();
    	} return mainParser;
    }
    
    //@@author A0139661Y
    /**
     * Initialize main parser.
     */
    private void init() {
    	datesAndTimes = new ArrayList<LocalDateTime>();
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
    	reset();
    	String[] splitedInput = userInput.split("\\s+");
    	String commandWord, arguments; 
    	if(splitedInput.length == 2 && 
    			((splitedInput[1].equals("done")) || (splitedInput[1].equals("all")) || splitedInput[1].equals("block"))) {
    		Matcher matcher = LIST_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            commandWord = matcher.group("commandWord");
            arguments = matcher.group("arguments");
    	}
    	else{
    		Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            commandWord = matcher.group("commandWord");
            arguments = matcher.group("arguments");
            
    	}
    	args = getCleanString(arguments);
        args = convertToTo(args); 
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd();
       
        case BlockCommand.COMMAND_WORD:
            return prepareBlock();

        case SelectCommand.COMMAND_WORD:
            return prepareSelect();

        case StorageCommand.COMMAND_WORD:
        	return prepareStorage();
            
        case DeleteCommand.COMMAND_WORD:
            return prepareDelete();
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
            
        case EditCommand.COMMAND_WORD:
        	return prepareEdit();

        case FindCommand.COMMAND_WORD:
            return prepareFind();
            
        case ListCommand.COMMAND_WORD_DONE:	
        case ListCommand.COMMAND_WORD_SHORT_DONE:
            return prepareList("--done");
        case ListCommand.COMMAND_WORD_BLOCK:
        case ListCommand.COMMAND_WORD_SHORT_BLOCK:
        	return prepareList("--block");
        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_ALL:        	
        case ListCommand.COMMAND_WORD_SHORT_ALL:
        	return prepareList(args);
        	
        case UpDownCommand.UP:
        case UpDownCommand.UP_ALT:
        	return new UpDownCommand(UpDownCommand.UP);
        case UpDownCommand.DOWN:
        case UpDownCommand.DOWN_ALT:
        	return new UpDownCommand(UpDownCommand.DOWN);
        case UpDownCommand.TOP:
        case UpDownCommand.TOP_ALT:
        	return new UpDownCommand(UpDownCommand.TOP);
        case UpDownCommand.BOTTOM:
        case UpDownCommand.BOTTOM_ALT:
        	return new UpDownCommand(UpDownCommand.BOTTOM);
        	
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /** PREPARATION METHODS **/
    //@@author A0139661Y
    private Command prepareAdd(){
    	datesAndTimes.clear();
    	try {
        	process();
    		if (dateTimeType <= 1) {
    			return new AddCommand(
    					detailToAdd,
    					dt.toLocalDate(),
    					dt.toLocalTime(),
    					extractPriority(),
    					getTagsFromArgs());
    		} else {
    			return new AddCommand(
    					detailToAdd,
    					dtStart.toLocalDate(),
    					dtStart.toLocalTime(),
    					dtEnd.toLocalDate(),
    					dtEnd.toLocalTime(),
    					extractPriority(),
    					getTagsFromArgs());
    		}
    	} catch (IllegalValueException ive) {
    		return new IncorrectCommand(ive.getMessage());
    	}
    }
    
    //@@author A0141128R
    private Command prepareBlock(){
    	try {
    		process();
    		overrideDueByDateAndTimeForBlock();
    		return new BlockCommand(
    			detailToAdd,
    			dtStart.toLocalDate(),
    			dtStart.toLocalTime(),
    			dtEnd.toLocalDate(),
    			dtEnd.toLocalTime(),
    			"",
    			getTagsFromArgs());
    	} catch (IllegalValueException ive) {
    		return new IncorrectCommand(ive.getMessage());
    	}
    }
    
     //@@author A0141128R
     private Command prepareDelete() {
         if (!isIndexInCommandPresent()) {
             return new IncorrectCommand(
                     String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
         }
         return new DeleteCommand(targetIndex);
     }

     //@@author A0141128R
     private Command prepareDone() {
     	if (!isIndexInCommandPresent()) {
             return new IncorrectCommand(
                     String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
         }
         return new DoneCommand(targetIndex);
     }
    
    //@@author A0141128R
    private Command prepareEdit(){
    	try {
    		if (!isLooseIndexInCommandPresent()) {
    			return new IncorrectCommand(
    	                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    		}    	
        args = args.replaceFirst("[0-9]+\\s", "");
        //If details is not empty, extract details
        if(detailToAdd == null || !detailToAdd.equals("")) {
        	extractDetailForEdit();
        }
    	splittedArgs = getCleanString(args).split(" ");
        // Parse date and time
        extractDueByDateAndTime();
        saveDueByDateAndTime();
        checkSpecialRequestInEdit();
    	//need to change constructor of edit
		if (dateTimeType <= 1) {
			return new EditCommand(
					priorityRequestInEdit,
					floatingRequestInEdit,
					onlyOneDateOrTimeRequestInEdit,
					targetIndex,
					detailToAdd,
					dt.toLocalDate(),
					dt.toLocalTime(),
					extractPriority(),
					getTagsFromArgs());
		} 
		else{ 
			//only use this constructor when time range is keyed in
			assert(dateTimeType!=0);
			return new EditCommand(
					priorityRequestInEdit,
					targetIndex,
					detailToAdd,
					dtStart.toLocalDate(),
					dtStart.toLocalTime(),
					dtEnd.toLocalDate(),
					dtEnd.toLocalTime(),
					extractPriority(),
					getTagsFromArgs());
    		}
    	} catch (IllegalValueException ive) {
    		return new IncorrectCommand(ive.getMessage());
    	}
    }

    //@@author A0141128R
    private Command prepareFind() {
        boolean taskStatus = false; // we assume the user is searching for undone tasks
    	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        if (keywordSet.contains("--done")) {
        	taskStatus = true;
        	keywordSet.remove("--done");
        }
        return new FindCommand(keywordSet, taskStatus);
    }

    private Command prepareSelect() {
    	if(!isIndexInCommandPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
        return new SelectCommand(targetIndex);
    }

    //@@author A0141006B
    private Command prepareStorage() {
    	if (args.equals("")) {
    		return new StorageCommand("data/cmdo.xml");
    	}
    	if (args.lastIndexOf("/cmdo.xml") == -1) {
    		args = new StringBuilder(args + "/cmdo.xml").toString();
    	}
    	return new StorageCommand(args);
    }
     
    //@@author A0139661Y
    private Command prepareList(String args) {
        int type = 0; // we assume the user is searching for undone tasks
        if (args.contains("--done")) {
        	type = 1;
        } else if (args.contains("--block")) {
        	type = 2;
        }
        return new ListCommand(type);
    }
    
//  ============== HELPER METHODS
    //@@author A0139661Y
    /**
     * Processes parameters detail, dbt, dbd, priority.
     * 
     * @throws IllegalValueException if any are invalid.
     */
    private void process() throws IllegalValueException {
    	extractDetail();			// Saves to detailToAdd
    	extractDueByDateAndTime(); 	// Saves to datesAndTimes
    	checkPriorityValidity(); 	// Throws exception if priority entered wrongly
    	splittedArgs = getCleanString(args).split(" ");
    	saveDueByDateAndTime(); 	// Saves to dt family.
    	reset();         			// Clear dates and times		
	}

    //@@author A0138471A
    /**
     * Extracts the detail embedded in user input ' '.
     * 
     * @throws IllegalValueException if only one ' found, or if detail is blank.
     */
    private void extractDetail() throws IllegalValueException {
    	checkValidDetailInput();
    	// Split into '  ...  '
    	String[] details = args.split("^ '(.+)'$");
    	// Details only, get rid of anything after the '
    	String output = new StringBuilder(details[0]).replace(details[0].lastIndexOf("'"), 
    													details[0].length(), 
    													"").toString();
        // Details only, get rid of anything before the ' 
        for(char o : output.toCharArray()) { 
          if(o =='\''){ 
            break; 
          } 
          else 
            output=output.replaceFirst(".", ""); 
        } 
    	// Get rid of the first '
    	output = output.replaceFirst("'","");
    	// Save to instance
    	detailToAdd = output;
        // return args without details 
        args = args.replace(output, ""); 
    }

    //@@author A0138471A
    /**
     * Extracts the detail embedded in user input ' ' for edit purposes.
     * ie details are optional, and if they are input, should not be empty.
     * 
     * @throws IllegalValueException if detail is blank.
     */
    private void extractDetailForEdit() throws IllegalValueException {
    	if (!checkValidDetailInputForEdit()) {
    		detailToAdd = "";
    		return;
    	}
    	// Split into '  ...  '
    	String[] details = args.split("^ '(.+)'$");
    	// Details only, get rid of anything after the '
    	String output = new StringBuilder(details[0]).replace(details[0].lastIndexOf("'"), 
    													details[0].length(), 
    													"").toString();
        // Details only, get rid of anything before the ' 
        for(char o : output.toCharArray()){ 
          if(o =='\''){ 
            break; 
          } 
          else 
            output=output.replaceFirst(".", ""); 
        } 
    	// Get rid of the first '
    	output = output.replaceFirst("'","");
    	// Save to instance
    	detailToAdd = output;
    	// return rear end
    	args = new StringBuilder(details[0]).substring(details[0].lastIndexOf("'")+1).toString();
        // return args without details 
        args = args.replace(output, ""); 
    }
	
    //@@author A0139661Y
    /**
     * Extracts the priority out of the args.
     * If / precedes neither high, medium or low, it will throw an error
     * Otherwise, it is taken to have default no priority.
     * 
     * @param splittedArgs an array of split user input
     * @return priority level string.
     */ 
    private String extractPriority() throws IllegalValueException {
    	List<String> rawArgs = Arrays.asList(splittedArgs);
    	for (String rawArg : rawArgs) {
    		if (rawArg.toLowerCase().startsWith("/")) {
    			switch(rawArg.replace("/", "")) {
    			case Priority.HIGH:
    				return Priority.HIGH;
    			case Priority.MEDIUM:
    				return Priority.MEDIUM;
    			case Priority.LOW:
    				return Priority.LOW;
    			default:
    				throw new IllegalValueException(MESSAGE_INVALID_PRIORITY);
    			}
    		}
    	}
    	return "";
    }
    
    //@@author A0139661Y
    /** 
     * Takes out the date and time of the natural language input
     */
    public void extractDueByDateAndTime() {
    	List<DateGroup> groups = parser.parse(args);
    	String cleanArgs = args;
    	
    	try {
    		// This retrieves either the start date/time, or the only date/time.
    		for (int i=0; i<groups.size(); i++) {
	    		DateGroup group = groups.get(i);
	    		List<Date> dateList = group.getDates(); 	// Extract date
	    		Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
	    		if ((!parseMap.containsKey("explicit_time") && parseMap.containsKey("relative_date")) || 
	    				(!parseMap.containsKey("explicit_time") && parseMap.containsKey("formal_date"))) {
	    			for (Date date : dateList) {
	    				LocalDateTime temp = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	    				datesAndTimes.add(LocalDateTime.of(temp.toLocalDate(), LocalTime.MAX));
	    			}
	    		} else {
	    			for (Date date : dateList) {
	    				datesAndTimes.add(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
	    			}
	    		}
	    		for (ParseLocation parsedWord : parseMap.get("parse")) {
	    			cleanArgs = cleanArgs.substring(0, parsedWord.getStart() - 1) + cleanArgs.substring(parsedWord.getEnd() -1);
	    		}
    		}
    		// Sort dates and times according to whichever is earlier
    		Collections.sort(datesAndTimes);
    		args = cleanArgs;	// Return a cleaned up string
    	} catch (IndexOutOfBoundsException e) {} // No date/time found. Do nothing
    }
    
    //@@author A0139661Y
    /**
     * Checks if the detail input is valid, as in, it requires the use of encapsulating ' ',
     * and must not be blank. This is used in conjunction with {@link #extractDetail()}.
     * 
     * @throws IllegalValueException if only one ' found, or if detail is blank.
     */
	private void checkValidDetailInput() throws IllegalValueException {
    	// Check if only one ' used
    	if (args.lastIndexOf("'") == args.indexOf("'"))
    		throw new IllegalValueException(MESSAGE_ENCAPSULATE_DETAIL_WARNING);
    	// Check if detail is empty.
    	if (args.lastIndexOf("'") == args.indexOf("'")+1)
    		throw new IllegalValueException(MESSAGE_BLANK_DETAIL_WARNING);
	}
	
    //@@author A0139661Y
	/**
     * Checks if the detail input is valid, as in, it must not be blank. 
     * This is used in conjunction with {@link #extractDetailForEdit()}.
     * 
     * @throws IllegalValueException if only one ' found, or if detail is blank.
     */
	private boolean checkValidDetailInputForEdit() throws IllegalValueException {
    	// Check if only one ' used
    	if (args.lastIndexOf("'") == args.indexOf("'"))
    		return false;
    	// Check if detail is empty.
    	if (args.lastIndexOf("'") == args.indexOf("'")+1)
    		throw new IllegalValueException(MESSAGE_BLANK_DETAIL_WARNING);
    	return true;
	}

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     * 
     * @throws IllegalValueException if tag input is invalid.
     */
    private static Set<String> getTagsFromArgs() throws IllegalValueException {
    	List<String> rawArgs = Arrays.asList(splittedArgs);
    	Collection<String> tagStrings = new ArrayList<String>();
    	boolean isEmpty = true;
    	for (String rawArg : rawArgs) {
    		isEmpty = false;
    		if (rawArg.startsWith("-")) {
    			tagStrings.add(rawArg.replace("-", ""));
    		}
    	}
    	if (isEmpty) {
    		return Collections.emptySet();
    	} 
    	return new HashSet<>(tagStrings);
    }
    
    //@@author A0139661Y
    /**
     * Utility method which replaces all redundant spaces
     * 
     * @param args an uncleaan string
     * @return a cleaned up string
     */
    private String getCleanString(String args) {
    	return args.trim().replaceAll("\\s+", " ");
    }
    
    //@@author A0138471A 
    private String convertToTo(String args) { 
    	return args.replaceAll(" - ", " to "); 
    } 
    
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     * The index here does not precede anything.  
	 *
     * @return {@code Optional.empty()} otherwise.
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
    
    //@@author A0139661Y
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     * An index is loose if there are arguments after the index.  
     *   
     * @return {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseLooseIndex(String command) {
        final Matcher matcher = TASK_LOOSE_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));
    } 

	//@@author A0139661Y
    /**
	 * Checks for accidental '/' instead of ' /'.
	 * 
	 * @throws IllegalValueException
	 */
    private void checkPriorityValidity() throws IllegalValueException {
    	if (args.contains("/") && !args.contains(" /"))
    		throw new IllegalValueException(Messages.MESSAGE_INVALID_PRIORITY_SPACE);
	}

    //@@author A0139661Y
    /**
     * Checks if the user wants to edit time or priority.
     * Used in conjunction with {@link #prepareEdit()}
     */
    private void checkSpecialRequestInEdit() {
        //if keyword float is entered, it becomes a floating task (no date no time)
        if(args.toLowerCase().contains("floating") || args.toLowerCase().contains("fl")){
        	floatingRequestInEdit = true;
        }
        //if keyword is only, we take it that the user means to have a single time or date.
        else if (args.toLowerCase().contains("only")) {
        	onlyOneDateOrTimeRequestInEdit = true;
        }
        //if keyword rp or remove priority is entered, priority is removed
        if(args.toLowerCase().contains("no priority") || args.toLowerCase().contains("np")) {
        	priorityRequestInEdit = true;
        }
        
    }
    
    //@@author A0139661Y
    /**
     * Tests to see if command ends in an index.
     * 
     * @return boolean indicative of where index is present
     */
    private boolean isIndexInCommandPresent() {
    	Optional<Integer> checkForIndex = parseIndex(args);
    	if (!checkForIndex.isPresent()) 
    		return false;
    	targetIndex = checkForIndex.get();
    	return true;
    }

    //@@author A0139661Y
    /**
     * Tests to see if command ends in a loose index.
     * A loose index is an index with proceeding strings.
     * 
     * @return boolean indicative of where loose index is present
     */    
    private boolean isLooseIndexInCommandPresent() {
    	Optional<Integer> checkForIndex = parseLooseIndex(args);
    	if (!checkForIndex.isPresent()) 
    		return false;
    	targetIndex = checkForIndex.get();
    	return true;
    }
    
	//@@author A0139661Y
    /**
	 * Saves the date and time from list to dt family according to the following table
	 * 
	 * ______________________
	 * | type 	| condition	|
	 * ----------------------
	 * | 0		| no DT		|
	 * | 1		| single DT	|
	 * | 2		| two DT	|
	 * ======================
	 */
    private void saveDueByDateAndTime() {
    	if (datesAndTimes.size() == 1) {
    		dt = datesAndTimes.get(0);
    		dateTimeType = 1;
    	} else if (datesAndTimes.size() == 2) {
    		dtStart = datesAndTimes.get(0);
    		dtEnd = datesAndTimes.get(1);
    		dateTimeType = 2;
    	} else {
    		dt = LocalDateTime.of(NO_DATE_DEFAULT, NO_TIME_DEFAULT);
    		dateTimeType = 0;
    	}
	}
    
    
    //@@author A0139661Y
    /**
     * Overrides the DBD and DBT when editing or adding a block task.
     * This is to comply with the constraints specified for blocks.
     * 
     * @throws IllegalValueException
     */
    private void overrideDueByDateAndTimeForBlock() throws IllegalValueException {
		// Override saveDueByDateAndTime()
    	if (dateTimeType == 0) {
    		throw new IllegalValueException("Specify a time/date range for the block.");
    	}
    	// Only one time or date or both
    	if (dateTimeType == 1) {
    		// Case 1: Date only
    		if (dt.toLocalTime().equals(NO_TIME_DEFAULT)) {
    			dtStart = dt;
    			dtEnd = dt;
    		} else {
        		// Case 2: Time only or date and time only
    			dtStart = dt;
    			dtEnd = dt.plusHours(1);
    		}
			dt = NO_DATETIME_DEFAULT;
    	}
    }

    //@@author A0139661Y
	private void reset() {
        datesAndTimes.clear();
        args = EMPTY_STRING;
        floatingRequestInEdit = false;
        priorityRequestInEdit = false;
	}

}