package seedu.dailyplanner.logic.parser;

import static seedu.dailyplanner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.dailyplanner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
													   // or
													   // more
													   // keywords
													   // separated
													   // by
													   // whitespace

    private static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward
							   // slashes are
							   // reserved for
							   // delimiter prefixes
	    Pattern.compile("(?<name>[^/]+)" + " (?<isPhonePrivate>p?)d/(?<date>[^/]+)"
		    + " (?<isEmailPrivate>p?)st/(?<starttime>[^/]+)" + " (?<isAddressPrivate>p?)et/(?<endtime>[^/]+)"
		    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of
							 // tags

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
	    
	case EditCommand.COMMAND_WORD:
	    return prepareEdit(arguments);
	    
	case SelectCommand.COMMAND_WORD:
	    return prepareSelect(arguments);

	case DeleteCommand.COMMAND_WORD:
	    return prepareDelete(arguments);

	case ClearCommand.COMMAND_WORD:
	    return new ClearCommand();

	case FindCommand.COMMAND_WORD:
	    return prepareFind(arguments);

	case ListCommand.COMMAND_WORD:
	    return new ListCommand();

	case ExitCommand.COMMAND_WORD:
	    return new ExitCommand();

	case HelpCommand.COMMAND_WORD:
	    return new HelpCommand();

	case ShowCommand.COMMAND_WORD:
	    if (arguments.equals(""))
		return new ShowCommand();
	    else
		return prepareShow(arguments);

	default:
	    return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
	}
    }

    private Command prepareEdit(String arguments) {
		// TODO Auto-generated method stub
    	
    	int index = 0;
		String taskName = "", date = "", startTime = "", endTime = "", isRecurring = "";
    	HashMap<String, String> mapArgs = parseEdit(arguments.trim());

    	// If arguments are in hashmap, pass them to addCommand, if not pass
    	// them as empty string
    	
    	//Change date to "dd/mm/yy/", time to "hh:mm"
        nattyParser natty = new nattyParser();
        
        if (mapArgs.containsKey("index")) {
    	    index = Integer.parseInt(mapArgs.get("index"));
    	}
    	if (mapArgs.containsKey("taskName")) {
    	    taskName = mapArgs.get("taskName");
    	}
    	if (mapArgs.containsKey("date")) {
    	    date = mapArgs.get("date");
    	    date = natty.parseDate(date);
    	}
    
    	if (mapArgs.containsKey("startTime")) {
    	    startTime = mapArgs.get("startTime");
    	    startTime = natty.parseTime(startTime);
    	}
    	if (mapArgs.containsKey("endTime")) {
    	    endTime = mapArgs.get("endTime");
    	    endTime = natty.parseTime(endTime);
    	}
    	if (mapArgs.containsKey("isRecurring")) {
    	    isRecurring = mapArgs.get("isRecurring");
    	}

    	Set<String> emptySet = new HashSet<String>();
    	
    	try {
    	    
    	    return new EditCommand(index, taskName, date, startTime, endTime, emptySet);
    	} catch (IllegalValueException ive) {
    	    return new IncorrectCommand(ive.getMessage());
    	}
	}

	/**
     * Parses arguments in the context of the add person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
	String taskName = "", date = "", startTime = "", endTime = "", isRecurring = "";
	String trimmedArgs = args.trim();
	
	
	if(!(isValidAddArgumentFormat(trimmedArgs))){
		return  new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
	}
	
	
	HashMap<String, String> mapArgs = parseAdd(trimmedArgs);

	// If arguments are in hashmap, pass them to addCommand, if not pass
	// them as empty string
	
	//Change date to "dd/mm/yy/", time to "hh:mm"
    nattyParser natty = new nattyParser();

	if (mapArgs.containsKey("taskName")) {
	    taskName = mapArgs.get("taskName");
	}
	if (mapArgs.containsKey("date")) {
	    date = mapArgs.get("date");
	} else {
	    date = "today";
	}
	date = natty.parseDate(date);
	if (mapArgs.containsKey("startTime")) {
	    startTime = mapArgs.get("startTime");
	    startTime = natty.parseTime(startTime);
	}
	if (mapArgs.containsKey("endTime")) {
	    endTime = mapArgs.get("endTime");
	    endTime = natty.parseTime(endTime);
	}
	if (mapArgs.containsKey("isRecurring")) {
	    isRecurring = mapArgs.get("isRecurring");
	}

	Set<String> emptySet = new HashSet<String>();
	
	
    
    
    
    

	try {
	    
	    return new AddCommand(taskName, date, startTime, endTime, emptySet);
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}
    }

	private boolean isValidAddArgumentFormat(String trimmedArgs) {
		if(trimmedArgs.charAt(1) == '/' || trimmedArgs.charAt(2) == '/'){
			return  false;
		}
		for(int k =0; k <trimmedArgs.length(); k++){
			if(trimmedArgs.charAt(k) == '/'){
				if(!(k+1 == trimmedArgs.length())){				
					if(trimmedArgs.charAt(k+1) == ' '){
						return  false;
					}
				else{
					if(trimmedArgs.charAt(k) == '/')
						return false;					
					}
				
				}
			}
		}
		return true;
	}

    /**
     * Parses the arguments given by the user in the add command and returns it
     * to prepareAdd in a HashMap with keys taskName, date, startTime, endTime,
     * isRecurring
     */

    private HashMap<String, String> parseAdd(String arguments) {
	HashMap<String, String> mapArgs = new HashMap<String, String>();
	String taskName = getTaskNameFromArguments(arguments);
	mapArgs.put("taskName", taskName);
	if (arguments.contains("/")) {
	    String[] splitArgs = arguments.substring(taskName.length() + 1).split(" ");
	    // loop through rest of arguments, add them to hashmap if valid

	    argumentArrayToHashMap(mapArgs, splitArgs);
	}

	return mapArgs;
    }
    
    private HashMap<String, String> parseEdit(String arguments) {
    	HashMap<String, String> mapArgs = new HashMap<String, String>();
    	
    	//Extract index
    	String[] splitArgs1 = arguments.split(" ", 2);
    	int indexStringLength = splitArgs1[0].length();
    	String index = arguments.substring(0, indexStringLength);
    	mapArgs.put("index", index);
    	
    	arguments = arguments.substring(indexStringLength+1);
    	String taskName="";
    	if (hasTaskName(arguments)) {
    	    taskName = getTaskNameFromArguments(arguments);
    	}
    	mapArgs.put("taskName", taskName);
    	if (hasTaskName(arguments) && arguments.contains("/")) {
    	    String[] splitArgs = arguments.substring(taskName.length() + 1).split(" ");
    	    argumentArrayToHashMap(mapArgs, splitArgs);
    	} else if (arguments.contains("/")) {
    	    String[] splitArgs = arguments.split(" ");
    	    argumentArrayToHashMap(mapArgs,splitArgs);
    	}

    	return mapArgs;
        }
    
    /*
     * Loops through arguments, adds them to hashmap if valid
     */
    private void argumentArrayToHashMap(HashMap<String, String> mapArgs, String[] splitArgs) {
        for (int i = 0; i < splitArgs.length; i++) {
        if (splitArgs[i].substring(0, 2).equals("d/")) {
            int j = i + 1;
            String arg = splitArgs[i].substring(2);
            while (j < splitArgs.length && !splitArgs[j].contains("/")) {
        	arg += " " + splitArgs[j];
        	j++;
            }
            mapArgs.put("date", arg);
        }
        if (splitArgs[i].substring(0, 2).equals("s/")) {
            int j = i + 1;
            String arg = splitArgs[i].substring(2);
            while (j < splitArgs.length && !splitArgs[j].contains("/")) {
        	arg += " " + splitArgs[j];
        	j++;
            }
            mapArgs.put("startTime", arg);
        }
        if (splitArgs[i].substring(0, 2).equals("e/")) {
            int j = i + 1;
            String arg = splitArgs[i].substring(2);
            while (j < splitArgs.length && !splitArgs[j].contains("/")) {
        	arg += " " + splitArgs[j];
        	j++;
            }
            mapArgs.put("endTime", arg);
        }
        if (splitArgs[i].substring(0, 2).equals("r/")) {
            int j = i + 1;
            String arg = splitArgs[i].substring(2);
            while (j < splitArgs.length && !splitArgs[j].contains("/")) {
        	arg += " " + splitArgs[j];
        	j++;
            }
            mapArgs.put("isRecurring", arg);
        }
        }
    }

    private boolean hasTaskName(String arguments) {
        if (arguments.substring(0,3).contains("/")) {
            return false;
        } else {
            return true;
        }
    }

    private String getTaskNameFromArguments(String arguments) {
	if (arguments.contains("/")) {
	    String[] firstPart = arguments.split("/");
	    return firstPart[0].substring(0, firstPart[0].length() - 2);
	} else {
	    return arguments;
	}
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments
     * string. Merges duplicate tag strings.
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
     * Parses arguments in the context of the delete person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

	Optional<Integer> index = parseIndex(args);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
	}

	return new DeleteCommand(index.get());
    }
    
    private Command prepareShow(String args) {
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String[] keywords = matcher.group("keywords").split("\\s+");
	nattyParser natty = new nattyParser();
	
	for (int i = 0; i < keywords.length; i++) {
		keywords[i] = natty.parseDate(keywords[i]);
	}
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new ShowCommand(keywordSet);
    }
       

    /**
     * Parses arguments in the context of the select person command.
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
	final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
	if (!matcher.matches()) {
	    return Optional.empty();
	}

	String index = matcher.group("targetIndex");
	if (!StringUtil.isUnsignedInteger(index)) {
	    return Optional.empty();
	}
	return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String[] keywords = matcher.group("keywords").split("\\s+");
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new FindCommand(keywordSet);
    }

}