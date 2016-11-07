//@@author A0139772U
package seedu.whatnow.logic.commands;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.*;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Adds a task to WhatNow.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to WhatNow. \n"
			+ "Parameters: \"TASK_NAME\" [t/TAG]...\n"
	        + "Parameters: \"TASK_NAME\" [on/by/from] [today/tomorrow/DATE] [to] [today/tomorrow/DATE] [every] [day/week/month/year] [till/until] [DATE] [t/TAG]...\n"
			+ "Parameters: \"TASK_NAME\" [by/at/from] [TIME] [till/to] [TIME] [t/TAG]...\n"
			+ "Example: \n"
			+ COMMAND_WORD + " \"Buy groceries\" on 23rd Feb 2017 t/highPriority\n"
			+ COMMAND_WORD + " \"Buy dinner\" at 6pm t/highPriority\n"
			+ COMMAND_WORD + " \"Lesson\" on 24/2/2017 from 8.30am to 4:30pm every week till 25/4/2017 t/lowPriority\n"
			+ COMMAND_WORD + " \"Submit homework\" by tomorrow 12pm t/lowPriority\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatNow.";
    public static final String MESSAGE_RECURRING_NO_END_DATE = "Please specify an end date for the recurring task.";
    private static final String STATUS_INCOMPLETE = "incomplete";
    
	private Task toAdd;
	
	//@@author A0126240W
	public AddCommand(Task taskToAdd, Set<String> tags) throws IllegalValueException, ParseException {
        TaskTime validateTime = null;
        TaskDate validateDate = null;
        String validatedTime = taskToAdd.getTaskTime();
        String validatedDate = taskToAdd.getTaskDate();
        
        if (taskToAdd.getTaskTime() != null || taskToAdd.getStartTime() != null || taskToAdd.getEndTime() != null) {
            validateTime = new TaskTime(taskToAdd.getTaskTime(), taskToAdd.getStartTime(), taskToAdd.getEndTime(), taskToAdd.getTaskDate(), taskToAdd.getStartDate(), taskToAdd.getEndDate());
            if (taskToAdd.getTaskDate() == null && taskToAdd.getStartDate() == null && taskToAdd.getEndDate() == null)
                validatedDate = validateTime.getDate();
        }
        
        if (taskToAdd.getTaskDate() != null || taskToAdd.getStartDate() != null || taskToAdd.getEndDate() != null) {
            validateDate = new TaskDate(taskToAdd.getTaskDate(), taskToAdd.getStartDate(), taskToAdd.getEndDate());
            if (taskToAdd.getTaskDate() != null) {
                validatedDate = validateDate.getDate();
            } else if (taskToAdd.getStartDate() != null) {
                taskToAdd.setStartDate(validateDate.getStartDate());
                taskToAdd.setEndDate(validateDate.getEndDate());
            }
        }
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toAdd = new Task(taskToAdd.getName(), validatedDate, taskToAdd.getStartDate(), taskToAdd.getEndDate(), 
                validatedTime, taskToAdd.getStartTime(), taskToAdd.getEndTime(), taskToAdd.getPeriod(), taskToAdd.getEndPeriod(), 
                new UniqueTagList(tagSet), STATUS_INCOMPLETE, null);
    }
	
	public void addRecurring(Recurrence recurring) throws DuplicateTaskException {
	    String currentDate;
	    Task nextToAdd = recurring.getNextTask(toAdd);
	    
	    if (nextToAdd.getTaskDate() != null) {
            currentDate = nextToAdd.getTaskDate();
        } else if (nextToAdd.getStartDate() != null) {
            currentDate = nextToAdd.getStartDate();
        } else {
            return;
        }
	    
	    while (recurring.hasNextTask(currentDate)) {  	        
	        model.addTask(nextToAdd);
	        model.getUndoStack().push(COMMAND_WORD);
	        model.getDeletedStackOfTasksAdd().push(nextToAdd);
	        
	        nextToAdd = recurring.getNextTask(nextToAdd);
	        
	        if (nextToAdd.getTaskDate() != null) {
	            currentDate = nextToAdd.getTaskDate();
	        } else if (nextToAdd.getStartDate() != null) {
	            currentDate = nextToAdd.getStartDate();
	        } else {
	            return;
	        }
	    }
	}

	//@@author A0139128A
	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			model.addTask(toAdd);
			model.getUndoStack().push(COMMAND_WORD);
			model.getDeletedStackOfTasksAdd().push(toAdd);
			Recurrence recurring = new Recurrence(toAdd.getPeriod(), toAdd.getTaskDate(), toAdd.getStartDate(), toAdd.getEndDate(), toAdd.getEndPeriod());
			model.clearRedoAll();
			if (recurring.hasRecurring()) {
			    addRecurring(recurring);
			}
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
	}
}
