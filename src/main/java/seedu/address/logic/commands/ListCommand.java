package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTaskFilter;

//@@author A0139339W
/**
 * Lists tasks in the task manager to the user according to the specified filter.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks filtered by specified parameters\n"
            + "Event Parameters: [TASK_TYPE] [STATUS] [DAY]\n"
            + "Event Example: " + COMMAND_WORD
            + " someday pending 08-11-16\n";  
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private Optional<String> taskType = Optional.empty();
    private Optional<String> doneStatus = Optional.of("default");
    private Optional<LocalDateTime> day = Optional.empty();
    
    private Predicate<ReadOnlyTask> listPredicate = null;
    private boolean isFirstPredicate = true;
    
    public ListCommand() {}
    
    public ListCommand(String taskType, String doneStatus, LocalDateTime day) {
    	this.taskType = Optional.ofNullable(taskType);
    	this.doneStatus = Optional.ofNullable(doneStatus);
    	this.day = Optional.ofNullable(day);
    }

    @Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));
    	
    	listPredicateUpdateTaskType();
    	listPredicateUpdateStatus();
    	listPredicateUpdateDay();
    	model.updateFilteredTaskList(listPredicate);
    	model.checkForOverdueTasks();
    	
        return new CommandResult(MESSAGE_SUCCESS);
    }

    
    /**
     * update the global listPredicate for the day to be listed
     */
    private void listPredicateUpdateDay() {
    	Predicate<ReadOnlyTask> dayPredicate = null;
    	if(day.isPresent()) {
    		dayPredicate = ReadOnlyTaskFilter.isThisDate(day.get().toLocalDate());
    		listPredicate = isFirstPredicate ?
    				dayPredicate : listPredicate.and(dayPredicate);
    		isFirstPredicate = false;
    	}
    }

	/**
	 * update the global listPredicate based on the specified status to be listed if any
	 */
    private void listPredicateUpdateStatus() {
    	Predicate<ReadOnlyTask> statusPredicate = null;
    	if(doneStatus.isPresent()) {
			assert doneStatus.get().equals("done") || doneStatus.get().equals("pending") || 
				doneStatus.get().equals("overdue") || doneStatus.get().equals("default");
    		switch(doneStatus.get()) {
    		case "done":
    			statusPredicate = ReadOnlyTaskFilter.isDone();
    			break;
    		case "pending":
    			statusPredicate = ReadOnlyTaskFilter.isPending();
    			break;
    		case "overdue":
    			statusPredicate = ReadOnlyTaskFilter.isOverdue();
    			break;
    		case "default":
    			statusPredicate = ReadOnlyTaskFilter.isDone().negate();
    		}
    		listPredicate = isFirstPredicate ? 
    				statusPredicate : listPredicate.and(statusPredicate);
    		isFirstPredicate = false;
    	}
	}

	/**
	 * Update the global listPredicate for what kind of task list should display 
	 */
    private void listPredicateUpdateTaskType() {
		Predicate<ReadOnlyTask> taskTypePredicate = null;
    	if(taskType.isPresent()) {
    		assert taskType.get().equals("someday") || taskType.get().equals("sd") ||
    				taskType.get().equals("deadline") || taskType.get().equals("dl") ||
    				taskType.get().equals("event") || taskType.get().equals("ev"); 
    		switch(taskType.get()) {
    		case "someday":
    		case "sd":
    			taskTypePredicate = ReadOnlyTaskFilter.isSomedayTask();
    			break;
    		case "deadline":
    		case "dl":
    			taskTypePredicate = ReadOnlyTaskFilter.isDeadlineTask();
    			break;
    		case "event":
    		case "ev":
    			taskTypePredicate = ReadOnlyTaskFilter.isEventTask();
    			break;
    		}
        	listPredicate = isFirstPredicate ? 
    				taskTypePredicate : listPredicate.and(taskTypePredicate);
    		isFirstPredicate = false;

    	}
	}
}

//@@author