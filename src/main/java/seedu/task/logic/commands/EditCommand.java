package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.person.EndTime;
import seedu.task.model.person.Location;
import seedu.task.model.person.Name;
import seedu.task.model.person.ReadOnlyTask;
import seedu.task.model.person.StartTime;
import seedu.task.model.person.Task;
import seedu.task.model.person.UniqueTaskList;
import seedu.task.model.person.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

public class EditCommand extends Command {
	public static final String COMMAND_WORD = "edit";
	 
	 public static final String MESSAGE_USAGE = COMMAND_WORD
	            + ": Deletes the task identified by the index number used in the last task listing.\n"
	            + "Parameters: INDEX (must be a positive integer)\n"
	            + "Example: " + COMMAND_WORD + " 1";
	 
	 public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edit Task: %1$s";
	 
	 public final int targetIndex;
	 private final Task toAdd;
	 
	 
	 public EditCommand(int targetIndex, String name, String startTime, String endTime, String location, Set<String> tags)
	            throws IllegalValueException {
	        final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : tags) {
	            tagSet.add(new Tag(tagName));
	        }
	        this.toAdd = new Task(
	        		new Name(name),
	                new StartTime(startTime),
	                new EndTime(endTime),
	                new Location(location),
	                new UniqueTagList(tagSet)
	                
	        );
	        this.targetIndex = targetIndex;
	    }
	    
	 
	 
	 //@Override
	 public CommandResult execute() {
		 UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		 
	        if (lastShownList.size() < targetIndex) {
	            indicateAttemptToExecuteIncorrectCommand();
	            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	        }

	        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

	        try {
	            model.deleteTask(taskToDelete);
	        } catch (TaskNotFoundException tnfe) {
	            assert false : "The target task cannot be missing";
	        }

	        //return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, taskToDelete));

	        //return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
	        
	        assert model != null;
	        try {
	            model.addTask(toAdd);
	            
	        } catch (UniqueTaskList.DuplicateTaskException e) {
	           // return new CommandResult(MESSAGE_DUPLICATE_PERSON);
	        }
	        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, toAdd));
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 

}
