package seedu.task.logic.commands;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Title;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in the task manager.
 */

public class EditCommand extends Command {
	public static final String COMMAND_WORD = "edit";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the task manager. "
            + "Parameters: Index t/newTaskName d/description sd/startDate dd/dueDate ts/tagSet"
            + "\nExample: " + COMMAND_WORD
            + " 1 t/newTaskName d/newDescription sd/11-11-2011 dd/11-11-2016 ts/tag ts/tag2"
            + "\nNote: You must have at least one parameter other than the index of the task. Use multiple 'ts/' to list multiple tags.";
	
	public final String MESSAGE_SUCCESS = "The data has been successfully edited.";
	public final String MESSAGE_NOT_FOUND = "The task was not found.";
	public final String MESSAGE_DUPLICATE = "The edited task is a duplicate of an existing task.";
	public static final String MESSAGE_SUCCESS_UNDO = "Undo of edit command";
	public final String MESSAGE_PARAM = "Incorrect parameters.";
	
	private ReadOnlyTask selectedTask;
	private Task copy, editedTask;
	private String newTitle, description, startDate, dueDate, interval, timeInterval;
	private Set<String> tags;
	private UnmodifiableObservableList<ReadOnlyTask> taskList;
	private int taskIndex, realIndex;
	//Task (before modification) for undo command
	private Task savedTaskForUndo; 

	/**
	 * Constructor
	 * @param index identifier of the task
	 * @param title new title of the task
	 * @param description new description of the task
	 * @param startDate new start date
	 * @param dueDate new due date
	 * @param interval new interval
	 * @param timeInterval new time interval
	 * @param tags new set of tags
	 */
	public EditCommand(int index, String title, String description, String startDate, String dueDate, String interval, String timeInterval, Set<String> tags) {
		taskIndex = index;
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
	 * @param index the index/identifier of the task
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
		model.deleteTask(selectedTask);
		model.addTaskWithSpecifiedIndex(editedTask, realIndex);
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
	 * Processing the parameters
	 * @param name new title
	 * @param description new description
	 * @param startDate new start date
	 * @param dueDate new due date
	 * @param interval new interval
	 * @param timeInterval new time interval
	 * @param tags new set of tags
	 * @throws IllegalValueException if any values are illegal
	 * @throws ParseException if any values are illegal
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
	 * Changes the tags in a task if specified in the parameters
	 * @param tags the new set of tags
	 * @throws IllegalValueException if the set of tags contains illegal values such as null
	 */
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
