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

	public EditCommand(int targetIndex, Optional<String> taskNameString, Optional<String> startDateString,
			Optional<String> endDateString, Optional<String> rateString, Optional<String> timePeriodString,
			Optional<String> priorityString, String resetFieldString)throws IllegalValueException {       
		
		this.targetIndex = targetIndex;
		taskName = null;
		startDate = null;
        endDate = null;
        priority = null;
        removeRepeat = false;
        removeStart = false;
        removeEnd = false;
        
        if (taskNameString.isPresent() && !taskNameString.get().toString().trim().equals("")) {
    		taskName = new Name(taskNameString.get());
        } 
        
        if (startDateString.isPresent()) {
            startDate = DateTime.convertStringToDate(startDateString.get());
        }

        if (endDateString.isPresent()) {
            endDate = DateTime.convertStringToDate(endDateString.get());
            if (startDate != null && !DateTime.hasDateValue(endDateString.get())) {
                endDate = DateTime.setEndDateToStartDate(startDate, endDate);
            }
        }

        if (rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(rateString.get(), timePeriodString.get());
        } else if (!rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString.get());
        } else if (rateString.isPresent() && !timePeriodString.isPresent()) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        } 
        
        if (recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        }

        priority = Priority.stringToPriority(priorityString.get());
                
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
		
	    if (model.isCurrentListDoneList()) {
	        indicateAttemptToExecuteIncorrectCommand();
	        return new CommandResult(String.format(Messages.MESSAGE_DONE_LIST_RESTRICTION));
	    }
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();

        if (lastShownList.size() < targetIndex || targetIndex == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        toEdit = (Task) taskToEdit;
        
        // Copy this task for history usage
        beforeEdit = new Task(taskToEdit);
        
        if (taskName != null) {        
            model.editName(taskToEdit, taskName);
        }
        
        if (startDate != null) {
            model.editStartDate(taskToEdit, startDate);
        }
        if (removeStart){
        	startDate = null;
            model.editStartDate(taskToEdit, startDate);
        }

        if (endDate != null) {
            model.editEndDate(taskToEdit, endDate);
        }
        if (removeEnd){
        	endDate = null;
            model.editEndDate(taskToEdit, endDate);
        }

        if (priority != null){
            model.editPriority(taskToEdit, priority);
        }

        if (recurrenceRate != null && (toEdit.getEndDate().isPresent() || toEdit.getStartDate().isPresent())) {
            model.editRecurrence(taskToEdit, recurrenceRate);
        } 
        if (removeRepeat){
        	recurrenceRate = null;
            model.editRecurrence(taskToEdit, recurrenceRate);
        }
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
                
        model.editName(toUndo, oldTaskName);
        
        model.editPriority(toUndo, oldPriority);
 
        
        // edit back the start date
        if (oldStartDate.isPresent()) {
            model.editStartDate(toUndo, oldStartDate.get());
        }
        else {
            model.editStartDate(toUndo, null);
        }
        
        // edit back the end date
        if (oldEndDate.isPresent()) {
            model.editEndDate(toUndo, oldEndDate.get());
        }
        else {
            model.editEndDate(toUndo, null);
        }
        
        // edit back the recurrence rate
        if (oldReccurence.isPresent()) {
            model.editRecurrence(toUndo, oldReccurence.get());
        }
        else {
            model.editRecurrence(toUndo, null);
        }
        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toUndoForPrint, toUndo));
    }

}
