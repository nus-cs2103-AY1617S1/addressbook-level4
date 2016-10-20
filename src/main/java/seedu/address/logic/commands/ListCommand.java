package seedu.address.logic.commands;

import seedu.address.model.task.Status;
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
    	model.updateFilteredListToShowAll();
	 return new CommandResult(MESSAGE_SUCCESS);
    }
<<<<<<< HEAD

    // filters by task status first then by task type
	private void filterTask() {
		if (status == null && taskType == null) {
			model.updateFilteredListToShowAll();
		} else if (status != null && taskType == null) {
			filterTaskStatus();
		} else if (status == null && taskType != null) {
			filterTaskType();
		} else if (status != null && taskType != null) {
			filterTaskStatusAndType();
		}
	}

	private void filterTaskStatusAndType() {
		if (status.value.equals(Status.DoneStatus.DONE)) {
			if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
				model.updateFilteredTaskList(TaskFilter.isSomedayTask().and(TaskFilter.isDone()));
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isDeadlineTask().and(TaskFilter.isDone()));
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isEventTask().and(TaskFilter.isDone()));
			}
		} else {
			if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
				model.updateFilteredTaskList(TaskFilter.isSomedayTask().and(TaskFilter.isDone().negate()));
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isDeadlineTask().and(TaskFilter.isDone().negate()));
			} else if (taskType.value.equals(TaskType.Type.DEADLINE)) {
				model.updateFilteredTaskList(TaskFilter.isEventTask().and(TaskFilter.isDone().negate()));
			}
		}
	}

	// filters by task status
	private void filterTaskStatus() {
		if (status.value.equals(Status.DoneStatus.DONE)) {
			model.updateFilteredTaskList(TaskFilter.isDone());
		} else {
			model.updateFilteredTaskList(TaskFilter.isDone().negate());
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
		} 
	}
=======
>>>>>>> d12ba525754ae037bb56ee3603ddc356e7cf0a6c
}
