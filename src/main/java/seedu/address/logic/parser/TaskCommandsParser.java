package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.logic.commands.taskcommands.FindTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

public class TaskCommandsParser {
	/**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    
    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public TaskCommandsParser() {}
    
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public TaskCommand parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
        	// TODO: Replace this placeholder
            return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "placeholder"));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();
        switch (commandWord) {

        case AddTaskCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case FindTaskCommand.COMMAND_WORD:
            return prepareFind(arguments);

        default:
            return new IncorrectTaskCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private TaskCommand prepareFind(String arguments) {
        // keywords delimited by whitespace
        final String[] keywords = arguments.split(" ");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindTaskCommand(keywordSet);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	private TaskCommand prepareDelete(String arguments) {
		 Optional<Integer> index = parseIndex(arguments);
	        if(!index.isPresent()){
	            return new IncorrectTaskCommand(
	                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
	        }

	        return new DeleteTaskCommand(index.get());
	}

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	private TaskCommand prepareAdd(String arguments) {
		// Assume arguments === description for now
		try {
			return new AddTaskCommand(arguments);
		} catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
		
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

   
}
