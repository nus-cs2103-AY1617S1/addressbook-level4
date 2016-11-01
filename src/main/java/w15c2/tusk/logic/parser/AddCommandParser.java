package w15c2.tusk.logic.parser;

import java.util.Date;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.commons.util.DateUtil;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

/**
 * Parses Add commands
 */
//@@author A0139817U
public class AddCommandParser extends CommandParser{
    public static final String COMMAND_WORD = AddTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = AddTaskCommand.ALTERNATE_COMMAND_WORD;

	/**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		try {
			// Call the appropriate AddTaskCommand constructor based on the arguments
			return createAppropriateAddTaskCommand(arguments);
		} catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
	}
	
	/**
     * Based on the description for the add command, determines if it is a FloatingTask, DeadlineTask or EventTask to be added.
     * Then, return the appropriate addTaskCommand by calling the corresponding constructor.
     */
    private AddTaskCommand createAppropriateAddTaskCommand(String description)
    			throws IllegalValueException {
    	// If there are double inverted commas at the start and end, it means that it is a FloatingTask
    	if (description.length() > 1 && description.endsWith("\"") && description.startsWith("\"")) {
    		return new AddTaskCommand(description);
    	}
    	
    	// Determine if it is DeadlineTask (which has "by" or "on") or EventTask (which has "from")
    	// Spaces needed before "from", "by" and "on" to ensure they are separate words
    	int finalIndex = -1;
    	String finalString = null;
    	int indexOfLastFrom = description.lastIndexOf(" from ");
    	int indexOfLastBy = description.lastIndexOf(" by ");
    	int indexOfLastOn = description.lastIndexOf(" on ");
    	
    	if (indexOfLastFrom > finalIndex) {
    		finalIndex = indexOfLastFrom;
    		finalString = " from ";
    	}
    	if (indexOfLastBy > finalIndex) {
    		finalIndex = indexOfLastBy;
    		finalString = " by ";
    	}
    	if (indexOfLastOn > finalIndex) {
    		finalIndex = indexOfLastOn;
    		finalString = " on ";
    	}
    	
    	if (finalIndex == -1) {
    		// If all keywords ("from", "by", "on") are missing, it means it is a Floating task
    		return new AddTaskCommand(description);
    	} else {
    		// Create an AddTaskCommand based on the keywords
    		return createAddTaskCommandBasedOnDateString(description, finalIndex, finalString);
    	} 
    }
    
    /**
     * Checks if the string from "substringFrom" to the end of description corresponds to a valid date format or 
     * a valid start date to end date format.
     * 
     * With the results, decide whether the task to add is a DeadlineTask, EventTask or FloatingTask.
     */
    private AddTaskCommand createAddTaskCommandBasedOnDateString(String description, int substringFrom, String keyword) 
    			throws IllegalValueException {
    	// Separate description into task description and the date string
    	String taskDescription = description.substring(0, substringFrom).trim();
    	String dateString = description.substring(substringFrom + keyword.length(), description.length()).trim();
    	
    	if ((keyword.equals(" by ") || keyword.equals(" on ")) && DateUtil.isValidDateFormat(dateString)) {
    		// dateString represents task's deadline
    		Date deadline = DateUtil.getDate(dateString);
			return new AddTaskCommand(taskDescription, deadline);
			
    	} else if (keyword.equals(" from ") && DateUtil.isValidStartDateToEndDateFormat(dateString)) {	
			// dateString represents task's start date and end date
    		Date[] startAndEndDates = DateUtil.getStartAndEndDates(dateString);
    		Date startDate = startAndEndDates[0];
    		Date endDate = startAndEndDates[1];
			return new AddTaskCommand(taskDescription, startDate, endDate);
			
		} else {
			// Floating task since sentence after "from" is not a valid date
			return new AddTaskCommand(description);
		}
    }
}
