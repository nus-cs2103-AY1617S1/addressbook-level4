package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.item.Task;
import seedu.address.model.Model;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit an item in the To-Do List. ";
              
    public static final String TOOL_TIP = "edit INDEX [NAME], [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]";

    public static final String MESSAGE_DUPLICATE_FLOATING_TASK = "This task already exists in the task manager";

    public static final String MESSAGE_SUCCESS = "Item edited: %1$s";

    public final int targetIndex;
    
    private Task toEdit;
    
    Name taskName;
	Date startDate ;
    Date endDate;
    RecurrenceRate recurrenceRate;
    Priority priority;
    
	public EditCommand(int targetIndex,String taskNameString, String startDateString, String endDateString, 
            String recurrenceRateString, String priorityString)  throws IllegalValueException {
        	
		this.targetIndex = targetIndex;
		taskName = null;
		startDate = null;
        endDate = null;
        priority = null;
        
        //System.out.println(taskNameString);
        if (taskNameString != null) {
    		taskName = new Name(taskNameString);
        }
        
        if (startDateString != null) {
            DateParser dp = new DateParser(startDateString);
            startDate = dp.parseDate();
        }
        
        if (endDateString != null) {
            DateParser dp = new DateParser(endDateString);
            endDate = dp.parseDate();
        }
        
        if (recurrenceRateString == null) {
            recurrenceRate = new RecurrenceRate(0);
        } else { //TODO: Should I put else-if here instead? Seems ridiculous though.
            recurrenceRate = new RecurrenceRate(Integer.valueOf(recurrenceRateString));
            System.out.println(Integer.valueOf(recurrenceRateString));
        }
        
        //TODO: Throw IllegalValueException for default cases?
        if(priorityString != null){
        	switch (priorityString) {
            	case ("low"): case ("l"): priority = Priority.LOW; break; 
            	case ("high"): case ("h"): priority = Priority.HIGH; break;
            	case ("medium"): case ("m"): priority = Priority.MEDIUM; break;
        	}
        } 

        this.toEdit = new Task(taskName, startDate, endDate, recurrenceRate, priority);      
	}

	@Override
	public CommandResult execute() {	    
		assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredFloatingTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToEdit = lastShownList.get(targetIndex - 1);
        
        if (taskName != null) {
            try {            
				model.editName(personToEdit, taskName);
			} catch (DuplicateTaskException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        if (startDate != null) {
            model.editStartDate(personToEdit, startDate);
        }

        if (endDate != null) {
            model.editEndDate(personToEdit, endDate);
        }

        if (priority != null){
            model.editPriority(personToEdit, priority);
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        
        /* TODO
            if (recurrenceRate != null) {
            model.editRecurrence(personToEdit, recurrenceRate);
        }
        */
	}

}
