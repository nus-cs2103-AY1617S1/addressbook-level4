package seedu.task.logic.parser;

import seedu.task.logic.commands.*;
import seedu.task.commons.util.StringUtil;
import seedu.task.commons.exceptions.IllegalValueException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import com.joestelmach.natty.*;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class CommandParser {
	
    /**
     * Used for initial separation of command word and args.
     */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
	
	private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
  //@@author A0147944U
    private static final Pattern NATURAL_ARGS_FORMAT = 
    		Pattern.compile("(?<name>[^#/:.]+)" + "(?<tagArguments>(?: #[^/]+)*)");
    
    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_TIME = 
    		Pattern.compile("(?<name>[^:.]+)" + "(at |from )(?<startTime>[^/#]+)" + "(?<tagArguments>(?: #[^/]+)*)");
    
    private static final Pattern NATURAL_ARGS_FORMAT_WITH_DEADLINE = 
            Pattern.compile("(?<name>[^:.]+)" + "by (?<deadline>[^/#]+)" + "(?<tagArguments>(?: #[^/]+)*)");
    
    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME = 
    		Pattern.compile("(?<name>[^:.]+)" + "(at |from )(?<startTime>[^/t]+)" + "to (?<endTime>[^/#]+)" + "(?<tagArguments>(?: #[^/]+)*)");

    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_DEADLINE = 
            Pattern.compile("(?<name>[^:.]+)" + "(at |from )(?<startTime>[^/b]+)" + "by (?<deadline>[^/#]+)" + "(?<tagArguments>(?: #[^/]+)*)");
    
    private static final Pattern NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME_AND_DEADLINE = 
            Pattern.compile("(?<name>[^:.]+)" + "(at |from )(?<startTime>[^/t]+)" + "to (?<endTime>[^/b]+)" + "by (?<deadline>[^/#]+)" + "(?<tagArguments>(?: #[^/]+)*)");
  //@@author
    
    public static final Pattern EDIT_TASK_DATA_ARGS_FORMAT_NATURAL = 
    					Pattern.compile("(?<targetIndex>.)"
    							+ " (?<content>.*)");
  //@@author A0147944U
    public static final Pattern DIRECTORY_ARGS_FORMAT = 
            Pattern.compile("(?<directory>[^<>|]+)");
  //@@author
    
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
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
          //@@author A0147944U            
        case DirectoryCommand.COMMAND_WORD:
            return prepareDirectory(arguments);
            
        case BackupCommand.COMMAND_WORD:
            return prepareBackup(arguments);
          //@@author
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
        
        final Matcher matcherNatural = NATURAL_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherStart = NATURAL_ARGS_FORMAT_WITH_START_TIME.matcher(args.trim());
        final Matcher matcherDeadline = NATURAL_ARGS_FORMAT_WITH_DEADLINE.matcher(args.trim());
        final Matcher matcherStartEnd = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME.matcher(args.trim());
        final Matcher matcherStartDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_DEADLINE.matcher(args.trim());
        final Matcher matcherStartEndDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME_AND_DEADLINE.matcher(args.trim());
        
        // Validate arg string format
        if (!matcherNatural.matches() && !matcherStart.matches() && !matcherDeadline.matches() && !matcherStartEnd.matches() && !matcherStartDeadline.matches() && !matcherStartEndDeadline.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        else if(matcherNatural.matches()){
        	try{
        		return createCommandNatural(matcherNatural.group("name"),
            			"no start time",
            			"no end time",
            			"no deadline",
            			getTagsFromArgs(matcherNatural.group("tagArguments"))
            	);
        	}catch(IllegalValueException ive){
        		return new IncorrectCommand(ive.getMessage());
        	}
        	
        }
        else if(matcherStartEndDeadline.matches()){
            try{
                return createCommandStartEndDeadline(
                        matcherStartEndDeadline.group("name"),
                        matcherStartEndDeadline.group("startTime"),
                        matcherStartEndDeadline.group("endTime"),
                        matcherStartEndDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartEndDeadline.group("tagArguments"))
                        );
                        
            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if(matcherDeadline.matches()){
            try{
                return createCommandDeadline(
                        matcherDeadline.group("name"),
                        "no start time",
                        "no end time",
                        matcherDeadline.group("deadline"),
                        getTagsFromArgs(matcherDeadline.group("tagArguments"))
                        );
                        
            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        //add do hw from 3:00pm to 4:00pm by 5:00pm
        else if(matcherStartEnd.matches()){
        	try{
        		return createCommandStartEnd(
        				matcherStartEnd.group("name"),
        				matcherStartEnd.group("startTime"),
        				matcherStartEnd.group("endTime"),
        				"no deadline",
        				getTagsFromArgs(matcherStartEnd.group("tagArguments"))
        				);
        				
        	}catch(IllegalValueException i){
        		return new IncorrectCommand(i.getMessage());
        	}
        }
        else if(matcherStartDeadline.matches()){
            try{
                return createCommandStartDeadline(
                        matcherStartDeadline.group("name"),
                        matcherStartDeadline.group("startTime"),
                        "no end time",
                        matcherStartDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartDeadline.group("tagArguments"))
                        );
                        
            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if(matcherStart.matches()){
            try{
                return createCommandStart(
                        matcherStart.group("name"),
                        matcherStart.group("startTime"),
                        "no end time",
                        "no deadline",
                        getTagsFromArgs(matcherStart.group("tagArguments"))
                        );
                        
            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else { return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)); }
        
}
    
     private Command createCommandNatural(String name, String startTime, String endTime, String deadline, Set<String> tags){
    	 try{
    		 return new AddCommand(name, startTime, endTime, deadline, tags);
    	 }catch(IllegalValueException i){
     		return new IncorrectCommand(i.getMessage());
     	}
    	 
     }
     
     private Command createCommandStart(String name, String startTime, String endTime, String deadline, Set<String> tags){
    	 try{
    		 return new AddCommand(name, startTime, endTime, deadline, tags);
    	 }catch(IllegalValueException i){
     		return new IncorrectCommand(i.getMessage());
     	}
    	 
     }
     
     private Command createCommandDeadline(String name, String startTime, String endTime, String deadline, Set<String> tags){
         try{
             return new AddCommand(name, startTime, endTime, deadline, tags);
         }catch(IllegalValueException i){
            return new IncorrectCommand(i.getMessage());
        }
         
     }
     
     private Command createCommandStartEnd(String name, String startTime, String endTime, String deadline, Set<String> tags){
    	 try{
    		 return new AddCommand(name, startTime, endTime, deadline, tags);
    	 }catch(IllegalValueException i){
     		return new IncorrectCommand(i.getMessage());
     	}
    	 
    	 
    	 
     }
     private Command createCommandStartDeadline(String name, String startTime, String endTime, String deadline, Set<String> tags){
         try{
             return new AddCommand(name, startTime, endTime, deadline, tags);
         }catch(IllegalValueException i){
            return new IncorrectCommand(i.getMessage());
        }
     }
     
     private Command createCommandStartEndDeadline(String name, String startTime, String endTime, String deadline, Set<String> tags){
         try{
             return new AddCommand(name, startTime, endTime, deadline, tags);
         }catch(IllegalValueException i){
            return new IncorrectCommand(i.getMessage());
        }
     }


    /**
     * Extracts the new task tags from the add command's tag arguments string.
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
    
    
    private Command prepareEdit(String args){
        final Matcher matcher = EDIT_TASK_DATA_ARGS_FORMAT_NATURAL.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        final Matcher matcherNatural = NATURAL_ARGS_FORMAT.matcher(matcher.group("content"));
        final Matcher matcherStart = NATURAL_ARGS_FORMAT_WITH_START_TIME.matcher(matcher.group("content"));
        final Matcher matcherDeadline = NATURAL_ARGS_FORMAT_WITH_DEADLINE.matcher(matcher.group("content"));
        final Matcher matcherStartEnd = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME.matcher(matcher.group("content"));
        final Matcher matcherStartDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_DEADLINE.matcher(matcher.group("content"));
        final Matcher matcherStartEndDeadline = NATURAL_ARGS_FORMAT_WITH_START_AND_END_TIME_AND_DEADLINE.matcher(matcher.group("content"));
        if (!matcherNatural.matches() && !matcherStart.matches() && !matcherStartEnd.matches() && !matcherStartDeadline.matches() && !matcherStartEndDeadline.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if(matcherNatural.matches()){
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherNatural.group("name"),
                        "now",
                        "no endtime",
                        "no deadline",
                        getTagsFromArgs(matcherNatural.group("tagArguments"))
                        );
            }catch(IllegalValueException ive){
                return new IncorrectCommand(ive.getMessage());
            }

        }
        else if(matcherStart.matches() && !(Pattern.compile("at.*by").matcher(args).find())){
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherStart.group("name"),
                        matcherStart.group("startTime"),
                        "no endtime",
                        "no deadline",
                        getTagsFromArgs(matcherStart.group("tagArguments"))
                        );

            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        
        else if(matcherDeadline.matches()){
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherStart.group("name"),
                        "now",
                        "no endtime",
                        matcherStart.group("deadline"),
                        getTagsFromArgs(matcherStart.group("tagArguments"))
                        );

            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if(matcherStartEnd.matches()  && !(Pattern.compile("from.*to.*by").matcher(args).find())){
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherStartEnd.group("name"),
                        matcherStartEnd.group("startTime"),
                        matcherStartEnd.group("endTime"),
                        "no deadline",
                        getTagsFromArgs(matcherStartEnd.group("tagArguments"))
                        );

            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if(matcherStartDeadline.matches() && (Pattern.compile("at.*by").matcher(args).find())){
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherStartDeadline.group("name"),
                        matcherStartDeadline.group("startTime"),
                        "no endtime",
                        matcherStartDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartDeadline.group("tagArguments"))
                        );

            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else if(matcherStartEndDeadline.matches() && (Pattern.compile("from.*to.*by").matcher(args).find())) {
            try{
                return new EditCommand(Integer.parseInt(matcher.group("targetIndex")),
                        matcherStartEndDeadline.group("name"),
                        matcherStartEndDeadline.group("startTime"),
                        matcherStartEndDeadline.group("endTime"),
                        matcherStartEndDeadline.group("deadline"),
                        getTagsFromArgs(matcherStartEndDeadline.group("tagArguments"))
                        );

            }catch(IllegalValueException i){
                return new IncorrectCommand(i.getMessage());
            }
        }
        else { return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)); }


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
     * Parses arguments in the context of the find task command.
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
    
  //@@author A0147944U
    /**
     * Parses arguments in the context of the directory command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDirectory(String args) {
        final Matcher matcher = DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirectoryCommand.MESSAGE_USAGE));
        }
        return new DirectoryCommand(
                matcher.group("directory")
        );
    }
    
    /**
    * Parses arguments in the context of the backup command.
    *
    * @param args full command args string
    * @return the prepared command
    */
    private Command prepareBackup(String args) {
        final Matcher matcher = DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        }
        return new BackupCommand(
                matcher.group("directory")
                );
    }
  //@@author
}