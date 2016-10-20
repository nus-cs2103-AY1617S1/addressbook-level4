package seedu.address.logic.commands;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskFilter;

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
    	System.out.println("DEBUG0: " + this.taskType.isPresent());
    }

    @Override
    public CommandResult execute() {
    	Predicate <ReadOnlyTask> taskTypePredicate = null;
    	Predicate <ReadOnlyTask> donePredicate = null;
    	
    	System.out.println("DEBUG1");
    	
    	if(taskType.isPresent()) {
    		System.out.println("DEBUG2");
    		assert taskType.get().equals("someday") || 
    				taskType.get().equals("deadline") || 
    				taskType.get().equals("event"); 
    		System.out.println("DEBUG3");
    		switch(taskType.get()) {
    		case "someday":
    			System.out.println("DEBUG4");
    			taskTypePredicate = (TaskFilter.isSomedayTask());
    			break;
    		case "deadline":
    			System.out.println("DEBUG5");
    			taskTypePredicate = (TaskFilter.isDeadlineTask());
    			break;
    		case "event":
    			System.out.println("DEBUG6");
    			taskTypePredicate = (TaskFilter.isEventTask());
    			break;
    		}
    	}
    	if(doneStatus.isPresent()) {
    		assert doneStatus.get().equals("done") || doneStatus.get().equals("not-done");
    		switch(doneStatus.get()) {
    		case "done":
    			donePredicate = TaskFilter.isDone();
    			break;
    		case "not-done":
    			donePredicate = TaskFilter.isDone().negate();
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
        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author
}
