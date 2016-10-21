package tars.logic.commands;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.task.Priority;
import tars.model.task.Status;
import tars.model.task.Task;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;

/**
 * Confirms a specified datetime for a reserved task and add it into the task list
 * 
 * @@author A0124333U
 */

public class ConfirmCommand extends UndoableCommand {
    
    public static final String COMMAND_WORD = "confirm";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms a datetime for a reserved task"
            + " and adds the task into the task list.\n"
            + "Parameters: <RESERVED_TASK_INDEX> <DATETIME_INDEX> -p <PRIORITY> -t <TAG>\n" 
            + "Example: " + COMMAND_WORD + " 1 3 -p h -t tag1";
    
    public static final String MESSAGE_CONFIRM_SUCCESS = "Task Confirmation Success! New task added: %1$s";
    
    private final int taskIndex;
    private final int dateTimeIndex;
    private final String priority;
    final Set<Tag> tagSet = new HashSet<>();
    
    public ConfirmCommand(int taskIndex, int dateTimeIndex, String priority, Set<String> tags) throws IllegalValueException {
        this.taskIndex = taskIndex;
        this.dateTimeIndex = dateTimeIndex;
        this.priority = priority;        
        
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
    }   

    @Override
    public CommandResult undo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult redo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        UnmodifiableObservableList<RsvTask> lastShownList = model.getFilteredRsvTaskList();
        
        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_RSV_TASK_DISPLAYED_INDEX);
        }
        
        RsvTask rsvTask = lastShownList.get(taskIndex - 1);
        
        if (rsvTask.getDateTimeList().size() < dateTimeIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_DATETIME_DISPLAYED_INDEX);
        }
        
        
        Task toConfirm;
        
        try {
            toConfirm = new Task(rsvTask.getName(), rsvTask.getDateTimeList().get((dateTimeIndex - 1)), new Priority(priority), new Status(), 
                    new UniqueTagList(tagSet));
        } catch (IllegalValueException ive) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        };
        
        try {
            model.addTask(toConfirm);
        } catch (DuplicateTaskException e) {
            return new CommandResult(Messages.MESSAGE_DUPLICATE_TASK);
        }
        
        try {
            model.deleteRsvTask(rsvTask);
        } catch (RsvTaskNotFoundException rtnfe) {
            return new CommandResult(Messages.MESSAGE_RSV_TASK_CANNOT_BE_FOUND);
        }
        
        return new CommandResult(String.format(MESSAGE_CONFIRM_SUCCESS, toConfirm));
        
    }

}
