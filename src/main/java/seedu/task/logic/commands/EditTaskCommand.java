package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.Description;
import seedu.task.model.item.Flag;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.taskcommons.core.LogsCenter;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

//@@author A0127570H
/**
 * Executes editing of tasks according to the input argument.
 * @author kian ming
 */

public class EditTaskCommand extends EditCommand  {

    private final Logger logger = LogsCenter.getLogger(EditTaskCommand.class);
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    private static final Boolean TASK_DEFAULT_STATUS = false;
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
    public static final String MESSAGE_INVALID_DEADLINE_REMOVAL = "This task does not have a deadline";
    
    private Name newName;
    private Description newDescription;
    private Deadline newDeadline;
    private boolean isDeadlineToBeRemoved;
    
    private Task editTask;
    private ReadOnlyTask targetTask;
    
    /**
     * Convenience constructor using raw values.
     * Only fields to be edited will have values parsed in.
     * 
     * For deadline task to be edited to a floating task, a remove flag "rm" is parsed in the deadline argument
     *  
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public EditTaskCommand(Integer index, String name, String description, String deadline) throws IllegalValueException {
        
        setTargetIndex(index);
        isDeadlineToBeRemoved = checkPresenceOfRemoveDeadlineFlag(deadline);
        if (!name.isEmpty()) {
            newName = new Name(name);
        } 
        if (!description.isEmpty()) {
            newDescription = new Description(description);
        }
        if (!deadline.isEmpty() && !isDeadlineToBeRemoved) {
            newDeadline = new Deadline(deadline);
        }
    }

    private boolean checkPresenceOfRemoveDeadlineFlag(String deadline) {
        return deadline.equals(Flag.removeFlag);
    } 

	/**
     * Gets the task to be edited based on the index.
     * Only fields to be edited will have values updated.
     * @throws DuplicateTaskException 
     */
    @Override
    public CommandResult execute() {
        logger.info("-------[Executing EditTaskCommand]");
        try {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();        
            targetTask = lastShownList.get(getTargetIndex());

            editTask = editTask(targetTask);
            model.editTask(editTask, targetTask);
            
            logger.info("-------[Executed EditTaskCommand]" + this.toString());
            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editTask));

        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException ie) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        } 
    }

    /**
     * Edits the necessary fields.
     * Assumes task completion status is reinstated to not completed.
     * @return task that has the fields according to edit requirements.
     * @throws IllegalValueException 
     */    
    private Task editTask(ReadOnlyTask targetTask) throws IllegalValueException {
        
        if (newName == null) {
            newName = targetTask.getTask();
        }
        if (newDescription == null) {
            newDescription = targetTask.getDescription().orElse(null);
        }
        if (isDeadlineToBeRemoved && !targetTask.getDeadline().isPresent()) {
            throw new IllegalValueException(MESSAGE_INVALID_DEADLINE_REMOVAL);
        }
        if (newDeadline == null && targetTask.getDeadline().isPresent() && !isDeadlineToBeRemoved) {
            newDeadline = targetTask.getDeadline().get();
        }
        return new Task (this.newName, this.newDescription, this.newDeadline, TASK_DEFAULT_STATUS);        
    }

	@Override
	public CommandResult undo() {
        try {
            model.editTask((Task)targetTask, editTask);
            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editTask));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD+ " from " + this.targetTask.getAsText()
		+ " to " + this.editTask.getAsText();
	}

}
