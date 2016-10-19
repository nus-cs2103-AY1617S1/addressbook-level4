package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.item.Task;
import seedu.address.model.item.TimePeriod;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;

public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    private static final String STRING_CONSTANT_ONE = "1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit an item in the To-Do List. ";
              
    public static final String TOOL_TIP = "edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY] [-reset parameter]";

    //public static final String MESSAGE_DUPLICATE_FLOATING_TASK = "This task already exists in the task manager";

    public static final String MESSAGE_SUCCESS = "Item edited: %1$s";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid edit item: %1$s reverted back to %2$s";

    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";

    public final int targetIndex;
    
    private Task toEdit;
    
    // saved state of task before edit for undo purposes
    private Task beforeEdit;
    
    Name taskName;
	Date startDate ;
    Date endDate;
    RecurrenceRate recurrenceRate;
    Priority priority;
    boolean removeRepeat, removeStart, removeEnd;

	public EditCommand(int targetIndex,String taskNameString, String startDateString, String endDateString, 
            String recurrenceRateString, String timePeriodString, String priorityString, String resetFieldString)  throws IllegalValueException {
        	
		this.targetIndex = targetIndex;
		taskName = null;
		startDate = null;
        endDate = null;
        priority = null;
        removeRepeat = false;
        removeStart = false;
        removeEnd = false;
        
        if ( taskNameString!=null && !taskNameString.trim().equals("")) {
    		taskName = new Name(taskNameString);
        } 
        
        if (startDateString != null) {
            assert DateTime.isValidDate(startDateString);
            startDate = DateTime.convertStringToStartDate(startDateString);
        }

        if (endDateString != null) {
            assert DateTime.isValidDate(endDateString);
            endDate = DateTime.convertStringToEndDate(endDateString, startDate);
        }

        if (recurrenceRateString != null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(recurrenceRateString, timePeriodString);
        } else if (recurrenceRateString == null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString);
        } else if (recurrenceRateString != null && timePeriodString == null) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        } 
        
        if (recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        }
        
        if(priorityString != null){
        	switch (priorityString) {
            	case ("low"): case ("l"): priority = Priority.LOW; break; 
            	case ("high"): case ("h"): priority = Priority.HIGH; break;
            	case ("medium"): case ("m"): case ("med"): priority = Priority.MEDIUM; break;
        	}
        } 
        
        /*
         * Check which field is to be reset
         */
        if(resetFieldString != null){
        	String[] resetField = resetFieldString.trim().split(" ");
        	for(int i = 0; i < resetField.length; i++){
        		switch (resetField[i].trim()) {
            		case ("repeat"):  removeRepeat = true; break; 
            		case ("start"): removeStart = true; break;
            		case ("end"): removeEnd = true; break;
        		}
        	}
        } 

	}

	@Override
	public CommandResult execute() {	    
		assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();

        if (lastShownList.size() < targetIndex || targetIndex == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        toEdit = (Task) taskToEdit;
        
        // Copy this task for history usage
        beforeEdit = new Task(taskToEdit);

        if (taskName == null) {        
            taskName = toEdit.getName();
        }
        
        if (startDate == null && toEdit.getStartDate().isPresent()) {
            startDate = toEdit.getStartDate().get();
        }
        
        if (removeStart){
        	startDate = null;
        }

        if (endDate == null && toEdit.getEndDate().isPresent()) {
        	endDate = toEdit.getEndDate().get();
        }
        
        if (removeEnd){
        	endDate = null;
        }

        if (priority == null){
        	priority = toEdit.getPriorityValue();
        }
                
        if (recurrenceRate == null && toEdit.getRecurrenceRate().isPresent()) {
        	recurrenceRate = toEdit.getRecurrenceRate().get();
        } 
                
        if (removeRepeat){
        	recurrenceRate = null;
        }
        model.editTask(taskToEdit,taskName,startDate,endDate,priority,recurrenceRate);

        updateHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        
	}

    @Override
    public CommandResult undo() {
        // edit all the fields back to the state before the edit took place
        
        // save this for printing purposes
        Task toUndoForPrint = new Task(toEdit);
        
        Task toUndo = toEdit;
        
        System.out.println(toUndo);
        
        Name oldTaskName = beforeEdit.getName();
        Optional<Date> oldStartDate = beforeEdit.getStartDate();
        Optional<Date> oldEndDate = beforeEdit.getEndDate();
        Priority oldPriority = beforeEdit.getPriorityValue();
        Optional<RecurrenceRate> oldReccurence = beforeEdit.getRecurrenceRate();
             
    	Date undoStartDate;
        Date undoEndDate;
        RecurrenceRate undoRecurrenceRate;

        //model.editName(toUndo, oldTaskName);
        
        //model.editPriority(toUndo, oldPriority);
 
        
        // edit back the start date
        if (!oldStartDate.isPresent()) {
            //model.editStartDate(toUndo, oldStartDate.get());
        	undoStartDate = null;
        }
        else {
            //model.editStartDate(toUndo, null);
        	undoStartDate = oldStartDate.get();
        }
        
        // edit back the end date
        if (!oldEndDate.isPresent()) {
            //model.editEndDate(toUndo, oldEndDate.get());
        	undoEndDate = null;
        }
        else {
            //model.editEndDate(toUndo, null);
        	undoEndDate = oldEndDate.get();
        }
        
        // edit back the recurrence rate
        if (!oldReccurence.isPresent()) {
            //model.editRecurrence(toUndo, oldReccurence.get());
        	undoRecurrenceRate = null;
        }
        else {
            //model.editRecurrence(toUndo, null);
        	undoRecurrenceRate =  oldReccurence.get();
        }
        
        model.editTask(toUndo,oldTaskName,undoStartDate,undoEndDate,oldPriority,undoRecurrenceRate);

        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toUndoForPrint, toUndo));
    }

}
