package seedu.gtd.logic.commands;

import seedu.gtd.commons.core.Messages;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Mark a task as done, identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task as done, by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
       ReadOnlyTask toEdit = lastShownList.get(targetIndex-1);
       Task taskToUpdate = new Task(toEdit);
       
		try {
	      taskToUpdate = updateTask(taskToUpdate, "done", "true");
	      System.out.println("IS IT DONE? " + taskToUpdate.getisDone());
		} catch (IllegalValueException ive) {
			return new CommandResult(ive.getMessage());
		}

       assert model != null;
       try {
    	   System.out.println("in donecommand.java");
    	   System.out.println(taskToUpdate.getName() + " " + taskToUpdate.getisDone());
    	   System.out.println("index at donecommand:" + targetIndex);
			model.doneTask(targetIndex-1, taskToUpdate);
			
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing";
		}
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToUpdate));

    }
    
    private Task updateTask(Task taskToUpdate, String detailType, String newDetail) throws IllegalValueException {
   	 taskToUpdate.edit(detailType, newDetail);
   	 return taskToUpdate;
    }

}
