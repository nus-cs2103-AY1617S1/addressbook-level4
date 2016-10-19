package seedu.address.logic.commands;

import seedu.address.model.task.Status;
import seedu.address.model.task.TaskFilter;
import seedu.address.model.task.TaskType;

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
    
    private TaskType taskType;
    private Status status;
    
    public ListCommand() {
    	taskType = null;
    	status = null;
    }
    
    public ListCommand(TaskType taskType, Status status) {
    	this.taskType = taskType;
    	this.status = status;
    }

    @Override
    public CommandResult execute() {
    	filterTask();
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    // filters by task status first then by task type
	private void filterTask() {
		if (status != null) {
			if (status.value.equals(Status.DoneStatus.DONE)) {
				model.updateFilteredTaskList(TaskFilter.isDone());
			} else {
				model.updateFilteredTaskList(TaskFilter.isDone().negate());
			}
			filterTaskType();
		} else {
			filterTaskType();
		}
	}
		

	// filters by task type
	private void filterTaskType() {
		if (taskType != null) {
			if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
				model.updateFilteredTaskList(TaskFilter.isSomedayTask());
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isDeadlineTask());
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isEventTask());
			}
		} else {
			model.updateFilteredListToShowAll();
		}
	}
}
