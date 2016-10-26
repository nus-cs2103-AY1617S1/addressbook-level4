package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.*;

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
            Pattern.compile("^((?<operand>(st|ed|pr|t)/)\\s*)?(?<keywords>\\S+(?:\\s+\\S+)*)$"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<description>[^/]+)"
                    + "( pr/)?(?<priority>([^/]+)?)"
                    + "( st/)?(?<timeStart>([^/]+)?)"
                    + "( ed/)?(?<timeEnd>([^/]+)?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern TAG_ADD_ARGS_FORMAT =
    		Pattern.compile("(?<targetIndex>.+)" + "(?<tag>.*)");

    private static final Pattern TAG_DELETE_ARGS_FORMAT =
    		Pattern.compile("(?<targetIndex>.+)" + "(?<tag>.*)");

    private static final Pattern TASK_UPDATE_ARGS_FORMAT =
    		Pattern.compile("(?<targetIndex>[^/]+)"
    				+ ("( des/)?(?<description>([^/]+)?)"
    	            + "( pr/)?(?<priority>([^/]+)?)"
    	            + "( st/)?(?<timeStart>([^/]+)?)"
    	            + "( ed/)?(?<timeEnd>([^/]+)?)"));

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws IllegalValueException
     */
    public Command parseCommand(String userInput) throws IllegalValueException {
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

        case AddTagCommand.COMMAND_WORD:
        	return prepareAddTag(arguments);

        case DeleteTagCommand.COMMAND_WORD:
        	return prepareDeleteTag(arguments);

        case UpdateCommand.COMMAND_WORD:
        	return prepareUpdate(arguments);

        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);

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
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("description"),
                    matcher.group("priority"),
                    matcher.group("timeStart"),
                    matcher.group("timeEnd"),
                    getTagsFromArgs(matcher.group("tagArguments"))
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
     * Parses arguments in the context of the complete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new CompleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
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
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
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
        final String operand = (matcher.group("operand") == null) ? "" : matcher.group("operand");
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(operand, keywordSet);
    }

    private Command prepareAddTag(String args){
    	final Matcher matcher = TAG_ADD_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagCommand.MESSAGE_USAGE));
        }


		String[] command = args.trim().split(" ");
		if(command.length!=2)
			return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));

        Optional<Integer> index = parseIndex(command[0]);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

		try {
			return new AddTagCommand(index.get(),command[1]);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(e.getMessage());
		}
    }

    private Command prepareDeleteTag(String args){
    	final Matcher matcher = TAG_DELETE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTagCommand.MESSAGE_USAGE));
        }


		String[] command = args.trim().split(" ");
		if(command.length!=2)
			return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));

        Optional<Integer> index = parseIndex(command[0]);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

		try {
			return new DeleteTagCommand(index.get(),command[1]);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(e.getMessage());
		}
    }

    private Command prepareUpdate(String args) throws IllegalValueException{
        final Matcher matcher = TASK_UPDATE_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        try {
            return new UpdateCommand(
            		matcher.group("targetIndex"),
            		matcher.group("description"),
                    matcher.group("priority"),
                    matcher.group("timeStart"),
                    matcher.group("timeEnd")
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }



}
