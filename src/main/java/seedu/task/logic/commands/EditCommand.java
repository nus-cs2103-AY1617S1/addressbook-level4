package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Status;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {
	public static final String COMMAND_WORD = "edit";
	 
	 public static final String MESSAGE_USAGE = COMMAND_WORD
	            + ": Edit the task identified by the index number used in the last task listing.\n"
	            + "Parameters: INDEX TASKNAME at START_TIME to END_TIME [by DEADLINE] [#TAG...]\n"
	            + "Example: " + COMMAND_WORD
	            + " 4 night class at 08.00pm to 10.00pm by 12.00am";
	 
	 public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edit Task: %1$s";
	 
	 public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
	 
	 public final int targetIndex;
	 private final Task toEdit;
	 
	 
	 public EditCommand(int targetIndex, String name, String startTime, String endTime, String deadline, Set<String> tags)
	            throws IllegalValueException {
	        final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : tags) {
	            tagSet.add(new Tag(tagName));
	        }
	        this.toEdit = new Task(
	        		new Name(name),
	                new StartTime(startTime),
	                new EndTime(endTime),
	                new Deadline(deadline),
	                new UniqueTagList(tagSet),
	                new Status()
	                
	        );
	        this.targetIndex = targetIndex;
	    }
	    
	 
	 
	 //@Override
	 public CommandResult execute(boolean isUndo) {
	     UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
	     assert model != null;
	     if (lastShownList.size() < targetIndex) {
	         indicateAttemptToExecuteIncorrectCommand();
	         return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	     }

	     ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
	     ReadOnlyTask editedTask = null;
	     try {
	         model.addTask(targetIndex - 1, toEdit);
	         editedTask = lastShownList.get(targetIndex - 1);
	     }  catch (UniqueTaskList.DuplicateTaskException e) {
	         try {
	             model.deleteTask(editedTask);
	         } catch (TaskNotFoundException tnfe) {
	             assert false : "The target task cannot be missing";
	         }
	         return new CommandResult(MESSAGE_DUPLICATE_TASK);
	     } 
	     
	     try {
	         model.deleteTask(currentTask);
	     } catch (TaskNotFoundException e) {
	         assert false : "The target task cannot be missing";
	     }
	     
	     if(isUndo == false){
             history.getUndoList().add(new RollBackCommand("edit" , this.toEdit, (Task) currentTask));
	     }
	     return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
	 }



    @Override
    public CommandResult execute(int index) {
        return null;
        
    }
	 
	 
	 
	 
	 
	 
	 

}