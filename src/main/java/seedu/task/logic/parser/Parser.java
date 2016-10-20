package seedu.task.logic.parser;

import seedu.task.logic.commands.*;
import seedu.task.commons.util.StringUtil;
import seedu.task.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startTime>[^/]+)"
                    + " e/(?<endTime>[^/]+)"
                    + " l/(?<location>[^/]+)"
                    + "(?<tagArguments>(?: #/[^/]+)*)"); // variable number of tags

    public static final Pattern EDIT_PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
    		             Pattern.compile("(?<targetIndex>.)"
    		             		+ "(?<name>[^/]+)"
    		             		+ " s/(?<startTime>[^/]+)"
    		                    + " e/(?<endTime>[^/]+)"
    		                    + " l/(?<location>[^/]+)"    		                     
    		                  + "(?<tagArguments>(?: #/[^/]+)*)"); // variable number of tags
    
    public static final Pattern DIRECTORY_ARGS_FORMAT = 
            Pattern.compile("(?<directory>[^<>|]+)");
    
    private static final Pattern TASK_DATA_ARGS_FOMAT = 
    		Pattern.compile("(?<name>[^/]+)");
    
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
            
        case DirectoryCommand.COMMAND_WORD:
            return prepareDirectory(arguments);
            
        case BackupCommand.COMMAND_WORD:
            return prepareBackup(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
 
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
            		matcher.group("name"),
                    matcher.group("startTime"),
                    matcher.group("endTime"),
                    matcher.group("location"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
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
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" #/", "").split(" #/"));
        return new HashSet<>(tagStrings);
    }
    
    
    private Command prepareEdit(String args){
    	        final Matcher matcher = EDIT_PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
    	         // Validate arg string format
    	         if (!matcher.matches()) {
    	             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    	         }
    	         try {
    	             return new EditCommand(
    	             		Integer.parseInt(matcher.group("targetIndex")),
    	             		matcher.group("name"),
    	                    matcher.group("startTime"),
    	                    matcher.group("endTime"),
    	                    matcher.group("location"),
    	                
    	 
    	                     getTagsFromArgs(matcher.group("tagArguments"))
    	             );
    	         } catch (IllegalValueException ive) {
    	             return new IncorrectCommand(ive.getMessage());
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

}