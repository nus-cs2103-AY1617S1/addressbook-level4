package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTaskFilter;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks filtered by specified parameters\n"
            + "Event Parameters: [TASK_TYPE] [done | pending] [dd-mm-yy] [hh:mm]\n"
            + "Event Example: " + COMMAND_WORD
            + " someday pending\n";  
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    //@@author A0139339W
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
    	System.out.println("DEBUG1");
    	dayPredicate = getDayPredicate(dayPredicate);
    	System.out.println("DEBUG2: " + dayPredicate);
    	listPredicate = getListPredicate(listPredicate, taskTypePredicate,
    			donePredicate, dayPredicate);
    	System.out.println("DEBUG3");
    	model.updateFilteredTaskList(listPredicate);
    	System.out.println("DEBUG4");
    	model.checkForOverdueTasks();
    	
        return new CommandResult(MESSAGE_SUCCESS);
    }

	private Predicate<ReadOnlyTask> getDayPredicate(Predicate<ReadOnlyTask> dayPredicate) {
		if(day.isPresent()) {
			dayPredicate = ReadOnlyTaskFilter.isThisDate(day.get().toLocalDate());
    	}
		return dayPredicate;
	}

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