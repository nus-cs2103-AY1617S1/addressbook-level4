package seedu.address.logic.commands;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTaskFilter;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks filtered by specified parameters\n"
            + "Event Parameters: [TASK_TYPE] [done| not-done] [dd-mm-yy] [hh:mm]\n"
            + "Event Example: " + COMMAND_WORD
            + " someday not-done\n";  
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    //@@author A0139339W
    private Optional<String> taskType = Optional.empty();
    private Optional<String> doneStatus = Optional.empty();
    
    public ListCommand() {}
    
    public ListCommand(String taskType, String doneStatus) {
    	this.taskType = Optional.ofNullable(taskType);
    	this.doneStatus = Optional.ofNullable(doneStatus);
    }

    @Override
    public CommandResult execute() {
    	Predicate <ReadOnlyTask> taskTypePredicate = null;
    	Predicate <ReadOnlyTask> donePredicate = null;
    	
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
    	if(doneStatus.isPresent()) {
    		switch(doneStatus.get()) {
    		case "done":
    			donePredicate = ReadOnlyTaskFilter.isDone();
    			break;
    		case "not-done":
    			donePredicate = ReadOnlyTaskFilter.isNotDone();
    			break;
    		case "overdue":
    			donePredicate = ReadOnlyTaskFilter.isOverdue();
    		}
    	}
    	
    	if(doneStatus.isPresent() && taskType.isPresent()) {
    		model.updateFilteredTaskList(taskTypePredicate.and(donePredicate));
    	} else if(!doneStatus.isPresent() && taskType.isPresent()) {
    		model.updateFilteredTaskList(taskTypePredicate);
    	} else if(doneStatus.isPresent() && !taskType.isPresent()) {
    		model.updateFilteredTaskList(donePredicate);
    	} else if(!doneStatus.isPresent() && !taskType.isPresent()) {
    		model.updateFilteredListToShowAll();
    	}
    	
    	model.checkForOverdueTasks();
    	
        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author
}
