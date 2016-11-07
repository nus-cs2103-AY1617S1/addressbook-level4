package seedu.address.logic.commands;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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

//@@author A0139516B
public class CompleteCommand extends Command {

	public static final String COMMAND_WORD = "complete";
	public static final String COMMAND_WORD_2 = "com"; //complete shortcut
 
	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Complete the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
	public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_COMPLETED_FAILURE = "This task is already completed and cannot be completed twice.";
	
    public final int targetIndex;
	public Name name;
	public final Startline startline;
	public final Deadline deadline;
	public final Priority priority;
	public final UniqueTagList tagSet;
	public Repeating repeating;
	private Task toAdd;

	public CompleteCommand(int index) throws IllegalValueException {
		this.targetIndex = index;

		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : getTagsFromArgs("")) {
			tagSet.add(new Tag(tagName));
		}
        
		this.startline = new Startline(null);
		this.deadline = new Deadline(null);
		this.priority = new Priority("0");
		this.tagSet = new UniqueTagList(tagSet);
		this.repeating = new Repeating();
	}

	private Set<String> getDeadlinesFromArgs(String deadlineArguments) {
		// no tags
		if (deadlineArguments.isEmpty()) {
			return Collections.emptySet();
		}
		// replace first delimiter prefix, then split
		final Collection<String> deadlineStrings = Arrays
				.asList(deadlineArguments.replaceFirst(" d/", "").split(" t/"));
		return new HashSet<>(deadlineStrings);
	}

	private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
		// no tags
		if (tagArguments.isEmpty()) {
			return Collections.emptySet();
		}
		// replace first delimiter prefix, then split
		final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
		return new HashSet<>(tagStrings);
	}

	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);
		this.repeating = personToDelete.getRepeating();
		if(!repeating.getRepeating()) {
			try {
				String nameComplete = personToDelete.getName().toString();
	           
				if (!nameComplete.contains(" is completed"))
					nameComplete = nameComplete + " is completed";
				else{
					return new CommandResult(MESSAGE_COMPLETED_FAILURE);
					
				}
				name = new Name(nameComplete);
	
			} catch (IllegalValueException e1) {
				e1.printStackTrace();
			}		
		} else {
			return setToNextDate(personToDelete);
		}

		try {
			model.deleteTask(personToDelete);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}

		toAdd = new Task(this.name, this.startline, this.deadline, this.priority, this.tagSet);
		toAdd.setOverdue(false);
		assert model != null;
		try {
			model.addTask(toAdd);
			String point = String.format(MESSAGE_COMPLETE_TASK_SUCCESS, toAdd);
			model.currentState(point);
			return new CommandResult(point);
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}

	}
	
	/**
	 * Sets a repeating task to the next calendar interval.
	 * @param task
	 * @return a CommandResult indicating success if able to repeat.
	 */
	private CommandResult setToNextDate(ReadOnlyTask task) {
		Calendar startlineCal = task.getStartline().calendar;
		Calendar deadlineCal = task.getDeadline().calendar;
		String repeatedStartline;
		String repeatedDeadline;
		if(startlineCal != null) {									// If there is a startline, it is repeated 
			startlineCal = repeatDate(startlineCal, task);
			repeatedStartline = mutateToDate(startlineCal);
		} else {
			repeatedStartline = null;
		}
		if(deadlineCal != null) {									// If there is a deadline, it is repeated 
			deadlineCal = repeatDate(deadlineCal, task);
			repeatedDeadline = mutateToDate(deadlineCal);
		} else {
			repeatedDeadline = null;
		}
		deleteTask(task);
		toAdd = newRepeatingTask(task, repeatedStartline, repeatedDeadline);
		return addRepeatingTask();		
	}
	
	/**
	 * Adds the repeating task to model.
	 * @return a CommandResult indicating whether task has been added.  
	 */
	private CommandResult addRepeatingTask() {
		assert model != null;
		try {
			model.addTask(toAdd);
			String point = String.format(MESSAGE_COMPLETE_TASK_SUCCESS, toAdd);
			model.currentState(point);
			return new CommandResult(point);
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}		
	}
	
	/**
	 * Creates a new task based on the repeated Task.
	 * @param task
	 * @param repeatedStartline
	 * @param repeatedDeadline
	 * @return a new repeating Task set to the next time interval.
	 */
	private Task newRepeatingTask(ReadOnlyTask task, String repeatedStartline, String repeatedDeadline) {
		Startline newStartline;
		Deadline newDeadline;
		try {
			newStartline = new Startline(repeatedStartline);
			newDeadline = new Deadline(repeatedDeadline);
		} catch (IllegalValueException e) {
			e.printStackTrace();
			return null;
		}
		Task repeatingTask = new Task(task.getName(), newStartline, newDeadline, task.getPriority(), task.getTags());
		repeatingTask.setRepeating(repeating);
		return repeatingTask;
	}
	
	/**
	 * Deletes the task instance in model.
	 * @param task
	 */
	private void deleteTask(ReadOnlyTask task) {
		try {
			model.deleteTask(task);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}
	}
	
	/**
	 * Sets the date to the next time interval.
	 * @param toCheck
	 * @param task
	 * @return a Calendar value with the repeated value.
	 */
	private Calendar repeatDate(Calendar toCheck, ReadOnlyTask task){
		if(task.getRepeating().getRepeating()){
			switch(task.getRepeating().getTimeInterval()){
				case "weekly":
					toCheck.add(Calendar.DATE, 7);
					break;
				case "monthly":
					toCheck.add(Calendar.MONTH, 1);
					break;
				case "yearly":
					toCheck.add(Calendar.YEAR, 1);
					break;
				default :
					break;						
			}
		}
		return toCheck;
	}
	
	/**
	 * Gets the string value of a calendar.
	 * @param cal
	 * @return a String value in the format dd-MM-yy HH:mm
	 */
	private String mutateToDate(Calendar cal){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
		return sdf.format(cal.getTime());
	}
	

}
