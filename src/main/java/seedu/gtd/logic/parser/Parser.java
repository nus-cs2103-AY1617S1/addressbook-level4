package seedu.gtd.logic.parser;

import static seedu.gtd.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gtd.commons.core.Messages.START_END_DATE_INVALID_COMMAND_FORMAT;
import static seedu.gtd.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.commons.util.StringUtil;
import seedu.gtd.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {
	//@@author addressbook-level4
	
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

//    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
//            Pattern.compile("(?<name>[^/]+)"
//                    + " d/(?<dueDate>[^/]+)"
//                    + " a/(?<address>[^/]+)"
//                    + " p/(?<priority>[^/]+)"
//                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    
    //@@author A0130677A
    
    private static final Pattern NAME_TASK_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+) (s|t|p|a|d|z)/.*");
    
    private static final Pattern PRIORITY_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* p/(?<priority>[^/]+) (s|t|a|d|z)/.*");
    
    private static final Pattern ADDRESS_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* a/(?<address>[^/]+) (s|t|p|d|z)/.*");
    
    private static final Pattern STARTDATE_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* s/(?<startDate>[^/]+) (d|t|a|p|z)/.*");
    
    private static final Pattern DUEDATE_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* d/(?<dueDate>[^/]+) (s|t|a|p|z)/.*");
    
    private static final Pattern TAGS_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* t/(?<tagArguments>[^/]+) (s|d|a|p|z)/.*");
    
  //@@author addressbook-level4
    
    private static final Pattern EDIT_DATA_ARGS_FORMAT =
    		Pattern.compile("(?<targetIndex>\\S+)" 
                    + " (?<newDetails>\\S+(?:\\s+\\S+)*)");

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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        	return prepareHelp(arguments);
        	

        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();

        case SetFilePathCommand.COMMAND_WORD:
        	return prepareSetFilePath(arguments);


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
    
    //@@author A0130677A
    
    private Command prepareAdd(String args){
    	String preprocessedArg = appendEnd(args.trim());
    	
        final Matcher nameMatcher = NAME_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher startDateMatcher = STARTDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher dueDateMatcher = DUEDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher addressMatcher = ADDRESS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher priorityMatcher = PRIORITY_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher tagsMatcher = TAGS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
 
        String nameToAdd = checkEmptyAndAddDefault(nameMatcher, "name", "nil");
        String startDateToAdd = checkEmptyAndAddDefault(startDateMatcher, "startDate", "nil");
        String dueDateToAdd = checkEmptyAndAddDefault(dueDateMatcher, "dueDate", "nil");
        String addressToAdd = checkEmptyAndAddDefault(addressMatcher, "address", "nil");
        String priorityToAdd = checkEmptyAndAddDefault(priorityMatcher, "priority", "1");
        
        // format date if due date or start date is specified
        
        Date dueDateInDateFormat = null;
        Date startDateInDateFormat = null;
        
        if (dueDateMatcher.matches()) {
        	dueDateInDateFormat = getDateInDateFormat(dueDateToAdd);
        	dueDateToAdd = parseDueDate(dueDateToAdd);
        	System.out.println(dueDateInDateFormat);
        }
        
        if (startDateMatcher.matches()) {
        	startDateInDateFormat = getDateInDateFormat(startDateToAdd);
        	startDateToAdd = parseDueDate(startDateToAdd);
        }
        
        // check that end date is strictly later than start date
        
        if (dueDateInDateFormat != null && startDateInDateFormat != null 
        		&& dueDateInDateFormat.compareTo(startDateInDateFormat) < 0) {
        	return new IncorrectCommand(START_END_DATE_INVALID_COMMAND_FORMAT);
        }
        
        Set<String> tagsProcessed = Collections.emptySet();
        
        if (tagsMatcher.matches()) {
        	tagsProcessed = getTagsFromArgs(tagsMatcher.group("tagArguments"));
        }
        
        
        // Validate arg string format
        if (!nameMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {
            return new AddCommand(
                    nameToAdd,
                    startDateToAdd,
                    dueDateToAdd,
                    addressToAdd,
                    priorityToAdd,
                    tagsProcessed
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

	private String appendEnd(String args) {
    	return args + " z/";
    }
    
    private String checkEmptyAndAddDefault(Matcher matcher, String groupName, String defaultValue) {
    	if (matcher.matches()) {
    		return matcher.group(groupName);
    	} else {
    		return defaultValue;
    	}
    }
    
    //@@author A0146130W
    
    private String parseDueDate(String dueDateRaw) {
    	NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
    	return nlp.formatString(dueDateRaw);
    }
    
    //@@author A0130677A
    
    private Date getDateInDateFormat(String dueDateRaw) {
    	NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
    	return nlp.getDate(dueDateRaw);
    }
    
    // remove time on date parsed to improve search results
    private String removeTimeOnDate(String dueDateRaw) {
    	String[] dateTime = dueDateRaw.split(" ");
    	return dateTime[0];
    }
    
    //@@author addressbook-level4
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.split(" "));
        return new HashSet<>(tagStrings);
    }
    
    //@@author A0146130W
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        
        Optional<Integer> index = parseIndex(args, EDIT_DATA_ARGS_FORMAT);
        final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        final String[] splitNewDetails = matcher.group("newDetails").split("\\s+");
        ArrayList<String> combinedDetails = combineSameDetails(splitNewDetails);
        
        Hashtable<String, String> newDetailsSet = new Hashtable<String, String>();
        
        for (String detail : combinedDetails) {
        	String detailType = extractDetailType(detail);
        	String preparedNewDetail = prepareNewDetail(detailType, detail);
        	System.out.println("before adding to hashtable: " + detailType + " " + preparedNewDetail);
			newDetailsSet.put(detailType, preparedNewDetail);
		}
        
        return new EditCommand(
           index.get()-1,
           newDetailsSet
        );
    }
    
    private ArrayList<String> combineSameDetails(String[] details) {
    	ArrayList<String> alDetails = new ArrayList<String>(Arrays.asList(details));
    	System.out.println(alDetails.toString());
    	
    	String name = new String();
    	String address = new String();
    	String dueDate = new String();
    	String priority = new String();
    	
    	int currentDetailType = 0;
    	
    	if(alDetails.size() == 1) {
    		return alDetails;
    	}
    	
    	for (String detail: alDetails) {
    		System.out.println("detail: " + detail);
    		
    		if(extractDetailType(detail).equals("name")) {
    			System.out.println("current detail type: " + currentDetailType);
    			switch(currentDetailType) {
    			case 1: address = address + " " + detail; break;
    			case 2: dueDate = dueDate + " " + detail; break;
    			case 3: priority = priority + " " + detail; break;
    			default: { 
    			         if(name.isEmpty()) name = detail;
		                 else name = name + " " + detail;
		                 break;
		                 }
    			}
    		}
    		else if(extractDetailType(detail).equals("address")) {
    			System.out.println("detected address " + detail);
    			address = detail;
    			currentDetailType = 1;
    		}
    		else if(extractDetailType(detail).equals("dueDate")) {
    			System.out.println("detected dueDate " + detail);
    			dueDate = detail;
    			currentDetailType = 2;
    		}
    		else if(extractDetailType(detail).equals("priority")) {
    			System.out.println("detected priority " + detail);
    			address = detail;
    			currentDetailType = 3;
    		}
    	}
    	
    	ArrayList<String> finalCombined = new ArrayList<String>();
    	//does not remove the separate words from the list, they will be overwritten by the final combined string
    	if(!name.isEmpty()) finalCombined.add(name);
    	System.out.println("from combining name: " + name);
    	if(!address.isEmpty()) finalCombined.add(address);
    	System.out.println("from combining address: " + address);
    	if(!dueDate.isEmpty()) finalCombined.add(dueDate);
    	if(!priority.isEmpty()) finalCombined.add(priority);
    	
    	System.out.println("from combining: " + finalCombined.toString());
    	return finalCombined;
    }
    
    private String removeDetailPrefix(String detailWithPrefix) {
    	return detailWithPrefix.substring(detailWithPrefix.indexOf('/') + 1);
    }
    
    private String prepareNewDetail(String detailType, String detailWithPrefix) {
    	String detail = removeDetailPrefix(detailWithPrefix);
    	if(detailType.equals("dueDate")) detail = parseDueDate(detail);
    	return detail;
    }
    
    //@@author A0146130W-reused
    private String extractDetailType(String args) {
    	String preprocessedArgs = " " + appendEnd(args.trim());
        final Matcher dueDateMatcher = DUEDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
        final Matcher addressMatcher = ADDRESS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
        final Matcher priorityMatcher = PRIORITY_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	
    	if(addressMatcher.matches()) {
    		return "address";
    	}
    	else if(dueDateMatcher.matches()) {
    		return "dueDate";
    	}
    	else if(priorityMatcher.matches()) {
    		return "priority";
    	}
    	
    	return "name";
    }
    
    //@@author addressbook-level4
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
    
    //@@author A0130677A
    
    private Command prepareDone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }
    
  //@@author addressbook-level4

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
    
    //@@author A0146130W
    private Optional<Integer> parseIndex(String command, Pattern matcherFormat) {
        final Matcher matcher = matcherFormat.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    
    //@@author addressbook-level4
    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    
    //@@author A0130677A
    private Command prepareFind(String args) {
    	
    	// check if parameters are specified and pass specified field to FindCommand
    	
    	String preprocessedArgs = " " + appendEnd(args.trim());
    	final Matcher addressMatcher = ADDRESS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	final Matcher priorityMatcher = PRIORITY_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	final Matcher startDateMatcher = STARTDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	final Matcher dueDateMatcher = DUEDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	final Matcher tagsMatcher = TAGS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArgs);
    	
    	Set<String> defaultSet = new HashSet<String>();
    	
    	if (addressMatcher.matches()) {
    		String addressToBeFound = addressMatcher.group("address");
    		return new FindCommand(addressToBeFound, defaultSet,"address");
    	}
    	if (priorityMatcher.matches()) {
    		String priorityToBeFound = priorityMatcher.group("priority");
    		return new FindCommand(priorityToBeFound, defaultSet, "priority");
    	}
    	if (startDateMatcher.matches()) {
    		String dueDateToBeFound = dueDateMatcher.group("startDate");
    		String parsedDueDateToBeFound = removeTimeOnDate(parseDueDate(dueDateToBeFound));
    		return new FindCommand(parsedDueDateToBeFound, defaultSet, "startDate");
    	}
    	if (dueDateMatcher.matches()) {
    		String dueDateToBeFound = dueDateMatcher.group("dueDate");
    		String parsedDueDateToBeFound = removeTimeOnDate(parseDueDate(dueDateToBeFound));
    		return new FindCommand(parsedDueDateToBeFound, defaultSet, "dueDate");
    	}
    	if (tagsMatcher.matches()) {
    		String tagsToBeFound = tagsMatcher.group("tagArguments");
    		return new FindCommand(tagsToBeFound, defaultSet,"tagArguments");
    	}
    	
    	// free-form search by keywords
    	
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] splitKeywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(splitKeywords));
        
        final String keywords = matcher.group("keywords");
        return new FindCommand(keywords, keywordSet, "nil");
    }
    
  //@@author addressbook-level4
    
private Command prepareList(String args) {
    	
    	// check if parameters are specified and pass specified field to FindCommand
    	
    	//String preprocessedArgs = " " + appendEnd(args.trim());
    	return new ListCommand(args);
}

    /**
     * Parses arguments in the context of the help command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author A0139158X
    private Command prepareHelp(String args) {
    	//if no argument
    	if (args.equals("")) {
    		args="help";
    	}
    	
    	final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return new HelpCommand(commandWord);
    }
    
    //@@author A0139072H
    /**
     * Parses arguments in the context of the setFilePath command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	private Command prepareSetFilePath(String args) {
		if(args.equals("")){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetFilePathCommand.MESSAGE_USAGE));
		}
		final String filePath = args;
		try {
			return new SetFilePathCommand(filePath);
		} catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetFilePathCommand.MESSAGE_USAGE));
		}
	}

}
