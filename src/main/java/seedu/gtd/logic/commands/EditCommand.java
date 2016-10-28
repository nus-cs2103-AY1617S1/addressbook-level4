package seedu.gtd.logic.commands;

import seedu.gtd.commons.core.Messages;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;
 
 /**
  * Adds a task to the address book.
  */
 public class EditCommand extends Command {
 
     public static final String COMMAND_WORD = "edit";
 
     public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
             + "Edits the task identified by the index number used in the last task listing.\n\t"
             + "Parameters: [INDEX] (must be a positive integer) prefix/[NEW DETAIL]\n\t"
             + "Example: " + COMMAND_WORD
             + " 1 p/98756433";
 
     public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task updated: %1$s";
     
     private int targetIndex;
     private String detailType;
     private String newDetail;
 
     public EditCommand(int targetIndex, String detailType, String newDetail) {
         this.targetIndex = targetIndex;
         this.detailType = detailType;
         this.newDetail = newDetail;
     }
     
 
     @Override
     public CommandResult execute() {

         UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

         if (lastShownList.size() < targetIndex) {
             indicateAttemptToExecuteIncorrectCommand();
             return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
         }
         
        ReadOnlyTask toEdit = lastShownList.get(targetIndex);
        Task taskToUpdate = new Task(toEdit);
        
		try {
	      taskToUpdate = updateTask(taskToUpdate, detailType, newDetail);
		} catch (IllegalValueException ive) {
			return new CommandResult(ive.getMessage());
		}

         assert model != null;
         try {
			model.editTask(targetIndex, taskToUpdate);
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing";
		}
         return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToUpdate));

     }
     
     private Task updateTask(Task taskToUpdate, String detailType, String newDetail) throws IllegalValueException {
    	 taskToUpdate.edit(detailType, newDetail);
    	 return taskToUpdate;
     }
 
 }