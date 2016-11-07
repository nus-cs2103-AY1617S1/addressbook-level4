package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.commons.util.DateUtil;
import w15c2.tusk.commons.util.StringUtil;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UpdateTaskCommand;
import w15c2.tusk.model.task.Description;
import w15c2.tusk.model.task.Task;

//@@author A0139817U
/**
 * Parses arguments and create the appropriate update commands. 
 * Format: <target index> <update type> <updated value>
 */
public class UpdateCommandParser extends CommandParser {
	public static final String COMMAND_WORD = UpdateTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = UpdateTaskCommand.ALTERNATE_COMMAND_WORD;

    private static final Pattern UPDATE_COMMAND_FORMAT = Pattern.compile("(?<targetIndex>\\S+) (?<updateType>\\S+) (?<arguments>.+)");
    private Matcher matcher;
    
    public static final String MESSAGE_INVALID_UPDATE_TYPE = "Update type must be either task, description or date\n Examples: \n"
    		+ "1) update INDEX task Meeting with colleagues by 20 Oct \n"
    		+ "2) update INDEX description \n"
    		+ "3) update INDEX date 20 Oct to 31 Oct";
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param arguments 	Target index, update type and updated value.
     * @return 				A prepared update command.
     */
	public TaskCommand prepareCommand(String arguments) {
		TaskCommand taskCommand;
		
		// Matches the arguments to the update command format
        matcher = UPDATE_COMMAND_FORMAT.matcher(arguments.trim());
        
        try {
        	// Checks validity of index and update type using the matcher
        	checkIndexAndUpdateType();
        	
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
	 * 
	 * @return 	A prepared update command.
	 * @throws IllegalValueException 	When update task constructor fails.
	 */
	private TaskCommand createAppropriateTaskCommand() throws IllegalValueException {
		TaskCommand taskCommand = null;
		int index = Integer.parseInt(matcher.group("targetIndex"));
		String updateType = matcher.group("updateType");
		String arguments = matcher.group("arguments");
		
		// Depending on the update type, choose an appropriate TaskCommand
		switch(updateType) {
			case "task": 
				taskCommand = createTaskUpdateTaskCommand(index, arguments);
				break;
			case "description":
				taskCommand = createDescriptionUpdateTaskCommand(index, arguments);
				break;
			case "date":
				taskCommand = createDateUpdateTaskCommand(index, arguments);
				break;
			default:
				throw new IllegalValueException(MESSAGE_INVALID_UPDATE_TYPE);
		}
		
		return taskCommand;
	}
	
	/**
	 * Creates an UpdateTaskCommand to update the entire task.
	 * 
	 * @param index		Targeted index of the task to update.
	 * @param arguments	Updated task to replace the old task.
	 * @return 			A prepared update task command.
	 * @throws IllegalValueException	When update task constructor fails.
	 */
	private TaskCommand createTaskUpdateTaskCommand(int index, String arguments) throws IllegalValueException {
		// Use AddCommandParser to create the new task that the user wants
		AddCommandParser parser = new AddCommandParser();
		Task task = ((AddTaskCommand) parser.prepareCommand(arguments)).getTask();
		return new UpdateTaskCommand(index, task);
	}
	
	/**
	 * Creates an UpdateTaskCommand to update the description.
	 * 
	 * @param index		Targeted index of the task to update.
	 * @param arguments	Updated description for the task.
	 * @return 			A prepared update description command.
	 * @throws IllegalValueException 	When update task constructor fails.
	 */
	private TaskCommand createDescriptionUpdateTaskCommand(int index, String arguments) throws IllegalValueException {
		Description description = new Description(arguments);
		return new UpdateTaskCommand(index, description);
	}
	
	/**
	 * Creates an UpdateTaskCommand to update the date.
	 * 
	 * @param index		Targeted index of the task to update.
	 * @param arguments	Updated date for the task.
	 * @return 			A prepared update date command.
	 * @throws IllegalValueException 	When update task constructor fails.
	 */
	private TaskCommand createDateUpdateTaskCommand(int index, String arguments) throws IllegalValueException {
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
			throw new IllegalValueException(MESSAGE_INVALID_DATE_FORMAT);
		}
	}
	
	/**
	 * Determines if the index and update type is valid. If it is, return null.
	 * Else, return an IncorrectTaskCommand.
	 * 
	 * @throws IllegalValueException	When format, target index or update type is not valid.
	 */
	private void checkIndexAndUpdateType() throws IllegalValueException {
		if (!matcher.matches()) {
            throw new IllegalValueException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE));
        }

        // Check if the index that user gave is valid
        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        // Check if the updateType that user gave is valid
        String updateType = matcher.group("updateType");
        if(!updateType.equals("task") && !updateType.equals("description") && !updateType.equals("date")) {
        	throw new IllegalValueException(MESSAGE_INVALID_UPDATE_TYPE);
        }
	}
}
