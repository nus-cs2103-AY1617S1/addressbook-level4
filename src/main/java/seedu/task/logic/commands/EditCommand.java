package seedu.task.logic.commands;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Title;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Interval;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Task;
import seedu.task.model.task.TimeInterval;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in the address book.
 * Specifically, it finds the details that requires editing and creates a new task, deleting the old one.
 * Currently, it can only edit the name of a task.
 * This Command is currently VERY INEFFICIENT because it uses deep copying instead of actual editing.
 */

public class EditCommand extends Command {
	public static final String COMMAND_WORD = "edit";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the address book. "
            + "Parameters: Index t/newTaskName d/description sd/startDate dd/dueDate ts/tagSet"
            + "\nExample: " + COMMAND_WORD
            + " 1 t/newTaskName";
	
	public final String MESSAGE_SUCCESS = "The data has been successfully edited.";
	public final String MESSAGE_NOT_FOUND = "The task was not found.";
	public final String MESSAGE_DUPLICATE = "The edited task is a duplicate of an existing task.";
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of edit command";
	public final String MESSAGE_PARAM = "Incorrect parameters.";
	
	private ReadOnlyTask selectedTask;
	private Task copy, editedTask;
	private int paramLength;
	private String[] params;
	private String newTitle, description, startDate, dueDate, interval, timeInterval;
	private Set<String> tags;
	private UnmodifiableObservableList<ReadOnlyTask> taskList;
	private int taskIndex, realIndex;
	private ArrayList<Task> tempCopy = new ArrayList<Task>();
	//Task (before modification) for undo command
	private Task savedTaskForUndo; 

	/**
	 * Constructor
	 * @param name the name/identifier of the task
	 * @param strings the parameters
	 */
	public EditCommand(int index, String title, String description, String startDate, String dueDate, String interval, String timeInterval, Set<String> tags) {
		taskIndex = index;
		//paramLength = strings.length;
		//params = strings;
		newTitle = title;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.interval = interval;
		this.timeInterval = timeInterval;
		this.tags = tags;
	}
	
	
	/**
	 * Searches through the task list to find the specified task
	 * @param name the name/identifier of the task
	 * @return the specified task
	 * @throws TaskNotFoundException if the task was not found
	 */
	public ReadOnlyTask searchTask(int index) throws TaskNotFoundException {
		realIndex = taskIndex - 1;
		assert !taskList.isEmpty();
		return taskList.get(realIndex);
	}

	@Override
	public CommandResult execute() {
		taskList = model.getFilteredTaskList();
		assert model != null;
        if (taskList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
		try {
			selectedTask = searchTask(taskIndex);
			saveTaskForUndo(selectedTask);
			edit(selectedTask);
			modifyList();
		} catch (TaskNotFoundException e) {
			return new CommandResult(MESSAGE_NOT_FOUND);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE);
		} catch (IllegalValueException e) {
			return new CommandResult(MESSAGE_PARAM);
		} catch (ParseException e) {
			return new CommandResult(MESSAGE_PARAM);
		}
		return new CommandResult(MESSAGE_SUCCESS);
	}

	/**
	 * Begins the modification process in the original list
	 * @throws TaskNotFoundException
	 * @throws DuplicateTaskException
	 */
	private void modifyList() throws TaskNotFoundException, DuplicateTaskException {
		realIndex = taskIndex - 1;
		//for (int i = taskIndex; i < taskList.size(); i++) {
			//tempCopy.add((Task) taskList.get(i));
		//}
		model.deleteTask(selectedTask);
		//for (int i = 0; i < tempCopy.size(); i++) {
			//model.deleteTask(tempCopy.get(i));
		//}
		model.addTaskWithSpecifiedIndex(editedTask, realIndex);
		//for (int i = 0; i < tempCopy.size(); i++) {
			//model.addTask((Task) tempCopy.get(i));
		//}
	}
	
	/**
	 * Begins the editing process
	 * @param task the specified task to edit
	 * @throws IllegalValueException if the parameters provided were incorrect
	 * @throws ParseException 
	 */
	public void edit(ReadOnlyTask task) throws IllegalValueException, ParseException{
		copy = (Task) selectedTask;
		iterateParams(newTitle, description, startDate, dueDate, interval, timeInterval, tags);
		editedTask = copy;
	}
	
	/**
	 * Iterates through the parameters
	 * @param params array of parameters
	 * @throws IllegalValueException if the parameters provided were incorrect
	 * @throws ParseException 
	 */
	public void iterateParams(String name, String description, String startDate, String dueDate, String interval, String timeInterval, Set<String> tags) throws IllegalValueException, ParseException{
		if (name != null) {
		    changeTitle(name);
		}
		if (description != null) {
			changeDescription(description);
		}
		if (startDate != null) {
			changeStartDate(startDate);
		}
		if (dueDate != null) {
			changeDueDate(dueDate);
		}
		//if (interval != null) {
			//changeInterval(interval);
		//}
		//if (timeInterval != null) {
			//changeTimeInterval(timeInterval);
		//}
		if (tags != null && !tags.isEmpty()) {
			changeTags(tags);
		}
	}
	
	/**
	 * Changes the name in a task if specified in the parameters
	 * @param name the new name value
	 * @throws IllegalValueException if the name value is invalid
	 */
	public void changeTitle(String title) throws IllegalValueException {
		Title newTitle = new Title(title);
		copy = new Task(newTitle, copy.getDescription(), copy.getStartDate(), copy.getDueDate(), copy.getInterval(), copy.getTimeInterval(), copy.getStatus(), copy.getTags());
	}
	
	/**
	 * Changes the description in a task if specified in the parameters
	 * @param description the new description value
	 * @throws IllegalValueException if the description value is invalid
	 */
	public void changeDescription(String description) throws IllegalValueException {
		Description newDescription = new Description(description);
		copy = new Task(copy.getTitle(), newDescription, copy.getStartDate(), copy.getDueDate(), copy.getInterval(), copy.getTimeInterval(), copy.getStatus(), copy.getTags());
	}
	
	/**
	 * Changes the start date in a task if specified in the parameters
	 * @param startDate the new start date value
	 * @throws IllegalValueException if the start date value is invalid
	 * @throws ParseException if start date value is invalid
	 */
	public void changeStartDate(String startDate) throws IllegalValueException, ParseException {
		StartDate newStartDate = new StartDate(startDate);
		copy = new Task(copy.getTitle(), copy.getDescription(), newStartDate, copy.getDueDate(), copy.getInterval(), copy.getTimeInterval(), copy.getStatus(), copy.getTags());
	}

	/**
	 * Changes the due date in a task if specified in the parameters
	 * @param dueDate the new due date value
	 * @throws IllegalValueException if the due date value is invalid
	 * @throws ParseException if due date value is invalid
	 */
	public void changeDueDate(String dueDate) throws IllegalValueException, ParseException {
		DueDate newDueDate = new DueDate(dueDate);
		copy = new Task(copy.getTitle(), copy.getDescription(), copy.getStartDate(), newDueDate, copy.getInterval(), copy.getTimeInterval(), copy.getStatus(), copy.getTags());
	}

	/**
	 * Changes the interval in a task if specified in the parameters
	 * @param interval the new interval value
	 * @throws IllegalValueException if the interval value is invalid
	 */
	public void changeInterval(String interval) throws IllegalValueException {
		Interval newInterval = new Interval(interval);
		copy = new Task(copy.getTitle(), copy.getDescription(), copy.getStartDate(), copy.getDueDate(), newInterval, copy.getTimeInterval(), copy.getStatus(), copy.getTags());
	}
	
	/**
	 * Changes the time interval in a task if specified in the parameters
	 * @param timeInterval the new time interval value
	 * @throws IllegalValueException if the time interval value is invalid
	 */
	public void changeTimeInterval(String timeInterval) throws IllegalValueException {
		TimeInterval newTimeInterval = new TimeInterval(timeInterval);
		copy = new Task(copy.getTitle(), copy.getDescription(), copy.getStartDate(), copy.getDueDate(), copy.getInterval(), newTimeInterval, copy.getStatus(), copy.getTags());
	}
	
	public void changeTags(Set<String> tags) throws IllegalValueException {
		Set<Tag> newTags = new HashSet<>();
		for (String tagName : tags) {
            newTags.add(new Tag(tagName));
        }
		copy = new Task(copy.getTitle(), copy.getDescription(), copy.getStartDate(), copy.getDueDate(), copy.getInterval(), copy.getTimeInterval(), copy.getStatus(), new UniqueTagList(newTags));
	}

	private void saveTaskForUndo(ReadOnlyTask task){
		this.savedTaskForUndo = new Task(task.getTitle(), task.getDescription(), task.getStartDate(), task.getDueDate(), task.getInterval(), task.getTimeInterval(), task.getStatus(), task.getTags()); 
	}
	
	@Override
	public CommandResult executeUndo() {
		taskList = model.getFilteredTaskList();
		try {
			model.deleteTask(searchTask(taskIndex));
			model.addTaskWithSpecifiedIndex(savedTaskForUndo, taskIndex-1);
		} catch (TaskNotFoundException e) {
			return new CommandResult(MESSAGE_NOT_FOUND);
		} catch (DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE);
		}
		return new CommandResult(MESSAGE_SUCCESS_UNDO);
	}


	@Override
	public boolean isReversible() {
		return true;
	}

}
