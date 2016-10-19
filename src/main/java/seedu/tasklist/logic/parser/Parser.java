package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tasklist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.*;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    //private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

    private static final Pattern TASK_DATA_ARGS_FORMAT = Pattern.compile(
            "(?<name>([^/](?<! (at|from|to|by) ))*)" + "((?: (at|from) )(?<start>(([^;](?<! (to|by) ))|(\\[^/]))+))?"
                    + "((?: (to|by) )(?<end>(([^;](?<! p/))|(\\[^/]))+))?" + "((?: p/)(?<priority>[^/]+))?"
                    + "(?<tagArguments>(?: t/[^;]+)*)"
                    );
    
    private static final Pattern TASK_UPDATE_ARGS_FORMAT = Pattern.compile( "(?<index>\\d+)"
    		+ "((?: )(?<name>([^/](?<! (at|from|to|by) ))*))?" + "((?: (at|from) )(?<start>(([^;](?<! (to|by) ))|(\\[^/]))+))?"
            + "((?: (to|by) )(?<end>(([^;](?<! p/))|(\\[^/]))+))?" + "((?: p/)(?<priority>[^/]+))?"
            + "(?<tagArguments>(?: t/[^;]+)*)"
            );
    
    
    private static final Pattern DELETE_COMPLETE_ARGS_PARSER = Pattern
            .compile("(?<index>(\\d+)?)|" + "(?<searchString>[^/]+)");

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SetStorageCommand.COMMAND_WORD:
        	return prepareFilePath(arguments);
        	
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);
        
        case ShowCommand.COMMAND_WORD:
        	return prepareShow(arguments);
        	
        case UndoCommand.COMMAND_WORD:
            return prepareUndo();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command prepareUndo() {
        return new UndoCommand();
    }

    private Command prepareFilePath(String args){
    	if(args!=null){
    		args= args.trim();
    		return new SetStorageCommand(args);
    	}
    	else
    		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStorageCommand.MESSAGE_USAGE));
    }
    
    private Command prepareUpdate(String args) {
        args = args.trim();
        final Matcher matcher = TASK_UPDATE_ARGS_FORMAT.matcher(args);
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        } else {
        	int targetIndex = Integer.valueOf(matcher.group("index"));
            String taskDetails = (matcher.group("name") == null) ? null : matcher.group("name").replace("\\", "");
            String startTime = (matcher.group("start") == null) ? null : matcher.group("start");
            String endTime = (matcher.group("end") == null) ? null : matcher.group("end");
            String priority = (matcher.group("priority") == null) ? null : matcher.group("priority");
            UniqueTagList utags = null;
            try {
                utags = new UniqueTagList(new Tag("Hi"));
            } catch (DuplicateTagException e) {
                e.printStackTrace();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            
            try {
                return new UpdateCommand(targetIndex, taskDetails, startTime, endTime, priority, utags);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }

    }

    private Tag[] getTags(String[] tagList) {
        Tag[] tags = new Tag[tagList.length];
        for(int i=0; i<tagList.length; i++){
            try {
                tags[i] = new Tag(tagList[i]);
            } 
            catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        return tags;
    }

    private Command prepareDone(String args) {
        final Matcher matcher = DELETE_COMPLETE_ARGS_PARSER.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } else if (matcher.group("index") != null) {
            return new DoneCommand(Integer.valueOf(matcher.group("index")));
        } else if (matcher.group("searchString") != null) {
            return new DoneCommand('*' + matcher.group("searchString") + '*');
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher taskMatcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!taskMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            String startTime = (taskMatcher.group("start") == null) ? "" : taskMatcher.group("start");
            String endTime = (taskMatcher.group("end") == null) ? "" : taskMatcher.group("end");
        
            try {
                return new AddCommand(taskMatcher.group("name").replace("\\", ""), startTime, endTime,
                        taskMatcher.group("priority"), getTagsFromArgs(taskMatcher.group("tagArguments")));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
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
        final Matcher matcher = DELETE_COMPLETE_ARGS_PARSER.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } else if (matcher.group("index") != null) {
            return new DeleteCommand(Integer.valueOf(matcher.group("index")));
        } else if (matcher.group("searchString") != null) {
            return new DeleteCommand('*' + matcher.group("searchString") + '*');
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
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
        /*
         * // keywords delimited by whitespace final String[] keywords =
         * matcher.group("keywords").split("\\s+"); final Set<String> keywordSet
         * = new HashSet<>(Arrays.asList(keywords)); return new
         * FindCommand(keywordSet);
         */
        args = args.trim();
        args = '*' + args + '*';
        return new FindCommand(args);
    }
    
    /**
     * Parses arguments in the context of the show task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareShow(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }
        args = args.trim();

        return new ShowCommand(args);
    }

}