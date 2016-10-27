package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Repeating;
import seedu.address.model.task.Startline;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class RepeatCommand extends Command{
	public static final String MESSAGE_SUCCESS = "Task repeated: ";
	public static final String MESSAGE_FAILURE = "Task unable to repeat";
	public static final String COMMAND_WORD = "repeat";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles on or off repeat for the selected task with a preset time extension.\n"
			+ "Parameters: INDEX (must be a positive integer)\n"
			+ "Example: " + COMMAND_WORD + " 1 weekly";
	public static final String TIME_INTERVAL_KEYWORDS = "Time Interval Keywords: weekly, monthly, yearly";
	
	public final int targetIndex;
	public String timeInterval;
    private Task toAdd;
	
	public RepeatCommand (int targetIndex, String timeInterval) throws IllegalValueException {
		this.targetIndex = targetIndex;
		if(validTimeInterval(timeInterval)){
			this.timeInterval = timeInterval;
		}
		else{
			throw new IllegalValueException(TIME_INTERVAL_KEYWORDS);
		}
	}
	
	public CommandResult execute(){
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        Repeating isRepeating = taskToDelete.getRepeating();
        
        UniqueTagList tagSet = taskToDelete.getTags();
        try{
        	if(!tagSet.contains(new Tag("repeats"))){
        		tagSet.add(new Tag("repeats"));
        		tagSet.add(new Tag(timeInterval));
        	}
        	else{
        		tagSet = removeOldTags(tagSet);
        	}
        } catch (IllegalValueException iv) {
        	return new CommandResult(MESSAGE_FAILURE + " " + iv.getMessage());
        }
        
        try{
	        this.toAdd = new Task(
	        		new Name(taskToDelete.getName().fullName), 
	        		new Startline(taskToDelete.getStartline().value), 
	        		new Deadline(taskToDelete.getDeadline().value),
	        		new Priority(taskToDelete.getPriority().value),
	        		new UniqueTagList(tagSet));
        } catch (IllegalValueException ive){
        	return new CommandResult(MESSAGE_FAILURE + " " + ive.getMessage());
        }
        toAdd.setRepeating(new Repeating(!isRepeating.getRepeating(), timeInterval));
        deleteTask(taskToDelete);
        addTask(toAdd);
		return new CommandResult(MESSAGE_SUCCESS + !isRepeating.getRepeating());
	}
	
	private boolean validTimeInterval(String timeInterval){
		switch (timeInterval){
			case "weekly":
				return true;
			case "monthly":
				return true;
			case "yearly":
				return true;
			default:
				return false;
		}
	}
	
	private void addTask(Task task){
		assert model != null;
        try {
            model.addPerson(toAdd);
        } catch (UniqueTaskList.DuplicateTaskException e) {
        }
	}
	
	private void deleteTask(ReadOnlyTask task){
		try {
            model.deleteTask(task);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
	}
	
	private UniqueTagList removeOldTags(UniqueTagList tagSet) throws IllegalValueException{
		Set<Tag> tags = tagSet.toSet();
		tags.remove(new Tag("repeats"));
		tags.remove(new Tag("weekly"));
		tags.remove(new Tag("montly"));
		tags.remove(new Tag("yearly"));
		tagSet = new UniqueTagList(tags);
		return tagSet;
	}
}
