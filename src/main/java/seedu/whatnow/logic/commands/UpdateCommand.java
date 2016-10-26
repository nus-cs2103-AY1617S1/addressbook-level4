package seedu.whatnow.logic.commands;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.Name;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.TaskDate;
import seedu.whatnow.model.task.TaskTime;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Update a task with new description/date/time/tag using it's last displayed index from WhatNow.
 */
public class UpdateCommand extends UndoAndRedo {
    
    public static final String COMMAND_WORD = "update";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Updates the description/date/time/tag of the task identified by the index number used in the last task listing.\n"
            + "Parameters: todo/schedule INDEX (must be a positive integer) description/date/time/tag DESCRIPTION/DATE/TIME/TAG\n"
            + "Example: " + COMMAND_WORD + " todo 1 tag priority low";
    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatNow";
    
    private static final String ARG_TYPE_DESCRIPTION = "description";
    private static final String ARG_TYPE_DATE = "date";
    private static final String ARG_TYPE_TIME = "time";
    private static final String ARG_TYPE_TAG = "tag";
    private static final String DELIMITER_BLANK_SPACE = " ";
    private static final String TASK_TYPE_TODO = "todo";
    private static final String TASK_TYPE_FLOATING = "floating";
    private static final String TASK_TYPE_NOT_FLOATING = "not_floating";
    private static final String DEFAULT = "default";
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    
    public final int targetIndex;
    public final String taskType;
    public final String arg_type;
    public final String arg;
    private Task toUpdate;
    
    public UpdateCommand(String taskType, int targetIndex, String arg_type, String arg) throws IllegalValueException, ParseException {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
        this.arg_type = arg_type;
        this.arg = arg;
        processArg();
    }
    
    /**
     * Processes the arguments in the update command
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws ParseException 
     */
    private void processArg() throws IllegalValueException, ParseException {
        String newName = DEFAULT;
        String date = null;
        String startDate = null;
        String endDate = null;
        String time = null;
        String startTime = null;
        String endTime = null;
        final Set<Tag> tagSet = new HashSet<>();
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == ZERO) {
            newName = arg;
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == ZERO) {
            if (arg != null) {
                String[] argComponents = arg.trim().split(DELIMITER_BLANK_SPACE);

                if (argComponents.length == ONE) {
                    date = argComponents[ZERO];  
                } else if (argComponents.length == TWO) {
                    startDate = argComponents[ZERO];
                    endDate = argComponents[ONE];
                }
            } 
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TIME) == ZERO) {
            if (arg != null) {
                String[] argComponents = arg.trim().split(DELIMITER_BLANK_SPACE);
                if (argComponents.length == ONE) {
                    time = argComponents[ZERO];
                } else if (argComponents.length == TWO) {
                    startTime = argComponents[ZERO];
                    endTime = argComponents[ONE];
                }
            } 
        }
        
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == ZERO) {
            if (arg != null) {
                Set<String> tags = processTag();
                for (String tagName : tags) {
                    tagSet.add(new Tag(tagName));
                }
            }   
        }
        
        TaskTime validateTime = null;
        TaskDate validateDate = null;
        
        if (time != null || startTime != null || endTime != null) {
            validateTime = new TaskTime(time, startTime, endTime, date, startDate, endDate);
            if (date == null && startDate == null && endDate == null) {
                date = validateTime.getDate();
            }
        }
        
        if (date != null || startDate != null || endDate != null) {
            validateDate = new TaskDate(date, startDate, endDate);
            if (date != null) {
                date = validateDate.getDate();
            } else if (startDate != null) {
                startDate = validateDate.getStartDate();
                endDate = validateDate.getEndDate();
            }
        }
        
        toUpdate = new Task(new Name(newName), date, startDate, endDate, time, startTime, endTime, new UniqueTagList(tagSet), null, null);
    }
    
    /**
     * Processes the tags in the update command
     */
    private Set<String> processTag() {
        if (arg.isEmpty()) {
            return Collections.emptySet();
        }
        final Collection<String> tagStrings = Arrays.asList(arg.split(DELIMITER_BLANK_SPACE));
        return new HashSet<>(tagStrings);
    }
    
    private void updateTheCorrectField(ReadOnlyTask taskToUpdate) {
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == ZERO) {
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            toUpdate.setStartDate(taskToUpdate.getStartDate());
            toUpdate.setEndDate(taskToUpdate.getEndDate());
            toUpdate.setTaskTime(taskToUpdate.getTaskTime());
            toUpdate.setStartTime(taskToUpdate.getStartTime());
            toUpdate.setEndTime(taskToUpdate.getEndTime());
            toUpdate.setStatus(taskToUpdate.getStatus());
            toUpdate.setTaskType(taskToUpdate.getTaskType());
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == ZERO) {
            toUpdate.setName(taskToUpdate.getName());
            if (arg != null) {
                toUpdate.setTaskTime(taskToUpdate.getTaskTime());
                toUpdate.setStartTime(taskToUpdate.getStartTime());
                toUpdate.setEndTime(taskToUpdate.getEndTime());
            }
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setStatus(taskToUpdate.getStatus());            
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TIME) == ZERO) {
            toUpdate.setName(taskToUpdate.getName());
            if (taskToUpdate.getTaskDate() != null) {
                toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            }
            
            if (taskToUpdate.getStartDate() != null) {
                toUpdate.setStartDate(taskToUpdate.getStartDate());
            }
            
            if (taskToUpdate.getEndDate() != null) {
                toUpdate.setEndDate(taskToUpdate.getEndDate());
            }
            
            toUpdate.setTags(taskToUpdate.getTags());
            toUpdate.setStatus(taskToUpdate.getStatus());
        }
        if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == ZERO) {
            toUpdate.setName(taskToUpdate.getName());
            toUpdate.setTaskDate(taskToUpdate.getTaskDate());
            toUpdate.setStartDate(taskToUpdate.getStartDate());
            toUpdate.setEndDate(taskToUpdate.getEndDate());
            toUpdate.setTaskTime(taskToUpdate.getTaskTime());
            toUpdate.setStartTime(taskToUpdate.getStartTime());
            toUpdate.setEndTime(taskToUpdate.getEndTime());
            toUpdate.setStatus(taskToUpdate.getStatus());
            toUpdate.setTaskType(taskToUpdate.getTaskType());
        }
        
        if (toUpdate.getTaskDate() == null && toUpdate.getStartDate() == null && toUpdate.getEndDate() == null && toUpdate.getTaskTime() == null && toUpdate.getStartTime() == null && toUpdate.getEndTime() == null) {
            toUpdate.setTaskType(TASK_TYPE_FLOATING);
        }
        else {
            toUpdate.setTaskType(TASK_TYPE_NOT_FLOATING);
        }
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        
        if (taskType.equals(TASK_TYPE_TODO) || taskType.equalsIgnoreCase(TASK_TYPE_FLOATING)) {
            lastShownList = model.getCurrentFilteredTaskList();
        } else {
            lastShownList = model.getCurrentFilteredScheduleList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - ONE);
        updateTheCorrectField(taskToUpdate);
        
        try {
            model.updateTask(taskToUpdate, toUpdate);
            model.getOldTask().push(taskToUpdate);
            model.getNewTask().push(toUpdate);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        model.getUndoStack().push(this);
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate));
    }

	//@@author A0139128A
	@Override
	public CommandResult undo() throws TaskNotFoundException {
		assert model != null;
		if(model.getOldTask().isEmpty() && model.getNewTask().isEmpty()) {
			return new CommandResult(String.format(UndoCommand.MESSAGE_FAIL));
		}
		else {
			ReadOnlyTask originalTask = model.getOldTask().pop();
			ReadOnlyTask unwantedTask = model.getNewTask().pop();
			model.getOldTask().push(unwantedTask);
			model.getNewTask().push(originalTask);
			try {
				model.updateTask(unwantedTask, (Task) originalTask);
			} catch(UniqueTaskList.DuplicateTaskException e) {
				return new CommandResult(UndoCommand.MESSAGE_FAIL);
			}
			return new CommandResult(UndoCommand.MESSAGE_SUCCESS); 
		}
	}

	//@@author A0139128A
	@Override
	public CommandResult redo() throws TaskNotFoundException {
		assert model != null;
		if(model.getOldTask().isEmpty() && model.getNewTask().isEmpty()) {
			return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
		}
		else {
			ReadOnlyTask originalTask = model.getNewTask().pop();
			ReadOnlyTask wantedTask = model.getOldTask().pop();
			model.getOldTask().push(originalTask);
			model.getNewTask().push(wantedTask);
			try {
				model.updateTask(originalTask, (Task) wantedTask);
			} catch (UniqueTaskList.DuplicateTaskException e) {
				return new CommandResult(RedoCommand.MESSAGE_FAIL);
			}
			return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
		}
	}
}