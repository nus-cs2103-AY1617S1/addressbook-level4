package seedu.taskmanager.logic.parser;

import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.DeleteCommand;
import seedu.taskmanager.logic.commands.EditCommand;
import seedu.taskmanager.logic.commands.ExitCommand;
import seedu.taskmanager.logic.commands.FindCommand;
import seedu.taskmanager.logic.commands.HelpCommand;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.ListCommand;
import seedu.taskmanager.logic.commands.ListDeadlineCommand;
import seedu.taskmanager.logic.commands.ListEventCommand;
import seedu.taskmanager.logic.commands.ListNotDoneCommand;
import seedu.taskmanager.logic.commands.ListTaskCommand;
import seedu.taskmanager.logic.commands.SelectCommand;
import seedu.taskmanager.logic.commands.DoneCommand;
import seedu.taskmanager.logic.commands.NotDoneCommand;
import seedu.taskmanager.logic.commands.SaveCommand;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.logic.commands.RedoCommand;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.logic.parser.ArgumentTokenizer.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;

/**
 * Parses user input.
 */
public class Parser {
    public static final String MESSAGE_DATETIME_PARSE_FAILURE = "Invalid datetime.";
    
    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[0-9]+)"); // single number of index delete\s+([0-9]+)
    private static final Pattern ITEM_INDEXES_ARGS_FORMAT = Pattern.compile("(?<targetIndexes>([0-9]+)\\s*([0-9]+\\s*)+)"); // variable number of indexes

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    //@@author A0140060A
    /*
     * Used to separate parameters from their delimiters
     */
    private static final Prefix namePrefix = new Prefix("n/");
    private static final Prefix startDatePrefix = new Prefix("sd/");
    private static final Prefix startTimePrefix = new Prefix("st/");
    private static final Prefix startDateTimePrefix = new Prefix("sdt/");
    private static final Prefix endDatePrefix = new Prefix("ed/");
    private static final Prefix endTimePrefix = new Prefix("et/");
    private static final Prefix endDateTimePrefix = new Prefix("edt/");
    private static final Prefix tagPrefix = new Prefix("#");
    private static final String removeTagPrefixString = "-";
    
    private static final int PARSEDATETIME_ARRAY_DATE_INDEX = 0;
    private static final int PARSEDATETIME_ARRAY_TIME_INDEX = 1;
    
    //@@author A0140060A-unused
    //Used in earlier version, functionality replaced by ArgumentTokenizer
    private static final Pattern NAME_ARG_FORMAT = Pattern.compile("(n/(?<name>[^/#]+))");
    private static final Pattern START_DATE_ARG_FORMAT = Pattern.compile("(sd/(?<startDate>[^/#]+))");    
    private static final Pattern START_TIME_ARG_FORMAT = Pattern.compile("(st/(?<startTime>[^/#]+))");
    private static final Pattern START_DATETIME_ARG_FORMAT = Pattern.compile("sdt/(?<startDateTime>[^/#]+)");
    private static final Pattern END_DATE_ARG_FORMAT = Pattern.compile("(ed/(?<endDate>[^/#]+))");
    private static final Pattern END_TIME_ARG_FORMAT = Pattern.compile("(et/(?<endTime>[^/#]+))");
    private static final Pattern END_DATETIME_ARG_FORMAT = Pattern.compile("edt/(?<endDateTime>[^/#]+)");
    
    //@@author
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(T|t)((A|a)(S|s)(K|k))?\\s*"
                    + "(n/)?(?<name>[^/]+)"
                    + "(?<tagArguments>(?: #[^/#]+)*)"); // variable number of tags

    private static final Pattern DEADLINE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(D|d)((E|e)(A|a)(D|d)(L|l)(I|i)(N|n)(E|e))?\\s*"
                    + "(n/)?(?<name>[^/]+)"
                    + END_DATE_ARG_FORMAT
                    + END_TIME_ARG_FORMAT + "?"
                    + "(?<tagArguments>(?: #[^/#]+)*)"); // variable number of tags

    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(E|e)((V|v)(E|e)(N|n)(T|t))?\\s*"
                    + "(n/)?(?<name>[^/]+)"
            		+ START_DATE_ARG_FORMAT
                    + START_TIME_ARG_FORMAT + "?"
                    + END_DATE_ARG_FORMAT
                    + END_TIME_ARG_FORMAT + "?"
                    + "(?<tagArguments>(?: #[^/#]+)*)"); // variable number of tags
    
    private static final Pattern DEADLINE_NLP_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(D|d)((E|e)(A|a)(D|d)(L|l)(I|i)(N|n)(E|e))?\\s*"
                    + "(n/)?(?<name>[^/]+)"
                    + END_DATETIME_ARG_FORMAT
                    + "(?<tagArguments>(?: #[^/#]+)*)"); // variable number of tags

    private static final Pattern EVENT_NLP_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(E|e)((V|v)(E|e)(N|n)(T|t))?\\s*"
                    + "(n/)?(?<name>[^/]+)"
            		+ START_DATETIME_ARG_FORMAT
                    + END_DATETIME_ARG_FORMAT
                    + "(?<tagArguments>(?: #[^/#]+)*)"); // variable number of tags
    
    private static final Pattern EDIT_COMMAND_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[\\d]+)" 
                                                                            + "(?<editCommandArguments>.+)");

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
        case AddCommand.SHORT_COMMAND_WORD:
            return prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.SHORT_COMMAND_WORD:
            return prepareEdit(arguments);
            
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.SHORT_COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.SHORT_COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.SHORT_COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:            
        case FindCommand.SHORT_COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.SHORT_COMMAND_WORD:
            return new ListCommand();
            
        case ListNotDoneCommand.COMMAND_WORD:
        case ListNotDoneCommand.SHORT_COMMAND_WORD:
            return new ListNotDoneCommand();
            
        case ListTaskCommand.COMMAND_WORD:
        case ListTaskCommand.SHORT_COMMAND_WORD:
        	return new ListTaskCommand();
        	
        case ListDeadlineCommand.COMMAND_WORD:
        case ListDeadlineCommand.SHORT_COMMAND_WORD:
        	return new ListDeadlineCommand();
        	
        case ListEventCommand.COMMAND_WORD:
        case ListEventCommand.SHORT_COMMAND_WORD:
        	return new ListEventCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.SHORT_COMMAND_WORD:
            return new HelpCommand();
            
        case DoneCommand.COMMAND_WORD:
        case DoneCommand.SHORT_COMMAND_WORD:
        case DoneCommand.ALTERNATE_SHORT_COMMAND_WORD:
            return prepareDone(arguments);

        case NotDoneCommand.COMMAND_WORD:
        case NotDoneCommand.SHORT_COMMAND_WORD:
            return prepareNotDone(arguments);
        
        case RedoCommand.COMMAND_WORD:
        case RedoCommand.SHORT_COMMAND_WORD:
            return new RedoCommand();
            
        case UndoCommand.COMMAND_WORD:
        case UndoCommand.SHORT_COMMAND_WORD:
        	return new UndoCommand();
        	
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add item command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher taskMatcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher deadlineMatcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher eventMatcher = EVENT_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher deadlineNlpMatcher = DEADLINE_NLP_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher eventNlpMatcher = EVENT_NLP_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!taskMatcher.matches() && !deadlineMatcher.matches() && !eventMatcher.matches() && 
                !deadlineNlpMatcher.matches() && !eventNlpMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            if (taskMatcher.matches()) {
                return new AddCommand(
                    ItemType.TASK_WORD,
                    taskMatcher.group("name"),
                    getTagsFromArgs(taskMatcher.group("tagArguments"))
                );
            } else if (deadlineMatcher.matches()) {
                return addDeadline(deadlineMatcher);
            } else if (eventMatcher.matches()) {
                return addEvent(eventMatcher);
            } else if (deadlineNlpMatcher.matches()) {
                return addNlpDeadline(deadlineNlpMatcher);
            } else if (eventNlpMatcher.matches()) {
                return addNlpEvent(eventNlpMatcher);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private Command addNlpEvent(final Matcher eventNlpMatcher) throws IllegalValueException {
        String endDateTime = eventNlpMatcher.group("endDateTime");
        String startDateTime = eventNlpMatcher.group("startDateTime");
        List<Date> startDateTimes = new PrettyTimeParser().parse(startDateTime); 
        List<Date> endDateTimes = new PrettyTimeParser().parse(endDateTime);
        // Just Take First Value for Start and End
        if (startDateTimes.isEmpty() || endDateTimes.isEmpty()) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DATETIME_PARSE_FAILURE));
        }
        Date processedStartDateTime = startDateTimes.get(0);
        Date processedEndDateTime = endDateTimes.get(0);
        if (processedEndDateTime.before(processedStartDateTime)) {
	        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Command.MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME));
	    }
        SimpleDateFormat dateFormat = new SimpleDateFormat(ItemDate.DATE_FORMAT);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String startDate = dateFormat.format(processedStartDateTime);
        String startTime = timeFormat.format(processedStartDateTime);
        String endDate = dateFormat.format(processedEndDateTime);
        String endTime = timeFormat.format(processedEndDateTime);
    	return new AddCommand(ItemType.EVENT_WORD,
                eventNlpMatcher.group("name"), 
                startDate, 
                startTime, 
                endDate, 
                endTime, 
                getTagsFromArgs(eventNlpMatcher.group("tagArguments")));
    }
    
    private Command addNlpDeadline(final Matcher deadlineNlpMatcher) throws IllegalValueException {
    	String endDateTime = deadlineNlpMatcher.group("endDateTime");
        List<Date> endDateTimes = new PrettyTimeParser().parse(endDateTime);
        // Just Take First Value for Start and End
        if (endDateTimes.isEmpty()) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DATETIME_PARSE_FAILURE));
        }
        for (int i=0; i<endDateTimes.size(); ++i) {
            System.out.println(endDateTimes.get(i));
        }
        Date processedEndDateTime = endDateTimes.get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat(ItemDate.DATE_FORMAT);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String endDate = dateFormat.format(processedEndDateTime);
        String endTime = timeFormat.format(processedEndDateTime);
    	return new AddCommand(ItemType.DEADLINE_WORD,
                deadlineNlpMatcher.group("name"),
                endDate, 
                endTime, 
                getTagsFromArgs(deadlineNlpMatcher.group("tagArguments")));
    }

	private Command addEvent(final Matcher eventMatcher) throws IllegalValueException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		SimpleDateFormat df = new SimpleDateFormat(ItemDate.DATE_FORMAT);
		String endTime = eventMatcher.group("endTime");
		String startTime = eventMatcher.group("startTime");
		try {	
		    df.setLenient(false);
		    // If yyyy-MM-dd
		    String startDateString;
		    String endDateString;
		    String[] parts = eventMatcher.group("endDate").split("-");
		    if (parts.length == 3) {
		        endDateString = eventMatcher.group("endDate");
		        df.parse(eventMatcher.group("endDate"));
		    } else { // MM-dd
		        LocalDateTime ldt = LocalDateTime.now();
		        endDateString = ldt.getYear() + "-" + eventMatcher.group("endDate");
		        df.parse(endDateString);
		    }
		    String[] parts2 = eventMatcher.group("startDate").split("-");
		    // If yyyy-MM-dd
		    if (parts2.length == 3) {
		        startDateString = eventMatcher.group("startDate");
		        df.parse(eventMatcher.group("startDate"));
		    } else { // MM-dd
		        LocalDateTime ldt = LocalDateTime.now();
		        startDateString = ldt.getYear() + "-" + eventMatcher.group("startDate");
		        df.parse(startDateString);
		    }
		    if (endTime == null) {
		        endTime = AddCommand.DEFAULT_END_TIME;
		    }
		    if (startTime == null) {
		        startTime = AddCommand.DEFAULT_START_TIME;
		    }
		    if (sdf.parse(endDateString + " " + endTime).before(sdf.parse(startDateString + " " + startTime))) {
		        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Command.MESSAGE_END_DATE_TIME_BEFORE_START_DATE_TIME));
		    }
		} catch (ParseException e) {
		    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDate.MESSAGE_DATE_CONSTRAINTS));
		}
		return new AddCommand(ItemType.EVENT_WORD, 
		                      eventMatcher.group("name"), 
		                      eventMatcher.group("startDate"), 
		                      startTime, 
		                      eventMatcher.group("endDate"), 
		                      endTime, 
		                      getTagsFromArgs(eventMatcher.group("tagArguments")));
	}

	private Command addDeadline(final Matcher deadlineMatcher) throws IllegalValueException {
		try {
		    SimpleDateFormat df = new SimpleDateFormat(ItemDate.DATE_FORMAT);
		    SimpleDateFormat df2 = new SimpleDateFormat(ItemDate.ALTERNATE_DATE_FORMAT);
		    df.setLenient(false);
		    String[] parts = deadlineMatcher.group("endDate").split("-");
		    // If yyyy-MM-dd
		    if (parts.length == 3) {
		        df.parse(deadlineMatcher.group("endDate"));
		    } else { // MM-dd
		        df2.parse(deadlineMatcher.group("endDate"));
		    }
		} catch (ParseException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDate.MESSAGE_DATE_CONSTRAINTS));
		}
		return new AddCommand(ItemType.DEADLINE_WORD,
		                      deadlineMatcher.group("name"), 
		                      deadlineMatcher.group("endDate"),
		                      deadlineMatcher.group("endTime"),
		                      getTagsFromArgs(deadlineMatcher.group("tagArguments")));
	}

    //@@author A0140060A	
    /**
     * Parses arguments in the context of the edit item command.
     *
     * @param args full command args string
     * @return the prepared EditCommand
     */
    private Command prepareEdit(String args) {
        assert args != null;
        final Matcher matcher = EDIT_COMMAND_ARGS_FORMAT.matcher(args.trim());
        
        if (matcher.matches()) {
            Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
            if (index.isPresent()) {
                String editCommandArgs = matcher.group("editCommandArguments");
                ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix, startDatePrefix, startTimePrefix, 
                                                                        startDateTimePrefix, endDatePrefix, endTimePrefix,
                                                                        endDateTimePrefix, tagPrefix);
                logger.fine("In prepareEdit, before tokenize");
                argsTokenizer.tokenize(editCommandArgs);
                
                //Capture argument values into their respective variables if available
                String name = getParsedArgumentFromArgumentTokenizer(argsTokenizer, namePrefix);
                String startDate = getParsedArgumentFromArgumentTokenizer(argsTokenizer, startDatePrefix);
                String startTime = getParsedArgumentFromArgumentTokenizer(argsTokenizer, startTimePrefix);
                String endDate = getParsedArgumentFromArgumentTokenizer(argsTokenizer, endDatePrefix);
                String endTime = getParsedArgumentFromArgumentTokenizer(argsTokenizer, endTimePrefix);
                String startDateTime = getParsedArgumentFromArgumentTokenizer(argsTokenizer, startDateTimePrefix);
                String endDateTime = getParsedArgumentFromArgumentTokenizer(argsTokenizer, endDateTimePrefix);
                List<String> tagsToAdd = getParsedTagsToAddFromArgumentTokenizer(argsTokenizer, tagPrefix);
                List<String> tagsToRemove = getParsedTagsToRemoveFromArgumentTokenizer(argsTokenizer, tagPrefix);
                
                try {
                    //Handle case where user enters start date and time using natural language via sdt/
                    if (startDateTime != null) {
                        String[] startDateTimeArr = parseDateTime(startDateTime, ItemDate.DATE_FORMAT, ItemTime.TIME_FORMAT);
                        startDate = startDateTimeArr[PARSEDATETIME_ARRAY_DATE_INDEX];
                        startTime = startDateTimeArr[PARSEDATETIME_ARRAY_TIME_INDEX];
                    }
                    
                    //Handle case where user enters end date and time using natural language via edt/
                    if (endDateTime != null) {
                        String[] endDateTimeArr = parseDateTime(endDateTime, ItemDate.DATE_FORMAT, ItemTime.TIME_FORMAT);
                        endDate = endDateTimeArr[PARSEDATETIME_ARRAY_DATE_INDEX];
                        endTime = endDateTimeArr[PARSEDATETIME_ARRAY_TIME_INDEX];
                    }
                    
                    //Ensure EditCommand is created with at least one non-null parameter other than targetIndex
                    if (name != null || startDate != null || startTime!= null || endDate != null 
                        || endTime != null || tagsToAdd != null || tagsToRemove != null) {
                        return new EditCommand(index.get(), name, startDate, startTime, endDate, endTime, tagsToAdd, tagsToRemove);
                    }
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }   
            }
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    /**
     * @param argsTokenizer
     * @param argumentPrefix
     * @return parsed argument value
     */
    private String getParsedArgumentFromArgumentTokenizer(ArgumentTokenizer argsTokenizer, Prefix argumentPrefix) {
        try {
            return argsTokenizer.getValue(argumentPrefix).get();
        } catch (NoSuchElementException nsee) { 
            return null;
        } 
    }

    /**
     * @param argsTokenizer
     * @param tagPrefix
     * @return list of parsed tags
     */
    private List<String> getParsedTagsToRemoveFromArgumentTokenizer(ArgumentTokenizer argsTokenizer, Prefix tagPrefix) {
        try {
            List<String> tags = argsTokenizer.getAllValues(tagPrefix).orElse(null);
            
            if (tags == null) {
                return null;
            }
            
            logger.fine("Before remove tags check");
            
            List<String> tagsToRemove = new ArrayList<String>();
            for (String tag : tags) {
                if (tag.length() > 0 && isATagToBeRemoved(tag)) {                            
                    tagsToRemove.add(processTagToBeRemoved(tag));
                }
            }
            
            return tagsToRemove;
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }
    
    /**
     * @param argsTokenizer
     * @param tagPrefix
     * @return list of parsed tags
     */
    private List<String> getParsedTagsToAddFromArgumentTokenizer(ArgumentTokenizer argsTokenizer, Prefix tagPrefix) {
        try {
            List<String> tags = argsTokenizer.getAllValues(tagPrefix).orElse(null);
            
            if (tags == null) {
                return null;
            }
            
            List<String> tagsToAdd = new ArrayList<String>();
            for (String tag : tags) {
                if (tag.length() > 0 && !isATagToBeRemoved(tag)) {                            
                    tagsToAdd.add(tag);
                }
            }
            
            return tagsToAdd;
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }
    
    /**
     * @param tag
     * @return tag without tag removal prefix
     */
    private String processTagToBeRemoved(String tag) {
        assert isATagToBeRemoved(tag);
        logger.fine("In processTagToBeRemoved, before return");
        return tag.substring(removeTagPrefixString.length(), tag.length());
    }

    /**
     * @param tag
     * @return true if tag is to be removed from the item's tag list.
     */
    private boolean isATagToBeRemoved(String tag) {
        return tag.substring(0, removeTagPrefixString.length()).equals(removeTagPrefixString);
    }

    /**
     * Parses date and time from argument acquired through NLP input 
     * @param argument
     * @param dateFormat the format the date should be returned in
     * @param timeFormat the format the time should be returned in
     * @return parsed argument as string or null if argument not parsed 
     */
    private String[] parseDateTime(String argument, String dateFormat, String timeFormat) throws IllegalValueException {
        assert dateFormat != null && !dateFormat.isEmpty();
        assert timeFormat != null && !timeFormat.isEmpty();
        assert argument != null;
    
        List<Date> dateTimes = new PrettyTimeParser().parse(argument);
        
        if (dateTimes.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATETIME_PARSE_FAILURE);
        }
        
        Date prettyParsedDateTime = dateTimes.get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat);
        String parsedDate = simpleDateFormat.format(prettyParsedDateTime);
        String parsedTime = simpleTimeFormat.format(prettyParsedDateTime);
        
        String[] parsedDateTime = new String[2];
        parsedDateTime[PARSEDATETIME_ARRAY_DATE_INDEX] = parsedDate;
        parsedDateTime[PARSEDATETIME_ARRAY_TIME_INDEX] = parsedTime;
        
        return parsedDateTime;
    }
    
    //@@author A0140060A-unused
    //Used in earlier version, functionality replaced due to ArgumentTokenizer
    /**
     * Extracts argument from a string containing command arguments
     * @param argumentPattern the pattern used to extract the argument from commandArgs
     * @param argumentGroupName the matcher group name of the argument used in argumentPattern
     * @param commandArgs string containing command arguments
     * @return parsed argument as string or null if argument not parsed 
     */
    private String parseArgument(Pattern argumentPattern, String argumentGroupName, String commandArgs) {
        String argument = null;
        final Matcher argumentMatcher = argumentPattern.matcher(commandArgs);
        if (argumentMatcher.find()) {
            argument = argumentMatcher.group(argumentGroupName);
            argument = removeTrailingCommandChars(argument, commandArgs);
        }
        return argument;
    }

    /**
     * Removes unwanted trailing command characters from argument
     * @param argument
     * @param commandArgs
     * @return cleaned argument string
     */
    private String removeTrailingCommandChars(String argument, String commandArgs) {
        //maximum size of trailing command characters is 3, including the space before them
        if (argument.length() >= 3 && argument.length() < commandArgs.trim().length()-3) {
            //size of trailing name command characters is 2, including the space before it
            if (argument.substring(argument.length()-2, argument.length()).matches(" n")) {
                argument = argument.substring(0, argument.length()-2);
            } else if (argument.substring(argument.length()-3, argument.length()).matches(" (sd|st|ed|et)")) {
            //size of trailing command characters is 3, including the space before them
                argument = argument.substring(0, argument.length()-3);
            }
        }
        return argument;
    }
    //@@author
    
    /**
     * Parses argument in the context of the saveAs specified file command.
     * @param arguments full argument args string
     * @return the prepared command
     */
    private Command prepareSave(String args) {
        args = args.trim();
        if(parseSaveCommandFormat(args)) {
            return new SaveCommand(args);
        }
        
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    /**
     * Checks valid file path is given as argument for SaveCommand.
     * @param args parameter input by user for save command
     * @return true if parameter is valid
     */
    private boolean parseSaveCommandFormat(String args) {
        return !args.equals("") && args.endsWith(".xml");
    }
    
    /**
     * Extracts the new item's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" #", "").split(" #"));
        return new HashSet<>(tagStrings);
    }
    
    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        
        final Matcher itemIndexesMatcher = ITEM_INDEXES_ARGS_FORMAT.matcher(args.trim());
        final Matcher itemIndexMatcher = ITEM_INDEX_ARGS_FORMAT.matcher(args.trim());

        if(itemIndexMatcher.matches()) {
            Optional<Integer> index = parseIndex(args);
            if(!index.isPresent()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(index.get());
        }

        else if(itemIndexesMatcher.matches()) {
            // separate into the different indexes
            ArrayList<String> indexList = new ArrayList<String>(Arrays.asList(args.trim().split("[^0-9]*")));
            for(String indexString : indexList) {
                if(indexString.equals("")) {
                    indexList.remove(indexString);
                }
            }
            ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();
            
            for(String indexInList: indexList) {
                Optional<Integer> index = parseIndex(indexInList);
                if(!index.isPresent()) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
                else {
                    indexesToDelete.add(index.get());
                }
            }
            return new DeleteCommand(indexesToDelete);
        }
        else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }
    
    /**
     * Parses arguments in the context of the done item command.
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

    /**
     * Parses arguments in the context of the not done item command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareNotDone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new NotDoneCommand(index.get());
    }    
    
    /**
     * Parses arguments in the context of the select person command.
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
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
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