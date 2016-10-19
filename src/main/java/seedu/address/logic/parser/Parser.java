package seedu.address.logic.parser;
import seedu.address.logic.commands.*;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringDateParser;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.TaskDate;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern FIND_ARGS_WITHOUT_DATE_FORMAT = 
    		Pattern.compile("(?<keywords>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern FIND_ARGS_WITH_DATE_FORMAT = 
    		Pattern.compile("(?<keywords>[^/]+)"
    				+ "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?: by [^/]+)))"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern FIND_ARGS_WITHOUT_KEYWORD_FORMAT =
    		Pattern.compile("((?<startTime>(?:from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?:by [^/]+)))"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern FIND_ARGS_WITH_TAG_FORMAT =
    		Pattern.compile("(?<tagArguments>(?:t/[^/]+)*)");
    
    private static final Pattern EDIT_ARGS_WITHOUT_DATE_FORMAT = 
    		Pattern.compile("(?<targetIndex>[\\d]+)"
    				+ "(?<name> [^/]+)"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern EDIT_ARGS_WITH_DATE_FORMAT =
    		Pattern.compile("(?<targetIndex>[\\d]+)"
    				+ "(?<name> [^/]+)"
    				+ "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?: by [^/]+)))"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern EDIT_ARGS_WITHOUT_NAME_FORMAT =
    		Pattern.compile("(?<targetIndex>[\\d]+)"
    				+ "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?: by [^/]+)))"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern EDIT_ARGS_WITH_TAG_FORMAT =
    		Pattern.compile("(?<targetIndex>[\\d]+)" 
    				+ "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern NON_FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<name>[^/]+)"
            		+ "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?: by [^/]+)))"
                    + "(?<recurring>(?: [^/(t/) ]+)*)"
                    + "(?<tagArguments>(?: t/[^ ]+)*)"); // variable number of tags
        
    private static final Pattern BLOCK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<startTime>(?:from [^/]+)(?<endTime>(?: to [^/]+)))"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final int DEADLINE_INDEX = 0;
    
    private static final int START_TIME_INDEX = 0;
    
    private static final int END_TIME_INDEX = 1;
    
    private static final int ONLY_DEADLINE= 1;
    
    private static final int TIME_PERIOD = 2;
    
    private static final com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
    
    public Parser() {
        nattyParser.notifyAll();
    }

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
            
        case BlockCommand.COMMAND_WORD:
        	return prepareBlock(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);
        
        case ChangeDirectoryCommand.COMMAND_WORD:
        	return new ChangeDirectoryCommand(arguments.trim());
        	
        case CompleteCommand.COMMAND_WORD:
        	return prepareComplete(arguments);

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
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();          

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
    private Command prepareAdd(String args){
        final Matcher matcherNonFloating = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcherNonFloating.matches()) {
            return prepareAddFloating(args);
        }
        return prepareAddNonFloating(args);
    }
    
    /**
     * Parses arguments in the context of adding a floating task
     * @param args full command args string
     * @return the prepared add floating command
     */
    private Command prepareAddFloating(String args) {
        final Matcher matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFloatingCommand.MESSAGE_USAGE));
        }
        try {
            return new AddFloatingCommand(
                    matcher.group("name"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of adding a non floating task
     * @param args full command args string
     * @return the prepared add non floating command
     */
    private Command prepareAddNonFloating(String args) {
        final Matcher matcher = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNonFloatingCommand.MESSAGE_USAGE));
        }
        try {
            RecurringType recurringType = RecurringType.NONE;
            
            if (matcher.group("recurring").isEmpty()) {
                recurringType = RecurringType.NONE;
            }
            else {
                try{
                    recurringType = extractRecurringInfo(matcher.group("recurring"));
                } catch (IllegalArgumentException iae) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNonFloatingCommand.MESSAGE_USAGE));                    
                }
            }
            
            if(matcher.group("deadline") != null) {
                return prepareAddNonFloatingByDate(matcher, recurringType);
            } else {
                return prepareAddNonFloatingFromDateToDate(matcher, recurringType);
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Prepares arguments in the context of adding a non floating task by date only
     * @param matcher Contains the information we need
     * @param recurringType 
     * @return the prepared add non floating command
     * @throws IllegalValueException Signals for incorrect command
     */
    private Command prepareAddNonFloatingByDate(Matcher matcher, RecurringType recurringType) throws IllegalValueException {
        String endInput = matcher.group("deadline");
        return new AddNonFloatingCommand(
                matcher.group("name"),
                getTagsFromArgs(matcher.group("tagArguments")),
                new TaskDate(TaskDate.DATE_NOT_PRESENT),
                new TaskDate(getDateFromString(endInput).getTime()),
                recurringType
                );
    }

    /**
     * Prepares arguments in the context of adding a non floating task from date to date
     * @param matcher Contains the information we need
     * @param recurringType 
     * @return the prepared add non floating command
     * @throws IllegalValueException Signals for incorrect command
     */    
    private Command prepareAddNonFloatingFromDateToDate(Matcher matcher, RecurringType recurringType) throws IllegalValueException {
        String startInput = matcher.group("startTime");
        String endInput = matcher.group("endTime");
        
        return new AddNonFloatingCommand(
                matcher.group("name"),
                getTagsFromArgs(matcher.group("tagArguments")),
                new TaskDate(getDateFromString(startInput).getTime()),
                new TaskDate(getDateFromString(endInput).getTime()),
                recurringType
                );
    }
    
    private Command prepareBlock(String args) {
		// TODO Auto-generated method stub
    	Matcher matcher = BLOCK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockCommand.MESSAGE_USAGE));
        }
        try {
            
            String startInput = matcher.group("startTime");
            String endInput = matcher.group("endTime");
            
            return new BlockCommand(
                    getTagsFromArgs(matcher.group("tagArguments")),
                    new TaskDate(getDateFromString(startInput).getTime()),
                    new TaskDate(getDateFromString(endInput).getTime())
                    );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
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
    
    private Command prepareComplete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new CompleteCommand(index.get());
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
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
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
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
    	if(args == null || args.length() == 0)
    		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        final Matcher noDateMatcher = FIND_ARGS_WITHOUT_DATE_FORMAT.matcher(args.trim());
        final Matcher dateMatcher = FIND_ARGS_WITH_DATE_FORMAT.matcher(args.trim());
        final Matcher tagMatcher = FIND_ARGS_WITH_TAG_FORMAT.matcher(args.trim());
        final Matcher noKeywordMatcher = FIND_ARGS_WITHOUT_KEYWORD_FORMAT.matcher(args.trim());
        
        Set<String> keywordSet = new HashSet<String>();
        Date startTime = null;
        Date endTime = null;
        Date deadline = null;
        Date today = null;
        Set<String> tagSet = new HashSet<String>();
        
        boolean dateMatcherMatches = dateMatcher.matches();
        boolean noDateMatcherMatches = noDateMatcher.matches();
        boolean tagMatcherMatches = tagMatcher.matches();
        boolean noKeywordMatcherMathces = noKeywordMatcher.matches();
        
        if(dateMatcherMatches) {
        	String[] keywords = dateMatcher.group("keywords").split("\\s+");
    		keywordSet = new HashSet<>(Arrays.asList(keywords));
    		try {
    			ArrayList<Date> dateSet = extractDateInfo(dateMatcher);
    			if(dateSet.size() == ONLY_DEADLINE) {
    				deadline = dateSet.get(DEADLINE_INDEX);
        		} else if(dateSet.size() == TIME_PERIOD) {
        			startTime = dateSet.get(START_TIME_INDEX);
        			endTime = dateSet.get(END_TIME_INDEX);
        		}
    		} catch(IllegalArgumentException iae) {
    			return new IncorrectCommand(iae.getMessage());
    		}
    		
    		try {
        		tagSet = getTagsFromArgs(noDateMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else if(noKeywordMatcherMathces) {
        	try {
    			ArrayList<Date> dateSet = extractDateInfo(noKeywordMatcher);
    			if(dateSet.size() == ONLY_DEADLINE) {
        			deadline = dateSet.get(DEADLINE_INDEX);
        		} else if(dateSet.size() == TIME_PERIOD) {
        			startTime = dateSet.get(START_TIME_INDEX);
        			endTime = dateSet.get(END_TIME_INDEX);
        		}
    		} catch(IllegalArgumentException iae) {
    			return new IncorrectCommand(iae.getMessage());
    		}
        	
        	try {
        		tagSet = getTagsFromArgs(noKeywordMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else if(tagMatcherMatches) {
        	final Collection<String> tagStrings = Arrays.asList(tagMatcher.group("tagArguments").replaceFirst("t/", "").split(" t/"));
            tagSet = new HashSet<>(tagStrings);
        } else if(noDateMatcherMatches) {
        	String[] keywords = noDateMatcher.group("keywords").split("\\s+");
    		keywordSet = new HashSet<>(Arrays.asList(keywords));
        	try {
        		tagSet = getTagsFromArgs(noDateMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        System.out.println(tagSet.toString());
        return new FindCommand(keywordSet, startTime, endTime, deadline, tagSet);
    }
    
    private Command prepareEdit(String args) {
    	if(args == null || args.length() == 0)
    		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
    	
    	final Matcher noDateMatcher = EDIT_ARGS_WITHOUT_DATE_FORMAT.matcher(args.trim());
        final Matcher dateMatcher = EDIT_ARGS_WITH_DATE_FORMAT.matcher(args.trim());
        final Matcher tagMatcher = EDIT_ARGS_WITH_TAG_FORMAT.matcher(args.trim());
        final Matcher noNameMatcher = EDIT_ARGS_WITHOUT_NAME_FORMAT.matcher(args.trim());
        
        final int targetIndex;
        String taskName = "";
        Date startTime = null;
        Date endTime = null;
        Set<String> tagSet = new HashSet<String>();
        
        boolean dateMatcherMatches = dateMatcher.matches();
        boolean noDateMatcherMatches = noDateMatcher.matches();
        boolean tagMatcherMatches = tagMatcher.matches();
        boolean noNameMatcherMathces = noNameMatcher.matches();
        
        if(dateMatcherMatches) {
        	targetIndex = Integer.parseInt(dateMatcher.group("targetIndex"));
        	taskName = dateMatcher.group("name").replaceFirst("\\s", "");
        	
        	try {
    			ArrayList<Date> dateSet = extractDateInfo(dateMatcher);
    			if(dateSet.size() == ONLY_DEADLINE) {
    				endTime = dateSet.get(DEADLINE_INDEX);
        		} else if(dateSet.size() == TIME_PERIOD) {
        			startTime = dateSet.get(START_TIME_INDEX);
        			endTime = dateSet.get(END_TIME_INDEX);
        		}
    		} catch(IllegalArgumentException iae) {
    			return new IncorrectCommand(iae.getMessage());
    		}
    		
    		try {
        		tagSet = getTagsFromArgs(noDateMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else if(noNameMatcherMathces) {
        	targetIndex = Integer.parseInt(noNameMatcher.group("targetIndex"));
        	
        	try {
    			ArrayList<Date> dateSet = extractDateInfo(noNameMatcher);
    			if(dateSet.size() == ONLY_DEADLINE) {
        			endTime = dateSet.get(DEADLINE_INDEX);
        		} else if(dateSet.size() == TIME_PERIOD) {
        			startTime = dateSet.get(START_TIME_INDEX);
        			endTime = dateSet.get(END_TIME_INDEX);
        		}
    		} catch(IllegalArgumentException iae) {
    			return new IncorrectCommand(iae.getMessage());
    		}
        	
        	try {
        		tagSet = getTagsFromArgs(noNameMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else if(tagMatcherMatches) {
        	targetIndex = Integer.parseInt(tagMatcher.group("targetIndex"));
        	
        	try {
        		tagSet = getTagsFromArgs(tagMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else if(noDateMatcherMatches) {
        	targetIndex = Integer.parseInt(noDateMatcher.group("targetIndex"));
        	taskName = noDateMatcher.group("name").replaceFirst("\\s", "");
        	
        	try {
        		tagSet = getTagsFromArgs(noDateMatcher.group("tagArguments"));
        	} catch(IllegalValueException ive) {
        		return new IncorrectCommand(ive.getMessage());
        	}
        } else {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }
        
        try {
        	return new EditCommand(targetIndex, taskName, tagSet, startTime, endTime);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }   
    }
    
    /**
     * Reformats any date into the format that we are storing and using in this software 
     * @param oldDate
     * @return the new formatted date
     */
    public static String reformatDate(String oldDate) {
    	long newDate = getDateFromString(oldDate).getTime();
    	SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma");
        return formatter.format(new Date(newDate));
    }
    
    public static ArrayList<Date> extractDateInfo(Matcher m) throws IllegalArgumentException {
    	ArrayList<Date> resultSet = new ArrayList<Date>();
    	try {
			String[] time = m.group("startTime").replace(" from ", "").split(" to ");
			resultSet.clear();
			try {
				resultSet.add(getDateFromString(time[START_TIME_INDEX]));
	    		resultSet.add(getDateFromString(time[END_TIME_INDEX]));
			} catch(Exception cnp) {
				throw new IllegalArgumentException("Illegal date input");
			}
			
		} catch(Exception ise) {
			resultSet.clear();
			try {
				resultSet.add(getDateFromString(m.group("deadline").replace(" by ", "")));
			} catch(Exception cnp) {
				throw new IllegalArgumentException("Illegal date input");
			}
    	} 	
    	return resultSet;
    }
    
    /**
     * Parses through the dateInput and provides the Date from that input
     * @param dateInput The date that we want to convert from string to Date
     * @return A single Date from the string
     */
    public static Date getDateFromString(String dateInput) {
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        
        return dateGroups.get(0).getDates().get(0);
    }
    
    public static RecurringType extractRecurringInfo(String recurringInfo) throws IllegalArgumentException {
        recurringInfo = recurringInfo.toUpperCase().trim();
        RecurringDateParser recurringParser = RecurringDateParser.getInstance();
        return recurringParser.getRecurringType(recurringInfo);
    }

}