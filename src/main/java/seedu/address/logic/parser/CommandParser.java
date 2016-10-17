package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.model.item.DateTime;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses user input.
 */
public class CommandParser {
    
    private final Logger logger = LogsCenter.getLogger(CommandParser.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    private static final Pattern EDIT_ARGS_FORMAT = Pattern.compile("(?i:"
    													+"(?<taskName>.*?)?"
            											+"(?:"
            											+"(?:, by (?<endDateFormatOne>.*?))"
            											+"|(?:, from (?<startDateFormatOne>.*?))"
            											+"|(?:, at (?<startDateFormatTwo>.*?))"
            											+"|(?:, start (?<startDateFormatThree>.*?))"
            											+")?"
            											+"(?:"
            											+"(?:, to (?<endDateFormatTwo>.*?))?"
            											+"(?:, end (?<endDateFormatThree>.*?))?"
            											+")?"
            											+"(?:, repeat every (?<recurrenceRate>.*?))?"
            											+"(?:"
            											+"(?:-reset (?<resetField>.*?))"
            											+")?"
            											+"(?:-(?<priority>.*?))?)");
    
    private static final Pattern RECURRENCE_RATE_ARGS_FORMAT = Pattern.compile("(?<rate>\\d+)?(?<timePeriod>.*?)");
    
    private static final String REGEX_CASE_IGNORE_OPENING = "(?i:";
    private static final String REGEX_CLOSE_BRACE = ")";
    private static final String REGEX_GREEDY_SELECT = ".*?";
    private static final String REGEX_NAME_NO_CLOSE_BRACE = "(?<taskName>.*?";
    private static final String REGEX_ADDITIONAL_KEYWORD = "(?:"
            +"(?: from )"
            +"|(?: at )"
            +"|(?: start )"
            +"|(?: by )"
            +"|(?: to )"
            +"|(?: end )"
            +")";
    private static final String REGEX_FIRST_DATE = "(?:"
            +"(?: from (?<startDateFormatOne>.*?))"
            +"|(?: at (?<startDateFormatTwo>.*?))"
            +"|(?: start (?<startDateFormatThree>.*?))"
            +"|(?: by (?<endDateFormatOne>.*?))"
            +"|(?: to (?<endDateFormatTwo>.*?))"
            +"|(?: end (?<endDateFormatThree>.*?))"
            +")";
    private static final String REGEX_SECOND_DATE = "(?:"
            +"(?: from (?<startDateFormatFour>.*?))"
            +"|(?: at (?<startDateFormatFive>.*?))"
            +"|(?: start (?<startDateFormatSix>.*?))"
            +"|(?: by (?<endDateFormatFour>.*?))"
            +"|(?: to (?<endDateFormatFive>.*?))"
            +"|(?: end (?<endDateFormatSix>.*?))"
            +")";
    private static final String REGEX_RECURRENCE_AND_PRIORITY = "(?: repeat every (?<recurrenceRate>.*?))?"
            +"(?: -(?<priority>.*?))?";

    public CommandParser() {}

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
            return new HelpCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return prepareAdd(commandWord + arguments);
        }
    }

	/**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        
        String argsTrimmed = args.trim();
        
        if(argsTrimmed.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        String taskName = null;
        String startDate = null;
        String endDate = null;
        String rate = null;
        String timePeriod = null;
        String priority = null;  
        
        Pattern pattern = Pattern.compile(REGEX_ADDITIONAL_KEYWORD);
        Matcher matcher = pattern.matcher(argsTrimmed);

        if (!matcher.matches()) {
        }

        int numberOfKeywords = 0;
        while(matcher.find()){
            numberOfKeywords++;
        }
        logger.info("Number of keywords in \"" + argsTrimmed + "\" = " + numberOfKeywords);
        
        try {
            if (numberOfKeywords == 0) {
                pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_CLOSE_BRACE
                        + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                matcher = pattern.matcher(argsTrimmed);

                if (!matcher.matches()) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }

                taskName = matcher.group("taskName").trim();

                if (matcher.group("recurrenceRate") != null) {
                    String recurrenceString = matcher.group("recurrenceRate");
                    final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);
                
                    if (!recurrenceMatcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    } 
                
                    if (recurrenceMatcher.group("rate") != null) {
                        rate = recurrenceMatcher.group("rate").trim();
                    }
                
                    assert recurrenceMatcher.group("timePeriod") != null;
                    timePeriod = recurrenceMatcher.group("timePeriod").trim();
                }
            
                if (matcher.group("priority") != null) {
                    priority = matcher.group("priority").trim();
                } else {
                    priority = "medium";
                }
                return new AddCommand(taskName, startDate, endDate, rate, timePeriod, priority);
            } else if (numberOfKeywords == 1) {
                pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_CLOSE_BRACE +
                        REGEX_FIRST_DATE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                matcher = pattern.matcher(argsTrimmed);

                if (!matcher.matches()) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            
                if (matcher.group("startDateFormatOne") != null) {
                    startDate = matcher.group("startDateFormatOne").trim();
                } else if (matcher.group("startDateFormatTwo") != null) {
                    startDate = matcher.group("startDateFormatTwo").trim();
                } else if (matcher.group("startDateFormatThree") != null) {
                    startDate = matcher.group("startDateFormatThree").trim();
                } else if (matcher.group("endDateFormatOne") != null) {
                    endDate = matcher.group("endDateFormatOne").trim(); 
                } else if (matcher.group("endDateFormatTwo") != null) {
                    endDate = matcher.group("endDateFormatTwo").trim();
                } else if (matcher.group("endDateFormatThree") != null) {
                    endDate = matcher.group("endDateFormatThree").trim();
                } 
            
                if (startDate != null && !DateTime.isStringValidDate(startDate)) {
                    // reparse
                    startDate = null;
                    pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_ADDITIONAL_KEYWORD + 
                            REGEX_GREEDY_SELECT + REGEX_CLOSE_BRACE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                } else if (endDate != null && !DateTime.isStringValidDate(endDate)) {
                    endDate = null;
                    pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_ADDITIONAL_KEYWORD + 
                            REGEX_GREEDY_SELECT + REGEX_CLOSE_BRACE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                }

                taskName = matcher.group("taskName").trim();
            
                if (matcher.group("recurrenceRate") != null) {
                    String recurrenceString = matcher.group("recurrenceRate");
                    final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);
                
                    if (!recurrenceMatcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    } 
                
                    if (recurrenceMatcher.group("rate") != null) {
                        rate = recurrenceMatcher.group("rate").trim();
                    }
                
                    assert recurrenceMatcher.group("timePeriod") != null;
                    timePeriod = recurrenceMatcher.group("timePeriod").trim();
                }

                if (matcher.group("priority") != null) {
                    priority = matcher.group("priority").trim();
                } else {
                    priority = "medium";
                }
                return new AddCommand(taskName, startDate, endDate, rate, timePeriod, priority);
            } else if (numberOfKeywords == 2) {
                pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_CLOSE_BRACE +
                        REGEX_FIRST_DATE + REGEX_SECOND_DATE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                matcher = pattern.matcher(argsTrimmed);

                if (!matcher.matches()) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            
                if (matcher.group("startDateFormatOne") != null) {
                    startDate = matcher.group("startDateFormatOne").trim();
                } else if (matcher.group("startDateFormatTwo") != null) {
                    startDate = matcher.group("startDateFormatTwo").trim();
                } else if (matcher.group("startDateFormatThree") != null) {
                    startDate = matcher.group("startDateFormatThree").trim();
                } else if (matcher.group("endDateFormatOne") != null) {
                    endDate = matcher.group("endDateFormatOne").trim(); 
                } else if (matcher.group("endDateFormatTwo") != null) {
                    endDate = matcher.group("endDateFormatTwo").trim();
                } else if (matcher.group("endDateFormatThree") != null) {
                    endDate = matcher.group("endDateFormatThree").trim();
                } 
                
                boolean isValidEndDate = true;
                // first keyword is part of the name
                if ((startDate != null && !DateTime.isStringValidDate(startDate)) || 
                        endDate != null && !DateTime.isStringValidDate(endDate)) {
                    isValidEndDate = false;
                    startDate = null;
                    endDate = null;
                    // reparse
                    pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_ADDITIONAL_KEYWORD +
                            REGEX_GREEDY_SELECT + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_RECURRENCE_AND_PRIORITY + 
                            REGEX_CLOSE_BRACE);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                    
                    if (matcher.group("startDateFormatOne") != null) {
                        startDate = matcher.group("startDateFormatOne").trim();
                    } else if (matcher.group("startDateFormatTwo") != null) {
                        startDate = matcher.group("startDateFormatTwo").trim();
                    } else if (matcher.group("startDateFormatThree") != null) {
                        startDate = matcher.group("startDateFormatThree").trim();
                    } else if (matcher.group("endDateFormatOne") != null) {
                        endDate = matcher.group("endDateFormatOne").trim(); 
                    } else if (matcher.group("endDateFormatTwo") != null) {
                        endDate = matcher.group("endDateFormatTwo").trim();
                    } else if (matcher.group("endDateFormatThree") != null) {
                        endDate = matcher.group("endDateFormatThree").trim();
                    } 
                }
                
                // second keyword is part of the name
                if ((startDate != null && !DateTime.isStringValidDate(startDate)) || 
                        endDate != null && !DateTime.isStringValidDate(endDate)) {
                    // reparse
                    startDate = null;
                    endDate = null;
                    pattern = Pattern.compile(REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE + REGEX_ADDITIONAL_KEYWORD + 
                            REGEX_GREEDY_SELECT + REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT + 
                            REGEX_CLOSE_BRACE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                }
                
                if (isValidEndDate) {
                    if (matcher.group("endDateFormatFour") != null) {
                        endDate = matcher.group("endDateFormatFour").trim(); 
                    } else if (matcher.group("endDateFormatFive") != null) {
                        endDate = matcher.group("endDateFormatFive").trim();
                    } else if (matcher.group("endDateFormatSix") != null) {
                        endDate = matcher.group("endDateFormatSix").trim();
                    } 
                }
            
                taskName = matcher.group("taskName").trim();
            
                if (matcher.group("recurrenceRate") != null) {
                    String recurrenceString = matcher.group("recurrenceRate");
                    final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);
                
                    if (!recurrenceMatcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    } 
                
                    if (recurrenceMatcher.group("rate") != null) {
                        rate = recurrenceMatcher.group("rate").trim();
                    }
                
                    assert recurrenceMatcher.group("timePeriod") != null;
                    timePeriod = recurrenceMatcher.group("timePeriod").trim();
                }

                if (matcher.group("priority") != null) {
                    priority = matcher.group("priority").trim();
                } else {
                    priority = "medium";
                }
                return new AddCommand(taskName, startDate, endDate, rate, timePeriod, priority);
            } else if (numberOfKeywords > 2) {
                int numberOfAdditionalKeywords = numberOfKeywords - 2;
                String regexOne = REGEX_CASE_IGNORE_OPENING + REGEX_NAME_NO_CLOSE_BRACE;
                String regexCopy = regexOne;
                for (int i = 0; i < numberOfAdditionalKeywords; i++) {
                    regexCopy += REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
                }
                regexCopy += REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_SECOND_DATE + 
                        REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE;
                pattern = Pattern.compile(regexCopy);
                matcher = pattern.matcher(argsTrimmed);

                if (!matcher.matches()) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            
                if (matcher.group("startDateFormatOne") != null) {
                    startDate = matcher.group("startDateFormatOne").trim();
                } else if (matcher.group("startDateFormatTwo") != null) {
                    startDate = matcher.group("startDateFormatTwo").trim();
                } else if (matcher.group("startDateFormatThree") != null) {
                    startDate = matcher.group("startDateFormatThree").trim();
                } else if (matcher.group("endDateFormatOne") != null) {
                    endDate = matcher.group("endDateFormatOne").trim(); 
                } else if (matcher.group("endDateFormatTwo") != null) {
                    endDate = matcher.group("endDateFormatTwo").trim();
                } else if (matcher.group("endDateFormatThree") != null) {
                    endDate = matcher.group("endDateFormatThree").trim();
                } 
                
                boolean isValidEndDate = true;
                // first keyword is part of the name
                if ((startDate != null && !DateTime.isStringValidDate(startDate)) || 
                        endDate != null && !DateTime.isStringValidDate(endDate)) {
                    isValidEndDate = false;
                    startDate = null;
                    endDate = null;
                    regexCopy = regexOne;
                    numberOfAdditionalKeywords++;
                    for (int i = 0; i < numberOfAdditionalKeywords; i++) {
                        regexCopy += REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
                    }
                    regexCopy += REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_RECURRENCE_AND_PRIORITY + 
                            REGEX_CLOSE_BRACE;
                    pattern = Pattern.compile(regexCopy);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                    
                    if (matcher.group("startDateFormatOne") != null) {
                        startDate = matcher.group("startDateFormatOne").trim();
                    } else if (matcher.group("startDateFormatTwo") != null) {
                        startDate = matcher.group("startDateFormatTwo").trim();
                    } else if (matcher.group("startDateFormatThree") != null) {
                        startDate = matcher.group("startDateFormatThree").trim();
                    } else if (matcher.group("endDateFormatOne") != null) {
                        endDate = matcher.group("endDateFormatOne").trim(); 
                    } else if (matcher.group("endDateFormatTwo") != null) {
                        endDate = matcher.group("endDateFormatTwo").trim();
                    } else if (matcher.group("endDateFormatThree") != null) {
                        endDate = matcher.group("endDateFormatThree").trim();
                    } 
                }
                
                // second keyword is part of the name
                if ((startDate != null && !DateTime.isStringValidDate(startDate)) || 
                        endDate != null && !DateTime.isStringValidDate(endDate)) {
                    // reparse
                    startDate = null;
                    endDate = null;
                    regexCopy = regexOne;
                    numberOfAdditionalKeywords++;
                    for (int i = 0; i < numberOfAdditionalKeywords; i++) {
                        regexCopy += REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
                    }
                    regexCopy += REGEX_CLOSE_BRACE + REGEX_RECURRENCE_AND_PRIORITY + REGEX_CLOSE_BRACE;
                    pattern = Pattern.compile(regexCopy);
                    matcher = pattern.matcher(argsTrimmed);

                    if (!matcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                }
                
                if (isValidEndDate) {
                    if (matcher.group("endDateFormatFour") != null) {
                        endDate = matcher.group("endDateFormatFour").trim(); 
                    } else if (matcher.group("endDateFormatFive") != null) {
                        endDate = matcher.group("endDateFormatFive").trim();
                    } else if (matcher.group("endDateFormatSix") != null) {
                        endDate = matcher.group("endDateFormatSix").trim();
                    } 
                }
            
                taskName = matcher.group("taskName").trim();
            
                if (matcher.group("recurrenceRate") != null) {
                    String recurrenceString = matcher.group("recurrenceRate");
                    final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);
                
                    if (!recurrenceMatcher.matches()) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    } 
                
                    if (recurrenceMatcher.group("rate") != null) {
                        rate = recurrenceMatcher.group("rate").trim();
                    }
                
                    assert recurrenceMatcher.group("timePeriod") != null;
                    timePeriod = recurrenceMatcher.group("timePeriod").trim();
                }

                if (matcher.group("priority") != null) {
                    priority = matcher.group("priority").trim();
                } else {
                    priority = "medium";
                }
                return new AddCommand(taskName, startDate, endDate, rate, timePeriod, priority);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }
    }

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
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
		
    	 //TODO parse the index and args
    	 int index = 0;
    	 
    	 args = args.trim();
    	 System.out.println(args);
    	 
    	 String[] parts = args.split(" ");
    	 String indexNum = parts[0];

    	 if(parts.length == 1){
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    	 }
    	 
    	 index = Integer.parseInt(indexNum);
    	 
    	 args = args.substring(2);
    	 
    	 //TODO
    	 //Update parser to make NAME field optional.
    	 final Matcher matcher = EDIT_ARGS_FORMAT.matcher(args.trim());
         
    	 String taskName = null;
         String startDate = null;
         String endDate = null;
         String recurrenceRate = null;
         String timePeriod = null;
         String priority = null;  
         String resetField = null;
         
         if (!matcher.matches()) {
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
         }
         
         try {
        	 
        	 if(matcher.group("taskName") != null){
        		 taskName = matcher.group("taskName");
        	 }
        	 
             if (matcher.group("startDateFormatOne") != null) {
                 startDate = matcher.group("startDateFormatOne");
             } else if (matcher.group("startDateFormatTwo") != null) {
                 startDate = matcher.group("startDateFormatTwo");
             } else if (matcher.group("startDateFormatThree") != null) {
                 startDate = matcher.group("startDateFormatThree");
             }
             
             if (matcher.group("endDateFormatOne") != null) {
                 endDate = matcher.group("endDateFormatOne"); 
             } else if (matcher.group("endDateFormatTwo") != null) {
                 endDate = matcher.group("endDateFormatTwo");
             } else if (matcher.group("endDateFormatThree") != null) {
                 endDate = matcher.group("endDateFormatThree");
             }  

             if (matcher.group("recurrenceRate") != null) {
                 String recurrenceString = matcher.group("recurrenceRate");
                 final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);
                 
                 //TODO: Won't reach here actually
                 if (!recurrenceMatcher.matches()) {
                     return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                 } 
                 
                 if (recurrenceMatcher.group("rate") != null) {
                     recurrenceRate = recurrenceMatcher.group("rate");
                 }
                 
                 timePeriod = recurrenceMatcher.group("timePeriod");
             }  

             if (matcher.group("priority") != null) {
                 priority = matcher.group("priority");
             }
             
             if (matcher.group("resetField") != null) {
            	 resetField = matcher.group("resetField");
             }
             
             System.out.println("Index = " + index);
             System.out.println("Taskname = " + taskName);
             System.out.println("StartDate = " + startDate);
             System.out.println("EndDate = " + endDate);
             System.out.println("Rate = " + recurrenceRate);
             System.out.println("TimePeiod = " + timePeriod);
             System.out.println("Reset = " + resetField);
             System.out.println("Priority = " + priority);
             
             return new EditCommand(index, taskName, startDate, endDate, recurrenceRate, timePeriod, priority, resetField);
         
         } catch (IllegalValueException ive) {
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
         }	
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<List<Integer>> indexes = parseIndexes(args);
        if(!indexes.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(indexes.get());
    }
    
    /**
     * Parses arguments in the context of the done person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {

        Optional<List<Integer>> indexes = parseIndexes(args);
        if(!indexes.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(indexes.get());
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
     * Parses arguments in the context of the select person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args) {
        Boolean isListDoneCommand = false;
        
        if (args != null && args.trim().toLowerCase().equals("done")) {
            isListDoneCommand = true;
        }

        return new ListCommand(isListDoneCommand);
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
     * Returns the specified indexes in the {@code command} IF any positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<List<Integer>> parseIndexes(String command) {
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String indexes = matcher.group("targetIndex");
        String[] indexesArray = indexes.split(" ");
        List<Integer> indexesToHandle = new ArrayList<Integer>();
        for (String index: indexesArray) {
            if (StringUtil.isUnsignedInteger(index)) {
                indexesToHandle.add(Integer.parseInt(index));
            }
        }
        if (indexesToHandle.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(indexesToHandle);

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
    
    /**
     * Parses an incomplete user input into a list of Strings (which are the command usages) to determine tooltips to show the user.
     * 
     * @param userInput user input string
     * @return a list of Strings for tooltips
     */
    public List<String> parseIncompleteCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        ArrayList<String> toolTips = new ArrayList<String>();
        if (!matcher.matches()) {
            //TODO: make this thing make sense
            toolTips.add(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            return toolTips;
        }

        final String commandWord = matcher.group("commandWord");
        // reserve this maybe can use next time to match more precisely
        // final String arguments = matcher.group("arguments");
        updateMatchedCommands(toolTips, commandWord);
        if (toolTips.isEmpty()){
            toolTips.add(AddCommand.TOOL_TIP);
        }
        return toolTips;      
    }

    /**
     * Updates the list of toolTips by checking if the user's input command word is a substring of the actual command word.
     * @param toolTips list of tooltips
     * @param commandWord the user input command word
     */
    // TODO: apply software eng to this shit
    private void updateMatchedCommands(List<String> toolTips, final String commandWord) {
        if (StringUtil.isSubstring(AddCommand.COMMAND_WORD, commandWord)){
            toolTips.add(AddCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(SelectCommand.COMMAND_WORD, commandWord)){
            toolTips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(DeleteCommand.COMMAND_WORD, commandWord)){
            toolTips.add(DeleteCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(ClearCommand.COMMAND_WORD, commandWord)){
            toolTips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(FindCommand.COMMAND_WORD, commandWord)){
            toolTips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(ListCommand.COMMAND_WORD, commandWord)){
            toolTips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(ExitCommand.COMMAND_WORD, commandWord)){
            toolTips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(HelpCommand.COMMAND_WORD, commandWord)){
            toolTips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstring(EditCommand.COMMAND_WORD, commandWord)){
            toolTips.add(EditCommand.TOOL_TIP);
        }
    }
}