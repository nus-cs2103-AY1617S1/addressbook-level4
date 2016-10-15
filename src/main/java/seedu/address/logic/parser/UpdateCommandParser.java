package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

/*
 * Parses update commands
 */
public class UpdateCommandParser extends CommandParser {
	public static final String COMMAND_WORD = UpdateTaskCommand.COMMAND_WORD;
    private static final Pattern UPDATE_COMMAND_FORMAT = Pattern.compile("(?<targetIndex>\\S+) (?<updateType>\\S+) (?<arguments>.+)");
	private Matcher matcher;
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		TaskCommand taskCommand;
		
		// Matches the arguments to the update command format
        matcher = UPDATE_COMMAND_FORMAT.matcher(arguments.trim());
        
        // Checks validity of index and update type using the matcher
        taskCommand = checkIndexAndUpdateType();
        if (taskCommand != null) {
        	return taskCommand;
        }

        try {
	        // Creates an appropriate UpdateTaskCommand or IncorrectTaskCommand using the matcher
		    taskCommand = createAppropriateTaskCommand();
        } catch (IllegalValueException ive) {
        	return new IncorrectTaskCommand(ive.getMessage());
        }
	    
	    return taskCommand;
	}
	
	/**
	 * Based on the arguments provided to the update command, determine if the user wants to change
	 * the task, description or date.
	 * Then, return the appropriate UpdateTaskCommand by calling the corresponding constructor or
	 * the IncorrectTaskCommand.
	 */
	private TaskCommand createAppropriateTaskCommand() throws IllegalValueException {
		TaskCommand taskCommand = null;
		int index = Integer.parseInt(matcher.group("targetIndex"));
		String updateType = matcher.group("updateType");
		String arguments = matcher.group("arguments");
		
		// Depending on the update type, choose an appropriate TaskCommand
		if (updateType.equals("task")) {
			// Use AddCommandParser to create the new task that the user wants
			AddCommandParser parser = new AddCommandParser();
			Task task = ((AddTaskCommand) parser.prepareCommand(arguments)).getTask();
			taskCommand = new UpdateTaskCommand(index, task);
			
		} else if (updateType.equals("description")) {
			Description description = new Description(arguments);
			taskCommand = new UpdateTaskCommand(index, description);
			
		} else if (updateType.equals("date")) {
			// Check if the arguments that the user provided is a valid date or a valid date range.
			// Then, call the appropriate UpdateTaskCommands or throw an exception (if date is invalid).
			if (DateUtil.isValidDateFormat(arguments)) {
				Date newDeadline = DateUtil.getDate(arguments);
				return new UpdateTaskCommand(index, newDeadline);
				
			} else if (DateUtil.isValidStartDateToEndDateFormat(arguments)) {
	    		Date[] startAndEndDates = DateUtil.getStartAndEndDates(arguments);
	    		Date newStartDate = startAndEndDates[0];
	    		Date newEndDate = startAndEndDates[1];
				return new UpdateTaskCommand(index, newStartDate, newEndDate);
				
			} else {
				throw new IllegalValueException("Date is invalid");
			}
		} else {
			assert false : "Update type must equal to either task, description or date";
		}
		
		return taskCommand;
	}
	
	/**
	 * Determines if the index and update type is valid. If it is, return null.
	 * Else, return an IncorrectTaskCommand.
	 */
	private IncorrectTaskCommand checkIndexAndUpdateType() {
		if (!matcher.matches()) {
            return new IncorrectTaskCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE));
        }

        // Check if the index that user gave is valid
        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return new IncorrectTaskCommand("Target index must be positive");
        }
        
        // Check if the updateType that user gave is valid
        String updateType = matcher.group("updateType");
        if(!updateType.equals("task") && !updateType.equals("description") && !updateType.equals("date")) {
        	return new IncorrectTaskCommand("Update type must be equal to either task, description or date");
        }
        return null;
	}
}
