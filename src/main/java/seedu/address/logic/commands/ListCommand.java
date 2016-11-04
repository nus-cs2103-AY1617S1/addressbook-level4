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
    
    public ListCommand() {}
    
    public ListCommand(String taskType, String doneStatus, LocalDateTime day) {
    	this.taskType = Optional.ofNullable(taskType);
    	this.doneStatus = Optional.ofNullable(doneStatus);
    	this.day = Optional.ofNullable(day);
    }

    @Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));
    	
    	Predicate <ReadOnlyTask> listPredicate = null;
    	Predicate <ReadOnlyTask> taskTypePredicate = null;
    	Predicate <ReadOnlyTask> donePredicate = null;
    	Predicate <ReadOnlyTask> dayPredicate = null;
    	
    	taskTypePredicate = getTaskTypePredicate(taskTypePredicate);
    	donePredicate = getStatusPredicate(donePredicate);
    	dayPredicate = getDayPredicate(dayPredicate);
    	listPredicate = getListPredicate(listPredicate, taskTypePredicate,
    			donePredicate, dayPredicate);
    	model.updateFilteredTaskList(listPredicate);
    	model.checkForOverdueTasks();
    	
        return new CommandResult(MESSAGE_SUCCESS);
    }

	/**
	 * Takes in the parameters
	 * @param taskTypePredicate
	 * @param donePredicate
	 * @param dayPredicate
	 * 
	 * @return listPredicate which combines all the three Predicates 
	 */
    private Predicate<ReadOnlyTask> getListPredicate(Predicate<ReadOnlyTask> listPredicate,
			Predicate<ReadOnlyTask> taskTypePredicate, Predicate<ReadOnlyTask> donePredicate,
			Predicate<ReadOnlyTask> dayPredicate) {
		boolean isFirstPredicate = true;
		if(taskType.isPresent()) {
    		listPredicate = taskTypePredicate;
    		isFirstPredicate = false;
    	}
    	if(doneStatus.isPresent()) {
    		listPredicate = isFirstPredicate ? 
    				donePredicate : listPredicate.and(donePredicate);
    		isFirstPredicate = false;
    	}
    	if(day.isPresent()) {
    		listPredicate = isFirstPredicate ?
    				dayPredicate : listPredicate.and(dayPredicate);
    		isFirstPredicate = false;
    	}
		return listPredicate;
	}
    
    /**
     * set the predicate for the day to be listed
     */
    private Predicate<ReadOnlyTask> getDayPredicate(Predicate<ReadOnlyTask> dayPredicate) {
    	if(day.isPresent()) {
    		dayPredicate = ReadOnlyTaskFilter.isThisDate(day.get().toLocalDate());
    	}
    	return dayPredicate;
    }

	/**
	 * set the predicate based on the specified status to be listed if any
	 */
    private Predicate<ReadOnlyTask> getStatusPredicate(Predicate<ReadOnlyTask> donePredicate) {
		if(doneStatus.isPresent()) {
			assert doneStatus.equals("done") || doneStatus.equals("pending") || 
				doneStatus.equals("overdue");
    		switch(doneStatus.get()) {
    		case "done":
    			donePredicate = ReadOnlyTaskFilter.isDone();
    			break;
    		case "pending":
    			donePredicate = ReadOnlyTaskFilter.isPending();
    			break;
    		case "overdue":
    			donePredicate = ReadOnlyTaskFilter.isOverdue();
    		case "default":
    			donePredicate = ReadOnlyTaskFilter.isDone().negate();
    		}
    	}
		return donePredicate;
	}

	/**
	 * Set the predicate for what kind of task list should display 
	 */
    private Predicate<ReadOnlyTask> getTaskTypePredicate(Predicate<ReadOnlyTask> taskTypePredicate) {
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
    	}
		return taskTypePredicate;
	}
}

//@@author