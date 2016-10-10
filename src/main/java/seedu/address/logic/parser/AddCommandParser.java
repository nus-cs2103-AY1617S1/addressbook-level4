package seedu.address.logic.parser;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Add commands
 */
public class AddCommandParser extends CommandParser{
    public static final String COMMAND_WORD = AddTaskCommand.COMMAND_WORD;
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
    	
    	// Determine if it is DeadlineTask (which has "by") or EventTask (which has "from")
    	int indexOfLastFrom = description.lastIndexOf("from");
    	int indexOfLastBy = description.lastIndexOf("by");
    	boolean fromWordIsAbsent = (indexOfLastFrom == -1);
    	boolean byWordIsAbsent = (indexOfLastBy == -1);
    	
    	if (fromWordIsAbsent && byWordIsAbsent) {
    		// If both words are not present, it means that it is a FloatingTask
    		return new AddTaskCommand(description);
    		
    	} else if (fromWordIsAbsent) {
    		// If only "by" exists, task is a DeadlineTask if the sentence after "by" is a valid date format
    		return createAddTaskCommandBasedOnDateString(description, indexOfLastBy);
    		
    	} else if (byWordIsAbsent) {
    		// If only "from" exists, task is an EventTask if the sentence after "from" is a valid date format
    		return createAddTaskCommandBasedOnDateString(description, indexOfLastFrom);
    		
    	} else if (indexOfLastFrom > indexOfLastBy) {
    		// Both indices exist, the one that comes later will be picked. If "from" came later.
    		return createAddTaskCommandBasedOnDateString(description, indexOfLastFrom);
    			
    	} else {
    		// If "by" came later.
    		return createAddTaskCommandBasedOnDateString(description, indexOfLastBy);

    	}
    }
    
    /**
     * Checks if the string from "substringFrom" to the end of description corresponds to a valid date format or 
     * a valid start date to end date format.
     * 
     * With the results, decide whether the task to add is a DeadlineTask, EventTask or FloatingTask.
     */
    private AddTaskCommand createAddTaskCommandBasedOnDateString(String description, int substringFrom) 
    			throws IllegalValueException {
    	String dateString = description.substring(substringFrom, description.length());
    	if (isValidDateFormat(dateString)) {
    		// dateString represents task's deadline
    		Date deadline = null;
			return new AddTaskCommand(description, deadline);
			
    	} else if (isValidStartDateToEndDateFormat(dateString)) {
			// dateString represents task's start date and end date
    		Date startDate = null;
    		Date endDate = null;
			return new AddTaskCommand(description, startDate, endDate);
			
		} else {
			// Floating task since sentence after "from" is not a valid date
			return new AddTaskCommand(description);
		}
    }
    
    /*
     * TODO: CHANGE LOCATION OF THIS FUNCTION
     * Checks if a string follows a valid date format.
     * 
     * The following examples are all valid and similar dates: 
     * Oct 31. 31 Oct. 
     * 31 Oct 2016. 2016 Oct 31.
     */
    public boolean isValidDateFormat(String dateString) {
    	return true;
    }
    
    /*
     * TODO: CHANGE LOCATION OF THIS FUNCTION
     * Checks if a string follows a valid format (using "to" or "-") to show start date and end date.
     * 
     * The following examples are all valid and similar illustrations: 
     * Sep 31 - Oct 31. 31 Sep 2016 - 31 Oct 2016. 
     * Sep 31 to Oct 31. 31 Sep 2016 to 31 Oct 2016
     */
    public boolean isValidStartDateToEndDateFormat(String dateString) {
    	return true;
    }
}
