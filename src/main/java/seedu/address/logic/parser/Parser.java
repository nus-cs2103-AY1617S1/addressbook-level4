package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    //TODO: Parser failing test case "add complete stuff by mon, at myhouse, from 8am" because of ", at..."
    private static final Pattern TASK_ARGS_FORMAT = Pattern.compile("(?i:(?<taskName>.*?)"
                                                        +"(?:"
                                                        +"(?:, by (?<endDateFormatOne>.*?))"
                                                        +"|(?:, from (?<startDateFormatOne>.*?))"
                                                        +"|(?:, at (?<startDateFormatTwo>.*?))"
                                                        +"|(?:, start (?<startDateFormatThree>.*?))"
                                                        +")?"
                                                        +"(?:"
                                                        +"(?: to (?<endDateFormatTwo>.*?))?"
                                                        +"(?: end (?<endDateFormatThree>.*?))?"
                                                        +")?"
                                                        +"(?: repeat every (?<recurrenceRate>.*?))?"
                                                        +"(?: -(?<priority>.*?))?)");

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
            											+"(?:-(?<priority>.*?))?)");
    
    private static final Pattern RECURRENCE_RATE_ARGS_FORMAT = Pattern.compile("(?<rate>\\d+)?(?<timePeriod>.*?)");

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

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

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
        final Matcher matcher = TASK_ARGS_FORMAT.matcher(args.trim());

        String taskName = null;
        String startDate = null;
        String endDate = null;
        String recurrenceRate = null;
        String timePeriod = null;
        String priority = null;  
        
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {
            taskName = matcher.group("taskName");
            
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
                
                // SHOULDN't WE DO THE CHECK HERE? PARSER SHOULD RETURN INCORRECT CCOMMAND IF INVALID.
                switch (priority){
                    case ("l"): case ("low"):
                    case ("m"): case ("med"): case("medium"):
                    case ("h"): case ("high"):
                        break;
                    default:
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            } else {
                priority = "medium";
            }
            return new AddCommand(taskName, startDate, endDate, recurrenceRate, timePeriod, priority);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
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
             
             System.out.println(index + " " + taskName + " " + startDate + " " + endDate + " " + recurrenceRate + " " + timePeriod + " " + priority );
             return new EditCommand(index, taskName, startDate, endDate, recurrenceRate, timePeriod, priority);
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

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
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