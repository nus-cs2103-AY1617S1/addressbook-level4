package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
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

//@@author A0139097U
/**
 * Sets whether or not a task repeats itself in intervals

 *
 */
public class RepeatCommand extends Command{
	
	public static final String MESSAGE_SUCCESS = "Task repeated: ";
	public static final String MESSAGE_FAILURE = "Task unable to repeat";
	public static final String MESSAGE_TASK_IS_COMPLETE = "Unable to repeat a completed task";
	public static final String COMMAND_WORD = "repeat";
	public static final String TIME_INTERVAL_KEYWORDS = "Time Interval Keywords: weekly, monthly, yearly";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles on or off repeat for the selected task with a preset time extension.\n"
			+ "Parameters: INDEX (must be a positive integer)\n"
			+ "Example: " + COMMAND_WORD + " 1 [timeInterval]\n"
			+ TIME_INTERVAL_KEYWORDS + "\n"
			+ "To turn repeat off for a task, type: repeat [taskIndex] off";	
	
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
	
	@Override
	public CommandResult execute(){
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        
        /**
         * "Uncompletes" a task on repeat
         */
        String name = taskToDelete.getName().toString();
        if(name.contains(" is completed") && !timeInterval.equalsIgnoreCase("off")){
        	name = name.replace(" is completed", "");
        }
        
        /**
         * Adds tags if given valid time interval, else removes tags entirely.
         */
        UniqueTagList tagSet = taskToDelete.getTags();
        try{
        	if((!tagSet.contains(new Tag("repeats"))) && (!timeInterval.equalsIgnoreCase("off"))) {
        		tagSet.add(new Tag("repeats"));
        		tagSet.add(new Tag(timeInterval));
        	}
        	else{
        		tagSet = removeOldTags(tagSet);
        		if(!timeInterval.equalsIgnoreCase("off")) { // Allows tasks that are already repeating to switch to a
        			tagSet.add(new Tag("repeats"));			// different time interval
            		tagSet.add(new Tag(timeInterval));
        		}
        	}
        } catch (IllegalValueException iv) {
        	return new CommandResult(MESSAGE_FAILURE + " " + iv.getMessage());
        }
        
        try{
	        this.toAdd = new Task(
	        		new Name(name), 
	        		new Startline(taskToDelete.getStartline().value), 
	        		new Deadline(taskToDelete.getDeadline().value),
	        		new Priority(taskToDelete.getPriority().value),
	        		new UniqueTagList(tagSet));
        } catch (IllegalValueException ive){
        	return new CommandResult(MESSAGE_FAILURE + " " + ive.getMessage());
        }
        /**
         * Switches repeat off if time interval is specified as such. Else changes repeat statement.
         */
        if(timeInterval.equalsIgnoreCase("off")) {
        	toAdd.setRepeating(new Repeating(false, timeInterval));
        } else {
        	toAdd.setRepeating(new Repeating(true, timeInterval));
        }
        
        deleteTask(taskToDelete);
        addTask(toAdd);
        String point = String.format(MESSAGE_SUCCESS + toAdd.getRepeating().getRepeating());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredTaskList().size() - 1));
		model.currentState(point);
        return new CommandResult(point);
	}
	
	/**
	 * Checks if the timeInterval specified is a valid one.
	 * @param timeInterval
	 * @return	true if valid
	 */
	private boolean validTimeInterval(String timeInterval){
		switch (timeInterval){
			case "weekly":
				return true;
			case "monthly":
				return true;
			case "yearly":
				return true;
			case "off":
				return true;
			default:
				return false;
		}
	}
	
	
	/**
	 * Adds the given task.
	 * @param task
	 * 
	 */
	private void addTask(Task task){
		assert model != null;
        try {
            model.addTask(toAdd);
        } catch (UniqueTaskList.DuplicateTaskException e) {
        }
	}
	
	/**
	 * Deletes the given task.
	 * @param task
	 * @throws false assertion if task is missing.
	 */
	private void deleteTask(ReadOnlyTask task){
		try {
            model.deleteTask(task);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
	}
	
	/**
	 * Removes old tags from task.
	 * @param tagSet
	 * @return UniqueTagList tagSet without tags relevant to repeat.
	 * @throws IllegalValueException
	 */
	private UniqueTagList removeOldTags(UniqueTagList tagSet) throws IllegalValueException{
		Set<Tag> tags = tagSet.toSet();
		tags.remove(new Tag("repeats"));
		tags.remove(new Tag("weekly"));
		tags.remove(new Tag("monthly"));
		tags.remove(new Tag("yearly"));
		tagSet = new UniqueTagList(tags);
		return tagSet;
	}
}
