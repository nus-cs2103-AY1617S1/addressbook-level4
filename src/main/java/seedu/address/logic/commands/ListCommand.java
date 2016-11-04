package seedu.address.logic.commands;

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
    
    public ListCommand() {}
    
    public ListCommand(String taskType, String doneStatus) {
    	this.taskType = Optional.ofNullable(taskType);
    	this.doneStatus = Optional.ofNullable(doneStatus);
    }

    @Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));
    	
    	Predicate <ReadOnlyTask> listPredicate = null;
    	Predicate <ReadOnlyTask> taskTypePredicate = null;
    	Predicate <ReadOnlyTask> donePredicate = null;
    	
    	taskTypePredicate = getTaskTypePredicate(taskTypePredicate);
    	donePredicate = getStatusPredicate(donePredicate);
    	
    	listPredicate = getListPredicate(listPredicate, taskTypePredicate, donePredicate);
    	
    	model.updateFilteredTaskList(listPredicate);
    	
    	model.checkForOverdueTasks();
    	
        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author

	private Predicate<ReadOnlyTask> getListPredicate(Predicate<ReadOnlyTask> listPredicate,
			Predicate<ReadOnlyTask> taskTypePredicate, Predicate<ReadOnlyTask> donePredicate) {
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
