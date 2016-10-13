package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.model.task.Description;

/*
 * Parses update commands
 */
public class UpdateCommandParser extends CommandParser {
	public static final String COMMAND_WORD = UpdateTaskCommand.COMMAND_WORD;
    private static final Pattern UPDATE_COMMAND_FORMAT = Pattern.compile("(?<targetIndex>\\S+) (?<updateType>\\S+) (?<arguments>.+)");
	
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		Optional<Integer> index = parseIndex(arguments);
        if(!index.isPresent()){
            return new IncorrectTaskCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE));
        }

	    return createAppropriateUpdateTaskCommand(index.get(), arguments);
	}
	
	/**
	 * Based on the arguments provided to the update command, determine if the user wants to change
	 * the task, description or date.
	 * Then, return the appropriate updateTaskCommand by calling the corresponding constructor.
	 */
	private UpdateTaskCommand createAppropriateUpdateTaskCommand(int targetIndex, String arguments) {
		return new UpdateTaskCommand(targetIndex, new Description(arguments));
	}

	/**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = UPDATE_COMMAND_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        // Check if the index that user gave is valid
        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        
        // Check if the updateType that user gave is valid
        String updateType = matcher.group("updateType");
        if(!updateType.equals("task") && !updateType.equals("description") && !updateType.equals("date")) {
        	return Optional.empty();
        }
        
        return Optional.of(Integer.parseInt(index));

    }
}
