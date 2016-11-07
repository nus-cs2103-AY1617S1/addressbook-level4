package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.File;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
            		+ "(?<startline>(?: s/[^/]+)*)"
                    + "(?<deadline>(?: d/[^/]+)*)"
                    + "(?<priority>(?: p/[^/]+)*)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    //@@author A0141812R
    private static final Pattern EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>[^/]+)"
            		+ "(?<name>(?: n/[^/]+)*)" //only name is compulsory
            		+ "(?<startline>(?: s/[^/]+)*)"
            		+ "(?<deadline>(?: d/[^/]+)*)"
                    + "(?<priority>(?: p/[^/]+)*)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
 	private static final Pattern REPEAT_DATE_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
		Pattern.compile("(?<targetIndex>.+)"
				+ " (?<timeInterval>[^/]+)");
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException 
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
	
        case AddCommand.COMMAND_WORD_2:
            return prepareAdd(arguments);
		
	case CompleteCommand.COMMAND_WORD:
	    try {
	   	return prepareComplete(arguments);
	    } catch (IllegalValueException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	
        case CompleteCommand.COMMAND_WORD_2:
	    try {
	   	return prepareComplete(arguments);
	    } catch (IllegalValueException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);
        
        case ListtagCommand.COMMAND_WORD:
        	return prepareListtag(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case DeleteCommand.COMMAND_WORD_2:
            return prepareDelete(arguments);
        
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case FindCommand.COMMAND_WORD_2:
            return prepareFind(arguments);
			
        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case EditCommand.COMMAND_WORD:
        	return prepareEdit(arguments);
        	
        case ClashCommand.COMMAND_WORD:
        	return new ClashCommand();
	
        case RepeatCommand.COMMAND_WORD:
        	return prepareRepeat(arguments);
        	
        case UpdateCommand.COMMAND_WORD:
        	return new UpdateCommand();
        
        case UndoCommand.COMMAND_WORD:
        	 return new UndoCommand();
        	 
        case RevertCommand.COMMAND_WORD:
        	 return new RevertCommand();
        	 
        case ScrollCommand.COMMAND_WORD:
        	return new ScrollCommand(arguments);
        	
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);
        
        case LoadCommand.COMMAND_WORD:
            return prepareLoad(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException 
     */
    private Command prepareAdd(String args) throws ParseException{
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    getStartlineFromArgs(matcher.group("startline")),
                    getDeadlinesFromArgs(matcher.group("deadline")),
                    getPriorityFromArgs(matcher.group("priority")),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Checks if user inputs startline
     * if not returns date from start of common era
     * if no time, time is set to 23:59 by default
     * @param args
     * @return args
     */
    private String getStartlineFromArgs(String args){
    	if(args.isEmpty()){
    		return null;
    	}
    	args = args.replaceFirst(" s/", "");
    	String[] strArr = args.split("\\s+");
    	if(strArr.length == 1){
    		return args + " " + "00:00";
    	}
    	return args;    	
    }

    private String getDeadlinesFromArgs(String args) {
    	if(args.isEmpty()){
    		return null;
    	}
    	args = args.replaceFirst(" d/", "");
    	String[] strArr = args.split("\\s+");
    	if(strArr.length == 1){
    		return args + " " + "23:59";
    	}
    	return args; 
	}
    
  //@@ author A0141812R
    private String getPriorityFromArgs(String args) {
        if (args.isEmpty()) {
            return "0";
        }
        args = args.replaceFirst(" p/", "");

        return args;
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
				
	private Command prepareComplete(String args) throws IllegalValueException {
  		  
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
     		return new IncorrectCommand(
	     		String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
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
     * Parses arguments in the context of the Group task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    
    
    private Command prepareListtag(String args) {
        final String keyword = args.trim();
    	   
        return new ListtagCommand(keyword);
    }
    
    /**
     * Parses arguments in the context of the edit task command
     * 
     * @param args full command args String
     * @return the edit command
     * @throws ParseException
     */
    private Command prepareEdit(String args) throws ParseException{
    	System.out.println(args.trim());
    	final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        //@@ author A0141812R
        try {
            return new EditCommand(
            		matcher.group("targetIndex"),
                    getNameFromArgs(matcher.group("name")),
                    getStartlineFromArgs(matcher.group("startline")),
                    getDeadlinesFromArgs(matcher.group("deadline")),
                    getPriorityFromArgs(matcher.group("priority")),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private String getNameFromArgs(String args) {
    	if (args.isEmpty()) {
            return "0";
        }
        args = args.replaceFirst(" n/", "");

        return args;
    }
    
    /**
     * Parses arguments in the context of the repeat task command
     * @param String args
     * @return the repeat command
     */
    private Command prepareRepeat(String args){
    	 final Matcher matcher = REPEAT_DATE_ARGS_FORMAT.matcher(args.trim());
    	 // Validate arg string format
    	 if(!matcher.matches()) {
    		 return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepeatCommand.MESSAGE_USAGE));
    	 }
    	 try{
    		 return new RepeatCommand(
    				 parseIndex(matcher.group("targetIndex")).get(),
    				 matcher.group("timeInterval")
    				 );
    	 } catch (IllegalValueException ive) {
    		 return new IncorrectCommand(ive.getMessage());
    	 }

    }
    
    private Command prepareSave(String args) throws ParseException{
       
        
            return new SaveCommand(args.trim());
    }
    
    private Command prepareLoad(String args) throws ParseException{
        
        
        return new LoadCommand(args.trim());
    }
    
    /**
     * Parses the arguments in the context of the list keyword command.
     * @param args
     * @return the list command.
     */
    private Command prepareList(String args) {
    	if(!args.isEmpty()) {
	    	final String keyword = args.trim();
	    	return new ListCommand(keyword);
    	} else {
    		return new ListCommand("");
    	}
    }


}
