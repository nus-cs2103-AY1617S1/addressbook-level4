package seedu.menion.logic.parser;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.StringUtil;
import seedu.menion.logic.commands.*;
import seedu.menion.model.activity.Activity;

import static seedu.menion.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.menion.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class ActivityParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ACTIVITY_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    public ActivityParser() {}

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
            System.out.println("here");
            System.out.println("Arguments = " + arguments);
        	//return new ListCommand();
            
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();
        	
        case RedoCommand.COMMAND_WORD:
        	return new RedoCommand();
        	
        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);
            
        case UnCompleteCommand.COMMAND_WORD:
            return prepareUnComplete(arguments);
        
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
   /* 
    private Command prepareList(String args){
    	
    	
    	
    }*/
    private Command prepareComplete(String args) {

        String[] splited = args.split("\\s+");
        assert(splited.length == 3); // Should only contain a space, Activity Type and Index
        boolean isValidType = false; // Checks that the activity type is of valid type
        String activityType = splited[1];

        if (activityType.equals(Activity.FLOATING_TASK_TYPE) || activityType.equals(Activity.TASK_TYPE) || activityType.equals(Activity.EVENT_TYPE)) {
            isValidType = true;
        }
        if (!isValidType) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = Optional.of(Integer.valueOf(splited[2]));

        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        
        return new CompleteCommand(splited);
    }
    
    private Command prepareUnComplete(String args) {

        String[] splited = args.split("\\s+");
        assert(splited.length == 3); // Should only contain a space, Activity Type and Index
        boolean isValidType = false; // Checks that the activity type is of valid type
        String activityType = splited[1];

        if (activityType.equals(Activity.FLOATING_TASK_TYPE) || activityType.equals(Activity.TASK_TYPE) || activityType.equals(Activity.EVENT_TYPE)) {
            isValidType = true;
        }
        if (!isValidType) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = Optional.of(Integer.valueOf(splited[2]));

        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new UnCompleteCommand(splited);
    }
    
    /**
     * @author Marx Low A0139164A
     * @param args
     * @return
     */
    private Command prepareEdit(String args) {

        String[] splited = args.split("\\s+");
        boolean isValidType = false; // Checks that the activity type is of valid type
        
        String activityType = splited[1];
        
        // Checks for valid activtiyType
        if (activityType.equals(Activity.FLOATING_TASK_TYPE) || activityType.equals(Activity.TASK_TYPE) || activityType.equals(Activity.EVENT_TYPE)) {
            isValidType = true;
        }
        if (!isValidType) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // Checks for valid index 
        Optional<Integer> index = Optional.of(Integer.valueOf(splited[2]));
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        // Checks for valid parameters.
        // Assumes they are valid
        
        return new EditCommand(splited);
    }
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
    	
        ArrayList<String> details = AddParser.parseCommand(args);
        if (details.isEmpty()){
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {
            return new AddCommand(details);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }


    /**
     * Parses arguments in the context of the delete activity command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        ArrayList<String> activityType = DeleteParser.parseArguments(args);
        if(activityType.isEmpty()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        Integer index = Integer.valueOf(activityType.get(1));
        if(index == null){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(activityType.get(0), index);
    }
    

    /**
     * Parses arguments in the context of the select activity command.
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
        final Matcher matcher = ACTIVITY_INDEX_ARGS_FORMAT.matcher(command.trim());
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
     * Parses arguments in the context of the find activity command.
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