package jym.manager.logic.commands;

import java.time.LocalDateTime;
import java.util.Date;

import jym.manager.commons.core.Messages;
import jym.manager.commons.core.UnmodifiableObservableList;
import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.logic.Logic;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.Description;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Task;
import jym.manager.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Edits a task given its fields.
 * 
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "update";
    
    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Updated Task: %1$s";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) DESCRIPTION [by DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 do this";
    
	private String newDescription;
	private Date newDeadline;
	private Date newStartDate;
	private Date newEndDate;
	private boolean clearDeadline;
	private boolean clearDuration;

	private final Task toUpdate;
	public final int targetIndex;
	
	public EditCommand(int index, String description, LocalDateTime newDeadline) throws IllegalValueException {
		this.targetIndex = index;
		this.newDescription = description;
		this.toUpdate = new Task(
				new Description(description),
				newDeadline,
				new UniqueTagList());
		}
	

	@Override
	public CommandResult execute() {
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.updateTask(taskToDelete, toUpdate);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, taskToDelete));

	}

}
