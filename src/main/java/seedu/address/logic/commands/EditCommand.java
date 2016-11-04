package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.TimePeriod;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;

//@@author A0139552B
public class EditCommand extends UndoableCommand {

    private final static Logger logger = LogsCenter.getLogger(EditCommand.class);

    public static final String COMMAND_WORD = "edit";

    private static final String STRING_CONSTANT_ONE = "1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit an item in the To-Do List. "
            + "Parameters: edit [NAME] [from/at/start DATE_TIME] [to/by/end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY] [-reset PARAMETER]\n"
            + "Example: " + COMMAND_WORD + " 1 play with cat by 3pm repeat every day -medium";
              
    public static final String TOOL_TIP = "edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY] [-reset PARAMETER]";

    public static final String MESSAGE_SUCCESS = "Item edited: %1$s";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid edit item: %1$s reverted back to %2$s";

    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";

    public static final String MESSAGE_END_DATE_CONSTRAINTS = "End date should be later than start date.";

    public final int targetIndex;
    
    private Task toEdit;
    
    // saved state of task before edit for undo purposes
    private Task beforeEdit;
    
    Name taskName;
	Date startDate;
    Date endDate;
    RecurrenceRate recurrenceRate;
    Priority priority;
    boolean removeReccurence, removeStartDate, removeEndDate;

	public EditCommand(int targetIndex, Optional<String> taskNameString, Optional<String> startDateString,
			Optional<String> endDateString, Optional<String> rateString, Optional<String> timePeriodString,
			Optional<String> priorityString, String resetFieldString) throws IllegalValueException {       
		
		this.targetIndex = targetIndex;
		initializeForEdit();
        assignTaskNameIfPresent(taskNameString); 
        assignStartDateIfPresent(startDateString);
        assignEndDateIfPresent(endDateString);
        assignRecurrenceRateIfPresent(rateString, timePeriodString); 
        assignPriorityIfPresent(priorityString);
        assignResetIfPresent(resetFieldString);

        if (isRecurWeekdaysButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        } 

	}

    private void initializeForEdit() {
        taskName = null;
        startDate = null;
        endDate = null;
        priority = null;
        recurrenceRate = null;
        removeReccurence = false;
        removeStartDate = false;
        removeEndDate = false;
    }
    
    /**
     * Check which field is to be reset
     * 
     * @param resetFieldString user's input of fields to be reset
     * set the remove fields as true if present
     */
    private void assignResetIfPresent(String resetFieldString) {
        if(resetFieldString != null){
            String[] resetField = resetFieldString.trim().split(" ");
            for(int i = 0; i < resetField.length; i++){
                switch (resetField[i].trim()) {
                    case ("repeat"):  removeReccurence = true; break; 
                    case ("start"): removeStartDate = true; break;
                    case ("end"): removeEndDate = true; break;
                }
            }                    
        }
    }

    /**
     * Assigns priority depending on the level stated
     * 
     * @param priorityString user's input of priority
     */
    private void assignPriorityIfPresent(Optional<String> priorityString) {
        if(priorityString != null){
            switch (priorityString.get()) {
                case ("low"):
                case ("l"):
                    priority = Priority.LOW; break; 
                case ("high"):
                case ("h"):
                    priority = Priority.HIGH; break;
                case ("medium"):
                case ("m"):
                case ("med"):
                    priority = Priority.MEDIUM; break;
            }
        }        
    }
    
    /**
     * Converts given String into the Name representation for task name.
     * 
     * @param taskNameString user's input of task name
     */
    private void assignTaskNameIfPresent(Optional<String> taskNameString) {
        if (taskNameString.isPresent() && !taskNameString.get().toString().trim().equals("")) {
    		taskName = new Name(taskNameString.get());
        }
    }
    
    /**
     * Converts given String into the Date representation for start date.
     * 
     * @param startDateString user's input of start date
     * @throws IllegalValueException if startDateString cannot be converted into a Date object
     */
    private void assignStartDateIfPresent(Optional<String> startDateString) throws IllegalValueException {
        if (startDateString.isPresent()) {
            startDate = DateTime.convertStringToDate(startDateString.get());
            if (!DateTime.hasTimeValue(startDateString.get())) {
                startDate = DateTime.setTimeToStartOfDay(startDate);
            }
        }
    }
    
    /**
     * Converts given String into the Date representation for end date.
     * 
     * @param endDateString user's input of end date
     * @throws IllegalValueException if endDateString cannot be converted into a Date object
     */
    private void assignEndDateIfPresent(Optional<String> endDateString) throws IllegalValueException {
        if (endDateString.isPresent()) {
            endDate = DateTime.convertStringToDate(endDateString.get());
            if (startDate != null && !DateTime.hasDateValue(endDateString.get())) {
                endDate = DateTime.setEndDateToStartDate(startDate, endDate);
            }
            if (!DateTime.hasTimeValue(endDateString.get())) {
                endDate = DateTime.setTimeToEndOfDay(endDate);
            }
        }
    }
    
    /**
     * Converts given String into the RecurrenceRate representation. 
     *
     * @param rateString user's input of rate
     * @param timePeriodString user's input of time period
     * @throws IllegalValueException if rateString is present but timePeriodString isn't present
     * (for e.g, "3" is invalid. Examples such as "3 days" or "week" is valid).
     */
    private void assignRecurrenceRateIfPresent(Optional<String> rateString, Optional<String> timePeriodString)
            throws IllegalValueException {
        if (rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(rateString.get(), timePeriodString.get());
        } else if (!rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString.get());
        } else if (rateString.isPresent() && !timePeriodString.isPresent()) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if both dates are null and the Task repeats every weekday e.g "monday". 
     *
     * @param startDate start date of Task
     * @param endDate   end date of Task
     * @param recurrenceRate    recurrence rate of Task
     * @return  true if both dates are null and Task repeats every weekday. Else, returns false.
     */
    private static boolean isRecurWeekdaysButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null;
    }
    
	@Override
	public CommandResult execute() {	    
	    assert model != null;
		
	    if (model.isCurrentListDoneList()) {
	        indicateAttemptToExecuteIncorrectCommand();
	        return new CommandResult(String.format(Messages.MESSAGE_DONE_LIST_RESTRICTION));
	    }
	    
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();

        if (lastShownList.size() < targetIndex || targetIndex == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        toEdit = (Task) taskToEdit;
        
        // Copy this task for history usage
        beforeEdit = new Task(taskToEdit);

        //assign previous name to taskName if user never input one
        if (taskName == null) {        
            taskName = toEdit.getName();
        }
        
        assignStartDate();
        assignEndDate();
        
        /*
         * Return error message if user key in end date that occurs before start date  
         */
        if(endDate != null && startDate != null && endDate.before(startDate)){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_END_DATE_CONSTRAINTS);
        }      
        
        //assign previous priority to priority if user never input one
        if (priority == null) {
        	priority = toEdit.getPriorityValue();
        }    

        if (recurrenceRate == null && toEdit.getRecurrenceRate().isPresent()) {
            recurrenceRate = toEdit.getRecurrenceRate().get();
        }  
        
        /*
         * Return error message if user key in recurrence rate without any dates 
         */
        if (recurrenceRate != null && !beforeEdit.getStartDate().isPresent() && !beforeEdit.getEndDate().isPresent()
                && startDate == null && endDate == null){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }
        
        //remove recurrence if the start and end date are removed
        if (removeReccurence || (startDate == null && endDate == null)) {
            recurrenceRate = null;
        }
        
        model.editTask(taskToEdit, taskName, startDate, endDate, priority, recurrenceRate);
        updateHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));      
	}

    private void assignEndDate() {
        //assign previous end date to endDate if user never input one
        if (endDate == null && toEdit.getEndDate().isPresent()) {
        	endDate = toEdit.getEndDate().get();
        }
        
        //assign endDate as null if user choose to reset end date
        if (removeEndDate) {
        	endDate = null;
        }
    }

    private void assignStartDate() {
        //assign previous start date to startDate if user never input one
        if (startDate == null && toEdit.getStartDate().isPresent()) {
            startDate = toEdit.getStartDate().get();
        }
        
        //assign startDate as null if user choose to reset start date
        if (removeStartDate) {
        	startDate = null;
        }
    }

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        // edit all the fields back to the state before the edit took place
        
        // save this for printing purposes
        Task toUndoForPrint = new Task(toEdit);
        
        Task toUndo = toEdit;
        
        System.out.println(toUndo);
        
        Name previousTaskName = beforeEdit.getName();
        Optional<Date> previousStartDate = beforeEdit.getStartDate();
        Optional<Date> previousEndDate = beforeEdit.getEndDate();
        Priority previousPriority = beforeEdit.getPriorityValue();
        Optional<RecurrenceRate> previousReccurence = beforeEdit.getRecurrenceRate();
        
        Date undoStartDate = null;
        Date undoEndDate = null;
        RecurrenceRate undoRecurrenceRate = null;
       
        // edit back the start date
        if (previousStartDate.isPresent()) {
            undoStartDate = previousStartDate.get();
        }
       
        // edit back the end date
        if (previousEndDate.isPresent()) {
            undoEndDate = previousEndDate.get();
        }
        
        // edit back the recurrence rate
        if (previousReccurence.isPresent()) {
            undoRecurrenceRate = previousReccurence.get();
        }
                   
        model.editTask(toUndo, previousTaskName, undoStartDate, undoEndDate, previousPriority, undoRecurrenceRate);      
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toUndoForPrint, toUndo));
    }
    //@@author

}
